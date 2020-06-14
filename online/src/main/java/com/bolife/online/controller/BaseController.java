package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;
import com.bolife.online.exception.QexzWebError;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.Question_ContentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 基控制器
 */
public class BaseController{
    @Autowired
    private Question_ContentService question_contentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ContestService contestService;
    //log4j2
    protected Logger logger = LogManager.getLogger(BaseController.class);

    public Integer contestRandomQuestions(Integer questionType,List<Question> questions,Integer contestId,Integer type,Integer scoreCount){
        List<Question_Contest> haveQuestion = question_contentService.getByContestId(contestId);
        if(haveQuestion != null && haveQuestion.size() > 0){
            List<Question> questionByIds = questionService.getQuestionByIds(haveQuestion);
            for (Question questionById : questionByIds) {
                if(questionById.getQuestionType() == type){
                    questions.remove(questionById);
                }
            }
        }

        if(questionType > 0){
            List<Question> queTypes = new ArrayList<>();
            if(questions == null || questions.size() < questionType){
                return 0;
            }
            for (int i = 0 ;i < questionType; i++){
                Random random = new Random();
                int ele = random.nextInt(questions.size());
                queTypes.add(questions.get(ele));
                scoreCount += questions.get(ele).getScore();
                questions.remove(ele);
            }
            question_contentService.saveQuestion(contestId, queTypes);
        }
        return scoreCount;
    }
}
