package com.example.chan_nuoi_thong_minh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main_dangnhap extends AppCompatActivity {
    Switch quat, dieuhoa, maysuoi, den, phunsuong;
    TextView temp, hum;
    EditText thietlap_nhietdo, thietlap_doam;
    Button thietlap, xoa_thietlap, xembieudo_temhum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dangnhap);
        Anhxa(); // Ánh xạ đến cái id

        set_switch(); // thiết lập trạng thái switch khi mới vào app
        chose_switch(); // Bật tắt các switch

        set_tem_hum(); // Thiết lập nhiệt độ, độ ẩm
        xem_bieudo_temhum(); // Xem biểu đồ nhiệt độ, độ ẩm

        refresh_data(); // refresh lại data khi có cập nhật

        set_thietlap(); // set lại giá trị độ ẩm thiết lập và nhiệt độ thiết lập khi vào app
        thietlap(); // thiết lập nhiệt độ độ ẩm tự động

    }
    void Anhxa(){
        quat = findViewById(R.id.id_switch_quat);
        dieuhoa = findViewById(R.id.id_switch_dieuhoa);
        maysuoi = findViewById(R.id.id_switch_maysuoi);
        den = findViewById(R.id.id_switch_den);
        phunsuong = findViewById(R.id.id_switch_phunsuong);
        temp = findViewById(R.id.id_tv_nhietdo);
        hum = findViewById(R.id.id_tv_doam);
        thietlap_nhietdo = findViewById(R.id.id_et_thietlap_nhietdo);
        thietlap_doam = findViewById(R.id.id_et_thietlap_doam);
        thietlap = findViewById(R.id.id_bt_thietlap);
        xoa_thietlap = findViewById(R.id.id_bt_xoathietlap);
        xembieudo_temhum = findViewById(R.id.id_bt_xembieudo_temhum);
    }

    void chose_switch(){
        String url = "http://192.168.1.196/app_channuoithongminh/insert_switch_data.php";
        quat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(Main_dangnhap.this, "Quạt đã được bật", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "1", "1");
                } else {
                    Toast.makeText(Main_dangnhap.this, "Quạt đã được tắt", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "0", "1");
                }
            }
        });

        dieuhoa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(Main_dangnhap.this, "Điều hòa đã được bật", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "1", "2");
                } else {
                    Toast.makeText(Main_dangnhap.this, "Điều hòa đã được tắt", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "0", "2");
                }
            }
        });

        maysuoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(Main_dangnhap.this, "Máy sưởi đã được bật", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "1", "3");
                } else {
                    Toast.makeText(Main_dangnhap.this, "Máy sưởi đã được tắt", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "0", "3");
                }
            }
        });

        den.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(Main_dangnhap.this, "Đèn đã được bật", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "1", "4");
                } else {
                    Toast.makeText(Main_dangnhap.this, "Đèn đã được tắt", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "0", "4");
                }
            }
        });

        phunsuong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(Main_dangnhap.this, "Phun sương đã được bật", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "1", "5");
                } else {
                    Toast.makeText(Main_dangnhap.this, "Phun sương đã được tắt", Toast.LENGTH_SHORT).show();
                    insert_switch_data(url, "0", "5");
                }
            }
        });
    }
    void insert_switch_data(String url, String status, String id){
        RequestQueue requestQueue = Volley.newRequestQueue(Main_dangnhap.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        if (response.trim().equals("success")){
//                            Toast.makeText(Main_dangnhap.this, "OK", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(Main_dangnhap.this, "NO", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main_dangnhap.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("AAA", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", status);
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    void set_switch(){
        String url = "http://192.168.1.196/app_channuoithongminh/getdata_switch.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("Id");
                                int status = jsonObject.getInt("Status");
                                switch(id){
                                    case 1:
                                        if (status == 1) quat.setChecked(true); else quat.setChecked(false);
                                        break;
                                    case 2:
                                        if (status == 1) dieuhoa.setChecked(true); else dieuhoa.setChecked(false);
                                        break;
                                    case 3:
                                        if (status == 1) maysuoi.setChecked(true); else maysuoi.setChecked(false);
                                        break;
                                    case 4:
                                        if (status == 1) den.setChecked(true); else den.setChecked(false);
                                        break;
                                    case 5:
                                        if (status == 1) phunsuong.setChecked(true); else phunsuong.setChecked(false);
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    void set_tem_hum(){
        String url = "http://192.168.1.196/app_channuoithongminh/getdata_tem_hum.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(response.length() - 1);
                            String nhietdo = jsonObject.getString("Tem");
                            String doam = jsonObject.getString("Hum");
                            temp.setText(nhietdo+"*C");
                            hum.setText(doam+"%");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    void xem_bieudo_temhum(){
        xembieudo_temhum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_dangnhap.this, Bieudo_temhum.class);
                startActivity(intent);
            }
        });
    }

    void refresh_data(){
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // data request
                set_tem_hum();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(refresh, 1000);
    }

    void thietlap(){
        String url = "http://192.168.1.196/app_channuoithongminh/insert_thietlap_data.php";
        thietlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(Main_dangnhap.this, "Đã thiết lập", Toast.LENGTH_SHORT).show();
                    String nhietdo_thietlap = thietlap_nhietdo.getText().toString();
                    String doam_thietlap = thietlap_doam.getText().toString();
                    thietlap_insert_data(url, nhietdo_thietlap, doam_thietlap);
            }
        });
        xoa_thietlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    thietlap_insert_data(url, "0", "0");
                    thietlap_nhietdo.setText("");
                    thietlap_doam.setText("");
            }
        });

    }
    void thietlap_insert_data(String url, String nhietdo_thietlap, String doam_thietlap){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Tem",nhietdo_thietlap);
                params.put("Hum",doam_thietlap);
                params.put("Id", "1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    void set_thietlap(){
        String url = "http://192.168.1.196/app_channuoithongminh/getdata_thietlap.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String nhietdo_thietlap = jsonObject.getString("Tem");
                            String doam_thietlap = jsonObject.getString("Hum");
                            if ((nhietdo_thietlap.compareTo("0.00")!=0) && (doam_thietlap.compareTo("0.00")!=0)){
                                thietlap_nhietdo.setText(nhietdo_thietlap);
                                thietlap_doam.setText(doam_thietlap);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
//    @Override
//    public void onRefresh() {
//        set_tem_hum();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }, 1000);
//    }
}