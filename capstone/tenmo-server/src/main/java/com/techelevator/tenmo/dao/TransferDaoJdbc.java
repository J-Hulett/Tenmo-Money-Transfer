package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.exceptions.ServerSideSQLError;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransferDaoJdbc implements TransferDao {
    private JdbcTemplate jdbcTemplate;


    public TransferDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * DataSource Needs to be Commented to Run Server and Uncommented to Run tests.
     */

//    public TransferDaoJdbc(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    @Override
    public boolean initiateTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?,?,?,?,?);";
        return jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFromId(),
                transfer.getAccountToId(), transfer.getTransferAmount()) == 0;
    }

    @Override
    public boolean rejectTransfer(Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?;";
        return jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId()) == 0;
    }

    @Override
    public boolean acceptTransfer(Transfer transfer) {
        String sql = "BEGIN TRANSACTION; " +
                "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?; " +
                "UPDATE account SET balance = balance - ? WHERE account_id = ?; " +
                "UPDATE account SET balance = balance + ? WHERE account_id = ?; " +
                "COMMIT;";
        return jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId(),
                transfer.getTransferAmount(), transfer.getAccountFromId(), transfer.getTransferAmount(),
                transfer.getAccountToId()) == 0;
    }

    @Override
    public boolean sendFunds(Transfer transfer) {
        String sql = "BEGIN TRANSACTION; " +
                "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?,?,?,?,?); " +
                "UPDATE account SET balance = balance - ? WHERE account_id = ?; " +
                "UPDATE account SET balance = balance + ? WHERE account_id = ?; " +
                "COMMIT;";
        return jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFromId(),
                transfer.getAccountToId(), transfer.getTransferAmount(), transfer.getTransferAmount(), transfer.getAccountFromId(),
                transfer.getTransferAmount(), transfer.getAccountToId()) == 0;
    }

    @Override
    public List<Transfer> listAllTransfers(int accountId) {
        List<Transfer> transferList = new ArrayList<>();

        String sql = "SELECT * FROM transfer JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id "
                + "WHERE account_from = ? OR account_to = ?;";


        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        while (rowSet.next()) {
            Transfer transfer = mapRowToTransfer(rowSet);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();

        String sql = "SELECT * FROM transfer JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "WHERE transfer_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);

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
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        return transfer;
    }
}
