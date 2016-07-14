package com.yizhangzhou.watparking;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class CapacityFragment extends Fragment {

    public class ParkingLot{
        public String lot_name;
        public double latitude;
        public double longitude;
        public int capacity;
        public int current_count;
        public int percent_filled;
        public String last_updated;
    }
    String line;

    List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
    List<PieChart> pieCharts = new ArrayList<PieChart>();
    List<Integer> tests = new ArrayList<Integer>();
    Boolean first=true;
    View major;

    private PieChart mChart;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private int flag=0;
    private int flag2=0;
    private int flag3=0;
    private int flag4=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //View view = inflater.inflate(R.layout.capacity_layout, container,false);
        //TextView textView = (TextView) view.findViewById(R.id.textView);
        //textView.setText("123");

        if(first==true) {
            View rootView = inflater.inflate(R.layout.capacity_layout, container, false);
            new GetData().execute();

            View view = inflater.inflate(R.layout.capacity_layout, container, false);

            //pieCharts.clear();
            //parkingLots.clear();
            //if (pieCharts.size() == 0) {
            mChart = (PieChart) view.findViewById(R.id.mChart);
            pieCharts.add(mChart);
            mChart = (PieChart) view.findViewById(R.id.mChart2);
            pieCharts.add(mChart);
            mChart = (PieChart) view.findViewById(R.id.mChart3);
            pieCharts.add(mChart);
            mChart = (PieChart) view.findViewById(R.id.mChart4);
            pieCharts.add(mChart);

            button = (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag == 0) {
                        button.setText("Subscribed");
                        new GetData().execute();
                        flag=1;
                    }else{
                        button.setText("subscribe_C");
                        flag=0;
                    }
                }
            });
            button2 = (Button) view.findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag2 == 0) {
                        button2.setText("Subscribed");
                        new GetData().execute();
                        flag2=1;
                    }else{
                        button2.setText("subscribe_N");
                        flag2=0;
                    }
                }
            });
            button3 = (Button) view.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag3 == 0) {
                        button3.setText("Subscribed");
                        new GetData().execute();
                        flag3=1;
                    }else{
                        button3.setText("subscribe_W");
                        flag3=0;
                    }
                }
            });
            button4 = (Button) view.findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag4 == 0) {
                        button4.setText("Subscribed");
                        new GetData().execute();
                        flag4=1;
                    }else{
                        button4.setText("subscribe_X");
                        flag4=0;
                    }
                }
            });


            //}

            Button refresh = (Button) view.findViewById(R.id.refresh);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new GetData().execute();
                }
            });
            first=false;
            major=view;
            return view;
        } else {
            return major;
        }



    }



    private class GetData extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls){
            try {
                URL url = new URL("https://api.uwaterloo.ca/v2/parking/watpark.json?key=e0b4a8543ba4a9a371f2bece0edfc351");

                // read text returned by server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                line = in.readLine();
                in.close();
            } catch(Exception e) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error Message");
                alertDialog.setMessage("Please check the network connection");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                getActivity().finish();
                System.exit(0);

            }


            return null;
        }


        protected void onPostExecute(Void unused) {
            //System.out.println(line);
            //System.out.println("ON POST EXECUTE");
            try {
                JSONObject jObjs = new JSONObject(line);
                //jObjs.getJSONObject("meta").getJSONObject("status") == 200;
                JSONArray jArray = jObjs.getJSONArray("data");

                parkingLots.clear();
                for(int i = 0; i < jArray.length();i++){
                    JSONObject jsObj = jArray.getJSONObject(i);
                    ParkingLot parkingLot = new ParkingLot();
                    parkingLot.lot_name = jsObj.getString("lot_name");
                    parkingLot.latitude = jsObj.getDouble("latitude");
                    parkingLot.longitude = jsObj.getDouble("longitude");
                    parkingLot.capacity = jsObj.getInt("capacity");
                    parkingLot.current_count = jsObj.getInt("current_count");
                    parkingLot.percent_filled = jsObj.getInt("percent_filled");
                    parkingLot.last_updated = jsObj.getString("last_updated");
                    parkingLots.add(parkingLot);
                }

            }catch(JSONException e){
                System.out.println("Error Message2");

            }
            //System.out.println("FINISH JSON");
            draw();

        }

    }

    public void draw(){
        //System.out.println("HERE");
        for(int i = 0; i < 4; i++){
            //String lot_name = parkingLots.get(i).lot_name;
            PieData mPieData = getPieData(2, 100,i);
            mChart = pieCharts.get(i);
            showChart(mChart, mPieData,parkingLots.get(i).lot_name, parkingLots.get(i).capacity-parkingLots.get(i).current_count);
        }
    }


    private void showChart(PieChart pieChart, PieData pieData, String s,int i) {
        //System.out.println("HERE1");
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleRadius(38f);

        pieChart.setDescription("");
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90);

        pieChart.setRotationEnabled(true);


        pieChart.setCenterText( s + "  " + i);

        pieChart.setData(pieData);

        pieChart.getLegend().setEnabled(false);

        pieChart.animateXY(1000, 1000);
    }


    private PieData getPieData(int count, float range, int i) {
        //System.out.println("HERE2");
        ArrayList<String> xValues = new ArrayList<String>();

        xValues.add("current_count: ");

        xValues.add("available: " );

        ArrayList<Entry> yValues = new ArrayList<Entry>();



        float capacity = parkingLots.get(i).capacity;
        float current_count = parkingLots.get(i).current_count;
        yValues.add(new Entry(current_count, 0));
        yValues.add(new Entry(capacity - current_count, 1));


        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(0f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(255, 160, 122));
        colors.add(Color.rgb(205, 205, 205)); // grey

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px);

        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }

}
