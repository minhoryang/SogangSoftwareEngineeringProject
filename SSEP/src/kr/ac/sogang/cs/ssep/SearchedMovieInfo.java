package kr.ac.sogang.cs.ssep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kr.ac.sogang.cs.ssep.Classes.VOD;
import kr.ac.sogang.cs.ssep.minhoryang.ListAdapter;
import kr.ac.sogang.cs.ssep.minhoryang.MovieOnClickEvent;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Environment;
import android.widget.ListView;

@EActivity(R.layout.searchedmovieinfoactivity)
public class SearchedMovieInfo extends Activity{
	SSEPDB db;
	
    @Override
    public void onResume(){
    	super.onResume();
        this.db = SSEPDB.DBOpen(Environment.getExternalStorageDirectory() + "//SSEP//db.json.txt");
        this.searchedResult();
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
    
    @ViewById ListView searchMovieInfoList;
    void searchedResult(){
        String query = getIntent().getExtras().getString("Query");
        setTitle(" " + query + " 로 검색했더니?");
        ListAdapter<VOD> resultsAdapter = new ListAdapter<VOD>();
        ArrayList<VOD> datas =  new ArrayList<VOD>();
        resultsAdapter.setData(getApplicationContext(), datas, R.layout.searchedmovieinfoentry);
        for(VOD v : this.db.VODs){
        	if(v.NAME.contains(query)){
        		datas.add(v);
        	}
        }
        searchMovieInfoList.setAdapter(resultsAdapter);
        resultsAdapter.notifyDataSetChanged();
    }
    
    @ItemClick void searchMovieInfoListItemClicked(VOD v){
        String me = getIntent().getExtras().getString("User");
    	MovieOnClickEvent.Movie(getApplicationContext(), v.NAME, me);
    }
}
