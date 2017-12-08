package File_System_Structure;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testFileSystem {
	public static void main(String[] args) {
		try{
			OSManager manager = new OSManager();
			meun(manager);
			}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void meun(OSManager manager) {
		Scanner s = new Scanner(System.in);
		String str = null;
		System.out.println("***********" + "Welcome to use the file simulation operating system" + "***********");
		System.out.println();
		manager.showFile();

		System.out.println("Please enter command line（Enter help to view the command table）：");
		while ((str = s.nextLine()) != null) {
			if (str.equals("exit")) {
				System.out.println("Thank you！");
				break;
			}

			String[] strs = editStr(str);
			switch (strs[0]) {
			case "createFile":
				if (strs.length < 4) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.createFile(strs[1], strs[2],
							Integer.parseInt(strs[3]));
					}
				break;
			case "createCatalog":
				if (strs.length < 2) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.createCatolog(strs[1]);
					}
				break;
			case "open":
				if (strs.length < 2) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.openFile(strs[1]);
					}
				break;
			case "cd":
				if (strs.length < 2) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.openFile(strs[1]);
					}
				break;
			case "cd..":
				manager.backFile();
				break;
			case "delete":
				if (strs.length < 2) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.deleteFile(strs[1]);
					}
				break;
			case "rename":
				if (strs.length < 3) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.reName(strs[1], strs[2]);
					}
				break;
			case "search": {
				if (strs.length < 2) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					String[] roadName = strs[1].split("/");
					manager.searchFile(roadName);
					}
				break;
			}
			case "showFAT":
				manager.showFAT();
				break;
			case "addContents":
				if (strs.length < 3) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} 
				else {
					manager.reAdd(strs[1], Integer.parseInt(strs[2]));
					}
				break;
			case "changeType":
				if (strs.length < 3) {
					System.out.println("The command you have entered is incorrect. Please check it.");
				} else {
					manager.changeType(strs[1], strs[2]);
				}
				break;
			case "help": {
				System.out.println("The commands are as follows（Space cannot be omitted）：");
				System.out
						.println("createFile FileName fileType fileSize");
				System.out.println("<Create a file, for instance：createFile PUSU txt 5 >");
				System.out.println();
				System.out
						.println("createCatalog FatalogName");
				System.out.println("<Create a directory, for instance：createCatalog myFile >");
				System.out.println();
				System.out
						.println("open Name.FileTypt");
				System.out.println("<Open a file, for instance：open PUSU.txt >");
				System.out.println();
				System.out.println("cd CatalogName");
				System.out.println("<Open a directory, for instance： cd myFile >");
				System.out.println();
				System.out.println("cd..");
				System.out.println("<Return to the superior directory, for instance： cd..");
				System.out.println();
				System.out
						.println("delete FileName/CatalogName");
				System.out.println("<Delete a files or a directory (the directory must be empty), for instance：delete PUSU >");
				System.out.println();
				System.out
						.println("rename FileName/CatalogName NewName");
				System.out.println("<rename a file or a directory, for instance： rename myfile mycomputer >");
				System.out.println();
				System.out
						.println("search FileAbsolutedRoad/CatalogAbsolutedRoad");
				System.out.println("<Finding a file or directory based on an absolute path, for instance： search root/marco >");
				System.out.println();
				System.out.println("showFAT");
				System.out.println("<Look at the FAT table, for instance： showFAT>");
				System.out.println();
				System.out.println();
				System.out.println("The following command needs to open the file before：");
				System.out
						.println("addContents FileName ContentSize");
				System.out.println("<Add content to a file, for instance：ddContents PUSU 4 >");
				System.out.println();
				System.out
						.println("changeType FileName newType");
				System.out.println("<Change file type, for instance： changeType PUSU doc>");
				System.out.println();
				break;
			}
			default:
				for(String st : strs)
					System.out.println(st);
				System.out.println("The command you have entered is incorrect. Please check it.");
			}
			System.out.println("Please enter command line（Enter help to view the command table）：：");
		}
	}

	public static String[] editStr(String str) {
		Pattern pattern = Pattern.compile("([a-zA-Z0-9.\\\\/]*) *");// 根据空格分割输入命令
		Matcher m = pattern.matcher(str);
		ArrayList<String>  list = new ArrayList<String>();
		while(m.find()){
			list.add(m.group(1));
		}
		String[] strs = list.toArray(new String[list.size()]);
		
		for (int i = 1; i < strs.length; i++) { // 判断除命令以外每一个参数中是否含有 "."
			int j = strs[i].indexOf(".");
			if (j != -1) { // 若含有"." 将其切割 取前部分作为文件名
				String[] index = strs[i].split("\\."); // 使用转义字符"\\."
				strs[i] = index[0];
			}
		} 
		return strs;
	}

}
