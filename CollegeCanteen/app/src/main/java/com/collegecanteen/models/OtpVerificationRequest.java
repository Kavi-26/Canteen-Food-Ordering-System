package com.collegecanteen.models;

    public Long userId;
    public String otp;

    public OtpVerificationRequest(Long userId, String otp) {
        this.userId = userId;
        this.otp = otp;
    }
}
