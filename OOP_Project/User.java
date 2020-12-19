package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Class for the user object to store them in our dictionary in the main class
We will get the users from a config file (in json format)
We will also use that dictionary for our final output
*/

public class User {
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("consistencyCheckProbability")
    @Expose
    private double consistencyCheckProbability;
    private UserMetric userMetric = new UserMetric();
    public User() {}
    public User(int id, String name, String type) {
        this.userId = id;
        this.userName = name;
        this.userType = type;
    }

    //Getters

    public Integer getId() {
        return userId;
    }
    
    public String getName() {
        return userName;
    }

    public String getType() {
        return userType;
    }

    public UserMetric getUserMetric(){
        return userMetric;
    }
    // Setters

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserMetric(){
        //should set user metric
    }
}