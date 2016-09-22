package com.example.raghav.estanciapremierleague;

import android.app.Activity;
import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "0xVug6ZtoXFuUHPoLZ06rZ9vpEX5TJO2of7q0CwQ", "6usWQ0xZwhmtfac5KkCvp7tMldfAbNwhkuDOFUuC");
    }
}
