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

import cz.i.entity.dimension.Dimension;
import cz.i.entity.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionValueMapper {
    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    Dimension dimensionById(@Param("id") Long id);

    @Results(value = {
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE order by id")
    List<DimensionValue> all();

    @Results(value = {
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where dimension_id = #{dimensionId} order by id")
    List<DimensionValue> allByDimension(Long dimensionId);

    @Results(value = {
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where id = #{id}")
    DimensionValue oneById(Long id);

    @Results(value = {
        @Result(property = "dimension", column = "dimension_id", javaType = Dimension.class, one = @One(select = "dimensionById"))
    })
    @Select("select * from DIMENSION_VALUE where code = #{code}")
    DimensionValue oneByCode(String code);

    @Insert("insert into DIMENSION_VALUE(ID, CODE, ALIAS, DIMENSION_ID) values(#{id}, #{code}, #{alias}, #{dimension.id})")
    void insert(DimensionValue dimensionValue);

    @Update("update DIMENSION_VALUE set CODE = #{code}, ALIAS = #{alias}, DIMENSION_ID = #{dimension.id} where id = #{id}")
    void update(DimensionValue dimensionValue);

    @Delete("delete from DIMENSION_VALUE where id = #{id}")
    void delete(DimensionValue dimensionValue);
}
