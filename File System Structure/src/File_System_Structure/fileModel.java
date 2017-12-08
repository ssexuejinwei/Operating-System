package File_System_Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fileModel {
//fileModel类用来记录文件或目录的相关属性

	public Map<String, fileModel> subMap = new HashMap<String, fileModel>();
	private String name; //文件名或目录名
	private	String type; //文件类型
	private int attr; //用来识别是文件还是目录 
	private int startNum;	//在FAT表中起始位置
	private int size;	//文件的大小
	private fileModel father = null;	//该文件或目录的上级目录
	
	public fileModel( String name, String type, int startNum, int size ){
		this.name = name;
		this.type = type;
		this.attr = 2;
		this.startNum = startNum;
		this.size = size;		
	}
	
	public fileModel( String name, int startNum ) {
		this.name = name;
		this.attr = 3;
		this.startNum = startNum;
		this.type = "  ";
		this.size = 1;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAttr() {
		return attr;
	}
	public void setAttr(int attr) {
		this.attr = attr;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public fileModel getFather() {
		return father;
	}

	public void setFather(fileModel father) {
		this.father = father;
	}

}
