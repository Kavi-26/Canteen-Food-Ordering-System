package com.collegecanteen.models;

import com.google.gson.JsonElement;

public class ApiResponse {
    public boolean success;
    public String message;
    public JsonElement data; // Generic data
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public JsonElement getData() { return data; }
}
