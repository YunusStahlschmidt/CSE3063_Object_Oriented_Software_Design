package OOP_Project;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigModel {
    
    @SerializedName("CurrentDatasetId")
    @Expose
    private Integer currentDatasetId;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("datasets")
    @Expose
    private List<Dataset> datasets = null;

    public Integer getCurrentDatasetId() {
        return currentDatasetId;
    }

    public void setCurrentDatasetId(Integer currentDatasetId) {
        this.currentDatasetId = currentDatasetId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

}