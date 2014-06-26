package kr.ac.sogang.cs.ssep.minhoryang;

import java.io.File;
import java.util.List;

import com.squareup.picasso.Picasso;

import kr.ac.sogang.cs.ssep.R;
import kr.ac.sogang.cs.ssep.Classes.VOD;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListAdapter<T> extends BaseAdapter{

	List<T> datas;
	Context context;
	int textViewResourceId;
	
	@Override public int getCount() { return datas.size(); }
	@Override public T getItem(int position) { return datas.get(position); }
	@Override public long getItemId(int position) { return position; }
	@Override public View getView(int position, View convertView, ViewGroup parent) {
		View nowItemView;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			nowItemView = inflater.inflate(textViewResourceId, parent, false);
		}else{
			nowItemView = convertView;
		}
		if(getItem(position).getClass() == VOD.class){
			VOD v = (VOD)getItem(position);
			TextView tv = (TextView)nowItemView.findViewById(R.id.InfoTitle);
			if(v.NAME.contains("\n"))
				tv.setText(v.NAME);
			else
				tv.setText(v.NAME+'\n');
			tv.setTextColor(Color.BLACK);
			ImageView iv = (ImageView)nowItemView.findViewById(R.id.InfoImage);
			iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
			iv.setAdjustViewBounds(true);
			iv.setScaleType(ScaleType.CENTER);

			if(v.thumbnail.contains("http"))
				Picasso.with(this.context).load(v.thumbnail).into(iv);
			else
				Picasso.with(this.context).load(new File(v.thumbnail)).into(iv);

		}
		return nowItemView;
	}
	
	public void setData(Context _context, List<T> _datas, int _textViewResourceId) {
		this.datas = _datas;
		this.context = _context;
		this.textViewResourceId = _textViewResourceId;
	}

}
