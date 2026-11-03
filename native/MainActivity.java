package com.mymusic.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle b) {
        registerPlugin(PlaybackPlugin.class);
        super.onCreate(b);
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    @Override
    public void onDestroy() {
        boolean closing = isFinishing();
        super.onDestroy();
        if (closing) { // appli fermée : coupe tout instantanément
            stopService(new Intent(this, PlaybackService.class));
            Process.killProcess(Process.myPid());
        }
    }
}
