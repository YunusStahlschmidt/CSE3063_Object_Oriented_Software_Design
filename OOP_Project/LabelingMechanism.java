package OOP_Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import OOP_Project.MetricsJSONModels.DatasetModel;
import OOP_Project.MetricsJSONModels.InstanceModel;
import OOP_Project.MetricsJSONModels.MetricModel;
import OOP_Project.MetricsJSONModels.UserModel;

public class LabelingMechanism {
    private Dataset dataset;
    private User currentUser;
    private MetricModel metrics;
    private UserMetric userMetric;
    private InstanceMetric instanceMetric;
    private DatasetMetric datasetMetric;
    private Integer randomInstanceProbability;
    private Integer recurrentLabeledInstanceIdx;
    private ArrayList<Instance> recurrentLabeledInstances;
    private ArrayList<User> assignedUsers;
    private Random random = new Random();
    private Date startDate, endDate;
    private LabelAssignment newLabelAssignment;
    private ArrayList<Label> newlyAddedLabels;
    private JSONSerializer serializer = new JSONSerializer();
    private String currentDirectory;
    private ArrayList<LabelAssignment> labelAssignments;

    private ArrayList<DatasetModel> datasetModelList = new ArrayList<DatasetModel>();
    private ArrayList<InstanceModel> instanceModelList = new ArrayList<InstanceModel>();
    private ArrayList<UserModel> userModelList = new ArrayList<UserModel>();

    private static final Logger logger = LoggerFactory.getLogger(LabelingMechanism.class);

    LabelingMechanism(Dataset dataset, MetricModel metrics, String currentDirectory,
            ArrayList<DatasetModel> datasetModelList, ArrayList<InstanceModel> instanceModelList,
            ArrayList<UserModel> userModelList, HashMap<Integer, ArrayList<LabelAssignment>> allLabelAssignments) {

        this.dataset = dataset;
        this.metrics = metrics;
        this.assignedUsers = dataset.getAssignedUsers();
        this.currentDirectory = currentDirectory;
        this.datasetModelList = datasetModelList;
        this.instanceModelList = instanceModelList;
        this.userModelList = userModelList;
        this.labelAssignments = allLabelAssignments.get(this.dataset.getDatasetId());
    }

    public void userLabeling(ArrayList<LabelAssignment> labelAssignments) {
        UI ui = new UI();
        // if (currentUser.getId())
        for (Instance anInstance : dataset.getInstances()) {
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

            System.out.println("\n");

            newlyAddedLabels = new ArrayList<>(); // initializing also here to prevent may not be initialized error
            instanceMetric = anInstance.getInstanceMetric();

            boolean running = true;
            while (running) {
                datasetMetric = dataset.getDatasetMetric();

                // System.out.printf("Comment: %s\n\n", anInstance.getInstance());
                String message1 = String.format("Comment: 1%s\n\n", anInstance.getInstance());
                ui.printOutput(message1);
                newlyAddedLabels = new ArrayList<>();
                // instanceMetric = anInstance.getInstanceMetric();

                for (Label l : dataset.getLabels()) {
                    // System.out.printf("%s. %s ", l.getId(), l.getLabelText());
                    String message3 = String.format("1%s. 2%s ", l.getId(), l.getLabelText());
                    ui.printOutput(message3);
                    labelOpt.add(l.getId().toString());
                }
                // System.out.printf("\nChoose max %s label/s number (seperate with coma if you
                // choose more than one): ", maxLabel);
                String message4 = String.format(
                        "\nChoose max 1%s label/s number (seperate with coma if you choose more than one): ", maxLabel);
                ui.printOutput(message4);
                String assign = ui.askForInput(message4);

                if (assign.equals("")) {
                    ui.printOutput("Choose a label. Please try again.");
                    continue;
                }
                chosen = Arrays.asList(assign.split(","));

                if (chosen.size() > maxLabel)
                    ui.printOutput("Over maximum value. Delete one or more labels");

                else {
                    int count = 0;
                    for (String str : chosen) {
                        if (!labelOpt.contains(str)) {
                            // System.out.printf("\n%s not in label options. Choose a valid Label.\n", str);
                            String message2 = String.format("\n1%s not in label options. Choose a valid Label.\n", str);
                            ui.printOutput(message2);
                            break;
                        } else {
                            count++;
                        }
                    }
                    if (count == chosen.size())
                        running = false;
                    for (int i = 0; i < chosen.size(); i++) {
                        Label tempLabel = dataset.getLabels().get(Integer.parseInt(chosen.get(i)));
                        newlyAddedLabels.add(tempLabel);
                        instanceMetric.addUniqueLabel(tempLabel);
                        datasetMetric.addInstanceForLabel(tempLabel, anInstance);
                    }
                    newLabelAssignment = new LabelAssignment(anInstance.getId(), newlyAddedLabels, currentUser.getId(),
                            new Date());
                    labelAssignments.add(newLabelAssignment);
                    endDate = new Date();
                }
            }
        }
    }

    public void botLabeling() {
        ArrayList<User> assignedUsers = this.dataset.getAssignedUsers();
        ArrayList<Label> addedLabels;
        User currentUser;
        Label randomLabel;

        for (Instance anInstance : dataset.getInstances()) {
            for (int i = 0; i < this.assignedUsers.size(); i++) {
                currentUser = assignedUsers.get(random.nextInt(assignedUsers.size()));
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

                this.logInfoAndUpdatingModels(anInstance);
            }
        }
    }

    public void mlLabeling() {

    }

    public void logInfoAndUpdatingModels(Instance anInstance) {
        // print the output to console via the logger (and too app.log file)
        if (newLabelAssignment.getAssignedLabelId().size() == 1) {
            String label_name = dataset.getLabels().get((int) newLabelAssignment.getSpecificAssignedLabelId(0) - 1)
                    .getLabelText();
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
                    + anInstance.getId() + " with class labels " + newLabelAssignment.getAssignedLabelId().toString()
                    + ":" + labels.toString() + " instance: " + anInstance.getInstance());
        }

        // updating current user metric
        userMetric.callAllNecessaryMethods(anInstance, dataset, newLabelAssignment, startDate, endDate);
        // updating Instance Metrics
        instanceMetric.callAllNecessaryMethods(currentUser, newLabelAssignment);
        // updating dataset metric
        datasetMetric.callAllNecessaryMethods(anInstance, dataset);
        // setting the list of models to the metrics object
        metrics.setUsers((List<UserModel>) userModelList);
        metrics.setDataset((List<DatasetModel>) datasetModelList);
        metrics.setInstance((List<InstanceModel>) instanceModelList);

        try {
            System.out.println();
            String outputPath = currentDirectory + "\\output" + String.valueOf(dataset.getDatasetId()) + ".json";
            serializer.serializeOutputFile(outputPath, dataset, this.labelAssignments, dataset.getAssignedUsers());

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