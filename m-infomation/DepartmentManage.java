import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



public class DepartmentManage extends JFrame implements ActionListener{
	
	JPanel departmentManage=new JPanel();
	private JLabel manageTitle=new JLabel("科室信息综合操作");
	private JLabel la1,la2,la3,la4,la5,la6;
	private JTextField tx1,tx2,tx3,tx4,tx5,tx6;
	public JButton save,query,modify,delete;
	private Font laFont=new Font("宋体",Font.BOLD,15);
	private JComboBox jcbb1;
	private String str1[]={"查询全部", "按科室编号查询", "按科室名称查询"};
	private final String []columnNames={"科室编号","科室名称","科室主任","科室电话"};
	private JScrollPane JScrollPane1=new JScrollPane();
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	private static JTable table;
    private static  DefaultTableModel dtm;
    private Pattern pattern=Pattern.compile("[0-9]*"); 
	
	public DepartmentManage(){
		//背景设置
		departmentManage.setLayout(null);
		ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg"); 
		JLabel label = new JLabel(background);
		
		//标题设置
		manageTitle.setFont(new Font("宋体",Font.BOLD,50));
		manageTitle.setBounds(60, 10, 1000, 50);
		departmentManage.add(manageTitle);
		
		//录入操作面板设置
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "录入操作", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red));
		panel.setBounds(45, 65, 550, 150);
		panel.setOpaque(false);
		
	    la1=new JLabel("科室编号：");
		la2=new JLabel("科室名称：");
		la3=new JLabel("科室主任：");
		la4=new JLabel("科室电话：");
		tx1=new JTextField();
		tx2=new JTextField();
		tx3=new JTextField();
		tx4=new JTextField();
		save=new JButton("保存");
		
		la1.setBounds(20, 20, 100, 50);
		la1.setFont(laFont);
		la2.setBounds(240, 20, 100, 50);
		la2.setFont(laFont);
		la3.setBounds(20, 80, 100, 50);
		la3.setFont(laFont);
		la4.setBounds(240, 80, 100, 50);
		la4.setFont(laFont);
		tx1.setBounds(100, 30, 120, 30);
		tx1.setFont(laFont);
		tx2.setBounds(320, 30, 120, 30);
		tx2.setFont(laFont);
		tx3.setBounds(100, 90, 120, 30);
		tx3.setFont(laFont);
		tx4.setBounds(320, 90, 120, 30);
		tx4.setFont(laFont);
		save.setBounds(460, 100, 80,40);
		
		panel.add(la1);
		panel.add(la2);
		panel.add(la3);
		panel.add(la4);
		panel.add(tx1);
		panel.add(tx2);
		panel.add(tx3);
		panel.add(tx4);
		panel.add(save);
		departmentManage.add(panel);
		
		//查询操纵面板设置
		final JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBorder(new TitledBorder(null, "查询操作", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red));
		panel1.setBounds(620, 65, 380, 150);
		panel1.setOpaque(false);
		
		query=new JButton("查询");
		la5=new JLabel("科室编号:");
		la6=new JLabel("科室名称:");
		tx5=new JTextField();
		tx6=new JTextField();
		jcbb1=new JComboBox(str1);
		jcbb1.setBounds(20, 28, 150, 25);
		jcbb1.setFont(laFont);
		la5.setBounds(20, 73, 80, 50);
		la5.setFont(laFont);
		la6.setBounds(20, 73, 80, 50);
		la6.setFont(laFont);
		tx5.setBounds(100, 80, 120, 30);
		tx5.setFont(laFont);
		tx6.setBounds(100, 80, 120, 30);
		tx6.setFont(laFont);
		query.setBounds(290, 100, 80, 40);
		
		la5.setVisible(false);
		la6.setVisible(false);
		tx5.setVisible(false);
		tx6.setVisible(false);
		
		panel1.add(la5);
		panel1.add(la6);
		panel1.add(tx5);
		panel1.add(tx6);
		panel1.add(jcbb1);
		panel1.add(query);
		departmentManage.add(panel1);
		
		//表格设置
		defaultTableModel();   //设置表格不可编辑
		setTableColumnCenter();   //设置表格内容居中显示
		setbgcolor();    //设置表格隔行不同颜色
		JScrollPane1.setBounds(new  Rectangle(45,230,850,270));
		JScrollPane1.setViewportView(table);   //创建一个滚动条（如果有必要）并设置其视图
		//table.getColumnModel().getColumn(0).setMinWidth(40);
		//table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getTableHeader().setReorderingAllowed(false);   //列不可拖动
		table.getTableHeader().setResizingAllowed(false);   //列宽不能改变
		departmentManage.add(JScrollPane1);  
		
		//按钮设置
		modify=new JButton("修改");
		delete=new JButton("删除");
		//reflash=new JButton("刷新");
		modify.setBounds(910, 230, 80, 40);
		delete.setBounds(910, 300, 80, 40);
		//reflash.setBounds(910, 370, 80, 40);
		departmentManage.add(modify);
		departmentManage.add(delete);
		//departmentManage.add(reflash);
		
		//添加监听器
		save.addActionListener(this);
		delete.addActionListener(this);
		query.addActionListener(this);
		modify.addActionListener(this);
		//reflash.addActionListener(this);
		jcbb1.addActionListener(this);
		
		//添加背景
		departmentManage.add(label);
		label.setBounds(0, 0, 1100, 700);
	}
	//设置表格不可编辑
	private void defaultTableModel(){
		dtm=new DefaultTableModel(columnNames,0);
		table=new JTable(dtm){
			  public boolean isCellEditable(int row, int column){
				  return false;
			  }
		  };
	}
	
	//设置表格内容居中显示
	private void setTableColumnCenter(){  
	    DefaultTableCellRenderer r = new DefaultTableCellRenderer();     
	    r.setHorizontalAlignment(JLabel.CENTER);     
	    table.setDefaultRenderer(Object.class, r);  
	}

	//设置表格隔行背景颜色不同
	private static void setbgcolor(){
		try{
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer(){
				public Component getTableCellRendererComponent(JTable table,
		                  Object value, boolean isSelected, boolean hasFocus,
		                  int row, int column){
					if(row%2 == 0)
						setBackground(new Color(223,220,239));   //设置奇数行底色
					else if(row%2 == 1)
						setBackground(Color.white);    //设置偶数行底色
					return super.getTableCellRendererComponent(table, value,
			                isSelected, hasFocus, row, column);
				}
			};
			for(int i = 0; i < table.getColumnCount(); i++){
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jcbb1){
			if(jcbb1.getSelectedIndex()==0){
				la5.setVisible(false);
				la6.setVisible(false);
				tx5.setVisible(false);
				tx6.setVisible(false);
			    return;
			}
			else if(jcbb1.getSelectedIndex()==1){
				la5.setVisible(true);
				tx5.setVisible(true);
				la6.setVisible(false);
				tx6.setVisible(false);
				return;
			}
			else if(jcbb1.getSelectedIndex()==2){
				tx5.setVisible(false);
				la5.setVisible(false);
				tx6.setVisible(true);
				la6.setVisible(true);
				return;
			}
		}
		
		if(e.getSource()==save){   //录入操作
			 //输入信息不能为空
			if(tx1.getText().equals("")||tx2.getText().equals("")||tx4.getText().equals("")){
				JOptionPane.showMessageDialog(null, "请输入完整的科室信息！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			//编号为3位数字
			else if(tx1.getText().length()>3||pattern.matcher(tx1.getText()).matches()==false){
				JOptionPane.showMessageDialog(null, "请输入正确的3位数科室编号!", "错误", JOptionPane.ERROR_MESSAGE);
			}
			//名字长度不能超过20个字符
			else if(tx2.getText().length()>10 || tx3.getText().length()>10){
				JOptionPane.showMessageDialog(null, "名字长度不能超过10个汉字！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			 //电话号码为8位数字
			else if(pattern.matcher(tx4.getText()).matches()==false||tx4.getText().length()!=8){  
				JOptionPane.showMessageDialog(null, "请输入正确的8位数电话号码！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
			else{
				String deptNo=tx1.getText().trim();
				String deptName=tx2.getText();
				String deptPhone=tx4.getText().trim();
				try{
					con=connect.getConnection();   //创建一个数据库连接
					Statement st1=con.createStatement();   //创建一个数据库会话对象
					Statement st2=con.createStatement();   //创建一个数据库会话对象
					Statement st3=con.createStatement();   //创建一个数据库会话对象
					ResultSet rs1=st1.executeQuery("select * from Department where DeptNo='"+deptNo+"'");   //SQL语句
					ResultSet rs2=st2.executeQuery("select * from Department where DeptName='"+deptName+"'");
					ResultSet rs3=st3.executeQuery("select * from Department where DeptPhone='"+deptPhone+"'");
					
					if(rs1.next()){   //判断结果集rs是否有记录，并且将指针后移一位
						JOptionPane.showMessageDialog(null, "该科室号已存在，请重新输入!", "错误", JOptionPane.ERROR_MESSAGE);
						st1.close();
					}
					else if(rs2.next()){   
						JOptionPane.showMessageDialog(null, "该科室名已存在，请重新输入!", "错误", JOptionPane.ERROR_MESSAGE);
						st2.close();
					}
					else if(rs3.next()){  
						JOptionPane.showMessageDialog(null, "该科室电话号码已存在，请重新输入!", "错误", JOptionPane.ERROR_MESSAGE);
						st3.close();
					}
					
					else{
						int ok=JOptionPane.showConfirmDialog(null, "是否保存该科室信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
						if(ok==JOptionPane.YES_OPTION){
							try{
								//信息添加到数据库
								String sql="INSERT INTO Department(DeptNo,DeptName,DrName,DeptPhone)VALUES(?,?,?,?)";
								PreparedStatement parepare=con.prepareStatement(sql);
								parepare.setString(1, tx1.getText());
	              				parepare.setString(2, tx2.getText());
	              				parepare.setString(3, tx3.getText());
	              				parepare.setString(4, tx4.getText());
	              				parepare.executeUpdate();
	              				
	              				String []data = new String[]{tx1.getText(),tx2.getText(),tx3.getText(),tx4.getText()};
	              				dtm.addRow(data);   //在表格添加一行刚添加的数据
	              				JOptionPane.showMessageDialog(null, "录入成功");
	              				tx1.setText("");
	              				tx2.setText("");
	              				tx3.setText("");
	              				tx4.setText("");
	              				
							}catch(Exception e1){
								e1.printStackTrace();
	              				System.out.println("SQL Exception occur.Message is:");
	                  			System.out.println(e1.getMessage());
							}
						}
					}
				}catch(Exception e2){
					e2.printStackTrace();
				}
			} 
		}
		
		if(e.getSource()==query){   //查询操作
			try{
				con=connect.getConnection();   //创建一个数据库连接
				if(jcbb1.getSelectedIndex()==0){   //全部查询
					String sql="select * from Department";
					databaseSearch(sql);
					if(table.getRowCount()!=0){
						JOptionPane.showMessageDialog(null, "查询成功！");
					}
					else{
						JOptionPane.showMessageDialog(null, "没有找到您要的信息！");
					}
				}
				if(jcbb1.getSelectedIndex()==1){   //编号查询
					if(tx5.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "请输入正确的科室编号！", "错误", JOptionPane.ERROR_MESSAGE);
					}
					else{
						String deptNo=tx5.getText().trim();
						String sql="select * from Department where DeptNo like'%"+deptNo+"%'";
						databaseSearch(sql);
						if(table.getRowCount()!=0){
							JOptionPane.showMessageDialog(null, "查询成功！");
							tx5.setText("");
						}
						else{
							JOptionPane.showMessageDialog(null, "没有找到您要的信息！");
						}
					}
				}
				if(jcbb1.getSelectedIndex()==2){   //名称查询
					if(tx6.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "请输入正确的科室名称！", "错误", JOptionPane.ERROR_MESSAGE);
					}
					else{
						String deptName=tx6.getText();
						String sql="select * from Department where DeptName like '%"+deptName+"%'";
						databaseSearch(sql);
						if(table.getRowCount()!=0){
							JOptionPane.showMessageDialog(null, "查询成功！");
							tx6.setText("");
						}
						else{
							JOptionPane.showMessageDialog(null, "没有找到您要的信息！");
						}
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		if(e.getSource()==delete){   //删除操作
			try{
				con=connect.getConnection();   //创建一个数据库连接
				Statement stmt=con.createStatement();   //创建一个数据库会话对象
				int selectCount=table.getSelectedRowCount();
				if(selectCount==0){
					JOptionPane.showMessageDialog(null, "请选择您要删除的信息！");
				}
				else if(selectCount==1){
					int ok=JOptionPane.showConfirmDialog(null, "是否删除该科室信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						int row=table.getSelectedRow();
						String deptNo=(String) table.getValueAt(row, 0);
						String sql="delete from Department where DeptNo='"+deptNo+"'";
						stmt.executeUpdate(sql);
						dtm.removeRow(row);
						JOptionPane.showMessageDialog(null, "删除成功！");
					}
				}
				else{
					int ok=JOptionPane.showConfirmDialog(null, "是否删除所选"+selectCount+"个科室信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						for(int i=1;i<=selectCount;i++){
							int row1=table.getSelectedRow();
							String deptNo=(String)table.getValueAt(row1, 0);
							String sql="delete from Department where DeptNo='"+deptNo+"'";
							stmt.executeUpdate(sql);
							dtm.removeRow(row1);
						}
						JOptionPane.showMessageDialog(null, "删除成功！");
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		//修改操作设置
		if(e.getSource()==modify){
			if(table.getSelectedRowCount()!=1){
				JOptionPane.showMessageDialog(null, "请选择一项科室信息进行修改！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			else{
				try{
					con=connect.getConnection();   //创建一个数据库连接
					Statement stmt1=con.createStatement();   //创建一个数据库会话对象
					
					int row=table.getSelectedRow();
					
					
					String value0=(String) table.getValueAt(row,0);
			    	String value1=(String) table.getValueAt(row,1);
			    	String value2=(String) table.getValueAt(row,2);
			    	String value3=(String) table.getValueAt(row,3);

					DepartmentModify dig=new DepartmentModify();
					dig.tx1.setText(value0);
					dig.tx2.setText(value1);
					dig.tx3.setText(value2);
					dig.tx4.setText(value3);
					
					dig.s.setVisible(true);	
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}
	
	//把数据库数据传入表格
	public void databaseSearch(String SQL){
		Connection con;
	
		con=connect.getConnection();
		ResultSet rs;
		try{
			int rowcount = dtm.getRowCount()-1;
			if (rowcount != -1){
				for (int i1 = rowcount; i1 >= 0; i1--){
					dtm.removeRow(i1);
				}
				dtm.setRowCount(0);
			}
			Statement stmt=con.createStatement();
			rs=stmt.executeQuery(SQL);
			String[] data = new String[4];
			while (rs.next()){
				for (int j = 1; j <= 4; j++){
					data[j - 1] = rs.getString(j);
				}
				dtm.addRow(data);
			}
			con.close();
		}catch(Exception err){
			String error=err.getMessage();
			JOptionPane.showMessageDialog(null, error);
		    err.printStackTrace();
		}finally{
			connect.closeConnection();
		}
	}
	
	//科室信息修改操作对话框设置++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class DepartmentModify implements ActionListener{
		
		public JDialog s=new JDialog();
		public JLabel la1,la2,la3,la4;
		public JTextField tx1,tx2,tx3,tx4,txWait;
		public JButton confirm,cancel;
		public Font laFont=new Font("宋体",Font.BOLD,15);
		public int row=table.getSelectedRow();
		
		public DepartmentModify(){
			ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg"); 
			JLabel label = new JLabel(background);
			s.setModal(true);
			s.setSize(500,250);
			s.setResizable(false);
			s.setLocationRelativeTo(null);
			s.setLayout(null);
			
			
			la1=new JLabel("科室编号：");
			la2=new JLabel("科室名称：");
			la3=new JLabel("科室主任：");
			la4=new JLabel("科室电话：");
			tx1=new JTextField();
			tx2=new JTextField();
			tx3=new JTextField();
			tx4=new JTextField();
			txWait=new JTextField();
			confirm=new JButton("确定");
			cancel=new JButton("取消");
			
			tx1.setEditable(false);
			
			la1.setBounds(30, 30, 100, 40);
			la1.setFont(laFont);
			la2.setBounds(250, 30, 100, 40);
			la2.setFont(laFont);
			la3.setBounds(30, 90, 100, 40);
			la3.setFont(laFont);
			la4.setBounds(250, 90, 100, 40);
			la4.setFont(laFont);
			tx1.setBounds(110, 35, 120, 30);
			tx1.setFont(laFont);
			tx2.setBounds(330, 35, 120, 30);
			tx2.setFont(laFont);
			tx3.setBounds(110, 95, 120, 30);
			tx3.setFont(laFont);
			tx4.setBounds(330, 95, 120, 30);
			tx4.setFont(laFont);
			confirm.setBounds(110, 150, 100, 40);
			cancel.setBounds(330, 150, 100, 40);
			
			s.add(la1);
			s.add(la2);
			s.add(la3);
			s.add(la4);
			s.add(tx1);
			s.add(tx2);
			s.add(tx3);
			s.add(tx4);
			s.add(confirm);
			s.add(cancel);
			
			confirm.addActionListener(this);
			cancel.addActionListener(this);
			
			s.add(label);
			label.setBounds(0, 0, 500, 250);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==cancel){
				s.dispose();
			}
			if(e.getSource()==confirm){
				
				int ok=JOptionPane.showConfirmDialog(null, "是否修改该科室信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(ok==JOptionPane.YES_OPTION){
					try{
						con=connect.getConnection();   //创建一个数据库连接
						Statement stmt=con.createStatement();   //创建一个数据库会话对象
						 //输入信息不能为空
						if(tx2.getText().equals("")||tx4.getText().equals("")){
							JOptionPane.showMessageDialog(null, "请输入完整的科室信息！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						//编号为3位数字
						else if(tx1.getText().length()>3||pattern.matcher(tx1.getText()).matches()==false){
							JOptionPane.showMessageDialog(null, "请输入正确的3位数科室编号!", "错误", JOptionPane.ERROR_MESSAGE);
						}
						//名字长度不能超过20个字符
						else if(tx2.getText().length()>10 || tx3.getText().length()>10){
							JOptionPane.showMessageDialog(null, "名字长度不能超过10个汉字！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						 //电话号码为8位数字
						else if(pattern.matcher(tx4.getText()).matches()==false||tx4.getText().length()!=8){  
							JOptionPane.showMessageDialog(null, "请输入正确的8位数电话号码！", "错误", JOptionPane.ERROR_MESSAGE);
						}

						else{
							
							int row2=table.getSelectedRow();
						String sql="update Department   set DeptName='"+tx2.getText().trim()+"',DrName='"+tx3.getText().trim()+"',DeptPhone='"+tx4.getText().trim()+"'where DeptNo='"+tx1.getText().trim()+"'  ";
						stmt.executeUpdate(sql);  
						String []data = new String[]{tx1.getText(),tx2.getText(),tx3.getText(),tx4.getText()};
						dtm.removeRow(row2);
						dtm.insertRow(row2, data);
						
          				JOptionPane.showMessageDialog(null, "修改成功");
          				s.dispose();
          				
							
						}
							
							}catch(Exception e1){
								e1.printStackTrace();
							}
						}
					
				
			}
		}
		
	}
}
