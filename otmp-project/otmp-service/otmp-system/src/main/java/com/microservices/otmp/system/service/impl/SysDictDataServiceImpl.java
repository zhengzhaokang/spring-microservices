package com.microservices.otmp.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.handler.sql.BaseSqlHandler;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.common.GetLoginUserUtil;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.entity.SysDictDataDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import com.microservices.otmp.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 *
 * @author lovefamily
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
    public static final String GEO_CODE = "geo_code";
    public static final String BUSINESS_GROUP = "business_group";
    @Autowired
    private SysDictDataManager sysDictDataManager;

    @Autowired
    GetLoginUserUtil loginUserUtil;

    @Autowired
    private RedisUtils redis;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        Long[] dictIds = Convert.toLongArray(dictData.getIds());
        dictData.setIdsArray(dictIds);
        List<SysDictDataDO> sysDictDataDOS = sysDictDataManager.selectDictDataList(dictData);
        List<SysDictData> sysDictDataList = new ArrayList<>(sysDictDataDOS.size());
        BeanUtils.copyListProperties(sysDictDataDOS, sysDictDataList, SysDictData.class);
        return sysDictDataList;
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictDataDO> sysDictDataDOS = sysDictDataManager.selectDictDataByType(dictType);
        //数据权限过滤
        sysDictDataDOS = filterDataScope(dictType, sysDictDataDOS);
        List<SysDictData> sysDictDataList = new ArrayList<>(sysDictDataDOS.size());
        for (SysDictDataDO sysDictDataDO : sysDictDataDOS) {
            SysDictData sysDictData = new SysDictData();
           org.springframework.beans. BeanUtils.copyProperties(sysDictDataDO, sysDictData);
            sysDictDataList.add(sysDictData);
        }
        return sysDictDataList;
    }

    @Override
    public List<SysDictData> selectDictDataByTypeCode(String dictType) {
        List<SysDictDataDO> sysDictDataDOS = sysDictDataManager.selectDictDataByTypeCode(dictType);
        //数据权限过滤
        sysDictDataDOS = filterDataScope(dictType, sysDictDataDOS);
        List<SysDictData> sysDictDataList = new ArrayList<>(sysDictDataDOS.size());
        for (SysDictDataDO sysDictDataDO : sysDictDataDOS) {
            SysDictData sysDictData = new SysDictData();
            org.springframework.beans. BeanUtils.copyProperties(sysDictDataDO, sysDictData);
            sysDictDataList.add(sysDictData);
        }
        return sysDictDataList;
    }

    public List<SysDictDataDO> filterDataScope(String type, List<SysDictDataDO> dictDataDOS) {
        if (null == dictDataDOS) {
            return dictDataDOS;
        }
        if (!("geo".equalsIgnoreCase(type) || GEO_CODE.equalsIgnoreCase(type)) && !BUSINESS_GROUP.equalsIgnoreCase(type)) {
            return dictDataDOS;
        }
        SysUser sysUser = loginUserUtil.getLoginUser();
        //不做数据权限过滤
        if (sysUser == null || sysUser.isAdmin() || CollUtil.isEmpty(sysUser.getSysUserDataScopeList())) {
            return dictDataDOS;
        }
        if (GEO_CODE.equalsIgnoreCase(type) || "geo".equals(type)) {
            return filterGeo(sysUser, dictDataDOS);
        }
        if (BUSINESS_GROUP.equalsIgnoreCase(type)) {
            return filterBG(sysUser, dictDataDOS);
        }
        return dictDataDOS;
    }

    /**
     * 过滤bg
     *
     * @param sysUser
     * @param sysDictDataDOS
     * @return
     */
    private List<SysDictDataDO> filterBG(SysUser sysUser, List<SysDictDataDO> sysDictDataDOS) {
        Set<String> geoCodeSet = getLoginUserDataScopeValue(BUSINESS_GROUP, sysUser.getSysUserDataScopeList());
        if (geoCodeSet == null) {
            return sysDictDataDOS;
        }
        return filterDataScope(sysDictDataDOS, geoCodeSet);
    }

    /**
     * 过滤geo
     *
     * @param sysUser
     * @param sysDictDataDOS
     * @return
     */
    private List<SysDictDataDO> filterGeo(SysUser sysUser, List<SysDictDataDO> sysDictDataDOS) {
        Set<String> geoCodeSet = getLoginUserDataScopeValue(GEO_CODE, sysUser.getSysUserDataScopeList());
        if (null == geoCodeSet) {
            return sysDictDataDOS;
        }
        return filterDataScope(sysDictDataDOS, geoCodeSet);
    }

    /**
     * 过滤数据权限
     *
     * @param sysDictDataDOS
     * @param dataScopeValues
     * @return
     */
    private List<SysDictDataDO> filterDataScope(List<SysDictDataDO> sysDictDataDOS, Set<String> dataScopeValues) {
        List<SysDictDataDO> dictDataDOS = new ArrayList<>();
        for (SysDictDataDO sysDictDataDO : sysDictDataDOS) {
            if (dataScopeValues.contains(sysDictDataDO.getDictValue())) {
                dictDataDOS.add(sysDictDataDO);
            }
        }
        return dictDataDOS;
    }

    /**
     * 查询当前登录人数据权限
     *
     * @param type
     * @param dataScopes
     * @return
     */
    private Set<String> getLoginUserDataScopeValue(String type, List<SysUserDataScope> dataScopes) {
        Set<String> dataScopeValues = new HashSet<>();
        if (null == dataScopes) {
            //这里就是要返回null null代表不控制权限 空集合代表没有权限
            return null;
        }
        for (SysUserDataScope dataScope : dataScopes) {
            if (type.equals(dataScope.getDataScopeKey())) {
                if (BaseSqlHandler.isSelectAll(dataScope)) {
                    //这里就是要返回null null代表不控制权限 空集合代表没有权限
                    return null;
                }
                dataScopeValues.add(dataScope.getDataScopeValue());
            }
        }
        return dataScopeValues;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return sysDictDataManager.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        SysDictDataDO sysDictDataDO = sysDictDataManager.selectDictDataById(dictCode);
        SysDictData sysDictData = new SysDictData();
        org.springframework.beans.BeanUtils.copyProperties(sysDictDataDO, sysDictData);
        return sysDictData;
    }

    /**
     * 通过字典ID删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDictDataById(Long dictCode) {
        int i = sysDictDataManager.deleteDictDataById(dictCode);
        init();
        return i;
    }

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDictDataByIds(String loginName, String ids) {
        int i = sysDictDataManager.deleteDictDataByIds(loginName, Convert.toStrArray(ids));
        init();
        return i;
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return sysDictDataManager.deleteByIds(ids);
    }

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDictData(SysDictData dictData) {
        SysDictDataDO sysDictDataDO = new SysDictDataDO();
        org.springframework.beans.BeanUtils.copyProperties(dictData, sysDictDataDO);
        int i = sysDictDataManager.insertDictData(sysDictDataDO);
        init();
        return i;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDictData(SysDictData dictData) {
        SysDictDataDO sysDictDataDO = new SysDictDataDO();
        org.springframework.beans.BeanUtils.copyProperties(dictData, sysDictDataDO);
        int i = sysDictDataManager.updateDictData(sysDictDataDO);
        init();
        return i;
    }

    @Override
    public PageInfo<SysDictData> selectDictDataPage(SysDictData dictData) {
        Long[] dictIds = Convert.toLongArray(dictData.getIds());
        dictData.setIdsArray(dictIds);
        List<SysDictDataDO> sysDictDataDOS = sysDictDataManager.selectDictDataList(dictData);
        PageInfo<SysDictDataDO> pageInfo = new PageInfo<>(sysDictDataDOS);
        List<SysDictData> sysDictDataList = new ArrayList<>(sysDictDataDOS.size());
        BeanUtils.copyListProperties(sysDictDataDOS, sysDictDataList, SysDictData.class);
        PageInfo<SysDictData> resultPageInfo = new PageInfo<>(sysDictDataList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    @Override
    public void init() {
        List<SysDictData> sysDictData = selectDictDataList(new SysDictData());
        Map<String, List<SysDictData>> groupByDictType = sysDictData.stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        groupByDictType.forEach((k, v) -> redis.set(RedisConstants.REDIS_DICTIONARY_DATA_NAME_PREFIX + k, v, RedisConstants.REDIS_EXPIRE_TIME_FOREVER));
    }

    @Override
    public int deleteDictDataByDictType(String dictType) {
        return sysDictDataManager.deleteDictDataByDictType(dictType);
    }

}
