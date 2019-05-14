package com.spotflock.studio.examples;

import com.spotflock.studio.StudioClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Clustering {

    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        String clusterData = "";
        String testData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            response = c.store("csv/airoplane_data.csv");
            clusterData = response.get("fileUrl").toString();
            System.out.println(clusterData);


            JSONArray features = new JSONArray();
            features.put("Activity Period");
            features.put("Operating Airline");
            response = c.cluster("clustering", "KMeansClustering", clusterData, features, new JSONObject());
            System.out.println(response.toString());

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());

            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String predFileUrl = (String) ((JSONObject) response.get("output")).get("clusterFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
