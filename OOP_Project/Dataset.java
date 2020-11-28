package OOP_Project;

import java.util.ArrayList;

/*
This class should parse all the information from the dataset so we can 
process them in our main function
We will get the data from the input json files which we have to parse in this class
*/

public class Dataset {
    private String id;
    private String name;
    private String maxLabel;
    private ArrayList<Label> labels = new ArrayList<Label>();
    private ArrayList<Instance> instances = new ArrayList<Instance>();
   
   
    // Getters 
   
    public String getId() {
     return id;
    }
   
    public String getName() {
     return name;
    }
   
    public String getMaxLabel() {
     return maxLabel;
    }
   
    // Setters
   
    public void setId(String newId) {
     this.id = newId;
    }
   
    public void setName(String newName) {
     this.name = newName;
    }
   
    public void setMaxLabel(String newMaxLabel) {
     this.maxLabel = newMaxLabel;
    }

    public void addLabel(String id, String name) {
        this.labels.add(new Label(id, name));
    }

    public void addInstance(String id, String text) {
        this.instances.add(new Instance(id, text));
    }
   }