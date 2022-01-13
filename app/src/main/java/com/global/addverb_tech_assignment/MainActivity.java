package com.global.addverb_tech_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView displayRegionRV;
    private String borders = "", capital, region, subregion, flag, common, population;
    private String languages = "";
    private static final String TAG = "MainActivity";
    public static List<DisplayRegionModel> displayRegionModelArrayList;
    private DisplayRegionModel displayRegionModel;
    private displayRegionAdapter regionAdapter;
    private Button delBtn;
    private ImageView refreshBtn;
    private ProgressBar progressBar;
    private TextView loadingTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayRegionRV = findViewById(R.id.displayRegionRv);
        delBtn = findViewById(R.id.delBtn);
        refreshBtn = findViewById(R.id.refreshButton);
        progressBar = findViewById(R.id.progress_circular);
        loadingTxt = findViewById(R.id.loadingTxt);
        displayRegionModelArrayList = new ArrayList<>();

        //
        boolean isInternetConnected = internetIsConnected();

        // to recall the api , if there is some issue
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInternetConnected2 = internetIsConnected();
                if (isInternetConnected2) {
                    refreshBtn.setClickable(false);
                    progressBar.setVisibility(View.VISIBLE);
                    loadingTxt.setVisibility(View.VISIBLE);
                    fetchData();
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // to delete room database
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllRegionList();

            }
        });


        if (isInternetConnected) {
            progressBar.setVisibility(View.GONE);
            loadingTxt.setVisibility(View.GONE);
            fetchData();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            loadingTxt.setVisibility(View.VISIBLE);
            getAllRegionList();
        }


    }

    // to check if user is connected to internet or not
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    // api call to fetch data from api and showing it on recyclerview
    private void fetchData() {

        // url of api
        String url = "https://restcountries.com/v3.1/region/asia";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                        JSONArray jsonArrayNew = new JSONArray(response);


                        for (int i = 0; i < jsonArrayNew.length(); i++) {

                            JSONObject jsonObject = jsonArrayNew.getJSONObject(i);

                            // to check if json object contain borders as key and if then adding borders in a string
                            if (jsonObject.has("borders")) {
                                JSONArray bordersArray = jsonObject.getJSONArray("borders");
                                borders = "";
                                for (int j = 0; j < bordersArray.length(); j++) {
                                    borders = borders + String.valueOf(bordersArray.get(j)) + ", ";

                                }
                            }

                            // to check if json object contain languages as key and if then adding languages in a string

                            if (jsonObject.has("languages")) {
                                JSONObject languagesObject = jsonObject.getJSONObject("languages");

                                if (languagesObject.names().length() != 0) {
                                    for (int n = 0; n < languagesObject.names().length(); n++) {

                                        Log.d(TAG, "fetchData: " + languagesObject.names().length());
                                        Log.d(TAG, "fetchData: " + languagesObject.names().get(n));
                                        String key = languagesObject.names().get(n).toString();
                                        if (languagesObject.has(key)) {

                                            languages = languages + languagesObject.getString(key) + ", ";

                                        }

                                    }
                                }

                            }
                            // to check if json object contain capital as key and if then adding capital in a string

                            if (jsonObject.has("capital")) {
                                JSONArray captialArray = jsonObject.getJSONArray("capital");
                                capital = String.valueOf(captialArray.get(0));
                            } else {
                                capital = " No Capitals";
                            }

                            // to check if json object contain flags as key and if then adding flags in a string

                            if (jsonObject.has("flags")) {
                                JSONObject flagsObject = jsonObject.getJSONObject("flags");

                                flag = flagsObject.getString("png");

                            } else {
                                flag = "";
                            }
                            // to check if json object contain region as key and if then adding region in a string

                            if (jsonObject.has("region")) {
                                region = jsonObject.getString("region");
                            } else {
                                region = "";
                            }

                            // to check if json object contain subregion as key and if then adding subregion in a string

                            if (jsonObject.has("subregion")) {
                                subregion = jsonObject.getString("subregion");

                            } else {
                                subregion = "";
                            }

                            // to check if json object contain population as key and if then adding population in a string

                            if (jsonObject.has("population")) {
                                population = jsonObject.getString("population");

                            } else {
                                population = "";
                            }


                            common = jsonObject.getJSONObject("name").getString("common");


                            displayRegionModel = new DisplayRegionModel(common, capital, borders.substring(0, borders.length() - 2), flag, region, subregion, population, languages.substring(0, languages.length() - 2));

                            // adding the displayRegionModel to the list
                            displayRegionModelArrayList.add(displayRegionModel);
                            languages = "";
                        }

                        regionAdapter = new displayRegionAdapter(MainActivity.this, displayRegionModelArrayList);

                        displayRegionRV.setHasFixedSize(true);
                        displayRegionRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        // setting the adapter for the recyclerview
                        displayRegionRV.setAdapter(regionAdapter);

                        progressBar.setVisibility(View.GONE);
                        loadingTxt.setVisibility(View.GONE);
                        refreshBtn.setClickable(true);


                        // inserting the data in room database
                        insertMutipleRegionList();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "fetchData: error" + e.getMessage());

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // showing data from room database if there is any problem showing data from api
                        getAllRegionList();
                        progressBar.setVisibility(View.GONE);
                        loadingTxt.setVisibility(View.GONE);
                        refreshBtn.setClickable(true);


                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    // to fetch all details of countries from room database
    public void getAllRegionList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<DisplayRegionModel> displayRegionModelList = MyRoomDatabase.getINSTANCE(getApplicationContext())
                        .regionDao()
                        .getAll();
                for (int i = 0; i < displayRegionModelList.size(); i++) {
                    Log.d(TAG, "run: " + displayRegionModelList.get(i).getCommon());

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        regionAdapter = new displayRegionAdapter(MainActivity.this, displayRegionModelList);

                        displayRegionRV.setHasFixedSize(true);
                        displayRegionRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        displayRegionRV.setAdapter(regionAdapter);

                        regionAdapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        loadingTxt.setVisibility(View.GONE);
                    }
                });

            }
        });
        thread.start();
    }

    // doing task in background
    class InsertAsyncTask extends AsyncTask<DisplayRegionModel, Void, Void> {

        @Override
        protected Void doInBackground(DisplayRegionModel... displayRegionModels) {

            MyRoomDatabase.getINSTANCE(getApplicationContext())
                    .regionDao()
                    .insert(displayRegionModels[0]);
            return null;
        }
    }

    // Delete data from room database
    public void deleteAllRegionList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<DisplayRegionModel> displayRegionModelList = MyRoomDatabase.getINSTANCE(getApplicationContext())
                        .regionDao()
                        .getAll();


                MyRoomDatabase.getINSTANCE(getApplicationContext())
                        .regionDao()
                        .delete(displayRegionModelList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayRegionRV.setAdapter(null);
                        regionAdapter.notifyDataSetChanged();
                    }
                });

                Log.d(TAG, "run: to do has been deleted");
            }
        });
        thread.start();
    }

    // to insert data into room database
    public void insertMutipleRegionList() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                MyRoomDatabase.getINSTANCE(getApplicationContext())
                        .regionDao()
                        .insertmutipleRegion(displayRegionModelArrayList);

                Log.d(TAG, "run: region list has been inserted");
            }
        }).start();
    }

}