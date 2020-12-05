package OOP_Project;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONSerializer {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public JSONSerializer() {
    }

    public void serializeJSONFile(String outputPath, Dataset dataset, ArrayList<LabelAssignment> lAssignments,
            ArrayList<User> users) throws Exception {

        String pattern = "dd/M/yyyy, hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Map hashMap;

        // Building Skeleton of JSON file
        JSONArray classLabels = new JSONArray();
        JSONArray instances = new JSONArray();
        JSONArray classLabelAssignments = new JSONArray();
        JSONArray usersArray = new JSONArray();

        for (Label label : dataset.getLabels()) {
            hashMap = new LinkedHashMap(2);
            hashMap.put("label id", label.getId());
            hashMap.put("label text", label.getName());
            classLabels.add(hashMap);
        }

        for (Instance instance : dataset.getInstances()) {
            hashMap = new LinkedHashMap(2);
            hashMap.put("id", instance.getId());
            hashMap.put("instance", instance.getText());
            instances.add(hashMap);
        }

        for (LabelAssignment assigned : lAssignments) {
            hashMap = new LinkedHashMap(4);
            hashMap.put("instance id", assigned.getInstanceId());
            hashMap.put("class label ids", assigned.getAssignedLabelId());
            hashMap.put("user id", assigned.getUserId());
            hashMap.put("datetime", simpleDateFormat.format(assigned.getDate()));
            classLabelAssignments.add(hashMap);
        }

        for (User user : users) {
            hashMap = new LinkedHashMap(3);
            hashMap.put("user id", user.getId());
            hashMap.put("user name", user.getName());
            hashMap.put("user type", user.getType());
            usersArray.add(hashMap);
        }

        Output myOutput = new Output(dataset.getId(), dataset.getName(), dataset.getMaxLabel(), classLabels, instances,
                classLabelAssignments, usersArray);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(myOutput);
        // System.out.println(json);

        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter(outputPath);
        logger.info("Output file was created successfully");

        pw.write(json);

        pw.flush();
        pw.close();

    }

}