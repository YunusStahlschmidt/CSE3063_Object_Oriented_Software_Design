package OOP_Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

// import sun.jvm.hotspot.utilities.IntArray;


/* 
This class will where al "computations" will take place
*/

public class Main {
    public static void main(String[] args) {

        // Required Objects
        Dataset dataset = new Dataset();
        ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Parser parser = new Parser();
        JSONSerializer serializer = new JSONSerializer();
        Scanner scan = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            try {
                // Getting input path from client
                System.out.print("Please type the absolute path of json file: ");
                String jsonPath = scan.nextLine(); // assign your JSON String here

                // parsing given json file
                parser.parseJSONFile(jsonPath, dataset);

                // Getting users path from client
                System.out.print("Please type the absolute path of config.json file: ");
                String configPath = scan.nextLine(); // assign your JSON String here
                // scan.close();

                // parsing given config.json file
                parser.parseConfigFile(configPath, users);
                break;
            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
            }
        }

        System.out.println(users);
        System.out.println(dataset.getInstances());

        int userIndex;
        ArrayList<Long> addedLabels;
        long randomLabel;
        ArrayList<Label> tempLabels = dataset.getLabels();

        for (Instance anInstance: dataset.getInstances()) {
            userIndex = random.nextInt(users.size());
            addedLabels = new ArrayList<>();

            for (int maxlabel = 1; maxlabel <= random.nextInt((int)dataset.getMaxLabel())+1;) {
                randomLabel = tempLabels.get( random.nextInt(tempLabels.size()) ).getId();

                if (!addedLabels.contains(randomLabel)) {
                    addedLabels.add(randomLabel);
                    maxlabel++;
                }
                
            }
            labelAssignments.add(new LabelAssignment(anInstance.getId(), addedLabels, users.get(userIndex).getId(), new Date()));
        }
        System.out.println(labelAssignments);

        for (LabelAssignment lAssignment : labelAssignments) { 
            System.out.println();
            System.out.println(String.format("Instance Id: %d", lAssignment.getInstanceId()));
            System.out.println("Assigned Label Id: " + lAssignment.getAssignedLabelId());
            System.out.println("User Id: " + lAssignment.getUserId());
            System.out.println("Date: " + lAssignment.getDate());
        }  
        
        while (true) {
            try {
                System.out.print("Please type the absolute path of config.json file: ");
                String outputPath = scan.nextLine(); // assign your JSON String here
                scan.close();

                // serializing to json file
                serializer.serializeJSONFile(outputPath, dataset, labelAssignments, users);
                break;
            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
            }
        }
    }
}