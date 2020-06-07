package com.bolife.online.service.impl;

import com.bolife.online.entity.Grade;
import com.bolife.online.mapper.GradeMapper;
import com.bolife.online.service.GraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 13:25
 * @Description:
 */
@Service
public class GradeServiceImpl implements GraderService {
    @Autowired
    private GradeMapper gradeMapper;
    @Override
    public Integer addGrade(Grade grade) {
        return gradeMapper.insertGrade(grade);
    }

    @Override
    public Grade getGradeByConIdAndStuId(int contestId, int studentId) {
        return gradeMapper.getGradeByConIdAndStuId(contestId,studentId);
    }
}
