package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.SysDictType;
import com.microservices.otmp.system.domain.entity.SysDictTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 * 
 * @author lovefamily
 */
@Mapper
public interface SysDictTypeMapper
{
    public List<SysDictTypeDO> selectDictTypeListLike(SysDictType dictType);
    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    public List<SysDictTypeDO> selectDictTypeList(SysDictType dictType);

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    public List<SysDictTypeDO> selectDictTypeAll();

    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    public SysDictTypeDO selectDictTypeById(Long dictId);

    /**
     * 通过字典ID删除字典信息
     * 
     * @param dictId 字典ID
     * @return 结果
     */
    public int deleteDictTypeById(Long dictId);

    /**
     * 批量删除字典类型
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteDictTypeByIds(@Param("loginName") String loginName,@Param("ids") Long[] ids);
    public int deleteDictTypeByDictType(@Param("dictType") String dictType);

    /**
     * 新增字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int insertDictType(SysDictTypeDO dictType);

    /**
     * 修改字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(SysDictTypeDO dictType);

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dictType 字典类型
     * @return 结果
     */
    public SysDictTypeDO checkDictTypeUnique(String dictType);
}
