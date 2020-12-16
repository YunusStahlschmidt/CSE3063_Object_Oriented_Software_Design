package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    private long id;
    private String text;
    private double entropy;
    private Label mostFrequentClassLabel;
    private double percentageOfMostFrequentLabel;
    private int totalNumberOfAssignedLabels;
    private int totalNumberOfUniqueAssignedLabels;
    private ArrayList<String[]> labelAssignments = new ArrayList<String[]>();
    private HashMap<Label, Double> classLabelsAndPercentages = new HashMap<Label,Double>();

    public Instance() {}
    public Instance(long id, String text) {
        this.id = id;
        this.text = text;
    }


    // Getters

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
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