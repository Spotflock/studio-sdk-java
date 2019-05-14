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

    /**
     * This is Spotflock Studio Java SDK Client for v1.3.0.
     *         For more information, visit https://studio.spotflock.com/documentation/
     *
     *
     * @param apiKey API Key Generated for an app in Spotflock Studio.
     */

    public StudioClient(String apiKey) {

        this.apiKey = apiKey;
        this.baseUrl = "https://studio.spotflock.com/api/v1";
    }

    /**
     *  The function to call sentiment analysis service in Phoenix Language.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-language/
     *
     * @param text The text on which sentiment analysis is to be applied.
     * @return A json object containing sentiment analysis response.
     * @throws IOException
     * @throws JSONException
     */

    public JSONObject sentimentAnalysis(String text) throws IOException, JSONException {

        String url = baseUrl + "/language-service/phoenix-language/nlp/sentiment";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("text", text);
        return new JSONObject(sendPost(url, headers, body));

    }

    /**
     * The function to call pos tagger service in Phoenix Language.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-language/
     *
     * @param text The text on which POS analysis is to be applied.
     * @return A json obj containing POS tagger response.
     * @throws IOException
     * @throws JSONException
     */

    public JSONObject posTagger(String text) throws IOException, JSONException {
        String url = baseUrl + "/language-service/phoenix-language/nlp/pos";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
        headers.put("User-Agent", USER_AGENT);
        headers.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("text", text);
        return  new JSONObject(sendPost(url, headers, body));
    }

    /**
     * The function to call face detection service in Phoenix Vision.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-vision/
     *
     * @param filePath The path of the image file.
     * @return A base64 decoded image with face detected.
     * @throws IOException
     */

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

    /**
     * The function to call face detection service in Phoenix Vision.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-vision/
     *
     * @param filePath The path of the image file.
     * @return  A list(JSONArray) of co-ordinates for all faces detected in the image.
     * @throws IOException
     */

    public JSONArray faceDetectionJson(String filePath) throws IOException {

        String response = "";
        String charset = "UTF-8";
        String requestURL = this.baseUrl + "/vision-service/phoenix-vision/face-detection/json";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();
        return  new JSONArray(response);
    }

    /**
     *The function to call license-plate detection service in Phoenix Vision.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-vision/
     *
     * @param filePath The path of the image file.
     * @return A base64 decoded image with face detected.
     * @throws IOException
     */

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

        return  response;
    }

    /**
     * The function to call license-plate detection service in Phoenix Vision.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-vision/
     * @param filePath The path of the image file.
     * @return A list(JSONArray) of co-ordinates all license plates detected in the image.
     * @throws IOException
     */
    public JSONArray licensePlateDetectionJson(String filePath) throws IOException {
        String response = "";
        String charset = "UTF-8";
        String requestURL = this.baseUrl + "/vision-service/phoenix-vision/license-plate/json";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();
        return  new JSONArray(response);
    }

    /**
     *  The function call to train service in Phoenix ML.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/"> Spotflock Studio </a>
     *
     * @param service Valid parameter values are classification, regression.
     * @param algorithm algorithm by which model will be trained.
     * @param datasetUrl dataset file location in spotflock storage.
     * @param label label of the column in dataset file.
     * @param features column name list which is used to train classification model.
     * @param params JSON object containing additional parameters like lib, modelName, saveModel, trainPercentage.
     *               each of the parameter have default values as mentioned below.
     *               <ul>
     *                 <li>lib: valid values for this params are weka, spotflock, H2O, scikit (default: weka)</li>
     *                 <li>modelName: name by which model will be saved. (default: Algorithm Name will be used.)</li>
     *                 <li>saveModel: boolean param indicating saving the model in cloud storage. (default: true)</li>
     *                 <li>trainPercentage: % dataset splitting for training and testing. (default: 80%)</li>
     *               </ul>
     *
     * @return Json object containing the training job details.
     * @throws IOException
     * @throws JSONException
     */

    public JSONObject train(String service, String algorithm, String datasetUrl, String label, JSONArray features, JSONObject params) throws IOException, JSONException {

        if (!params.has("modelName"))
            params.put("modelName", algorithm);
        if (!params.has("trainPercentage"))
            params.put("trainPercentage", 80);
        if (!params.has("saveModel"))
            params.put("saveModel", true);
        if (!params.has("lib"))
            params.put("lib", "weka");

        String url = baseUrl + "/ml-service/phoenix-ml/" + service + "/train";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
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
        return  new JSONObject(sendPost(url, headers, body));

    }

    /**
     *  The function call to cluster service in Phoenix ML.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/
     *
     * @param service Valid parameter values are CLUSTER.
     * @param algorithm algorithm by which model will be trained.
     * @param datasetUrl dataset file location in spotflock storage.
     * @param features column name list which is used for clustering.
     * @param params JSON object containing additional parameters like lib, modelName, saveModel, numOfClusters.
     *                     each of the parameter have default values as mentioned below.
     *                     <ul>
     *                       <li>lib: valid values for this params are weka, spotflock, H2O, scikit (default: weka)</li>
     *                       <li>modelName: name by which model will be saved. (default: Algorithm Name will be used.)</li>
     *                       <li>saveModel: boolean param indicating saving the model in cloud storage. (default: true)</li>
     *                       <li>numOfClusters: number of cluster, (default: 2)</li>
     *                     </ul>
     *
     * @return A json obj containing model info.
     * @throws IOException
     * @throws JSONException
     */

    public JSONObject cluster(String service, String algorithm, String datasetUrl, JSONArray features, JSONObject params) throws IOException, JSONException {


        if (!params.has("modelName"))
            params.put("modelName", algorithm);
        if (!params.has("numOfClusters"))
            params.put("numOfClusters", 2);
        if (!params.has("saveModel"))
            params.put("saveModel", true);
        if (!params.has("lib"))
            params.put("lib", "weka");


        String url = baseUrl + "/ml-service/phoenix-ml/cluster";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("ApiKey", this.apiKey);
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
        return  new JSONObject(sendPost(url, headers, body));

    }


    /**
     *   The function call to feedback service in Phoenix ML.
     *
     * @param service Trained model's service.
     * @param algorithm Trained model's algorithm.
     * @param datasetUrl Trained model's dataset url.
     * @param feedbackDatasetUrl feedback_data:
     *                 a)Dataset (used for feedback) file location in spotflock storage.
     *                 b)Feedback dataset upload. IMP: Please ensure the dataset has all features used for training the model.
     * @param jobId Job_id from training API response.
     * @param modelUrl Model file location in spotflock storage.
     * @param label Trained model's label.
     * @param features Trained model's features.
     * @param params JSON object containing additional parameters like lib, modelName, saveModel, trainPercentage.
     *                     each of the parameter have default values as mentioned below.
     *                     <ul>
     *                      <li>lib: valid values for this params are weka, spotflock, H2O, scikit (default: weka)</li>
     *                       <li>modelName: name by which model will be saved. (default: Algorithm Name will be used.)</li>
     *                       <li>saveModel: boolean param indicating saving the model in cloud storage. (default: true)</li>
     *                       <li>trainPercentage: % dataset splitting for training and testing. (default: 80%)</li>
     *                     </ul>
     * @return A json obj containing feedback model info.
     * @throws IOException
     * @throws JSONException
     */

    public JSONObject feedback(String service, String algorithm, String datasetUrl, String feedbackDatasetUrl, int jobId, String modelUrl, String label, JSONArray features, JSONObject params) throws IOException, JSONException {
        String response = "";

        if (!params.has("modelName"))
            params.put("modelName", algorithm);
        if (!params.has("trainPercentage"))
            params.put("trainPercentage", 80);
        if (!params.has("saveModel"))
            params.put("saveModel", true);
        if (!params.has("lib"))
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
        return  new JSONObject(sendPost(url, headers, body));

    }

    /**
     *
     * The function call to predict service in Phoenix ML.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/
     *
     * @param service Valid parameter values are classification, regression.
     * @param datasetUrl dataset file location in spotflock storage.
     * @param modelUrl trained model location in spotflock storage.
     * @param params JSON object containing additional parameters like lib, modelName, saveModel, trainPercentage.
     *                           each of the parameter have default values as mentioned below.
     *                           <ul>
     *                            <li>lib: valid values for this params are weka, spotflock, H2O, scikit (default: weka)</li>
     *                             </ul>
     * @return  A json obj containing the file info which has the predictions.
     * @throws IOException
     */

    public JSONObject predict(String service, String datasetUrl, String modelUrl, JSONObject params) throws IOException {

        if (!params.has("lib"))
            params.put("lib", "weka");

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
        return  new JSONObject(sendPost(url, headers, body));

    }


    /**
     * The function call to store in Phoenix ML.
     *              For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/
     *
     * @param filePath The path of the dataset file.
     * @return A json obj containing the file path in storage.
     * @throws IOException
     */

    public JSONObject store(String filePath) throws IOException {
        String response = "";
        String requestURL = this.baseUrl + "/solution-service/cloud-solution/storage";
        String charset = "UTF-8";
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
        multipart.addFilePart("file", new File(filePath));
        response = multipart.finish();
        return  new JSONObject(response);
    }

    /**
     * The function call to get the job status in Phoenix ML.
     *             For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/
     *
     * @param jobId jobId from the train api response.
     * @return A json obj containing the status details.
     * @throws IOException
     * @throws InterruptedException
     */

    public JSONObject jobStatus(Integer jobId) throws IOException, InterruptedException {

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
        return  new JSONObject(res);

    }

    /**
     *   The function call to get the job output in Phoenix ML.
     *                 For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/
     *
     * @param jobId jobId from the train api response.
     * @return  A json obj containing the job output details.
     * @throws IOException
     */

    public JSONObject jobOutput(Integer jobId) throws IOException {

        String url = this.baseUrl + "/ml-service/phoenix-ml/output/findBy?jobId=" + jobId;
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("ApiKey", this.apiKey);
        headersMap.put("User-Agent", USER_AGENT);
        return new JSONObject(sendGet(url, headersMap));

    }

    /**
     *  The function call to download the prediction file in Phoenix ML.
     *                 For more information, visit https://studio.spotflock.com/docs/ai/phoenix-ml/
     *
     * @param fileUrl location url of file stored in cloud storage.
     * @return file content in simple text format.
     * @throws IOException
     */

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