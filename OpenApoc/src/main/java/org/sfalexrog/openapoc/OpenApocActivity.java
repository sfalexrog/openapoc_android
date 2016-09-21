package org.sfalexrog.openapoc;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.libsdl.app.SDLActivity;
import org.sfalexrog.openapoc.config.Config;

import java.util.ArrayList;
import java.util.List;

public class OpenApocActivity extends SDLActivity {

    private final static String TAG = "OpenApocActivity";

    private static OpenApocActivity mOpenApocActivity;

    private AssetManager assetManager;

    static {
        /*System.loadLibrary("physfs");
        System.loadLibrary("allegro");
        System.loadLibrary("icuuc55");
        System.loadLibrary("tinyxml2");*/
        System.loadLibrary("openapoc");
    }

    private Config config;

    private boolean extractionRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Black_NoTitleBar);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            extractionRun = intent.getBooleanExtra("GenerateRulesets", false);
        } else {
            extractionRun = false;
        }
        config = Config.init(this);
        assetManager = getAssets();
        mOpenApocActivity = this;

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    @Override
    protected String[] getLibraries() {
        return new String[] {"openapoc"};
    }

    @Override
    protected String[] getArguments() {
        List<String> argsList = new ArrayList<>();

        if (extractionRun) {
            argsList.add("--extract-data");
        }
        argsList.add("--enable-tracing");

        for (Config.Option option : Config.Option.values()) {
            if (option.isPassed()) {
                argsList.add(option.key() + "=" + config.getOption(option));
            }
        }
        String[] args = new String[argsList.size()];
        args = argsList.toArray(args);
        Log.i(TAG, "Built args list:");
        for (String arg: argsList) {
            Log.i(TAG, arg);
        }
        return args;
    }
}
