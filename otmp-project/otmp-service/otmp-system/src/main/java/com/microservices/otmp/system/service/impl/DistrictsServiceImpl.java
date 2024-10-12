package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.system.domain.Districts;
import com.microservices.otmp.system.domain.entity.DistrictsDO;
import com.microservices.otmp.system.manager.DistrictsManager;
import com.microservices.otmp.system.service.IDistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地区 服务层实现
 *
 * @author lovefamily
 * @date 2018-12-19
 */
@Service
public class DistrictsServiceImpl implements IDistrictsService {
    @Autowired
    private DistrictsManager districtsManager;

    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    @Override
    public Districts selectDistrictsById(Integer id) {
        return districtsManager.selectDistrictsById(id);
    }

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    @Override
    public List<Districts> selectDistrictsList(Districts districts) {
        return districtsManager.selectDistrictsList(districts);
    }

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int insertDistricts(Districts districts) {
        DistrictsDO districtsDO = new DistrictsDO();
        org.springframework.beans.BeanUtils.copyProperties(districts, districtsDO);
        return districtsManager.insertDistricts(districtsDO);
    }

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int updateDistricts(Districts districts) {
        DistrictsDO districtsDO = new DistrictsDO();
        org.springframework.beans.BeanUtils.copyProperties(districts, districtsDO);
        return districtsManager.updateDistricts(districtsDO);
    }

    /**
     * 删除地区对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDistrictsByIds(String ids) {
        return districtsManager.deleteDistrictsByIds(Convert.toStrArray(ids));
    }

    @Override
    public PageInfo<Districts> selectDistrictsPage(Districts districts) {
        List<Districts> districtsList = districtsManager.selectDistrictsList(districts);
        return new PageInfo<>(districtsList);
    }

}
