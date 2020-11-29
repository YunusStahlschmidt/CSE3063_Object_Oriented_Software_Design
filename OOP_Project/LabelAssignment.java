package OOP_Project;

import java.util.Date;

/*
This class is for the result of our processing, after the user has labeled
an instance we will store this created LabelAssignment object in the arrar list
in the main class; after we are done with with our labelling we will use that list
for our final output
*/

public class LabelAssignment {
    private long instanceId;
    private long assignedLabelId;
    private long userId;
    private Date date;

    public LabelAssignment() {}
    public LabelAssignment(long instanceId, long assignedLabelId, long userId, Date date) {
        this.instanceId = instanceId;
        this.assignedLabelId = assignedLabelId;
        this.userId = userId;
        this.date = date;
    }


    // Getters

    public long getInstanceId() {
        return instanceId;
    }

    public long getAssignedLabelId() {
        return assignedLabelId;
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

    public void setAssignedLabelId(long assignedLabelId) {
        this.assignedLabelId = assignedLabelId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
