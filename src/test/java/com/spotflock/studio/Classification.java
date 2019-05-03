package com.spotflock.studio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Classification {
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
            JSONObject params = new JSONObject();
            params.put("lib","weka");
            params.put("saveModel",true);
            params.put("trainPercentage",80);
            params.put("modelName","Player Churn Model");
            String trainResponse = c.train("classification", "NaiveBayesMultinomial", trainData, "player_activity", features, params);
            System.out.println(trainResponse);

            response = new JSONObject(trainResponse);
            int train_jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            String jobStatusResponse = c.jobStatus(train_jobId);
            System.out.println(jobStatusResponse);

            String jobOutputResponse = c.jobOutput(train_jobId);
            System.out.println(jobOutputResponse);

            response = new JSONObject(jobOutputResponse);
            String modelUrl = (String) ((JSONObject) response.get("output")).get("modelUrl");
            params = new JSONObject();
            params.put("lib","weka");
            String predictResponse = c.predict("classification", testData, modelUrl, params);

            response = new JSONObject(predictResponse);
            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            String predictResponseStatus = c.jobStatus(jobId);
            System.out.println(predictResponseStatus);

            String predictionOutputResponse = c.jobOutput(jobId);
            System.out.println(predictionOutputResponse);

            response = new JSONObject(predictionOutputResponse);
            String predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);

            // feedback model
            // Feedback config should be same as train config except training percentage.
            // Job id, model url, dataset url used/generated in training a model is required to feedback respective model.

            r = c.store("csv/player_feedback.csv");
            response = new JSONObject(r);
            String feedbackData = response.get("fileUrl").toString();
            System.out.println(feedbackData);

            params = new JSONObject();
            params.put("lib","weka");
            params.put("saveModel",true);
            params.put("trainPercentage",80);
            params.put("modelName","Player Churn Model");
            String feedbackResponse = c.feedback("classification", "NaiveBayesMultinomial", trainData, feedbackData, train_jobId, modelUrl, "player_activity", features, params);
            System.out.println(feedbackResponse);

            String feedbackJobStatusResponse = c.jobStatus(train_jobId);
            System.out.println(feedbackJobStatusResponse);

            String feedbackJobOutputResponse = c.jobOutput(train_jobId);
            System.out.println(feedbackJobOutputResponse);

            response = new JSONObject(feedbackJobOutputResponse);
            modelUrl = (String) ((JSONObject) response.get("output")).get("modelUrl");

            params = new JSONObject();
            params.put("lib","weka");
            predictResponse = c.predict("classification", testData, modelUrl, params);

            response = new JSONObject(predictResponse);
            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            predictResponseStatus = c.jobStatus(jobId);
            System.out.println(predictResponseStatus);

            predictionOutputResponse = c.jobOutput(jobId);
            System.out.println(predictionOutputResponse);

            response = new JSONObject(predictionOutputResponse);
            predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            predictions = c.download(predFileUrl);
            System.out.println(predictions);




        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
