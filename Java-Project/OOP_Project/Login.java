package OOP_Project;

import java.util.ArrayList;

public class Login {

    public User logIn(ArrayList<User> allUsers, ArrayList<User> AssignedUsers, UI ui) { // returns true if random should be initialized else
        // false

        // key is username value is password
        // Scanner scanner = new Scanner(System.in);
        // UI ui = new UI();

        while (true) {
            boolean userIsAssigned = true;
            // System.out.print("What is your username: ");
            // String username = scanner.nextLine().trim();
            String username = ui.askForInput("What is your username: ");

            // System.out.print("What is your password: ");
            // String password = scanner.nextLine().trim();
            String password = ui.askForInput("What is your password: ");

            if (username.equals("")) {
                if (password.equals("")) {
                    return null;
                }

                else {
                    // System.out.println("Username can't be empty");
                    ui.printOutput("Username can't be empty");
                }
            }

            else {
                for (User user : allUsers) {
                    if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                        String message1 = String.format("Welcome mr/mrs %s ", username);
                        ui.printOutput(message1);

                        if (!AssignedUsers.contains(user)) {
                            ui.printOutput("\nYou are not assigned to this dataset. Please login with assigned user.\n");
                            userIsAssigned = false;
                            break;
                        }

                        return user;
                    }
                }
                if (userIsAssigned)
                    ui.printOutput("\nInvalid username or password. Please try again.\n");
            }
        }
    }
}