package com.bolife.online.service;

import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;

import java.util.List; /**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/9 18:19
 * @Description:
 */
public interface Question_ContentService {
    Integer saveQuestion(Integer contestId, List<Question> queTypes);

    List<Question_Contest> getByContestId(Integer contestId);

    Boolean deleteQuestion(int id);
}
