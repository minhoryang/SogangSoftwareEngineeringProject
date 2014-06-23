package kr.ac.sogang.cs.ssep.Classes;

import java.util.ArrayList;

import kr.ac.sogang.cs.ssep.SSEPDB;

public class User {
	public static int usernummax;
	public int USERNUM;
	public String ID;
	public String PW;
	public int COIN;
	public boolean ADMIN;
	public ArrayList<VOD> PURCHASE_LIST;
	
	public User(String name, String password, int coin){
		this.USERNUM = ++usernummax;
		this.ID = name;
		this.PW = password;
		this.COIN = coin;
		this.PURCHASE_LIST = new ArrayList<VOD>();
	}
	
	public static User Login(SSEPDB db, String name, String password){
		for(User t : db.Users){
			if(t.ID.equals(name)){
				if(t.PW.equals(password)){
					return t;
				}else
					break;
			}
		}
		return null;
	}
	
	public boolean CheckPrice(int coin){
		if(this.COIN - coin >= 0)
			return true;
		return false;
	}
	
	public VOD PurchaseVOD(VOD wanted){
		if(this.CheckPrice(wanted.PRICE)){
			this.PURCHASE_LIST.add(wanted);
			this.COIN -= wanted.PRICE;
			return wanted;
		}
		return null;
	}
}
