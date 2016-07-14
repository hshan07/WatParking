package com.yizhangzhou.watparking;

/**
 * Created by Shiina on 2016/6/15.
 */

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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PredictionFragment extends Fragment {
    Boolean first=true;
    View major;
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Entry> entries2 = new ArrayList<>();
    ArrayList<Entry> entries3 = new ArrayList<>();
    ArrayList<Entry> entries4 = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    String oneline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //View view = inflater.inflate(R.layout.capacity_layout, container,false);
        //TextView textView = (TextView) view.findViewById(R.id.textView);
        //textView.setText("123");

        if(first==true) {
            new TheTask().execute();

            View rootView = inflater.inflate(R.layout.prediction_layout, container, false);

            View view = inflater.inflate(R.layout.prediction_layout, container, false);

            LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
            LineChart lineChart2 = (LineChart) view.findViewById(R.id.chart2);
            LineChart lineChart3 = (LineChart) view.findViewById(R.id.chart3);
            LineChart lineChart4 = (LineChart) view.findViewById(R.id.chart4);
//            // creating list of entry
//
//



            entries.add(new Entry(4f, 0));
            entries.add(new Entry(6f, 1));
            entries.add(new Entry(6f, 2));
            entries.add(new Entry(2f, 3));
            entries.add(new Entry(18f, 4));
            entries.add(new Entry(9f, 5));
            entries.add(new Entry(10f, 6));

            entries2.add(new Entry(10f, 0));
            entries2.add(new Entry(7f, 1));
            entries2.add(new Entry(4f, 2));
            entries2.add(new Entry(3f, 3));
            entries2.add(new Entry(5f, 4));
            entries2.add(new Entry(0f, 5));
            entries2.add(new Entry(9f, 6));

            entries3.add(new Entry(1f, 0));
            entries3.add(new Entry(2f, 1));
            entries3.add(new Entry(3f, 2));
            entries3.add(new Entry(4f, 3));
            entries3.add(new Entry(5f, 4));
            entries3.add(new Entry(6f, 5));
            entries3.add(new Entry(7f, 6));

            entries4.add(new Entry(6f, 0));
            entries4.add(new Entry(6f, 1));
            entries4.add(new Entry(6f, 2));
            entries4.add(new Entry(6f, 3));
            entries4.add(new Entry(6f, 4));
            entries4.add(new Entry(6f, 5));
            entries4.add(new Entry(6f, 6));

            LineDataSet dataset = new LineDataSet(entries, "# of Calls");
            LineDataSet dataset2 = new LineDataSet(entries2, "# of Calls");
            LineDataSet dataset3 = new LineDataSet(entries3, "# of Calls");
            LineDataSet dataset4 = new LineDataSet(entries4, "# of Calls");

            labels.add("8:00");
            labels.add("10:00");
            labels.add("12:00");
            labels.add("2:00");
            labels.add("4:00");
            labels.add("6:00");
            labels.add("8:00");
////


            LineData data = new LineData(labels,dataset);
            LineData data2 = new LineData(labels,dataset2);
            LineData data3 = new LineData(labels,dataset3);
            LineData data4 = new LineData(labels,dataset4);

            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            dataset.setDrawCubic(true);
            dataset.setDrawFilled(true);

            dataset2.setColors(ColorTemplate.COLORFUL_COLORS); //
            dataset2.setDrawCubic(true);
            dataset2.setDrawFilled(true);

            dataset3.setColors(ColorTemplate.COLORFUL_COLORS); //
            dataset3.setDrawCubic(true);
            dataset3.setDrawFilled(true);

            dataset4.setColors(ColorTemplate.COLORFUL_COLORS); //
            dataset4.setDrawCubic(true);
            dataset4.setDrawFilled(true);


            lineChart.setData(data);
            lineChart2.setData(data2);
            lineChart3.setData(data3);
            lineChart4.setData(data4);

            data.notifyDataChanged();
            lineChart.animateY(5000);
            lineChart2.animateY(5000);
            lineChart3.animateY(5000);
            lineChart4.animateY(5000);

            first=false;
            major=view;
            return view;
        } else {
            return major;
        }



    }

    class TheTask extends AsyncTask<String,String,String>
    {


        protected void onPostExecute(Void result) {
            try {
                JSONObject jObjs = new JSONObject(oneline);
                //jObjs.getJSONObject("meta").getJSONObject("status") == 200;
                JSONArray jArray = jObjs.getJSONArray("data");
                System.out.println(jArray.getJSONObject(0).getString("date"));


            }catch(JSONException e){
                System.out.println("Error Message2");

            }

        }


        @Override
        protected String doInBackground(String... params) {
            try {


                // read text returned by server
//                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//                String line = in.readLine();
//                in.close();

                HttpClient client = new DefaultHttpClient();
                HttpPost parkingp= new HttpPost("http://uwaterloo-server.com/parking");
                JSONObject object = new JSONObject();
                try {
                    object.accumulate("lot_name", "N");
                    object.accumulate("date", "2016-07-06");

                } catch (Exception ex) {

                }

                try {
                    String message = object.toString();

                    StringEntity se =new StringEntity(message);
                    parkingp.setEntity(se);
                }
                catch(Exception e) {
                    System.out.println("7-------------6");
                    e.printStackTrace();

                }

                parkingp.setHeader("Accept", "application/json");
                parkingp.setHeader("Content-type", "application/json");

                System.out.println("6-------------6");
                HttpResponse response = client.execute(parkingp);
                InputStream inputStream = null;
                System.out.println("8-------------7");
                inputStream = response.getEntity().getContent();
                System.out.println("9999999-------------6");
                if(inputStream != null) {
                    oneline = convertInputStreamToString(inputStream);
                }
                else
                    oneline = "Did not work!";

            }catch(Exception e) {
                System.out.println("99-------------99");
                e.printStackTrace();


            }
            return "End";


        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        System.out.println("I am here     yyyyyyyyyyyyyyyyy");
        System.out.println(result);
        return result;
    }




}