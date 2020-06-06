package com.bolife.online.service;

import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:33
 * @Description:
 */
public interface ContestService {
    public Map<String,Object> getContests(int pageNum,int pageSize);
}
