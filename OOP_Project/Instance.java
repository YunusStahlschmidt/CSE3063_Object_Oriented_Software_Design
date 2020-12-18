package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instance")
    @Expose
    private String instance;
    private Double entropy;
    private Label mostFrequentClassLabel;
    private Double percentageOfMostFrequentLabel;
    private Integer totalNumberOfAssignedLabels = 0;
    private Integer totalNumberOfUniqueAssignedLabels = 0;
    private ArrayList<LabelAssignment> labelAssignments = new ArrayList<LabelAssignment>();
    private HashMap<Label, Double> classLabelsAndPercentages = new HashMap<Label,Double>();
    private HashMap<Label, Integer> uniqueLabels = new HashMap<Label,Integer>();
    private Set<User> uniqueUsers = new HashSet<User>();
    
    public Instance() {}
    public Instance(Integer id, String instance) {
        this.id = id;
        this.instance = instance;
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getInstance() {
        return instance;
    }

    public Double getEntropy(){
        return entropy;
    }

    public Integer getTotalNumberOfAssignedLabels(){
        return totalNumberOfAssignedLabels;
    }

    public Integer getNumberOfUniqueAssignedLabels(){
        return uniqueLabels.size();
    }

    public Integer getNumberOfUniqueUsers(){
        return uniqueUsers.size();
    }

    public Label getMostFrequentLabel(){
        return mostFrequentClassLabel;
    }

    public Double getMostFrequentLabelPercentage(){
        //implementation needed
        return 0.0;
    }

    public HashMap getClassLabelsAndPercentages(){
        return classLabelsAndPercentages;
    }

    public Integer getLabelAssignments() {
        return labelAssignments.size();
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setClassLabelsAndPercentages(){
        //implementaion needed
    }

    public void setPercentageOfMostFrequentLabel(){
        //implementaion needed
    }
    /*New added*/ 
    public void setMostFrequentLabel(){
        //implementaion needed
    }

    public void setEntropy(){
        //implementaion needed
    }

    public void incrementTotalNumberOfAssignedLabels() {
        this.totalNumberOfAssignedLabels++;
        
    }

    public void setLabelAssignments(LabelAssignment newLabelAssignment) {
        this.labelAssignments.add(newLabelAssignment);
    }

    public void setNumberOfUniqueAssignedLabels(){
        //go over labelAssignments array and find and set totalNumberOfUniqueAssignedLabels 
    }

    public void setNumberOfUniqueUsers(){
        //get size of hashset userList
    }

    public void addUniqueUser(User user){
        uniqueUsers.add(user);
    }

    public void addUniqueLabel(Label label){
        if (uniqueLabels.get(label) == null) {
            uniqueLabels.put(label, 1);
        } else {
            uniqueLabels.put(label, uniqueLabels.get(label)+1);
        }
    }
}