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
  JLabel a=new JLabel("�û���:");
  JLabel cuowu=new JLabel("�û����������벻�ԣ�����������");//������ʾ��ǩ
  JLabel b=new JLabel("���룺");
  public JRadioButton r1,r2,r3;
  ImageIcon bj = new ImageIcon("F:/�γ����/Login_bg.jpg");
  JLabel label = new JLabel(bj);
  
  JButton c=new JButton("��¼");
  JButton c1=new JButton("ȡ��");
 
private boolean flag;
  static JTextField d=new JTextField();
  static JPasswordField f=new JPasswordField();
   Login(String sTitle){
	   super(sTitle);
	   this.setLayout(null);
	   this.add(cuowu);   //��ӿؼ�
	   this.add(a);
	   this.add(b);
	   this.add(c);
	   this.add(c1);
	  
	   this.add(d);
	   this.add(f);
	  
	   
	   
	   
	   final JRadioButton r1 = new JRadioButton("����Ա");
		final JRadioButton r2 = new JRadioButton("�շ�Ա");
		final JRadioButton r3 = new JRadioButton("ҽ��");
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
						String name=d.getText().toString();    //��ȡ�ʺ��ı�������
				        String x=f.getText().toString(); //��ȡ�����ı�������
				        SqlLogin.jdbc connect=new SqlLogin().new jdbc();
						con=connect.getConnection();   //�������ݿ����
	         		Statement stmt =con.createStatement();
						ResultSet rs=stmt.executeQuery("select * from Doctor");  //ִ��SQL��䣬���ؽ����	
							while(rs.next()){
								
								user=rs.getString("DrId");  //��ȡ��¼���û���ţ�
							 	user1=rs.getString("DrName");//��ȡ��¼���û�����
		                            password=rs.getString("Password");     //��ȡ���ݿ��е������������ 
				                if(user.equals(name)&&password.equals(x)){//�ж����ݿ���û�����Լ������Ƿ����ı����ֵ��ͬ
				                	aa=1;
				                    break;
				                } 
				               
				               	                
				            }
							if(aa==1){
								JOptionPane.showMessageDialog(null, "��¼�ɹ�");
								MainPanel11 a=  	new MainPanel11("1111");   //��ʾϵͳ������
                               a.setVisible(true);
								Login.this.setVisible(false);// �رյ�¼��ť
							}
							else{
								d.setText("");  //����Ļ����ı�����������Ϊ�գ���ʾ�����ǩ
			                     f.setText("");
			                    
								 JOptionPane.showMessageDialog(null, "��½����");
							}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						System.out.println(e2);
					}
				}
			
				else if(r1.isSelected()) {
					
					try{
					String name=d.getText().toString();    //��ȡ�ʺ��ı�������
			        String x=f.getText().toString(); //��ȡ�����ı�������
			        SqlLogin.jdbc connect=new SqlLogin().new jdbc();
					con=connect.getConnection();   //�������ݿ����
         		Statement stmt =con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from Manager");  //ִ��SQL��䣬���ؽ����	
						while(rs.next()){
							
							user=rs.getString("ManagerID");  //��ȡ��¼���û���ţ�
						 	user1=rs.getString("ManagerName");//��ȡ��¼���û�����
	                            password=rs.getString("MaPassWord");     //��ȡ���ݿ��е������������ 
			                if(user.equals(name)&&password.equals(x)){//�ж����ݿ���û�����Լ������Ƿ����ı����ֵ��ͬ
			                	
			                	aa=1;
			                
			                    break;
			                } 
			               
			               	                
			            }
						if(aa==1){
							JOptionPane.showMessageDialog(null, "��¼�ɹ�");
		                	new MainPanel("1111ͳ");   //��ʾϵͳ������
                            Login.this.setVisible(false);// �رյ�¼��ť
						}
						else{
							d.setText("");  //����Ļ����ı�����������Ϊ�գ���ʾ�����ǩ
		                     f.setText("");
		                    
							 JOptionPane.showMessageDialog(null, "��½����");
						}
				} catch(SQLException e3){
					// TODO Auto-generated catch block
					System.out.println(e3);
				}
					
			}

				else if(r2.isSelected()) {
					
					try{
					String name=d.getText().toString();    //��ȡ�ʺ��ı�������
			        String x=f.getText().toString(); //��ȡ�����ı�������
			        SqlLogin.jdbc connect=new SqlLogin().new jdbc();
					con=connect.getConnection();   //�������ݿ����
         		Statement stmt =con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from Cashier");  //ִ��SQL��䣬���ؽ����	
						while(rs.next()){
							
							user=rs.getString("cashierId");  //��ȡ��¼���û���ţ�
						 	user1=rs.getString("cashierName");//��ȡ��¼���û�����
	                            password=rs.getString("cashierPassWord");     //��ȡ���ݿ��е������������ 
			                if(user.equals(name)&&password.equals(x)){//�ж����ݿ���û�����Լ������Ƿ����ı����ֵ��ͬ
			                	
			                	aa=1;
			                
			                    break;
			                } 
			               
			               	                
			            }
						if(aa==1){
							JOptionPane.showMessageDialog(null, "��¼�ɹ�");
		                	new MainPanel2("555555");   //��ʾϵͳ������
                            Login.this.setVisible(false);// �رյ�¼��ť
						}
						else{
							d.setText("");  //����Ļ����ı�����������Ϊ�գ���ʾ�����ǩ
		                     f.setText("");
		                    
							 JOptionPane.showMessageDialog(null, "��½����");
						}
				} catch(SQLException e3){
					// TODO Auto-generated catch block
					System.out.println(e3);
				}
					
			}
				else if(r1.isSelected()==false&&r2.isSelected()==false){
					 JOptionPane.showMessageDialog(null, "��ѡ���û�����");

				}
	     	
			}
	   }); 
	    //��¼��ť��ӹ����¼�
	   
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
	   this.setSize(600,350);   //���ô��ڴ�С
		this.setResizable(false);    //���ò��ɵ������ڴ�С
       this.setLocationRelativeTo(null);    
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
   }


public static void main (String args[]){
       Login ts=new Login("ҽԺ����ϵͳ");
	}


}
