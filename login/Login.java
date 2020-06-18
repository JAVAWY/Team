import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import javax.swing.*;


import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Login extends JFrame {
public static String namew;
public static String user;
public static  String user1;
public static String password;
int aa=0;
	private static final long serialVersionUID=1L;
	public   SqlLogin.jdbc connect=new SqlLogin().new jdbc();
       java.sql.Connection con=null;
  JLabel a=new JLabel("用户名:");
  JLabel cuowu=new JLabel("用户名或者密码不对，请重新输入");//错误提示标签
  JLabel b=new JLabel("密码：");
  public JRadioButton r1,r2,r3;
  ImageIcon bj = new ImageIcon("F:/课程设计/Login_bg.jpg");
  JLabel label = new JLabel(bj);
  
  JButton c=new JButton("登录");
  JButton c1=new JButton("取消");
 
private boolean flag;
  static JTextField d=new JTextField();
  static JPasswordField f=new JPasswordField();
   Login(String sTitle){
	   super(sTitle);
	   this.setLayout(null);
	   this.add(cuowu);   //添加控件
	   this.add(a);
	   this.add(b);
	   this.add(c);
	   this.add(c1);
	  
	   this.add(d);
	   this.add(f);
	  
	   
	   
	   
	   final JRadioButton r1 = new JRadioButton("管理员");
		final JRadioButton r2 = new JRadioButton("收费员");
		final JRadioButton r3 = new JRadioButton("医生");
		ButtonGroup rg=new ButtonGroup();
		this.add(r2);
		rg.add(r1);
		this.add(r3);
		rg.add(r3);
		this.add(r1);
			rg.add(r2);
			r1.setBounds(150, 180, 80, 30);
			r2.setBounds(230, 180, 80, 30);
			r3.setBounds(310, 180, 80, 30);
			r1.setFocusPainted(false);r2.setFocusPainted(false);
			r3.setFocusPainted(false);r3.setContentAreaFilled(false);
			r1.setContentAreaFilled(false);r2.setContentAreaFilled(false);
	   cuowu.setBounds(100, 130, 200, 50);
	   cuowu.setForeground(Color.black);
	   cuowu.setVisible(false);
	   label.setBounds(0, 0, 592 ,350);
	   c.addActionListener(new ActionListener(){
		   public boolean flag=false;
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				
				
				
				if(r3.isSelected()){
					try {
						String name=d.getText().toString();    //获取帐号文本框内容
				        String x=f.getText().toString(); //获取密码文本框内容
				        SqlLogin.jdbc connect=new SqlLogin().new jdbc();
						con=connect.getConnection();   //创建数据库对象
	         		Statement stmt =con.createStatement();
						ResultSet rs=stmt.executeQuery("select * from Doctor");  //执行SQL语句，返回结果集	
							while(rs.next()){
								
								user=rs.getString("DrId");  //获取登录的用户编号，
							 	user1=rs.getString("DrName");//获取登录的用户姓名
		                            password=rs.getString("Password");     //获取数据库中的数据项的密码 
				                if(user.equals(name)&&password.equals(x)){//判断数据库的用户编号以及密码是否与文本框的值相同
				                	aa=1;
				                    break;
				                } 
				               
				               	                
				            }
							if(aa==1){
								JOptionPane.showMessageDialog(null, "登录成功");
								MainPanel11 a=  	new MainPanel11("1111");   //显示系统主界面
                               a.setVisible(true);
								Login.this.setVisible(false);// 关闭登录按钮
							}
							else{
								d.setText("");  //错误的话则文本框内容设置为空，显示错误标签
			                     f.setText("");
			                    
								 JOptionPane.showMessageDialog(null, "登陆错误");
							}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						System.out.println(e2);
					}
				}
			
				else if(r1.isSelected()) {
					
					try{
					String name=d.getText().toString();    //获取帐号文本框内容
			        String x=f.getText().toString(); //获取密码文本框内容
			        SqlLogin.jdbc connect=new SqlLogin().new jdbc();
					con=connect.getConnection();   //创建数据库对象
         		Statement stmt =con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from Manager");  //执行SQL语句，返回结果集	
						while(rs.next()){
							
							user=rs.getString("ManagerID");  //获取登录的用户编号，
						 	user1=rs.getString("ManagerName");//获取登录的用户姓名
	                            password=rs.getString("MaPassWord");     //获取数据库中的数据项的密码 
			                if(user.equals(name)&&password.equals(x)){//判断数据库的用户编号以及密码是否与文本框的值相同
			                	
			                	aa=1;
			                
			                    break;
			                } 
			               
			               	                
			            }
						if(aa==1){
							JOptionPane.showMessageDialog(null, "登录成功");
		                	new MainPanel("1111统");   //显示系统主界面
                            Login.this.setVisible(false);// 关闭登录按钮
						}
						else{
							d.setText("");  //错误的话则文本框内容设置为空，显示错误标签
		                     f.setText("");
		                    
							 JOptionPane.showMessageDialog(null, "登陆错误");
						}
				} catch(SQLException e3){
					// TODO Auto-generated catch block
					System.out.println(e3);
				}
					
			}

				else if(r2.isSelected()) {
					
					try{
					String name=d.getText().toString();    //获取帐号文本框内容
			        String x=f.getText().toString(); //获取密码文本框内容
			        SqlLogin.jdbc connect=new SqlLogin().new jdbc();
					con=connect.getConnection();   //创建数据库对象
         		Statement stmt =con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from Cashier");  //执行SQL语句，返回结果集	
						while(rs.next()){
							
							user=rs.getString("cashierId");  //获取登录的用户编号，
						 	user1=rs.getString("cashierName");//获取登录的用户姓名
	                            password=rs.getString("cashierPassWord");     //获取数据库中的数据项的密码 
			                if(user.equals(name)&&password.equals(x)){//判断数据库的用户编号以及密码是否与文本框的值相同
			                	
			                	aa=1;
			                
			                    break;
			                } 
			               
			               	                
			            }
						if(aa==1){
							JOptionPane.showMessageDialog(null, "登录成功");
		                	new MainPanel2("555555");   //显示系统主界面
                            Login.this.setVisible(false);// 关闭登录按钮
						}
						else{
							d.setText("");  //错误的话则文本框内容设置为空，显示错误标签
		                     f.setText("");
		                    
							 JOptionPane.showMessageDialog(null, "登陆错误");
						}
				} catch(SQLException e3){
					// TODO Auto-generated catch block
					System.out.println(e3);
				}
					
			}
				else if(r1.isSelected()==false&&r2.isSelected()==false){
					 JOptionPane.showMessageDialog(null, "请选择用户类型");

				}
	     	
			}
	   }); 
	    //登录按钮添加功能事件
	   
	   a.setBounds(150, 50, 100, 50);
	   a.setFont(new Font("",1,20));
	  
	   b.setBounds(150, 120, 100, 50);
	  
	   b.setFont(new Font("",1,20));
	   c.setBounds(150, 220, 100, 40);
	   c.setBackground(Color.CYAN);
	   c1.setBounds(280, 220, 100, 40);
	   c1.setBackground(Color.CYAN);
	 
	   d.setBounds(250, 60, 150, 30);
	   f.setBounds(250, 130, 150, 30);

	   this.add(label);
	   this.setSize(600,350);   //设置窗口大小
		this.setResizable(false);    //设置不可调整窗口大小
       this.setLocationRelativeTo(null);    
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
   }


public static void main (String args[]){
       Login ts=new Login("医院管理系统");
	}


}
