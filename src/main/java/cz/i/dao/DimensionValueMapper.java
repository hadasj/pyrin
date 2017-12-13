package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.model.dimension.Dimension;
import cz.i.entity.model.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionValueMapper {
    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    Dimension dimensionById(@Param("id") Long id);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE order by id")
    List<DimensionValue> all();

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where dimension_id = #{dimensionId} order by id")
    List<DimensionValue> allByDimension(Long dimensionId);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where id = #{id}")
    DimensionValue oneById(Long id);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where code = #{code}")
    DimensionValue oneByCode(String code);

    @Insert("insert into DIMENSION_VALUE(ID_EXT, CODE, ALIAS, DIMENSION_ID, PARENT_ID, TEXT_CS, TEXT_EN, TEXT_BG) " +
        "values(#{idExt}, #{code}, #{alias}, #{dimensionId}, #{parentId}, #{textCs}, #{textEn}, #{textBg})")
    void insert(DimensionValueDb dimensionValue);

    @Update("update DIMENSION_VALUE set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, DIMENSION_ID = #{dimension.id}, " +
        "PARENT_ID = #{parentId}, TEXT_CS = #{textCs}, TEXT_EN = #{textEn}, TEXT_BG = #{textBg} where id = #{id}")
    void update(DimensionValueDb dimensionValue);

    @Delete("delete from DIMENSION_VALUE where id = #{id}")
    void delete(DimensionValueDb dimensionValue);
}
