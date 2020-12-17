package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Class for the user object to store them in our dictionary in the main class
We will get the users from a config file (in json format)
We will also use that dictionary for our final output
*/

public class User {
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("consistencyCheckProbability")
    @Expose
    private double consistencyCheckProbability;
    private int numberOfDatasetAssigned = 0;
    private HashMap<Dataset, Double> datasetCompleteness=new HashMap<Dataset, Double>();
    private int numOfLabeledInstances = 0;
    private HashMap<String, Integer> uniqueLabeledInstances =  new HashMap<String, Integer>();
    private double consistencyPercentage;
    private double averageTimeSpent;
    private ArrayList<Double> timeSpentPerInstance = new ArrayList<Double>();
    private double standarDeviation;


    public User() {}
    public User(int id, String name, String type) {
        this.userId = id;
        this.userName = name;
        this.userType = type;
    }


    //Getters

    public Integer getId() {
        return userId;
    }
    
    public String getName() {
        return userName;
    }

    public String getType() {
        return userType;
    }

    public double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setConsistencyCheckProbability(double consistencyCheckProbability) {
        this.consistencyCheckProbability = consistencyCheckProbability;
    }

    public void incrementNumberOfDatasetsAssigned(){
        this.numberOfDatasetAssigned++;
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