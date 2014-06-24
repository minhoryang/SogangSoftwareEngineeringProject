package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import kr.ac.sogang.cs.ssep.minhoryang.FileDialog;
import kr.ac.sogang.cs.ssep.minhoryang.FileDialog.FileSelectedListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

@EActivity(R.layout.adminactivity)
public class AdminActivity extends Activity{
	File ourDirectory;
	SSEPDB db;
	
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
	
	class WhatIfFileSelected implements FileSelectedListener{
		Activity parent;
		public WhatIfFileSelected(Activity _parent){ this.parent = _parent; }
		
		@Override
		public void fileSelected(File file) {
			Toast.makeText(this.parent, file.toString(), Toast.LENGTH_SHORT).show();
		}
	}
}
