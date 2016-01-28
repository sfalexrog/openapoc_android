package org.sfalexrog.openapoc.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sfalexrog.openapoc.R;
import org.sfalexrog.openapoc.config.Config;

/**
 * Created by Alexey on 22.01.2016.
 */
public class RendererConfigOption implements ConfigOption {

    private String optionName;
    private Config.Option option;

    public RendererConfigOption(Config.Option option) {
        this.option = option;
        this.optionName = option.toString();
    }

    @Override
    public Config.Option getOption() {
        return option;
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
        convertView = inflater.inflate(R.layout.renderer_config_option, parent, false);
        convertView.setTag(option);
        TextView tvLabel  = (TextView) convertView.findViewById(R.id.rendererTextLabel);
        tvLabel.setText(option.key());
        TextView tvValue = (TextView) convertView.findViewById(R.id.rendererOptionValue);
        tvValue.setText(Config.getInstance().getOption(option));
        return convertView;
    }
}
