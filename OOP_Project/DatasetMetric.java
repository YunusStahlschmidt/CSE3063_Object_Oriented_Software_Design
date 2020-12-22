/*
    - datasetCompleteness = 0
    - classDistributions = empty
    
*/

package OOP_Project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import OOP_Project.MetricsJSONModels.DatasetModel;
import OOP_Project.MetricsJSONModels.ListNumberOfUniqueInstancesForEachClassLabel;
import OOP_Project.MetricsJSONModels.ClassDistributionBasedOnFinalInstanceLabel;
import OOP_Project.MetricsJSONModels.ListOfUsersAssignedAndTheirConsistencyPercentage;
import OOP_Project.MetricsJSONModels.ListOfUsersAssignedAndTheirCompletenessPercentage;

public class DatasetMetric {
    private HashMap<Instance, Label> classDistributions = new HashMap<>();
    private HashMap<Label, HashSet<Instance>> uniqueInstancesForLabel = new HashMap<Label, HashSet<Instance>>();

    private Set<Instance> uniqueLabeledInstances = new HashSet<Instance>();
    private DatasetModel datasetModel = new DatasetModel();

    public DatasetMetric() {
    }

    public void addUniqueLabeledInstances(Instance instance) {
        this.uniqueLabeledInstances.add(instance);
    }

    public void setNumberOfAssignedUsers(Integer numberOfAssignedUsers) {
        this.datasetModel.setNumberOfUsersAssignedToThisDataset(numberOfAssignedUsers);
    }

    public void calculateDatasetCompleteness(Integer numberOfInstances) {

        this.datasetModel.setCompletenessPercentage(this.uniqueLabeledInstances.size() / (double) numberOfInstances);
    }

    public void calculateClassDistribution() {
        ArrayList<ClassDistributionBasedOnFinalInstanceLabel> classDistributionsForDatasetModel = new ArrayList<>();
        ClassDistributionBasedOnFinalInstanceLabel aPercentageOfLabel;
        Integer maxCount, currentCount;
        Label maxLabel;
        HashMap<Label, Integer> labelCountsForAllInstances = new HashMap<>();
        for (Instance instance : uniqueLabeledInstances) {
            maxCount = 0;
            maxLabel = null;
            for (Label label : instance.getInstanceMetric().getUniqueLabels().keySet()) {
                currentCount = instance.getInstanceMetric().getUniqueLabels().get(label);
                if (currentCount > maxCount) {
                    maxLabel = label;
                    maxCount = currentCount;
                }
            }
            classDistributions.put(instance, maxLabel);
        }

        for (Label label : classDistributions.values()) {
            if (labelCountsForAllInstances.get(label) == null) {
                labelCountsForAllInstances.put(label, 1);
            } else {
                labelCountsForAllInstances.put(label, labelCountsForAllInstances.get(label) + 1);
            }
        }

        for (Label label : labelCountsForAllInstances.keySet()) {
            aPercentageOfLabel = new ClassDistributionBasedOnFinalInstanceLabel();
            aPercentageOfLabel.setLabel(label.getLabelText());
            aPercentageOfLabel
                    .setDistribution(labelCountsForAllInstances.get(label) / (double) uniqueLabeledInstances.size());

            classDistributionsForDatasetModel.add(aPercentageOfLabel);
        }
        this.datasetModel.setClassDistributionBasedOnFinalInstanceLabels(classDistributionsForDatasetModel);

    }

    public void addInstanceForLabel(Label label, Instance instance) {
        ArrayList<ListNumberOfUniqueInstancesForEachClassLabel> listOfUniqueInstancesForLabel = new ArrayList<>();
        ListNumberOfUniqueInstancesForEachClassLabel NoOfUniqueInstancesForALabel;
        if (this.uniqueInstancesForLabel.get(label) == null) {
            this.uniqueInstancesForLabel.put(label, new HashSet<Instance>());
            this.uniqueInstancesForLabel.get(label).add(instance);
        } else {
            this.uniqueInstancesForLabel.get(label).add(instance);
        }

        for (Label aLabel : uniqueInstancesForLabel.keySet()) {
            NoOfUniqueInstancesForALabel = new ListNumberOfUniqueInstancesForEachClassLabel();
            NoOfUniqueInstancesForALabel.setLabel(aLabel.getLabelText());
            NoOfUniqueInstancesForALabel.setAmount(uniqueInstancesForLabel.get(aLabel).size());
        }
        this.datasetModel.setListNumberOfUniqueInstancesForEachClassLabel(listOfUniqueInstancesForLabel);
    }

    public void calculateUserCompleteness(Dataset currentDataset, ArrayList<User> assignedUsers) {
        ArrayList<ListOfUsersAssignedAndTheirCompletenessPercentage> assignedUsersCompleteness = new ArrayList<>();
        ListOfUsersAssignedAndTheirCompletenessPercentage anAssignedUserCompleteness;
        for (User user : assignedUsers) {
            for (Dataset datasetInUserMetric : user.getUserMetric().getDatasetCompleteness().keySet()) {
                if (datasetInUserMetric == currentDataset) {
                    anAssignedUserCompleteness = new ListOfUsersAssignedAndTheirCompletenessPercentage();
                    anAssignedUserCompleteness.setUserId(user.getId());
                    anAssignedUserCompleteness.setCompletenessPercentage(
                            user.getUserMetric().getDatasetCompleteness().get(currentDataset));

                    assignedUsersCompleteness.add(anAssignedUserCompleteness);
                }
            }
        }
        this.datasetModel.setListOfUsersAssignedAndTheirCompletenessPercentage(assignedUsersCompleteness);
    }

    public void calculateAssignedUsersAndConcistencyPercentage(ArrayList<User> assignedUsers) {
        ArrayList<ListOfUsersAssignedAndTheirConsistencyPercentage> assignedUsersConsistency = new ArrayList<>();
        ListOfUsersAssignedAndTheirConsistencyPercentage consistencyPercentageOfaUser;
        for (User user : assignedUsers) {
            consistencyPercentageOfaUser = new ListOfUsersAssignedAndTheirConsistencyPercentage();
            consistencyPercentageOfaUser.setUserId(user.getId());
            consistencyPercentageOfaUser.setConsistencyPercentage(user.getUserMetric().getConsistencyPercentage());

            assignedUsersConsistency.add(consistencyPercentageOfaUser);
        }
        this.datasetModel.setListOfUsersAssignedAndTheirConsistencyPercentage(assignedUsersConsistency);
    }
}
