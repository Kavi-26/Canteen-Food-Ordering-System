package com.collegecanteen.models;

public class RegisterRequest {
    public String name;
    public String rollNo;
    public String email;
    public String mobile;
    public String password;

    public RegisterRequest(String name, String rollNo, String email, String mobile, String password) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }
}
