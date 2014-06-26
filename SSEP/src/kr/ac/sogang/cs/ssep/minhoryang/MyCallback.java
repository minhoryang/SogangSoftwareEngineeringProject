package kr.ac.sogang.cs.ssep.minhoryang;

import java.io.File;
import java.io.IOException;

import org.michaelevans.colorart.library.ColorArt;

import android.app.Activity;
import android.os.Handler;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MyCallback implements Callback{
	protected Activity parent;
	protected String url;
	public MyCallback(Activity act,String url) {
		this.parent = act;
		this.url = url;
	}

	@Override
	public void onError() {	}

	@Override
	public void onSuccess() {
		final String _url = this.url;
		final Activity _act = this.parent;
		new Handler().post(new Runnable(){

			@Override
			public void run() {
				try{
					ColorArt ca = null;
					if(_url.contains("http")){
						ca = new ColorArt(Picasso.with(_act.getApplicationContext()).load(_url).get());
					}else{
						ca = new ColorArt(Picasso.with(_act.getApplicationContext()).load(new File(_url)).get());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			;
		});
	}
}
