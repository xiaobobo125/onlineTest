package com.bolife.online.mapper;

import com.bolife.online.entity.Contest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:45
 * @Description:
 */
@Mapper
public interface ContestMapper {
    /***
     * 获取个数
     * @return
     */
    public Integer getCount();

    /***
     * 获取全部的试题
     * @return
     */
    public List<Contest> getContests();

    Contest getContestById(@Param("contestId") int contestId);

    void updateContestById(@Param("contest") Contest contest);
}
