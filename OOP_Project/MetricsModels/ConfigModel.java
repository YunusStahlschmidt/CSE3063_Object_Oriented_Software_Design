package OOP_Project.MetricsModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigModel {

    @SerializedName("Users")
    @Expose
    private List<User> users = null;
    @SerializedName("Instance")
    @Expose
    private List<Instance> instance = null;
    @SerializedName("Dataset")
    @Expose
    private List<Dataset> dataset = null;

    public List<User> getUsers() {
    return users;
    }

    public void setUsers(List<User> users) {
    this.users = users;
    }

    public List<Instance> getInstance() {
    return instance;
    }

    public void setInstance(List<Instance> instance) {
    this.instance = instance;
    }

    public List<Dataset> getDataset() {
    return dataset;
    }

    public void setDataset(List<Dataset> dataset) {
    this.dataset = dataset;
    }

}
