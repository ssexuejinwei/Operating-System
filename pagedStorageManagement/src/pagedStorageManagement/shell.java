package pagedStorageManagement;
import java.util.Scanner;

public class shell {
	page shell[];
	private int current;
	private int length;
	public shell(){};
	public shell( int length )
	{
		this.length = length;
		this.current = 0;
		shell = new page[length];
		for( int i = 0; i < length ; i++ )
		{
			this.shell[i] = new page();
		}
	}
	public void setCurrent( int current )
	{
		this.current = current;
	}
	public int getCurrent()
	{
		return this.current;
	}
	public int searchPage( int pageNumber )
	{
		int i = 0;
		if( this.current == 0 )
		{
			return -2;
		}
		else
		{
			while( i < this.current )
			{
				if( this.shell[i].getPageNumber() == pageNumber )
				{
					return i;
				}
				i++;
			}
			return -1;
		}
	}
	public void inChange( int b[], String ch, int number )
	{
		Scanner a = new Scanner( System.in );
		switch( ch )
		{
			case "yes":
			{
				System.out.println("请输入一个新的数据");
				b[this.shell[number].getPhysicsNumber() ] = a.nextInt();
				this.shell[number].setChange( true );
				System.out.println("修改成功");
				break;
			}
			case "no":
			{
				break;
			}
			default:
			{
				System.out.println("输入字符有误，将退出程序！");
				System.exit(0);
			}
		}
	}
	public int isOver()
	{
		if( this.current >= this.length )
		{
			return 1;
		}
		else
			return 0;
	}
	public int minVisitCount()
	{
		int i,t=0;
		for (i=1; i<this.current; i++) 
		{
			if( this.shell[i].getVisitCount() < this.shell[t].getVisitCount() )
			{
				t = i;
			}
		}
		return t;
	}
	public int isChange( int number )
	{
		if( this.shell[number].getChange() == true )
		{
			return 1;
		}
		else
			return 0;
	}
	public void printPageShell()
	{
		System.out.println("页表:");
		System.out.println("索引\t"+"页号\t"+"物理块号\t"+"状态\t"+"访问次数\t"+"修改\t"+"外存地址\t");
		for( int i=0; i<this.length; i++ )
		{
			System.out.println(i+"\t"+this.shell[i].getPageNumber()+"\t"+this.shell[i] .getPhysicsNumber()+"\t"+this.shell[i].getState()+"\t"+this.shell[i].getVisitCount()+"\t"+this.shell[i].getChange()+"\t"+this.shell[i].getCRTAddress());
		}
	}
	public void programFunction()
	{
		System.out.println("***********************请求分页存储系统***********************");
		System.out.println("功能:");
		System.out.println("\t 1.查看页表");
		System.out.println("\t 2.查看快表");
		System.out.println("\t 3.查看外存");
		System.out.println("\t 4.在内存修改数据");
		System.out.println("\t 5.继续访问页面");
		System.out.println("\t 6.退出程序");
	}
	public void dealFunction( int i, KShell TLB, source s[], int b[] )
	{
		if (i == 1 )
		{
			this.printPageShell();
		} 
		else if ( i == 2) 
		{
			TLB.printKShell();
		}
		else if ( i ==3 ) 
		{
			System.out.println("外存：");
			System.out.println("外存地址\t"+"页号\t"+"数据\n");
			for (int k=0; k<20; k++) 
			{
				s[k].printSource(k);
			}
		}
		else if ( i == 4) 
		{
			String ch = "yes";
			int pageNumber;
			Scanner a = new Scanner( System.in );
			System.out.println("请输入一个页号:");
			pageNumber = a.nextInt();
			int number = this.searchPage( pageNumber );
			if( number <0 )
			{
				System.out.println("内存中没有此页号");
			}
			else
			{
				this.inChange( b,ch,number );
			}
		}
		else if ( i == 6) 
		{
			System.out.println("结束程序");
			System.exit(0);
		}
	}
	public static void main( String[] args )
	{
		Scanner a = new Scanner( System.in );
		int i,number = -10,k1,k2,result;
		int k3 = 0;//当前存储的内存地址
		int t;//页表中访问次数最小的索引
		int b[] = new int[10];//内存中存储的数据
		String ch;
		int sLength,pLength,tLength,data;
		System.out.println("请输入外存大小：");
		sLength = a.nextInt();
		System.out.println("请输入页表大小：");
		pLength = a.nextInt();
		System.out.print("请输入快表大小:"); 
		tLength = a.nextInt();
		//定义页表,快表，外存
		shell pageShell = new shell( pLength );//页表
		source s[] = new source[sLength];//外表
		KShell TLB = new KShell( tLength );//快表
		System.out.println("产生一个随机序列作为外存数据！");
		//录入外存地址和数据
		for( i = 0; i < sLength; i++ )
			{ 
				data = (int)( 100 * Math.random() ); 
				System.out.print( data + "\t"); 
				s[i] = new source( i, data );
			}
			System.out.println("\n外存设置成功");
			//请求页表
			do
			{
				//TLB.printKShell();//打印当前快表的情况
				//pageShell.printPageShell();//打印当前页表的情况
				System.out.println("请输入一个页面的页号(0-19):");
				k1 = a.nextInt();
				if( k1 >= 20 || k1 < 0 )
				{
					System.out.println("输入数据有错，将退出程序!");
					System.exit(0);
				}
				//检测快表,快表存储当前的页表项，即当快表满时采用最近最久未被使用算法置换快表
				System.out.println("进入快表检测");
				if( TLB.getCurrent() > 0 )
				{
					number = TLB.searchPage(k1);
					if( number!=-1 && number!=-2 )
					{
						result = b[TLB.shell[number].getPhysicsNumber()];
						System.out.println("在快表中找到，结果为：" + result );
						//找出该页号在页表中的位置并修改访问字段
						number = TLB.shell[number].getCRTAddress();
						pageShell.shell[number].setVisitCount(pageShell.shell[number].getVisitCount()+1 );
					}
				}
				if( TLB.getCurrent() <= 0 || number == -1 )
				{
					System.out.println("在快表中找不到！" + "进入内存检测：");
					//在快表中找不到,去内存区的页表找
					if( pageShell.current>0 )
					{
						number = pageShell.searchPage(k1);//页号k1所在的下标
						if( number !=-1 && number!=-2 )
						{
							result = b[pageShell.shell[number].getPhysicsNumber()];
							System.out.println("在页表中找到,结果为:" + result );
							//修改访问字段和状态位
							pageShell.shell[number].setVisitCount(pageShell.shell[number].getVisitCount()+1);
							//修改快表
							TLB.changeKShell( pageShell,number );

						}
					}
					if( pageShell.current <= 0 || number== -1 )
					{
						System.out.println("在内存中找不到！");
						System.out.println("从外存中调入内存：");
						//在页表找不到，去外存区找
						for( i=0; i<sLength ; i++ )
						{
							if( k1 ==s[i].getPageNumber() )//在外存找到了缺页
							{
								k2 = pageShell.isOver();
								if( k2 == 1 )//内存已满
								{
									t = pageShell.minVisitCount();
									System.out.println("内存已满!即将调出页号 " + pageShell.shell[t].getPageNumber());
								}
								else
								{
									t = pageShell.current;
									pageShell.setCurrent( pageShell.getCurrent() + 1 );
								}
								//判断是否修改了内存的数据
								if( pageShell.isChange(t) == 1 )
								{
									s[pageShell.shell[t].getCRTAddress()].setSts(b[pageShell.shell[t].getPhysicsNumber()]);
								}
								//调入内存
								pageShell.shell[t].setPageNumber(k1);
								if( k2 == 1 )
								{
									b[pageShell.shell[t].getPhysicsNumber()] = s[i].getSts();
								}
								else
								{
									pageShell.shell[t].setPhysicsNumber(k3);//未满则设置物理块号,满了只改变其他5个字段
									b[k3] = s[i].getSts();
									k3++;//物理块号
								}
								pageShell.shell[t].setState(true);
								pageShell.shell[t].setVisitCount(1);
								pageShell.shell[t].setChange(false);
								pageShell.shell[t].setCRTAddress(i);
								System.out.println("调入内存成功！");
								//修改快表
								TLB.changeKShell(pageShell,t);
								System.out.println("修改快表成功！");
								System.out.println("结果为：" + b[ k3-1 ]);
								break;
							}
						}
					}
				}
				do
				{
					pageShell.programFunction();
					System.out.println("请输入一个整数(1-6)：");
					i = a.nextInt();
					while( i<1 || i>6 )
					{						
						System.out.println("输入有误，请重新输入（1-6）：");
						i = a.nextInt();
					}
					pageShell.dealFunction( i,TLB,s,b );
				}while( i != 5 );
			}while(i==5);
			System.out.println("退出程序!");
	}
}
