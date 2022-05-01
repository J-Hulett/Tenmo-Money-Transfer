package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidAccountNumber;
import com.techelevator.tenmo.exceptions.InvalidUserId;
import com.techelevator.tenmo.model.AccountHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountHolderDaoJdbc implements AccountHolderDao {

    private JdbcTemplate jdbcTemplate;

    public AccountHolderDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AccountHolderDaoJdbc(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<AccountHolder> getListOfOtherAccountHoldersByUserId(int userId) throws InvalidUserId{
        List<AccountHolder> accountHolders = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM account " +
                "JOIN tenmo_user on tenmo_user.user_id = account.user_id " +
                "WHERE account.user_id != ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            AccountHolder accountHolder = mapRowToAccountHolder(rowSet);
            accountHolders.add(accountHolder);
        }
        return accountHolders;
    }

    @Override
    public AccountHolder getAccountHolderByAccountId(int accountId) throws InvalidAccountNumber {
        AccountHolder accountHolder = new AccountHolder();
        String sql = "SELECT * " +
                "FROM account " +
                "JOIN tenmo_user on tenmo_user.user_id = account.user_id " +
                "WHERE account_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);

        if (rowSet.next()) {
            accountHolder = mapRowToAccountHolder(rowSet);
        }
        return accountHolder;
    }

    @Override
    public AccountHolder getAccountHolderByUserId(int userId) throws InvalidUserId {
        AccountHolder accountHolder = new AccountHolder();
        String sql = "SELECT * " +
                "FROM account " +
                "JOIN tenmo_user on tenmo_user.user_id = account.user_id " +
                "WHERE tenmo_user.user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()) {
            accountHolder = mapRowToAccountHolder(rowSet);
        }
        return accountHolder;
    }

    public AccountHolder mapRowToAccountHolder(SqlRowSet rowSet) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setAccountId(rowSet.getInt("account_id"));
        accountHolder.setUsername(rowSet.getString("username"));
        accountHolder.setBalance(rowSet.getBigDecimal("balance"));
        accountHolder.setUserId(rowSet.getInt("user_id"));
        return accountHolder;
    }

}
