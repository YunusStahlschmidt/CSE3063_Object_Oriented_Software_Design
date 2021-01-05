package Iteration_1;

import java.util.ArrayList;
import java.util.Date;

/*
This class is for the result of our processing, after the user has labeled
an instance we will store this created LabelAssignment object in the arrar list
in the main class; after we are done with with our labelling we will use that list
for our final output
*/

public class LabelAssignment {
    private long instanceId;
    private ArrayList<Long> assignedLabelIds;
    private long userId;
    private Date date;

    public LabelAssignment() {
    }

    public LabelAssignment(long instanceId, ArrayList<Long> assignedLabelIds, long userId, Date date) {
        this.instanceId = instanceId;
        this.assignedLabelIds = assignedLabelIds;
        this.userId = userId;
        this.date = date;
    }

    // Getters

    public long getInstanceId() {
        return instanceId;
    }

    public ArrayList<Long> getAssignedLabelId() {
        return assignedLabelIds;
    }

    public long getSpecificAssignedLabelId(int n) {
        return getAssignedLabelId().get(n);
    }

    public long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    // Setters

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addAssignedLabelId(Long id) {
        this.assignedLabelIds.add(id);
    }
}
