package OOP_Project;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Output {
    private long datasetId;
    private String datasetName;
    private long maximumNumberOfLabelsPerInstance;
    private JSONArray classLabels;
    private JSONArray instances;
    private JSONArray classLabelAssignments;
    private JSONArray users;

    Output(long datasetId, String datasetName, long maximumNumberOfLabelsPerInstance,
            JSONArray classLabels, JSONArray instances, JSONArray classLabelAssignments, JSONArray users){
        this.datasetId = datasetId;
        this.datasetName = datasetName;
        this.maximumNumberOfLabelsPerInstance = maximumNumberOfLabelsPerInstance;
        this.classLabels = classLabels;
        this.instances = instances;
        this.classLabelAssignments = classLabelAssignments;
        this.users = users;           
    }
}
