package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.entity.db.dimension.DimensionDb;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionMapper {
    public static final String DIMENSION_OF_FACTS_CODE = "DFAC";

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION order by id_ext, id")
    List<DimensionDb> all();

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    DimensionDb oneById(@Param("id") Long id);

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where id_ext = #{idExt}")
    DimensionDb oneByIdExt(Long idExt);

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where code = #{code,jdbcType=VARCHAR}")
    DimensionDb oneByCode(@Param("code") String code);

    @Insert("insert into DIMENSION(ID_EXT, CODE, ALIAS, MODE, STRUCTURE, TYPE, TEXT_CS, TEXT_EN, TEXT_BG) " +
        "values(#{idExt}, #{code}, #{alias}, #{mode}, #{structure}, #{type}, #{textCs}, #{textEn}, #{textBg})")
    void insert(DimensionDb dimension);

    @Update("update DIMENSION set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, MODE = #{mode}, STRUCTURE = #{structure}, " +
        "TYPE = #{type}, TEXT_CS = #{textCs}, TEXT_EN = #{textEn}, TEXT_BG = #{textBg} where ID = #{id}")
    void update(DimensionDb dimension);

    @Delete("delete from DIMENSION where ID = #{id}")
    void delete(DimensionDb dimension);
}
