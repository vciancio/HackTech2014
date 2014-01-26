package edu.scu.engr.acm.locationater.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.scu.engr.acm.locationater.util.Constants;
import edu.scu.engr.acm.locationater.util.ServerComm;
import edu.scu.engr.acm.locationater.util.Shared;

/**
 * Created: vincente on 1/25/14.
 */
public class EventListeningService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        //Get Info From the Server
        ServerComm comms = new ServerComm();
        String rawResponse = comms.hasEvent(this);
        JSONObject response;
        JSONArray eventsJSON = null;
        ArrayList<Shared> sharedEvents = new ArrayList<Shared>();
        try {
            response = new JSONObject(rawResponse);
            eventsJSON = response.getJSONArray("Events");

 /*           for(int i=0; i<eventsJSON.length(); i++){
                JSONObject eventJSON = eventsJSON.getJSONObject(i);
                Shared sharedEvent = new Shared();

                sharedEvent.setfirstname(eventJSON.getString("first"));
                sharedEvent.setlastname(eventJSON.getString("last"));
                sharedEvent.setMessage(eventJSON.getString("title"));
                sharedEvent.setEmail(eventJSON.getString("email"));
                sharedEvent.setnodeid(eventJSON.getInt("eID"));

                sharedEvents.add(sharedEvent);
            }
*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Intent that will be called by either
        Intent i = null;
        Bundle extras = new Bundle();

        //Check the Info to see if we have a valid event_id
        //If -1, we will start the Alarm Manager and keep on checking for a certain amount of time
        //If  0, we will notify the user that the person is near you.
        //If **, we are being asked if we can share our data and will display the appropriate notification
        if (eventsJSON.length() == 0) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //Create the Intent which we are going to have started when we wake-up
            i = new Intent(this, EventListeningService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
            //Set the Alarm
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + Constants.TIME_DELAY,
                    pendingIntent);
            if (Constants.DEBUGGING)
                Log.i("EventListeningService", "Setting the Alarm");
        }
/*      else if (event_id == 0) {
            i = new Intent(this, NotificationService.class);
            extras.putInt(Constants.ID_NOTIFY, 0);
            extras.putString(Constants.USER_NAME, name);
            extras.putString(Constants.MESSAGE, "I'm Close By");
            i.putExtras(extras);
            startService(i);
        }
 */
        //If we do, we will create a notification so the User can respond to the event
        else {
            i = new Intent(this, NotificationService.class);
            extras.putInt(Constants.ID_NOTIFY, 1);
            extras.putString("JSONevents", eventsJSON.toString());
            i.putExtras(extras);
            this.startService(i);
        }
        //We have started everything we needed to... Lets Shutddoooowwwwwwwwnnnnnnnn
        stopSelf();
        return START_STICKY;
    }
}
