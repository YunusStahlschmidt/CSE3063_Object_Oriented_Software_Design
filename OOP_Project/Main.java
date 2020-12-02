package CSE3063F20P1_GRP27.OOP_Project;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Iterator; 
import java.util.Map; 
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.*;
import org.json.simple.parser.*;

// import sun.jvm.hotspot.utilities.IntArray;


/* 
This class will where al "computations" will take place
*/

public class Main {
    public static void main(String[] args) {
        Dataset dataset = new Dataset();
        ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Parser parser = new Parser(null, dataset);

        parser.parseJSONFile();

        Random rand = new Random();
        int numberOfUsers = rand.nextInt(7);
        String username;
        for (int i = 1; i < numberOfUsers; i++) {
            username = String.format("RandomLabelingMechanism%d", i);
            users.add(new User(i, username, "RandomBot"));
        }

        int userIndex;
        for (int i = 0; i < dataset.getInstances().size(); i++) {
            for (long maxlabel = 0; maxlabel < dataset.getMaxLabel(); maxlabel++) {
                userIndex = rand.nextInt(users.size());
                ArrayList<Label> tempLabels = dataset.getLabels();
                labelAssignments.add(new LabelAssignment(dataset.getInstances().get(i).getId(),
                                                            getRandomElement(tempLabels, dataset.getMaxLabel()), users.get(userIndex).getId(),
                                                            new Date()));
            }
        }
        System.out.println(labelAssignments);

        for (LabelAssignment lAssignment : labelAssignments) { 
            System.out.println();
            System.out.println(String.format("Instance Id: %d", lAssignment.getInstanceId()));
            System.out.println(String.format("Assigned Label Id: %d", lAssignment.getAssignedLabelId()));
            System.out.println(String.format("User Id: %d", lAssignment.getUserId()));
            System.out.println("Date: " + lAssignment.getDate());
        }    
    }

    public static ArrayList<Long> getRandomElement(ArrayList<Label> labels, long totalItems) 
    { 
        Random rand = new Random(); 

        // create a temporary list for storing 
        // selected element 

        ArrayList<Long> newList = new ArrayList<>(); 
        for (long i = 0; i < totalItems; i++) { 

            // take a raundom index between 0 to size  
            // of given List 
            int randomIndex = rand.nextInt(labels.size()); 

            // add element in temporary list 
            newList.add(labels.get(randomIndex).getId()); 

            // Remove selected element from orginal list 
            labels.remove(randomIndex); 
        } 
        return newList; 
    } 
}