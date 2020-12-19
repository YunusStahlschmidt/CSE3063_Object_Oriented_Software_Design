package OOP_Project.MetricsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListNumberOfUniqueInstancesForEachClassLabel {

@SerializedName("label")
@Expose
private String label;
@SerializedName("amount")
@Expose
private Integer amount;

public String getLabel() {
return label;
}

public void setLabel(String label) {
this.label = label;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

}