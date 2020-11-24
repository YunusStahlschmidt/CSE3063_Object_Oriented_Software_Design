package OOP_Project;

import java.util.ArrayList;

/*
This class should parse all the information from the dataset so we can 
process them in our main function
We will get the data from the input json files which we have to parse in this class
*/

public class Dataset {
    private float id;
    private String name;
    private float maxLabels;
    private ArrayList<Label> Labels = new ArrayList<Label>();
    private ArrayList<Instance> instances = new ArrayList<Instance>();
   
   
    // Getters 
   
    public float getId() {
     return id;
    }
   
    public String getName() {
     return name;
    }
   
    public float getMaxLabels() {
     return maxLabels;
    }
   
    // Setters
   
    public void setId(float newId) {
     this.id = newId;
    }
   
    public void setName(String newName) {
     this.name = newName;
    }
   
    public void setMaxLabels(float newMaxLabels) {
     this.maxLabels = newMaxLabels;
    }
   }