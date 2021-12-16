package com.example.hmdfpm;

import android.os.Message;

import com.example.hmdfpm.netservice.TCPLocalDeviceLoader;
import com.stealthcopter.networktools.subnet.Device;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

class PingCheckRunnable implements Runnable {
    private final CameraActivity cameraActivity;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public PingCheckRunnable(CameraActivity cameraActivity) {
        this.cameraActivity = cameraActivity;
    }

    @Override
    public void run() {
        while (true) {
            if(!running.get()) continue;

            Map<String, Device> devices = TCPLocalDeviceLoader.getInstance().devices;

            if (devices.isEmpty()) continue;

            Message message = new Message();
            message.what = 3;
            message.obj = devices;
            cameraActivity.wifiHandler.sendMessage(message);
            running.set(false);
        }
    }

    public void activate() {running.set(true);}
    public void deactivate() {
        running.set(false);
    }

    public void stopThread() {
        running.set(false);
    }
}
