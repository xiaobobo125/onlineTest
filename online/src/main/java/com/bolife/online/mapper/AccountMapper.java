package com.bolife.online.mapper;

import com.bolife.online.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:25
 * @Description:
 */
@Mapper
public interface AccountMapper {
    public List<Account> getAccounts();

    Account getAccountByUsername(@Param("username") String username);

    Account getAccountById(@Param("id") int id);

    boolean updateAccountById(@Param("account") Account account);

    int getCount();

    int insertAccount(@Param("account") Account account);

    int deleteAccount(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);
}
