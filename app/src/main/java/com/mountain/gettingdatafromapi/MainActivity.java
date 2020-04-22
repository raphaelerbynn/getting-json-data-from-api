package com.mountain.gettingdatafromapi;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data);

        if (internetAvailable()) {

            DataFetching processData = new DataFetching();
            processData.execute();

        }else {

            data.setText("There is NO INTERNET CONNECTION");
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();

        }

    }

    protected boolean internetAvailable(){
        boolean have_WiFi = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info : networkInfos){

            if(info.getTypeName().equalsIgnoreCase("WIFI")){
                if (info.isConnected()){
                    have_WiFi = true;
                }
            }
            if(info.getTypeName().equalsIgnoreCase("MOBILE")){
                if (info.isConnected()){
                    have_MobileData = true;
                }
            }

        }

        return have_WiFi || have_MobileData;


    }



    public class DataFetching extends AsyncTask<Void, Void, Void> {

        String data;
        String dataParsed;



        @Override
        protected Void doInBackground(Void... voids) {



            try {

                URL url = new URL("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
//            URL url = new URL("https://coronavirus-19-api.herokuapp.com/all/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String lineData = "";

                while (lineData != null){

                    lineData = bufferedReader.readLine();

                    data = data + lineData;

                }

                JSONArray jsonArray = new JSONArray(data);
                for (int i=0; i<jsonArray.length(); i++){

                    jsonArray.get(i);




                }



            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainActivity.data.setText(data);
//            Log.i("myData", data);
        }



    }
}
