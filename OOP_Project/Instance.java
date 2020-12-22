/*

*/

package OOP_Project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    // @SerializedName("datasetId")
    // @Expose
    // private Integer datasetId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instance")
    @Expose
    private String instance;

    private InstanceMetric instanceMetric = new InstanceMetric();

    public Instance() {
    }

    public Instance(Integer id, String instance) {
        this.id = id;
        this.instance = instance;
    }

    // Getters

    // public Integer getDatasetId() {
    // return datasetId;
    // }

    public Integer getId() {
        return id;
    }

    public String getInstance() {
        return instance;
    }

    public InstanceMetric getInstanceMetric() {
        return instanceMetric;
    }

    // Setters
    // public void setDatasetId(Integer datasetId) {
    // this.datasetId = datasetId;
    // }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setInstanceMetric() {
        // not sure do
    }

    public void setDatasetIdToInstanceModel(Integer datasetId) {
        instanceMetric.getInstanceModel().setDatasetId(datasetId);
    }

    public void setInstanceIdToModel() {
        instanceMetric.getInstanceModel().setInstanceId(this.id);
    }

}