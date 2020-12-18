package OOP_Project;

import java.util.ArrayList;
import java.util.Date;

/*
This class is for the result of our processing, after the user has labeled
an instance we will store this created LabelAssignment object in the arrar list
in the main class; after we are done with with our labelling we will use that list
for our final output
*/

public class LabelAssignment {
    private int instanceId;
    private ArrayList<Label> assignedLabels;
    private int userId;
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

    public int getInstanceId() {
        return instanceId;
    }

    public ArrayList<Integer> getAssignedLabelId() {
        ArrayList<Integer> lablesIds = new ArrayList<>();
        for (Label aLabel: assignedLabels) {
            lablesIds.add(aLabel.getId());
        } 
        return lablesIds;
    }

    public int getSpecificAssignedLabelId(int n) {
        /* To get specific label id */
        return getAssignedLabelId().get(n);
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    // Setters

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addAssignedLabel(Label label) {
        this.assignedLabels.add(label);
    }
}
