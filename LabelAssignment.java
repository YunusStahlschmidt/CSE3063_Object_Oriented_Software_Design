package OOP_Project;

import java.util.Date;

/*
This class is for the result of our processing, after the user has labeled
an instance we will store this created LabelAssignment object in the arrar list
in the main class; after we are done with with our labelling we will use that list
for our final output
*/

public class LabelAssignment {
    private int instanceId;
    private int assignedLabelId;
    private int userId;
    private Date date;


    // Getters

    public int getInstanceId() {
        return instanceId;
    }

    public int getAssignedLabelId() {
        return assignedLabelId;
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

    public void setAssignedLabelId(int assignedLabelId) {
        this.assignedLabelId = assignedLabelId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
