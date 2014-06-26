package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import kr.ac.sogang.cs.ssep.Classes.VOD;
import kr.ac.sogang.cs.ssep.minhoryang.FileDialog;
import kr.ac.sogang.cs.ssep.minhoryang.FileDialog.FileSelectedListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Environment;
import android.widget.EditText;

@EActivity(R.layout.adminactivity)
public class AdminActivity extends Activity{
	File ourDirectory;
	SSEPDB db;
	
    @Override
    public void onResume(){
    	super.onResume();
        this.ourDirectory = SSEPDB.DirectoryCreate(Environment.getExternalStorageDirectory());
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt");
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
	
    @ViewById EditText Name, Movie, Thumbnail, Price, Staff, Story;
    
	@Click void SelectMovie(){
		FileDialog newFileDialog = new FileDialog(this, this.ourDirectory);
		newFileDialog.addFileEndsWith(".mp4");
		newFileDialog.addFileListener(new WhatIfFileSelected(this.Movie));
		newFileDialog.showDialog();
	}

	@Click void SelectThumbnail(){
		FileDialog newFileDialog = new FileDialog(this, this.ourDirectory);
		newFileDialog.addFileEndsWith(".jpg");
		newFileDialog.addFileListener(new WhatIfFileSelected(this.Thumbnail));
		newFileDialog.showDialog();
	}
	
	class WhatIfFileSelected implements FileSelectedListener{
		EditText target;
		public WhatIfFileSelected(EditText _target){ this.target = _target; }
		
		@Override
		public void fileSelected(File file) {
			this.target.setText(file.toString());
		}
	}
	
	@Click void Upload(){
		this.db.VODs.add(new VOD(Name.getText().toString(), Integer.parseInt(Price.getText().toString()), Staff.getText().toString(), Story.getText().toString(), Thumbnail.getText().toString(), Movie.getText().toString()));
	}
}
