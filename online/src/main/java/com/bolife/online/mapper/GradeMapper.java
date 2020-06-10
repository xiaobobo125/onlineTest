package com.bolife.online.mapper;

import com.bolife.online.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 13:26
 * @Description:
 */
@Mapper
public interface GradeMapper {
    Integer insertGrade(@Param("grade") Grade grade);

    Grade getGradeByConIdAndStuId(@Param("contestId") Integer contestId,
                                  @Param("studentId") Integer studentId);

    int getCountByStudentId(int studentId);

    List<Grade> getGradesByStudentId(int studentId);

    List<Grade> getGradesByContestId(@Param("contestId") int contestId);

    Integer updateGrade(@Param("grade") Grade grade);
}
