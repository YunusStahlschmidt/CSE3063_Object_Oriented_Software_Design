package CSE3063F20P1_GRP27.OOP_Project;

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
    private String jsonPath;
    private Dataset dataset;

    Parser(String jsonPath, Dataset dataset){
        this.jsonPath = jsonPath;
        this.dataset = dataset;
    }
    
    // Getters

    public String getJsonPath() {
        return jsonPath;
    }

    public Dataset getDataset() {
        return dataset;
    }


    // Setters

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public void parseJSONFile() {        
        Scanner scan = new Scanner(System.in);
        System.out.print("Please type the absolute path of json file: ");
        String jsonString = scan.nextLine(); // assign your JSON String here
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(jsonString);
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            // Parsing
            JSONArray classLables = (JSONArray) jsonObject.get("class labels");
            JSONArray instances = (JSONArray) jsonObject.get("instances");
            long datasetId = (long) jsonObject.get("dataset id");
            String datasetName = (String) jsonObject.get("dataset name");
            long maxNumberOfLabelsPerInstance = (long) jsonObject.get("maximum number of labels per instance");

            System.out.println(classLables);
            System.out.println();
            System.out.println(instances);
            System.out.println();
            System.out.println(datasetId);
            System.out.println();
            System.out.println(datasetName);
            System.out.println();
            System.out.println(maxNumberOfLabelsPerInstance);

            dataset.setId(datasetId);
            dataset.setName(datasetName.toString());
            dataset.setMaxLabel(maxNumberOfLabelsPerInstance);

            // Parsing and creating Class Labels
            Iterator<Map.Entry> itr1;
            Iterator itr2 = classLables.iterator();
            long id = 0;
            String text = "";
            
            while (itr2.hasNext())  
            {
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
            while (itr2.hasNext())  
            {
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

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void parseConfigFile(){

    }
}
