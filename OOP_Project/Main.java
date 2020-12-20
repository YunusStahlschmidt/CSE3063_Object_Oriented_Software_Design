package OOP_Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        Dataset dataset;
        MetricModel metrics;
        ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Parser parser = new Parser();
        JSONSerializer serializer = new JSONSerializer();
        Scanner scan = new Scanner(System.in);
        Random random = new Random();
        int currentDatasetId = 0;
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory += "\\OOP_Project";

        while (true) {
            try {

                parser.parseConfigFile(currentDirectory);
                dataset = parser.getCurrentDataset();
                parser.parseMetrics(currentDirectory);
                metrics = parser.getMetrics();
                currentDatasetId = parser.getCurrentDatasetId();
                users = parser.getUsers();

                logger.info("config file was parsed successfully");
                logger.info("dataset was parsed successfully");

                break;
            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
                logger.warn("FileNotFound error has occured!");
            }
        }

        int userIndex;
        ArrayList<Label> addedLabels;
        Label randomLabel;
        List<Label> tempLabels;
        tempLabels = dataset.getLabels();
        User currentUser;
        Date startDate, endDate;
        LabelAssignment newLabelAssignment;

        ArrayList<Instance> labeledInstaneces = new ArrayList<>();
        ArrayList<Instance> newInstances = new ArrayList<>();
        // metrices
        UserMetric userMetric;
        InstanceMetric instanceMetric;
        DatasetMetric datasetMetric = dataset.getDatasetMetric();
        // algorithm
        /*
         * 
         * while newInstanes.length > 0 ; random num from 1 to 100 ; if num < 10 ;label
         * from labeledInstances; else; label from newInstace and add instanece to
         * labeledInstances; update metrics; serialize output metric
         * 
         */
        for (Instance anInstance : dataset.getInstances()) {
            instanceMetric = anInstance.getInstanceMetric();

            for (int i = 0; i < 10; i++) {
                userIndex = random.nextInt(users.size());
                currentUser = users.get(userIndex);
                addedLabels = new ArrayList<>();
                userMetric = currentUser.getUserMetric();

                startDate = new Date();
                for (int maxlabel = 1; maxlabel <= random.nextInt((int) dataset.getMaxLabel()) + 1;) {
                    randomLabel = dataset.getLabels().get(random.nextInt(dataset.getLabels().size()));

                    if (!addedLabels.contains(randomLabel)) {
                        addedLabels.add(randomLabel);
                        maxlabel++;

                        // updating Instance Metrices
                        instanceMetric.incrementTotalNumberOfAssignedLabels();
                        instanceMetric.addUniqueUser(currentUser);
                        instanceMetric.addUniqueLabel(randomLabel);
                    }
                }
                newLabelAssignment = new LabelAssignment(anInstance.getId(), addedLabels, currentUser.getId(),
                        new Date());
                labelAssignments.add(newLabelAssignment);
                endDate = new Date();

                // updating User Metrices
                userMetric.incrementDatasetCompleteness(dataset);
                userMetric.setNumberOfLabeledInstances(); // corresponding method should be handled
                // userMetric.setUniqueLabeledInstances(
                // String.format("%d : %d", dataset.getDatasetId(), anInstance.getId()));
                userMetric.setAverageTimeSpent(((endDate.getTime() - startDate.getTime()) / (double) 1000)); // corresponding
                // method
                // should
                // be
                // handled

                // updating Instance Metrics
                instanceMetric.setLabelAssignments(newLabelAssignment);
                instanceMetric.setNumberOfUniqueAssignedLabels(); // parameters and method should be handled
                instanceMetric.setNumberOfUniqueUsers();
                /* May change the order of method calls */
                instanceMetric.setMostFrequentLabel();
                instanceMetric.setPercentageOfMostFrequentLabel();
                instanceMetric.setClassLabelsAndPercentages();

                // ------Hopefully Instance Metrics set-----
            }
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

        while (true) {
            try {
                String outputPath = currentDirectory + "\\output" + String.valueOf(currentDatasetId) + ".json";
                serializer.serializeOutputFile(outputPath, dataset, labelAssignments, users);

                String filePath = currentDirectory + "\\metrics" + String.valueOf(currentDatasetId) + ".json";
                serializer.serializeMetricFile(metrics, filePath);

                break;
            } catch (Exception e) {
                System.out.println("File not found! Please make sure you provided a correct path.");
                logger.warn("Output File not found!");
            }
        }
        logger.info("Program Excuted Successfully");
    }
}