package Iteration_3;

import java.util.Scanner;

public class UI {
    private Scanner sc = new Scanner(System.in);

    public String askForInput(String question) {
        System.out.println(question);
        String answer = sc.nextLine();
        return answer;
    }

    public void printOutput(String Message) {
        System.out.println(Message);
    }
}
