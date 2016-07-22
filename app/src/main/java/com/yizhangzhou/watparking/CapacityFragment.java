package com.yizhangzhou.watparking;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;



public class CapacityFragment extends Fragment {


    List<PieChart> pieCharts = new ArrayList<PieChart>();

    Boolean first=true;
    View major;

    private PieChart mChart;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;



    LocalAdapter localAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        if(first==true) {
            View rootView = inflater.inflate(R.layout.capacity_layout, container, false);

            localAdapter = ((LocalAdapter) this.getActivity().getApplicationContext());
            localAdapter.new GetData().execute();
            localAdapter.CapcityInit(this);

            View view = inflater.inflate(R.layout.capacity_layout, container, false);

            mChart = (PieChart) view.findViewById(R.id.mChart);
            pieCharts.add(mChart);
            mChart = (PieChart) view.findViewById(R.id.mChart2);
            pieCharts.add(mChart);
            mChart = (PieChart) view.findViewById(R.id.mChart3);
            pieCharts.add(mChart);
            mChart = (PieChart) view.findViewById(R.id.mChart4);
            pieCharts.add(mChart);


            button = (Button) view.findViewById(R.id.button);
            if(localAdapter.readInt("C") == 1) {
                button.setText("Subscribed");
            }

                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(localAdapter.readInt("C") == 0) {
                        button.setText("Subscribed");
                        String[] params = {"C", "sub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("C",1);
                    }else{
                        button.setText("subscribe_C");
                        String[] params = {"C", "unsub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("C",0);
                    }
                }
            });
            button2 = (Button) view.findViewById(R.id.button2);
            if(localAdapter.readInt("N") == 1) {
                button2.setText("Subscribed");
            }
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(localAdapter.readInt("N") == 0) {
                        button2.setText("Subscribed");
                        String[] params = {"N", "sub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("N",1);

                    }else{
                        button2.setText("subscribe_N");
                        String[] params = {"N", "unsub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("N",0);
                    }
                }
            });

            button3 = (Button) view.findViewById(R.id.button3);
            if(localAdapter.readInt("W") == 1) {
                button3.setText("Subscribed");
            }
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(localAdapter.readInt("W") == 0) {
                        button3.setText("Subscribed");
                        String[] params = {"W", "sub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("W",1);
                    }else{
                        button3.setText("subscribe_W");
                        String[] params = {"W", "unsub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("W",0);

                    }
                }
            });
            button4 = (Button) view.findViewById(R.id.button4);
            if(localAdapter.readInt("X") == 1) {
                button4.setText("Subscribed");
            }
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(localAdapter.readInt("X") == 0) {
                        button4.setText("Subscribed");
                        String[] params = {"X", "sub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("X",1);
                    }else{
                        button4.setText("subscribe_X");
                        String[] params = {"X", "unsub"};
                        localAdapter.new Subscribe().execute(params);
                        localAdapter.save("X",0);

                    }
                }
            });

            localAdapter.setUp();



            Button refresh = (Button) view.findViewById(R.id.refresh);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    localAdapter.new GetData().execute();
                    ;
                }
            });
            first=false;
            major=view;
            return view;
        } else {
            return major;
        }



    }



    public void draw(){
        for(int i = 0; i < 4; i++){
            PieData mPieData = getPieData(2, 100,i);
            mChart = pieCharts.get(i);

            String lot_name = ((LocalAdapter) this.getActivity().getApplication()).parkingLots.get(i).lot_name;
            int capacity = ((LocalAdapter) this.getActivity().getApplication()).parkingLots.get(i).capacity;
            int current_count = ((LocalAdapter) this.getActivity().getApplication()).parkingLots.get(i).current_count;

            showChart(mChart, mPieData, lot_name, capacity);
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

        pieChart.setCenterText(s + "  " + i);

        //pieChart.setDraw

        pieChart.setData(pieData);

        pieChart.getLegend().setEnabled(false);

        pieChart.animateXY(1000, 1000);
    }


    private PieData getPieData(int count, float range, int i) {
        ArrayList<String> xValues = new ArrayList<String>();

        xValues.add("current");

        xValues.add("available" );


        //xValues.add

        ArrayList<Entry> yValues = new ArrayList<Entry>();


        float capacity = ((LocalAdapter) this.getActivity().getApplication()).parkingLots.get(i).capacity;
        float current_count = ((LocalAdapter) this.getActivity().getApplication()).parkingLots.get(i).current_count;

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
        pieData.setValueTextSize(10f);
        return pieData;
    }

}
