package com.brynhildr.asgard.connection;


import android.os.AsyncTask;
import android.util.Log;

import com.brynhildr.asgard.dblayout.relationships.RelationshipDatabase;
import com.brynhildr.asgard.entity.entities.RelationWithID;
import com.brynhildr.asgard.global.MyApplication;
import com.brynhildr.asgard.global.RemoteServerInformation;
import com.brynhildr.asgard.local.UpdateLocalRelationships;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lqshan on 11/18/15.
 */

public class GetRelationsFromRemote extends AsyncTask<Void, Integer, String> {

    private static final String TAG = "HttpGetTask";
    private static final String URL = RemoteServerInformation.URL_SERVER
            + RemoteServerInformation.URL_GET_RELATIONSHIPS;

    protected String doInBackground(Void... para1) {
        String data = "";
        HttpURLConnection httpUrlConnection = null;
        System.out.println("Start to perform background task.");
        try {
            httpUrlConnection = (HttpURLConnection) new URL(URL)
                    .openConnection();

            InputStream in = new BufferedInputStream(
                    httpUrlConnection.getInputStream());

            MyApplication.startUsingDatabase();

            UpdateLocalRelationships updateLocalRelationships = new UpdateLocalRelationships(in);
            RelationshipDatabase relationshipDatabase = new RelationshipDatabase(MyApplication.getAppContext());
            updateLocalRelationships.compareAndUpdate(relationshipDatabase);

            MyApplication.completeUsingDatabase();

            ArrayList<RelationWithID> relationWithIDs = relationshipDatabase.readAllRows();


            for (int i = 0; i < relationWithIDs.size(); ++i) {
                System.out.println("Event ID = " + relationWithIDs.get(i).getEventID());
                System.out.println("user name = " + relationWithIDs.get(i).getUserName());
                System.out.println("Primary ID = " + relationWithIDs.get(i).getPrimaryID());
            }

            in.close();
        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
        } finally {
            if (null != httpUrlConnection)
                httpUrlConnection.disconnect();
        }
        //return data;
        System.out.println(data);
        return data;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        //showDialog("Downloaded " + result + " bytes");
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer("");
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
}