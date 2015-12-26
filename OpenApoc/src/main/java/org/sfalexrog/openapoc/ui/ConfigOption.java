package org.sfalexrog.openapoc.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sfalexrog.openapoc.config.Config;

/**
 * Created by Alexey on 08.12.2015.
 */
public interface ConfigOption {
    Config.Option getOption();
    String getName();
    String getValue();
    View getView(LayoutInflater inflater, View convertView, ViewGroup parent);
}
