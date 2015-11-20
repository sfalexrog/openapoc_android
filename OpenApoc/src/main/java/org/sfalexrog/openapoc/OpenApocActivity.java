package org.sfalexrog.openapoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.liballeg.android.AllegroActivity;

public class OpenApocActivity extends AllegroActivity {

    static {
        System.loadLibrary("physfs");
        System.loadLibrary("allegro");
        System.loadLibrary("icuuc55");
        System.loadLibrary("tinyxml2");
    }

    public OpenApocActivity() {
        super("libopenapoc.so");
    }

}
