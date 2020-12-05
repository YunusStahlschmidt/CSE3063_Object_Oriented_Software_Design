package Iteration_1;

import org.json.simple.JSONArray;

public class Output {
    private long datasetId;
    private String datasetName;
    private long maximumNumberOfLabelsPerInstance;
    private JSONArray classLabels;
    private JSONArray instances;
    private JSONArray classLabelAssignments;
    private JSONArray users;

    public Output(long datasetId, String datasetName, long maximumNumberOfLabelsPerInstance,
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
