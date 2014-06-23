package kr.ac.sogang.cs.ssep.Classes;

import java.util.ArrayList;
import java.util.List;

import kr.ac.sogang.cs.ssep.SSEPDB;

public class VOD {
	public static int vodnummax;
	public int VODNUM;
	public String NAME;
	public int PRICE;
	public String STAFF;
	public String STORY;
	public String thumbnail;
	public String mp4;
	
	public VOD(String name, int price, String staff, String story, String thumbnail, String mp4){
		this.VODNUM = ++vodnummax;
		this.NAME = name;
		this.PRICE = price;
		this.STAFF = staff;
		this.STORY = story;
		this.thumbnail = thumbnail;
		this.mp4 = mp4;
	}
	
	public static List<VOD> Search(SSEPDB db, String name){
		List<VOD> ret = new ArrayList<VOD>();
		for(VOD v : db.VODs){
			if(v.NAME.contains(name))
				ret.add(v);
		}
		return ret;
	}
}
