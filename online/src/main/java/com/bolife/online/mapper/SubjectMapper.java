package com.bolife.online.mapper;

import com.bolife.online.entity.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:55
 * @Description:
 */
@Mapper
public interface SubjectMapper {
    public List<Subject> getSubjects();

    Integer getCount();

    Subject getSubjectById(@Param("id") Integer id);

    int insertSubject(@Param("subject")Subject subject);

    Integer updateSubject(@Param("subject")Subject subject);

    boolean deleteSubjectById(@Param("id")int id);
}
