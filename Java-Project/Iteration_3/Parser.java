package Iteration_3;

import Iteration_3.MetricsJSONModels.MetricModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
This class is responsible for parsing the input and config files
*/

public class Parser {
    private HashMap<Integer, Dataset> datasetHashMap = new HashMap<Integer, Dataset>();
    private int currentDatasetId;
    private ArrayList<User> users;
    private MetricModel metrics = null;
    private HashMap<Integer, ArrayList<LabelAssignment>> previousLabelAssignments = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public Parser() {
    }

    // Getters

    public HashMap<Integer, Dataset> getDatasetHashMap() {
        return datasetHashMap;
    }

    public int getCurrentDatasetId() {
        return currentDatasetId;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public MetricModel getMetrics() {
        return metrics;
    }

    public HashMap<Integer, ArrayList<LabelAssignment>> getPreviousLabelAssignments() {
        return previousLabelAssignments;
    }

    // Setters

    public void setCurrentDatasetId(int currentDatasetId) {
        this.currentDatasetId = currentDatasetId;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    private Dataset parseDatasetFile(DatasetParsingModel datasetPM, String currentDirectory) throws Exception {
        try {
            Gson gson = new Gson();
            currentDirectory += "\\" + datasetPM.getPath();
            BufferedReader br = new BufferedReader(new FileReader(currentDirectory));
            Dataset newDataset = gson.fromJson(br, Dataset.class);

            // newDataset.setPath(datasetPM.getPath());
            // newDataset.setAssignedUserIds(datasetPM.getAssignedUserIds());
            String logString = "dataset " + newDataset.getDatasetId() + " was parsed successfully";
            logger.info(logString);
            return newDataset;

        } catch (Exception e) {
            logger.warn("Invalid dataset path!");
        }
        return null;
    }

    public void parseConfigFile(String currentDirectory) throws Exception {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader(currentDirectory + "\\config.json"));
            ConfigModel configModel = gson.fromJson(br, ConfigModel.class);
            this.currentDatasetId = configModel.getCurrentDatasetId();
            this.users = (ArrayList<User>) configModel.getUsers();
            for (DatasetParsingModel datasetPM : configModel.getDatasetParsingModels()) {
                ArrayList<User> assignedUsers = new ArrayList<User>();
                for (User user : this.users) {
                    for (Integer userId : datasetPM.getAssignedUserIds()) {
                        if (userId.equals(user.getId())) {
                            user.getUserMetric().incrementNumberOfDatasetsAssigned();// set number of assigned datasets
                            assignedUsers.add(user);
                        }
                    }
                }
                previousLabelAssignments.put(datasetPM.getDatasetId(),
                        parsePreviousLabelAssignments(currentDirectory, datasetPM.getDatasetId()));

                // conversion from datasetParsingModel to dataset
                Dataset dataset = parseDatasetFile(datasetPM, currentDirectory); // parse each dataset
                dataset.setAssignedUsers(assignedUsers);
                dataset.getDatasetMetric().setNumberOfAssignedUsers(assignedUsers.size());// Dataset Metric - 4
                datasetHashMap.put(dataset.getDatasetId(), dataset); // put dataset into map of datasets
            }
        } catch (Exception e) {
            logger.warn("Error while parsing datasets!");
        }
        logger.info("config file was parsed successfully");
    }

    public void parseMetrics(String currentDirectory) throws Exception {
        Gson gson = new Gson();
        try {
            currentDirectory += "\\metrics.json";
            BufferedReader br = new BufferedReader(new FileReader(currentDirectory));
            metrics = gson.fromJson(br, MetricModel.class);
            logger.info("metrics file was parsed successfully");
        } catch (Exception e) {
            logger.warn("No previous metrics on record!");
        }
    }

    private ArrayList<LabelAssignment> parsePreviousLabelAssignments(String currentDirectory, Integer datasetId)
            throws Exception {
        Gson gson = new Gson();
        ArrayList<LabelAssignment> listOfLabelAssignments = new ArrayList<LabelAssignment>();
        try {
            currentDirectory += "\\output" + String.valueOf(datasetId) + ".json";
            BufferedReader br = new BufferedReader(new FileReader(currentDirectory));
            Output output;
            output = gson.fromJson(br, Output.class);

            listOfLabelAssignments = output.getClassLabelAssignments();
            logger.info("old output file was parsed successfully");

        } catch (Exception e) {
            logger.warn("No previous output on record!");
        }
        return listOfLabelAssignments;
    }
}