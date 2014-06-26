package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import kr.ac.sogang.cs.ssep.Classes.User;
import kr.ac.sogang.cs.ssep.Classes.VOD;
import kr.ac.sogang.cs.ssep.minhoryang.MovieOnClickEvent;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
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
        else
        	showID.setText("안녕하세요  " + me + "님 - " + Integer.toString(this.myself.COIN) + "유디니(원)을 보유");
    }
    
    @ViewById LinearLayout userLayout;
	@SuppressWarnings("deprecation")
	private void ListUpMovies(){
		this.userLayout.removeAllViewsInLayout();
		int i = 0;
		LinearLayout cur = null;
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f);
		layoutParams.setMargins(10, 0, 0, 10);
		for(VOD v : this.db.VODs){
			if(++i % 3 == 1){
				cur = new LinearLayout(getApplicationContext());
				cur.setOrientation(LinearLayout.HORIZONTAL);
				cur.setLayoutParams(layoutParams);
				this.userLayout.addView(cur);
			}
			ImageView iv = new ImageView(getApplicationContext());
			iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f));
			iv.setAdjustViewBounds(true);
			
			if(v.thumbnail.contains("http"))
				Picasso.with(this.getApplicationContext()).load(v.thumbnail).placeholder(R.drawable.loading).into(iv);
			else
				Picasso.with(this.getApplicationContext()).load(new File(v.thumbnail)).placeholder(R.drawable.loading).into(iv);
			iv.setOnClickListener(new MovieOnClickEvent(getApplicationContext(), v, this.myself));
			cur.addView(iv);
		}
		int j = 3 - i%3;
		if(j!=3)
		while(j-->0){
			ImageView iv = new ImageView(getApplicationContext());
			iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f));
			iv.setAdjustViewBounds(true);
			Picasso.with(this.getApplicationContext()).load(R.drawable.loading).into(iv);
			cur.addView(iv);
		}
	}
	
	@ViewById EditText MovieNameForSearch;
	@Click void Search(){
		if(!MovieNameForSearch.getText().toString().equals("")){
			Intent a = new Intent(getApplicationContext(), SearchedMovieInfo_.class);
			a.putExtra("Query", MovieNameForSearch.getText().toString());
	    	a.putExtra("User", this.myself.ID);
			startActivity(a);
		}
	}	
}
