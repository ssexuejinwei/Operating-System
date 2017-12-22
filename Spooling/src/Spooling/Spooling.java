package Spooling;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Spooling extends JFrame implements ActionListener{
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JTextField field1;
	JTextField field2;
	JScrollPane p1;
	JScrollPane p2;
	JScrollPane p3;
	JScrollPane p4;
	JTextArea textArea1;
	JTextArea textArea2;
	JTextArea textArea3;
	JTextArea textArea4;
	JButton button;
	Manage manage;

	public Spooling(){
	//界面构造函数
		manage = new Manage( this );
		Container container = this.getContentPane();
		container.setLayout( new BorderLayout() );

		field1 = new JTextField( 3 );//设置panel1
		field2 = new JTextField( 3 );

		button = new JButton( "运行" );
		button.addActionListener( this );

		panel1 = new JPanel();
		panel1.setLayout( new FlowLayout() );

		panel1.add( new JLabel( "用户进程1文件数：", SwingConstants.RIGHT ) );
		panel1.add( field1 );
		panel1.add( new JLabel( "用户进程2文件数：", SwingConstants.RIGHT) );
		panel1.add( field2 );
		panel1.add( button );
		//设置panel1完毕

		textArea1 = new JTextArea( 80,100 );//设置panel2
		textArea2 = new JTextArea( 112,400 );

		textArea1.append( "用户进程1的文件生成情况及状态变换\n文件序号	位    置	剩余空间	状态\n" );
		textArea2.append("用户进程2的文件生成情况及状态变换\n文件序号	位    置	剩余空间	状态\n");

		p1 = new JScrollPane( textArea1 );
		p2 = new JScrollPane( textArea2 );

		panel2 = new JPanel();
		panel2.setLayout ( new GridLayout( 1,2 ) );
		panel2.add( p1 );
		panel2.add( p2 );
		//设置panel2完毕

		textArea3 = new JTextArea( 15,400 );//设置panel3
		textArea4 = new JTextArea( 15,150 );
		textArea3.append( "打印输出\n" );
		textArea4.append("主程序调度\n");

		p3 = new JScrollPane( textArea3 );
		p4 = new JScrollPane( textArea4 );

		panel3 = new JPanel();
		panel3.setLayout( new GridLayout( 1,2 ) );
		panel3.add( p3 );
		panel3.add( p4 );

		container.add( panel1, BorderLayout.NORTH );//设置窗口
		container.add( panel2 ,BorderLayout.CENTER );
		container.add( panel3, BorderLayout.SOUTH );

		this.setSize( 1200,600 );
		this.setLocation( 100,100 );
		this.setTitle( "Spooling" );
		this.setVisible( true );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );//设置窗口完毕
	}

	public void actionPerformed( ActionEvent e ){
		manage.start();
	}

	public static void main( String args[] ){
		Spooling spooling = new Spooling();
	}
}