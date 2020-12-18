package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;

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
    private Integer totalNumberOfAssignedLabels;
    private Integer totalNumberOfUniqueAssignedLabels;
    private ArrayList<LabelAssignment> labelAssignments = new ArrayList<LabelAssignment>();
    private HashMap<Label, Double> classLabelsAndPercentages = new HashMap<Label,Double>();

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
        return totalNumberOfUniqueAssignedLabels;
    }

    public Integer getNumberOfUniqueUsers(){
        //implementation needed
        return 0;
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

    public void setEntropy(){
        //implementaion needed
    }

    public void setLabelAssignments(LabelAssignment newLabelAssignment) {
        this.labelAssignments.add(newLabelAssignment);
    }
}