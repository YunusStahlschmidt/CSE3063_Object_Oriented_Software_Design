package OOP_Project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.*;

/* 
This class is responsible for parsing the input and config files
*/

public class Parser {
    private JSONParser jsonParser = new JSONParser();

    public Parser() {}
    
    public void parseJSONFile(String jsonPath, Dataset dataset) throws Exception {
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
    }

    public void parseConfigFile(String configPath, ArrayList<User> users) throws Exception {
        JSONObject jsonObject = jsonObjectCreator(configPath);

        // Parsing
        JSONArray newUserArray = (JSONArray) jsonObject.get("users");
        System.out.println(newUserArray);
        Iterator<Map.Entry> itr1;
        Iterator itr2 = newUserArray.iterator();
        long id = 0;
        String name = "";
        String text = "";

        while (itr2.hasNext()) {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if (pair.getKey().equals("user id")) {
                    id = (long) pair.getValue();
                } else if (pair.getKey().equals("user name")) {
                    name = (String) pair.getValue();
                } else {
                    text = (String) pair.getValue();
                }
            }
            users.add(new User(id, name, text));
        }
    }

    private JSONObject jsonObjectCreator(String path) throws Exception{
        FileReader reader = new FileReader(path);
        Object obj = jsonParser.parse(reader);
        return (JSONObject) obj;
    }
}