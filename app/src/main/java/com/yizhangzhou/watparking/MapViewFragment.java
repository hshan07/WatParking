package com.yizhangzhou.watparking;

/**
 * Created by Shiina on 2016/6/15.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapViewFragment extends Fragment {
    protected ArrayList<JSONObject>search_result=new ArrayList<JSONObject>();
    protected JSONArray fetch_result;
    Boolean first=true;
    View major;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // TODO Auto-generated method stub
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        if(first==true) {

            final View view = inflater.inflate(R.layout.map_layout, container, false);

            EditText myTextBox = (EditText) view.findViewById(R.id.SearchBar);

            //System.out.println("MAP VIEW CREATED");



            myTextBox.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                   // System.out.println("***************************");
                    int len = fetch_result.length();
                    search_result.clear();
                    for (int i = 0; i < len; i++) {
                        try {
                            if (fetch_result.getJSONObject(i).getString("building_code").contains(s.toString().toUpperCase())) {
                                search_result.add(fetch_result.getJSONObject(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                   // System.out.println(fetch_result.length() + "--------------------------------------------------" + search_result.size());
                    ListView l2 = (ListView) view.findViewById(R.id.BuildinglistView);
                    l2.invalidateViews();

                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {


                }
            });

            System.out.println("111111111111111");

            String data_String = null;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(getActivity().getAssets().open("building.json")));
                data_String = reader.readLine();
                System.out.println(data_String);
               // System.out.print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject json = new JSONObject(data_String);

                fetch_result = json.getJSONArray("data");
            } catch (Exception e) {
                e.printStackTrace();
            }


            ListView l1 = (ListView) view.findViewById(R.id.BuildinglistView);
            BaseAdapter adapter = new BuildingAdapter(search_result);

            ListView list = (ListView) view.findViewById(R.id.BuildinglistView);
            list.setAdapter(adapter);

        ListView l3 = (ListView) view.findViewById(R.id.BuildinglistView);
        l3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
               //System.out.println("row selected: " + position );
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("Myclass",search_result.get(position).toString());
                startActivity(intent);

            }
        });
            first=false;
            major=view;
            return view;
        }else{
            return major;

        }


        //return inflater.inflate(R.layout.map_layout, container,false);
    }




    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class BuildingAdapter extends BaseAdapter {
        private ArrayList<JSONObject> BuildingArray;

        public BuildingAdapter(ArrayList<JSONObject> json_array) {
            super();
            BuildingArray = json_array;
        }

        @Override
        public JSONObject getItem (int position)
        {
            return BuildingArray.get(position);

        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return BuildingArray.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //System.out.println("now print row:" + position);
            // Get the data item for this position
            JSONObject buildingJson = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            View row;
            LayoutInflater inflater = getLayoutInflater(null);

            convertView = inflater.inflate(R.layout.buildingview, null);
            TextView BuildingCode = (TextView) convertView.findViewById(R.id.BuildingCode);
            TextView BuildingName = (TextView) convertView.findViewById(R.id.BuildingName);
            // Populate the data into the template view using the data object
            try {
                //System.out.println(buildingJson.getString("building_code"));
                // System.out.println("=============================================");
                BuildingCode.setText(buildingJson.getString("building_code"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                BuildingName.setText(buildingJson.getString("building_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }



}
