package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.Districts;
import com.microservices.otmp.system.domain.entity.DistrictsDO;
import com.microservices.otmp.system.manager.DistrictsManager;
import com.microservices.otmp.system.mapper.DistrictsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 16:58
 */

@Service
public class DistrictsManagerImpl implements DistrictsManager {

    @Autowired
    private DistrictsMapper districtsMapper;

    @Override
    public Districts selectDistrictsById(Integer id) {
        return districtsMapper.selectDistrictsById(id);
    }

    @Override
    public List<Districts> selectDistrictsList(Districts districts) {
        return districtsMapper.selectDistrictsList(districts);
    }

    @Override
    public int insertDistricts(DistrictsDO districtsDO) {
        return districtsMapper.insertDistricts(districtsDO);
    }

    @Override
    public int updateDistricts(DistrictsDO districtsDO) {
        return districtsMapper.updateDistricts(districtsDO);
    }

    @Override
    public int deleteDistrictsById(Integer id) {
        return districtsMapper.deleteDistrictsById(id);
    }

    @Override
    public int deleteDistrictsByIds(String[] ids) {
        return districtsMapper.deleteDistrictsByIds(ids);
    }
}
