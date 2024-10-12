package com.microservices.otmp.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.auth.table.JoinTable;
import com.microservices.otmp.common.auth.table.Table;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.handler.scope.DataScopeHandler;
import com.microservices.otmp.common.handler.user.LoginUserHandler;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.ServletUtils;
//import com.microservices.otmp.gen.domain.GenTableColumnDTO;
//import com.microservices.otmp.gen.feign.RemoteGenService;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.dto.GenTableColumnDTO;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.handler.scope.DataScopeHandler;
import com.microservices.otmp.common.handler.user.LoginUserHandler;
import com.microservices.otmp.common.utils.ServletUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDataScopeUtil {

    protected Logger log = LoggerFactory.getLogger(UserDataScopeUtil.class);

    @Autowired
    RedisUtils redisUtils;

//    RemoteGenService remoteGenService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    LoginUserHandler loginUserHandler;

    @Autowired
    DataScopeSpringContextHolder springContextHolder;

//    @Autowired
//    public void setRemoteGenService(@Lazy RemoteGenService remoteGenService) {
//        this.remoteGenService = remoteGenService;
//    }

    /**
     * key  维度 value  维度的值的集合
     * @param sysUser
     * @return
     */
    public  Map<String, List<SysUserDataScope>> getUserDataScope(SysUser sysUser,String handlerName) {
        DataScopeHandler dataScopeHandler = springContextHolder.getDataScopeHandler(handlerName);
        log.info(" 由 {}{}",handlerName,"执行数据权限处理");
        return dataScopeHandler.getLoginUserDataScope(sysUser);
    }

    /**
     *
     * @return
     */
    public  SysUser getLoginUser() {
        SysUser sysUser = loginUserHandler.getLoginUser();
        if (null != sysUser) {
            return sysUser;
        }
        HttpServletRequest request = ServletUtils.getRequest();
        if (null == request) {
            log.error("数据权限获取用户信息失败，获取HttpServletRequest为空");
            return null;
        }
        String token = request.getHeader(Constants.TOKEN);
        if (StrUtil.isBlank(token)) {
            log.error("数据权限获取用户信息失败，获取token为空");
            return null;
        }
        SysUser user = redisUtils.get(Constants.ACCESS_TOKEN + token, SysUser.class);
        if (user == null) {
            log.error("数据权限获取用户信息失败，获取用户信息为空");
            return null;
        }
        return user;
    }


    /**
     * 把表里不存在的都remove掉
     *
     * @param scopeMap
     * @param tableName
     */
    public void handlerUserDataScope(Map<String, List<SysUserDataScope>> scopeMap, String tableName) {
        if (StrUtil.isBlank(tableName) || null == scopeMap) {
            return;
        }
        Set<String> tableColumns = genTableColumns(tableName);
        if (CollUtil.isNotEmpty(tableColumns)) {
            handlerDataScope(tableColumns, scopeMap);
        }
    }

    /**
     * 把表里没有的维度删掉
     * @param tableColumns
     * @param scopeMap
     */
    private void handlerDataScope(Set<String> tableColumns, Map<String, List<SysUserDataScope>> scopeMap) {
        Iterator<Map.Entry<String, List<SysUserDataScope>>> iterator = scopeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<SysUserDataScope>> next = iterator.next();
            if (!tableColumns.contains(next.getKey())) {
                iterator.remove();
            }
        }
    }
    /**
     * join 时的预处理 把表里没有且join 里边也没有的维度删掉
     * @param scopeMap
     * @param tableName
     * @param table
     */
    public  void handlerJoinUserDataScope(Map<String, List<SysUserDataScope>> scopeMap, String tableName, Table table) {
        if (null == table || CollectionUtils.isEmpty(table.getJoinTables())) {
            this.handlerUserDataScope(scopeMap, tableName);
        } else {
            Set<String> joinFields = getJoinFields(table);
            Set<String> tableColumns = getAllColumns(table, tableName);
            if (CollUtil.isNotEmpty(tableColumns)) {
                joinFields.addAll(tableColumns);
            }
            handlerDataScope(joinFields, scopeMap);
        }
    }

    public Set<String> getAllColumns(Table table, String tableName) {
        String name = "";
        if (table != null) {
            name = table.getName();
        }
        if (StrUtil.isBlank(name)) {
            name = tableName;
        }
        Set<String> tableColumns = genTableColumns(name);
        if (table != null && CollUtil.isNotEmpty(table.getJoinTables())) {
            Set<JoinTable> joinTables = table.getJoinTables();
            for (JoinTable joinTable : joinTables) {
                if (StrUtil.isBlank(joinTable.getJoinTableName())) {
                    continue;
                }
                tableColumns.addAll(genTableColumns(joinTable.getJoinTableName()));
            }
        }
        return tableColumns;
    }

    private Set<String> getJoinFields(Table table) {
        Set<String> set = new HashSet<>();
        String masterTableDimension = table.getDimension();
        if (StrUtil.isNotBlank(masterTableDimension)) {
            Set<String> m = Arrays.stream(masterTableDimension.split(",")).collect(Collectors.toSet());
            set.addAll(m);
        }
        Set<JoinTable> joinTables = table.getJoinTables();
        if (CollUtil.isNotEmpty(joinTables)) {
            for (JoinTable joinTable : joinTables) {
                if (StrUtil.isBlank(joinTable.getDimension())) {
                    continue;
                }
                Set<String> dimensions = Arrays.stream(joinTable.getDimension().split(",")).collect(Collectors.toSet());
                set.addAll(dimensions);
            }
        }
        return set;
    }

    /**
     * 获取表的字段
     *
     * @param tableName
     * @return
     */
    private Set<String> genTableColumns(String tableName) {
        List<GenTableColumnDTO> list = TableCache.get(tableName);
        if (CollUtil.isEmpty(list)) {
//            list = remoteGenService.getTableColumnByName(tableName);

            redisUtils.getList(RedisConstants.REDIS_DBTABLECOLUMN_DATA_NAME_PREFIX + tableName);
            if (CollUtil.isNotEmpty(list)) {
                TableCache.put(tableName, list);
            }
        }
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(GenTableColumnDTO::getColumnName).collect(Collectors.toSet());
        } else {
            throw new OtmpException("not fund Table");
        }
    }



}
