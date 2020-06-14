package com.bolife.online.service.impl;

import com.bolife.online.entity.Contest;
import com.bolife.online.entity.Subject;
import com.bolife.online.mapper.ContestMapper;
import com.bolife.online.mapper.SubjectMapper;
import com.bolife.online.service.ContestService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:44
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class ContestServiceImpl implements ContestService {
    @Autowired(required = false)
    private ContestMapper contestMapper;

    @Autowired(required = false)
    private SubjectMapper subjectMapper;
    @Override
    public Map<String, Object> getContests(int pageNum, int pageSize) {
        Map<String,Object> data = new HashMap<>();
        Integer count = contestMapper.getCount();
        //查询不到信息
        if (count != null && count == 0){
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("contests", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        //没有下一页
        if(pageNum > totalPageNum){
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("contests", new ArrayList<>());
            return data;
        }
        List<Subject> subjectList = subjectMapper.getSubjects();
        //转换为map
        Map<Integer, String> subjects = subjectList.stream().
                collect(Collectors.toMap(Subject::getId, Subject::getName));
        PageHelper.startPage(pageNum,pageSize);
        List<Contest> contests = contestMapper.getContests();
        for (Contest contest : contests) {
            contest.setSubjectName(subjects.getOrDefault(contest.getSubjectId(),"未知科目"));
        }
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("contests", contests);
        return data;
    }

    @Override
    public Contest getContestById(int contestId) {
        return contestMapper.getContestById(contestId);
    }

    @Override
    public List<Contest> getAllContests() {
        return contestMapper.getContests();
    }

    @Override
    public boolean updateContest(Contest contest) {
        return contestMapper.updateContestById(contest) > 0;
    }

    @Override
    public boolean deleteContest(int id) {
        return contestMapper.deleteContest(id) > 0;
    }

    @Override
    public Integer addContest(Contest contest) {
        return contestMapper.insertContest(contest);
    }

    @Override
    public void updateContestStateById(int id, int contestState) {
        contestMapper.updateContestStateById(id,contestState);
    }

    @Override
    public void updataTotalScore(Integer contestId, Integer totalScore) {
        contestMapper.updateTotalScore(contestId,totalScore);
    }
}
