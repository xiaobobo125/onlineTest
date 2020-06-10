package com.bolife.online.service;

import com.bolife.online.entity.Grade;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 13:25
 * @Description:
 */
public interface GraderService {
    public Integer addGrade(Grade grade);

    Grade getGradeByConIdAndStuId(int contestId, int studentId);

    Map<String, Object> getGradesByStudentId(int pageNum, int pageSize, int studentId);

    List<Grade> getGradesByContestId(int contestId);

    boolean updateGrade(Grade grade);
}
