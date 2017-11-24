package pagedStorageManagement;

public class source {
	private int pageNumber;//页号
	private int sts;//数据
	private	int length;

	public source(){};
	public source( int pageNumber , int sts )
	{
		this.pageNumber = pageNumber;
		this.sts = sts;
	}
	public void setPageNumber( int pageNumber )
	{
		this.pageNumber = pageNumber;
	}
	public void setSts( int sts )
	{
		this.sts = sts;
	}
	public int getPageNumber()
	{
		return pageNumber;
	}
		public int getSts()
	{
		return sts;
	}
	public void printSource( int i )
	{
		System.out.println( i + "\t" + this.pageNumber + "\t" + this.sts + "\n" );
	}

}
