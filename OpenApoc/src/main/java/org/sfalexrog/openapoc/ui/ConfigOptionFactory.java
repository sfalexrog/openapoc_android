package org.sfalexrog.openapoc.ui;

import android.util.Log;

import org.sfalexrog.openapoc.config.Config;

/**
 * Created by Alexey on 08.12.2015.
 */
public class ConfigOptionFactory {
    private static final String TAG = "ConfigOptionFactory";
    public static ConfigOption createOption(Config.Option option)
    {
        switch (option) {
            case LANGUAGE:
                return new LanguageConfigOption();
            case RES_LOCAL_CD_PATH:
            case RES_LOCAL_DATA_DIR:
            //case RES_SYSTEM_CD_PATH:
            //case RES_SYSTEM_DATA_DIR:
                return new DataDirConfigOption(option);
            case VISUAL_RENDERERS:
                return new RendererConfigOption(option);
            case VISUAL_SCALE_X:
            case VISUAL_SCALE_Y:
                return new ScaleConfigOption(option);
            default:
                Log.w(TAG, "Creating Option " + option.toString() + " not yet implemented!");
                return null;
        }
    }
}
