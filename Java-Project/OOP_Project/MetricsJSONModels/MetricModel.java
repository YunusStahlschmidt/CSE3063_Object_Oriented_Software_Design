package OOP_Project.MetricsJSONModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetricModel {

    @SerializedName("Users")
    @Expose
    private List<UserModel> users = null;
    @SerializedName("Instance")
    @Expose
    private List<InstanceModel> instance = null;
    @SerializedName("Dataset")
    @Expose
    private List<DatasetModel> dataset = null;

    public List<UserModel> getUsersList() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public List<InstanceModel> getInstanceList() {
        return instance;
    }

    public void setInstance(List<InstanceModel> instance) {
        this.instance = instance;
    }

    public List<DatasetModel> getDatasetList() {
        return dataset;
    }

    public void setDataset(List<DatasetModel> dataset) {
        this.dataset = dataset;
    }

}
