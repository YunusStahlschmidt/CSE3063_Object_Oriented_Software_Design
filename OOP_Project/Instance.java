package OOP_Project;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    private String id;
    private String text;

    public Instance(String id, String text) {
        this.id = id;
        this.text = text;
    }


    // Getters

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }


    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
