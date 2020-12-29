package OOP_Project;
import java.util.HashMap;
import java.util.Scanner;

public class Login {

    public static boolean randomInitialize() { //returns true if random should be initialized else false
        
        //key is username value is password
        HashMap<String, String> validUsers = new HashMap<String, String>();
        validUsers.put("ahmet", "1234");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("What is your username: ");
            String username = scanner.nextLine();

            System.out.print("What is your password: ");
            String password = scanner.nextLine();
            
            if (username.equals(""))
            {
                if (password.equals(""))
                {
                    scanner.close();
                    return true;
                }

            }

            else if (validUsers.containsKey(username))
            {
                if (password.equals(validUsers.get(username)))
                {
                    System.out.println("Welcome mr/mrs " + username);
                    scanner.close();
                    return false;
                }

                else
                    System.out.println("Invalid username or password. Please try again.");
            }

            else
                System.out.println("Invalid username or password. Please try again.");

        }   
    }  
}
