package global;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import engine.myFiles;

public class Strings {
	
	private String language;
	private String path;
	private String[] strings;
	
	private FileReader fr;
	
	private int size;
		
	public Strings (String path) throws IOException{
		this.language = Settings.LANGUAGE;
		this.path = "strings/"+this.language+"/"+path;
		try {
			this.fr = new FileReader(this.path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FileReader don't work in " + this.path);
			e.printStackTrace();
		}
		
		try {
			this.size = myFiles.countLines(this.path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Counter Lines fail in " + this.path);
			e.printStackTrace();
		}
		
		String[] strings = new String[size];
		
		BufferedReader bufferedReader = new BufferedReader(fr);
		String line = null;
		
		try {
			line = bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail in bufferedReader");
			e.printStackTrace();
		}
		
		for (int i = 0; line != null; i++){
			strings[i] = line;
			line = bufferedReader.readLine();
		}
		
		
		this.strings = strings;
		
	}
	
	public String show(int index){
		return this.strings[index];
	}
}
