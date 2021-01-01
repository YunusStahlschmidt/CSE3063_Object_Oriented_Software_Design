package OOP_Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import OOP_Project.MetricsJSONModels.DatasetModel;
import OOP_Project.MetricsJSONModels.InstanceModel;
import OOP_Project.MetricsJSONModels.MetricModel;
import OOP_Project.MetricsJSONModels.UserModel;

/* 
This class hold all "computations" will take place
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
        Scanner sc = new Scanner(System.in);


        // parsing the files
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
        List<Label> allLabels;
        User currentUser;
        Date startDate, endDate;
        LabelAssignment newLabelAssignment;

        ArrayList<DatasetModel> datasetModelList = new ArrayList<DatasetModel>();
        ArrayList<InstanceModel> instanceModelList = new ArrayList<InstanceModel>();
        ArrayList<UserModel> userModelList = new ArrayList<UserModel>();

        // if we ran the program before we assign the previous data to the new modelds
        // and add it to the algorithm
        if (metrics != null) {
            datasetModelList = (ArrayList<DatasetModel>) metrics.getDatasetList();
            instanceModelList = (ArrayList<InstanceModel>) metrics.getInstanceList();
            userModelList = (ArrayList<UserModel>) metrics.getUsersList();

            ArrayList<LabelAssignment> updatedLabelAssignments = new ArrayList<>();
            LabelAssignment newestLabelAssignment;
            ArrayList<Label> newAddedLabels;
            Boolean checkpoint = false;

            // add previous label assignments for current dataset to our main list of
            // assignments
            for (LabelAssignment prevLabelAssignment : previousLabelAssignments.get(currentDatasetId)) {
                for (Instance currInstance : dataset.getInstances()) {
                    for (User currUser : dataset.getAssignedUsers()) {
                        if (prevLabelAssignment.getInstanceId().equals(currInstance.getId())
                                && prevLabelAssignment.getUserId().equals(currUser.getId())) {
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
                            newestLabelAssignment = new LabelAssignment(currInstance.getId(), newAddedLabels,
                                    currUser.getId(), prevLabelAssignment.getDate());
                            updatedLabelAssignments.add(newestLabelAssignment);

                            currInstance.getInstanceMetric().callAllNecessaryMethods(currUser, newestLabelAssignment);
                            currUser.getUserMetric().callAllNecessaryMethods(currInstance, dataset,
                                    newestLabelAssignment, null, null);
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
                        break;
                    }
                }
            }

            for (Dataset myDataset : datasetHashMap.values()) {
                for (DatasetModel datasetModel : datasetModelList) {
                    if (datasetModel.getDatasetId().equals(myDataset.getDatasetId())) {
                        myDataset.getDatasetMetric().setDatasetModel(datasetModel);
                        break;
                    }
                }

                for (Instance instanceForId : myDataset.getInstances()) {
                    for (InstanceModel instanceModel : instanceModelList) {
                        if (instanceModel.getInstanceId().equals(instanceForId.getId())
                                && (instanceModel.getDatasetId().equals(myDataset.getDatasetId()))) {
                            instanceForId.getInstanceMetric().setInstanceModel(instanceModel);
                            break;
                        }
                    }
                }
            }

        } else {
            metrics = new MetricModel();
            for (Dataset myDataset : datasetHashMap.values()) {
                myDataset.setDatasetIdToModel();
                myDataset.getDatasetMetric().setInitialDatasetModel();
                datasetModelList.add(myDataset.getDatasetMetric().getDatasetModel());

                for (User user : myDataset.getAssignedUsers()) {
                    user.getUserMetric().incrementDatasetCompleteness(myDataset);
                    user.setUserIdToModel();
                    user.getUserMetric().setInitialUserModel();
                }
                for (Instance instanceForId : myDataset.getInstances()) {
                    instanceForId.setDatasetIdToInstanceModel(myDataset.getDatasetId());
                    instanceForId.setInstanceIdToModel();
                    instanceForId.getInstanceMetric().setInitialInstanceModel();
                    instanceModelList.add(instanceForId.getInstanceMetric().getInstanceModel());
                }
            }
            for (User user : users) {
                userModelList.add(user.getUserMetric().getUserModel());
            }
        }

        // metrices
        UserMetric userMetric;
        InstanceMetric instanceMetric;
        DatasetMetric datasetMetric = dataset.getDatasetMetric();
        Integer randomInstanceProbability, recurrentLabeledInstanceIdx;
        ArrayList<Instance> recurrentLabeledInstances;

        ArrayList<User> assignedUsers = dataset.getAssignedUsers();
        // metric model set dataset model list
        
        User continueRandom = Login.randomInitialize(users);

        if (continueRandom != null)
        {
            for (Instance anInstance : dataset.getInstances()) {
                numberOfAssignmentsPerInstance = assignedUsers.size();
                
                currentUser = continueRandom;
                // picking random Instance based on probability
                randomInstanceProbability = random.nextInt(100) + 1;

                if (randomInstanceProbability < currentUser.getConsistencyCheckProbability() * 100
                        && currentUser.getUserMetric().getUniqueLabeledInstances().size() > 0) {
                    recurrentLabeledInstances = new ArrayList<>();
                    for (Instance instanceToArray : currentUser.getUserMetric().getUniqueLabeledInstances()) {
                        recurrentLabeledInstances.add(instanceToArray);
                    }
                    recurrentLabeledInstanceIdx = random.nextInt(recurrentLabeledInstances.size());
                    anInstance = recurrentLabeledInstances.get(recurrentLabeledInstanceIdx);
                }
                
                startDate = new Date();

                userMetric = currentUser.getUserMetric();

                int maxLabel = (int) dataset.getMaxLabel();
                List<String> chosen;
                ArrayList<String> labelOpt = new ArrayList<String>();
                allLabels = dataset.getLabels();

                System.out.println("\n");

                addedLabels = new ArrayList<>(); //initializing also here to prevent may not be initialized error
                instanceMetric = anInstance.getInstanceMetric();

                boolean running = true;
                while (running)
                {
                    datasetMetric = dataset.getDatasetMetric();

                    System.out.printf("Comment: %s\n\n", anInstance.getInstance());
                
                    addedLabels = new ArrayList<>();
                    instanceMetric = anInstance.getInstanceMetric();

                    for (Label l: allLabels)
                    {
                        System.out.printf("%s. %s ", l.getId(), l.getLabelText());
                        labelOpt.add(l.getId().toString());
                        addedLabels.add(l);
                        instanceMetric.addUniqueLabel(l);
                        datasetMetric.addInstanceForLabel(l, anInstance);
                    }             

                    System.out.printf("\nChoose max %s label/s number (seperate with coma if you choose more than one): ", maxLabel);
                
                    String assign = sc.nextLine().trim();

                    if (assign.equals(""))
                    {
                        System.out.println("You have to choose a label. Please try again.");
                        continue;
                    }
                    chosen =  Arrays.asList(assign.split(","));

                    if (chosen.size() > maxLabel)
                        System.out.println("\nYou outmaxed the maximum value. Please decrease number of labels\n");

                    else
                    {   
                        int count = 0;
                        for (String str : chosen) 
                        {
                            if (!labelOpt.contains(str))
                            {
                                System.out.printf("\n%s not in label options. Please choose valid ones.\n", str);
                                break;
                            }
                            else
                            {
                                count++;
                            }
                        }
                        if (count == chosen.size())
                            running = false;
                    }
                }
                
                newLabelAssignment = new LabelAssignment(anInstance.getId(), addedLabels, currentUser.getId(), new Date());
                labelAssignments.add(newLabelAssignment);
                endDate = new Date();

                // print the output to console via the logger (and too app.log file)
                if (newLabelAssignment.getAssignedLabelId().size() == 1) {
                    String label_name = dataset.getLabels()
                            .get((int) newLabelAssignment.getSpecificAssignedLabelId(0) - 1).getLabelText();
                    logger.info("user id:" + currentUser.getId() + " " + currentUser.getName() + " tagged instance id:"
                            + anInstance.getId() + " with class label " + newLabelAssignment.getAssignedLabelId() + ":"
                            + label_name + " instance: " + anInstance.getInstance());
                } else {
                    ArrayList<String> labels = new ArrayList<>();

                    for (int n = 0; n < newLabelAssignment.getAssignedLabelId().size(); n++) {
                        labels.add(dataset.getLabels().get((int) newLabelAssignment.getSpecificAssignedLabelId(n) - 1)
                                .getLabelText());
                    }
                    logger.info("user id:" + currentUser.getId() + " " + currentUser.getName() + " tagged instance id:"
                            + anInstance.getId() + " with class labels "
                            + newLabelAssignment.getAssignedLabelId().toString() + ":" + labels.toString()
                            + " instance: " + anInstance.getInstance());
                }

                // updating current user metric
                userMetric.callAllNecessaryMethods(anInstance, dataset, newLabelAssignment, startDate, endDate);
                // updating Instance Metrics
                instanceMetric.callAllNecessaryMethods(currentUser, newLabelAssignment);
                // updating dataset metric
                datasetMetric.callAllNecessaryMethods(anInstance, dataset);
                // setting the list of models to the metrics object
                metrics.setUsers(List.copyOf(userModelList));
                metrics.setDataset(List.copyOf(datasetModelList));
                metrics.setInstance(List.copyOf(instanceModelList));

                try {
                    System.out.println();
                    String outputPath = currentDirectory + "\\output" + String.valueOf(currentDatasetId) + ".json";
                    serializer.serializeOutputFile(outputPath, dataset, labelAssignments, users);

                    String filePath = currentDirectory + "\\metrics.json";
                    serializer.serializeMetricFile(metrics, filePath);
                    System.out.println();
                    System.out.println();

                } catch (Exception e) {
                    System.out.println();
                    System.out.println("File not found! Please make sure you provided a correct path.");
                    logger.warn("Output File path not found!");
                }
            }
        }

        // bot labeling mechanism
        for (Instance anInstance : dataset.getInstances()) {
            numberOfAssignmentsPerInstance = assignedUsers.size();
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
                    recurrentLabeledInstanceIdx = random.nextInt(recurrentLabeledInstances.size());
                    anInstance = recurrentLabeledInstances.get(recurrentLabeledInstanceIdx);
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

                // print the output to console via the logger (and too app.log file)
                if (newLabelAssignment.getAssignedLabelId().size() == 1) {
                    String label_name = dataset.getLabels()
                            .get((int) newLabelAssignment.getSpecificAssignedLabelId(0) - 1).getLabelText();
                    logger.info("user id:" + currentUser.getId() + " " + currentUser.getName() + " tagged instance id:"
                            + anInstance.getId() + " with class label " + newLabelAssignment.getAssignedLabelId() + ":"
                            + label_name + " instance: " + anInstance.getInstance());
                } else {
                    ArrayList<String> labels = new ArrayList<>();

                    for (int n = 0; n < newLabelAssignment.getAssignedLabelId().size(); n++) {
                        labels.add(dataset.getLabels().get((int) newLabelAssignment.getSpecificAssignedLabelId(n) - 1)
                                .getLabelText());
                    }
                    logger.info("user id:" + currentUser.getId() + " " + currentUser.getName() + " tagged instance id:"
                            + anInstance.getId() + " with class labels "
                            + newLabelAssignment.getAssignedLabelId().toString() + ":" + labels.toString()
                            + " instance: " + anInstance.getInstance());
                }

                // updating current user metric
                userMetric.callAllNecessaryMethods(anInstance, dataset, newLabelAssignment, startDate, endDate);
                // updating Instance Metrics
                instanceMetric.callAllNecessaryMethods(currentUser, newLabelAssignment);
                // updating dataset metric
                datasetMetric.callAllNecessaryMethods(anInstance, dataset);
                // setting the list of models to the metrics object
                metrics.setUsers(List.copyOf(userModelList));
                metrics.setDataset(List.copyOf(datasetModelList));
                metrics.setInstance(List.copyOf(instanceModelList));

                try {
                    System.out.println();
                    String outputPath = currentDirectory + "\\output" + String.valueOf(currentDatasetId) + ".json";
                    serializer.serializeOutputFile(outputPath, dataset, labelAssignments, users);

                    String filePath = currentDirectory + "\\metrics.json";
                    serializer.serializeMetricFile(metrics, filePath);
                    System.out.println();
                    System.out.println();

                } catch (Exception e) {
                    System.out.println();
                    System.out.println("File not found! Please make sure you provided a correct path.");
                    logger.warn("Output File path not found!");
                }
            }
        }

        System.out.println();
        logger.info("Program Excuted Successfully");
        sc.close();
    }
}