package org.sfalexrog.openapoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.sfalexrog.openapoc.config.Config;
import org.sfalexrog.openapoc.util.AssetUnzipper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class PreloaderActivity extends Activity {

    private Config config;
    private AssetUnzipper unzipper;

    private static final int REQCODE_CONFIG = 1;
    private static final int REQCODE_GAME = 2;

    private static final String TAG = "PreloaderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        config = Config.init(this);
        unzipper = new AssetUnzipper(this);
        checkSanity();
    }

    private void onSanityCheckComplete(boolean isSane) {
        if (isSane) {
            runGame();
        } else {
            runConfig();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQCODE_CONFIG:
                // Returning from config: attempt to start the game proper?
                // Check the config for sanity
                checkSanity();
                break;
            case REQCODE_GAME:
                // TODO: Implement a successful/unsuccessful loading check
                finish();
                break;
            default:
                Log.w(TAG, "Unknown request code: " + requestCode);
        }
    }

    private void checkSanity() {
        // Configuration checks!
        // Check if ISO is found (it _COULD_ have the wrong MD5, but hey,
        // why would you break the game deliberately?!
        File isoFile = new File(config.getOption(Config.Option.RES_LOCAL_CD_PATH));
        if (!isoFile.exists()) {
            isoFile = new File(config.getOption(Config.Option.RES_SYSTEM_CD_PATH));
        }
        if (!isoFile.exists()) {
            onSanityCheckComplete(false);
        }
        // Check if our data dir even exists
        File dataDir = new File(config.getOption(Config.Option.RES_LOCAL_DATA_DIR));
        if (!dataDir.exists()) {
            dataDir = new File(config.getOption(Config.Option.RES_SYSTEM_DATA_DIR));
        }
        if (!dataDir.exists()) {
            onSanityCheckComplete(false);
        }
        // TODO: Add more checks?
        // Update data if required
        if (needsUpdating()) {
            new DataUnpackerTask().execute(config.getOption(Config.Option.RES_LOCAL_DATA_DIR));
        } else {
            onSanityCheckComplete(true);
        }
    }

    private boolean needsUpdating() {
        // No update needed if ISO can't be found
        File gameIsoFile = new File(config.getOption(Config.Option.RES_LOCAL_CD_PATH));
        if (!gameIsoFile.exists()) {
            // Check both paths before bailing out
            gameIsoFile = new File(config.getOption(Config.Option.RES_SYSTEM_CD_PATH));
        }
        if (!gameIsoFile.exists()) {
            return false;
        }
        Set<String> storedHashes = config.getOptionSet(Config.Option.ANDROID_DATA_HASH);
        Set<String> assetHashes = new HashSet<>();
        for (String assetName : unzipper.getAssetDataZips()) {
            assetHashes.add(assetName + ":" + unzipper.getAssetHash(assetName));
        }
        return assetHashes.size() != storedHashes.size() || !assetHashes.containsAll(storedHashes);
    }

    private void runConfig() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivityForResult(intent, REQCODE_CONFIG);
    }

    private void runGame() {
        Intent intent = new Intent(this, OpenApocActivity.class);
        startActivityForResult(intent, REQCODE_GAME);
    }

    class DataUnpackerTask extends AsyncTask<String, String, Void> {

        ProgressDialog progressDialog;

        String dataPath;

        @Override
        protected Void doInBackground(String... params) {
            AssetUnzipper unzipper = new AssetUnzipper(PreloaderActivity.this);
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
            progressDialog = new ProgressDialog(PreloaderActivity.this, ProgressDialog.THEME_HOLO_DARK);
            progressDialog.setTitle("Updating game files...");
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
            Config.getInstance().setOption(Config.Option.RES_LOCAL_DATA_DIR, dataPath);
            Toast.makeText(PreloaderActivity.this, "Unpacked assets to " + dataPath, Toast.LENGTH_LONG);
            onSanityCheckComplete(true);
        }
    }


}
