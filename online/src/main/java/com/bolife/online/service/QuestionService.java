package com.bolife.online.service;

import com.bolife.online.entity.Question;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 12:48
 * @Description:
 */
public interface QuestionService {
    public List<Question> getQuestionByContestId(Integer contestId);

    Map<String,Object> getQuestionByPCD(int page, int questionPageSize, Integer problemsetId, String content, int difficulty);

    Question getQuestionById(Integer problemId);
}
