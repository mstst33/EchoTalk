package com.mstst33.echoproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CameraAlbumDialog extends DialogFragment implements View.OnClickListener{
	public static final int REQUEST_SELECT_PHOTO = 0;
	public static final int REQUEST_TAKE_A_PICTURE = 1;
	public static final int REQUEST_PHOTO_ALBUM = 2;
	public static final int REQUEST_CROP_PHOTO = 3;
	
	public static boolean REQUEST_PROFILE_PHOTO;
	public static boolean REQUEST_REAL_PHOTO;
	
	private View camera_album;
	
	private Button camera;
	private Button photoAlbum;
	private Button cancelBtn;
	
	private Uri imageUri;
	private String url;
	
	public static CameraAlbumDialog newInstance(int num) {
		CameraAlbumDialog cameraAlbumDialog = new CameraAlbumDialog();
		Bundle bundle = new Bundle();
		bundle.putInt("num", num);
		cameraAlbumDialog.setArguments(bundle);

		return cameraAlbumDialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int num = getArguments().getInt("num");
		int style = DialogFragment.STYLE_NO_FRAME, theme = android.R.style.Theme_Dialog;

		setStyle(style, theme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInsntaceState) {
		// Remove the title
		// getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// LayoutInflater inflater = (LayoutInflater)
		// getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		camera_album = View.inflate(getActivity(), R.layout.camera_album, null);

		camera = (Button) camera_album.findViewById(R.id.camera);
		photoAlbum = (Button) camera_album.findViewById(R.id.photoAlbum);
		cancelBtn = (Button) camera_album.findViewById(R.id.cancelBtn);

		camera.setOnClickListener(this);
		photoAlbum.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);

		// 다이얼로그 바깥부분 터치시 종료
		if (getDialog() != null)
			getDialog().setCanceledOnTouchOutside(true);

		return camera_album;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.camera:
    		takePicture();
    		break;
    	case R.id.photoAlbum:
    		photoAlbum();
    		break;
    	case R.id.cancelBtn:
    		getDialog().dismiss();
    		break;
		}
	}

	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 임시로 사용할 파일의 경로를 생성
		url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
		imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, REQUEST_TAKE_A_PICTURE);
	}
	
	private void photoAlbum() {
		/*
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(Images.Media.CONTENT_TYPE);
		intent.setData(Images.Media.EXTERNAL_CONTENT_URI);*/
		
		imageUri = Uri.fromFile( getTempFile() );
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		
		if(REQUEST_PROFILE_PHOTO){
			intent.putExtra("aspectX", 1);  
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
        }
        else if(REQUEST_REAL_PHOTO){
        	intent.putExtra("aspectX", 4);  
            intent.putExtra("aspectY", 3);
            intent.putExtra("outputX", 640);
            intent.putExtra("outputY", 480);
        }
		
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		
		startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
		Log.d("Processing photo", "In Pohto Album");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent intent = getActivity().getIntent();
		
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != FragmentActivity.RESULT_OK){
			Log.d("Processing photo", "failed to get Image");
			getTargetFragment().onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_CANCELED, intent);
			getDialog().dismiss();
			return;
		}
		
		switch (requestCode) {
		case REQUEST_PHOTO_ALBUM:
			Log.d("Processing photo", "Pohto Album");
			// bitmapImage = Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
			// temp_image.setImageURI(data.getData());
			// temp_image.setImageBitmap(bitmapImage);
			/*
			imageUri = data.getData();
			
			// intent.putExtra("bitmapStr", imageUri.toString());
            File original_file = getImageFile(imageUri);
            
            url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
    		imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
            File cpoy_file = new File(imageUri.getPath());
            
            // SD카드에 저장된 파일을 이미지 Crop을 위해 복사한다.
            copyFile(original_file , cpoy_file);*/
			
			intent.putExtra("bitmapStr", imageUri.toString());
			Log.d("Processing photo", "Image path = " + imageUri);
			getTargetFragment().onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_OK, intent);
			getDialog().dismiss();
			break;
		case REQUEST_TAKE_A_PICTURE:
			Log.d("Processing photo", "Take a photo");
			// bitmapImage = loadPicture();
			// temp_image.setImageBitmap(bitmapImage);
			
			// intent.putExtra("bitmapStr", imageUri.toString());
			
			// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
            // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
 
            Intent intentCrop = new Intent("com.android.camera.action.CROP");
            intentCrop.setDataAndType(imageUri, "image/*"); 
             
            // Crop한 이미지를 저장할 Path
            intentCrop.putExtra("output", imageUri);
            intentCrop.putExtra("crop", true);
            intentCrop.putExtra("scale", true);
            intentCrop.putExtra("noFaceDetection", true);
            
            if(REQUEST_PROFILE_PHOTO){
            	intentCrop.putExtra("aspectX", 1);  
                intentCrop.putExtra("aspectY", 1);
                intentCrop.putExtra("outputX", 512);
                intentCrop.putExtra("outputY", 512);
            }
            else if(REQUEST_REAL_PHOTO){
            	intentCrop.putExtra("aspectX", 4);  
                intentCrop.putExtra("aspectY", 3);
                intentCrop.putExtra("outputX", 640);
                intentCrop.putExtra("outputY", 480);
            }
            
            // Return Data를 사용하면 번들 용량 제한으로 크기가 큰 이미지는 넘겨 줄 수 없다.
            // intent.putExtra("return-data", true); 
            startActivityForResult(intentCrop, REQUEST_CROP_PHOTO);
			break;
		case REQUEST_CROP_PHOTO:
			Log.d("Processing photo", "CROP_FROM_CAMERA");
			
			intent.putExtra("bitmapStr", imageUri.toString());
			
			Log.d("Processing photo", imageUri.toString());
			/* 임시 파일 삭제
			File tempFile = new File(imageUri.getPath());
			if (tempFile.exists()) {
				Log.d("Camera & Album", "Photo deleted");
				tempFile.delete();
			}*/
			
			Log.d("Processing photo", "Image path = " + imageUri);
			getTargetFragment().onActivityResult(getTargetRequestCode(), FragmentActivity.RESULT_OK, intent);
			getDialog().dismiss();
			
			break;
		}
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		super.onDismiss(dialog);
		
		Log.d("CameraAlbumDialog", "onDismiss");
		
		REQUEST_PROFILE_PHOTO = false;
		REQUEST_REAL_PHOTO = false;
	}
	
	private File getTempFile(){
		url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
		File file = new File( Environment.getExternalStorageDirectory(), url);
		try{
			file.createNewFile();
		}
		catch( Exception e ){
			Log.e("Processing phot", "file Creation fail" );
		}
		return file;
	}
	
	/**
     * 선택된 uri의 사진 Path를 가져온다.
     * uri 가 null 경우 마지막에 저장된 사진을 가져온다.
     * @param uri
     * @return
     */
    public File getImageFile(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
 
        Cursor mCursor = getActivity().getContentResolver().query(uri, projection, null, null, 
                MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if(mCursor == null || mCursor.getCount() < 1) {
        	Log.d("CameraAlbum", "Cursor null");
            return null; // no cursor or no record
        }
        int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        mCursor.moveToFirst();
 
        String path = mCursor.getString(column_index);
 
        if (mCursor !=null ) {
            mCursor.close();
            mCursor = null;
        }
 
        return new File(path);
    }
    
	/**
     * 파일 복사
     * @param srcFile : 복사할 File
     * @param destFile : 복사될 File
     * @return
     */
    public boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally  {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
    
    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    private boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            OutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /*
    BitmapFactory.Options options = new BitmapFactory.Options();
    
    // 이미지는 로드하지 않고 높이와 넓이 속성 정보만 읽는다. 메모리 부담이 적게 된다.
    // true로 설정되어 있을 경우 decode 시에 null을 리턴한다. 로드된 이미지가 없기 때문에.
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(getActivity().getResources(), R.id.myimage, options);
    int imageHeight = options.outHeight;
    int imageWidth = options.outWidth;*/
    
    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
    	final BitmapFactory.Options options = new BitmapFactory.Options();
    	options.inJustDecodeBounds = true;
    	BitmapFactory.decodeResource(res, resId, options);
    	
    	options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
    	
    	// 로드하기 위해서는 위에서 true로 설정헀던 inJustDecodeBounds의 값을 false로 설정합니다
    	options.inJustDecodeBounds = false;
    	return BitmapFactory.decodeResource(res, resId, options);
    }
    
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
    	final int height = options.outHeight;
    	final int width = options.outWidth;
    	int inSampleSize = 1;
    	
    	if(height > reqHeight || width > reqWidth){
    		final int heightRatio = Math.round((float) height / (float) reqHeight);
    		final int widthRatio = Math.round((float) width / (float) reqWidth);
    		
    		inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    	}
    	
    	return inSampleSize;
    }
}
