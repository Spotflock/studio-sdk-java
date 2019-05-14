package com.spotflock.studio.examples;

import com.spotflock.studio.StudioClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Classification {
    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        String trainData = "";
        String testData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            response = c.store("csv/player_train.csv");

            trainData = response.get("fileUrl").toString();
            System.out.println(trainData);

            response = c.store("csv/player_test.csv");

            testData = response.get("fileUrl").toString();
            System.out.println(testData);


            JSONArray features = new JSONArray();
            features.put("stamina");
            features.put("challenges");
            features.put("achievements");
            JSONObject params = new JSONObject();
            params.put("lib", "weka");
            params.put("saveModel", true);
            params.put("trainPercentage", 80);
            params.put("modelName", "Player Churn Model");
            JSONObject trainResponse = c.train("classification", "NaiveBayesMultinomial", trainData, "player_activity", features, params);
            System.out.println(trainResponse.toString());

            int train_jobId = (Integer) ((JSONObject) trainResponse.get("data")).get("jobId");

            response = c.jobStatus(train_jobId);
            System.out.println(response.toString());

            response = c.jobOutput(train_jobId);
            System.out.println(response.toString());

            String modelUrl = (String) ((JSONObject) response.get("output")).get("modelUrl");
            params = new JSONObject();
            params.put("lib", "weka");
            response = c.predict("classification", testData, modelUrl, params);

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());

            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);

            // feedback model
            // Feedback config should be same as train config except training percentage.
            // Job id, model url, dataset url used/generated in training a model is required to feedback respective model.

            response = c.store("csv/player_feedback.csv");

            String feedbackData = response.get("fileUrl").toString();
            System.out.println(feedbackData);

            params = new JSONObject();
            params.put("lib", "weka");
            params.put("saveModel", true);
            params.put("trainPercentage", 80);
            params.put("modelName", "Player Churn Model");
            response = c.feedback("classification", "NaiveBayesMultinomial", trainData, feedbackData, train_jobId, modelUrl, "player_activity", features, params);
            System.out.println(response.toString());

            response = c.jobStatus(train_jobId);
            System.out.println(response.toString());

            response = c.jobOutput(train_jobId);
            System.out.println(response.toString());


            modelUrl = (String) ((JSONObject) response.getJSONObject("output")).get("modelUrl");

            params = new JSONObject();
            params.put("lib", "weka");
            response = c.predict("classification", testData, modelUrl, params);

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());

            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
