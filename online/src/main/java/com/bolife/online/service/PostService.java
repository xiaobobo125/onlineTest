package com.bolife.online.service;

import com.bolife.online.entity.Post;

import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:17
 * @Description:
 */
public interface PostService {
    public Map<String, Object> getPosts(int pageNum,int pageSize);

    Post getPostById(Integer postId);

    Integer addPost(Post post);

    void deletePost(int pid);

    Boolean updateReplyNumById(int postId);

    Map<String,Object> getPostsByAuthorId(int page, int postPageSize, int id);

    boolean deletePostById(int id);

    void updateGoodNumById(int i, int id);
}
