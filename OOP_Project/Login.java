package OOP_Project;

import java.util.ArrayList;

public class Login {

    public User logIn(ArrayList<User> users, UI ui) { // returns true if random should be initialized else
        // false

        // key is username value is password
        // Scanner scanner = new Scanner(System.in);
        // UI ui = new UI();

        while (true) {
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
                for (User user : users) {
                    if (username.equals(user.getName())) {
                        if (password.equals(user.getPassword())) {
                            // System.out.println("Welcome mr/mrs " + username);
                            String message1 = String.format("Welcome mr/mrs 1%s ", username);
                            ui.printOutput(message1);

                            return user;
                        }
                    }
                }
                // System.out.println("Invalid username or password. Please try again.");
                ui.printOutput("Invalid username or password. Please try again.");
            }
        }
    }
}