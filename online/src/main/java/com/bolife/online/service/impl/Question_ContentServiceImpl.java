package com.bolife.online.service.impl;

import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;
import com.bolife.online.mapper.Quesetion_ContestMapper;
import com.bolife.online.service.Question_ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/9 18:20
 * @Description:
 */
@Service
public class Question_ContentServiceImpl implements Question_ContentService {

    @Autowired
    private Quesetion_ContestMapper quesetion_contestMapper;

    @Override
    public Integer saveQuestion(Integer contestId, List<Question> queTypes) {
        Integer count = 0;
        for (Question queType : queTypes) {
            quesetion_contestMapper.insert(contestId,queType.getId());
            count++;
        }
        return count;
    }

    @Override
    public List<Question_Contest> getByContestId(Integer contestId) {
        return quesetion_contestMapper.getByContestId(contestId);
    }

    @Override
    public Boolean deleteQuestion(int id) {
        return quesetion_contestMapper.deleteQuestion(id);
    }
}
