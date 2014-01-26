package edu.scu.engr.acm.locationater.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import edu.scu.engr.acm.locationater.R;
import edu.scu.engr.acm.locationater.util.Constants;

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

    /*
        Must Pass:
            Name & Event Id, and Notify Id
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String name = bundle.getString(Constants.USER_NAME);
        String eventid = bundle.getString(Constants.EVENT_NODE_ID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if (Constants.DEBUGGING)
            Log.i("NotificationService", "onStartCommand");
        switch (bundle.getInt(Constants.ID_NOTIFY)) {
            case 1:
                //We are the Stationary who is being asked to share his/her location
                builder.setContentTitle("Share Location");
                builder.setContentText("Share location with " + name + "?");
                Intent shareIntent = new Intent(this, ShareLocationService.class);
                Bundle extras = new Bundle();
                extras.putString(Constants.EVENT_NODE_ID, eventid);
                shareIntent.putExtras(extras);
                PendingIntent pendingShareIntent =
                        PendingIntent.getService(getBaseContext(), 0, shareIntent, 0);
                builder.addAction(android.R.drawable.ic_menu_share, "Share!", pendingShareIntent);
                break;

            default:
                if (Constants.DEBUGGING)
                    Log.i("NotificationService", "It is a general \"near you!\" notification");
                String message = bundle.getString(Constants.MESSAGE);
                builder.setContentTitle(name);
                builder.setContentText(message);

                //We displayed the notification, time to start polling the server again
                Intent i = new Intent(this, EventListeningService.class);
                startService(i);

                break;
        }

        builder.setSmallIcon(R.drawable.ic_launcher);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(3309, builder.build());

        stopSelf();
        return START_STICKY;
    }
}
