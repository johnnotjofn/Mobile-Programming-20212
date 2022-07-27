package com.example.chan_nuoi_thong_minh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bieudo_temhum extends AppCompatActivity {
    LineChart bieudo_temhum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieudo_temhum);

        bieudo_temhum = findViewById(R.id.id_bieudo_temhum);
        String url = "http://192.168.1.196/app_channuoithongminh/getdata_tem_hum.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                String nhietdo = jsonObject.getString("Tem");
                                String doam = jsonObject.getString("Hum");
                                Toast.makeText(Bieudo_temhum.this, nhietdo + doam, Toast.LENGTH_SHORT).show();
                                Float nhietdo_int = Float.valueOf(nhietdo);
                                Float doam_int = Float.valueOf(doam);
                                //data.add(new Entry(nhietdo_int, doam_int));
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
//        LineDataSet lineDataSet = new LineDataSet(dataValues(), "Biểu đồ nhiệt độ, độ ẩm");
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataSet);
//
//        LineData lineData = new LineData(dataSets);
//        bieudo_temhum.setData(lineData);
//        bieudo_temhum.invalidate();
    }
    private ArrayList<Entry> dataValues(){
        ArrayList<Entry> data = new ArrayList<Entry>();

//        data.add(new Entry(0, 20));
//        data.add(new Entry(1, 24));
//        data.add(new Entry(2, 2));
//        data.add(new Entry(3, 10));
//        data.add(new Entry(4, 20));
        return data;
    }
}