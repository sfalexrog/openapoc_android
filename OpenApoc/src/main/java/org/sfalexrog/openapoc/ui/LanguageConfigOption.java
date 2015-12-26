package org.sfalexrog.openapoc.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sfalexrog.openapoc.R;
import org.sfalexrog.openapoc.config.Config;
import org.w3c.dom.Text;

/**
 * Created by Alexey on 08.12.2015.
 */
public class LanguageConfigOption implements ConfigOption {

    @Override
    public Config.Option getOption() {
        return Config.Option.LANGUAGE;
    }

    public LanguageConfigOption() {}

    class ViewTag {
        TextView optionValue;
    }

    @Override
    public String getName() {
        return "Language";
    }

    @Override
    public String getValue() {
        Config config = Config.getInstance();
        return config.getOption(Config.Option.LANGUAGE);
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.language_config_option, parent, false);
        convertView.setTag(Config.Option.LANGUAGE);
        TextView value = (TextView) convertView.findViewById(R.id.languageOptionValue);
        value.setText(getValue());
        return convertView;
    }
}
