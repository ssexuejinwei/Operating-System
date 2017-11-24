package pagedStorageManagement;

public class KShell 
{
	page shell[];
	private int current;
	private int length;
	private int changeNumber;//修改快表的次数
	public KShell(){};
	public KShell( int length )
	{
		this.length = length;
		this.current = 0;
		this.changeNumber = 0;
		shell = new page[length];
		for (int i=0; i<length; i++) 
		{
			this.shell[i] = new page();
		}
	}
	public void setCurrent( int current )
	{
		this.current = current;
	}
	public void setChangeNumber( int changeNumber )
	{
		this.changeNumber = changeNumber;
	}
	public int getCurrent()
	{
		return current;
	}
	public int getChangeNumber()
	{
		return changeNumber;
	}
	public int getLength()
	{
		return length;
	}
	public int searchPage( int pageNumber )
	{
		int i = 0;
		if( this.changeNumber == 0 && this.current == 0 )
		{
			return -2;
		}
		else if( this.changeNumber < this.length )
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
		else
		{
			while( i < this.length )
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
	public void changeKShell( shell pageShell, int number )
	{
		if( this.getChangeNumber() >= this.getLength() )
		{
			if ( this.getCurrent() == this.getLength() ) 
			{
				this.setCurrent(0);
			}
			System.out.println("快表已满，快表中即将调出页号" + this.shell[ this.current ].getPageNumber() );
		}
		if ( this.getCurrent() < this.getLength() ) 
		{
			this.shell[this.getCurrent()].setCRTAddress(number);
			this.shell[this.getCurrent()].setPageNumber(pageShell.shell[number].getPageNumber());
			this.shell[this.getCurrent()].setPhysicsNumber(pageShell.shell[number].getPhysicsNumber());
			this.setCurrent( this.getCurrent()+1 );
			this.setChangeNumber( this.getChangeNumber() + 1 );
		}
	}
	public void printKShell()
	{
		System.out.println("快表：");
		System.out.println("索引\t"+"页号\t"+"物理块号\t"+"在页表下的索引");
		for( int i=0; i<this.length; i++ )
		{
			System.out.println( i + "\t" + this.shell[i].getPageNumber() + "\t" + this.shell[i] .getPhysicsNumber() + "\t" + this.shell[i].getCRTAddress() );
		}
	}
}