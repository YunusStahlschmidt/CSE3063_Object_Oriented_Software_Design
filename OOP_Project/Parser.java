package OOP_Project;

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

    public void parseDatasetFile(Dataset dataset) throws Exception {
        URL url;
        File file;
        url = getClass().getResource(dataset.getPath());
        file = new File(url.getPath());
        //System.out.println(file.getAbsolutePath());

        Gson gson = new Gson();   
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        Dataset newDataset = gson.fromJson(br, Dataset.class);
        //System.out.println(dataset.getDatasetName());

        this.currentDataset = newDataset;
        this.currentDataset.setPath(dataset.getPath());
        this.currentDataset.setAssignedUserIds(dataset.getAssignedUserIds());
    }

    public void parseConfigFile(String configPath) throws Exception {    
        Gson gson = new Gson();   
        BufferedReader br = new BufferedReader(new FileReader(configPath));
        ConfigModel configModel = gson.fromJson(br, ConfigModel.class);
        //System.out.println(configModel.getUsers().get(0).getName());

        //System.out.println(configModel.getCurrentDatasetId());
        //System.out.println(configModel.getUsers());
        this.currentDatasetId = configModel.getCurrentDatasetId();
        this.users = (ArrayList<User>) configModel.getUsers();

        for (Dataset dataset : configModel.getDatasets()) {  
            // tbd assign datasets to users    
            for (User user : this.users){
                for (Integer userId : dataset.getAssignedUserIds()){
                    if (userId == user.getId()){
                        user.incrementNumberOfDatasetsAssigned();
                    }
                }
                //System.out.println(user.getNumberOfDatesetsAssigned()); 
            }
            if (dataset.getDatasetId() == configModel.getCurrentDatasetId()){
                parseDatasetFile(dataset);
            }     
        } 
    }
}