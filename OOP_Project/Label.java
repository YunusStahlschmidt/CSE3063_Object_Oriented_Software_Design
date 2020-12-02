package CSE3063F20P1_GRP27.OOP_Project;

/*
Class for the label object to store them in our array list in the dataset
*/

public class Label {
    private long id;
    private String name;

    
    public Label() {}
    public Label(long id, String name) {
        this.id = id;
        this.name = name;
    }
    // Getters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
