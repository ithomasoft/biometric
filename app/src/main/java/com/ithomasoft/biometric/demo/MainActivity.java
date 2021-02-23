package com.ithomasoft.biometric.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.ithomasoft.biometric.BiometricManager;

public class MainActivity extends AppCompatActivity {
    private TextView tvUser;
    private AppCompatButton btnSetting;
    private AppCompatButton btnLoginPassword;
    private AppCompatButton btnLoginBiometric;

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
        btnLoginBiometric.setEnabled(BiometricManager.isHardwareDetected());
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
                if (!BiometricManager.hasBiometricInfo()) {
                    BiometricManager.addNewBiometricInfo();
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


    }

}