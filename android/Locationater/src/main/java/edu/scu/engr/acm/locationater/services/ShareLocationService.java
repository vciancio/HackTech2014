package edu.scu.engr.acm.locationater.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import edu.scu.engr.acm.locationater.util.Constants;
import edu.scu.engr.acm.locationater.util.ServerComm;

/**
 * Created: vincente on 1/25/14.
 */
public class ShareLocationService extends Service {

    SharedPreferences sp;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        ServerComm comms = new ServerComm();
        comms.sendLocation(sp.getLong(Constants.LONGITUTDE, 0), sp.getLong(Constants.LATITUDE, 0),
                System.currentTimeMillis(), intent.getExtras().getInt(Constants.ID_EVENT), getBaseContext());

        Intent i = new Intent(this, EventListeningService.class);
        startService(i);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        notificationManager.cancel(3309);

        stopSelf();
        return START_STICKY;
    }
}
