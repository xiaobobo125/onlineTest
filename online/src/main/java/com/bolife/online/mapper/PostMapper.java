package com.bolife.online.mapper;

import com.bolife.online.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:19
 * @Description:
 */
@Mapper
public interface PostMapper {
    public Integer getCount();

    public List<Post> getPosts();

    Post getPostById(@Param("postId") Integer postId);

    Integer insert(@Param("post") Post post);

    void deletePost(@Param("pid") int pid);
}
