package com.spotflock.studio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        String r = "";
        String trainData = "";
        String testData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            r = c.store("csv/player_train.csv");
            response = new JSONObject(r);
            trainData = response.get("fileUrl").toString();
            System.out.println(trainData);

            r = c.store("csv/player_test.csv");
            response = new JSONObject(r);
            testData = response.get("fileUrl").toString();
            System.out.println(testData);


            JSONArray features = new JSONArray();
            features.put("stamina");
            features.put("challenges");
            features.put("achievements");
            String trainResponse = c.train("classification", "NaiveBayesMultinomial", trainData, "player_activity", features, new JSONObject());
            System.out.println(trainResponse);

            response = new JSONObject(trainResponse);
            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            String jobStatusResponse = c.jobStatus(jobId);
            System.out.println(jobStatusResponse);

            String jobOutputResponse = c.jobOutput(jobId);
            System.out.println(jobOutputResponse);

            response = new JSONObject(jobOutputResponse);
            String modelUrl = (String) ((JSONObject) response.get("output")).get("modelUrl");
            String predictResponse = c.predict("classification", testData, modelUrl, new JSONObject());

            response = new JSONObject(predictResponse);
            jobId = (Integer) response.get("data");

            String predictResponseStatus = c.jobStatus(jobId);
            System.out.println(predictResponseStatus);

            String predictionOutputResponse = c.jobOutput(jobId);
            System.out.println(predictionOutputResponse);

            response = new JSONObject(predictionOutputResponse);
            String predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
