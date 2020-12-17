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
    private double entropy;
    private Label mostFrequentClassLabel;
    private double percentageOfMostFrequentLabel;
    private int totalNumberOfAssignedLabels;
    private int totalNumberOfUniqueAssignedLabels;
    private ArrayList<String[]> labelAssignments = new ArrayList<String[]>();
    private HashMap<Label, Double> classLabelsAndPercentages = new HashMap<Label,Double>();

    public Instance() {}
    public Instance(int id, String instance) {
        this.id = id;
        this.instance = instance;
    }


    // Getters

    public int getId() {
        return id;
    }

    public String getInstance() {
        return instance;
    }

    public double getEntropy(){
        return entropy;
    }

    public int getTotalNumberOfAssignedLabels(){
        return totalNumberOfAssignedLabels;
    }

    public int getNumberOfUniqueAssignedLabels(){
        return totalNumberOfUniqueAssignedLabels;
    }

    public int getNumberOfUniqueUsers(){
        //implementation needed
        return 0;
    }

    public Label getMostFrequentLabel(){
        return mostFrequentClassLabel;
    }

    public double getMostFrequentLabelPercentage(){
        //implementation needed
        return 0;
    }

    public HashMap getClassLabelsAndPercentages(){
        return classLabelsAndPercentages;
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
}