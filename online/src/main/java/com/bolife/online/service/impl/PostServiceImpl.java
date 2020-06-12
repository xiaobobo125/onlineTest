package com.bolife.online.service.impl;

import com.bolife.online.entity.Post;
import com.bolife.online.mapper.PostMapper;
import com.bolife.online.service.PostService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:18
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public Map<String, Object> getPosts(int pageNum, int pageSize) {
        Map<String,Object> data = new HashMap<>();
        Integer count = postMapper.getCount();
        //查询不到信息
        if (count != null && count == 0){
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("posts", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        //没有下一页
        if(pageNum > totalPageNum){
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("posts", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Post> posts = postMapper.getPosts();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("posts", posts);
        return data;
    }

    @Override
    public Post getPostById(Integer postId) {
        return postMapper.getPostById(postId);
    }

    @Override
    public Integer addPost(Post post) {
        return postMapper.insert(post);
    }

    @Override
    public void deletePost(int pid) {
        postMapper.deletePost(pid);
    }

    @Override
    public Boolean updateReplyNumById(int postId) {
        return postMapper.updateReplyNumById(postId, new Date()) > 0;
    }

    @Override
    public Map<String, Object> getPostsByAuthorId(int pageNum, int pageSize, int authorId) {
        Map<String, Object> data = new HashMap<>();
        int count = postMapper.getCountByAuthorId(authorId);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("posts", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("posts", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = postMapper.getPostsByAuthorId(authorId);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("posts", posts);
        return data;
    }

    @Override
    public boolean deletePostById(int id) {
        return postMapper.deletePost(id) > 0;
    }

    @Override
    public void updateGoodNumById(int i, int id) {
        postMapper.updateGoodNumById(i,id);
    }
}
