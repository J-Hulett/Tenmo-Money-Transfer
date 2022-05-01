package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountHolderService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.util.BasicLogger;
import com.techelevator.util.TransferNotAllowedException;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountHolderService accountHolderService = new AccountHolderService();
    private final TransferService transferService = new TransferService();

    private AuthenticatedUser currentUser;


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser.getToken() != null) {
            accountHolderService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
        }
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        int userId = Math.toIntExact(currentUser.getUser().getId());
        System.out.println(consoleService.printBalance(userId, accountHolderService));
    }

    private void viewTransferHistory() {
        consoleService.printTransferList(transferService.getTransferList(), accountHolderService);
        int transferId = consoleService.promptUserForTransferId();
        if (transferId == 0) {
            mainMenu();
        } else if (transferService.getTransferById(transferId) == null) {
            System.out.println();
            System.out.println("Invalid Transfer ID, please try again!");
            System.out.println();
            viewTransferHistory();
        } else {
            consoleService.printTransferDetail(transferId, transferService, accountHolderService);
        }

    }

    private void viewPendingRequests() {
        int selectedOption = -1;
        boolean success = false;
        consoleService.printRequestList(transferService.getTransferList(), accountHolderService);
        int transferId = consoleService.promptForTransferIdToApproveOrReject();
        if (transferId == 0) {
            mainMenu();
        } else if (transferService.getTransferById(transferId) == null) {
            System.out.println();
            System.out.println("Invalid Transfer ID, please try again!");
            System.out.println();
            viewPendingRequests();
        } else if (transferService.getTransferById(transferId).getAccountFromId() != accountHolderService.getCurrentAccountHolder().getAccountId()) {
            System.out.println();
            System.out.println("Invalid Transfer Selection, please try again!");
            System.out.println();
            viewPendingRequests();
        } else if (transferService.getTransferById(transferId).getTransferStatusId() != ConsoleService.PENDING) {
            System.out.println();
            System.out.println("Transfer Already Completed!");
            System.out.println();
        } else {
            consoleService.printApproveOrRejectTransferOption();
            selectedOption = consoleService.promptForApproveOrReject();
            success = transferService.acceptOrRejectRequest(selectedOption, transferId);
        }
        if (success) {
            if (selectedOption == 1 || selectedOption == 2) {
                System.out.println();
                System.out.println("Status Updated!");
                System.out.println();
            }
            mainMenu();
        }

    }

    private void sendBucks() {
        consoleService.listContacts(accountHolderService.getContactList());
        int userIdToSend = consoleService.promptForUserIdToSendTo();
        BigDecimal transferAmount = consoleService.promptForTransferAmount();

        try {
            boolean success = transferService.sendingFunds(transferAmount, userIdToSend);
            if (!success) {
                throw new TransferNotAllowedException();
            }
        } catch (TransferNotAllowedException e) {
            BasicLogger.log(e.getMessage());
            consoleService.printErrorMessage();
        }
    }

    private void requestBucks() {
        consoleService.listContacts(accountHolderService.getContactList());
        int userToRequest = consoleService.promptForUserIdToRequestFrom();
        BigDecimal requestAmount = consoleService.promptForTransferAmount();

        try {
            boolean succes = transferService.sendingRequest(requestAmount, userToRequest);
            if (!succes) {
                throw new TransferNotAllowedException();
            }
        } catch (TransferNotAllowedException e) {
            BasicLogger.log(e.getMessage());
            consoleService.printErrorMessage();
        }
    }
}
