
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import OOP_Project.MetricsJSONModels.UserModel;

public class UserMetric {
    // @SerializedName("consistencyCheckProbability")
    // @Expose
    // private double consistencyCheckProbability;
     private int numberOfDatasetAssigned = 0;
    private UserModel userModel = new UserModel();
    private HashMap<Dataset, Double> datasetCompleteness = new HashMap<Dataset, Double>();
    private int numOfLabeledInstances = 0;
    private int uniqueLabeledInstances = 0;
    // private double consistencyPercentage;
    private double averageTimeSpent;
    private ArrayList<Double> timeSpentPerInstance = new ArrayList<Double>();
    private double standarDeviation;
    private double sumOfTimeSpent =0;
    private double avgTimeSpentForInstances;
    public UserMetric() {}
    
    // Getters 

    // public double getConsistencyCheckProbability() {
    //     return consistencyCheckProbability;
    // }

     public int getNumberOfDatesetsAssigned(){
         return numberOfDatasetAssigned;
     }
    
     public int getNumberOfLabeledInstances(){
         return numOfLabeledInstances;
     }

    // public double getConsistencyPercentage(){
    //     return consistencyPercentage;
    // }
    public UserModel getUserModel() {
        return userModel;
    }
    public int getUniqueLabeledInstances() {
        return uniqueLabeledInstances;
    }

    // public double getAverageTimeSpent(){
    //     return averageTimeSpent;
    // }

    public ArrayList<Double> getTimeSpentPerInstance(){
        return timeSpentPerInstance;
    }

     public double getStandardDeviation(){
         return standarDeviation;
     }
    
    public HashMap<Dataset, Double> getDatasetCompleteness() {
        return datasetCompleteness;
    }
    
    // Setters

    
    public void setConsistencyCheckProbability(double consistencyCheckProbability) {
        // this.consistencyCheckProbability = consistencyCheckProbability;
    }

    public void incrementNumberOfDatasetsAssigned(){
         this.numberOfDatasetAssigned++;
    }

    public void incrementNumberOfLabeledInstances(){
        this.numOfLabeledInstances++;
    }

    public void setConsistencyPercentage(){
        //related calculations
    }

    public void setStandardDeviation(){
        standarDeviation = Math.sqrt(Math.pow(sumOfTimeSpent,2)/timeSpentPerInstance.size());
    }

    public void setAverageTimeSpent(double TimeSpent) {

        for (int i= 0; i<timeSpentPerInstance.size();i++)
            sumOfTimeSpent +=timeSpentPerInstance.get(i) ;
        averageTimeSpent= sumOfTimeSpent/timeSpentPerInstance.size();
        avgTimeSpentForInstances = (averageTimeSpent * numOfLabeledInstances + TimeSpent) / (numOfLabeledInstances + 1);
        //TBD +1 depends on the execution order
    }

    public void incrementUniqueLabeledInstances() {
        this.uniqueLabeledInstances++;
    }

    public void incrementDatasetCompleteness(Dataset dataset) {
        if (this.datasetCompleteness.get(dataset) == null) {
            this.datasetCompleteness.put(dataset, 0.0);
        } else {
            int sizeOfDataset = dataset.getInstances().size();
            Double currentValue = this.datasetCompleteness.get(dataset);

            this.datasetCompleteness.put(dataset, currentValue + 1/sizeOfDataset);
        }
    }
//    public void updateUserMetrics(){

//    }
}
