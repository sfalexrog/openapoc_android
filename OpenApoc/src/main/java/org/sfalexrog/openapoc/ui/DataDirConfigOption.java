package org.sfalexrog.openapoc.ui;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.sfalexrog.openapoc.R;
import org.sfalexrog.openapoc.config.Config;

/**
 * Created by Alexey on 08.12.2015.
 */
public class DataDirConfigOption implements ConfigOption {

    private String optionName;
    private Config.Option option;

    @Override
    public Config.Option getOption() {
        return option;
    }

    DataDirConfigOption(Config.Option option) {
        this.option = option;
        this.optionName = option.toString();
    }

    @Override
    public String getName() {
        return optionName;
    }

    @Override
    public String getValue() {
        return Config.getInstance().getOption(option);
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.data_dir_config_option, parent, false);
        convertView.setTag(option);
        TextView tvLabel  = (TextView) convertView.findViewById(R.id.dataDirConfigLabel);
        tvLabel.setText(option.key());
        TextView tvValue = (TextView) convertView.findViewById(R.id.dataDirConfigValue);
        tvValue.setText(Config.getInstance().getOption(option));
        return convertView;
    }
}
