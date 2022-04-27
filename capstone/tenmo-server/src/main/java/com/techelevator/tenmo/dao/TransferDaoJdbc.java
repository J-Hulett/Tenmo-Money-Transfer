package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferDaoJdbc implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public TransferDaoJdbc(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    @Override
    public Transfer initiateTransfer(Transfer transfer) {

        return null;
    }

    @Override
    public List<Transfer> listAllTransfersByAccount(int accountId) {
        List<Transfer> transferList = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM transfer " +
                "JOIN transfer_status on transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type on transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "WHERE account_from OR account_to = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);

        while (rowSet.next()) {
           Transfer transfer = mapRowToTransfer(rowSet);
           transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public Transfer getTransferById(int transferID) {
        Transfer transfer = new Transfer();
        String sql = "SELECT * " +
                "FROM transfer " +
                "JOIN transfer_status on transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type on transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "WHERE transfer_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferID);

        if (rowSet.next()) {
            transfer = mapRowToTransfer(rowSet);
        }

        return transfer;
    }

    public Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferType(rowSet.getString("transfer_type_desc"));
        transfer.setTransferStatus(rowSet.getString("transfer_status_desc"));
        transfer.setAccountToId(rowSet.getInt("account_to"));
        transfer.setAccountFromId(rowSet.getInt("account_from"));
        transfer.setTransferAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }




}
