package OOP_Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.crypto.Data;

import com.google.gson.Gson;

import org.json.simple.*;
import org.json.simple.parser.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
This class is responsible for parsing the input and config files
*/

public class Parser {
    //private JSONParser jsonParser = new JSONParser();
    //private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    long currentDatasetId;

    public Parser() {
    }

    public Dataset parseDatasetFile(String datasetPath, Dataset dataset) throws Exception {
        URL url;
        File file;
        url = getClass().getResource(datasetPath);
        file = new File(url.getPath());
        System.out.println(file.getAbsolutePath());

        Gson gson = new Gson();   
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        InputModel inputModel= gson.fromJson(br, InputModel.class);
        System.out.println(inputModel.getDatasetName());

        
        dataset.setDatasetId(inputModel.getDatasetId());
        dataset.setDatasetName(inputModel.getDatasetName());
        dataset.setMaxLabel(inputModel.getMaximumNumberOfLabelsPerInstance());
        dataset.setLabels(inputModel.getClassLabels());
        //tbd datset set instances

        return dataset;
    }

    //private static final Type CONFIG_TYPE = new TypeToken<List<ConfigModel>>() {}.getType();

    public void parseConfigFile(String configPath, ArrayList<User> users, HashMap<Long, Dataset> datasets, int currentDatasetId) throws Exception {    
        Gson gson = new Gson();   
        BufferedReader br = new BufferedReader(new FileReader(configPath));
        ConfigModel configModel= gson.fromJson(br, ConfigModel.class);
        System.out.println(configModel.getUsers().get(0).getName());

        // tbd set current dataset
        // tbd add users to users in main
        //currentDatasetId = configModel.getCurrentDatasetId();
        //users = (ArrayList<User>) configModel.getUsers();

        for (Dataset dataset : configModel.getDatasets()) {
            // tbd add to datasets in main
            if (dataset.getDatasetId()==configModel.getCurrentDatasetId()){
                // tbd assign datasets to users
                parseDatasetFile(dataset.getPath(), dataset);
                break;
            }     
        }
    }
}