package kr.ac.sogang.cs.ssep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import kr.ac.sogang.cs.ssep.Classes.*;
public class SSEPDB {

	public static File DirectoryCreate(File base){
		File ourDirectory = new File(base + "//SSEP//");
		if(!ourDirectory.exists()) ourDirectory.mkdirs();
		return ourDirectory;
	}
	public static SSEPDB DBOpen(String dbf){
		File db = new File(dbf);
		if(db.exists()){
			try {
				return new SSEPDB(db);
			} catch (IOException e) {
				
			}
		}
		return new SSEPDB();
	}
	
	public static void main(String[] args) throws IOException {
		Gson a = new Gson();
		SSEPDB b = new SSEPDB();
		b.Users.add(new User("Hello", "World", 100, false));
		b.Users.add(new User("Minho", "Ryang", 200, true));
		b.Users.add(new User("What", "The", 300, false));
		b.Save(new File("test.txt"));
		SSEPDB c = new SSEPDB(new File("test.txt"));
		c.Users.add(new User("KIKI", "KATKAT", 400, false));
		System.out.println(a.toJson(c));
	}

	public ArrayList<User> Users;
	public ArrayList<VOD> VODs;
	public SSEPDB(){
		this.VODs = new ArrayList<VOD>();
		this.Users = new ArrayList<User>();
		this.VODs.add(new VOD("Maleficent",3000,"안젤리나 졸리","가장 강력한 마법을 가진 숲의 수호자 말레피센트는 인간왕국과의 거대한 전쟁의 소용돌이 속에서 왕국을 다스리는 스테판 왕의 딸 ‘오로라 공주’의 세례식 날, 거부할 수 없는 치명적인 저주를 내리는데...","http://cnu.sogang.ac.kr/update/ssep/maleficent.jpg","http://cnu.sogang.ac.kr/update/ssep/maleficent.mp4"));
		this.VODs.add(new VOD("Transformer4",5000,"마이클 베이, 피터 쿨렌, 존 굿맨","오토봇’과 ‘디셉티콘’의 마지막 결전 그 이후, 살아남은 인류는 폐허가 된 도시를 재건하기 위해 힘쓴다. 하지만 강력한 어둠의 세력이 정체를 드러내며 전 세계의 역사를 바꿀 새로운 위협이 인류를 향해 다가오고, ‘케이드 예거’(마크 윌버그)의 도움으로 ‘옵티머스 프라임’과 오토봇 군단은 지금껏 보지 못한 가장 위험한 적에 맞선 도전을 준비하는데...","http://cnu.sogang.ac.kr/update/ssep/transformer4.jpg","http://cnu.sogang.ac.kr/update/ssep/transformer4.mp4"));
		this.VODs.add(new VOD("Edge of Tomorrow",3000,"톰크루즈, 에밀리 블런트, 샤롯 라일리","가까운 미래, 미믹이라 불리는 외계 종족의 침략으로 인류는 멸망 위기를 맞는다. 빌 케이지 (톰 크루즈)는 자살 작전이나 다름없는 작전에 훈련이나 장비를 제대로 갖추지 못한 상태로 배정되고 전투에 참여하자마자 죽음을 맞는다. 하지만 불가능한 일이 일어난다. 그가 다시 그 끔찍한 날이 시작된 시간에 다시 깨어나 다시 전투에 참여하게 되고 다시 죽었다가 또 다시 살아나는 것. 외계인과의 접촉으로 같은 시간대를 반복해서 겪게 되는 타임 루프에 갇히게 된 것이다.","http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.jpg","http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.mp4"));
	}
	public SSEPDB(String in){
		SSEPDB t = new Gson().fromJson(in, SSEPDB.class);
		if(t.Users != null){
			this.Users = t.Users;
			for(User a : this.Users){
				if(a.usernummax<a.USERNUM)
					a.usernummax = a.USERNUM;
			}
		}else
			this.Users = new ArrayList<User>();
		if(t.VODs != null){
			this.VODs = t.VODs;
			for(VOD a : this.VODs){
				if(a.vodnummax<a.VODNUM)
					a.vodnummax = a.VODNUM;
			}
		}else
			this.VODs = new ArrayList<VOD>();
	}
	@SuppressWarnings("resource")
	public SSEPDB(File input) throws IOException{
		this(new BufferedReader(new FileReader(input)).readLine());
	}
	
	public void Save(File target) throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter(target));
		out.write(new Gson().toJson(this));
		out.close();
	}
}
