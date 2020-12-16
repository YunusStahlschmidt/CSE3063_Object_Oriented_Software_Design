package OOP_Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.xml.crypto.Data;

import org.json.simple.*;
import org.json.simple.parser.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
This class is responsible for parsing the input and config files
*/

public class Parser {
    private JSONParser jsonParser = new JSONParser();
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    long currentDatasetId;

    public Parser() {
    }

    public Dataset parseJSONFile(String jsonPath) throws Exception {
        Dataset dataset = new Dataset();
        JSONObject jsonObject = jsonObjectCreator(jsonPath);

        // Parsing
        JSONArray classLables = (JSONArray) jsonObject.get("class labels");
        JSONArray instances = (JSONArray) jsonObject.get("instances");
        long datasetId = (long) jsonObject.get("dataset id");
        String datasetName = (String) jsonObject.get("dataset name");
        long maxNumberOfLabelsPerInstance = (long) jsonObject.get("maximum number of labels per instance");

        dataset.setId(datasetId);
        dataset.setName(datasetName.toString());
        dataset.setMaxLabel(maxNumberOfLabelsPerInstance);

        // Parsing and creating Class Labels
        Iterator<Map.Entry> itr1;
        Iterator itr2 = classLables.iterator();
        long id = 0;
        String text = "";

        while (itr2.hasNext()) {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if (pair.getKey().equals("label id")) {
                    id = (long) pair.getValue();
                } else {
                    text = (String) pair.getValue();
                }
            }
            dataset.addLabel(id, text);
        }

        itr2 = instances.iterator();
        while (itr2.hasNext()) {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if (pair.getKey().equals("id")) {
                    id = (long) pair.getValue();
                } else {
                    text = (String) pair.getValue();
                }
            }
            dataset.addInstance(id, text);
        }
        return dataset;
    }

    public void parseConfigFile(String configPath, ArrayList<User> users, HashMap<Long, Dataset> datasets) throws Exception {
        JSONObject jsonObject = jsonObjectCreator(configPath);

        // Parsing
        currentDatasetId = (long) jsonObject.get("CurrentDatasetId");
        JSONArray newUserArray = (JSONArray) jsonObject.get("users");
        JSONArray newDatasetArray = (JSONArray) jsonObject.get("datasets");
        URL url;
        File file;

        // System.out.println(newUserArray);

        // Users
        Iterator<Map.Entry> itr1;
        Iterator itr2 = newUserArray.iterator();
        User newUser;
        long id = 0;
        String name = "";
        String userType = "";
        JSONArray assignedDatasets = new JSONArray();
        
        while (itr2.hasNext()) {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if (pair.getKey().equals("user id")) {
                    id = (long) pair.getValue();
                } else if (pair.getKey().equals("user name")) {
                    name = (String) pair.getValue();
                } else if (pair.getKey().equals("user type")) {
                    userType = (String) pair.getValue();
                } else {
                    // System.out.println(jsonObject.get("assigned datasets").getClass().getName());
                    assignedDatasets = (JSONArray) jsonObject.get("assigned datasets");
                }
            }
            newUser = new User(id, name, userType);
            newUser.setNumberOfDatasetsAssigned(assignedDatasets.size());
            System.out.println("NumberOfDatasetsAssigned" + assignedDatasets.size());
            users.add(newUser);
            logger.info("userManager: created " + name + " as " + userType);
        }

        // Datasets
        Long datasetId = (long) 0;
        String datasetPath = "";

        itr2 = newDatasetArray.iterator();
        while (itr2.hasNext()) {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if (pair.getKey().equals("dataset id")) {
                    datasetId = (long) pair.getValue();
                } else if (pair.getKey().equals("path")) {
                    datasetPath = (String) pair.getValue();
                }
            }
            // url = getClass().getResource(datasetPath);
            // file = new File(url.getPath());
            datasets.put(datasetId, parseJSONFile(datasetPath));
        }
    }

    private JSONObject jsonObjectCreator(String path) throws Exception {
        FileReader reader = new FileReader(path);
        Object obj = jsonParser.parse(reader);
        return (JSONObject) obj;
    }

    public long getCurrentDatasetId() {
        return currentDatasetId;
    }
}