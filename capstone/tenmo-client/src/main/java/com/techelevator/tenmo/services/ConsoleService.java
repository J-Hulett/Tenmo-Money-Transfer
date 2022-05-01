package com.techelevator.tenmo.services;


import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.InvalidContactException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    public static final int PENDING = 1;
    public static final int REJECTED = 3;
    public static final int APPROVED = 2;

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printTransferListBanner() {
        System.out.println("-------------------------------------------");
        System.out.println("User");
        System.out.println("ID          Name");
        System.out.println("-------------------------------------------");
    }


    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void listContacts(AccountHolder[] contacts) {
        printTransferListBanner();
        if (contacts.length > 0) {
            for (AccountHolder accountHolder : contacts) {
                System.out.println(accountHolder.getTransferContacts());
            }
        } else {
            System.out.println("You dont have any contacts");
        }
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    public void printTransferList(Transfer[] transfers, AccountHolderService accountHolderService) {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        System.out.println("-------------------------------------------");
        for (Transfer transfer : transfers) {
            if (transfer.getTransferStatusId() != PENDING) {
                System.out.println(transfer.viewTransferToString(accountHolderService, true));
            }
        }
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    public void printRequestList(Transfer[] transfers, AccountHolderService accountHolderService) {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID            To                     Amount");
        System.out.println("-------------------------------------------");

        for (Transfer transfer : transfers) {
            if (transfer.getTransferStatusId() == PENDING && transfer.getAccountToId() != accountHolderService.getCurrentAccountHolder().getAccountId()) {
                System.out.println(transfer.viewTransferToString(accountHolderService, false));
            }
        }
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    public void printTransferDetail(int transferId, TransferService transferService, AccountHolderService accountHolderService) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println();
        System.out.println(transferService.getTransferById(transferId).toString(accountHolderService));
    }

    public void printApproveOrRejectTransferOption() {
        System.out.println("-------------------------------------------");
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't Approve or reject");
        System.out.println("-------------------------------------------");
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public int promptForApproveOrReject() {
        String userSelection = "Please choose an option:";
        return promptForInt(userSelection);
    }

    public int promptUserForTransferId() {
        String transferId = "Please enter transfer ID to view details (0 to cancel): ";
        return promptForInt(transferId);
    }

    public BigDecimal promptForTransferAmount() {
        String amountToTransfer = "Enter amount:";
        return promptForBigDecimal(amountToTransfer);
    }

    public int promptForUserIdToSendTo() {
        String enterId = "Enter ID of user you are sending to (0 to cancel):";
        return promptForInt(enterId);
    }

    public int promptForUserIdToRequestFrom() {
        String enterId = "Enter ID of user you are requesting from (0 to cancel):";
        return promptForInt(enterId);
    }

    public int promptForTransferIdToApproveOrReject() {
        String transferId = "Please enter transfer ID to approve/reject (0 to cancel): ";
        return promptForInt(transferId);
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public String printBalance(AccountHolderService accountHolderService) {
        return accountHolderService.getCurrentAccountHolder().getHolderBalance();

    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println();
        System.out.println("An error occurred. Check the log for details.");
        System.out.println();
    }

}
