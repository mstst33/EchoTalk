package com.mstst33.echoproject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore.Images;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.mstst33.myinfo.MyInfoWholeView;
public class MyInfoFragment extends Fragment implements GPS.NoticeGPSListener{
	private static final int MY_INFO_UPDATE = 0;
	private static final int MY_INFO_LOCATION = 1;
	
	private MyInfoWholeView myInfoWholeView;
	private ArrayList<NameValuePair> nameValuePairs;
	
	private String my_profile_photo_url;
	private int myInfoState;
	
	public final String MY_INFO_UPDATE_URL = BasicInfo.SERVER_ADDRESS + "my_info_update.php";
	
	// My pic is being set;
	private ImageView myPic;
	
	SharedPreferences.Editor edt;
	SharedPreferences prefs;
	
	public MyInfoFragment(){
		my_profile_photo_url = "";
		myInfoState = 0;
	}
	
	private FrameLayout frameBase;
	private LinearLayout progress;
	private boolean isProgress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		edt = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		prefs = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE);
		
		myInfoWholeView = new MyInfoWholeView(getActivity());
		
		frameBase = (FrameLayout)View.inflate(getActivity(), R.layout.echo_framelayout, null);
		progress = (LinearLayout)View.inflate(getActivity(), R.layout.echo_progress, null);		

		// to set the image into my pic
		myPic = (ImageView) myInfoWholeView.findViewById(R.id.my_profile_photo);
		
		//***
		myInfoWholeView.my_profile_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPhoto(CameraAlbumDialog.REQUEST_SELECT_PHOTO);
			}
		});
		myInfoWholeView.my_profile_photo.setEnabled(false);
		
		myInfoWholeView.modify_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myInfoWholeView.modify();
			}
		});
		
		myInfoWholeView.decision_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myInfoWholeView.decision();
				myInfoWholeView.saveMyProfile();
				setMyProfile();
			}
		});
		
		if(!BasicInfo.IS_FIRST){
			setMyImage();
		}
		else{
			progress.setVisibility(View.INVISIBLE);
		}
		frameBase.addView(myInfoWholeView);
		frameBase.addView(progress);
		return frameBase;
	}

	public Handler MyInfoHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 9:
				progress.setVisibility(View.INVISIBLE);
				myPic.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
	
	private void setMyImage(){
		Log.i("MyInfoWholeView",BasicInfo.USER_PHOTO);
		if(BasicInfo.USER_PHOTO.equals("null")||BasicInfo.USER_PHOTO.isEmpty()||BasicInfo.USER_PHOTO.equals("0")){
			Log.i("MyInfoWholeView", "MyPic is null");
			MyInfoHandler.sendEmptyMessage(9);
		}
		else{
			if(BasicInfo.USER_PHOTO.length() > 2)
			{ 	Log.i("MyInfoWholeView", "Mypic is downloding");
				Log.i("MyInfoWholeView", BasicInfo.USER_PHOTO);
				myPic.setVisibility(View.INVISIBLE);
				new URLBitmapDownload(BasicInfo.USER_PHOTO, myPic, false, false, MyInfoHandler).start();
			}
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		if(MainActivity.gps != null)
			MainActivity.gps.setOnNoticeGPSListener(this);
		
		updateWithGPS();
	}
	@Override
	public void onPause() {
		super.onPause();
		if(MainActivity.gps != null)
			MainActivity.gps.detachOnNoticeGPSListener();
	}
	public void onDestroyView(){
		super.onDestroyView();
		myInfoWholeView.setBackgroundDrawable(null);
		myInfoWholeView.decision_btn.setBackgroundDrawable(null);
		myInfoWholeView.modify_btn.setBackgroundDrawable(null);
		myInfoWholeView.gender_group.setBackgroundDrawable(null);
		myInfoWholeView.my_age_edit.setBackgroundDrawable(null);
		myInfoWholeView.my_email_edit.setBackgroundDrawable(null);
		myInfoWholeView.my_id.setBackgroundDrawable(null);
		myInfoWholeView.my_name_edit.setBackgroundDrawable(null);
		myInfoWholeView.my_profile_photo.setBackgroundDrawable(null);
		myInfoWholeView.my_status_message.setBackgroundDrawable(null);
		frameBase.setBackgroundDrawable(null);
		progress.setBackgroundDrawable(null);
		URLBitmapDownload.recycleBitmap(myPic);
	}
	
	public void addPhoto(int type){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("select_photo_dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);
	    
	    CameraAlbumDialog.REQUEST_PROFILE_PHOTO = true;
	    
	    switch (type) {
	        case CameraAlbumDialog.REQUEST_SELECT_PHOTO:
	            DialogFragment dialogFrag = CameraAlbumDialog.newInstance(0);
	            dialogFrag.setTargetFragment(this, CameraAlbumDialog.REQUEST_SELECT_PHOTO);
	            dialogFrag.show(getFragmentManager().beginTransaction(), "select_photo_dialog");
	            break;
	    }
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CameraAlbumDialog.REQUEST_SELECT_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				Intent intent = getActivity().getIntent();		
				my_profile_photo_url = intent.getStringExtra("bitmapStr");
				
				Log.d("Select Photo", "Completed to get photo");
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.d("Select Photo", "Failed to get photo");
			}
			break;
		}
	}
	
	public void setMyProfile(){
		myInfoState = MY_INFO_UPDATE;
		
		if(nameValuePairs != null){
			if(!nameValuePairs.isEmpty()){
				nameValuePairs.clear();
			}
			
			nameValuePairs = null;
		}
		
		String millisecond = String.valueOf(TodayDate.getMilliSecond());
		
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("user_id", BasicInfo.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("user_name", BasicInfo.USER_NAME));
		nameValuePairs.add(new BasicNameValuePair("user_age", BasicInfo.USER_AGE));
		nameValuePairs.add(new BasicNameValuePair("user_gender", BasicInfo.USER_GENDER));
		nameValuePairs.add(new BasicNameValuePair("user_comment", BasicInfo.USER_COMMENT));
		nameValuePairs.add(new BasicNameValuePair("writing_num", millisecond));
		
		edt.putString("user_name", BasicInfo.USER_NAME);
		edt.putString("user_age", BasicInfo.USER_AGE);
		edt.putString("user_gender", BasicInfo.USER_GENDER);
		edt.putString("user_comment", BasicInfo.USER_COMMENT);
		edt.commit();
		
		//if(my_profile_photo_url != null){
		if(!my_profile_photo_url.isEmpty() || my_profile_photo_url != null){
			Uri bitmapUri = Uri.parse(my_profile_photo_url);
			Bitmap bitmapImage = loadPicture(bitmapUri);
			
			// Delete temporary file
			File tempFile = new File(bitmapUri.getPath());
			if (tempFile.exists()) {
				Log.d("MyInfoFragment", "Photo deleted");
				tempFile.delete();
			}
		
			if(bitmapImage != null){
				// Send imageFile
				saveData(bitmapImage);
				myInfoWholeView.my_profile_photo.setImageBitmap(bitmapImage);
				myInfoWholeView.setVisibility(View.INVISIBLE);
				myInfoWholeView.setVisibility(View.VISIBLE);
				
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bao);
				byte [] ba = bao.toByteArray();
				
				String image = Base64.encodeToString(ba, Base64.DEFAULT);
				nameValuePairs.add(new BasicNameValuePair("image", image));
				Log.d("MyInfoFragment", "Add Photo");
				
				BasicInfo.USER_PHOTO = "photo_" + millisecond + ".png";
				edt.putString("user_photo", BasicInfo.USER_PHOTO);
			}
		}
		
		new MyInfoUpdateInfo().execute(nameValuePairs);
	}
	
	private Bitmap loadPicture(Uri uri) {
		Bitmap tempBitmap = null;
		try {
			tempBitmap = Images.Media.getBitmap(getActivity().getContentResolver(), uri);
			
			int width = tempBitmap.getWidth();
			int height = tempBitmap.getHeight();
			
			if(width > 1024){
				if(tempBitmap == null)
					Log.d("MyInfoFragment", "bitmap null");
				
				String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
				File file = new File(Environment.getExternalStorageDirectory(), url);
				FileOutputStream out = new FileOutputStream(file);
				tempBitmap = Bitmap.createScaledBitmap(tempBitmap, (width=1024), (height=height*1024/width), true);
				tempBitmap.compress(CompressFormat.PNG, 100, out);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tempBitmap;
	}
	
	/*
	public void readFromSaveFile(ImageView imgview) {
		try {
			String imgpath = "data/data/com.mstst33.echoproject/files/profile.png";
			Bitmap bm = BitmapFactory.decodeFile(imgpath);
			if(bm != null)
				imgview.setImageBitmap(bm);
			else
				Log.d("MyInfoFragment", "Bitmap Image is null");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void saveData(Bitmap bm){
		try{
		     File file = new File("profile.png");          
		     FileOutputStream fos = getActivity().openFileOutput("profile.png" , 0);          
		     bm.compress(CompressFormat.PNG, 100 , fos);          
		     fos.flush();     
		     fos.close();
		        
		     }catch(Exception e) {
		    	 e.printStackTrace();
		     }
	}
	
	@Override
	public void onNoticeGPS(boolean isSucceeded) {
		update();
	}
	
	public void update(){
		if (GPS.IS_GET_DATA) {
			Log.d("MyInfoFragment", "Updating succeeded");

			if (nameValuePairs != null) {
				if (!nameValuePairs.isEmpty()) {
					nameValuePairs.clear();
				}

				nameValuePairs = null;
			}

			nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("userID", BasicInfo.USER_ID));
			nameValuePairs.add(new BasicNameValuePair("location", BasicInfo.LOCATION));
			nameValuePairs.add(new BasicNameValuePair("setting_distance", BasicInfo.DISTANCE_TYPE));
			nameValuePairs.add(new BasicNameValuePair("setting_range", BasicInfo.RANGE_TYPE));

			Log.d("MyInfoFragment", BasicInfo.LOCATION);
			
			myInfoState = MY_INFO_LOCATION;
			new MyInfoUpdateInfo().execute(nameValuePairs);
			
			GPS.IS_GET_DATA = false;
		} else {
			Log.d("MyInfoFragment", "Updating succeeded with before data");
			myInfoWholeView.updateLocation(false);
			myInfoWholeView.updateNumPeople(false);
		}
	}
	
	public void updateWithGPS(){
		myInfoWholeView.updateLocation(false);
		myInfoWholeView.updateNumPeople(false);
		
		if(!GPS.IS_RUNNING){
			Log.d("MyInfoFragment", "Update with gps");
			MainActivity.gps.startUpdate();
		}
	}
	
	public class MyInfoUpdateInfo extends AsyncTask<ArrayList<NameValuePair>, String, Boolean> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			
			switch(myInfoState){
			case MY_INFO_UPDATE:
				try {
					json = RestClient.requestLoginResult(MY_INFO_UPDATE_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case MY_INFO_LOCATION:
				try {
					json = RestClient.requestLoginResult(MainActivity.LOCATION_SETUP_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
				
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result == null)
				return;
			
			switch(myInfoState){
			case MY_INFO_UPDATE:
				if(result){
					Log.d("MyInfoFragment", "My profile is updated successfully");
				}
				break;
			case MY_INFO_LOCATION:
				if (result){
					try {
						BasicInfo.NUM_OF_PEOPLE_AROUND_ME = json.getString("num_of_people_around_me");
						
						myInfoWholeView.updateLocation(true);
						myInfoWholeView.updateNumPeople(true);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Log.d("MyInfoFragment", "Updating location to server succeeded");
					
					if(BasicInfo.IS_FIRST){
						BasicInfo.IS_FIRST = false;
						
						edt.putBoolean("isFirst", BasicInfo.IS_FIRST);
						edt.commit();
						
						Log.d("EchoFragment", "Get location again at first");
						if(nameValuePairs != null){
							if(!nameValuePairs.isEmpty()){
								nameValuePairs.clear();
							}
							
							nameValuePairs = null;
						}
						
						nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("userID", BasicInfo.USER_ID));
						nameValuePairs.add(new BasicNameValuePair("location", BasicInfo.LOCATION));
						nameValuePairs.add(new BasicNameValuePair("setting_distance", BasicInfo.DISTANCE_TYPE));
						nameValuePairs.add(new BasicNameValuePair("setting_range", BasicInfo.RANGE_TYPE));
						
						new MyInfoUpdateInfo().execute(nameValuePairs);
					}
				}
				else{
					myInfoWholeView.updateLocation(false);
					myInfoWholeView.updateNumPeople(false);
					
					Log.d("MyInfoFragment", "Updating location to server failed");
				}
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
}