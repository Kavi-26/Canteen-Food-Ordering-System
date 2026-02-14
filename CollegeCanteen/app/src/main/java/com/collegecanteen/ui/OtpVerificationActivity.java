package com.collegecanteen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.collegecanteen.R;
import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.OtpVerificationRequest;
import com.collegecanteen.network.RetrofitClient;
import com.collegecanteen.utils.CartManager;
import com.collegecanteen.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerify;
    private TextView tvOtpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        etOtp = findViewById(R.id.etOtp);
        btnVerify = findViewById(R.id.btnVerify);
        tvOtpInfo = findViewById(R.id.tvOtpInfo);
        
        String msg = getIntent().getStringExtra("MESSAGE");
        if (msg != null) {
            tvOtpInfo.setText(msg); // Show OTP for demo purposes
        }

        btnVerify.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = etOtp.getText().toString();
        if (otp.length() != 4) {
            Toast.makeText(this, "Enter valid 4-digit OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionManager session = new SessionManager(this);
        OtpVerificationRequest request = new OtpVerificationRequest(session.getUserId(), otp);

        RetrofitClient.getApiService().verifyOtp(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Toast.makeText(OtpVerificationActivity.this, "Order Confirmed!", Toast.LENGTH_LONG).show();
                    CartManager.getInstance().clearCart();
                    
                    // Navigate to Home or Order Confirmation (Using Home for now)
                    Intent intent = new Intent(OtpVerificationActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(OtpVerificationActivity.this, "Invalid OTP or Expired", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(OtpVerificationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
