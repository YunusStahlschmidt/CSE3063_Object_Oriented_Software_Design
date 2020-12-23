/*
    - ConsistencyPercentage: NaN
    - consistencyCheckProb is not set
    - LabeledInstances size = 0 but numberOfLabeldInstances = 5  (totalNumberOfInstancesLabeld is null in model)
    - user id name and type are not set in model
*/

package OOP_Project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import OOP_Project.MetricsJSONModels.ListOfAllDatasetsWithTheirCompletenessPercentage;
import OOP_Project.MetricsJSONModels.UserModel;

public class UserMetric {
    private int numberOfDatasetAssigned = 0;
    private UserModel userModel = new UserModel();
    private HashMap<Dataset, Double> datasetCompleteness = new HashMap<Dataset, Double>();
    private int numOfLabeledInstances = 0;
    private Set<Instance> uniqueLabeledInstances = new HashSet<Instance>();
    private HashMap<Instance, ArrayList<LabelAssignment>> labeledInstances = new HashMap<>(); // ??
    private double consistencyPercentage = 0.0;
    private double averageTimeSpent = 0.0;
    private ArrayList<Double> timeSpentPerInstance = new ArrayList<Double>();
    private double standardDeviation = 0.0;

    public UserMetric() {
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

    public ArrayList<Double> getTimeSpentPerInstance() {
        return timeSpentPerInstance;
    }

    public HashMap<Dataset, Double> getDatasetCompleteness() {
        return datasetCompleteness;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    // Setters
    public void addLabeledInstances(Instance instance, LabelAssignment lAssignment) {
        ArrayList<LabelAssignment> listOfAssignment;
        if (!labeledInstances.containsKey(instance)) {
            listOfAssignment = new ArrayList<LabelAssignment>();
            listOfAssignment.add(lAssignment);
        } else {
            listOfAssignment = labeledInstances.get(instance);
            listOfAssignment.add(lAssignment);
        }
    }

    public void addTimeSpentPerInstance(Double timeSpent) {
        this.timeSpentPerInstance.add(timeSpent);
    }

    public void incrementNumberOfDatasetsAssigned() {
        this.numberOfDatasetAssigned++;
        userModel.setNumberOfDatasetsAssigned(this.numberOfDatasetAssigned);
    }

    public void incrementNumberOfLabeledInstances() {
        this.numOfLabeledInstances++;
        //userModel.setTotalNumberOfInstancesLabeled(labeledInstances.size());
        userModel.setTotalNumberOfInstancesLabeled(this.numOfLabeledInstances);
    }

    // HashMap<Instance, ArrayList<LabelAssignment>>
    public void setConsistencyPercentage() {
        // related calculations
        Integer total = 0, consistent = 0;
        for (ArrayList<LabelAssignment> listOfAssignment : labeledInstances.values()) {
            Integer i, j;
            if (listOfAssignment.size() > 1) {
                for (i = 0; i < listOfAssignment.size(); i++) {
                    for (j = i + 1; j < listOfAssignment.size(); j++) {
                        total++;
                        Collections.sort(listOfAssignment.get(i).getAssignedLabelId());
                        Collections.sort(listOfAssignment.get(j).getAssignedLabelId());
                        if (listOfAssignment.get(i).getAssignedLabelId()
                                .equals(listOfAssignment.get(j).getAssignedLabelId())) {
                            consistent++;
                        }
                    }
                }
            }
        }
        consistencyPercentage = consistent / Double.valueOf(total);
        if (total == 0) {
            consistencyPercentage = 0.0;
        }
        userModel.setConsistencyPercentage(consistencyPercentage);
    }

    public void setStandardDeviation() {
        Double sumOfDeviations = 0.0, deviationPart = 0.0;
        for (Double timeSpent : this.timeSpentPerInstance) {
            deviationPart = timeSpent - this.averageTimeSpent;
            sumOfDeviations += Math.pow(deviationPart, 2);
        }
        standardDeviation = Math.sqrt(sumOfDeviations / timeSpentPerInstance.size());
        userModel.setStdDevOfTimeSpentInLabelingAnInstanceInSeconds(standardDeviation);
    }

    public void setAverageTimeSpent(Double timeSpent) {
        // for (int i= 0; i<timeSpentPerInstance.size();i++)
        // sumOfTimeSpent += timeSpentPerInstance.get(i);
        // averageTimeSpent = sumOfTimeSpent/timeSpentPerInstance.size();
        this.averageTimeSpent = (averageTimeSpent * numOfLabeledInstances + timeSpent) / (numOfLabeledInstances + 1);
        // TBD +1 depends on the execution order
        this.addTimeSpentPerInstance(timeSpent);
        this.setStandardDeviation();
        userModel.setAverageTimeSpentInLabelingAnInstanceInSeconds(this.averageTimeSpent);
    }

    public void addUniqueLabeledInstances(Instance labeledInstance) {
        this.uniqueLabeledInstances.add(labeledInstance);
        userModel.setTotalNumberOfUniqueInstancesLabeled(this.uniqueLabeledInstances.size());
    }

    public void incrementDatasetCompleteness(Dataset dataset) { // call when new unique labelAssignment is done
        ArrayList<ListOfAllDatasetsWithTheirCompletenessPercentage> datasetsWithCompleteness = new ArrayList<>();
        ListOfAllDatasetsWithTheirCompletenessPercentage aCompletenessOfDataset;
        if (this.datasetCompleteness.get(dataset) == null) {
            this.datasetCompleteness.put(dataset, 0.0);
        } else {
            Integer sizeOfDataset = dataset.getInstances().size();
            this.datasetCompleteness.put(dataset, this.uniqueLabeledInstances.size() / (double) sizeOfDataset);
        }
        for (Dataset currentDataset : this.datasetCompleteness.keySet()) {
            aCompletenessOfDataset = new ListOfAllDatasetsWithTheirCompletenessPercentage();
            aCompletenessOfDataset.setDatasetId(currentDataset.getDatasetId());
            aCompletenessOfDataset.setCompletenessPercentage(this.datasetCompleteness.get(currentDataset));

            datasetsWithCompleteness.add(aCompletenessOfDataset);
        }
        userModel.setListOfAllDatasetsWithTheirCompletenessPercentage(datasetsWithCompleteness);
    }

    public void callAllNecessaryMethods(Instance anInstance, Dataset dataset, LabelAssignment newLabelAssignment,
            Date startDate, Date endDate) {
        this.addLabeledInstances(anInstance, newLabelAssignment);
        // updating User Metrices
        // User metric 1: Number of datasets assigned already done in parser class
        this.addUniqueLabeledInstances(anInstance); // User Metric -4
        // List of all datasets with their completeness percentage setting with
        // incrementDatasetCompleteness
        this.incrementDatasetCompleteness(dataset);// User Metric - 2
        this.incrementNumberOfLabeledInstances();// User Metric - 3 
        this.setConsistencyPercentage(); // User Metric - 5
        // STD DEV called inside the setAverageTimeSpent func Metric - 6 - 7
        if (startDate != null && endDate != null) {
            this.setAverageTimeSpent(((endDate.getTime() - startDate.getTime()) / (double) 1000));
        }
    }

    /*"total number of instances labeled": 0,
      "total number of unique instances labeled": 5,
      "consistency percentage": 0.0,
      "Average time spent in labeling an instance in seconds": 0.0,
      "Std. dev. of time spent in labeling an instance in seconds": 4.803844614152614E-4 */
    public void setInitialUserModel () {
        userModel.setTotalNumberOfInstancesLabeled(0);
        userModel.setTotalNumberOfUniqueInstancesLabeled(0);
        userModel.setConsistencyPercentage(0.0);
        userModel.setAverageTimeSpentInLabelingAnInstanceInSeconds(0.0);
        userModel.setStdDevOfTimeSpentInLabelingAnInstanceInSeconds(0.0);
    }
}
