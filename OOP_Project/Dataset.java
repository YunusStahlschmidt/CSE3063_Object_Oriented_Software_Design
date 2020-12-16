package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;

/*
This class should parse all the information from the dataset so we can 
process them in our main function
We will get the data from the input json files which we have to parse in this class
*/

public class Dataset {
    private long id;
    private String name;
    private long maxLabel;
    private ArrayList<Label> labels = new ArrayList<Label>();
    private ArrayList<Instance> instances = new ArrayList<Instance>();
    private double completenessPercentage;
    private HashMap classDistributions = new HashMap<>();
    private HashMap uniqueInstancesForLabel = new HashMap<>();
    private int numberOfAssignedUsers;
    private HashMap assignedUsersCompleteness = new HashMap<>();
    private HashMap assignedUsersConsistency = new HashMap<>();

    public Dataset() {}
    public Dataset(long id, String name, long maxLabel) {
        this.id = id;
        this.name = name;
        this.maxLabel = maxLabel;
    }
    // Getters 

    public long getId() {
     return id;
    }

    public String getName() {
     return name;
    }

    public long getMaxLabel() {
     return maxLabel;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<Instance> getInstances() {
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

    public void setId(long newId) {
     this.id = newId;
    }

    public void setName(String newName) {
     this.name = newName;
    }

    public void setMaxLabel(long newMaxLabel) {
     this.maxLabel = newMaxLabel;
    }

    public void addLabel(long id, String name) {
        this.labels.add(new Label(id, name));
    }

    public void addInstance(long id, String text) {
        this.instances.add(new Instance(id, text));
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