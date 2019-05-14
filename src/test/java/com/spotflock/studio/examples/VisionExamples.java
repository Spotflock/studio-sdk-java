package com.spotflock.studio.examples;

import com.spotflock.studio.StudioClient;
import org.json.JSONArray;

import java.io.IOException;

public class VisionExamples {

    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        try {

            String response = c.faceDetection("img/fd-actual-img.jpg");
            System.out.println(response);
            JSONArray responseJson = c.faceDetectionJson("img/fd-actual-img.jpg");
            System.out.println(responseJson.toString());
            response = c.licensePlateDetection("img/lp-actual-img.jpg");
            System.out.println(response);
            responseJson = c.licensePlateDetectionJson("img/lp-actual-img.jpg");
            System.out.println(responseJson.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
