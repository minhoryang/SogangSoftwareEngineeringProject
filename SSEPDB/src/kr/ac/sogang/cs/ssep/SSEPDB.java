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
		this.VODs.add(new VOD("Maleficent\n말리피센트",3000,"안젤리나 졸리","가장 강력한 마법을 가진 숲의 수호자 말레피센트는 인간왕국과의 거대한 전쟁의 소용돌이 속에서 왕국을 다스리는 스테판 왕의 딸 ‘오로라 공주’의 세례식 날, 거부할 수 없는 치명적인 저주를 내리는데...","http://cnu.sogang.ac.kr/update/ssep/maleficent.jpg","http://cnu.sogang.ac.kr/update/ssep/maleficent.mp4"));
		this.VODs.add(new VOD("Transformer4\n트랜스포머4",5000,"마이클 베이, 피터 쿨렌, 존 굿맨","오토봇’과 ‘디셉티콘’의 마지막 결전 그 이후, 살아남은 인류는 폐허가 된 도시를 재건하기 위해 힘쓴다. 하지만 강력한 어둠의 세력이 정체를 드러내며 전 세계의 역사를 바꿀 새로운 위협이 인류를 향해 다가오고, ‘케이드 예거’(마크 윌버그)의 도움으로 ‘옵티머스 프라임’과 오토봇 군단은 지금껏 보지 못한 가장 위험한 적에 맞선 도전을 준비하는데...","http://cnu.sogang.ac.kr/update/ssep/transformer4.jpg","http://cnu.sogang.ac.kr/update/ssep/transformer4.mp4"));
		this.VODs.add(new VOD("Edge of Tomorrow\n에지 오브 투마로우",3000,"톰크루즈, 에밀리 블런트, 샤롯 라일리","가까운 미래, 미믹이라 불리는 외계 종족의 침략으로 인류는 멸망 위기를 맞는다. 빌 케이지 (톰 크루즈)는 자살 작전이나 다름없는 작전에 훈련이나 장비를 제대로 갖추지 못한 상태로 배정되고 전투에 참여하자마자 죽음을 맞는다. 하지만 불가능한 일이 일어난다. 그가 다시 그 끔찍한 날이 시작된 시간에 다시 깨어나 다시 전투에 참여하게 되고 다시 죽었다가 또 다시 살아나는 것. 외계인과의 접촉으로 같은 시간대를 반복해서 겪게 되는 타임 루프에 갇히게 된 것이다.","http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.jpg","http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.mp4"));
		this.VODs.add(new VOD("Confession\n좋은친구들",4000,"지성, 주지훈, 이광수","세상에 둘도 없는 우정을 나눈 세 남자 현태, 인철, 민수. 거액의 현금이 사라진 강도화재사건으로 현태의 가족이 죽고 사건은 미궁에 빠진다. 수사 과정도 경찰도 의심스러운 현태는 사건을 집요하게 파헤치기 시작하고, 인철과 민수에게 도움을 청한다. 그러나, 사건을 파헤칠수록 믿었던 친구들 마저 의심스러워 지는데...","http://cnu.sogang.ac.kr/update/ssep/confession.jpg","http://cnu.sogang.ac.kr/update/ssep/confession.mp4"));
		this.VODs.add(new VOD("Her\n그녀",4000,"호아킨 피닉스, 스칼렛 요한슨","‘테오도르’(호아킨 피닉스)는 다른 사람들의 편지를 대신 써주는 대필 작가로,아내(루니 마라)와 별거 중이다. 타인의 마음을 전해주는 일을 하고 있지만,정작 자신은 너무 외롭고 공허한 삶을 살고 있다. 그러던 어느 날, 스스로 생각하고 느끼는 인공 지능 운영체제인 ‘사만다’(스칼렛 요한슨)를 만나게 된다. 자신의 말에 귀 기울이고, 이해해주는 ‘사만다’로 인해 조금씩 행복을 되찾기 시작한 ‘테오도르’는 점점 그녀에게 사랑을 느끼게 되는데...","http://cnu.sogang.ac.kr/update/ssep/her.jpg","http://cnu.sogang.ac.kr/update/ssep/her.mp4"));
		this.VODs.add(new VOD("이브 생 로랑",4000,"샤롯 르본, 피에르 니네이","크리스찬 디올의 갑작스런 사망 후, 이브 생 로랑은 21살이라는 어린 나이에 그를 뒤이을 수석 디자이너로 임명된다. 패션계의 모든 이목이 집중된 가운데 첫 컬렉션을 성공리에 치른 이브는 평생의 파트너가 될 피에르 베르제를 만나게 된다. 그 후 두 사람은 함께 이브 생 로랑의 이름을 내세운 개인 브랜드를 설립하고 이브는 발표하는 컬렉션마다 센세이션을 일으키며 세계적인 디자이너로 발돋움한다. 하지만 이브가 모델, 동료 디자이너들과 어울려 방탕한 생활에 빠지면서 베르제와의 갈등은 깊어지고 조울증도 더욱 악화가 되는데...","http://cnu.sogang.ac.kr/update/ssep/ives.jpg","http://cnu.sogang.ac.kr/update/ssep/ives.mp4"));
		this.VODs.add(new VOD("Grace of Monaco\n그레이스 오브 모나코",2000,"니콜 키드먼, 팀 로스","아름답고 우아한 이미지로 전 세계인의 사랑을 받던 ‘할리우드의 여신’ 그레이스는 모나코의 레니에 3세와 세기의 결혼식을 올리면서 할리우드를 떠난다. 하지만, 답답한 왕실 생활에 서서히 지쳐가던 그녀는 히치콕 감독의 영화계 복귀 제안에 마음이 흔들리고, 모나코를 합병시키고 싶었던 프랑스는 할리우드 복귀를 고민하는 그녀를 이용해 모나코 왕실을 위기에 빠트리는데... 모나코의 운명을 바꾼 세기의 여배우! 그녀 생애 가장 위대한 순간이 펼쳐진다!","http://cnu.sogang.ac.kr/update/ssep/grace_of_monaco.jpg","http://cnu.sogang.ac.kr/update/ssep/grace_of_monaco.mp4"));
		this.VODs.add(new VOD("신의 한 수",1000,"정우성, 안성기, 이범수","프로 바둑기사 태석(정우성)은 내기바둑판에서 살수(이범수)팀의 음모에 의해 형을 잃는다. 심지어살인 누명을 쓰고 교도소에서 복역하기에 이르고, 몇 년 후 살수와의 대결을 위해 전국의 내로라하는 선수들을 모은다. 각자의 복수와 마지막 한판 승부를 위해 모인 태석(정우성), 주님(안성기), 꽁수(김인권), 허목수(안길강)는 승부수를 띄울 판을 짠다. 단 한번이라도 지면 절대 살려두지 않는 악명 높은 살수(이범수)팀을 향한 계획된 승부가 차례로 시작되고... 범죄로 인해 곪아버린 내기바둑판에서 꾼들의 명승부가 펼쳐진다.","http://cnu.sogang.ac.kr/update/ssep/god1.jpg","http://cnu.sogang.ac.kr/update/ssep/god1.mp4"));
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
