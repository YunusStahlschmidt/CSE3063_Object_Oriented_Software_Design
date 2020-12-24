package OOP_Project.MetricsJSONModels;

import java.text.DecimalFormat;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatasetModel {

    @SerializedName("datasetId")
    @Expose
    private Integer datasetId;
    @SerializedName("Completeness percentage")
    @Expose
    private Double completenessPercentage;
    @SerializedName("Class distribution based on final instance labels")
    @Expose
    private List<ClassDistributionBasedOnFinalInstanceLabel> classDistributionBasedOnFinalInstanceLabels = null;
    @SerializedName("List number of unique instances for each class label")
    @Expose
    private List<ListNumberOfUniqueInstancesForEachClassLabel> listNumberOfUniqueInstancesForEachClassLabel = null;
    @SerializedName("Number of users assigned to this dataset")
    @Expose
    private Integer numberOfUsersAssignedToThisDataset;
    @SerializedName("List of users assigned and their completeness percentage")
    @Expose
    private List<ListOfUsersAssignedAndTheirCompletenessPercentage> listOfUsersAssignedAndTheirCompletenessPercentage = null;
    @SerializedName("List of users assigned and their consistency percentage")
    @Expose
    private List<ListOfUsersAssignedAndTheirConsistencyPercentage> listOfUsersAssignedAndTheirConsistencyPercentage = null;

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public Double getCompletenessPercentage() {
        return completenessPercentage;
    }

    public void setCompletenessPercentage(double completenessPercentage) {
        this.completenessPercentage = Double
                .parseDouble(new DecimalFormat("##.###").format(completenessPercentage * 100));

    }

    public List<ClassDistributionBasedOnFinalInstanceLabel> getClassDistributionBasedOnFinalInstanceLabels() {
        return classDistributionBasedOnFinalInstanceLabels;
    }

    public void setClassDistributionBasedOnFinalInstanceLabels(
            List<ClassDistributionBasedOnFinalInstanceLabel> classDistributionBasedOnFinalInstanceLabels) {
        this.classDistributionBasedOnFinalInstanceLabels = classDistributionBasedOnFinalInstanceLabels;
    }

    public List<ListNumberOfUniqueInstancesForEachClassLabel> getListNumberOfUniqueInstancesForEachClassLabel() {
        return listNumberOfUniqueInstancesForEachClassLabel;
    }

    public void setListNumberOfUniqueInstancesForEachClassLabel(
            List<ListNumberOfUniqueInstancesForEachClassLabel> listNumberOfUniqueInstancesForEachClassLabel) {
        this.listNumberOfUniqueInstancesForEachClassLabel = listNumberOfUniqueInstancesForEachClassLabel;
    }

    public Integer getNumberOfUsersAssignedToThisDataset() {
        return numberOfUsersAssignedToThisDataset;
    }

    public void setNumberOfUsersAssignedToThisDataset(Integer numberOfUsersAssignedToThisDataset) {
        this.numberOfUsersAssignedToThisDataset = numberOfUsersAssignedToThisDataset;
    }

    public List<ListOfUsersAssignedAndTheirCompletenessPercentage> getListOfUsersAssignedAndTheirCompletenessPercentage() {
        return listOfUsersAssignedAndTheirCompletenessPercentage;
    }

    public void setListOfUsersAssignedAndTheirCompletenessPercentage(
            List<ListOfUsersAssignedAndTheirCompletenessPercentage> listOfUsersAssignedAndTheirCompletenessPercentage) {
        this.listOfUsersAssignedAndTheirCompletenessPercentage = listOfUsersAssignedAndTheirCompletenessPercentage;
    }

    public List<ListOfUsersAssignedAndTheirConsistencyPercentage> getListOfUsersAssignedAndTheirConsistencyPercentage() {
        return listOfUsersAssignedAndTheirConsistencyPercentage;
    }

    public void setListOfUsersAssignedAndTheirConsistencyPercentage(
            List<ListOfUsersAssignedAndTheirConsistencyPercentage> listOfUsersAssignedAndTheirConsistencyPercentage) {
        this.listOfUsersAssignedAndTheirConsistencyPercentage = listOfUsersAssignedAndTheirConsistencyPercentage;
    }

}
