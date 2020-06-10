package com.bolife.online.service.impl;

import com.bolife.online.entity.Grade;
import com.bolife.online.mapper.GradeMapper;
import com.bolife.online.service.GraderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getGradesByStudentId(int pageNum, int pageSize, int studentId) {
        Map<String, Object> data = new HashMap<>();
        int count = gradeMapper.getCountByStudentId(studentId);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("grades", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("grades", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Grade> grades = gradeMapper.getGradesByStudentId(studentId);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("grades", grades);
        return data;
    }

    @Override
    public List<Grade> getGradesByContestId(int contestId) {
        return gradeMapper.getGradesByContestId(contestId);
    }

    @Override
    public boolean updateGrade(Grade grade) {
        return gradeMapper.updateGrade(grade) > 0;
    }
}
