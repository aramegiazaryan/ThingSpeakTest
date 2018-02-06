package com.example.aram.thingspeaktest;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_temp;
    private TextView tv_press;
    private Button btn_send;
//    private  static final String  API_KEY_WRITE_TEMPERATURE="G7P12C649PWYB136";
//    private  static final String  API_KEY_READ_TEMPERATURE="SW574S9PYCWGIF5T";
//    private  static final String  API_KEY_WRITE_PRESSURE="022C55MBXDIBJXRH";
//    private  static final String  API_KEY_READ_PRESSURE="51AUQUUTQWPXX7ND";
    private  static final String  API_KEY_WRITE_TEMPERATURE="XZ0JUM4OON3R6Y4L";
    private  static final String  API_KEY_READ_TEMPERATURE="185HQC73LVE3S8VC";
    private  static final String  API_KEY_WRITE_PRESSURE="022C55MBXDIBJXRH";
    private  static final String  API_KEY_READ_PRESSURE="51AUQUUTQWPXX7ND";
    private  static final String  CHANNEL="418538";
    private Api client;
    private Button btn_get;
    private DataPoint dataPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       tv_temp=(TextView) findViewById(R.id.tv_temp);
      // tv_press=(TextView) findViewById(R.id.tv_pressure);
       btn_send=(Button) findViewById(R.id.btn_send);
       btn_send.setOnClickListener(this);
        btn_get=(Button) findViewById(R.id.btn_get);
        btn_get.setOnClickListener(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thingspeak.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         client = retrofit.create(Api.class);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:{
                if(!tv_temp.getText().toString().isEmpty()){
                    //  double temp=Float.parseFloat(tv_temp.getText().toString());
               //     String press=tv_press.getText().toString();
                    String temp=tv_temp.getText().toString();
                    //   final double  press=Float.parseFloat(tv_press.getText().toString());

                    client.saveData(API_KEY_WRITE_TEMPERATURE,temp).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Toast.makeText(MainActivity.this, "Temperature save data "+response.body().toString(), Toast.LENGTH_SHORT).show();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_temp.setText(null);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                 /*   client.saveData(API_KEY_WRITE_PRESSURE,press).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Toast.makeText(MainActivity.this, "Pressure save data "+response.body().toString(), Toast.LENGTH_SHORT).show();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_press.setText(null);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error send Data Pressure", Toast.LENGTH_SHORT).show();
                        }
                    });*/

                }
                break;
            }

            case R.id.btn_get: {
                client.gateData(CHANNEL,API_KEY_READ_TEMPERATURE,50).enqueue(new Callback<ResultSchema>() {
                    public Date date;

                    @Override
                    public void onResponse(Call<ResultSchema> call, Response<ResultSchema> response) {

                        List<Feed> result = response.body().getFeeds();
                        GraphView graph = (GraphView) findViewById(R.id.graph);
                        DataPoint [] dataPoints = new DataPoint[result.size()];
                        for (int i = 0; i < result.size(); i++) {
                          date =  result.get(i).getCreatedAt();
                            dataPoints[i] = new DataPoint(i,Double.valueOf(result.get(i).getField1()));
                          //  Toast.makeText(MainActivity.this, result.get(i).getField1(), Toast.LENGTH_SHORT).show();
                        }
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
                    //    series.setDrawDataPoints(true);
                       // series.setTitle("Temperature");
                        series.setAnimated(true);
                       // series.appendData(new DataPoint(35,Double.valueOf(result.get(22).getField1())),true,50,true);
                        graph.addSeries(series);

                    }

                    @Override
                    public void onFailure(Call<ResultSchema> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }





    }
}
