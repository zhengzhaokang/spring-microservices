package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.ViewField;
import com.microservices.otmp.system.domain.ViewFieldInfo;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import com.microservices.otmp.system.manager.ViewFieldManager;
import com.microservices.otmp.system.mapper.ViewFieldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 页面字段展示信息Mapper接口
 *
 * @author sdms
 * @date 2022-02-15
 */

@Service
public class ViewFieldManagerImpl implements ViewFieldManager {

    @Autowired
    private ViewFieldMapper viewFieldMapper;
    @Autowired
    private SysDictDataManager selectDictDataByType;

    @Override
    public ViewFieldDO selectViewFieldById(Integer id) {
        return viewFieldMapper.selectViewFieldById(id);
    }

    @Override
    public List<ViewFieldDO> selectViewFieldList(ViewField viewField) {
        return viewFieldMapper.selectViewFieldList(viewField);
    }

    @Override
    public List<Map<String, Object>> selectViewFieldListPrecise(Map<String, Object> map) {
        return viewFieldMapper.selectViewFieldListPrecise(map);
    }

    @Override
    public int insertViewFieldMap(Map<String, Object> viewField) {
        return viewFieldMapper.insertViewFieldMap(viewField);
    }

    @Override
    public int insertViewField(ViewField viewField) {
        viewField.setCreateTime(DateUtils.getNowDate());
        viewField.setUpdateTime(DateUtils.getNowDate());
        return viewFieldMapper.insertViewField(viewField);
    }

    @Override
    public int updateViewField(ViewField viewField) {
        viewField.setUpdateTime(DateUtils.getNowDate());
        return viewFieldMapper.updateViewField(viewField);
    }

    @Override
    public int updateViewFieldMap(Map<String, Object> viewField) {
        return viewFieldMapper.updateViewFieldMap(viewField);
    }

    @Override
    public int deleteViewFieldById(Integer id) {
        return viewFieldMapper.deleteViewFieldById(id);
    }

    @Override
    public int deleteViewFieldByIds(String[] ids) {
        return viewFieldMapper.deleteViewFieldByIds(ids);
    }

    @Override
    public List<ViewFieldInfo> selectViewFieldInfoList(ViewFieldInfo viewFieldInfo) {
        return viewFieldMapper.selectViewFieldInfoList(viewFieldInfo);
    }

    @Override
    public List<ViewFieldDO> selectPageKeyList(ViewField viewField) {
        return viewFieldMapper.selectPageKeyList(viewField);
    }

    @Override
    public List<String> getTableNames() {
        return viewFieldMapper.getTableNames();
    }

    @Override
    public List<Map<String, Object>> selectViewFieldAndCondition(Map<String, Object> map) {
        return viewFieldMapper.selectViewFieldAndCondition(map);
    }

    @Override
    public List<Map<String, Object>> selectViewFieldAndTemplateAndCondition(Map<String, Object> map) {
        return viewFieldMapper.selectViewFieldAndTemplateAndCondition(map);
    }
}
