package com.bolife.online.service;

import com.bolife.online.entity.Contest;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:33
 * @Description:
 */
public interface ContestService {
    public Map<String,Object> getContests(int pageNum,int pageSize);

    Contest getContestById(int contestId);

    List<Contest> getAllContests();

    boolean updateContest(Contest contest);

    boolean deleteContest(int id);

    Integer addContest(Contest contest);

    void updateContestStateById(int id, int contestState);

    void updataTotalScore(Integer contestId, Integer totalScore);
}
