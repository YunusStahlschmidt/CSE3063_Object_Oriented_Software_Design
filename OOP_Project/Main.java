package OOP_Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/* 
This class will where al "computations" will take place
*/

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Required Objects
        Dataset dataset;
        ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Parser parser = new Parser();
        JSONSerializer serializer = new JSONSerializer();
        Scanner scan = new Scanner(System.in);
        Random random = new Random();
        int currentDatasetId = 0;

        while (true) {
            try {
                // Getting users path from client
                System.out.print("Please type the absolute path of config.json file: ");
                String configPath = scan.nextLine(); // assign your JSON String here

                // parsing given config.json file
                parser.parseConfigFile(configPath);
                dataset = parser.getCurrentDataset();
                currentDatasetId = parser.getCurrentDatasetId();
                users = parser.getUsers();
                //System.out.println(currentDatasetId);
                //System.out.println(users);
                logger.info("config file was parsed successfully");
                logger.info("dataset was parsed successfully");

                break;
            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
                logger.warn("FileNotFound error has occured!");
            }
        }
        scan.close();

        int userIndex;
        ArrayList<Label> addedLabels;
        Label randomLabel;
        List<Label> tempLabels;
        tempLabels = dataset.getLabels();
        User currentUser;
        Date startDate, endDate;
        LabelAssignment newLabelAssignment;

        // metrices
        UserMetric userMetric;
        InstanceMetric instanceMetric;
        DatasetMetric datasetMetric = dataset.getDatasetMetric();

        for (Instance anInstance : dataset.getInstances()) {
            for (int i = 0; i < 10; i++) {
                userIndex = random.nextInt(users.size());
                currentUser = users.get(userIndex);
                userMetric = currentUser.getUserMetric();
                instanceMetric = anInstance.getInstanceMetric();
                addedLabels = new ArrayList<>();
                
                startDate = new Date();
                for (int maxlabel = 1; maxlabel <= random.nextInt((int) dataset.getMaxLabel()) + 1;) {
                    randomLabel = dataset.getLabels().get(random.nextInt(dataset.getLabels().size()));

                    if (!addedLabels.contains(randomLabel)) {
                        addedLabels.add(randomLabel);
                        maxlabel++;

                        // updating Instance Metric
                        instanceMetric.findTotalNumberOfAssignedLabels(); //update while model class object is created
                        instanceMetric.addUniqueUser(currentUser);
                        instanceMetric.addUniqueLabel(randomLabel);
                        
                        // updating Datset Metric
                        datasetMetric.addInstance(randomLabel, anInstance);
                        
                    }
                }
                newLabelAssignment = new LabelAssignment(anInstance.getId(), addedLabels, currentUser.getId(), new Date());
                labelAssignments.add(newLabelAssignment);
                endDate = new Date();

                // updating User Metrices
                userMetric.incrementDatasetCompleteness(dataset);
                userMetric.setNumberOfLabeledInstances(); // corresponding method should be handled
                userMetric.addUniqueLabeledInstances(anInstance);
                userMetric.setAverageTimeSpent(((endDate.getTime() - startDate.getTime()) / (double)1000)); // corresponding method should be handled

                // updating Instance Metrics
                instanceMetric.setLabelAssignments(newLabelAssignment);
                instanceMetric.setNumberOfUniqueAssignedLabels(); // parameters and method should be handled
                instanceMetric.setNumberOfUniqueUsers();
                /* May change the order of method calls */ 
                instanceMetric.setMostFrequentLabel();
                instanceMetric.setPercentageOfMostFrequentLabel();
                instanceMetric.setClassLabelsAndPercentages();      
                instanceMetric.setEntropy();
                //------Hopefully Instance Metrics done-----

                
            }
            // updating Dataset Metrices
            datasetMetric.calculateDatasetCompleteness();
            datasetMetric.calculateClassDistribution();
            // metric - 3 is done inside the loop
            datasetMetric.calculateUserCompleteness();
            //metric 4 should be called (maybe outside of the loop) 
            datasetMetric.calculateAssignedUsersAndConcistencyPercentage();
       
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
            String instance_text = dataset.getInstances().get((int) lAssignment.getInstanceId() - 1).getInstance();
            long class_label_id = lAssignment.getInstanceId();

            if (lAssignment.getAssignedLabelId().size() == 1) {
                String label_name = dataset.getLabels().get((int) lAssignment.getSpecificAssignedLabelId(0) - 1)
                        .getLabelText();
                logger.info("user id:" + user_id + " " + user_name + " tagged instance id:" + instance_id
                        + " with class label " + class_label_id + ":" + label_name + " instance: " + instance_text);
            } else {
                ArrayList<String> labels = new ArrayList<>();

                for (int n = 0; n < lAssignment.getAssignedLabelId().size(); n++) {
                    labels.add(dataset.getLabels().get((int) lAssignment.getSpecificAssignedLabelId(n) - 1).getLabelText());
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