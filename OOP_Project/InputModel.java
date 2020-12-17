package OOP_Project;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputModel {

@SerializedName("dataset id")
@Expose
private Integer datasetId;
@SerializedName("dataset name")
@Expose
private String datasetName;
@SerializedName("maximum number of labels per instance")
@Expose
private Integer maximumNumberOfLabelsPerInstance;
@SerializedName("class labels")
@Expose
private List<Label> classLabels = null;
@SerializedName("instances")
@Expose
private List<Instance> instances = null;

public Integer getDatasetId() {
return datasetId;
}

public void setDatasetId(Integer datasetId) {
this.datasetId = datasetId;
}

public String getDatasetName() {
return datasetName;
}

public void setDatasetName(String datasetName) {
this.datasetName = datasetName;
}

public Integer getMaximumNumberOfLabelsPerInstance() {
return maximumNumberOfLabelsPerInstance;
}

public void setMaximumNumberOfLabelsPerInstance(Integer maximumNumberOfLabelsPerInstance) {
this.maximumNumberOfLabelsPerInstance = maximumNumberOfLabelsPerInstance;
}

public List<Label> getClassLabels() {
return classLabels;
}

public void setClassLabels(List<Label> classLabels) {
this.classLabels = classLabels;
}

public List<Instance> getInstances() {
return instances;
}

public void setInstances(List<Instance> instances) {
this.instances = instances;
}

}