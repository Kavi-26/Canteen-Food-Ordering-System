package com.collegecanteen.dto;

public record ApiResponse(boolean success, String message, Object data) {}
