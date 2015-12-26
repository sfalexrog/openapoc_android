package org.sfalexrog.openapoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.libsdl.app.SDLActivity;
import org.sfalexrog.openapoc.config.Config;

import java.util.ArrayList;
import java.util.List;

public class OpenApocActivity extends SDLActivity {

    private final static String TAG = "OpenApocActivity";

    static {
        /*System.loadLibrary("physfs");
        System.loadLibrary("allegro");
        System.loadLibrary("icuuc55");
        System.loadLibrary("tinyxml2");*/
        System.loadLibrary("openapoc");
    }

    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Black_NoTitleBar);
        super.onCreate(savedInstanceState);
        config = Config.init(this);
    }

    @Override
    protected String[] getLibraries() {
        return new String[] {"openapoc"};
    }

    @Override
    protected String[] getArguments() {
        List<String> argsList = new ArrayList<>();
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
