package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.michaelevans.colorart.library.ColorArt;

import com.mattyork.colours.Colour;
import com.squareup.picasso.Picasso;

import kr.ac.sogang.cs.ssep.Classes.User;
import kr.ac.sogang.cs.ssep.Classes.VOD;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.movieinfoactivity)
public class MovieInfo extends Activity{
	SSEPDB db;
	VOD vod;
	User myself;
	
    @Override
    public void onResume(){
    	super.onResume();
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt");
        this.whoIAm();
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
    
    void whoIAm(){
        String me = getIntent().getExtras().getString("User");
        for(User i : this.db.Users){
        	if(i.ID.equals(me)){
        		this.myself = i;
        	}
        }
    }
    
    @ViewById ImageView InfoThumbnail;
    @ViewById TextView InfoTitle;
    @ViewById TextView InfoStaff;
    @ViewById TextView InfoStory;
    @ViewById(R.id.Play) Button PlayButton;
    void showMovie(){
        String movie = getIntent().getExtras().getString("Movie");
        for(VOD i : this.db.VODs){
        	if(i.NAME.equals(movie))
        		this.vod = i;
        }
        if(this.vod == null)
        	onBackPressed();
        else{
        	final String thumbnailurl = this.vod.thumbnail;
			if(this.vod.thumbnail.contains("http")){
				Picasso.with(this.getApplicationContext()).load(this.vod.thumbnail).into(InfoThumbnail);
			}else{
				Picasso.with(this.getApplicationContext()).load(new File(this.vod.thumbnail)).into(InfoThumbnail);
			}
			InfoTitle.setText(this.vod.NAME);
			InfoStaff.setText(this.vod.STAFF);
			InfoStory.setText(this.vod.STORY);
			PlayButton.setText("보러가기! " + this.vod.PRICE + "유디니");
			setTitle(this.myself.ID + "님, " + this.myself.COIN + "유디니(원)");
        }
        test();
    }
    @Background()
    void test(){
    	ColorArt ca = null;
		try {
			if(this.vod.thumbnail.contains("http")){
				ca = new ColorArt(Picasso.with(this.getApplicationContext()).load(this.vod.thumbnail).get());
			}else{
				ca = new ColorArt(Picasso.with(this.getApplicationContext()).load(new File(this.vod.thumbnail)).get());
			}
			test2(ca);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @ViewById LinearLayout Header;
    @UiThread
    void test2(ColorArt ca){
    	Typeface tf = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf.mp3");
    	Header.setBackgroundColor(ca.getBackgroundColor());
    	InfoTitle.setTextColor(ca.getPrimaryColor());
    	InfoTitle.setTypeface(tf);
    	InfoStaff.setTextColor(ca.getDetailColor());
    	InfoStaff.setTypeface(tf);
    	InfoStory.setTextColor(ca.getDetailColor());
    	InfoStory.setTypeface(tf);
    	PlayButton.setBackgroundColor(ca.getSecondaryColor());
    	PlayButton.setTypeface(tf);
    	PlayButton.setTextColor(Colour.blackOrWhiteContrastingColor(ca.getSecondaryColor()));
    }
    
    @Click void Play(){
    	final VOD target = this.vod;
        if(this.myself != null){
        	boolean isAlreadyGot = false;
        	for(VOD a : myself.PURCHASE_LIST){
        		if(a.NAME.equals(target.NAME)){
        			isAlreadyGot = true;
        			break;
        		}
        	}
        	if(isAlreadyGot){
    	    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	    	dialog.setMessage("과금 없이 재생됩니다.").setPositiveButton("알겠습니다", new DialogInterface.OnClickListener() {
    				@Override
    				public void onClick(DialogInterface _dialog, int which) {
    					Intent player = new Intent(Intent.ACTION_VIEW);
    					player.setDataAndType(Uri.parse(target.mp4), "video/*");
    					startActivity(player);
    				}
    			});
    	    	AlertDialog alert = dialog.create();
    	    	alert.setTitle("이미 구매했습니다.");
    	    	alert.show();
        	}else{
	        	if(this.myself.CheckPrice(target.PRICE)){
	        		final User targetUser = myself;
	    	    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	    	dialog.setMessage(
	    	    			myself.COIN + " - " + target.PRICE + " = " + (myself.COIN-target.PRICE) + "유디니."
	    	    	).setPositiveButton("네, 보러갑시다!", new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface _dialog, int which) {
	    					targetUser.PurchaseVOD(target);
	    					
	    					Intent player = new Intent(Intent.ACTION_VIEW);
	    					player.setDataAndType(Uri.parse(target.mp4), "video/*");
	    					startActivity(player);
	    				}
	    			}).setNegativeButton("아니요", new DialogInterface.OnClickListener(){
	    				@Override
	    				public void onClick(DialogInterface _dialog, int which) {
	    					_dialog.cancel();
	    				}
	    			});
	    	    	AlertDialog alert = dialog.create();
	    	    	alert.setTitle("잠깐! 진짜 볼까요?");
	    	    	alert.show();
	        	}else{
	    	    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	    	dialog.setMessage("코인이 부족합니다.").setPositiveButton("알겠습니다", new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface _dialog, int which) {
	    					_dialog.cancel();
	    				}
	    			}).setCancelable(false);
	    	    	AlertDialog alert = dialog.create();
	    	    	alert.setTitle("코인이 부족합니다.");
	    	    	alert.show();
	        	}
        	}
        }
    }
}
