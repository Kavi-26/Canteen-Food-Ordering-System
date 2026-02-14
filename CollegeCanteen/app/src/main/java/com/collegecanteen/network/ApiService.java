package com.collegecanteen.network;

import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.FoodItem;
import com.collegecanteen.models.LoginRequest;
import com.collegecanteen.models.OrderRequest;
import com.collegecanteen.models.OtpVerificationRequest;
import com.collegecanteen.models.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/users/register")
    Call<ApiResponse> register(@Body RegisterRequest request);

    @POST("/api/users/login")
    Call<ApiResponse> login(@Body LoginRequest request);

    @GET("/api/food/menu")
    Call<List<FoodItem>> getMenu();

    @POST("/api/orders/place")
    Call<ApiResponse> placeOrder(@Body OrderRequest request);

    @POST("/api/orders/verify-otp")
    Call<ApiResponse> verifyOtp(@Body OtpVerificationRequest request);
}
