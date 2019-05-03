package com.spotflock.studio;

import java.io.IOException;

public class VisionExamples {

    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        try {
            String response = c.faceDetection("img/fd-actual-img.jpg");
            System.out.println(response);
            response = c.faceDetectionJson("img/fd-actual-img.jpg");
            System.out.println(response);
            response = c.licensePlateDetection("img/lp-actual-img.jpg");
            System.out.println(response);
            response = c.licensePlateDetectionJson("img/lp-actual-img.jpg");
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
