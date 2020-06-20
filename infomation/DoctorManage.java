import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class DoctorManage extends JFrame implements ActionListener{
	JPanel doctorManage=new JPanel();;
	public JButton query,queryAll,modify,delete;
	private JLabel manageTitle=new JLabel("医生档案信息操作");
	private final String []columnNames={"医生编号","医生姓名","性别","年龄","职称","学历","所属科室","籍贯","入职日期","电话号码"};
	private static JTable table;
	private static  DefaultTableModel dtm;
	private JScrollPane tableScroll=new JScrollPane();
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	 private JLabel la1,la2;
	 private JTextField tx1;
	 Font f1=new Font("草书", Font.CENTER_BASELINE,25);
	 String o;
	public DoctorManage(){
		//背景设置
		doctorManage.setLayout(null);
		ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg"); 
		JLabel label = new JLabel(background);
		
		//标题设置
		manageTitle.setFont(new Font("宋体",Font.BOLD,50));
		manageTitle.setBounds(60, 10, 1000, 50);
		doctorManage.add(manageTitle);
		
		//查询面板基本设置
		final JPanel queryPanel=new JPanel();
		queryPanel.setLayout(null);
		queryPanel.setBorder(new TitledBorder(null, "请选择您要进行的操作", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,null, Color.red));
		queryPanel.setBounds(70, 70, 590, 80);
		queryPanel.setOpaque(false);

	//标签设置
		la1=new JLabel("医生人数统计：");
		la1.setFont(f1);
		la2=new JLabel("人");
		la2.setFont(f1);
		
		
		tx1=new JTextField();
		//按钮设置
		query=new JButton("条件查询 ");
		query.setBounds(90, 95, 100,45);
		queryAll=new JButton("全部查询");
		queryAll.setBounds(240, 95, 100,45);
		modify=new JButton("修改");
		modify.setBounds(390, 95, 100,45);
		delete=new JButton("删除");
		delete.setBounds(540, 95,100,45);
        la1.setBounds(700, 60, 200, 45);
        tx1.setBounds(700, 105, 50, 35);
        tx1.setEditable(false);
        la2.setBounds(760, 98, 50, 50);
		//表格设置
		tableScroll.setBounds(new  Rectangle(70,150,935,355)); 
		defaultTableModel();   
		setTableColumnCenter();
		setbgcolor();
		doctorManage.add(tableScroll);
		tableScroll.setViewportView(table);
		table.getTableHeader().setReorderingAllowed(false);   //列不可拖动
		table.getTableHeader().setResizingAllowed(false);   //列宽不能改变
		
		Connection con;
		  con=connect.getConnection();
		  ResultSet rs;
		try{
			String sql1 = "select Count(*) from Doctor";
      	Statement stmt=con.createStatement();
		    rs=stmt.executeQuery(sql1);
      	while(rs.next()){
      		 o=rs.getString(1);
      		
      	}
			tx1.setText(o);
			
			
		}catch(Exception r){
			r.printStackTrace();
		}
		
		
		
		//添加组件
		doctorManage.add(la2);
		doctorManage.add(la1);
		doctorManage.add(tx1);
		doctorManage.add(delete);
		doctorManage.add(modify);
		doctorManage.add(query);
		doctorManage.add(queryAll);
		doctorManage.add(queryPanel);
		
		//添加监听器
		query.addActionListener(this);
		delete.addActionListener(this);
		modify.addActionListener(this);
		queryAll.addActionListener(this);
		
		
		
		String	sql="select * from Doctor";
		databaseSearch(sql);
		
		//添加背景
		doctorManage.add(label);
		label.setBounds(0, 0, 1100, 700);
	}
	
	//设置表格不可编辑
	public void defaultTableModel(){
		dtm=new DefaultTableModel(columnNames,0);
		table=new JTable(dtm){
			  public boolean isCellEditable(int row, int column){
				  return false;
			  }
		  };
	}
	
	//设置表格内容居中显示
	public void setTableColumnCenter(){  
	    DefaultTableCellRenderer r = new DefaultTableCellRenderer();     
	    r.setHorizontalAlignment(JLabel.CENTER);     
	    table.setDefaultRenderer(Object.class, r);  
	}

	//设置表格隔行背景颜色不同
	public static void setbgcolor(){
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
			String[] data = new String[11];
			while (rs.next()){
				for (int j = 1; j <= 11; j++){
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==modify){   //修改操作设置
			if(table.getSelectedRowCount()!=1){
				JOptionPane.showMessageDialog(null, "请选择一项医生信息进行修改！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			else{
				try{
					con=connect.getConnection();   //创建一个数据库连接
					Statement stmt1=con.createStatement();   //创建一个数据库会话对象
					Statement stmt2=con.createStatement();  
					int row=table.getSelectedRow();
					String drId=(String) table.getValueAt(row, 0);
					String sql="select * from Doctor where DrId='"+drId+"'";
					ResultSet rs=stmt1.executeQuery(sql);
					rs.next();
					DoctorModify dig=new DoctorModify();
					dig.tx1.setText(rs.getString("DrId"));
					dig.tx2.setText(rs.getString("DrName"));
					dig.sex.setSelectedItem(rs.getString("DrSex"));
					dig.age.setSelectedItem(rs.getString("DrAge"));
					dig.position.setSelectedItem(rs.getString("DrPos"));
					dig.educated.setSelectedItem(rs.getString("DrEdu"));
					dig.department.setSelectedItem(rs.getString("DeptName"));
					dig.tx8.setText(rs.getString("DrNative"));
					dig.showDate1.setText(rs.getString("DrDate"));
					dig.tx10.setText(rs.getString("DrPhone"));
					dig.tx11.setText(rs.getString("Password"));
					dig.txWait.setText(drId);
					dig.s.setVisible(true);
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==query){    //条件查询操作设置
			DoctorQuery dig=new DoctorQuery();
			dig.s.setVisible(true);
		}
		if(e.getSource()==queryAll){   //全部查询操作设置
			try{
				con=connect.getConnection();   //创建一个数据库连接
				String sql="select * from Doctor";
				databaseSearch(sql);
				if(table.getRowCount()!=0){
					JOptionPane.showMessageDialog(null, "查询成功！");
				}
				else{
					JOptionPane.showMessageDialog(null, "没有找到您要的信息！");
				}
				
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		if(e.getSource()==delete){   //删除操作设置
			try{
				con=connect.getConnection();   //创建一个数据库连接
				Statement stmt=con.createStatement();   //创建一个数据库会话对象
				int selectCount=table.getSelectedRowCount();
				if(selectCount==0){
					JOptionPane.showMessageDialog(null, "请选择您要删除的信息！");
				}
				else if(selectCount==1){
					int ok=JOptionPane.showConfirmDialog(null, "是否删除该医生信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						int row=table.getSelectedRow();
						String drId=(String) table.getValueAt(row, 0);
						String sql="delete from Doctor where DrId='"+drId+"'";
						stmt.executeUpdate(sql);
						dtm.removeRow(row);
						JOptionPane.showMessageDialog(null, "删除成功！");
					}
				}
				else{
					int ok=JOptionPane.showConfirmDialog(null, "是否删除所选"+selectCount+"个医生信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						for(int i=1;i<=selectCount;i++){
							int row1=table.getSelectedRow();
							String drId=(String)table.getValueAt(row1, 0);
							String sql="delete from Doctor where DrId='"+drId+"'";
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
		
	}
	
	

	//医生信息修改对话框设置+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class DoctorModify implements ActionListener{
		private JDialog s=new JDialog();
		private JLabel la0,la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;
		private JTextField tx1,tx2,tx8,tx10,txWait;
		private JPasswordField tx11;
		private JButton confirm,cancel;
		private Font laFont=new Font("宋体",Font.BOLD,15);
		private JComboBox sex,age,position,educated,department;
		private String str3[],str4[],str5[],str6[],str7[];
		private JTextField showDate1 = new JTextField("单击选择日期");
		private Timedate dateChooser1 = Timedate.getInstance("yyyy-MM-dd");
		private Pattern pattern=Pattern.compile("[0-9]*"); 
		private java.sql.Connection con=null;
		
		private int row=table.getSelectedRow();
		Font f2=new Font("隶书", Font.BOLD, 25);
		public DoctorModify(){
		
			ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg"); 
			JLabel label = new JLabel(background);
			s.setModal(true);
			s.setSize(950,300);
			s.setResizable(false);
			s.setLocationRelativeTo(null);
			s.setLayout(null);
			la0=new JLabel("医生信息修改");  
			la0.setFont(f2);
			la1=new JLabel("医生编号:");   //s标签设置
			la2=new JLabel("医生姓名:");
			la3=new JLabel("性别:");
			la4=new JLabel("年龄:");
			la5=new JLabel("职称:");
			la6=new JLabel("学历:");
			la7=new JLabel("所属科室:");
			la8=new JLabel("籍贯:");
			la9=new JLabel("入职日期:");
			la10=new JLabel("电话号码:");
			la11=new JLabel("系统密码:");
			tx1=new JTextField();
			tx1.setEditable(false);
			tx2=new JTextField();   
			tx8=new JTextField();   
			tx10=new JTextField();   
			tx11=new JPasswordField(); 
			txWait=new JTextField();
			confirm=new JButton("确定");
			cancel=new JButton("取消");
			
			la0.setBounds(0, 0, 200, 40);
			la1.setBounds(30, 60, 100, 40);
			la1.setFont(laFont);
			la2.setBounds(300, 60, 100, 40);
			la2.setFont(laFont);
			la3.setBounds(570, 60, 100, 40);
			la3.setFont(laFont);
			la4.setBounds(750, 60, 100, 40);
			la4.setFont(laFont);
			la5.setBounds(30, 110, 100, 40);
			la5.setFont(laFont);
			la6.setBounds(210, 110, 100, 40);
			la6.setFont(laFont);
			la7.setBounds(390, 110, 100, 40);
			la7.setFont(laFont);
			la8.setBounds(640, 110, 100, 40);
			la8.setFont(laFont);
			la9.setBounds(30, 160, 100, 40);
			la9.setFont(laFont);
			la10.setBounds(300, 160, 100, 40);
			la10.setFont(laFont);
			la11.setBounds(570,160,100,40);
			la11.setFont(laFont);
			
			tx1.setBounds(110, 65, 150, 30);
			tx1.setFont(laFont);
			tx2.setBounds(380, 65, 150, 30);
			tx2.setFont(laFont);
			tx8.setBounds(690, 115, 150, 30);
			tx8.setFont(laFont);
			tx10.setBounds(380, 165, 150, 30);
			tx10.setFont(laFont);
			tx11.setBounds(650, 165, 150, 30);
			tx11.setFont(laFont);
			tx11.setEditable(false);
			confirm.setBounds(600, 215, 150, 30);
			cancel.setBounds(750, 215, 150, 30);
			
			//性别下拉框设置
			str3=new String[]{"","男","女"};
		    sex=new JComboBox(str3);
		    sex.setBounds(620, 65, 100, 25);
		    sex.setFont(laFont);
		    
		    //年龄下拉框设置
			str4=new String[54];
			str4[0]="";
			for(int i=1;i<=53;i++){
				str4[i]=String.valueOf(i+17);
			}
			age=new JComboBox(str4);
			age.setBounds(800, 65, 100, 25);
			age.setFont(laFont);
			
			//职称下拉框设置
			str5=new String[]{"","医师","主治医师","副主任医师","主任医师"};
			position=new JComboBox(str5);
			position.setBounds(80, 115, 100, 25);
			position.setFont(laFont);
			
			//学历下拉框设置
			str6=new String[]{"","大专","本科","硕士","博士","博士后"};
			educated=new JComboBox(str6);
			educated.setBounds(260, 115, 100, 25);
			educated.setFont(laFont);
			
			//所属科室下拉框设置
			str7=new String[]{""};
			department=new JComboBox(str7);
			try{
				con=connect.getConnection();   //创建一个数据库连接
				Statement stmt=con.createStatement();   //创建一个数据库会话对象
				String sql="select * from Department";
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next()){
					department.addItem(rs.getString("DeptName"));
				}
				stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			connect.closeConnection();
			department.setBounds(470, 115, 125, 25);
			department.setFont(laFont);
			
			//入职时间设置
			showDate1.setFont(new Font("",1,20));
			dateChooser1.register(showDate1);
			s.add(showDate1);
			showDate1.setBounds(110, 165, 150, 30);
			
			s.add(la0);
			s.add(la1);
			s.add(la2);
			s.add(la3);
			s.add(la4);
			s.add(la5);
			s.add(la6);
			s.add(la7);
			s.add(la8);
			s.add(la9);
			s.add(la10);
			s.add(la11);
			s.add(tx1);
			s.add(tx2);
			s.add(sex);
			s.add(age);
			s.add(position);
			s.add(educated);
			s.add(department);
			s.add(tx8);
			s.add(showDate1);
			s.add(tx10);
			s.add(tx11);
			s.add(confirm);
			s.add(cancel);
			
			//添加监听器
			confirm.addActionListener(this);
			cancel.addActionListener(this);
			
			s.add(label);
			label.setBounds(0, 0, 950, 300);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==cancel){
				s.dispose();
			}
			if(e.getSource()==confirm){
				String wait=txWait.getText();
				int ok=JOptionPane.showConfirmDialog(null, "是否修改该医生信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(ok==JOptionPane.YES_OPTION){
					try{
						con=connect.getConnection();   //创建一个数据库连接
						Statement stmt1=con.createStatement();   //创建一个数据库会话对象
						Statement stmt2=con.createStatement();   //创建一个数据库会话对象
						
						String SQL2=("update Medical_records set DrName='"+tx2.getText()+"',DeptName='"+department.getSelectedItem().toString()+"'where DrId='"+tx1.getText()+"'");
						
						//输入条件
						if(tx1.getText().equals("")||tx2.getText().equals("")||tx8.getText().equals("")||
						   tx10.getText().equals("")||tx11.getText().equals("")||sex.getSelectedIndex()==0||
						   age.getSelectedIndex()==0||position.getSelectedIndex()==0||educated.getSelectedIndex()==0||
						   department.getSelectedIndex()==0||showDate1.getText().equals("单击选择日期")){
							JOptionPane.showMessageDialog(null, "请输入完善的医生信息！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else if(tx1.getText().length()!=4||pattern.matcher(tx1.getText()).matches()==false){
							JOptionPane.showMessageDialog(null, "请输入4位数医生编号！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else if(tx2.getText().length()>10){
							JOptionPane.showMessageDialog(null, "名字长度不能超过10个汉字！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else if(tx8.getText().length()>10){
							JOptionPane.showMessageDialog(null, "籍贯长度不能超过10个汉字！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else if(tx10.getText().length()!=11||pattern.matcher(tx10.getText()).matches()==false){
							JOptionPane.showMessageDialog(null, "请输入11位数手机号码！", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else if(tx11.getText().length()<6||tx11.getText().length()>8||pattern.matcher(tx11.getText()).matches()==false){
							JOptionPane.showMessageDialog(null, "请输入6-8位数字密码!", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else{
								String sql="update Doctor set DrName='"+tx2.getText()+"',DrSex='"+sex.getSelectedItem().toString()+"',DrAge='"+age.getSelectedItem().toString()+"',DrPos='"+position.getSelectedItem().toString()+"',"
										+ "DrEdu='"+educated.getSelectedItem().toString()+"',DeptName='"+department.getSelectedItem().toString()+"',DrNative='"+tx8.getText()+"',DrDate='"+showDate1.getText()+"',DrPhone='"+tx10.getText()+"',"
												+ "Password='"+tx11.getText()+"' where DrId='"+tx1.getText()+"'";
								
								stmt1.executeUpdate(sql);
								int row2=table.getSelectedRow();
								String []data = new String[]{tx1.getText(),tx2.getText(),sex.getSelectedItem().toString(),age.getSelectedItem().toString(),position.getSelectedItem().toString(),educated.getSelectedItem().toString(),department.getSelectedItem().toString(),tx8.getText(),showDate1.getText(),tx10.getText()};
								dtm.removeRow(row2);
								dtm.insertRow(row2, data);
								
		          				JOptionPane.showMessageDialog(null, "修改成功");
		          				s.dispose();
		          				try {
				     				stmt1 = con.createStatement();
				     				stmt1.executeUpdate(SQL2);
				    
		      					}
		      					catch(Exception e5){
		      						e5.printStackTrace();
		      					}
						
								}
							}catch(Exception e1){
								e1.printStackTrace();
							
							}
				}
					
			}
		}
		
	}
	
	//医生信息查询对话框设置+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class DoctorQuery implements ActionListener{
		
		JDialog s=new JDialog();
		private JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;
		private JTextField tx1,tx2,tx9;
		private JComboBox jcbb3,jcbb4,jcbb5,jcbb6,jcbb7,jcbb8;
		private JButton query,cancel;
		private String str3[],str4[],str5[],str6[],str7[],str8[];
		private JLabel title=new JLabel("请输入您需要查询的条件信息：");
		private Font laFont=new Font("宋体",Font.BOLD,15);
		private JTextField date10 = new JTextField("单击选择日期");
		private JTextField date12 = new JTextField("单击选择日期");
		private Timedate dateChooser10 = Timedate.getInstance("yyyy-MM-dd");
		private Timedate dateChooser12 = Timedate.getInstance("yyyy-MM-dd");
		
		public DoctorQuery(){
			ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg");  //背景图片 
			JLabel label = new JLabel(background);
			title.setFont(new Font("宋体",Font.BOLD,30));
			title.setBounds(30, 10, 900, 50);
			s.add(title);
			
			la1=new JLabel("编号:");
			la2=new JLabel("姓名:");
			la3=new JLabel("性别:");
			la4=new JLabel("年龄:");
			la5=new JLabel("到");
			la6=new JLabel("职称:");
			la7=new JLabel("学历:");
			la8=new JLabel("所属科室:");
			la9=new JLabel("籍贯:");
			la10=new JLabel("入职日期:");
			la11=new JLabel("到");
			query=new JButton("查询");
			cancel=new JButton("取消");
			query.setBounds(120, 510, 100, 50);
			cancel.setBounds(280, 510, 100, 50);
			tx1=new JTextField();
			tx2=new JTextField();
			tx9=new JTextField();
			
			la1.setBounds(40, 60, 100, 40);
			la1.setFont(laFont);
			tx1.setBounds(120, 65, 120, 25);
			tx1.setFont(laFont);
			la2.setBounds(40, 110, 100, 40);
			la2.setFont(laFont);
			tx2.setBounds(120, 115, 120, 25);
			tx2.setFont(laFont);
			
			la3.setBounds(40, 160, 100, 40);
			la3.setFont(laFont);
			str3=new String[]{"","男","女"};
		    jcbb3=new JComboBox(str3);
		    jcbb3.setBounds(120, 165, 120, 25);
		    jcbb3.setFont(laFont);
			
			la4.setBounds(40, 210, 100, 40);
			la4.setFont(laFont);
			str4=new String[54];
			str4[0]="";
			for(int i=1;i<=53;i++){
				str4[i]=String.valueOf(i+17);
			}
			jcbb4=new JComboBox(str4);
			jcbb4.setBounds(120, 215, 80, 25);
			jcbb4.setFont(laFont);
			
			la5.setBounds(210, 210, 100, 40);
			la5.setFont(laFont);
			str5=new String[54];
			str5[0]="";
			for(int i=1;i<=53;i++){
				str5[i]=String.valueOf(i+17);
			}
			jcbb5=new JComboBox(str5);
			jcbb5.setBounds(240, 215, 80, 25);
			jcbb5.setFont(laFont);
			
			
			la6.setBounds(40, 260, 100, 40);
			la6.setFont(laFont);
			str6=new String[]{"","医师","主治医师","副主任医师","主任医师"};
			jcbb6=new JComboBox(str6);
			jcbb6.setBounds(120, 265, 120, 25);
			jcbb6.setFont(laFont);
			
			la7.setBounds(40, 310, 100, 40);
			la7.setFont(laFont);
			str7=new String[]{"","大专","本科","硕士","博士","博士后"};
			jcbb7=new JComboBox(str7);
			jcbb7.setBounds(120, 315, 120, 25);
			jcbb7.setFont(laFont);
			
			la8.setBounds(40, 360, 100, 40);
			la8.setFont(laFont);
			str8=new String[]{""};
			jcbb8=new JComboBox(str8);
			try{
				con=connect.getConnection();   //创建一个数据库连接
				Statement stmt=con.createStatement();   //创建一个数据库会话对象
				String sql="select * from Department";
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next()){
					jcbb8.addItem(rs.getString("DeptName"));
				}
				stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			connect.closeConnection();
			jcbb8.setBounds(120, 365, 120, 25);
			jcbb8.setFont(laFont);
			
			la9.setBounds(40, 410, 100, 40);
			la9.setFont(laFont);
			tx9.setBounds(120, 415, 120, 25);
			tx9.setFont(laFont);
			
			la10.setBounds(40, 460, 100, 40);
			la10.setFont(laFont);
			date10.setFont(laFont);
			dateChooser10.register(date10);
			s.add(date10); 
			date10.setBounds(120, 465, 120, 25);
			
			la11.setBounds(250, 460, 100, 40);
			la11.setFont(laFont);
			
			date12.setFont(laFont);
			dateChooser12.register(date12);
			s.add(date12);
			date12.setBounds(280, 465, 120, 25);
			
			//添加监听器
			query.addActionListener(this);
			cancel.addActionListener(this);
			tx1.addActionListener(this);
			
			s.add(la1);
			s.add(tx1);
			s.add(la2);
			s.add(tx2);
			s.add(la3);
			s.add(jcbb3);
			s.add(la4);
			s.add(jcbb4);
			s.add(la5);
			s.add(jcbb5);
			s.add(jcbb6);
			s.add(jcbb7);
			s.add(jcbb8);
			s.add(la6);
			s.add(la7);
			s.add(la8);
			s.add(la9);
			s.add(tx9);
			s.add(la10);
			s.add(date10);
			s.add(la11);
			s.add(date12);
			s.add(query);
			s.add(cancel);
			
			s.setModal(true);
			s.setSize(500,600);
			s.setResizable(false);
			s.setLocationRelativeTo(null);
			s.setLayout(null);
			
			label.setBounds(0, 0, 500, 600);
			s.add(label);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==cancel){
				s.dispose();
			}
			if(e.getSource()==query){
				if(tx1.getText().trim().equals("")&&tx2.getText().trim().equals("")&&jcbb3.getSelectedIndex()==0&&
				   jcbb4.getSelectedIndex()==0&&jcbb5.getSelectedIndex()==0&&jcbb6.getSelectedIndex()==0&&
				   jcbb7.getSelectedIndex()==0&&jcbb8.getSelectedIndex()==0&&tx9.getText().trim().equals("")&&
				   date10.getText().trim().equals("单击选择日期")&&date12.getText().trim().equals("单击选择日期")){
					JOptionPane.showMessageDialog(null, "请输入您要查询的条件信息！", "错误", JOptionPane.ERROR_MESSAGE);
				}
				else if(!tx1.getText().trim().equals("")){
					try{
						con=connect.getConnection();   //创建一个数据库连接
						//Statement st1=con.createStatement();   //创建一个数据库会话对象
						String drId=tx1.getText().trim();
						String sql1="select * from Doctor where DrId='"+drId+"'";
						databaseSearch(sql1);
						if(table.getRowCount()!=0){
							JOptionPane.showMessageDialog(null, "查询成功！");
						}
						else{
							JOptionPane.showMessageDialog(null, "没有找到您要的信息！");
						}
						s.dispose();
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
				else {
					try{
						String sql="select * from Doctor where 1=1";
						String drName=tx2.getText().trim();
						String drSex=jcbb3.getSelectedItem().toString();
						String drAge1=jcbb4.getSelectedItem().toString();
						String drAge2=jcbb5.getSelectedItem().toString();
						String drPos=jcbb6.getSelectedItem().toString();
						String drEdu=jcbb7.getSelectedItem().toString();
						String drDept=jcbb8.getSelectedItem().toString();
						String drNative=tx9.getText().trim();
						String drDate1=date10.getText();
						String drDate2=date12.getText();
						if(!drName.equals("")){
							sql+=" and DrName='"+drName+"'";
						}
						if(!drSex.equals("")){
							sql+=" and DrSex='"+drSex+"'";
						}
						if(!drAge1.equals("")&&drAge2.equals("")){
							sql+=" and DrAge='"+drAge1+"'";
						}
						if(drAge1.equals("")&&!drAge2.equals("")){
							sql+=" and DrAge='"+drAge2+"'";
						}
						if(!drAge1.equals("")&&!drAge2.equals("")){
							if(Integer.parseInt(drAge1)>Integer.parseInt(drAge2)){
								JOptionPane.showMessageDialog(null, "年龄范围请按小到大选择！", "错误", JOptionPane.ERROR_MESSAGE);
							}
							else{
								sql+=" and DrAge between "+drAge1+" and "+drAge2+"";
							}
						}
						if(!drPos.equals("")){
							sql+=" and DrPos='"+drPos+"'";
						}
						if(!drEdu.equals("")){
							sql+=" and DrEdu='"+drEdu+"'";
						}
						if(!drDept.equals("")){
							sql+=" and DeptName='"+drDept+"'";
						}
						if(!drNative.equals("")){
							sql+=" and DrNative like '%"+drNative+"%'";
						}
						if(!drDate1.equals("单击选择日期")&&!drDate2.equals("单击选择日期")){
							try{
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								Date dt1 = df.parse(date10.getText());
					            Date dt2 = df.parse(date12.getText());
					            if(dt1.getTime()>dt2.getTime()){
					            	JOptionPane.showMessageDialog(null, "日期范围请按先到后选择！", "错误", JOptionPane.ERROR_MESSAGE);
					            }
					            else {
					            	sql+=" and DrDate between '"+drDate1+"' and '"+drDate2+"'";
					            }
							}catch(Exception e1){
								e1.printStackTrace();
							}
						}
						if(!drDate1.equals("单击选择日期")&&drDate2.equals("单击选择日期")){
							sql+=" and DrDate='"+drDate1+"'";
						}
						if(drDate1.equals("单击选择日期")&&!drDate2.equals("单击选择日期")){
							sql+=" and DrDate='"+drDate2+"'";
						}
						if(!drName.equals("")||!drSex.equals("")||!drAge1.equals("")||!drAge2.equals("")||
						   !drPos.equals("")||!drEdu.equals("")||!drDept.equals("")||!drNative.equals("")||
						   !drDate1.equals("单击选择日期")||!drDate2.equals("单击选择日期")){
							con=connect.getConnection();   //创建一个数据库连接
							databaseSearch(sql);
							if(table.getRowCount()!=0){
								JOptionPane.showMessageDialog(null, "查询成功！");
							}
							else{
								JOptionPane.showMessageDialog(null, "没有找到您要的信息！");
							}
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
