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
		this.VODs.add(new VOD("Maleficent",3000,"�������� ����","���� ������ ������ ���� ���� ��ȣ�� �����Ǽ�Ʈ�� �ΰ��ձ����� �Ŵ��� ������ �ҿ뵹�� �ӿ��� �ձ��� �ٽ����� ������ ���� �� �����ζ� ���֡��� ���ʽ� ��, �ź��� �� ���� ġ������ ���ָ� �����µ�...","http://cnu.sogang.ac.kr/update/ssep/maleficent.jpg","http://cnu.sogang.ac.kr/update/ssep/maleficent.mp4"));
		this.VODs.add(new VOD("Transformer4",5000,"����Ŭ ����, ���� ��, �� �¸�","���亿���� �����Ƽ�ܡ��� ������ ���� �� ����, ��Ƴ��� �η��� ���㰡 �� ���ø� ����ϱ� ���� ������. ������ ������ ����� ������ ��ü�� �巯���� �� ������ ���縦 �ٲ� ���ο� ������ �η��� ���� �ٰ�����, �����̵� ���š�(��ũ ������)�� �������� ����Ƽ�ӽ� �����ӡ��� ���亿 ������ ���ݲ� ���� ���� ���� ������ ���� �¼� ������ �غ��ϴµ�...","http://cnu.sogang.ac.kr/update/ssep/transformer4.jpg","http://cnu.sogang.ac.kr/update/ssep/transformer4.mp4"));
		this.VODs.add(new VOD("Edge of Tomorrow",3000,"��ũ����, ���и� ��Ʈ, ���� ���ϸ�","����� �̷�, �̹��̶� �Ҹ��� �ܰ� ������ ħ������ �η��� ��� ���⸦ �´´�. �� ������ (�� ũ����)�� �ڻ� �����̳� �ٸ����� ������ �Ʒ��̳� ��� ����� ������ ���� ���·� �����ǰ� ������ �������ڸ��� ������ �´´�. ������ �Ұ����� ���� �Ͼ��. �װ� �ٽ� �� ������ ���� ���۵� �ð��� �ٽ� ��� �ٽ� ������ �����ϰ� �ǰ� �ٽ� �׾��ٰ� �� �ٽ� ��Ƴ��� ��. �ܰ��ΰ��� �������� ���� �ð��븦 �ݺ��ؼ� �ް� �Ǵ� Ÿ�� ������ ������ �� ���̴�.","http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.jpg","http://cnu.sogang.ac.kr/update/ssep/edge_of_tomorrow.mp4"));
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
