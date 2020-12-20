package OOP_Project;

import OOP_Project.MetricsJSONModels.MetricModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

import org.json.simple.*;
import org.json.simple.parser.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
This class is responsible for parsing the input and config files
*/

public class Parser {
    private Dataset currentDataset;
    private int currentDatasetId;
    private ArrayList<User> users;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    MetricModel metrics = null;

    public Parser() {
    }

    // Getters

    public Dataset getCurrentDataset() {
        return currentDataset;
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

    // Setters

    public void setCurrentDataset(Dataset currentDataset) {
        this.currentDataset = currentDataset;
    }

    public void setCurrentDatasetId(int currentDatasetId) {
        this.currentDatasetId = currentDatasetId;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void parseDatasetFile(Dataset dataset, String currentDirectory) throws Exception {

        Gson gson = new Gson();
        currentDirectory += "\\" + dataset.getPath();
        BufferedReader br = new BufferedReader(new FileReader(currentDirectory));

        Dataset newDataset = gson.fromJson(br, Dataset.class);

        this.currentDataset = newDataset;
        this.currentDataset.setPath(dataset.getPath());
        this.currentDataset.setAssignedUserIds(dataset.getAssignedUserIds());
        logger.info("Dataset has been Parsed Sucessfully!");

    }

    public void parseConfigFile(String currentDirectory) throws Exception {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(currentDirectory + "\\config.json"));
        ConfigModel configModel = gson.fromJson(br, ConfigModel.class);
        this.currentDatasetId = configModel.getCurrentDatasetId();
        this.users = (ArrayList<User>) configModel.getUsers();
        logger.info("Config.json has been Parsed Sucessfully!");
        for (Dataset dataset : configModel.getDatasets()) {
            // tbd assign datasets to users
            for (User user : this.users) {
                for (Integer userId : dataset.getAssignedUserIds()) {
                    if (userId == user.getId()) {
                        user.getUserMetric().incrementNumberOfDatasetsAssigned();
                        // dataset completeness
                        user.getUserMetric().incrementDatasetCompleteness(dataset);
                    }
                }
            }
            if (dataset.getDatasetId() == configModel.getCurrentDatasetId()) {
                parseDatasetFile(dataset, currentDirectory);
            }
        }
    }

    public void parseMetrics(String currentDirectory) throws Exception {
        Gson gson = new Gson();
        try {
            currentDirectory += "\\metrics.json";
            BufferedReader br = new BufferedReader(new FileReader(currentDirectory));
            metrics = gson.fromJson(br, MetricModel.class);

        } catch (Exception e) {
            System.out.println("No previous metrcics on record!");
            logger.warn("No previous metrcics on record!");
        }
        if (metrics != null) { // if not first run set flag true and parse
            // actions?

            // logger.info("metrcics has been parsed sucessfully!");

        }
    }
}