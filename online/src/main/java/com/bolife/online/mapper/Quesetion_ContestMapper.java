package com.bolife.online.mapper;

import com.bolife.online.entity.Question_Contest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/9 18:20
 * @Description:
 */
@Mapper
public interface Quesetion_ContestMapper {
    void insert(@Param("contestId") Integer contestId, @Param("questionId") int questionId);

    List<Question_Contest> getByContestId(@Param("contestId") Integer contestId);

    Boolean deleteQuestion(@Param("questionId") int questionId);
}
