package com.example.kontakt;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class appKlasa extends Application {

    public static final String APPLICATION_ID = "61A62031-C7E8-EF03-FF4A-2B856B6BBF00";
    public static final String API_KEY = "603B6D4A-1B38-462F-950A-8F07B8E75AFE";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser korisnik;
    public static List<Kontakt> kontakt;

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );

    }
}
