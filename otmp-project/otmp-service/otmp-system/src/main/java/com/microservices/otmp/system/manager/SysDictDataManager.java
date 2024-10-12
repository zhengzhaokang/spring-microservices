package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.entity.SysDictDataDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */
public interface SysDictDataManager {
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictDataDO> selectDictDataList(SysDictData dictData);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictDataDO> selectDictDataByType(@Param("dictType") String dictType);

    /**
     * 根据字典类型查询字典数据 ,避免使用缓存，使用缓存影响到使用复杂的sql无法解析的问题
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictDataDO> selectDictDataByTypeCode(@Param("dictType") String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public SysDictDataDO selectDictDataById(@Param("dictCode") Long dictCode);

    /**
     * 查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    public int countDictDataByType(@Param("dictType") String dictType);

    /**
     * 通过字典ID删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    public int deleteDictDataById(@Param("dictCode") Long dictCode);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteDictDataByIds(@Param("loginName") String loginName, @Param("ids") String[] ids);

    /**
     * 新增字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int insertDictData(SysDictDataDO dictData);

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int updateDictData(SysDictDataDO dictData);

    /**
     * 同步修改字典类型
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新旧字典类型
     * @return 结果
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteByIds(Long[] ids);

    public int deleteDictDataByDictType(String dictType);
}
