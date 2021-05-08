package com.nextgentele.busvalidatorv2.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.nextgentele.busvalidatorv2.callback.LocationCallBack;
import com.nextgentele.busvalidatorv2.models.Stop;

import java.util.List;

import static android.content.ContentValues.TAG;
import static androidx.core.location.LocationManagerCompat.isLocationEnabled;


public class GeoFencing implements LocationListener {

    final int m_interval = 5 * 1000;
    double R = 6378.137; // Radius of earth in KM
    CheckPermission checkPermission;
    Location location, location2;
    private Handler m_handler;
    Criteria crit = new Criteria();
    String choix_source;
    LocationCallBack locationCallBack;
    Location firstLocation;
    boolean flag = false;
    LocationManager locationManager;
    float radius = (float) 50.00;
    int index;
    List<Stop> locationList;
    float dist;
    Context context;



    public GeoFencing(List<Stop> locationList, Context context, int index) {
        this.context = context;
        this.locationList = locationList;
        this.index = index;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationCallBack = (LocationCallBack) context;
        Log.w(TAG, " onlocationValue : " + locationList);
    }

//    public void geoCalc() {
//        checkPermission = new CheckPermission(context);
//        try {
//            Log.i("Location", "Started");
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
////                //    ActivityCompat#requestPermissions
////                checkPermission.checkPermission(
////                        Manifest.permission.ACCESS_FINE_LOCATION,
////                        GPS_PERMISSION);
//                return;
//            } else {
//                location = new Location("LocationA");
//                location2 = new Location("LocationA");
//                Log.i("Location", "Ended");
//                Log.i("Location_Result", String.valueOf(location));
//                if (location != null) {
//                    location.setLatitude(17.372102);
//                    location.setLongitude(78.484196);
//
//                    location2.setLatitude(17.372102);
//                    location2.setLongitude(78.484195);
//
//                }
//                lat1 = location.getLatitude();
//                lon1 = location.getLongitude();
//                lat2 = location2.getLatitude();
//                lon2 = location2.getLongitude();
//                Log.e("TAG", "GPS is on");
//                //onLocationChanged(location);
//                //Toast.makeText(context, "latitude:" + location.getLatitude() + " longitude:" + location.getLatitude(), Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
//            Log.i("Exception : ", String.valueOf(e));
//        }
//
//        double dist = location.distanceTo(location2);
//        Toast.makeText(context, "FROM 2 :" + dist, Toast.LENGTH_SHORT).show();
//        Log.i("from 2 ", String.valueOf(dist));
//        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
//        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c;
//        double dm = d * 1000; // meters
//        Toast.makeText(context, "From 1 :" + dm, Toast.LENGTH_LONG).show();
//        Log.i("From 1 ", String.valueOf(dm));
//    }


    public void requestUpdate() {
        crit.setCostAllowed(true);
        crit.setAccuracy(Criteria.ACCURACY_FINE); //précis à 1km pret
        choix_source = locationManager.getBestProvider(crit, true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(choix_source, 2500, 20, this);
        location = locationManager.getLastKnownLocation(choix_source);
        double latitude = 0;
        double longitude = 0;
        if (isLocationEnabled(locationManager) && location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        Toast.makeText(context, "Current Location : " + latitude + " : " + longitude, Toast.LENGTH_SHORT).show();
    }

    Runnable m_statusChecker = new Runnable() {
        @Override
        public void run() {
            if (location == null)
                runner(); //this function can change value of m_interval.
            m_handler.postDelayed(m_statusChecker, m_interval);
        }
    };

    private void runner() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.w(TAG, " LocationInit");
        locationManager.requestLocationUpdates(choix_source, 2500, 0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        //current stop coardinates
        Location stopLocation = new Location(LocationManager.GPS_PROVIDER);
        stopLocation.setLatitude(Double.parseDouble(locationList.get(index).getGpsLatitude()));
        stopLocation.setLongitude(Double.parseDouble(locationList.get(index).getGpsLongitude()));

        //stop reaching
        if (index == 0) {
            if (firstLocation==null)
                firstLocation = location;
        } else{
            firstLocation.setLatitude(Double.parseDouble(locationList.get(index-1).getGpsLatitude()));
            firstLocation.setLongitude(Double.parseDouble(locationList.get(index-1).getGpsLongitude()));
        }

        Log.w(TAG, "onLocation : " + stopLocation.distanceTo(location));
        if (location.distanceTo(stopLocation) < radius) {
            //stop Reached
            stopReached();
        }

        dist = firstLocation.distanceTo(stopLocation) + radius;

        if (dist < firstLocation.distanceTo(location)) {
            //Stop left
            stopLeft();
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.w(TAG, " onStatusChanged : received" + s);
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.w(TAG, " onProviderEnabled : received" + s);
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.w(TAG, " onProviderDisabled : received" + s);
    }

    private void stopReached() {
        Toast.makeText(context, "Stop Reached " + location, Toast.LENGTH_SHORT).show();
        Log.w(TAG, "onReached : received" + location);
        locationCallBack.changeStop(locationList.get(index));
        locationCallBack.setEntryExitEnable(true);
        flag=true;
    }

    private void stopLeft() {
        if (flag)
        {
            flag=false;
        locationCallBack.setEntryExitEnable(false);
        Log.w(TAG," onstopLeft : ");
        }

        Toast.makeText(context, "Stop Left", Toast.LENGTH_SHORT).show();

        if (locationList.size()-1 > index) {
            index++;
            Log.w(TAG, " onstopLeft : index ");
        }
    }
}
