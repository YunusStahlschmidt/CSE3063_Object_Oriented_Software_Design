package OOP_Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import OOP_Project.MetricsJSONModels.MetricModel;

/* 
This class will where al "computations" will take place
*/

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Required Objects
        HashMap<Integer, Dataset> datasetHashMap;
        Dataset dataset;
        MetricModel metrics;
        ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Parser parser = new Parser();
        JSONSerializer serializer = new JSONSerializer();
        Random random = new Random();
        int currentDatasetId = 0;
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory += "\\OOP_Project";
        HashMap<Integer, ArrayList<LabelAssignment>> previousLabelAssignments = new HashMap<>();

        while (true) {
            try {

                parser.parseConfigFile(currentDirectory);
                datasetHashMap = parser.getDatasetHashMap();
                previousLabelAssignments = parser.getPreviousLabelAssignments();
                parser.parseMetrics(currentDirectory);
                metrics = parser.getMetrics();
                currentDatasetId = parser.getCurrentDatasetId();
                users = parser.getUsers();
                break;

            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
                logger.warn("FileNotFound error has occured!");
                System.exit(0);
            }
        }
        for (Dataset myDataset : datasetHashMap.values()) {
            for (User user : myDataset.getAssignedUsers()) {
                user.getUserMetric().incrementDatasetCompleteness(myDataset);
            }
        }

        dataset = datasetHashMap.get(currentDatasetId);

        int userIndex, numberOfAssignmentsPerInstance;
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
        Integer randomInstanceProbability, recurrentLabeledInstanceIdx;
        ArrayList<Instance> recurrentLabeledInstances;

        for (Instance anInstance : dataset.getInstances()) {

            numberOfAssignmentsPerInstance = random.nextInt(users.size()) + 1;
            for (int i = 0; i < numberOfAssignmentsPerInstance; i++) {
                userIndex = random.nextInt(users.size());
                currentUser = users.get(userIndex);
                // picking random Instance based on probability
                randomInstanceProbability = random.nextInt(100) + 1;

                if (randomInstanceProbability <= currentUser.getUserMetric().getConsistencyPercentage() * 100) {
                    recurrentLabeledInstances = new ArrayList<>();
                    for (Instance instanceToArray : currentUser.getUserMetric().getUniqueLabeledInstances()) {
                        recurrentLabeledInstances.add(instanceToArray);
                    }
                    while (true) {
                        recurrentLabeledInstanceIdx = random.nextInt(recurrentLabeledInstances.size());
                        if (anInstance == recurrentLabeledInstances.get(recurrentLabeledInstanceIdx)) {
                            continue;
                        }
                        anInstance = recurrentLabeledInstances.get(recurrentLabeledInstanceIdx);
                        break;
                    }
                }

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
                        instanceMetric.addUniqueLabel(randomLabel);

                        // updating Dataset Metric - 3
                        datasetMetric.addInstanceForLabel(randomLabel, anInstance);
                    }
                }
                newLabelAssignment = new LabelAssignment(anInstance.getId(), addedLabels, currentUser.getId(),
                        new Date());
                labelAssignments.add(newLabelAssignment);
                endDate = new Date();

                // updating User Metrices
                // User metric 1: Number of datasets assigned already done in parser class
                userMetric.addUniqueLabeledInstances(anInstance); // User Metric -4
                // List of all datasets with their completeness percentage setting with
                // incrementDatasetCompleteness
                userMetric.incrementDatasetCompleteness(dataset);// User Metric - 2
                userMetric.incrementNumberOfLabeledInstances();// User Metric - 3
                userMetric.setConsistencyPercentage(); // User Metric - 5
                // STD DEV called inside the setAverageTimeSpent func Metric - 6 - 7
                userMetric.setAverageTimeSpent(((endDate.getTime() - startDate.getTime()) / (double) 1000));

                // updating Instance Metrics
                /* we are calling addUniqueLabel inside the for loop */
                instanceMetric.addUniqueUser(currentUser);
                instanceMetric.addLabelAssignments(newLabelAssignment);
                instanceMetric.setTotalNumberOfAssignedLabels(); // update while model class object is created
                instanceMetric.setNumberOfUniqueAssignedLabels(); // parameters and method should be handled
                // instanceMetric.setNumberOfUniqueUsers(); // calling inside addUniqueUser

                /* May change the order of method calls */
                // instanceMetric.setMostFrequentLabel();
                // instanceMetric.setPercentageOfMostFrequentLabel();
                instanceMetric.updateClassLabelsAndPercentages();
                instanceMetric.setEntropy();
                // ------Hopefully Instance Metrics done-----

            }
            // updating Dataset Metrices
            datasetMetric.calculateDatasetCompleteness(dataset.getInstances().size());// Dataset Metric - 1
            datasetMetric.calculateClassDistribution(); // Dataset Metric - 2
            // dataset metric - 3 is done inside the loop
            datasetMetric.calculateUserCompleteness(dataset, dataset.getAssignedUsers()); // Dataset Metric - 5
            // dataset metric 4 called while parsing
            datasetMetric.calculateAssignedUsersAndConcistencyPercentage(dataset.getAssignedUsers()); // Dataset Metric
                                                                                                      // - 6

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
                    labels.add(dataset.getLabels().get((int) lAssignment.getSpecificAssignedLabelId(n) - 1)
                            .getLabelText());
                }
                logger.info("user id:" + user_id + " " + user_name + " tagged instance id:" + instance_id
                        + " with class labels " + lAssignment.getAssignedLabelId().toString() + ":" + labels.toString()
                        + " instance: " + instance_text);
            }
        }

        try {
            String outputPath = currentDirectory + "\\output" + String.valueOf(currentDatasetId) + ".json";
            serializer.serializeOutputFile(outputPath, dataset, labelAssignments, users);

            String filePath = currentDirectory + "\\metrics" + String.valueOf(currentDatasetId) + ".json";
            serializer.serializeMetricFile(metrics, filePath);

        } catch (Exception e) {
            System.out.println("File not found! Please make sure you provided a correct path.");
            logger.warn("Output File path not found!");
        }
        logger.info("Program Excuted Successfully");
    }
}
