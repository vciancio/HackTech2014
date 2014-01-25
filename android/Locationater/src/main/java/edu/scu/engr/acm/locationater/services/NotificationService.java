package edu.scu.engr.acm.locationater.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created: vincente on 1/25/14.
 */
public class NotificationService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: Create the Notificaiton saying that you are within range of at least one user
        return START_STICKY;
    }
}
