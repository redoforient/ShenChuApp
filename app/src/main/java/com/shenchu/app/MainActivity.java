package com.shenchu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shenchu.app.bean.RequestBean;
import com.shenchu.app.bean.ResponseBean;
import com.shenchu.app.framework.IDataListener;
import com.shenchu.app.framework.NetFramework;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        String url = "https://v.juhe.cn/historyWeather/citys";
        RequestBean param = new RequestBean("2", "bb52107206585ab074f5e59a8c73875b");
        NetFramework.sendJsonRequest(url, param,
                ResponseBean.class,
                new IDataListener<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        Toast.makeText(MainActivity.this, "响应码是：" + responseBean.getError_code(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure() {
                        Log.i("MainActivity", "请求失败...");
                    }
                });

    }
}