package com.example.mami.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.bean.AmmendMent;
import com.example.mami.myapplication.database.DatabaseAccess;
import com.example.mami.myapplication.lisenar.SpanClickLisenar;
import com.example.mami.myapplication.viewbinder.DirectoryNodeBinder;
import com.example.mami.myapplication.viewbinder.FileNodeBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

/**
 * Created by Mami on 12/16/2017.
 */



public class FragmentNote extends Fragment {

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Button btGetData;
    TextView tvLocalData;
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_note, container, false);
        btGetData=view.findViewById(R.id.btGetData);
        tvLocalData=view.findViewById(R.id.tvLocalData);
        btGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetMethodDemo().execute("http://www.claimanttech.com/");
            }
        });
        return view;
    }



    public class GetMethodDemo extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.v("CatalogClient", "1  "+strings[0]);
                url = new URL(strings[0]);
                Log.v("CatalogClient", "2  "+url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                Log.v("CatalogClient", "3  "+urlConnection);
                int responseCode = urlConnection.getResponseCode();
                Log.v("CatalogClient", "responseCode  "+responseCode);

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }
                return server_response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("CatalogClient", "dcvvg responseCode  "+e.toString());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                tvLocalData.setText(s);
            }
            Log.e("CatalogClient", "Last " + server_response);


        }
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }



}
