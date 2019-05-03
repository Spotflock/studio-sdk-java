package com.spotflock.studio;

import org.json.JSONException;

import java.io.IOException;

public class NLPExample {
    public static void main(String[] args) {

        StudioClient c = new StudioClient("xxx");
        String r = null;
        try {
            r = c.sentimentAnalysis("I am feeling sick.");
            System.out.print(r);
            r = c.posTagger("He was working with confidence.");
            System.out.print(r);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
