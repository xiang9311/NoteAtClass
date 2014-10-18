package com.example.noteatclass;

import android.app.Activity;
import android.widget.Toast;

public class BaseActivity extends Activity {
	
	
	public void makeToast(String s){
		Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
	}
}
