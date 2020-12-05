package OOP_Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import sun.jvm.hotspot.utilities.IntArray;

/* 
This class will where al "computations" will take place
*/

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
                logger.info("data set was parsed successfully");

                // Getting users path from client
                System.out.print("Please type the absolute path of config.json file: ");
                String configPath = scan.nextLine(); // assign your JSON String here
                // scan.close();

                // parsing given config.json file
                parser.parseConfigFile(configPath, users);
                logger.info("config file was parsed successfully");

                break;
            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
                logger.warn("FileNotFound error has occured!");

            }
        }

        int userIndex;
        ArrayList<Long> addedLabels;
        long randomLabel;
        ArrayList<Label> tempLabels = dataset.getLabels();

        for (Instance anInstance : dataset.getInstances()) {
            userIndex = random.nextInt(users.size());
            addedLabels = new ArrayList<>();

            for (int maxlabel = 1; maxlabel <= random.nextInt((int) dataset.getMaxLabel()) + 1;) {
                randomLabel = tempLabels.get(random.nextInt(tempLabels.size())).getId();

                if (!addedLabels.contains(randomLabel)) {
                    addedLabels.add(randomLabel);
                    maxlabel++;
                }

            }
            labelAssignments.add(
                    new LabelAssignment(anInstance.getId(), addedLabels, users.get(userIndex).getId(), new Date()));
        }
        System.out.println(labelAssignments);

        for (LabelAssignment lAssignment : labelAssignments) {
            System.out.println();
            System.out.println(String.format("Instance Id: %d", lAssignment.getInstanceId()));
            System.out.println("Assigned Label Id: " + lAssignment.getAssignedLabelId());
            System.out.println("User Id: " + lAssignment.getUserId());
            System.out.println("Date: " + lAssignment.getDate());

            long user_id = lAssignment.getUserId();
            String user_name = users.get((int) lAssignment.getUserId() - 1).getName();
            long instance_id = lAssignment.getInstanceId();
            String instance_text = dataset.getInstances().get((int) lAssignment.getInstanceId() - 1).getText();
            long class_label_id = lAssignment.getInstanceId();

            if (lAssignment.getAssignedLabelId().size() == 1) {
                String label_name = dataset.getLabels().get((int) lAssignment.getSpecificAssignedLabelId(0) - 1)
                        .getName();
                logger.info("user id:" + user_id + " " + user_name + " tagged instance id:" + instance_id
                        + " with class label " + class_label_id + ":" + label_name + " instance: " + instance_text);
            } else {
                ArrayList<String> labels = new ArrayList<>();

                for (int n = 0; n < lAssignment.getAssignedLabelId().size(); n++) {
                    labels.add(dataset.getLabels().get((int) lAssignment.getSpecificAssignedLabelId(n) - 1).getName());
                }
                logger.info("user id:" + user_id + " " + user_name + " tagged instance id:" + instance_id
                        + " with class labels " + lAssignment.getAssignedLabelId().toString() + ":" + labels.toString()
                        + " instance: " + instance_text);
            }

        }

        while (true) {
            try {
                System.out.print("Please type the absolute path of output file: ");
                String outputPath = scan.nextLine(); // assign your JSON String here
                scan.close();

                // serializing to json file
                serializer.serializeJSONFile(outputPath, dataset, labelAssignments, users);

                break;
            } catch (Exception e) {
                System.out.println("File not found! Please make sure you provided a correct path.");
                logger.warn("Output File not found!");
            }
        }
        logger.info("Program Excuted Successfully");
    }
}