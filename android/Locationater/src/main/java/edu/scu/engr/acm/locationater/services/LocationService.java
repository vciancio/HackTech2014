package edu.scu.engr.acm.locationater.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import edu.scu.engr.acm.locationater.util.Constants;

/**
 * Created: vincente on 1/25/14.
 */
public class LocationService extends Service {
    private LocationManager locationManager;
    private PersonalLocationListerner locationListener;
    private String locationProvider = LocationManager.NETWORK_PROVIDER;
    private SharedPreferences sp;
    private Location mLocation;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new PersonalLocationListerner();

        //Get an Initial Position of the user so we can start our process
        mLocation = locationManager.getLastKnownLocation(locationProvider);
        sp = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        storeJsonLocation(mLocation);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (Constants.DEBUGGING)
            Log.i("LocationService", "Created the Service and location Manager!");
        // TODO: Implement locationManger to request location updates and activate a pending intent when the user gets close enough
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        locationManager.removeUpdates(locationListener);
        if (Constants.DEBUGGING)
            Log.i("LocationService", "In onDestroy... Bye Bye");
    }

    public double getCurrentLatitude() {
        return mLocation.getLatitude();
    }

    public double getCurrentLongitude() {
        return mLocation.getLongitude();
    }

    void storeJsonLocation(Location location) {
        try {
            JSONObject locationJson = new JSONObject();
            locationJson.put(Constants.LATITUDE, location.getLatitude());
            locationJson.put(Constants.LONGITUTDE, location.getLongitude());
            sp.edit().putString(Constants.JSON_LOCATION, locationJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class PersonalLocationListerner implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (Constants.DEBUGGING)
                Log.i("LocationService", "Got a new Location, checking if better than previous");
            if (isBetterLocation(location, mLocation)) {
                if (Constants.DEBUGGING)
                    Log.i("LocationService", "Got a new Location to store");
                storeJsonLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        /**
         * Determines whether one Location reading is better than the current Location fix
         *
         * @param location            The new Location that you want to evaluate
         * @param currentBestLocation The current Location fix, to which you want to compare the new one
         */
        protected boolean isBetterLocation(Location location, Location currentBestLocation) {
            if (currentBestLocation == null) {
                // A new location is always better than no location
                return true;
            }

            // Check whether the new location fix is newer or older
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > Constants.TIME_DELAY;
            boolean isSignificantlyOlder = timeDelta < -Constants.TIME_DELAY;
            boolean isNewer = timeDelta > 0;

            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved
            if (isSignificantlyNewer) {
                return true;
                // If the new location is more than two minutes older, it must be worse
            } else if (isSignificantlyOlder) {
                return false;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(location.getProvider(),
                    currentBestLocation.getProvider());

            // Determine location quality using a combination of timeliness and accuracy
            if (isMoreAccurate) {
                return true;
            } else if (isNewer && !isLessAccurate) {
                return true;
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }
            return false;
        }

        /**
         * Checks whether two providers are the same
         */
        private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }
    }
}
