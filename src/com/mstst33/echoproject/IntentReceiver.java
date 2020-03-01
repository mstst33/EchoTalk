package com.mstst33.echoproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IntentReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(!intent.getData().equals("com.mstst33.echoproject")){
			Log.d("_PACKAGE_OBSERVER_", "data = " + intent.getData());
			Log.d("_PACKAGE_OBSERVER_", "It's not our package.");
			return;
		}
		
		if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
			Log.d("_PACKAGE_OBSERVER_", "Added");
		}
		else if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){
			Log.d("_PACKAGE_OBSERVER_", "Removed");
		}
		else if(intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")){
			Log.d("_PACKAGE_OBSERVER_", "Replaced");
		}
		
		Log.d("_PACKAGE_OBSERVER_", "action = " + intent.getAction());
		Log.d("_PACKAGE_OBSERVER_", "data = " + intent.getData());
	}
}