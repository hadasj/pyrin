package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.common.DimensionMode;
import cz.i.common.DimensionStructure;
import cz.i.entity.dimension.Dimension;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionMapper {

    @Select("select * from DIMENSION order by id")
    List<Dimension> selectAllDimensions();

    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    Dimension selectDimensionById(@Param("id") Long id);

    @Select("select * from DIMENSION where code = #{code,jdbcType=VARCHAR}")
    Dimension selectDimensionByCode(@Param("code") String code);

    @Insert("insert into DIMENSION(CODE, ALIAS, MODE, STRUCTURE) values(#{code}, #{alias}, #{mode}, #{structure})")
    void insertDimension(Dimension dimension);

    @Update("update DIMENSION set CODE = #{code}, ALIAS = #{alias}, MODE=#{mode}, STRUCTURE=#{structure} where ID = #{id}")
    void updateDimension(Dimension dimension);

    @Delete("delete from DIMENSION where ID = #{id}")
    void deleteDimension(Dimension dimension);
}
