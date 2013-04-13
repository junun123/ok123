package com.example.photo;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {
	private static final int PIC_CROP = 2;
	private Uri picUri;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dispatchTakePictureIntent(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void dispatchTakePictureIntent(int actionCode) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(takePictureIntent, actionCode);
	}
// this is app for crop operaion of image
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	    
	picUri = data.getData();
	performCrop();
	    }
	    else if(requestCode == PIC_CROP){
			//get the returned data
			Bundle extras = data.getExtras();
			//get the cropped bitmap
			Bitmap thePic = extras.getParcelable("data");
			
			//retrieve a reference to the ImageView
			ImageView picView = (ImageView)findViewById(R.id.picture);
			//display the returned cropped image
			picView.setImageBitmap(thePic);
		}
	}
	private void performCrop(){
		
		final int PIC_CROP = 2;
		try {
		}
		catch(ActivityNotFoundException anfe){
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}
		//keep track of cropping intent
		
	    //call the standard crop action intent (the user device may not support it)
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		    //indicate image type and Uri
		cropIntent.setDataAndType(picUri, "image/*");
		    //set crop properties
		cropIntent.putExtra("crop", "true");
		    //indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		    //indicate output X and Y
		cropIntent.putExtra("outputX", 256);
		cropIntent.putExtra("outputY", 256);
		    //retrieve data on return
		cropIntent.putExtra("return-data", true);
		    //start the activity - we handle returning in onActivityResult
		startActivityForResult(cropIntent, PIC_CROP);
	
		
	}



}// this is app for taking image
