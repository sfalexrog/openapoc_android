package org.sfalexrog.openapoc.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.sfalexrog.openapoc.config.Config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by Alexey on 17.12.2015.
 */
public class AssetUnzipper {

    private Config config;

    private static String TAG = "AssetUnzipper";

    public interface ProgressReportCallback {
        void onSetFileCount(int count);
        void onReportProgress(String filename, int progress);
    }


    // Of course, ZipFile being a really, really old class
    // doesn't actually implement Iterable, so no cool for-each
    // loops for it...
    class ZipEntriesIterable implements Iterable<ZipEntry> {

        Enumeration<? extends ZipEntry> zipEnumeration;

        public ZipEntriesIterable(final Enumeration<? extends ZipEntry> zipEnumeration) {
            this.zipEnumeration = zipEnumeration;
        }

        @Override
        public Iterator<ZipEntry> iterator() {
            return new Iterator<ZipEntry>() {
                @Override
                public boolean hasNext() {
                    return zipEnumeration.hasMoreElements();
                }

                @Override
                public ZipEntry next() {
                    return zipEnumeration.nextElement();
                }

                @Override
                public void remove() {
                    Log.e(TAG, "remove operation not permitted for this collection!");
                }
            };
        }
    }

    private ProgressReportCallback callback;

    private AssetManager assets;

    public AssetUnzipper(Context context) {
        assets = context.getAssets();
        config = Config.init(context);
    }

    public void setProgressReportCallback(ProgressReportCallback callback) {
        this.callback = callback;
    }

    private void publishFileCount(int count) {
        if (callback != null) {
            callback.onSetFileCount(count);
        }
    }

    private void publishProgress(String filename, int progress) {
        if (callback != null) {
            callback.onReportProgress(filename, progress);
        }
    }

    public String getAssetHash(String assetName) {
        String assetHashName = assetName + ".md5";
        try {
            BufferedInputStream hashStream = new BufferedInputStream(assets.open(assetHashName));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int readlen;
            while((readlen = hashStream.read(buffer)) != -1) {
                out.write(buffer, 0, readlen);
            }
            return out.toString();
        } catch (IOException e) {
            Log.e(TAG, "Could not find hash for " + assetName, e);
            return "";
        }
    }

    public Set<String> getAssetDataZips() {
        Set<String> assetZips = new HashSet<>();
        try {
            for (String assetName : assets.list("")) {
                if (assetName.toLowerCase().endsWith(".zip")) {
                    Log.d(TAG, "Adding asset: " + assetName);
                    assetZips.add(assetName);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Could not enumerate assets!", e);
        }
        return assetZips;
    }

    public void unzipAssets(String path) {
        Map<String, String> assetHashes = new HashMap<>();
        try {
            for (String assetName : assets.list("")) {
                if (assetName.toLowerCase().endsWith(".zip")) {
                    Log.d(TAG, "Unzipping asset: " + assetName);
                    ZipInputStream assetZip = new ZipInputStream(assets.open(assetName));
                    ZipEntry ze;
                    int counter = 0;
                    publishFileCount(93); // TODO: Actually check how many files are in the archive
                    while ((ze = assetZip.getNextEntry()) != null) {
                        File outFile = new File(path + "/" + ze.getName());
                        publishProgress(ze.getName(), counter++);
                        if (ze.isDirectory()) {
                            outFile.mkdirs();
                        } else {
                            byte[] buffer = new byte[8192];
                            int length;
                            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outFile));
                            while((length = assetZip.read(buffer)) != -1) {
                                os.write(buffer, 0, length);
                            }
                            os.flush();
                            os.close();
                        }
                    }
                    assetHashes.put(assetName, getAssetHash(assetName));
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Bad asset!", e);
        }
        Set<String> assetHashSet = new HashSet<>();
        for (String key: assetHashes.keySet()) {
            Log.d(TAG, "Adding asset " + key + " with hash " + assetHashes.get(key));
            assetHashSet.add(key + ":" + assetHashes.get(key));
        }
        config.setOption(Config.Option.ANDROID_DATA_HASH, assetHashSet);
    }
}
