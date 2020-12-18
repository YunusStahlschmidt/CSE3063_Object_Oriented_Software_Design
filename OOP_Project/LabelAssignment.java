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
    private ArrayList<Integer> assignedLabelIds;
    private int userId;
    private Date date;

    public LabelAssignment() {
    }

    public LabelAssignment(int instanceId, ArrayList<Integer> assignedLabelIds, int userId, Date date) {
        this.instanceId = instanceId;
        this.assignedLabelIds = assignedLabelIds;
        this.userId = userId;
        this.date = date;
    }

    // Getters

    public int getInstanceId() {
        return instanceId;
    }

    public ArrayList<Integer> getAssignedLabelId() {
        return assignedLabelIds;
    }

    public int getSpecificAssignedLabelId(int n) {
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

    public void addAssignedLabelId(int id) {
        this.assignedLabelIds.add(id);
    }
}
