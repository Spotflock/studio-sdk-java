package com.spotflock.studio.examples;

import com.spotflock.studio.StudioClient;
import org.json.JSONObject;

import java.io.IOException;

public class NLPExample {
    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        JSONObject r = null;
        try {
            r = c.sentimentAnalysis("I am feeling sick.");
            System.out.print(r);
            r = c.posTagger("He was working with confidence.");
            System.out.print(r);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
