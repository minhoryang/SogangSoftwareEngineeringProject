package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

@EActivity(R.layout.useractivity)
public class UserActivity extends Activity{
	SSEPDB db;
	
    @Override
    public void onResume(){
    	super.onResume();
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json");
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	try {
			this.db.Save(new File(Environment.getExternalStorageDirectory() + "//SSEP//db.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@Click
	void MoviePlay(){
		Intent player = new Intent(Intent.ACTION_VIEW);
		// http://cnu.sogang.ac.kr/update/ssep/maleficent.mp4
		// http://cnu.sogang.ac.kr/update/ssep/transformer4.mp4
		player.setDataAndType(Uri.parse("http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.mp4"), "video/*");
		startActivity(player);
	}
	
}
