package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import kr.ac.sogang.cs.ssep.Classes.User;
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
        	// TODO �����ֱ�.
        }
    }
    
    @Click void Play(){
    	final VOD target = this.vod;
    	User myself = null;
        String me = getIntent().getExtras().getString("User");
        for(User i : this.db.Users){
        	if(i.ID.equals(me)){
        		myself = i;
        	}
        }
        if(myself != null){
        	boolean isAlreadyGot = false;
        	for(VOD a : myself.PURCHASE_LIST){
        		if(a.NAME.equals(target.NAME)){
        			isAlreadyGot = true;
        			break;
        		}
        	}
        	if(isAlreadyGot){
    	    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	    	dialog.setMessage("���� ���� ����˴ϴ�.").setPositiveButton("�˰ڽ��ϴ�", new DialogInterface.OnClickListener() {
    				@Override
    				public void onClick(DialogInterface _dialog, int which) {
    					Intent player = new Intent(Intent.ACTION_VIEW);
    					player.setDataAndType(Uri.parse(target.mp4), "video/*");
    					startActivity(player);
    				}
    			}).setCancelable(false);
    	    	AlertDialog alert = dialog.create();
    	    	alert.setTitle("�̹� �����߽��ϴ�.");
    	    	alert.show();
        	}else{
	        	if(myself.CheckPrice(target.PRICE)){
	        		final User targetUser = myself;
	    	    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	    	dialog.setMessage("�����˴ϴ�!").setPositiveButton("��", new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface _dialog, int which) {
	    					targetUser.PurchaseVOD(target);
	    					
	    					Intent player = new Intent(Intent.ACTION_VIEW);
	    					player.setDataAndType(Uri.parse(target.mp4), "video/*");
	    					startActivity(player);
	    				}
	    			}).setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener(){
	    				@Override
	    				public void onClick(DialogInterface _dialog, int which) {
	    					_dialog.cancel();
	    				}
	    			}).setCancelable(false);
	    	    	AlertDialog alert = dialog.create();
	    	    	alert.setTitle("�����˴ϴ�.");
	    	    	alert.show();
	        	}else{
	    	    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	    	dialog.setMessage("������ �����մϴ�.").setPositiveButton("�˰ڽ��ϴ�", new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface _dialog, int which) {
	    					_dialog.cancel();
	    				}
	    			}).setCancelable(false);
	    	    	AlertDialog alert = dialog.create();
	    	    	alert.setTitle("������ �����մϴ�.");
	    	    	alert.show();
	        	}
        	}
        }
    }
}