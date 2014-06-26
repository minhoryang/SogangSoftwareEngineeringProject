package kr.ac.sogang.cs.ssep.minhoryang;

import kr.ac.sogang.cs.ssep.MovieInfo_;
import kr.ac.sogang.cs.ssep.Classes.VOD;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MovieOnClickEvent implements OnClickListener{
	VOD id;
	Context context;
	
	public MovieOnClickEvent(Context _context, VOD _id){
		this.id = _id;
		this.context = _context;
	}
	
	@Override public void onClick(View v) {
		MovieOnClickEvent.Movie(this.context, this.id.NAME);
	}
	
	public static void Movie(Context c, String name){
		Intent a = new Intent(c, MovieInfo_.class);
    	a.putExtra("Movie", name);
    	a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	c.startActivity(a);
	}
}
