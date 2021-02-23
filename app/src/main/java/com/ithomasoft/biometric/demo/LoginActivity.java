package com.ithomasoft.biometric.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.biometric.BiometricPrompt;

import android.os.Bundle;
import android.view.View;

import com.ithomasoft.biometric.BiometricIdentifyCallback;
import com.ithomasoft.biometric.BiometricManager;

public class LoginActivity extends AppCompatActivity {
    private AppCompatEditText etUsername;
    private AppCompatEditText etPassword;
    private AppCompatButton btnLogin;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        type = getIntent().getExtras().getInt("type");
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    UserHelper.setUserName(LoginActivity.this, etUsername.getText().toString());
                    UserHelper.setPassword(LoginActivity.this, etPassword.getText().toString());
                    UserHelper.setLoginState(LoginActivity.this, true);
                } else {
                    BiometricManager.startBiometricIdentify(LoginActivity.this, new BiometricIdentifyCallback() {
                        @Override
                        public void onOther() {
                            super.onOther();
                        }

                        @Override
                        public void onNoBiometric() {
                            super.onNoBiometric();
                        }

                        @Override
                        public void onSucceeded(BiometricPrompt.AuthenticationResult result) {
                            super.onSucceeded(result);
                            UserHelper.setUserName(LoginActivity.this, etUsername.getText().toString());
                            UserHelper.setPassword(LoginActivity.this, etPassword.getText().toString());
                            UserHelper.setLoginState(LoginActivity.this, true);
                        }

                        @Override
                        public void onError(int code, String reason) {
                            super.onError(code, reason);
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                        }
                    });
                }
            }
        });
    }
}