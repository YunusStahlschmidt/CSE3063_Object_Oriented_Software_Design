package OOP_Project;

/*
Class for the label object to store them in our array list in the dataset
*/

public class Label {
    private String id;
    private String name;

    public Label(String id, String name) {
        this.id = id;
        this.name = name;
    }
    // Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
