package kr.ac.sogang.cs.ssep.minhoryang;

import java.util.List;

import kr.ac.sogang.cs.ssep.R;
import kr.ac.sogang.cs.ssep.Classes.VOD;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
			TextView tv = (TextView)nowItemView.findViewById(R.id.textView1);
			tv.setText(((VOD)getItem(position)).NAME);
		}
		return nowItemView;
	}
	
	public void setData(Context _context, List<T> _datas, int _textViewResourceId) {
		this.datas = _datas;
		this.context = _context;
		this.textViewResourceId = _textViewResourceId;
	}

}
