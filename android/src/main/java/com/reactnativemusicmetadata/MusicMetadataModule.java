package com.reactnativemusicmetadata;

import androidx.annotation.NonNull;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Base64;
import java.util.HashMap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

@ReactModule(name = MusicMetadataModule.NAME)
public class MusicMetadataModule extends ReactContextBaseJavaModule {
    public static final String NAME = "MusicMetadata";

    public MusicMetadataModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android


    private String convertToBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
      }

    private WritableMap getData(String path) {
        Uri uri = Uri.parse(path);
        MediaMetadataRetriever meta = new MediaMetadataRetriever();
        if(isContentUri(uri)) meta.setDataSource(getReactApplicationContext(), uri); else meta.setDataSource(path, new HashMap<String, String>());
        String title = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String albumName = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String albumArtist = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        Double duration = Double.valueOf(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000.0;
        byte[] bytes = meta.getEmbeddedPicture();

        // Creates the returnable objects for Javascript
        WritableMap songMap = Arguments.createMap();
        songMap.putString("title", title);
        songMap.putString("artist", artist);
        songMap.putString("albumName", albumName);
        songMap.putString("albumArtist", albumArtist);
        songMap.putDouble("duration", duration);
        songMap.putString("uri", path);
        if (bytes != null) {
            songMap.putString("artwork", convertToBase64(bytes));
        } else {
            songMap.putString("artwork", "");
        }

        return songMap;
    }

    @ReactMethod
    public void getMetadata(ReadableArray uris, Promise promise) {
        WritableArray songArray = Arguments.createArray();
        for (int i = 0; i < uris.size(); i++) {
            String uri = uris.getString(i);
            WritableMap songMap = this.getData(uri);
            songArray.pushMap(songMap);
        }
        promise.resolve(songArray);
    }

    private boolean isContentUri(Uri uri) {
        boolean ret = false;
        if(uri != null) {
            String uriSchema = uri.getScheme();
            if("content".equalsIgnoreCase(uriSchema)) ret = true;
        }
        return ret;
    }
}
