package com.mstst33.echoproject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mstst33.echo.EchoBall;
import com.mstst33.echo.EchoShoutWindow;
import com.mstst33.echo.EchoView;
import com.mstst33.echo.EchoWholeView;

public class EchoFragment extends Fragment implements GPS.NoticeGPSListener{
	public static int ECHO_PAGE_NUM = 1;
	
	public final int ECHO_SHOUT_DIALOG = 1;
	
	public final int ECHO_SHOUT = 0;
	public final int ECHO_LOCATION = 1;
	public final int ECHO_DOWNLOAD = 2;
	public final int ECHO_SEND_GCM = 3;
	
	public int echo_state;
	private boolean isFirst;
	private EchoWholeView echoWholeView;
	private ArrayList<EchoView> eViewList;
	private JSONArray jsonArray;
	private ArrayList<NameValuePair> nameValuePairs;
	private ArrayList<EchoBall> eBallWholeList;
	private ArrayList<String> curNumBallList;
	
	private final String UPLOAD_WRITING_URL = BasicInfo.SERVER_ADDRESS + "upload_writing.php";
	private final String DOWNLOAD_ECHO_URL = BasicInfo.SERVER_ADDRESS + "download_echo.php";
	private final String SEND_GCM_URL = BasicInfo.SERVER_ADDRESS + "send_gcm.php";
	//OnArticleSelectedListener mListener;
	
	SharedPreferences.Editor edt;
	
	String travel_string;
	String exercise_string;
	String hobby_string;
	String study_string;
	String question_string;
	String job_string;
	String used_string;
	
	public EchoFragment(){
		isFirst = true;
		jsonArray = new JSONArray();
		nameValuePairs = null;
		eBallWholeList = null;
		curNumBallList = null;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		travel_string = activity.getResources().getString(R.string.travel);
		exercise_string = activity.getResources().getString(R.string.exercise);
		hobby_string = activity.getResources().getString(R.string.hobby);
		study_string = activity.getResources().getString(R.string.study);
		question_string = activity.getResources().getString(R.string.question);
		job_string = activity.getResources().getString(R.string.job);
		used_string = activity.getResources().getString(R.string.used);
		/*
		try {
            mListener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }*/
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		edt = getActivity().getSharedPreferences("Echo", Context.MODE_PRIVATE).edit();
		
		echoWholeView = new EchoWholeView(getActivity());
		echoWholeView.basicView.shout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(ECHO_SHOUT_DIALOG);
			}
		});
		
		Log.d("EchoFragment", "OnCreateView");
		
		return echoWholeView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d("EchoFragment", "OnResume");
		
		if(MainActivity.gps != null)
			MainActivity.gps.setOnNoticeGPSListener(this);
		
		updateWithGPS();
		if (!isFirst) {
			eViewList = new ArrayList<EchoView>();

			try {
				for (int i = 0; i < jsonArray.length(); ++i)
					eViewList.add((EchoView) jsonArray.get(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			echoWholeView.setEchoViewList(eViewList);
			jsonArray = new JSONArray();
		}
		else{
			isFirst = false;
			readEchoFromSaveFile();
			
			if(eBallWholeList != null){
				int size = eBallWholeList.size();
				for(int i = 0; i < echoWholeView.basicView.eViewList.size(); ++i){
					if(size >= EchoView.MAX_NUM_BALL){
						for(int j = 0; j < EchoView.MAX_NUM_BALL; ++j)
							echoWholeView.basicView.eViewList.get(i).eBallList.add(eBallWholeList.get(j + EchoView.MAX_NUM_BALL * i));
						
						size -= EchoView.MAX_NUM_BALL;
					}
					else{
						for(int j = 0; j < size; ++j)
							echoWholeView.basicView.eViewList.get(i).eBallList.add(eBallWholeList.get(j + EchoView.MAX_NUM_BALL * i));
					}
				}
			}
			else
				Log.d("EchoFragment", "eBall is null");
			
			if(curNumBallList != null){
				for(int i = 0; i < echoWholeView.basicView.eViewList.size(); ++i){
					echoWholeView.basicView.eViewList.get(i).setCurNumBall(Integer.parseInt(curNumBallList.get(i)));
				}
			}
			else
				Log.d("EchoFragment", "curNum is null");
		}
	}
	
	// Use this function to save important info because that guarantees before being finished
	@Override
	public void onPause(){
		super.onPause();
		Log.d("EchoFragment", "OnPause");
		
		if(MainActivity.gps != null)
			MainActivity.gps.detachOnNoticeGPSListener();
		
		if(eBallWholeList != null){
			if(!eBallWholeList.isEmpty())
				eBallWholeList.clear();
		}
		else{
			eBallWholeList = new ArrayList<EchoBall>();
		}
		
		if(curNumBallList != null){
			if(!curNumBallList.isEmpty())
				curNumBallList.clear();
		}
		else{
			curNumBallList = new ArrayList<String>();
		}
		
		eViewList = echoWholeView.getEchoViewList();
		try {
			for(int i = 0; i < eViewList.size(); ++i){
				jsonArray.put(i, eViewList.get(i));
				
				curNumBallList.add(String.valueOf(eViewList.get(i).curNumBall));
				for(int j = 0; j < eViewList.get(i).eBallList.size(); ++j)
					eBallWholeList.add(eViewList.get(i).eBallList.get(j));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		saveEchoData();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.d("EchoFragment", "OnStop");
	}
	
	// Use this function to remove eViewList from its parent
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		// finish drawThread
		echoWholeView.eDThread.isExit = true;
		boolean retry = true;
		
		while(retry){
			try{
				echoWholeView.eDThread.join();
				retry = false;
			} catch(InterruptedException e){
			}
		}
		
		// remove eViewList items in their parent
		for(int i = 0; i < eViewList.size(); ++i){
			if(((ViewGroup) eViewList.get(i).getParent()) != null){
				((ViewGroup) eViewList.get(i).getParent()).removeView(eViewList.get(i));
				Log.d("ViewPager", "Remove parent " + Integer.toString(i));
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
	}
	
	public void showDialog(int type) {
	    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("echo_shout_dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    switch (type) {
	        case ECHO_SHOUT_DIALOG:
	            DialogFragment dialogFrag = EchoShoutWindow.newInstance(0);
	            dialogFrag.setTargetFragment(this, ECHO_SHOUT_DIALOG);
	            dialogFrag.show(getFragmentManager().beginTransaction(), "echo_shout_dialog");
	            break;
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ECHO_SHOUT_DIALOG:
			if (resultCode == Activity.RESULT_OK) {
				echo_state = ECHO_SHOUT;
				Intent intent = getActivity().getIntent();
				int selectedInfo = intent.getIntExtra("selectedInfo", -1);
				JSONObject jsonObj = null;
				
				if(nameValuePairs != null){
					if(!nameValuePairs.isEmpty()){
						nameValuePairs.clear();
					}
					
					nameValuePairs = null;
				}
				
				nameValuePairs = (ArrayList<NameValuePair>) intent.getSerializableExtra("nameValuePairs_data");
				nameValuePairs.add(new BasicNameValuePair("userID", BasicInfo.USER_ID));
				nameValuePairs.add(new BasicNameValuePair("selectedInfo", String.valueOf(selectedInfo)));
				nameValuePairs.add(new BasicNameValuePair("writing_num", String.valueOf(TodayDate.getMilliSecond())));
				nameValuePairs.add(new BasicNameValuePair("writing_date", TodayDate.getDate() + " " + TodayDate.getTime()));
				nameValuePairs.add(new BasicNameValuePair("location", BasicInfo.LOCATION));
				
				String uri = intent.getStringExtra("bitmapStr");
				if(uri != null){
					Uri bitmapUri = Uri.parse(uri);
					Bitmap bitmapImage = loadPicture(bitmapUri);
				
					// Delete temporary file
					File tempFile = new File(bitmapUri.getPath());
					if (tempFile.exists()) {
						Log.d("ShoutClicked", "Photo deleted");
						tempFile.delete();
					}
				
					if(bitmapImage != null){
						// Send imageFile
						ByteArrayOutputStream bao = new ByteArrayOutputStream();
						bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bao);
						byte [] ba = bao.toByteArray();
				
						String image = Base64.encodeToString(ba, Base64.DEFAULT);
						nameValuePairs.add(new BasicNameValuePair("image", image));
						Log.d("ShoutClicked", "Add Photo");
					}
				}
				new EchoUpdateInfo().execute(nameValuePairs);
				
				Log.d("ShoutClicked", "Success to get data!");
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// After Cancel code.
			}

			break;
		}
	}
	
	private Bitmap loadPicture(Uri uri) {
		Bitmap tempBitmap = null;
		try {
			tempBitmap = Images.Media.getBitmap(getActivity().getContentResolver(), uri);
			
			int width = tempBitmap.getWidth();
			int height = tempBitmap.getHeight();
			
			if(width > 1024){
				/*
				String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
				File file = new File(Environment.getExternalStorageDirectory(), url);
				CameraAlbumDialog.copyFile(CameraAlbumDialog.getImageFile(uri, getActivity()), file);
				tempBitmap = Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
				*/
				if(tempBitmap == null)
					Log.d("ShoutClicked", "bitmap null");
				
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
	
	public void readEchoFromSaveFile() {
		try {
			FileInputStream finput = getActivity().openFileInput("Echo");
			ObjectInputStream input = new ObjectInputStream(finput);
			eBallWholeList = (ArrayList<EchoBall>) input.readObject();
			curNumBallList = (ArrayList<String>) input.readObject();
			finput.close();
			input.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveEchoData(){
		try {
			FileOutputStream foutput = getActivity().openFileOutput("Echo", Context.MODE_PRIVATE);
			ObjectOutputStream output = new ObjectOutputStream(foutput);
			output.writeObject(eBallWholeList);
			output.writeObject(curNumBallList);
			foutput.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		if (GPS.IS_GET_DATA) {
			Log.d("EchoShout", "Updating succeeded");

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

			Log.d("EchoFragment", BasicInfo.LOCATION);
			echo_state = ECHO_LOCATION;
			new EchoUpdateInfo().execute(nameValuePairs);
			GPS.IS_GET_DATA = false;
		} else {
			Log.d("EchoShout", "Updating succeeded with before data");
			echoWholeView.basicView.updateLocation(false);
			echoWholeView.basicView.updateNumPeople(false);
		}
	}
	
	public void updateWithGPS(){
		echoWholeView.basicView.updateLocation(false);
		echoWholeView.basicView.updateNumPeople(false);
		
		if(!GPS.IS_RUNNING){
			Log.d("EchoFragment", "Update with gps");
			MainActivity.gps.startUpdate();
		}
	}
	
	@Override
	public void onNoticeGPS(boolean isSucceeded) {
		update();
	}
	
	Handler updateHandler = new Handler() {
		public void handleMessage(Message msg) {
		}
	};
	
	public class EchoUpdateInfo extends AsyncTask<ArrayList<NameValuePair>, String, Boolean>{
		JSONObject json;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			Boolean result = null;
			
			switch(echo_state){
			case ECHO_SHOUT:
				try {
					json = RestClient.requestLoginResult(UPLOAD_WRITING_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case ECHO_LOCATION:
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
			case ECHO_DOWNLOAD:
				try {
					json = RestClient.requestLoginResult(DOWNLOAD_ECHO_URL, params[0]);
					result = Boolean.parseBoolean(json.getString("is_success"));
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case ECHO_SEND_GCM:
				try {
					RestClient.requestLoginResultVoid(SEND_GCM_URL, params[0]);
					result = true;
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
			
			switch(echo_state){
			case ECHO_SHOUT:
				if (result){
					Log.d("EchoShout", "Uploading writing succeeded");
					int selectedInfo = 0;
					String writing_num = "";
					String msg = "";
					
					try {
						selectedInfo = json.getInt("selectedInfo");
						writing_num = json.getString("writing_num");
						
						if(selectedInfo > 0)
							msg = json.getString("msg");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
					echoWholeView.basicView.eViewList.get(ECHO_PAGE_NUM - 1).createEchoBall(selectedInfo, true, writing_num);
					
					if(selectedInfo > 0){
						if(nameValuePairs != null){
							if(!nameValuePairs.isEmpty()){
								nameValuePairs.clear();
							}
							
							nameValuePairs = null;
						}
						
						String head = "";
						
						switch(selectedInfo){
						case 1:
							head = "[" + travel_string + "]";
							break;
						case 2:
							head = "[" + exercise_string + "]";
							break;
						case 3:
							head = "[" + hobby_string + "]";
							break;
						case 4:
							head = "[" + study_string + "]";
							break;
						case 5:
							head = "[" + question_string + "]";
							break;
						case 6:
							head = "[" + job_string + "]";
							break;
						case 7:
							head = "[" + used_string + "]";
							break;
						}
						
						nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("userID", BasicInfo.USER_ID));
						nameValuePairs.add(new BasicNameValuePair("location", BasicInfo.LOCATION));
						nameValuePairs.add(new BasicNameValuePair("setting_distance", BasicInfo.DISTANCE_TYPE));
						nameValuePairs.add(new BasicNameValuePair("setting_range", BasicInfo.RANGE_TYPE));
						nameValuePairs.add(new BasicNameValuePair("selectedInfo", String.valueOf(selectedInfo)));
						nameValuePairs.add(new BasicNameValuePair("writing_num", writing_num));
						nameValuePairs.add(new BasicNameValuePair("head", head));
						nameValuePairs.add(new BasicNameValuePair("msg", msg));
						
						echo_state = ECHO_SEND_GCM;
						
						new EchoUpdateInfo().execute(nameValuePairs);
						Log.d("EchoShout", "Trying to send GCM message");
					}
				}
				else{
					Toast.makeText(getActivity(), "위치 정보를 읽어 올 수 없어 실패하였습니다", Toast.LENGTH_SHORT);
					Log.d("EchoShout", "Uploading writing failed");
				}
				break;
			case ECHO_LOCATION:
				if (result){
					try {
						BasicInfo.NUM_OF_PEOPLE_AROUND_ME = json.getString("num_of_people_around_me");
						
						echoWholeView.basicView.updateLocation(true);
						echoWholeView.basicView.updateNumPeople(true);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Log.d("EchoShout", "Updating location to server succeeded");
				}
				else{
					echoWholeView.basicView.updateLocation(false);
					echoWholeView.basicView.updateNumPeople(false);
					
					Log.d("EchoShout", "Updating location to server failed");
				}
				break;
			case ECHO_DOWNLOAD:
				if (result){
					Log.d("EchoShout", "Downloading echo succeeded");
				}
				else{
					Log.d("EchoShout", "Downloading echo failed");
				}
				break;
			case ECHO_SEND_GCM:
				
				Log.d("EchoShout", "Completed to send GCM message");
				break;
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