package org.sfalexrog.openapoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.libsdl.app.SDLActivity;

public class OpenApocActivity extends SDLActivity {

    static {
        /*System.loadLibrary("physfs");
        System.loadLibrary("allegro");
        System.loadLibrary("icuuc55");
        System.loadLibrary("tinyxml2");*/
        System.loadLibrary("openapoc");
    }

    @Override
    protected String[] getLibraries() {
        return new String[] {"openapoc"};
    }

    @Override
    protected String[] getArguments() {
        String[] args = new String[] {
                "Resource.SystemCDPath=/sdcard/openapoc/cd.iso",
                "Resource.LocalCDPath=/sdcard/openapoc/cd.iso",
                "Resource.SystemDataDir=/sdcard/openapoc",
                "Resource.LocalDataDir=/sdcard/openapoc"
        };
        return args;
    }

}
