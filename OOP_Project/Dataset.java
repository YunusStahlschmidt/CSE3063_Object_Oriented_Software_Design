package OOP_Project;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/*
This class should parse all the information from the dataset so we can 
process them in our main function
We will get the data from the input json files which we have to parse in this class
*/

public class Dataset {
    @SerializedName("dataset id")
    @Expose
    private Integer datasetId;
    @SerializedName("dataset name")
    @Expose
    private String datasetName;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("assignedUserIds")
    @Expose
    private List<Integer> assignedUserIds = null;
    @SerializedName("maximum number of labels per instance")
    @Expose
    private Integer maxLabel;
    @SerializedName("class labels")
    @Expose
    private List<Label> labels = null;
    @SerializedName("instances")
    @Expose
    private List<Instance> instances = null;
    private double completenessPercentage;
    private HashMap classDistributions = new HashMap<>();
    private HashMap uniqueInstancesForLabel = new HashMap<>();
    private int numberOfAssignedUsers;
    private HashMap assignedUsersCompleteness = new HashMap<>();
    private HashMap assignedUsersConsistency = new HashMap<>();

    public Dataset() {}
    public Dataset(int id, String name, int maxLabel) {
        this.datasetId = id;
        this.datasetName = name;
        this.maxLabel = maxLabel;
    }
    // Getters 

    public Integer getDatasetId() {
        return datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getPath() {
        return path;
    }

    public List<Integer> getAssignedUserIds() {
        return assignedUserIds;
    }
//----------------------Datset Model--------------------------------


    public int getMaxLabel() {
     return maxLabel;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    // tbd some of these may require computations before returning
    public double getCompletenessPercentage() {
        return completenessPercentage;
    }

    public HashMap getClassDistributions() {
        return classDistributions;
    }

    public HashMap getUniqueInstancesForLabel() {
        return uniqueInstancesForLabel;
    }

    public int getNumberOfAssignedUsers() {
        return numberOfAssignedUsers;
    }

    public HashMap getAssignedUsersCompleteness() {
        return assignedUsersCompleteness;
    }

    public HashMap getAssignedUsersConsistency() {
        return assignedUsersConsistency;
    }


    // Setters

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAssignedUserIds(List<Integer> assignedUserIds) {
        this.assignedUserIds = assignedUserIds;
    }

    public void setMaxLabel(int newMaxLabel) {
     this.maxLabel = newMaxLabel;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    // tbd some of these may require calculations or shoud be changed to add instead of set
    public void setCompletenessPercentage(double completenessPercentage) {
        this.completenessPercentage = completenessPercentage;
    }

    public void setClassDistributions(HashMap classDistributions) {
        this.classDistributions = classDistributions;
    }

    public void setUniqueInstancesForLabel(HashMap uniqueInstancesForLabel) {
        this.uniqueInstancesForLabel = uniqueInstancesForLabel;
    }

    public void setNumberOfAssignedUsers(int numberOfAssignedUsers) {
        this.numberOfAssignedUsers = numberOfAssignedUsers;
    }

    public void setAssignedUsersCompleteness(HashMap assignedUsersCompleteness) {
        this.assignedUsersCompleteness = assignedUsersCompleteness;
    }

    public void setAssignedUsersConsistency(HashMap assignedUsersConsistency) {
        this.assignedUsersConsistency = assignedUsersConsistency;
    }
} 