package Iteration_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Iteration_3.MetricsJSONModels.InstanceModel;
import Iteration_3.MetricsJSONModels.ListClassLabelsAndPercentage;

public class InstanceMetric {
    private ArrayList<LabelAssignment> labelAssignments = new ArrayList<LabelAssignment>();
    private HashMap<Label, Double> classLabelsAndPercentages = new HashMap<Label, Double>();
    private HashMap<Label, Integer> uniqueLabels = new HashMap<Label, Integer>();
    private Set<User> uniqueUsers = new HashSet<User>();
    private InstanceModel instanceModel = new InstanceModel();

    public InstanceMetric() {
    }

    public void setInitialInstanceModel() {
        instanceModel.setTotalNumberOfLabelAssignments(0);
        List<ListClassLabelsAndPercentage> classLabelList = new ArrayList<>();
        instanceModel.setMostFrequentClassLabelAndPercentage(classLabelList);
        instanceModel.setListClassLabelsAndPercentages(classLabelList);
        instanceModel.setNumberOfUniqueLabelAssignments(0);
        instanceModel.setEntropy(0.0);
    }

    public InstanceModel getInstanceModel() {
        return instanceModel;
    }

    public HashMap<Label, Double> getClassLabelsAndPercentages() {
        return classLabelsAndPercentages;
    }

    public Integer getLabelAssignments() {
        return labelAssignments.size();
    }

    public HashMap<Label, Integer> getUniqueLabels() {
        return uniqueLabels;
    }

    public Set<User> getUniqueUsers() {
        return uniqueUsers;
    }

    public void setInstanceModel(InstanceModel instanceModel) {
        this.instanceModel = instanceModel;
    }

    // Setters

    public void callAllNecessaryMethods(User currentUser, LabelAssignment newLabelAssignment) {
        this.addUniqueUser(currentUser);
        this.addLabelAssignments(newLabelAssignment);
        this.setTotalNumberOfAssignedLabels(); // update while model class object is created
        this.setNumberOfUniqueAssignedLabels(); // parameters and method should be handled
        this.updateClassLabelsAndPercentages();
        this.setEntropy();
    }

    private void updateClassLabelsAndPercentages() {
        Double total = 0.0, mostPercentage = 0.0, currentPercentage = 0.0;
        Label frequentLabel = null;

        ArrayList<ListClassLabelsAndPercentage> listClassLabelsAndPercentages = new ArrayList<>();
        ListClassLabelsAndPercentage aClassUserAndPercentagesObject;
        for (Label label : uniqueLabels.keySet()) {
            total += uniqueLabels.get(label);
        }
        for (Label label : uniqueLabels.keySet()) {
            currentPercentage = uniqueLabels.get(label) / total;
            this.classLabelsAndPercentages.put(label, currentPercentage);

            aClassUserAndPercentagesObject = new ListClassLabelsAndPercentage();
            aClassUserAndPercentagesObject.setLabel(label.getLabelText());
            aClassUserAndPercentagesObject.setPercentage(currentPercentage);
            listClassLabelsAndPercentages.add(aClassUserAndPercentagesObject);

            if (currentPercentage > mostPercentage) {
                mostPercentage = currentPercentage;
                frequentLabel = label;
            }
        }
        instanceModel.setListClassLabelsAndPercentages(listClassLabelsAndPercentages);
        this.setMostFrequentLabel(frequentLabel, mostPercentage);
    }

    /* New added */
    private void setMostFrequentLabel(Label frequentLabel, Double percentage) { // Do not call again, already called
                                                                                // updateClassLabels above
        ListClassLabelsAndPercentage mostFreq = new ListClassLabelsAndPercentage();
        mostFreq.setLabel(frequentLabel.getLabelText());
        mostFreq.setPercentage(percentage);
        ArrayList<ListClassLabelsAndPercentage> list = new ArrayList<>();
        list.add(mostFreq);
        instanceModel.setMostFrequentClassLabelAndPercentage(list);
    }

    private void setEntropy() { // check for base of logarithm (num of labels)
        Double resultEnt = 0d;

        for (Double percentage : classLabelsAndPercentages.values()) {
            resultEnt += -(percentage * myLog(percentage));
        }
        instanceModel.setEntropy(resultEnt);
    }

    private Double myLog(Double N) {
        Double result = (Double) (Math.log(N) / Math.log(this.classLabelsAndPercentages.size()));
        return result;
    }

    private void setTotalNumberOfAssignedLabels() {
        Integer totalNumberOfAssignedLabels = 0;
        for (LabelAssignment lAssignment : this.labelAssignments) {
            for (Integer labelId : lAssignment.getAssignedLabelId()) {
                totalNumberOfAssignedLabels++;
            }
        }
        instanceModel.setTotalNumberOfLabelAssignments(totalNumberOfAssignedLabels);
    }

    private void addLabelAssignments(LabelAssignment newLabelAssignment) { // call before setTotalNumberOfAssignedLabels
                                                                           // method
        this.labelAssignments.add(newLabelAssignment);
    }

    private void setNumberOfUniqueAssignedLabels() {
        instanceModel.setNumberOfUniqueLabelAssignments(uniqueLabels.size());
    }

    public void setNumberOfUniqueUsers() {
        instanceModel.setNumberOfUniqueUsers(uniqueUsers.size());
    }

    private void addUniqueUser(User user) { // call before setNumberOfUniqueUsers
        uniqueUsers.add(user);
        instanceModel.setNumberOfUniqueUsers(this.uniqueUsers.size());
    }

    public void addUniqueLabel(Label label) { // call before setNumberOfUniqueLabels
        if (uniqueLabels.get(label) == null) {
            uniqueLabels.put(label, 1);
        } else {
            uniqueLabels.put(label, uniqueLabels.get(label) + 1);
        }
    }

}
