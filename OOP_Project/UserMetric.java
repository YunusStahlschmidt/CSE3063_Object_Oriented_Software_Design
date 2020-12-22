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
        userModel.setNumberOfDatasetsAssigned(labeledInstances.size());
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
}
