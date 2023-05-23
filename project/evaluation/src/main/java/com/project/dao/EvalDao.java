package com.project.dao;

import com.project.domain.Evaluation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EvalDao {
    @Insert("insert into evaluation values(#{point},#{text},#{pic},#{orderId},#{client},#{server})")
    void addEval(Evaluation evaluation);

    @Select("select * from evaluation where server = #{server}")
    List<Evaluation> getEval(Integer server);
}
