package Iteration_3;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
This class is for the result of our processing, after the user has labeled
an instance we will store this created LabelAssignment object in the arrar list
in the main class; after we are done with with our labelling we will use that list
for our final output
*/

public class LabelAssignment {
    @SerializedName("instance id")
    @Expose
    private int instanceId;
    @SerializedName("class label ids")
    @Expose
    private ArrayList<Label> assignedLabels;
    @SerializedName("user id")
    @Expose
    private int userId;
    @SerializedName("datetime")
    @Expose
    private Date date;

    public LabelAssignment() {
    }

    public LabelAssignment(int instanceId, ArrayList<Label> assignedLabels, int userId, Date date) {
        this.instanceId = instanceId;
        this.assignedLabels = assignedLabels;
        this.userId = userId;
        this.date = date;
    }

    // Getters

    public Integer getInstanceId() {
        return instanceId;
    }

    public ArrayList<Integer> getAssignedLabelId() {
        ArrayList<Integer> lablesIds = new ArrayList<>();
        for (Label aLabel : assignedLabels) {
            lablesIds.add(aLabel.getId());
        }
        return lablesIds;
    }

    public Integer getSpecificAssignedLabelId(int n) {
        /* To get specific label id */
        return getAssignedLabelId().get(n);
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    // Setters

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addAssignedLabel(Label label) {
        this.assignedLabels.add(label);
    }
}
