package org.sfalexrog.openapoc.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.sfalexrog.openapoc.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 08.12.2015.
 */
public class ConfigAdapter extends BaseAdapter {

    private Context context;

    private List<ConfigOption> configOptions = new ArrayList<>();

    public ConfigAdapter(Context context) {
        this.context = context;
        for (Config.Option option: Config.Option.values()) {
            ConfigOption cfgOption = ConfigOptionFactory.createOption(option);
            if (cfgOption != null) {
                configOptions.add(cfgOption);
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return configOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return configOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return configOptions.get(position).getView(inflater, convertView, parent);
    }
}
