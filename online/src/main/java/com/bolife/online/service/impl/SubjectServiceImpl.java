package com.bolife.online.service.impl;

import com.bolife.online.entity.Subject;
import com.bolife.online.mapper.SubjectMapper;
import com.bolife.online.service.SubjectService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 17:50
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class SubjectServiceImpl implements SubjectService {
    @Autowired(required = false)
    private SubjectMapper subjectMapper;

    @Override
    public Map<String, Object> getSubjects(int pageNum, int pageSize) {
        Map<String,Object> data = new HashMap<>();
        Integer count = subjectMapper.getCount();
        //查询不到信息
        if (count != null && count == 0){
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("subjects", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        //没有下一页
        if(pageNum > totalPageNum){
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("subjects", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Subject> subjects = subjectMapper.getSubjects();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("subjects", subjects);
        return data;
    }

    @Override
    public Subject getSubjectById(Integer problemsetId) {
        return subjectMapper.getSubjectById(problemsetId);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectMapper.getSubjects();
    }

    @Override
    public int addSubject(Subject subject) {
        return subjectMapper.insertSubject(subject);
    }

    @Override
    public boolean updateSubject(Subject subject) {
        return subjectMapper.updateSubject(subject) > 0;
    }

    @Override
    public boolean deleteSubjectById(int id) {
        return subjectMapper.deleteSubjectById(id);
    }
}
