package com.ocr.lpr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.ocr.ui.LPRActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import static com.example.ocr.ui.LPRActivity.REQUEST_LPR_CODE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        findViewById(R.id.brn_lpr).setOnClickListener(v -> {
            startActivityForResult(new Intent(this, LPRActivity.class), REQUEST_LPR_CODE);
        });
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，车牌识别
        if (requestCode == REQUEST_LPR_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String card = data.getStringExtra("card");
                new AlertDialog.Builder(this)
                        .setMessage(card)
                        .setNegativeButton("OK", (dialog, which) -> {
                        })
                        .show();
            }
        }
    }

    private void getPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA)
                .onGranted(new Action() {
                    @Override
                    public void onAction(Object data) {

                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(@NonNull List<String> permissions) {

            }
        }).start();
    }
}
