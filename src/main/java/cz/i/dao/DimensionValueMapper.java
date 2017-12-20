package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.db.dimension.DimensionValueDb;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionValueMapper {
    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where ID = #{id,jdbcType=INTEGER}")
    DimensionDb dimensionById(@Param("id") Long id);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = DimensionDb.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE order by id")
    List<DimensionValueDb> all();

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = DimensionDb.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where dimension_id = #{dimensionId} order by id")
    List<DimensionValueDb> allByDimension(Long dimensionId);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = DimensionDb.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where parent_id = #{id}")
    List<DimensionValueDb> allByParent(DimensionValueDb parent);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = DimensionDb.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where ID_EXT = #{id}")
    DimensionValueDb oneById(Long id);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT")
    })
    @Select("select * from DIMENSION_VALUE where DIMENSION_ID in (select ID from dimension where code = #{dimensionCode}) " +
        "and ID_EXT = #{valueIdExt}")
    DimensionValueDb oneByDimensionCodeAndIdExt(@Param("dimensionCode") String dimensionCode,
                                                @Param("valueIdExt") Long valueIdExt);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT")
    })
    @Select("select * from DIMENSION_VALUE where DIMENSION_ID = #{dimensionId} and ID_EXT = #{valueIdExt}")
    DimensionValueDb oneByDimensionIdAndIdExt(@Param("dimensionId") Long dimensionId,
                                              @Param("valueIdExt") Long valueIdExt);

    @Results(value = {
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "dimension_id", javaType = DimensionDb.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where code = #{code}")
    DimensionValueDb oneByCode(String code);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into DIMENSION_VALUE(ID_EXT, CODE, ALIAS, DIMENSION_ID, PARENT_ID, TEXT_CS, TEXT_EN, TEXT_BG) " +
        "values(#{idExt}, #{code}, #{alias}, #{dimensionId}, #{parentId}, #{textCs}, #{textEn}, #{textBg})")
    void insert(DimensionValueDb dimensionValue);

    @Update("update DIMENSION_VALUE set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, DIMENSION_ID = #{dimension.id}, " +
        "PARENT_ID = #{parentId}, TEXT_CS = #{textCs}, TEXT_EN = #{textEn}, TEXT_BG = #{textBg} where id = #{id}")
    void update(DimensionValueDb dimensionValue);

    @Delete("delete from DIMENSION_VALUE where id = #{id}")
    void delete(DimensionValueDb dimensionValue);
}
