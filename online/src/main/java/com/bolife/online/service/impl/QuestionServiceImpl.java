package com.bolife.online.service.impl;

import com.bolife.online.entity.Contest;
import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;
import com.bolife.online.mapper.ContestMapper;
import com.bolife.online.mapper.QuestionMapper;
import com.bolife.online.service.QuestionService;
import com.bolife.online.util.ExcelUtil;
import com.bolife.online.util.FinalDefine;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 12:49
 * @Description:
 */
@Service
@SuppressWarnings("all")
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

    @Override
    public List<Question> getQuestionBySubjectId(Integer subjectId) {
        return questionMapper.getQuestionBySubjectId(subjectId);
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionMapper.updateQuestionById(question);
    }

    @Override
    public Integer getCountQuestionBySubject(Integer subjectId) {
        return questionMapper.getCountBySubjectId(subjectId);
    }

    @Override
    public Integer insertManyFile(InputStream in,String filename, Integer subjectId) {
        Integer cou = 0;
        try {
            List<List<Object>> bankListByExcel = ExcelUtil.getBankListByExcel(in, filename);
            List<Question> questions = new ArrayList<>();
            for (List<Object> objects : bankListByExcel) {
                Question qu = new Question();
                List<Object> list = objects;
                String flag = String.valueOf(list.get(0));
                String title = String.valueOf(list.get(1));
                if(StringUtils.isEmpty(title)){
                    continue;
                }
                qu.setTitle(String.valueOf(list.get(1)));
                qu.setContent(String.valueOf(list.get(1)));
                if(flag.equals("xz")){
                    qu.setOptionA(String.valueOf(list.get(2)));
                    qu.setOptionB(String.valueOf(list.get(3)));
                    qu.setOptionC(String.valueOf(list.get(4)));
                    qu.setOptionD(String.valueOf(list.get(5)));
                    qu.setAnswer(String.valueOf(list.get(6)));
                    qu.setQuestionType(0);
                    qu.setScore(2);
                }else  if(flag.equals("tk")){
                    String answer = "";
                    for (int i = 2 ; i < list.size();i++){
                        String ans = String.valueOf(list.get(i));
                        if(StringUtils.isNotEmpty(ans)){
                            answer += String.valueOf(list.get(i));
                            answer+= FinalDefine.SPLIT_CHAR;
                        }
                    }
                    qu.setScore(4);
                    qu.setAnswer(answer);
                    qu.setQuestionType(4);
                }else if(flag.equals("pd")){
                    qu.setOptionA(String.valueOf(list.get(2)));
                    qu.setOptionB(String.valueOf(list.get(3)));
                    qu.setAnswer(String.valueOf(list.get(4)));
                    qu.setQuestionType(5);
                    qu.setScore(1);
                }else if (flag.equals("hd")){
                    qu.setAnswer(String.valueOf(list.get(2)));
                    qu.setQuestionType(2);
                    qu.setScore(6);
                }
                qu.setState(1);
                qu.setUpdateTime(new Date());
                qu.setDifficulty(2);
                qu.setSubjectId(subjectId);
                questions.add(qu);
            }
            for (Question question : questions) {
                int count = questionMapper.insertQuestion(question);
                cou+=count;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cou;
    }
}
