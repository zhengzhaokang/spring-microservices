package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.Districts;
import com.microservices.otmp.system.domain.entity.DistrictsDO;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 16:58
 */
public interface DistrictsManager {
    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    public Districts selectDistrictsById(Integer id);

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    public List<Districts> selectDistrictsList(Districts districts);

    /**
     * 新增地区
     *
     * @param districtsDO 地区信息
     * @return 结果
     */
    public int insertDistricts(DistrictsDO districtsDO);

    /**
     * 修改地区
     *
     * @param districtsDO 地区信息
     * @return 结果
     */
    public int updateDistricts(DistrictsDO districtsDO);

    /**
     * 删除地区
     *
     * @param id 地区ID
     * @return 结果
     */
    public int deleteDistrictsById(Integer id);

    /**
     * 批量删除地区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDistrictsByIds(String[] ids);

}
