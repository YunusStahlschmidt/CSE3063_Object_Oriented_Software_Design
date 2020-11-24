package OOP_Project;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    private int id;
    private String text;


    // Getters

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }


    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
