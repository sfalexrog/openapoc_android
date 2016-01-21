package org.sfalexrog.openapoc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.sfalexrog.openapoc.config.Config;
import org.sfalexrog.openapoc.config.DataChecker;
import org.sfalexrog.openapoc.ui.ConfigAdapter;
import org.sfalexrog.openapoc.ui.ConfigOption;
import org.sfalexrog.openapoc.util.AssetUnzipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ar.com.daidalos.afiledialog.FileChooserDialog;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "ConfigActivity";

    private ListView listView;

    private FileChooserDialog fileDialog;

    private DataChecker dataChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.init(this);

        setContentView(R.layout.activity_config);

        Toolbar cfgToolbar = (Toolbar) findViewById(R.id.configToolbar);
        setSupportActionBar(cfgToolbar);

        fileDialog = new FileChooserDialog(this, Config.getInstance().getOption(Config.Option.RES_LOCAL_DATA_DIR));
        fileDialog.setShowFullPath(true);
        dataChecker = DataChecker.INSTANCE;

        listView = (ListView) findViewById(R.id.configOptionListView);
        listView.setAdapter(new ConfigAdapter(this));
        listView.setOnItemClickListener(new ListItemDispatcher());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class ListItemDispatcher implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Adapter optionAdapter = parent.getAdapter();
            ConfigOption option = (ConfigOption) optionAdapter.getItem(position);
            switch (option.getOption()) {
                case LANGUAGE:
                    break;
                case RES_LOCAL_DATA_DIR:
                case RES_SYSTEM_DATA_DIR:
                    prepareDirDialog(option);
                    fileDialog.show();
                    break;
                case RES_LOCAL_CD_PATH:
                case RES_SYSTEM_CD_PATH:
                    prepareIsoDialog(option);
                    fileDialog.show();
                    break;
                default:
                    Log.w(TAG, "Unknown setting: " + option.getOption());
            }
        }

        private void prepareDirDialog(ConfigOption option) {
            FileChooserDialog dialog = fileDialog;
            dialog.setFolderMode(true); // Show only folders
            dialog.setCanCreateFiles(true);
            dialog.setFilter("");
            dialog.setShowOnlySelectable(true);
            dialog.setCurrentFolderName(option.getValue());
            dialog.removeAllListeners();
            dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {
                @Override
                public void onFileSelected(Dialog source, File file) {
                    new DataUnpackerTask().execute(file.getAbsolutePath());
                }

                @Override
                public void onFileSelected(Dialog source, File folder, String name) {
                    new File(folder.getAbsolutePath() + "/" + name).mkdirs();
                    Toast.makeText(ConfigActivity.this, "Created directory: " + name + " at " + folder.getAbsolutePath(), Toast.LENGTH_LONG);
                }
            });
        }

        private void prepareIsoDialog(ConfigOption option) {
            FileChooserDialog dialog = fileDialog;
            dialog.setFolderMode(false); // Show all files...
            dialog.setFilter(".*\\.[iI][sS][oO]"); // ...ending with .iso
            dialog.setShowOnlySelectable(true);
            dialog.setCanCreateFiles(false);
            dialog.removeAllListeners();
            dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {
                @Override
                public void onFileSelected(Dialog source, File file) {
                    new IsoCheckerTask().execute(file.getAbsolutePath());
                }

                @Override
                public void onFileSelected(Dialog source, File folder, String name) {

                }
            });
            dialog.setCurrentFolderName(option.getValue());
        }
    }

    class IsoCheckerTask extends AsyncTask<String, Long, Boolean> {

        ProgressDialog progressDialog;
        MessageDigest digest;

        String isoFilePath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ConfigActivity.this, ProgressDialog.THEME_HOLO_DARK);
            progressDialog.setTitle("Checking ISO file...");
            progressDialog.setMessage("Calculating MD5 sum");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(10000);
            progressDialog.setOnCancelListener(new ProgressDialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    IsoCheckerTask.this.cancel(true);
                }
            });
            progressDialog.setOnCancelListener(new ProgressDialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    IsoCheckerTask.this.cancel(true);
                }
            });
            progressDialog.show();
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, "Could not find hashing algorithm!", e);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if (aBoolean) {
                Config.getInstance().setOption(Config.Option.RES_LOCAL_CD_PATH, isoFilePath);
                fileDialog.dismiss();
                Toast.makeText(ConfigActivity.this, "CD path changed successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ConfigActivity.this, "Did not set CD path: Incorrect hash!", Toast.LENGTH_LONG).show();
            }
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            int progress = (int) ((values[1] * 10000) / values[0]);
            progressDialog.setProgress(progress);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            isoFilePath = params[0];
            File isoFile = new File(isoFilePath);
            if (!isoFile.exists()) {
                return false;
            }
            long fileSize = isoFile.length();
            long alreadyRead = 0;
            try {
                InputStream is = new FileInputStream(isoFile);
                byte[] buffer = new byte[8192];
                int readLen;
                while((readLen = is.read(buffer)) > 0) {
                    alreadyRead += readLen;
                    digest.update(buffer, 0, readLen);
                    publishProgress(fileSize, alreadyRead);
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Could not open iso file!", e);
            } catch (IOException e) {
                Log.e(TAG, "Error reading iso file!", e);
            }
            String hash = new BigInteger(1, digest.digest()).toString(16);

            return dataChecker.isValidData(hash);
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
            Toast.makeText(ConfigActivity.this, "Did not set CD path: cancelled by user!", Toast.LENGTH_LONG);
        }
    }

    class DataUnpackerTask extends AsyncTask<String, String, Void> {

        ProgressDialog progressDialog;

        String dataPath;

        @Override
        protected Void doInBackground(String... params) {
            AssetUnzipper unzipper = new AssetUnzipper(ConfigActivity.this);
            unzipper.setProgressReportCallback( new AssetUnzipper.ProgressReportCallback() {
                @Override
                public void onSetFileCount(int count) {
                    // Will probably fail here
                    // No touching Views from non-UI thread :(
                    //progressDialog.setMax(count);
                }

                @Override
                public void onReportProgress(String filename, int progress) {
                    publishProgress(filename, Integer.toString(progress));
                }
            });
            dataPath = params[0];
            unzipper.unzipAssets(dataPath);
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ConfigActivity.this, ProgressDialog.THEME_HOLO_DARK);
            progressDialog.setTitle("Unpacking assets...");
            progressDialog.setMessage("<NO FILE>");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(10000);
            progressDialog.setCancelable(false); // Unzip operation interruption not allowed.
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);
            // I'm gonna burn in hell for this one
            progressDialog.setProgress(Integer.parseInt(values[1]));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            ConfigActivity.this.fileDialog.dismiss();
            Config.getInstance().setOption(Config.Option.RES_LOCAL_DATA_DIR, dataPath + "/data");
            Toast.makeText(ConfigActivity.this, "Unpacked assets to " + dataPath, Toast.LENGTH_LONG);
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }
}
