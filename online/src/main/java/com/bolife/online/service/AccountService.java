package com.bolife.online.service;

import com.bolife.online.entity.Account;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:23
 * @Description:
 */
public interface AccountService {
    public List<Account> getAllAccount();

    public Account getAccountByUsername(String username);

    Account getAccountById(int authorId);

    boolean updateAccount(Account currentAccount);

    Map<String,Object> getAccounts(int page, int accountPageSize);

    int addAccount(Account account);

    boolean deleteAccount(int id);

    boolean disabledAccount(int id);

    boolean abledAccount(int id);
}
