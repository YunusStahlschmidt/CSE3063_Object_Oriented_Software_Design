package Iteration_1;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    private long id;
    private String text;

    public Instance() {}
    public Instance(long id, String text) {
        this.id = id;
        this.text = text;
    }


    // Getters

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }


    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}