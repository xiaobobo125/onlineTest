package com.bolife.online.service;

import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:17
 * @Description:
 */
public interface PostService {
    public Map<String, Object> getPosts(int pageNum,int pageSize);
}
