package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import kr.ac.sogang.cs.ssep.Classes.VOD;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

@EActivity(R.layout.movieinfoactivity)
public class MovieInfo extends Activity{
	SSEPDB db;
	VOD vod;
	
    @Override
    public void onResume(){
    	super.onResume();
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt");
        this.showMovie();
    }

    @Override
    public void onPause(){
    	super.onPause();
    	try {
			this.db.Save(new File(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    void showMovie(){
        String movie = getIntent().getExtras().getString("Movie");
        for(VOD i : this.db.VODs){
        	if(i.NAME.equals(movie))
        		this.vod = i;
        }
        if(this.vod == null)
        	onBackPressed();
        else{
        	// TODO 보여주기.
        }
    }
    
    @Click void Play(){
    	final VOD target = this.vod;
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setMessage("결제됩니다!").setPositiveButton("네", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int which) {
				Intent player = new Intent(Intent.ACTION_VIEW);
				player.setDataAndType(Uri.parse(target.mp4), "video/*");
				startActivity(player);
			}
		}).setNegativeButton("아니요", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface _dialog, int which) {
				_dialog.cancel();
			}
		}).setCancelable(false);
    	AlertDialog alert = dialog.create();
    	alert.setTitle("결제됩니다.");
    	alert.show();
    }
}
