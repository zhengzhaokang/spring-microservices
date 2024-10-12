package com.microservices.otmp.masterdata.manager.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.microservices.otmp.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseGtnCategoryMapper;
import com.microservices.otmp.masterdata.domain.entity.BizBaseGtnCategoryDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseGtnCategoryManager;

/**
 * gtnCategoryManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
@Service
public class BizBaseGtnCategoryManagerImpl implements IBizBaseGtnCategoryManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseGtnCategoryManagerImpl.class);

    @Autowired
    private BizBaseGtnCategoryMapper bizBaseGtnCategoryMapper;

    /**
     * 查询gtnCategory
     * 
     * @param id gtnCategory主键
     * @return gtnCategoryDO
     */
    @Override
    public BizBaseGtnCategoryDO selectBizBaseGtnCategoryById(Long id)
    {
        return bizBaseGtnCategoryMapper.selectBizBaseGtnCategoryById(id);
    }

    /**
     * 查询gtnCategory列表
     *
     * @param bizBaseGtnCategory gtnCategory
     * @return gtnCategoryDO
     */
    @Override
    public List<BizBaseGtnCategoryDO> selectBizBaseGtnCategoryList(BizBaseGtnCategoryDTO bizBaseGtnCategory)
    {

        String ids = bizBaseGtnCategory.getIds();
        if (StringUtils.isNotEmpty(ids)) {
            List<String> idsList = Arrays.stream(ids.split(",")).collect(Collectors.toList());
            List<Long> list = idsList.stream().map(Long::parseLong).collect(Collectors.toList());
            bizBaseGtnCategory.setIdsList(list);
        }
        return bizBaseGtnCategoryMapper.selectBizBaseGtnCategoryList(bizBaseGtnCategory);
    }
}
