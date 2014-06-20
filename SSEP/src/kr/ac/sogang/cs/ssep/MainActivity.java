package kr.ac.sogang.cs.ssep;

import java.io.File;

import kr.ac.sogang.cs.ssep.minhoryang.FileDialog;
import kr.ac.sogang.cs.ssep.minhoryang.FileDialog.FileSelectedListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

@EActivity
public class MainActivity extends Activity {
	File ourDirectory;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        
        // XXX Directory Create.
        this.ourDirectory = new File(Environment.getExternalStorageDirectory() + "//SSEP//");
		if(!this.ourDirectory.exists()) this.ourDirectory.mkdirs();
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
