package com.spotflock.studio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Clustering {

    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        String r = "";
        String clusterData = "";
        String testData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            r = c.store("csv/airoplane_data.csv");
            response = new JSONObject(r);
            clusterData = response.get("fileUrl").toString();
            System.out.println(clusterData);


            JSONArray features = new JSONArray();
            features.put("Activity Period");
            features.put("Operating Airline");
            String clusterResponse = c.cluster("clustering", "KMeansClustering", clusterData, features, new JSONObject());
            System.out.println(clusterResponse);

            response = new JSONObject(clusterResponse);
            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            String jobStatusResponse = c.jobStatus(jobId);
            System.out.println(jobStatusResponse);

            String jobOutputResponse = c.jobOutput(jobId);
            System.out.println(jobOutputResponse);

            response = new JSONObject(jobOutputResponse);
            String predFileUrl = (String) ((JSONObject) response.get("output")).get("clusterFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
