package OOP_Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import OOP_Project.MetricsJSONModels.DatasetModel;
import OOP_Project.MetricsJSONModels.InstanceModel;
import OOP_Project.MetricsJSONModels.MetricModel;
import OOP_Project.MetricsJSONModels.UserModel;

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
                metrics = parser.getMetrics();// if null we have to initialize for all models first

                currentDatasetId = parser.getCurrentDatasetId();
                users = parser.getUsers();
                break;

            } catch (Exception e) {
                System.out.println("FileNotFound error has been occured! Please check your file paths.");
                logger.warn("FileNotFound error has occured!");
                System.exit(0);
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

        ArrayList<DatasetModel> datasetModelList = new ArrayList<DatasetModel>();
        ArrayList<InstanceModel> instanceModelList = new ArrayList<InstanceModel>();
        ArrayList<UserModel> userModelList = new ArrayList<UserModel>();

        if (metrics != null) {
            datasetModelList = (ArrayList<DatasetModel>) metrics.getDatasetList();
            instanceModelList = (ArrayList<InstanceModel>) metrics.getInstanceList();
            userModelList = (ArrayList<UserModel>) metrics.getUsersList();

            // temp lists
            // ArrayList<DatasetModel> tempDatasetModelList = (ArrayList<DatasetModel>) metrics.getDatasetList();
            // ArrayList<InstanceModel> tempInstanceModelList = (ArrayList<InstanceModel>) metrics.getInstanceList();
            // ArrayList<UserModel> tempUserModelList = (ArrayList<UserModel>) metrics.getUsersList();

            // Traversing inside the modelLists to set corresponding dataset, instance and
            // user objects.

            // HashSet<Instance> unlabeledInstances = (HashSet<Instance>)
            // dataset.getInstances();
            ArrayList<LabelAssignment> updatedLabelAssignments = new ArrayList<>();
            LabelAssignment newestLabelAssignment;
            ArrayList<Label> newAddedLabels;
            Boolean checkpoint = false;

            for (LabelAssignment prevLabelAssignment : previousLabelAssignments.get(currentDatasetId)) {
                for (Instance currInstance : dataset.getInstances()) {
                    for (User currUser : dataset.getAssignedUsers()) {
                        if (prevLabelAssignment.getInstanceId().equals(currInstance.getId())
                                && prevLabelAssignment.getUserId().equals(currUser.getId())) {
                            // dataset.getDatasetMetric().addUniqueLabeledInstances(currInstance);
                            // tbd dataset metric updates
                            newAddedLabels = new ArrayList<>();
                            for (Integer labelId : prevLabelAssignment.getAssignedLabelId()) {
                                for (Label currentLabel : dataset.getLabels()) {
                                    if (currentLabel.getId().equals(labelId)) {
                                        currInstance.getInstanceMetric().addUniqueLabel(currentLabel);
                                        dataset.getDatasetMetric().addInstanceForLabel(currentLabel, currInstance);
                                        newAddedLabels.add(currentLabel);
                                    }
                                }
                            }
                            newestLabelAssignment = new LabelAssignment(currInstance.getId(), newAddedLabels, currUser.getId(), prevLabelAssignment.getDate());
                            updatedLabelAssignments.add(newestLabelAssignment);

                            currInstance.getInstanceMetric().callAllNecessaryMethods(currUser, newestLabelAssignment);
                            currUser.getUserMetric().callAllNecessaryMethods(currInstance, dataset, newestLabelAssignment, null, null);

                            /* should be added */
                            // updating Instance Metric

                            // updating Dataset Metric - 3
                            checkpoint = true;
                        }
                    }
                    if (checkpoint) {
                        dataset.getDatasetMetric().callAllNecessaryMethods(currInstance, dataset);
                        checkpoint = false;
                    }
                }
            }
            for (User user : users) {
                for (UserModel userModel : userModelList) {
                    if (userModel.getUserId().equals(user.getId())) {
                        user.getUserMetric().setUserModel(userModel);
                        // tempUserModelList.remove(userModel);
                        break;
                    }
                }
            }

            for (Dataset myDataset : datasetHashMap.values()) {
                for (DatasetModel datasetModel : datasetModelList) {
                    if (datasetModel.getDatasetId().equals(myDataset.getDatasetId())) {
                        myDataset.getDatasetMetric().setDatasetModel(datasetModel);
                        // datasetModelList.remove(datasetModel);
                        break;
                    }
                }
                
                for (Instance instanceForId : myDataset.getInstances()) {
                    for (InstanceModel instanceModel : instanceModelList) {
                        if (instanceModel.getInstanceId().equals(instanceForId.getId()) && (instanceModel.getDatasetId().equals(myDataset.getDatasetId())) ){
                            instanceForId.getInstanceMetric().setInstanceModel(instanceModel);
                            // tempInstanceModelList.remove(instanceModel);
                            break;
                        }
                    }
                }
            }
            // for (Dataset myDataset : datasetHashMap.values()) {
            //     for (DatasetModel datasetModel : datasetModelList) {
            //         if (datasetModel.getDatasetId().equals(myDataset.getDatasetId())) {
            //             myDataset.getDatasetMetric().setDatasetModel(datasetModel);
            //             // datasetModelList.remove(datasetModel);
            //             break;
            //         }
            //     }

            //     for (User user : myDataset.getAssignedUsers()) {
            //         for (UserModel userModel : userModelList) {
            //             if (userModel.getUserId().equals(user.getId())) {
            //                 user.getUserMetric().setUserModel(userModel);
            //                 // tempUserModelList.remove(userModel);
            //                 break;
            //             }
            //         }
            //     }
            //     for (Instance instanceForId : myDataset.getInstances()) {
            //         for (InstanceModel instanceModel : instanceModelList) {
            //             if (instanceModel.getInstanceId().equals(instanceForId.getId())) {
            //                 instanceForId.getInstanceMetric().setInstanceModel(instanceModel);
            //                 // tempInstanceModelList.remove(instanceModel);
            //                 break;
            //             }
            //         }
            //     }
            // }
        } else {
            // Metric Model ListList<ListOfUsersAssignedAndTheirConsistencyPercentage>s
            metrics = new MetricModel();
            for (Dataset myDataset : datasetHashMap.values()) {
                myDataset.setDatasetIdToModel();
                myDataset.getDatasetMetric().setInitialDatasetModel();
                datasetModelList.add(myDataset.getDatasetMetric().getDatasetModel());
                // for (User user : myDataset.getAssignedUsers()) {
                // user.getUserMetric().incrementNumberOfDatasetsAssigned();
                // }

                for (User user : myDataset.getAssignedUsers()) {
                    // user.getUserMetric().set
                    user.getUserMetric().incrementDatasetCompleteness(myDataset);
                    user.setUserIdToModel();
                }
                for (Instance instanceForId : myDataset.getInstances()) {
                    instanceForId.setDatasetIdToInstanceModel(myDataset.getDatasetId());
                    instanceForId.setInstanceIdToModel();
                    instanceForId.getInstanceMetric().setInitialInstanceModel();
                    instanceModelList.add(instanceForId.getInstanceMetric().getInstanceModel());
                }
            }
            for (User user : users) {
                userModelList.add(user.getUserMetric().getUserModel());// tbd should only be called once
            }
        }

        // metrices
        UserMetric userMetric;
        InstanceMetric instanceMetric;
        DatasetMetric datasetMetric = dataset.getDatasetMetric();
        Integer randomInstanceProbability, recurrentLabeledInstanceIdx;
        ArrayList<Instance> recurrentLabeledInstances;

        ArrayList<User> assignedUsers = dataset.getAssignedUsers();

        // metric model set datase model list
        // datasetModelList.add(datasetMetric.getDatasetModel());
        for (Instance anInstance : dataset.getInstances()) {

            numberOfAssignmentsPerInstance = 10;
            // numberOfAssignmentsPerInstance = random.nextInt(assignedUsers.size()) + 1;
            for (int i = 0; i < numberOfAssignmentsPerInstance; i++) {
                userIndex = random.nextInt(assignedUsers.size());
                currentUser = assignedUsers.get(userIndex);
                // picking random Instance based on probability
                randomInstanceProbability = random.nextInt(100) + 1;

                if (randomInstanceProbability < currentUser.getConsistencyCheckProbability() * 100
                        && currentUser.getUserMetric().getUniqueLabeledInstances().size() > 0) {
                    recurrentLabeledInstances = new ArrayList<>();
                    for (Instance instanceToArray : currentUser.getUserMetric().getUniqueLabeledInstances()) {
                        recurrentLabeledInstances.add(instanceToArray);
                    }
                    while (true) {
                        recurrentLabeledInstanceIdx = random.nextInt(recurrentLabeledInstances.size());
                        // if (anInstance == recurrentLabeledInstances.get(recurrentLabeledInstanceIdx)) {
                        //     continue;
                        // }
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

                userMetric.callAllNecessaryMethods(anInstance, dataset, newLabelAssignment, startDate, endDate);
                // userMetric.addLabeledInstances(anInstance, newLabelAssignment);
                // // updating User Metrices
                // // User metric 1: Number of datasets assigned already done in parser class
                // userMetric.addUniqueLabeledInstances(anInstance); // User Metric -4
                // // List of all datasets with their completeness percentage setting with
                // // incrementDatasetCompleteness
                // userMetric.incrementDatasetCompleteness(dataset);// User Metric - 2
                // userMetric.incrementNumberOfLabeledInstances();// User Metric - 3
                // userMetric.setConsistencyPercentage(); // User Metric - 5
                // // STD DEV called inside the setAverageTimeSpent func Metric - 6 - 7
                // userMetric.setAverageTimeSpent(((endDate.getTime() - startDate.getTime()) /
                // (double) 1000));

                // updating Instance Metrics
                instanceMetric.callAllNecessaryMethods(currentUser, newLabelAssignment);
                /* we are calling addUniqueLabel inside the for loop */
                // instanceMetric.addUniqueUser(currentUser);
                // instanceMetric.addLabelAssignments(newLabelAssignment);
                // instanceMetric.setTotalNumberOfAssignedLabels(); // update while model class
                // object is created
                // instanceMetric.setNumberOfUniqueAssignedLabels(); // parameters and method
                // should be handled
                // instanceMetric.setNumberOfUniqueUsers(); // calling inside addUniqueUser

                /* May change the order of method calls */
                // instanceMetric.setMostFrequentLabel();
                // instanceMetric.setPercentageOfMostFrequentLabel();
                // instanceMetric.updateClassLabelsAndPercentages();
                // instanceMetric.setEntropy();
                // ------Hopefully Instance Metrics done-----

                // pushing related Models into MetricModel List
                // instanceModelList.add(instanceMetric.getInstanceModel());
                // userModelList.add(userMetric.getUserModel());

            }
            // updating Dataset Metrices
            datasetMetric.callAllNecessaryMethods(anInstance, dataset);
            // datasetMetric.addUniqueLabeledInstances(anInstance); // for
            // datasetCompleteness
            // datasetMetric.calculateDatasetCompleteness(dataset.getInstances().size());//
            // Dataset Metric - 1
            // datasetMetric.calculateClassDistribution(); // Dataset Metric - 2
            // // dataset metric - 3 is done inside the loop
            // datasetMetric.calculateUserCompleteness(dataset, dataset.getAssignedUsers());
            // // Dataset Metric - 5
            // // dataset metric 4 called while parsing
            // datasetMetric.calculateAssignedUsersAndConcistencyPercentage(dataset.getAssignedUsers());
            // // Dataset Metric
            // -6

        }
        // setting hashset to final metric file !

        metrics.setUsers(List.copyOf(userModelList));
        metrics.setDataset(List.copyOf(datasetModelList));
        metrics.setInstance(List.copyOf(instanceModelList));

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

            // String filePath = currentDirectory + "\\metrics" +
            // String.valueOf(currentDatasetId) + ".json";
            String filePath = currentDirectory + "\\metrics.json";
            serializer.serializeMetricFile(metrics, filePath);

        } catch (Exception e) {
            System.out.println("File not found! Please make sure you provided a correct path.");
            logger.warn("Output File path not found!");
        }
        logger.info("Program Excuted Successfully");
    }
}