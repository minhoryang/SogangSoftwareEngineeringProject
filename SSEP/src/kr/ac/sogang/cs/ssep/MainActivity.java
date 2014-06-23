package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import kr.ac.sogang.cs.ssep.minhoryang.FileDialog;
import kr.ac.sogang.cs.ssep.minhoryang.FileDialog.FileSelectedListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

@EActivity
public class MainActivity extends Activity {
	File ourDirectory;
	SSEPDB db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
        this.ourDirectory = SSEPDB.DirectoryCreate(Environment.getExternalStorageDirectory());
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json");
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	try {
			this.db.Save(new File(Environment.getExternalStorageDirectory() + "//SSEP//db.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Click
	void SameAsButtonID(View clickedView){
		FileDialog newFileDialog = new FileDialog(this, this.ourDirectory);
		newFileDialog.addFileEndsWith(".mp4");
		newFileDialog.addFileListener(new WhatIfFileSelected(this));
		newFileDialog.showDialog();
	}
	
	@Click
	void MoviePlay(){
		Intent player = new Intent(Intent.ACTION_VIEW);
		// http://cnu.sogang.ac.kr/update/ssep/maleficent.mp4
		// http://cnu.sogang.ac.kr/update/ssep/transformer4.mp4
		player.setDataAndType(Uri.parse("http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.mp4"), "video/*");
		startActivity(player);
	}
	
	class WhatIfFileSelected implements FileSelectedListener{
		Activity parent;
		public WhatIfFileSelected(Activity _parent){ this.parent = _parent; }
		
		@Override
		public void fileSelected(File file) {
			Toast.makeText(this.parent, file.toString(), Toast.LENGTH_SHORT).show();
		}
	}
}
