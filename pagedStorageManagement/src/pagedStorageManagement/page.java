package pagedStorageManagement;

public class page {
	private int pageNumber;//页号
	private int physicsNumber;//物理页号
	private boolean state;//状态位
	private int visitCount;//访问字段
	private boolean change;//修改位
	private int CRTAddress;//外存地址

	public page()
	{
		this.pageNumber = -1;
		this.physicsNumber = -1;
		this.state = false;
		this.visitCount = 0;
		this.change = false;
		this.CRTAddress = -1;
	}
	public page( int pageNumber, int physicsNumber, boolean state, int visitCount, boolean change, int CRTAddress)
	{
		this.pageNumber = pageNumber;
		this.physicsNumber = physicsNumber;
		this.state = state;
		this.visitCount = visitCount;
		this.change = change;
		this.CRTAddress = CRTAddress;
	}
	public void setPageNumber( int pageNumber )
	{
		this.pageNumber = pageNumber;
	}
	public void setPhysicsNumber( int physicsNumber )
	{
		this.physicsNumber = physicsNumber;
	}
	public void setState (boolean state )
	{
		this.state=state;
	}
	public void setVisitCount( int visitCount )
	{
		this.visitCount = visitCount;
	}
	public void setChange( boolean change )
	{
		this.change = change;
	}
	public void setCRTAddress( int CRTAddress )
	{
		this.CRTAddress = CRTAddress;
	}
	public int getPageNumber( )
	{
		return this.pageNumber;
	}
	public int getPhysicsNumber()
	{
		return this.physicsNumber;
	}
	public boolean getState()
	{
		return this.state;
	} 
	public int getVisitCount()
	{
		return this.visitCount;
	}
	public boolean getChange()
	{
		return this.change;
	}
	public int getCRTAddress()
	{
		return this.CRTAddress;
	}

}
