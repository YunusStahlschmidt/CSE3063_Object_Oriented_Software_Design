package Iteration_2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Class for the label object to store them in our array list in the dataset
*/

public class Label {
    @SerializedName("label id")
    @Expose
    private Integer labelId;
    @SerializedName("label text")
    @Expose
    private String labelText;

    public Label() {
    }

    public Label(int id, String text) {
        this.labelId = id;
        this.labelText = text;
    }
    // Getters

    public Integer getId() {
        return labelId;
    }

    public String getLabelText() {
        return this.labelText;
    }

    // Setters

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
}
