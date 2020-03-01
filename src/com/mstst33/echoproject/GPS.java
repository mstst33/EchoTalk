package com.mstst33.echoproject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GPS implements android.location.LocationListener{
	private final int TWO_MINUTES = 1000 * 60 * 2;
	public static String CUR_ADDRESS;
	public static double CUR_LAT;
	public static double CUR_LNG;
	public static boolean IS_GET_DATA;
	
	private LocationManager locationManager;
	private LocationListener locationListenerGPS;
	private LocationListener locationListenerNetwork;
	private Location curLocation;
	private String locationProvider;
	
	public boolean gpsOn;
	public boolean networkOn;
	
	private Context context;
	
	private Locale country;
	private List<Address> addressList;
	private Geocoder geocoder;
	
	public static boolean IS_RUNNING;
	
	private NoticeGPSListener m_Listener;
	
	public interface NoticeGPSListener {
        public void onNoticeGPS(boolean isSucceeded);
    }
	
	public void setOnNoticeGPSListener(NoticeGPSListener m_Listener){
		this.m_Listener = m_Listener;
	}
	
	public void detachOnNoticeGPSListener(){
		this.m_Listener = null;
	}
	
	public GPS(Context context) {
		this.context = context;
		
		locationProvider = "";
		country = Locale.KOREA;
		geocoder = new Geocoder(context, country);
		addressList = null;
		CUR_ADDRESS = "";
		CUR_LAT = -1;
		CUR_LNG = -1;
		
		gpsOn = false;
		networkOn = false;
		
		IS_GET_DATA = false;
		IS_RUNNING = false;
		
		setGPS();
	}

	/** Initialize GPS */
	private void setGPS() {
		try {
			// Acquire a reference to the system Location Manager
			locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			locationListenerGPS = this;
			locationListenerNetwork = this;
			
			// Ask you to set GPS if not enabled
			// if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				// alertCheckGPS();
            
        } catch (Exception e) {
            Log.d("GPS", e.getMessage());
        }
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if(IS_RUNNING){
			if(isBetterLocation(location, curLocation)){
				curLocation = location;
			}
			
			Log.d("GPS", "onLocationChanged: " + location.getProvider());
			makeUseOfNewLocation(curLocation);
		}
	}

	@Override
	public void onProviderDisabled(java.lang.String provider) {
	}

	@Override
	public void onProviderEnabled(java.lang.String provider) {
	}

	@Override
	public void onStatusChanged(java.lang.String provider, int status, Bundle extras) {
		switch (status) {
	    case LocationProvider.OUT_OF_SERVICE:
	    	// When impossible to use GPS
	        Toast.makeText(context, "GPS: Out of Service", Toast.LENGTH_SHORT).show();
	        break;
	    case LocationProvider.TEMPORARILY_UNAVAILABLE:
	    	// When removing this listener
	        Toast.makeText(context, "GPS: Temporarily Unavailable", Toast.LENGTH_SHORT).show();
	        break;
	    case LocationProvider.AVAILABLE:
	    	// When update this listener or possible to use GPS
	        Toast.makeText(context, "GPS: Available", Toast.LENGTH_SHORT).show();
	        break;
		}
	}
	
	private void makeUseOfNewLocation(Location location) {
		if(location == null){
			Log.d("GPS", "location is null");
			return;
		}
			
		try {
		    addressList = geocoder.getFromLocation(curLocation.getLatitude(), curLocation.getLongitude(), 3);
		    
		    // 0 is most detailed address
			Address address = addressList.get(0);
			CUR_ADDRESS = address.getLocality() + " " + address.getSubLocality();
			CUR_LAT = location.getLatitude();
			CUR_LNG = location.getLongitude();
			
			if(!country.equals(address.getLocale())){
				country = address.getLocale();
				geocoder = new Geocoder(context, country);
				Log.d("GPS", "Country: " + country);
			}
			
			IS_GET_DATA = true;
		} catch (IOException e) {
			Log.d("GPS", "Fail to get address by IOException");
		    e.printStackTrace();
		} finally{
			locationProvider = location.getProvider();
			endUpdate();
		}
    }
	
	public void startUpdate(){
		if(!IS_RUNNING){
			Log.d("GPS", "Start updating");
			IS_RUNNING = true;
			IS_GET_DATA = false;
			
			try {
				if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					gpsOn = true;
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, locationListenerGPS);
				}
				
				if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
					networkOn = true;
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0F, locationListenerNetwork);
				}
			} catch (java.lang.SecurityException ex) {
				Log.e("GPS", "SecurityException : " + ex.getMessage());
			} catch (IllegalArgumentException ex) {
				Log.e("GPS", "provider does not exist " + ex.getMessage());
			}
		}
	}
	
	public void endUpdate(){
		Log.d("GPS", "End update");
		
		try {
			if(gpsOn){
				gpsOn = false;
				locationManager.removeUpdates(locationListenerGPS);
			}
			
			if(networkOn){
				networkOn = false;
				locationManager.removeUpdates(locationListenerNetwork);
			}
		} catch (Exception ex) {
			Log.e("GPS", ex.getMessage());
		}
		
		if(GPS.IS_GET_DATA){
			Log.d("GPS", "Success to get location! Provider: " + locationProvider);
			
			if(m_Listener != null){
				m_Listener.onNoticeGPS(true);
			}
			else
				Log.d("GPS", "Listener is null");
		}
		else{
			if(m_Listener != null){
				m_Listener.onNoticeGPS(false);
			}
			else
				Log.d("GPS", "Listener is null");
			
			Log.d("GPS", "Failed to get location. Provider: " + locationProvider);
		}
		
		IS_RUNNING = false;
	}
	
	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
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

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
	
	/** Ask you to set GPS */ 
	public void alertCheckGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS is disabled! Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveConfigGPS();
                            }
                    })
                .setNegativeButton("Do nothing",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                    }).show();
    }
	
    /** Move to GPS setting screen */
    public void moveConfigGPS() {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(gpsOptionsIntent);
    }
}
