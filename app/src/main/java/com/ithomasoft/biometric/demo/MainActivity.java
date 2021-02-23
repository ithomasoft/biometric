package com.ithomasoft.biometric.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ithomasoft.biometric.BiometricHelper;
import com.ithomasoft.biometric.BiometricIdentifyCallback;
import com.ithomasoft.biometric.BiometricStateCallback;

public class MainActivity extends AppCompatActivity {
    private TextView tvUser;
    private AppCompatButton btnSetting;
    private AppCompatButton btnLoginPassword;
    private AppCompatButton btnLoginBiometric;
    private boolean noInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvUser = findViewById(R.id.tv_user);
        btnSetting = findViewById(R.id.btn_setting);
        btnLoginPassword = findViewById(R.id.btn_login_password);
        btnLoginBiometric = findViewById(R.id.btn_login_biometric);

        if (UserHelper.isLogin(this)) {
            tvUser.setText("当前登录用户：" + UserHelper.getUserName(this) + "_" + UserHelper.getPassword(this));
        } else {
            tvUser.setText("当前未登录");
        }

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLoginPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                intent.putExtras(bundle);
                intent.setClass(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnLoginBiometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noInfo) {
                    BiometricHelper.configBiometric(MainActivity.this);
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    intent.putExtras(bundle);
                    intent.setClass(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        BiometricHelper.verifyBiometric(this, new BiometricStateCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
            }

            @Override
            public void onNotSupport() {
                super.onNotSupport();
                btnLoginBiometric.setEnabled(false);
            }

            @Override
            public void onNoInfo() {
                super.onNoInfo();
                noInfo = true;
            }

            @Override
            public void onUnknown() {
                super.onUnknown();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BiometricHelper.verifyBiometric(this, new BiometricStateCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                noInfo = false;
            }

            @Override
            public void onNotSupport() {
                super.onNotSupport();
                btnLoginBiometric.setEnabled(false);
            }

            @Override
            public void onNoInfo() {
                super.onNoInfo();
                noInfo = true;
            }

            @Override
            public void onUnknown() {
                super.onUnknown();
            }
        });
    }
}