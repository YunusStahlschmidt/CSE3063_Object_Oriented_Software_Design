package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import OOP_Project.MetricsJSONModels.UserModel;

public class UserMetric {
    @SerializedName("consistencyCheckProbability")
    @Expose
    private double consistencyCheckProbability;
    private int numberOfDatasetAssigned = 0;
    private UserModel userModel = new UserModel();
    private HashMap<Dataset, Double> datasetCompleteness = new HashMap<Dataset, Double>();
    private int numOfLabeledInstances = 0;
    private Set<Instance> uniqueLabeledInstances = new HashSet<Instance>();
    private double consistencyPercentage;
    private double averageTimeSpent;
    private ArrayList<Double> timeSpentPerInstance = new ArrayList<Double>();
    private double standardDeviation;

    public UserMetric() {
    }

    // Getters

    public double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }

    public int getNumberOfDatesetsAssigned() {
        return numberOfDatasetAssigned;
    }

    public int getNumberOfLabeledInstances() {
        return numOfLabeledInstances;
    }

    public double getConsistencyPercentage() {
        return consistencyPercentage;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public Set<Instance> getUniqueLabeledInstances() {
        return uniqueLabeledInstances;
    }

    public double getAverageTimeSpent() {
        return averageTimeSpent;
    }

    public ArrayList<Double> getTimeSpentPerInstance() {
        return timeSpentPerInstance;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public HashMap<Dataset, Double> getDatasetCompleteness() {
        return datasetCompleteness;
    }

    // Setters

    public void setConsistencyCheckProbability(double consistencyCheckProbability) {
        this.consistencyCheckProbability = consistencyCheckProbability;
    }

    public void incrementNumberOfDatasetsAssigned() {
        this.numberOfDatasetAssigned++;
    }

    public void setNumberOfLabeledInstances() {
        this.numOfLabeledInstances++;
    }

    public void setConsistencyPercentage() {
        // related calculations
    }

    public void setStandardDeviation(double numArray[]) {
        // use an array of all times spent on all instances , then pass them to this
        // function
        double sum = 0.0, std = 0.0;
        int length = numArray.length;

        for (double num : numArray) {
            sum += num;
        }

        double mean = sum / length;

        for (double num : numArray) {
            std += Math.pow(num - mean, 2);
        }

        this.standardDeviation = Math.sqrt(std / length);
        // related calculations

    }

    public void setAverageTimeSpent(double TimeSpent) {
        // in here we should take TimeSpent and add it to averageTimeSpent
        // formula should be (AverageTimeSpent * numberOfLabeledInstances + TimeSpent) /
        // (numberOfLabeledInstances + 1)
    }

    public void addUniqueLabeledInstances(Instance labeledInstance) {
        this.uniqueLabeledInstances.add(labeledInstance);
    }

    public void incrementDatasetCompleteness(Dataset dataset) {
        if (this.datasetCompleteness.get(dataset) == null) {
            this.datasetCompleteness.put(dataset, 0.0);
        } else {
            int sizeOfDataset = dataset.getInstances().size();
            Double currentValue = this.datasetCompleteness.get(dataset);

            this.datasetCompleteness.put(dataset, currentValue + 1 / sizeOfDataset);
        }
    }

}
