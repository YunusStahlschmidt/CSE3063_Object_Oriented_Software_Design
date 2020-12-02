package CSE3063F20P1_GRP27.OOP_Project;

import java.util.ArrayList;

/*
This class should parse all the information from the dataset so we can 
process them in our main function
We will get the data from the input json files which we have to parse in this class
*/

public class Dataset {
    private long id;
    private String name;
    private long maxLabel;
    private ArrayList<Label> labels = new ArrayList<Label>();
    private ArrayList<Instance> instances = new ArrayList<Instance>();

    public Dataset() {}
    public Dataset(long id, String name, long maxLabel) {
        this.id = id;
        this.name = name;
        this.maxLabel = maxLabel;
    }
    // Getters 

    public long getId() {
     return id;
    }

    public String getName() {
     return name;
    }

    public long getMaxLabel() {
     return maxLabel;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }


    // Setters

    public void setId(long newId) {
     this.id = newId;
    }

    public void setName(String newName) {
     this.name = newName;
    }

    public void setMaxLabel(long newMaxLabel) {
     this.maxLabel = newMaxLabel;
    }

    public void addLabel(long id, String name) {
        this.labels.add(new Label(id, name));
    }

    public void addInstance(long id, String text) {
        this.instances.add(new Instance(id, text));
    }
} 