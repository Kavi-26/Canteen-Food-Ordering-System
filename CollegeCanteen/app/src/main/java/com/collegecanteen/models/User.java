package com.collegecanteen.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private int userId;
    
    private String name;
    private String email;
    private String role;
    private String mobile;
    private String rollNo;
    private String token;

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getMobile() { return mobile; }
    public String getRollNo() { return rollNo; }
    public String getToken() { return token; }

    // Keep backward compat
    public int getId() { return userId; }
}
