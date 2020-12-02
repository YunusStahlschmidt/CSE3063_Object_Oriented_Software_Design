package OOP_Project;

/*
Class for the user object to store them in our dictionary in the main class
We will get the users from a config file (in json format)
We will also use that dictionary for our final output
*/

public class User {
    private long id;
    private String name;
    private String type;

    public User() {}
    public User(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }


    //Getters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


    // Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}