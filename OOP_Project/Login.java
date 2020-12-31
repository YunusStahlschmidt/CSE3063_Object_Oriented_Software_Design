package OOP_Project;
import java.util.Scanner;
import java.util.ArrayList;

public class Login {

    public static User randomInitialize(ArrayList<User> users) { //returns true if random should be initialized else false
        
        //key is username value is password
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("What is your username: ");
            String username = scanner.nextLine().trim();

            System.out.print("What is your password: ");
            String password = scanner.nextLine().trim();

            if (username.equals(""))
            {
                if (password.equals(""))
                {   
                    return null;
                }

                else
                {
                    System.out.println("Username can't be empty");
                }

            }

            else
            {
                for (User user: users)
                {
    
                    if (username.equals(user.getName()))
                    {
                        if (password.equals(user.getPassword()))
                        {
                            System.out.println("Welcome mr/mrs " + username);
                            
                            return user;
                        }
                    }

    
                }
                
                System.out.println("Invalid username or password. Please try again.");
            
            }   
        }  
    }
}