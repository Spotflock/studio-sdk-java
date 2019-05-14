package com.spotflock.studio.examples;

import com.spotflock.studio.StudioClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Regression {
    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        String trainData = "";
        String testData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            response = c.store("csv/housing_train.csv");
            trainData = response.get("fileUrl").toString();
            System.out.println(trainData);

            response = c.store("csv/housing_test.csv");
            testData = response.get("fileUrl").toString();
            System.out.println(testData);


            JSONArray features = new JSONArray();
            features.put("LotShape");
            features.put("Street");
            response = c.train("regression", "LinearRegression", trainData, "SalePrice", features, new JSONObject());
            System.out.println(response.toString());

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());


            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String modelUrl = (String) ((JSONObject) response.get("output")).get("modelUrl");
            response = c.predict("regression", testData, modelUrl, new JSONObject());
            System.out.println(response.toString());

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());

            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
