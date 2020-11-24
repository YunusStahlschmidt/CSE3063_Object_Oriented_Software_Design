package OOP_Project;

import java.util.ArrayList;
import java.util.Dictionary;

/* 
This class will where al "computations" will take place
*/


public class Main {
    private Dataset dataset;
    private ArrayList<LabelAssignment> labelAssignments;
    private Dictionary<String, User> usersDict;  


    // Getters

    public Dataset getDataset() {
        return dataset;
    }


    // Setters

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
