package edu.scu.engr.acm.locationater.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        JSONArray eventsJSON = null;

        try {
            eventsJSON = new JSONArray(bundle.getString("JSONevents"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if (Constants.DEBUGGING)
            Log.i("NotificationService", "onStartCommand");

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        switch (bundle.getInt(Constants.ID_NOTIFY)) {

            case 1:
                //We are the Stationary who is being asked to share his/her location
                Bundle extras = new Bundle();
                for (int i = 0; i < eventsJSON.length(); i++) {
                    int notifyId = 0;
                    try {
                        JSONObject eventJSON = eventsJSON.getJSONObject(i);
                        builder.setContentText(eventJSON.getString("description"));
                        builder.setContentTitle(eventJSON.getString("title"));
                        extras.putString(Constants.EVENT_NODE_ID, String.valueOf(eventJSON.getInt("eID")));
                        notifyId = eventJSON.getInt("eID");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    builder.setOngoing(true);
                    Intent shareIntent = new Intent(this, ShareLocationService.class);
                    shareIntent.putExtras(extras);
                    PendingIntent pendingShareIntent =
                            PendingIntent.getService(getBaseContext(), 0, shareIntent, 0);
                    builder.addAction(android.R.drawable.ic_menu_share, "Share!", pendingShareIntent);
                    builder.setSmallIcon(R.drawable.ic_launcher);
                    notificationManager.notify(notifyId, builder.build());
                }
                break;

            default:
                if (Constants.DEBUGGING)
                    Log.i("NotificationService", "It is a general \"near you!\" notification");
                String message = bundle.getString(Constants.MESSAGE);
                //builder.setContentTitle(name);
                builder.setContentText(message);
                builder.setSmallIcon(R.drawable.ic_launcher);
                notificationManager.notify(3309, builder.build());


                //We displayed the notification, time to start polling the server again
                Intent i = new Intent(this, EventListeningService.class);
                startService(i);

                break;
        }

        stopSelf();
        return START_STICKY;
    }
}
