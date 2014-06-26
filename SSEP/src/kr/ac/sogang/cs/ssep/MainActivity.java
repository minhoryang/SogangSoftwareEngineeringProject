package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;

import kr.ac.sogang.cs.ssep.Classes.User;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

@EActivity(R.layout.mainactivity)
@OptionsMenu(R.menu.mainactivitymenu)
public class MainActivity extends Activity {
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
    
    @ViewById EditText ID;
    @ViewById EditText PW;
    
    @Click
    void Login(){
		String id = ID.getText().toString();
		String pw = PW.getText().toString();
		if(id.length() > 1 && pw.length() > 1){
			User found = User.Login(this.db, id, pw);
			if(found != null){
				Intent a;
				if(found.ADMIN){
			    	a = new Intent(getApplicationContext(), AdminActivity_.class);
				}else{
					a = new Intent(getApplicationContext(), UserActivity_.class);
			    	a.putExtra("User", id);
				}
		    	startActivity(a);
			}else
				Toast.makeText(getApplicationContext(), "Æ²·È¾î¿ä!!", Toast.LENGTH_SHORT).show();
		}else
			Toast.makeText(getApplicationContext(), "Âª¾Æ¿ä!", Toast.LENGTH_SHORT).show();
    }
    
	@OptionsItem
	void Register(){
		String id = ID.getText().toString();
		String pw = PW.getText().toString();
		if(id.length() > 1 && pw.length() > 1)
			this.db.Users.add(new User(id, pw, 10000, false));
		else
			Toast.makeText(getApplicationContext(), "Âª¾Æ¿ä!", Toast.LENGTH_SHORT).show();
	}
	
	@OptionsItem
	void RegisterAsAdmin(){
		String id = ID.getText().toString();
		String pw = PW.getText().toString();
		if(id.length() > 1 && pw.length() > 1)
			this.db.Users.add(new User(id, pw, 0, true));
		else
			Toast.makeText(getApplicationContext(), "Âª¾Æ¿ä!", Toast.LENGTH_SHORT).show();
	}
	
	@OptionsItem
	void ClearDB(){
		this.db = new SSEPDB();
		try {
			this.db.Save(new File(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
