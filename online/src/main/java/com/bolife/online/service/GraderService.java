package com.bolife.online.service;

import com.bolife.online.entity.Grade;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 13:25
 * @Description:
 */
public interface GraderService {
    public Integer addGrade(Grade grade);

    Grade getGradeByConIdAndStuId(int contestId, int studentId);
}
