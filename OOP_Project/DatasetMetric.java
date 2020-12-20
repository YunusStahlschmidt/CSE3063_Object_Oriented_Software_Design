package OOP_Project;

import java.util.HashMap;
import java.util.HashSet;

public class DatasetMetric {
    private Double completenessPercentage;
    private HashMap classDistributions = new HashMap<>();
    private HashMap<Label, HashSet> uniqueInstancesForLabel = new HashMap<Label, HashSet>();
    private Integer numberOfAssignedUsers;
    private HashMap<User, Double> assignedUsersCompleteness = new HashMap<>();
    private HashMap<User, Double> assignedUsersConsistency = new HashMap<>();

    public DatasetMetric() {
    }
    //
    //
    // tbd some of these may require computations before returning
    // public Double getCompletenessPercentage() {
    // return completenessPercentage;
    //

    public HashMap getClassDistributions() {
        return classDistributions;
    }

    public HashMap getUniqueInstancesForLabel() {
        return uniqueInstancesForLabel;
    }

    public Integer getNumberOfAssignedUsers() {
        return numberOfAssignedUsers;
    }

    public HashMap getAssignedUsersCompleteness() {
        return assignedUsersCompleteness;
    }

    public HashMap getAssignedUsersConsistency() {
        return assignedUsersConsistency;
    }

    /* Setters */
    // tbd some of these may require calculations or shoud be changed to add instead
    // of set
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

    public void calculateDatasetCompleteness() {
        // implementation needed
    }

    public void calculateClassDistribution() {
        // implementation needed
    }

    public void addInstance(Label label, Instance instance) {
        /*
         * Method for uniqueInstancesForLabel. If there is no such a label create one
         * with HashSet. If there is, associate instance to corresponding label
         */
    }

    public void calculateUserCompleteness() {
        /*
         * this method will calculate user completeness based on the formula of total
         * number of labeled instances for each user divided by total number of
         * instances
         */
    }

    public void calculateAssignedUsersAndConcistencyPercentage() {
        /** Method to find every assgnd users and their concistency percentage */
    }

}
