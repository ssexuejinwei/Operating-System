package File_System_Structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OSManager {
	//OSManager这个类实现对文件的各种操作

	public Map<String, fileModel> totalFiles = new HashMap<String, fileModel>();
	//定义FAT表
	private int[] fat = new int[128]; 
	//创建根目录 使用fat表的第一项
	private fileModel root = new fileModel("root", 1);
	private fileModel nowCatalog = root;
	
	public OSManager() {
		//将FAT表初始化全部为0，并将第一位设为根目录的空间
		for( int i = 0; i < fat.length ; i++ ) {
			fat[i] = 0;
		}
		fat[ 1 ] = 255; //255表示磁盘块已占用
		fat[ 0 ] = 126; //纪录磁盘剩余块数	
		root.setFather( root );
		totalFiles.put( "root", root );
	}
	
	public int setFat(int size) {
		int[] startNum = new int[128];
		int i = 2; //纪录fat循环定位
		for( int j = 0; j < size; i++ ) {
			if( fat[ i ] == 0 ) {
				startNum[ j ] = i; //纪录该文件所有磁盘块
				if( j > 0 ) {
					fat[ startNum[ j-1 ] ] = i; //fat上一磁盘块指向下一磁盘块地址
				}
				j++;
			}
		}
		fat[ i-1 ] = 255;
		return startNum[ 0 ]; //返回该文件起始块盘号
	}


	/*
	 * 
	 * 该方法用于删除时释放FAT表的空间
	 */
	public void deleteFAT( int startNum ) {
		int nextPoint = fat[ startNum ];
		int nowPoint = startNum;
		int count = 0;
		while( fat[nowPoint ] != 0 ) {
			nextPoint = fat[ nowPoint ];
			if( nextPoint == 255 ) {
				fat[ nowPoint ] =0;
				count++;
				break;
			} else {
				fat[ nowPoint ] = 0;
				count++;
				nowPoint = nextPoint;
			}
		}
		fat[0] += count;
	}
	

	/*
	 * 
	 * 以下为追加内容时修改fat表
	 * 
	 */
	public void AddFAT(int startNum, int addSize) {
		int nowPoint = startNum;
		int nextPoint = fat[startNum];
		while( fat[ nowPoint ] != 255 ) {
			nowPoint = nextPoint;
			nextPoint = fat[ nowPoint ];
		}//找到该文件终结盘块

		for( int i = 2, count = 0; count < addSize; i++ ) {
			if( fat[ i ] == 0 ) {
				fat[ nowPoint ] = i;
				nowPoint = i;
				count++;
				fat[ nowPoint ] = 255;//作为当前文件终结盘块
			}
		}
	}
	
	/*
	 * 	以下为创建文件和目录方法
	 * 
	 */
	public void createFile( String name, String type, int size ) {
		
		if( fat[ 0 ] >= size ) {	//判断磁盘剩余空间是否足够建立文件
			fileModel value = nowCatalog.subMap.get( name ); //该目录下是否寻找同名目录或文件
			if( value != null ) {  //判断该文件是否存在
				if( value.getAttr() == 3 ) {   //若存在同名目录 继续创建文件
					int startNum = setFat( size ); 
					fileModel file = new fileModel( name, type, startNum, size );
					file.setFather( nowCatalog ); //纪录上一层目录
					nowCatalog.subMap.put( name, file ); //在父目录添加该文件
					totalFiles.put( file.getName(), file );
					fat[ 0 ] -= size;
					System.out.println( "File is successfully created!" );
					showFile();
				} else if( value.getAttr() == 2 ) { //若同名文件已存在，创建失败
					System.out.println("File fails to create because the file already exists"); 
					showFile();
				}
			} else if( value == null ) { //若无同名文件或文件夹，继续创建文件
				int startNum = setFat( size ); 
				fileModel file = new fileModel( name, type, startNum, size );
				file.setFather( nowCatalog ); //纪录上一层目录
				nowCatalog.subMap.put( name, file ); //在父目录添加该文件
				totalFiles.put( file.getName(), file );
				fat[0] -= size;
				System.out.println( "File is successfully created!");
				showFile();
				}
		} else {
			System.out.println("File fails to create because insufficient disk space！");
		}
	
	}
	
	public void createCatolog( String name ) {
		
		if( fat[ 0 ] >= 1 ) { //判断磁盘空间是否足够创建文件夹
			
			fileModel value = nowCatalog.subMap.get( name ); //判断该目录下是否存在同名目录或文件
			if( value != null ) {
				if( value.getAttr() == 2 ) {
					int startNum = setFat( 1 );
					fileModel catalog = new fileModel( name, startNum );
					catalog.setFather( nowCatalog ); //纪录上一层目录
					nowCatalog.subMap.put( name, catalog );
					fat[ 0 ]--;
					totalFiles.put( catalog.getName(), catalog );
					System.out.println( "Directory is successfully created!" );
					showFile();
				} 
				else if(value.getAttr() == 3) {
					System.out.println( "Directory fails to create because the directory already exists!" );
					showFile();
				} 
			} 
			else if(value == null) {
				int startNum = setFat(1);
				fileModel catalog = new fileModel( name, startNum );
				catalog.setFather( nowCatalog ); //纪录上一层目录
				nowCatalog.subMap.put( name, catalog );
				fat[ 0 ]--;
				totalFiles.put( catalog.getName(), catalog );
				System.out.println( "Directory is successfully created!" );
				showFile();
			}			
		} 
		else {
			System.out.println("Directory fails to create because insufficient disk space！");
		}
	}
	
	
	/*
	 * 
	 * 以下为显示该目录下的所有文件信息
	 * 
	 */
	public void showFile() {
		System.out.println("***************** < " + nowCatalog.getName() + " > *****************");
       
		if( !nowCatalog.subMap.isEmpty() ) {
			for( fileModel value : nowCatalog.subMap.values() ) {
				if(value.getAttr() == 3) { //目录文件
					System.out.println("File Name:" + value.getName());
					System.out.println("Operation Type:" + "Folder");
					System.out.println("Starting Disk Blocks:" + value.getStartNum());
					System.out.println("Size: " + value.getSize());
					System.out.println("<-------------------------------------->");
				}
				else if(value.getAttr() == 2) {
					System.out.println("File Name:" + value.getName() + "." + value.getType());
					System.out.println("Operation Type: " + "Readable & Writable File");
					System.out.println("Starting Disk Blocks:" + value.getStartNum());
					System.out.println("Size:" + value.getSize());
					System.out.println("<-------------------------------------->");
				}
			}
		}
		for(int i =0; i<2; i++)	
		System.out.println();
		System.out.println("Disk Surplus Space:" + fat[ 0 ] + "            " + "Exit the system please enter:exit");
		System.out.println();
	}

	/*
	 * 
	 * 以下为删除该目录下某个文件
	 * 
	 */
	public void deleteFile(String name) {
		
		fileModel value = nowCatalog.subMap.get( name );
		if( value == null ) {
			System.out.println("Delete failed, No File or Folder!!");
		} 
		else if( !value.subMap.isEmpty() ){
			System.out.println("Delete failed because the folder contains files!");
		} 
		else {
			nowCatalog.subMap.remove(name);
			deleteFAT(value.getStartNum());
			if(value.getAttr() == 3) {
				System.out.println("Folder " + value.getName() + " Have been successfully deleted");
				showFile();
			} 
			else if(value.getAttr() == 2) {
				System.out.println("File " + value.getName() + "Have been successfully deleted");
				showFile();
			}
		}
	}
	
	/*
	 * 
	 * 以下为文件或文件夹重命名方法
	 * 
	 */
	public void reName(String name, String newName) {
		if( nowCatalog.subMap.containsKey(name) ) {
			if( nowCatalog.subMap.containsKey( newName ) ) {
				System.out.println("Rename failed because the same name file already exists!");	
				showFile();
			} 
			else {
				fileModel value = nowCatalog.subMap.get( name );
				value.setName( newName );
				nowCatalog.subMap.remove( name );
				nowCatalog.subMap.put( newName, value );
				System.out.println( "Rename has succeed" );
				System.out.println();
				showFile();
			}
		} 
		else {
			System.out.println("Rename failed because there is no this file");
			showFile();
		}
	}
	
	/*
	 * 
	 * 以下为修改文件类型
	 * 修改类型需要打开文件后才能操作
	 */
	public void changeType( String name, String type ) {
		
		nowCatalog = nowCatalog.getFather();
		if( nowCatalog.subMap.containsKey( name ) ) {
			fileModel value = nowCatalog.subMap.get( name );
			if(value.getAttr() == 2){
				value.setType(type);
				nowCatalog.subMap.remove(name);
				nowCatalog.subMap.put(name, value);
				System.out.println("Modify type success!");
				showFile();
			} 
			else if(value.getAttr() == 3) {
				System.out.println("Change error because the folder can not modify type!！");
				openFile( value.getName() );
			}
		} 
		else {
			System.out.println("Modify error, please check whether the input file name is correct！");
		}
	}
	
	/*
	 * 以下为打开文件或文件夹方法
	 * 
	 */
	public void openFile( String name ) {
		if( nowCatalog.subMap.containsKey( name ) ) {
			fileModel value = nowCatalog.subMap.get(name);
			if(value.getAttr() == 2) {
				nowCatalog = value;
				System.out.println("The file has been opened and the file size is: " + value.getSize() );				
			}
			else if(value.getAttr() == 3) {
				nowCatalog = value;
				System.out.println("The file has been opened!");
				showFile();
			}
		} 
		else{
			System.out.println("Open failed because the file does not exist!");
		}
	}
	

	/*
	 * 
	 * 以下为向文件追加内容方法
	 * 追加内容需要打开文件后才能操作
	 */
	public void reAdd(String name, int addSize){
		if( fat[0] >= addSize ) {
			nowCatalog = nowCatalog.getFather();
			if(nowCatalog.subMap.containsKey(name)) {
				fileModel value = nowCatalog.subMap.get(name);
				if(value.getAttr() == 2) {
					value.setSize(value.getSize() + addSize);
					AddFAT(value.getStartNum(), addSize);
					System.out.println("Addition content is successful! The file is being reopened...");
					openFile(name);
				} 
				else{
					System.out.println("The appended content failed, please verify that the filename is entered correctly.");					
				}
			}
		}
		else{
			System.out.println("Addition content is failed because insufficient memory space");
			}
		}
	
	
	/*
	 * 
	 * 以下为返回上一层目录
	 * 
	 */
	public void backFile() {
		if(nowCatalog.getFather() == null) {
			System.out.println("The document does not have a superior directory!");
		} else {
			nowCatalog = nowCatalog.getFather();
			showFile();
		}
	}
	
	/*
	 * 以下根据绝对路径寻找文件
	 * 
	 */
	public void searchFile(String[] roadName) {
		
		fileModel theCatalog = nowCatalog; //设置断点纪录当前目录
		
		if( totalFiles.containsKey(roadName[roadName.length-1]) ) { //检查所有文件中有无该文件
			nowCatalog = root; //返回根目录
			if( nowCatalog.getName().equals( roadName[0]) ) {	//判断输入路径的首目录是否root
				System.out.println("yes");
				for( int i = 1; i < roadName.length; i++ ) {
					if( nowCatalog.subMap.containsKey( roadName[ i ] ) ) {
						nowCatalog = nowCatalog.subMap.get( roadName[ i ] ); //一级一级往下查

					} 
					else {
						System.out.println("Can't find the file or directory under this path, please check whether the path is correct!");
						nowCatalog = theCatalog;
						showFile();
						break;
					}
				}
				if( roadName.length > 1 ){
					nowCatalog = nowCatalog.getFather(); //返回文件上一级目录 
					showFile();
				}
			} 
			else{
				nowCatalog = theCatalog;
				System.out.println("Please enter the correct absolute path！");
				showFile();
			}
		} 
		else{
			System.out.println("This file or directory does not exist, please enter the correct absolute path！");
			showFile();
		}
	}
	
	/*
	 * 以下为打印FAT表内容
	 * 
	 */
	public void showFAT() {

		for(int j=0; j<125; j+=5) {
			System.out.println("第几项 | " + j + "        " + (j+1) + "        " + (j+2) + "        "
					+ (j+3) + "        " + (j+4));
			System.out.println("内容 | " + fat[j] + "        " + fat[j+1] + "        " + fat[j+2]
					 + "        " + fat[j+3] + "        " + fat[j+4]);
			System.out.println();
		}
		int j = 125;
		System.out.println("第几项 | " + j + "        " + (j+1) + "        " + (j+2));
		System.out.println("内容 | " + fat[j] + "        " + fat[j+1] + "        " + fat[j+2]);
		System.out.println();
		showFile();
	}
}
