package com.bolife.online.service;

import com.bolife.online.entity.Subject;

import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 17:49
 * @Description:
 */
public interface SubjectService {
    public Map<String,Object> getSubjects(int pageNum,int pageSize);

    Subject getSubjectById(Integer problemsetId);
}
