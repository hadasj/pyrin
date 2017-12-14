package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.entity.db.fact.FactDb;
import cz.i.entity.db.fact.FactValueDb;
import cz.i.entity.model.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
public interface FactMapper {
    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from FACT where parent_id = #{parentId} order by id")
    List<FactDb> metadataByParentId(Long parentId);

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from FACT where id_ext = #{idExt} order by id")
    List<FactDb> parentByIdExt(Long idExt);

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION_VALUE where id = #{id,jdbcType=INTEGER}")
    DimensionValue oneDimensionValueById(@Param("id") Long id);

    @Results({
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "valueValue", column = "VALUE_VALUE"),
        @Result(property = "dimensionValue", column = "DIMENSION_VALUE_ID", javaType = DimensionValue.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT_VALUE where fact_id = #{factId} order by id")
    List<FactValueDb> valuesByFactId(Long factId);

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "metadata", column = "ID_EXT", javaType = List.class, many = @Many(select = "metadataByParentId"))/*,
        @Result(property = "values", column = "id", javaType = List.class, many = @Many(select = "valuesByFactDbId"))*/})
    @Select("select * from FACT order by id")
    List<FactDb> all();

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "metadata", column = "ID_EXT", javaType = List.class, many = @Many(select = "metadataByParentId"))})
    @Select("select * from FACT where id = #{id,jdbcType=INTEGER}")
    FactDb oneById(@Param("id") Long id);

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "metadata", column = "ID_EXT", javaType = List.class, many = @Many(select = "metadataByParentId"))})
    @Select("select * from FACT where code = #{code,jdbcType=VARCHAR} order by id")
    List<FactDb> allByCode(@Param("code") String code);

    @Insert("insert into FACT(ID_EXT, CODE, ALIAS, NAME, FACT_TYPE, PARENT_ID) " +
        "values(#{idExt}, #{code}, #{alias}, #{name}, #{factType}, #{parentId})")
    void insert(FactDb fact);

    @Update("update FACT set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, NAME=#{name}, PARENT_ID=#{parent.id} where ID = #{id}")
    void update(FactDb fact);

    @Delete("delete from FACT where ID = #{id}")
    void delete(FactDb fact);
}
