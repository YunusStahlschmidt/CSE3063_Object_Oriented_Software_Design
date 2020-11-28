package OOP_Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/* 
This class will where al "computations" will take place
*/

public class Main {
    private Dataset dataset;
    private ArrayList<LabelAssignment> labelAssignments;
    private Dictionary<String, User> usersDict;

    public static void main(String[] args) {

        // File file = new File("C:\\demo\\demofile.txt");
        String jsonString = "C:\\Users\\huzey\\Desktop\\Personal files (important)\\Github Repositories\\CSE3063F20P1_GRP27\\OOP_Project\\CES3063F20_LabelingProject_Input-1.json"; // assign your JSON String here
        JSONParser jsonParser = new JSONParser();
        Dataset dataset = new Dataset();
        try {
            FileReader reader = new FileReader(jsonString);
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            // Parsing
            JSONArray classLables = (JSONArray) jsonObject.get("class labels");
            JSONArray instances = (JSONArray) jsonObject.get("instances");
            Object datasetId = jsonObject.get("dataset id");
            Object datasetName = jsonObject.get("dataset name");
            Object maxNumberOfLabelsPerInstance = jsonObject.get("maximum number of labels per instance");

            System.out.println(classLables);
            System.out.println();
            System.out.println(instances);
            System.out.println();
            System.out.println(datasetId);
            System.out.println();
            System.out.println(datasetName);
            System.out.println();
            System.out.println(maxNumberOfLabelsPerInstance);

            dataset.setId(datasetId.toString());
            dataset.setName(datasetName.toString());
            dataset.setMaxLabel(maxNumberOfLabelsPerInstance.toString());

            
            // for (int idx = 0; idx < classLables.size(); idx++) {
            //     for (int idx2 = 0; idx2 < classLables.get; )
            //     Object dict_ = classLables.get(idx);
            //     System.out.println(dict_);
            // }

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    // Getters

    public Dataset getDataset() {
        return dataset;
    }

    // Setters

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
