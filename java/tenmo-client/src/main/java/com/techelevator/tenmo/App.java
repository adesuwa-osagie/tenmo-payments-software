package com.techelevator.tenmo;

import com.techelevator.tenmo.models.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountsService accountsService;
    private UserService userService;
    private TransfersService transfersService;


    public static void main(String[] args) {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL),
                new AccountsService(API_BASE_URL), new UserService(API_BASE_URL), new TransfersService(API_BASE_URL));
        app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService,
               AccountsService accountsService, UserService userService, TransfersService transfersService) {
        this.console = console;
        this.authenticationService = authenticationService;
        this.accountsService = accountsService;
        this.userService = userService;
        this.transfersService = transfersService;
    }

    public void run() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                try {
                    viewCurrentBalance(currentUser.getUser().getId(), currentUser.getToken());

                } catch (AccountsServiceException e) {
                    e.printStackTrace();
                    System.out.println("Something in the view balance is not working");
                }
            } else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                //viewTransferHistory(Long currentUserId, String token, String currentUserName)
                viewTransferHistory(currentUser.getUser().getId(),currentUser.getToken(), currentUser.getUser().getUsername());

            } else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
                System.out.println("Feature coming soon");
            } else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {

                try {
                    sendBucks(currentUser.getUser().getId(), currentUser.getToken());
                } catch (AccountsServiceException e){
                    e.printStackTrace();
                    System.out.println("Something in send balance is not working");
                }

            } else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
                System.out.println("Feature coming soon");
            } else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void viewCurrentBalance(Long id, String token) throws AccountsServiceException {
        BigDecimal currentUserBalance = accountsService.getBalance(id, token);
        System.out.println("Your current account balance is: $" + currentUserBalance);
    }

    private void viewTransferHistory(Long currentUserId, String token, String currentUserName) {
        //currentUserAccountNumber pass into (GET request) gets currentUserTransfers for both sent and received transfers
        //Accounts test = accountsService.findCurrentUserAccount(currentUser.getUser().getId(), currentUser.getToken());

        //Get the user's accountId
        Accounts currentUserAccount = accountsService.findCurrentUserAccount(currentUserId, token);
        Long accountId = currentUserAccount.getId();

        printHeaderTransfer();
        //printListOfTransfers(String token, Long currentUserAccountId, String currentUserName)
        printListOfTransfers(token, accountId, currentUserName);

        try {
            long transferId = Long.parseLong(console.getUserInput("Please enter transfer ID to view details (0 to cancel)").trim());

            boolean found = false;
            if (transferId != 0) {
                Transfers[] transfers = transfersService.listAllTransfersForUser(accountId, token);
                for (Transfers theTransfer : transfers) {
                    if (theTransfer.getTransferId() == transferId) {
                        System.out.println(theTransfer);
                        found = true;
                    }
                }
                if (!found) {
                    console.printError("Invalid transfer id!\n");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid format.");
        }
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks(Long currentUserId, String token) throws AccountsServiceException {

            String transferType = "Send";
            printHeader();
            printListOfUsers(token, currentUser.getUser().getId());

        try {
            long receiverUserId =
                    Long.parseLong(console.getUserInput("Enter ID of user you are requesting from (0 to cancel)").trim());
            boolean found = false;
            if (receiverUserId != 0) {
                User[] users = userService.getAllUsers(token);
                for (User theUser : users) {
                    if (theUser.getId() == receiverUserId) {

                        BigDecimal amount =
                                BigDecimal.valueOf(Double.parseDouble(console.getUserInput("Enter amount to transfer (No $ sign)").trim()));

                        if (amount.remainder(new BigDecimal(0.01)).compareTo(BigDecimal.ZERO) == 0
                        || amount.remainder(new BigDecimal(1.00)).compareTo(BigDecimal.ZERO) == 0 ) {


                            //Use the GET request(getBalance) to get current user's balance
                            BigDecimal currentUserBalance = accountsService.getBalance(currentUserId, token);

                            //Check if the the amount is LESS THAN 0 OR GREATER THAN current user's balance
                            //if (value.compareTo(BigDecimal.ZERO) > 0)
                            if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(currentUserBalance) > 0) {
                                //if so,
                                //prompt: "Insufficient funds to proceed"
                                //break out to main menu

                                //POST for REJECTED
                                transfersService.addTransfer
                                        ("Send", "Rejected", currentUser.getUser().getId(), receiverUserId, amount, currentUser.getToken());
                                System.out.println("Your transfer has been 'REJECTED'");

                                System.out.println("Insufficient funds to proceed");
                                mainMenu();
                            }

                            //Otherwise, the app will continue to the following:

                            //current user's balance - amount = currentUserUpdatedBalance
//                        bg3 = bg1.subtract(bg2);
                            BigDecimal currentUserUpdatedBalance = currentUserBalance.subtract(amount);

                            //Accounts updateUserBalance(BigDecimal updatedBalance, Long currentUserId, String AUTH_TOKEN)
                            //Use the PUT request(updateUserBalance) to update the current user's balance over on the database's side
                            accountsService.updateUserBalance(currentUserUpdatedBalance, currentUserId, token);

                            //Ust the GET request(getBalance) to get receiver user's balance
                            BigDecimal receiverUserBalance = accountsService.getBalance(receiverUserId, token);
                            //receiverUserId's balance + amount = receiverUserUpdatedBalance
                            BigDecimal receiverUserUpdatedBalance = receiverUserBalance.add(amount);

                            //Use the PUT request(updateUserBalance) to update the receiver user's over on the balance
                            accountsService.updateUserBalance(receiverUserUpdatedBalance, receiverUserId, token);

                            transfersService.addTransfer
                                    ("Send", "Approved", currentUser.getUser().getId(), receiverUserId, amount, currentUser.getToken());
                            System.out.println("Your transfer has been 'APPROVED'");
                            found = true;
                        } else {
                            System.out.println("Invalid format. Try again");
                            mainMenu();
                        }
                    }
                }
                if (!found) {
                    console.printError("Invalid user id!\n");

                }

            }
        } catch (NumberFormatException e){
            System.out.println("Invalid number format. Try again.");
        }
        }

    private void requestBucks() {
        // TODO Auto-generated method stub
        //String transferType = "Request";

    }

    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while (!isAuthenticated()) {
            String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                // the only other option on the login menu is to exit
                exitProgram();
            }
        }
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch (AuthenticationServiceException e) {
//              System.out.println("REGISTRATION ERROR: "); //removed e.getMessage()
                System.out.println("\nREGISTRATION ERROR: Please attempt to register again.\n");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null) //will keep looping until user is logged in
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
            } catch (AuthenticationServiceException e) {
//              System.out.println("LOGIN ERROR: " + e.getMessage());
                System.out.println("\nLOGIN ERROR: Please attempt to login again.\n");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }

    private void printHeader() {
        System.out.println("-------------------------------------------");
        System.out.println("User");
        System.out.printf("%-20s %-10s\n", "Id", "Name");
        System.out.println("-------------------------------------------");
    }

    private void printHeaderTransfer(){
        System.out.println("-----------------------------------------");
        System.out.println("Transfers");
        System.out.printf("%-10s %-15s %-10s\n", "Id", "From/To", "Amount");
        System.out.println("-----------------------------------------");
    }

    private void printListOfUsers(String token, Long currentUserId) {
        User[] users = userService.getAllUsers(token);
        for (User theUser : users) {
            if (!theUser.getId().equals(currentUserId)) {
                System.out.printf("%-20s %-10s\n", theUser.getId(), theUser.getUsername());
            }
        }
    }

    private void printListOfTransfers(String token, Long currentUserAccountId, String currentUserName) {
        Transfers[] transfers = transfersService.listAllTransfersForUser(currentUserAccountId, token);

        for (Transfers theTransfer : transfers) {
            if (!theTransfer.getUserFrom().equals(currentUserName)){
                System.out.printf("%-10s %-15s $%-10s\n", theTransfer.getTransferId(), "From: " + theTransfer.getUserFrom(), theTransfer.getAmount());
            } else {
                System.out.printf("%-10s %-15s $%-10s\n", theTransfer.getTransferId(), "To:   " + theTransfer.getUserTo(), theTransfer.getAmount());
            }
        }
    }

    private List<Long> sendTEBucksDisplay(String token) {
        printHeader();
        printListOfUsers(token, currentUser.getUser().getId());
        User[] users = userService.getAllUsers(token);

        List<Long> userListIds = new ArrayList<>();
        for (User theUser : users) {
            if (!theUser.getId().equals(currentUser.getUser().getId())) {
                userListIds.add(theUser.getId());
            }
        }

        return userListIds;


    }

}
