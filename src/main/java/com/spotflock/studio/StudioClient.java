package com.spotflock.studio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StudioClient {
    private static final String USER_AGENT = "Mozilla/5.0";
    String baseUrl = null;
    String apiKey = null;

    public StudioClient(String apiKey) {

        this.apiKey = apiKey;
        this.baseUrl = "https://studio.spotflock.com/api/v1";
    }

    public String sentimentAnalysis(String text) throws IOException, JSONException {

        String url = baseUrl + "/language-service/phoenix-language/nlp/sentiment";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("text", text);
        return sendPost(url, headers, body);

    }

    public String posTagger(String text) throws IOException, JSONException {
        String url = baseUrl + "/language-service/phoenix-language/nlp/pos";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("text", text);
        return sendPost(url, headers, body);
    }

    public String faceDetection(String filePath) throws IOException {
        String response = "";
        String charset = "UTF-8";
        String requestURL = this.baseUrl + "/vision-service/phoenix-vision/face-detection/image";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();

        return response;
    }

    public String faceDetectionJson(String filePath) throws IOException {

        String response = "";
        String charset = "UTF-8";
        String requestURL = this.baseUrl + "/vision-service/phoenix-vision/face-detection/json";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();
        return response;
    }

    public String licensePlateDetection(String filePath) throws IOException {
        String response = "";
        String charset = "UTF-8";
        String requestURL = this.baseUrl + "/vision-service/phoenix-vision/license-plate/image";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();

        return response;
    }

    public String licensePlateDetectionJson(String filePath) throws IOException {
        String response = "";
        String charset = "UTF-8";
        String requestURL = this.baseUrl + "/vision-service/phoenix-vision/license-plate/json";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();
        return response;
    }

    public String train(String service, String algorithm, String datasetUrl, String label, JSONArray features, JSONObject params) throws IOException, JSONException {
        String response = "";

        if(!params.has("modelName"))
            params.put("modelName", algorithm);
        if(!params.has("trainPercentage"))
            params.put("trainPercentage", 80);
        if(!params.has("saveModel"))
            params.put("saveModel", true);
        if(!params.has("lib"))
            params.put("lib", "weka");

        String url = baseUrl + "/ml-service/phoenix-ml/" + service + "/train";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
//        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("library", params.remove("lib"));
        body.put("task", "train");
        JSONObject config = new JSONObject();
        config.put("name", params.remove("modelName"));
        config.put("algorithm", algorithm);
        config.put("datasetUrl", datasetUrl);
        config.put("label", label);
        config.put("trainPercentage", params.remove("trainPercentage"));
        config.put("features", features);
        config.put("saveModel", params.remove("saveModel"));
        config.put("params", params);
        body.put("config", config);
        return sendPost(url, headers, body);

    }

    public String cluster(String service, String algorithm, String datasetUrl, JSONArray features, JSONObject params) throws IOException, JSONException {
        String response = "";


        if(!params.has("modelName"))
            params.put("modelName", algorithm);
        if(!params.has("numOfClusters"))
            params.put("numOfClusters", 2);
        if(!params.has("saveModel"))
            params.put("saveModel", true);
        if(!params.has("lib"))
            params.put("lib", "weka");


        String url = baseUrl + "/ml-service/phoenix-ml/cluster";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
//        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("library", params.remove("lib"));
        body.put("task", "CLUSTER");
        body.put("service", service);
        JSONObject config = new JSONObject();
        config.put("name", params.remove("modelName"));
        config.put("algorithm", algorithm);
        config.put("datasetUrl", datasetUrl);
        config.put("epsilon", 0.1);
        config.put("numOfClusters", params.remove("numOfClusters"));
        config.put("features", features);
        config.put("saveModel", params.remove("saveModel"));
        config.put("params", params);
        body.put("config", config);
        return sendPost(url, headers, body);

    }


    public String feedback(String service, String algorithm, String datasetUrl, String feedbackDatasetUrl, int jobId, String modelUrl, String label, JSONArray features, JSONObject params) throws IOException, JSONException
    {
        String response = "";

        if(!params.has("modelName"))
            params.put("modelName", algorithm);
        if(!params.has("trainPercentage"))
            params.put("trainPercentage", 80);
        if(!params.has("saveModel"))
            params.put("saveModel", true);
        if(!params.has("lib"))
            params.put("lib", "weka");

        String url = baseUrl + "/ml-service/phoenix-ml/" + service + "/feedback";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
//        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("library", params.remove("lib"));
        body.put("task", "FEEDBACK");
        body.put("service", service);
        JSONObject config = new JSONObject();
        config.put("name", params.remove("modelName"));
        config.put("jobId", jobId);
        config.put("algorithm", algorithm);
        config.put("datasetUrl", datasetUrl);
        config.put("feedbackDatasetUrl", feedbackDatasetUrl);
        config.put("modelUrl", modelUrl);
        config.put("label", label);
        config.put("trainPercentage", params.remove("trainPercentage"));
        config.put("features", features);
        config.put("saveModel", params.remove("saveModel"));
        config.put("params", params);
        body.put("config", config);
        return sendPost(url, headers, body);

    }


    public String predict(String service, String datasetUrl, String modelUrl, JSONObject params) throws IOException {

        String url = this.baseUrl + "/ml-service/phoenix-ml/" + service + "/predict";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("library", params.remove("lib"));
        JSONObject config = new JSONObject();
        config.put("params", params);
        config.put("datasetUrl", datasetUrl);
        config.put("modelUrl", modelUrl);
        body.put("config", config);
        return sendPost(url, headers, body);

    }

    public String store(String filePath) throws IOException {
        String response = "";
        String requestURL = this.baseUrl + "/solution-service/cloud-solution/storage";
        String charset = "UTF-8";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();
        return response;
    }

    public String jobStatus(Integer jobId) throws IOException, InterruptedException {

        String url = this.baseUrl + "/ml-service/phoenix-ml/job/status?id=" + jobId;
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        String res = sendGet(url, headersMap);
        JSONObject resJson = new JSONObject(res);
        String state = (String) resJson.get("state");
        if (state.equals("FAIL")) {
            throw new RuntimeException("Prediction job failed! ");
        }
        while (state.equals("RUN")) {
            res = sendGet(url, headersMap);
            resJson = new JSONObject(res);
            state = (String) resJson.get("state");
            Thread.sleep(5000);
        }
        return res;

    }

    public String jobOutput(Integer jobId) throws IOException {

        String url = this.baseUrl + "/ml-service/phoenix-ml/output/findBy?jobId=" + jobId;
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        return sendGet(url, headersMap);

    }

    public String download(String fileUrl) throws IOException {

        String url = this.baseUrl + "/solution-service/cloud-solution/storage/?url=" + fileUrl;
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        return sendGet(url, headersMap);

    }


    private String sendPost(String urlStr, HashMap<String, String> headersMap, JSONObject bodyJson) throws IOException, JSONException {

        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        URL url = null;
        url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setInstanceFollowRedirects(false);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        for (Map.Entry<String, String> header : headersMap.entrySet()) {
            con.setRequestProperty(header.getKey(), header.getValue());
        }
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        String bodyStr = bodyJson.toString();
        wr.write(bodyStr);
        wr.flush();
        wr.close();
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        Reader streamReader = null;
        int status = con.getResponseCode();
        if (status < HttpURLConnection.HTTP_BAD_REQUEST) {
            streamReader = new InputStreamReader(con.getInputStream());
        } else {
            streamReader = new InputStreamReader(con.getErrorStream());
        }
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    private String sendGet(String urlStr, HashMap<String, String> headersMap) throws IOException, JSONException {

        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        URL url = null;
        url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("GET");
        for (Map.Entry<String, String> header : headersMap.entrySet()) {
            con.setRequestProperty(header.getKey(), header.getValue());
        }

        con.setConnectTimeout(5000 * 4);
        con.setReadTimeout(5000 * 4);
        Reader streamReader = null;
        int status = con.getResponseCode();
        if (status < HttpURLConnection.HTTP_BAD_REQUEST) {
            streamReader = new InputStreamReader(con.getInputStream());
        } else {
            streamReader = new InputStreamReader(con.getErrorStream());
        }
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

}