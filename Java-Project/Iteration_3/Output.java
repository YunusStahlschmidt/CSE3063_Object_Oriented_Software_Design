package Iteration_3;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Output {
    @SerializedName("datasetId")
    @Expose
    private Integer datasetId;
    @SerializedName("datasetName")
    @Expose
    private String datasetName;
    @SerializedName("maximumNumberOfLabelsPerInstance")
    @Expose
    private Integer maximumNumberOfLabelsPerInstance;
    @SerializedName("classLabels")
    @Expose
    private List<Label> classLabels;
    @SerializedName("instances")
    @Expose
    private List<Instance> instances;
    @SerializedName("classLabelAssignments")
    @Expose
    private ArrayList<LabelAssignment> classLabelAssignments;
    @SerializedName("users")
    @Expose
    private ArrayList<User> users;

    public Output(Integer datasetId, String datasetName, Integer maximumNumberOfLabelsPerInstance,
            List<Label> classLabels, List<Instance> instances, ArrayList<LabelAssignment> classLabelAssignments,
            ArrayList<User> users) {
        this.datasetId = datasetId;
        this.datasetName = datasetName;
        this.maximumNumberOfLabelsPerInstance = maximumNumberOfLabelsPerInstance;
        this.classLabels = classLabels;
        this.instances = instances;
        this.classLabelAssignments = classLabelAssignments;
        this.users = users;
    }

    public ArrayList<LabelAssignment> getClassLabelAssignments() {
        return this.classLabelAssignments;
    }
}
