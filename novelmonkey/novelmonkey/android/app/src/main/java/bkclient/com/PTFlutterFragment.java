package bkclient.com;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class PTFlutterFragment extends FlutterFragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        GeneratedPluginRegistrant.registerWith(getFlutterEngine());
    }

}
