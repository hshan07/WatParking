package com.yizhangzhou.watparking;

/**
 * Created by Shiina on 2016/6/15.
 */

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class PredictionFragment extends Fragment {
    Boolean first=true;
    View major;

    List<LineChart> lineCharts = new ArrayList<LineChart>();
    String oneline;
    String oneline2;
    int flag=0;
    LocalAdapter localAdapter;

    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Entry> entries2 = new ArrayList<>();
    ArrayList<Entry> entries3 = new ArrayList<>();
    ArrayList<Entry> entries4 = new ArrayList<>();;

    ArrayList<String> labels = new ArrayList<String>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //View view = inflater.inflate(R.layout.capacity_layout, container,false);
        //TextView textView = (TextView) view.findViewById(R.id.textView);
        //textView.setText("123");

        localAdapter = ((LocalAdapter) this.getActivity().getApplicationContext());
        if(true) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("9999999");
                    BeginDraw();
                }
            }, 30*1000);

            View rootView = inflater.inflate(R.layout.prediction_layout, container, false);

            View view = inflater.inflate(R.layout.prediction_layout, container, false);

            LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
            LineChart lineChart2 = (LineChart) view.findViewById(R.id.chart2);
            LineChart lineChart3 = (LineChart) view.findViewById(R.id.chart3);
            LineChart lineChart4 = (LineChart) view.findViewById(R.id.chart4);
            lineCharts.add(lineChart);
            lineCharts.add(lineChart2);
            lineCharts.add(lineChart3);
            lineCharts.add(lineChart4);
            lineChart.setDescription("N");
            lineChart2.setDescription("W");
            lineChart3.setDescription("X");
            lineChart4.setDescription("C");



            Init_Table();
            if(localAdapter.readString("current") == ""){
                BeginDraw();

            }
            else{
                Draw();
            }
            major=view;
            return view;
        } else {
            return major;
        }



    }

    public void BeginDraw(){
        String InputString = localAdapter.readString("past");


        if(InputString == ""|| InputString.length() < 10){

            System.out.println("Not Exist!!!!");

            String[] params = {"1469102400", "1469145600","past"};
            new TheTask().execute(params);
            //Draw(InputString, false);

        }
        else{
            System.out.println("Great!!! Exist");
            getCurrent();

        }

    }

    public void getCurrent(){
        String[] params = {"1469188800", "1469232000","current"};
        new TheTask().execute(params);
    }
    class TheTask extends AsyncTask<String,Void,String>
    {

        protected void onPostExecute(String s) {
            if(s == "past"){
                getCurrent();
            }
            else if (s == "current"){
                Draw();

            }

        }


        @Override
        protected String doInBackground(String... params) {

            try{
                URL url = new URL("http://uwaterloo-server.com/parking?sdate=" + params[0] + "&edate=" + params[1]);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = in.readLine();
                if(line.length() >= 10){
                    localAdapter.save(params[2], line);
                }

                in.close();
            }catch(Exception e) {
                e.printStackTrace();
                return "ERROR";


            }
            return params[2];
        }
    }
    public void Draw() {

        String CurrentString = localAdapter.readString("current");
        String PastString = localAdapter.readString("past");
        JSONObject PastJson;
        JSONObject CurrentJson;

        ArrayList<Entry> entrie_N = new ArrayList<>();
        ArrayList<Entry> entrie_W = new ArrayList<>();
        ArrayList<Entry> entrie_X = new ArrayList<>();
        ArrayList<Entry> entrie_C = new ArrayList<>();

        try {

            entries = new ArrayList<Entry>();
            entries2 = new ArrayList<>();
            entries3 = new ArrayList<>();
            entries4 = new ArrayList<>();


            JSONObject WholePastJson = new JSONObject(PastString);

            JSONObject WholeCurrentJson = new JSONObject(CurrentString);
            PastJson=WholePastJson.getJSONObject("data");
            CurrentJson=WholeCurrentJson.getJSONObject("data");

            int zeroCount = 0;
            if(WholeCurrentJson.getInt("start_time") == 0 ){
                zeroCount = 1440;
            }
            int Past_zeroCount = (1469107574 - 1469102400)/30;
            JSONArray Current_N =CurrentJson.getJSONArray("N");

            JSONArray Current_W=CurrentJson.getJSONArray("W");

            JSONArray Current_X=CurrentJson.getJSONArray("X");

            JSONArray Current_C=CurrentJson.getJSONArray("C");


            JSONArray PastJson_N=PastJson.getJSONArray("N");

            JSONArray PastJson_W=PastJson.getJSONArray("W");

            JSONArray PastJson_X=PastJson.getJSONArray("X");

            JSONArray PastJson_C=PastJson.getJSONArray("C");

            int Midtotal = 0;
            int endTotal = 0;

            int Past_Midtotal = 0;
            int Past_endTotal = 0;
            int count=Current_N.length();
            int Past_count=PastJson_N.length();
            for(int i = 0; i<zeroCount; i++){
                entries.add(new Entry(0, i));
                entries2.add(new Entry(0,i));
                entries3.add(new Entry(0,i));
                entries4.add(new Entry(0,i));
            }


            if(zeroCount + count >  1440){
                Midtotal = 1440 - zeroCount;
                endTotal = 0;

            }
            else{

                Midtotal = count;
                endTotal = 1440 - zeroCount - count;
            }


            if(Past_zeroCount + Past_count >  1440){
                Past_Midtotal = 1440 - Past_zeroCount;
                Past_endTotal = 0;

            }
            else{

                Past_Midtotal = Past_count;
                Past_endTotal = 1440 - Past_zeroCount - Past_count;
            }

            for(int i= 0; i<Midtotal; i++){

                entries.add(new Entry(Current_N.getInt(i), i + zeroCount));
                entries2.add(new Entry(Current_W.getInt(i),i+zeroCount));
                entries3.add(new Entry(Current_X.getInt(i),i+zeroCount));
                entries4.add(new Entry(Current_C.getInt(i),i+zeroCount));
            }
            for(int i = 0; i<endTotal; i++){
                entries.add(new Entry(0, i + zeroCount + Midtotal));
                entries2.add(new Entry(0,i+zeroCount+Midtotal));
                entries3.add(new Entry(0,i+zeroCount+Midtotal));
                entries4.add(new Entry(0,i+zeroCount+Midtotal));
            }
//
            for(int i= 0; i<Past_Midtotal; i++){

                entrie_N.add(new Entry(PastJson_N.getInt(i), i + Past_zeroCount));
                entrie_W.add(new Entry(PastJson_W.getInt(i), i + Past_zeroCount));
                entrie_X.add(new Entry(PastJson_X.getInt(i), i + Past_zeroCount));
                entrie_C.add(new Entry(PastJson_C.getInt(i), i + Past_zeroCount));
            }
            for(int i = 0; i<Past_endTotal; i++){
                entrie_N.add(new Entry(0, i + Past_zeroCount + Past_Midtotal));
                entrie_W.add(new Entry(0, i + Past_zeroCount + Past_Midtotal));
                entrie_X.add(new Entry(0, i + Past_zeroCount + Past_Midtotal));
                entrie_C.add(new Entry(0, i + Past_zeroCount + Past_Midtotal));
            }




            LineDataSet dataset = new LineDataSet(entries, "Today");
            dataset.setColor(Color.RED);
            LineDataSet dataset2 = new LineDataSet(entries2, "Today");
            dataset2.setColor(Color.GREEN);
            LineDataSet dataset3 = new LineDataSet(entries3, "Today");
            dataset3.setColor(Color.BLUE);
            LineDataSet dataset4 = new LineDataSet(entries4, "Today");
            dataset4.setColor(Color.YELLOW);


            LineDataSet dataset5 = new LineDataSet(entrie_N, "Last Week");
            dataset5.setColor(Color.DKGRAY);
            LineDataSet dataset6 = new LineDataSet(entrie_W, "Last Week");
            dataset6.setColor(Color.DKGRAY);
            LineDataSet dataset7 = new LineDataSet(entrie_X, "Last Week");
            dataset7.setColor(Color.DKGRAY);
            LineDataSet dataset8 = new LineDataSet(entrie_C, "Last Week");
            dataset8.setColor(Color.DKGRAY);


            LineData data = new LineData(labels,dataset);
            data.addDataSet(dataset5);
            LineData data2 = new LineData(labels,dataset2);
            data2.addDataSet(dataset6);
            LineData data3 = new LineData(labels,dataset3);
            data3.addDataSet(dataset7);
            LineData data4 = new LineData(labels,dataset4);
            data4.addDataSet(dataset8);


            dataset.setDrawCircles(false);
            dataset.setDrawHorizontalHighlightIndicator(false);
            dataset.setDrawHighlightIndicators(true);

            dataset2.setDrawCircles(false);
            dataset2.setDrawHorizontalHighlightIndicator(false);
            dataset2.setDrawHighlightIndicators(false);

            dataset3.setDrawCircles(false);
            dataset3.setDrawHorizontalHighlightIndicator(false);
            dataset3.setDrawHighlightIndicators(false);

            dataset4.setDrawCircles(false);
            dataset4.setDrawHorizontalHighlightIndicator(false);
            dataset4.setDrawHighlightIndicators(false);

            dataset5.setDrawCircles(false);
            dataset5.setDrawHorizontalHighlightIndicator(false);
            dataset5.setDrawHighlightIndicators(false);

            dataset6.setDrawCircles(false);
            dataset6.setDrawHorizontalHighlightIndicator(false);
            dataset6.setDrawHighlightIndicators(false);

            dataset7.setDrawCircles(false);
            dataset7.setDrawHorizontalHighlightIndicator(false);
            dataset7.setDrawHighlightIndicators(false);

            dataset8.setDrawCircles(false);
            dataset8.setDrawHorizontalHighlightIndicator(false);
            dataset8.setDrawHighlightIndicators(false);





            lineCharts.get(0).setData(data);
            lineCharts.get(1).setData(data2);
            lineCharts.get(2).setData(data3);
            lineCharts.get(3).setData(data4);

            data.notifyDataChanged();
            data2.notifyDataChanged();
            data3.notifyDataChanged();
            data4.notifyDataChanged();

            int Time = 0;
            if(first){
                Time = 5000;
                first=false;
            }
            lineCharts.get(0).animateY(Time);
            lineCharts.get(1).animateY(Time);
            lineCharts.get(2).animateY(Time);
            lineCharts.get(3).animateY(Time);







        }catch(JSONException e){
            System.out.println("Draw Error");

        }



    }

    public void Init_Table(){
        for(int i=0; i<1440;i++){

            labels.add(String.valueOf(i/120 + 8));
        }
    }

}