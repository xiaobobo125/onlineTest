package com.bolife.online.service.impl;

import com.bolife.online.entity.Contest;
import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;
import com.bolife.online.mapper.ContestMapper;
import com.bolife.online.mapper.QuestionMapper;
import com.bolife.online.service.QuestionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 12:49
 * @Description:
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ContestMapper contestMapper;
    @Override
    public List<Question> getQuestionByContestId(Integer contestId) {
        return questionMapper.getQuestionByContestId(contestId);
    }

    @Override
    public Map<String, Object> getQuestionByPCD(int pageNum, int pageSize, Integer problemsetId, String content, int difficulty) {
        Map<String, Object> data = new HashMap<>();
        int count = questionMapper.getCountQuestionByPCD(problemsetId,
                content, difficulty);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("problemsetId", problemsetId);
            data.put("questions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("problemsetId", problemsetId);
            data.put("questions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.getQuestionByPCD(
                problemsetId, content, difficulty);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("questionsSize", questions.size());
        data.put("problemsetId", problemsetId);
        data.put("questions", questions);
        return data;
    }

    @Override
    public Question getQuestionById(Integer problemId) {
        return questionMapper.getQuestionById(problemId);
    }

    @Override
    public Map<String, Object> getQuestionsByContent(int pageNum, int pageSize, String content) {
        Map<String, Object> data = new HashMap<>();
        int count = questionMapper.getCountByContent(content);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("questions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("questions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.getQuestionsByContent(content);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("questionsSize", questions.size());
        data.put("questions", questions);
        return data;
    }

    @Override
    public int addQuestion(Question question) {
        if (question.getContestId() == 0) {
            question.setState(1);
        } else {
            question.setState(0);
            Contest contest = contestMapper.getContestById(question.getContestId());
            contest.setTotalScore(contest.getTotalScore()+question.getScore());
            contestMapper.updateContestById(contest);
        }
        return questionMapper.insertQuestion(question);
    }

    @Override
    public boolean deleteQuestion(int id) {
        return questionMapper.deleteQuestion(id) > 0;
    }

    @Override
    public Integer getQuestionCountByQuestionType(int questionType) {
        return questionMapper.getQuestionCountByQuestionType(questionType);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionMapper.getAllQuestion();
    }

    @Override
    public List<Question> getQuestionByIds(List<Question_Contest> byContestId) {
        List<Question> questions = new ArrayList<>();
        for (Question_Contest question_contest : byContestId) {
            Question questionById = questionMapper.getQuestionById(question_contest.getQuestionId());
            questions.add(questionById);
        }
        return questions;
    }
}
