package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kr.ac.sogang.cs.ssep.Classes.User;
import kr.ac.sogang.cs.ssep.Classes.VOD;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

@EActivity(R.layout.useractivity)
public class UserActivity extends Activity{
	SSEPDB db;
	User myself;
	
    @Override
    public void onResume(){
    	super.onResume();
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt");
        this.onLogin();
        this.ListUpMovies();
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
    
    @ViewById TextView showID;
    private void onLogin(){
        String me = getIntent().getExtras().getString("User");
        for(User i : this.db.Users){
        	if(i.ID.equals(me))
        		this.myself = i;
        }
        if(this.myself == null)
        	onBackPressed();
        showID.setText("æ»≥Á«œººø‰ : " + me + "¥‘. (" + Integer.toString(this.myself.COIN) + "Öﬂ)");
    }
    
    @ViewById LinearLayout userLayout;
	private void ListUpMovies(){
		this.userLayout.removeAllViewsInLayout();
		for(VOD v : this.db.VODs){
			ImageView iv = new ImageView(getApplicationContext());
			iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			iv.setScaleType(ScaleType.FIT_XY);
			Picasso.with(this.getApplicationContext()).load(v.thumbnail).into(iv);
			iv.setOnClickListener(new MovieOnClickEvent(v.mp4));
			this.userLayout.addView(iv);
		}

	}
	
	class MovieOnClickEvent implements OnClickListener{
		private String url;
		public MovieOnClickEvent(String url){ this.url = url; }
		@Override public void onClick(View v) {
			Intent player = new Intent(Intent.ACTION_VIEW);
			player.setDataAndType(Uri.parse(this.url), "video/*");
			startActivity(player);
		}
	}
}
