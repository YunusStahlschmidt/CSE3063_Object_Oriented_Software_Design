package OOP_Project;

import java.io.FileReader;
import java.io.IOException;
import java.security.DrbgParameters.NextBytes;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Iterator; 
import java.util.Map; 
import java.util.Random;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.*;

// import sun.jvm.hotspot.utilities.IntArray;


/* 
This class will where al "computations" will take place
*/

public class Main {
    public static void main(String[] args) {
        Dataset dataset = new Dataset();
        ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
        Dictionary<String, User> usersDict;

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
                    // System.out.println(pair.getKey() + " : " + pair.getValue()); 
                }
                dataset.addLabel(id, text);
            }
            // System.out.println("Labels");
            // System.out.println(dataset.getLabels());
            

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
                    // System.out.println(pair.getKey() + " : " + pair.getValue()); 
                }
                dataset.addInstance(id, text);
            }
            // System.out.println("Instances");
            // System.out.println(dataset.getInstances());

            Random rand = new Random();
            int numberOfUsers = rand.nextInt(7);
            String username;
            for (int i = 1; i < numberOfUsers; i++) {
                username = String.format("RandomLabelingMechanism%d", i);
                dataset.addUser(i, username, "RandomBot");
            }

            int userIndex;
            for (int i = 0; i < dataset.getInstances().size(); i++) {
                for (long maxlabel = 0; maxlabel < maxNumberOfLabelsPerInstance; maxlabel++) {
                    userIndex = rand.nextInt(dataset.getUsers().size());
                    labelAssignments.add(new LabelAssignment(dataset.getInstances().get(i).getId(),
                                                             i+1, dataset.getUsers().get(userIndex).getId(),
                                                             new Date()));
                }
            }
            System.out.println(labelAssignments);

            for (LabelAssignment lAssignment : labelAssignments) { 
                System.out.println();
                System.out.println(String.format("Instance Id: %d", lAssignment.getInstanceId()));
                System.out.println(String.format("Assigned Label Id: %d", lAssignment.getAssignedLabelId()));
                System.out.println(String.format("User Id: %d", lAssignment.getUserId()));
                System.out.println("Date: " + lAssignment.getDate());
            }

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
