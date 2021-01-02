package Iteration_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
// import java.util.Scanner;
import java.util.HashMap;
// import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Iteration_3.MetricsJSONModels.DatasetModel;
import Iteration_3.MetricsJSONModels.InstanceModel;
import Iteration_3.MetricsJSONModels.MetricModel;
import Iteration_3.MetricsJSONModels.UserModel;

public class LabelingMechanism {
    private Dataset dataset;
    private MetricModel metrics;
    private UserMetric userMetric;
    private InstanceMetric instanceMetric;
    private DatasetMetric datasetMetric;
    private Integer randomInstanceProbability;
    private Integer recurrentLabeledInstanceIdx;
    private ArrayList<Instance> recurrentLabeledInstances;
    private Random random = new Random();
    private Date startDate, endDate;
    private LabelAssignment newLabelAssignment;
    private ArrayList<Label> newlyAddedLabels;
    private JSONSerializer serializer = new JSONSerializer();
    private String currentDirectory;
    private ArrayList<LabelAssignment> labelAssignments;
    private UI ui;

    private ArrayList<DatasetModel> datasetModelList = new ArrayList<DatasetModel>();
    private ArrayList<InstanceModel> instanceModelList = new ArrayList<InstanceModel>();
    private ArrayList<UserModel> userModelList = new ArrayList<UserModel>();

    private static final Logger logger = LoggerFactory.getLogger(LabelingMechanism.class);

    public LabelingMechanism(Dataset dataset, MetricModel metrics, String currentDirectory,
            ArrayList<DatasetModel> datasetModelList, ArrayList<InstanceModel> instanceModelList,
            ArrayList<UserModel> userModelList, HashMap<Integer, ArrayList<LabelAssignment>> allLabelAssignments, UI ui) {

        this.dataset = dataset;
        this.metrics = metrics;
        this.currentDirectory = currentDirectory;
        this.datasetModelList = datasetModelList;
        this.instanceModelList = instanceModelList;
        this.userModelList = userModelList;
        this.labelAssignments = allLabelAssignments.get(this.dataset.getDatasetId());
        this.ui = ui;
    }

    public void userLabeling(User currentUser) {
        for (Instance anInstance : dataset.getInstances()) {
            // picking random Instance based on probability
            randomInstanceProbability = random.nextInt(100) + 1;
            if (currentUser.getUserMetric().getUniqueLabeledInstances().contains(anInstance)) {
                continue;
            }

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

                String message1 = String.format("Comment: %s\n\n", anInstance.getInstance());
                ui.printOutput(message1);
                newlyAddedLabels = new ArrayList<>();

                for (Label l : dataset.getLabels()) {
                    String message3 = String.format("%s. %s ", l.getId(), l.getLabelText());
                    ui.printOutput(message3);
                    labelOpt.add(l.getId().toString());
                }
                String message4 = String.format(
                        "\nChoose max %s label/s number (seperate with coma if you choose more than one): ", maxLabel);
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
                            String message2 = String.format("\n%s not in label options. Choose a valid Label.\n", str);
                            ui.printOutput(message2);
                            break;
                        } else {
                            count++;
                        }
                    }
                    if (count == chosen.size()) {
                        running = false;
                        for (int i = 0; i < chosen.size(); i++) {
                            Label tempLabel = dataset.getLabels().get(Integer.parseInt(chosen.get(i)) - 1);
                            newlyAddedLabels.add(tempLabel);
                            instanceMetric.addUniqueLabel(tempLabel);
                            datasetMetric.addInstanceForLabel(tempLabel, anInstance);
                        }
                        newLabelAssignment = new LabelAssignment(anInstance.getId(), newlyAddedLabels,
                                currentUser.getId(), new Date());
                        labelAssignments.add(newLabelAssignment);
                        endDate = new Date();
                    }
                }
            }
            this.logInfoAndUpdatingModels(anInstance, currentUser);
        }
    }

    public void botLabeling() {
        ArrayList<User> assignedBotUsers = (ArrayList<User>) this.dataset.getAssignedUsers().stream()
                .filter(u -> u.getType().equals("RandomBot") || u.getType().equals("MLBot"))
                .collect(Collectors.toList());
        User currentUser;

        datasetMetric = dataset.getDatasetMetric();

        for (Instance anInstance : dataset.getInstances()) {

            for (int i = 0; i < assignedBotUsers.size(); i++) {
                currentUser = assignedBotUsers.get(random.nextInt(assignedBotUsers.size()));
                // picking random Instance based on probability
                if (currentUser.getUserMetric().getUniqueLabeledInstances().contains(anInstance)) {
                    continue;
                }
                randomInstanceProbability = random.nextInt(100) + 1;
                if (currentUser.getType().equals("RandomBot"))
                    randomLabeling(currentUser, anInstance);
                else if (currentUser.getType().equals("MLBot"))
                    ruleBasedLabeling(currentUser, anInstance);
            }
        }
    }

    public void randomLabeling(User currentUser, Instance anInstance) {
        ArrayList<Label> addedLabels;
        Label randomLabel;
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
        newLabelAssignment = new LabelAssignment(anInstance.getId(), addedLabels, currentUser.getId(), new Date());
        labelAssignments.add(newLabelAssignment);
        endDate = new Date();

        this.logInfoAndUpdatingModels(anInstance, currentUser);
    }

    public void ruleBasedLabeling(User currentUser, Instance anInstance) {
        ArrayList<Label> addedLabels = new ArrayList<>();

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

        startDate = new Date();
        if (this.dataset.getDatasetId().equals(1) || this.dataset.getDatasetId().equals(3)) {
            Double average = 0.0;
            Integer usedWords = 0;

            HashMap<String, Integer> wordWeights = new HashMap<>();
            wordWeights.put("hasarlıydı", 0); wordWeights.put("ilgilenerek", 1);
            wordWeights.put("güzel", 1);      wordWeights.put("teşekkürler", 1);
            wordWeights.put("yoktu", 0);      wordWeights.put("çalışmıyor", 0);
            wordWeights.put("memnun", 1);     wordWeights.put("iyi", 1);
            wordWeights.put("düşmesi", 0);    wordWeights.put("artısı", 1);
            wordWeights.put("büyük", 1);      wordWeights.put("bitiriyor", 0);
            wordWeights.put("basarili", 1);   wordWeights.put("alabilirsiniz", 1);

            for (String word : anInstance.getInstance().split(" ")) {
                if (wordWeights.containsKey(word)) {
                    average += wordWeights.get(word);
                    usedWords++;
                }
            }

            average /= usedWords;
            Label currentLabel = null;
            if (average.equals((double) 1)) { currentLabel = dataset.getLabels().get(0); }
            else if (average.equals((double) 0)) { currentLabel = dataset.getLabels().get(1); }
            else if (average.equals(0.5)) { currentLabel = dataset.getLabels().get(2); }

            addedLabels.add(currentLabel);
            instanceMetric.addUniqueLabel(currentLabel);
            datasetMetric.addInstanceForLabel(currentLabel, anInstance);

        } else {
            for (Label label : this.dataset.getLabels()) {
                if (anInstance.getInstance().contains(label.getLabelText())) {
                    addedLabels.add(label);
                    instanceMetric.addUniqueLabel(label);
                    datasetMetric.addInstanceForLabel(label, anInstance);
                }
            }
        }

        newLabelAssignment = new LabelAssignment(anInstance.getId(), addedLabels, currentUser.getId(), new Date());
        labelAssignments.add(newLabelAssignment);
        endDate = new Date();

        this.logInfoAndUpdatingModels(anInstance, currentUser);
    }

    public void logInfoAndUpdatingModels(Instance anInstance, User currentUser) {
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
            ui.printOutput("\nFile not found! Please make sure you provided a correct path.");
            logger.warn("Output File path not found!");
        }
    }
}