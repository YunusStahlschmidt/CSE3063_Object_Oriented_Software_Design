package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;

/*
Class for the user object to store them in our dictionary in the main class
We will get the users from a config file (in json format)
We will also use that dictionary for our final output
*/

public class User {
    private long id;
    private String name;
    private String type;
    private int numberOfDatasetAssigned = 0;
    private HashMap<Dataset, Double> datasetCompleteness=new HashMap<Dataset, Double>();
    private int numOfLabeledInstances = 0;
    private HashMap<String, Integer> uniqueLabeledInstances =  new HashMap<String, Integer>();
    private double consistencyPercentage;
    private double averageTimeSpent;
    private ArrayList<Double> timeSpentPerInstance = new ArrayList<Double>();
    private double standarDeviation;


    public User() {}
    public User(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }


    //Getters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getNumberOfDatesetsAssigned(){
        return numberOfDatasetAssigned;
    }
    
    public int getNumberOfLabeledInstances(){
        return numOfLabeledInstances;
    }

    public double getConsistencyPercentage(){
        return consistencyPercentage;
    }

    public int getNumberOfUniqueLabeledInstances(){
        //implementation needed
        return 0;
    }

    public double getAverageTimeSpent(){
        return averageTimeSpent;
    }

    public ArrayList<Double> getTimeSpentPerInstance(){
        return timeSpentPerInstance;
    }

    public double getStandardDeviation(){
        return standarDeviation;
    }

    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumberOfDatasetsAssigned(int length){
        this.numberOfDatasetAssigned = length;
    }

    public void setNumberOfLabeledInstances(){
        //related calculations
    }

    public void setConsistencyPercentage(){
        //related calculations
    }

    public void setStandardDeviation(){
        //related calculations
    }
}