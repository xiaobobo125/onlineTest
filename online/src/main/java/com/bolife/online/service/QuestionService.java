package com.bolife.online.service;

import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;

import java.io.InputStream;
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

    Map<String,Object> getQuestionsByContent(int page, int questionPageSize, String content);

    int addQuestion(Question question);

    boolean deleteQuestion(int id);

    Integer getQuestionCountByQuestionType(int questionType);

    List<Question> getAllQuestion();

    List<Question> getQuestionByIds(List<Question_Contest> byContestId);

    List<Question> getQuestionBySubjectId(Integer subjectId);

    boolean updateQuestion(Question question);

    Integer getCountQuestionBySubject(Integer subjectId);

    Integer insertManyFile(InputStream in, String filename, Integer subjectId);
}
