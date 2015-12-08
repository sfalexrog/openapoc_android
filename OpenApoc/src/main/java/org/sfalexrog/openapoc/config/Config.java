package org.sfalexrog.openapoc.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * Created by Alexey on 06.12.2015.
 */
public class Config {

    private class OnPrefEditListener implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.apply();
        }
    }

    public enum Option {
        LANGUAGE ("Language", "en_gb"),
        GAMERULES ("GameRules", "XCOMAPOC.XML"),
        RES_LOCAL_DATA_DIR("Resource.LocalDataDir", null),
        RES_SYSTEM_DATA_DIR("Resource.SystemDataDir", null),
        RES_LOCAL_CD_PATH("Resource.LocalCDPath", null),
        RES_SYSTEM_CD_PATH("Resource.SystemCDPath", null),
        VISUAL_RENDERERS("Visual.Renderers", "GLES_3_0:GLES_2_0"),
        AUDIO_BACKENDS("Audio.Backends", "SDLRaw:allegro:null"),
        FRAMEWORK_THREADPOOL_SIZE("Framework.TreadPoolSize", "0");

        private final String key;
        private final String defaultValue;

        Option(String key, String defaultValue) {
            this.key = key;
            if (defaultValue != null) {
                this.defaultValue = defaultValue;
            } else {
                String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                if (key.contains("DataDir")) {
                    this.defaultValue = externalStoragePath + "/OpenApoc/data";
                } else if (key.contains("CDPath")) {
                    this.defaultValue = externalStoragePath + "/OpenApoc/cd.iso";
                } else {
                    this.defaultValue = null;
                }
            }
        }

        public String key() { return key; }
        public String value() { return defaultValue; }
    }

    public String getOption(Option option) {
        return mPrefs.getString(option.key(), option.value());
    }

    private static final String SP_NAME = "OpenApocPreferences";

    public String getLanguage() {
        return mPrefs.getString(Option.LANGUAGE.key(), Option.LANGUAGE.value());
    }

    public String getGamerules() {
        return mPrefs.getString(Option.GAMERULES.key(), Option.GAMERULES.value());
    }

    public String getLocalDataDir() {
        return mPrefs.getString(Option.RES_LOCAL_DATA_DIR.key(), Option.RES_LOCAL_DATA_DIR.value());
    }

    public String getSystemDataDir() {
        return mPrefs.getString(Option.RES_SYSTEM_DATA_DIR.key(), Option.RES_SYSTEM_DATA_DIR.value());
    }

    public String getLocalCdPath() {
        return mPrefs.getString(Option.RES_LOCAL_CD_PATH.key(), Option.RES_LOCAL_CD_PATH.value());
    }

    public String getSystemCdPath() {
        return mPrefs.getString(Option.RES_SYSTEM_CD_PATH.key(), Option.RES_SYSTEM_CD_PATH.value());
    }

    public String getRenderers() {
        return mPrefs.getString(Option.VISUAL_RENDERERS.key(), Option.VISUAL_RENDERERS.value());
    }

    public String getAudioBackends() {
        return mPrefs.getString(Option.AUDIO_BACKENDS.key(), Option.VISUAL_RENDERERS.value());
    }

    public int getThreadpoolSize() {
        int prefThreadpoolSize = Integer.parseInt(Option.FRAMEWORK_THREADPOOL_SIZE.value());
        return mPrefs.getInt(Option.FRAMEWORK_THREADPOOL_SIZE.key(), prefThreadpoolSize);
    }

    private Context mContext;

    private SharedPreferences mPrefs;

    private static Config INSTANCE = null;

    public static Config getInstance() { return INSTANCE; }

    public static Config init(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Config(context);
        }
        return INSTANCE;
    }

    public Config save() {
        SharedPreferences.Editor editor = mPrefs.edit();
        for (Option pref: Option.values()) {
            editor.putString(pref.key(), pref.value());
        }
        return this;
    }

    public Config setDefaults() {
        SharedPreferences.Editor editor = mPrefs.edit();
        for (Option pref: Option.values()) {
            editor.putString(pref.key(), pref.value());
        }
        return this;
    }

    private Config(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mPrefs.registerOnSharedPreferenceChangeListener(new OnPrefEditListener());
        setDefaults();
    }
}
