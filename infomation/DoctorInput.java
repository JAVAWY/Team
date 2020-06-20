import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class DoctorInput extends JFrame implements ActionListener{
	
	JPanel doctorInput=new JPanel();
	private JLabel inputTitle =new JLabel("医生档案信息录入");
	private JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;	
	private JTextField tx1,tx2,tx8,tx10;
	private JPasswordField tx11;
	public JButton save,clean;
	private JComboBox sex,age,position,educated,department;
	private String str3[],str4[],str5[],str6[],str7[];
	private final String columnNames[];
	private JTextField showDate1 = new JTextField("单击选择日期");
	private Timedate dateChooser1 = Timedate.getInstance("yyyy-MM-dd");
	private Font laFont=new Font("宋体",Font.BOLD,15);
	private JScrollPane JScrollPane1=new JScrollPane();
	private static JTable table;
	private static  DefaultTableModel dtm;
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	private Pattern pattern=Pattern.compile("[0-9]*"); 
	
	public DoctorInput(){
		//医生信息录入界面设置
		doctorInput.setLayout(null);
		ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg");  //背景图片 
		JLabel label = new JLabel(background);
		inputTitle.setFont(new Font("宋体",Font.BOLD,50));
		inputTitle.setBounds(60, 10, 1000, 50);
		doctorInput.add(inputTitle);
		
		//录入操作面板设置
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "录入操作", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red));
		panel.setBounds(60, 310, 950,200);
		panel.setOpaque(false);
		
		la1=new JLabel("医生编号:");   //标签设置
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
		
		la1.setBounds(30, 20, 100, 40);
		la1.setFont(laFont);
		la2.setBounds(300, 20, 100, 40);
		la2.setFont(laFont);
		la3.setBounds(570, 20, 100, 40);
		la3.setFont(laFont);
		la4.setBounds(750, 20, 100, 40);
		la4.setFont(laFont);
		la5.setBounds(30, 70, 100, 40);
		la5.setFont(laFont);
		la6.setBounds(210, 70, 100, 40);
		la6.setFont(laFont);
		la7.setBounds(390, 70, 100, 40);
		la7.setFont(laFont);
		la8.setBounds(640, 70, 100, 40);
		la8.setFont(laFont);
		la9.setBounds(30, 120, 100, 40);
		la9.setFont(laFont);
		la10.setBounds(300, 120, 100, 40);
		la10.setFont(laFont);
		la11.setBounds(570,120,100,40);
		la11.setFont(laFont);
		
		tx1=new JTextField();   
		tx1.setBounds(110, 25, 150, 30);
		tx1.setFont(laFont);
		tx2=new JTextField();
		tx2.setBounds(380, 25, 150, 30);
		tx2.setFont(laFont);
		tx8=new JTextField();
		tx8.setBounds(690, 75, 150, 30);
		tx8.setFont(laFont);
		tx10=new JTextField();
		tx10.setBounds(380, 125, 150, 30);
		tx10.setFont(laFont);
		tx11=new JPasswordField();
		tx11.setBounds(650, 125, 150, 30);
		tx11.setFont(laFont);
		
		save=new JButton("保存");
		clean=new JButton("清空");
		save.setBounds(630, 163, 150, 30);
		clean.setBounds(780, 163, 150, 30);
		
		//性别下拉框设置
		str3=new String[]{"","男","女"};
	    sex=new JComboBox(str3);
	    sex.setBounds(620, 25, 100, 25);
	    sex.setFont(laFont);
	    
	    //年龄下拉框设置
		str4=new String[54];
		str4[0]="";
		for(int i=1;i<=53;i++){
			str4[i]=String.valueOf(i+17);
		}
		age=new JComboBox(str4);
		age.setBounds(800, 25, 100, 25);
		age.setFont(laFont);
		
		//职称下拉框设置
		str5=new String[]{"","医师","主治医师","副主任医师","主任医师"};
		position=new JComboBox(str5);
		position.setBounds(80, 75, 100, 25);
		position.setFont(laFont);
		
		//学历下拉框设置
		str6=new String[]{"","大专","本科","硕士","博士","博士后"};
		educated=new JComboBox(str6);
		educated.setBounds(260, 75, 100, 25);
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
		department.setBounds(470, 75, 125, 25);
		department.setFont(laFont);
		
		//入职时间设置
		showDate1.setFont(new Font("",1,20));
		dateChooser1.register(showDate1);
		panel.add(showDate1);
		showDate1.setBounds(110, 125, 150, 30);
		
		//表格设置
		columnNames=new String[]{"医生编号","医生姓名","性别","年龄","职称","学历","所属科室","籍贯","入职日期","电话号码"};
		defaultTableModel();
		setTableColumnCenter();
		setbgcolor();
		JScrollPane1.setBounds(new  Rectangle(60,70,935,230));
		doctorInput.add(JScrollPane1);
		JScrollPane1.setViewportView(table);   //创建一个视口（如果有必要）并设置其视图
		table.getTableHeader().setReorderingAllowed(false);   //列不可拖动
		table.getTableHeader().setResizingAllowed(false);   //列宽不能改变
		

		String	sql="select * from Doctor";
		databaseSearch(sql);
		
		
		
		//组件添加
		panel.add(la1);
		panel.add(la2);
		panel.add(la3);
		panel.add(la4);
		panel.add(la5);
		panel.add(la6);
		panel.add(la7);
		panel.add(la8);
		panel.add(la9);
		panel.add(la10);
		panel.add(la11);
		panel.add(tx1);
		panel.add(tx2);	
		panel.add(sex);
		panel.add(age);
		panel.add(position);
		panel.add(educated);
		panel.add(department);
		panel.add(tx8);
		panel.add(tx10);
		panel.add(tx11);
		panel.add(save);
		panel.add(clean);
		
		//添加监听器
		save.addActionListener(this);
		clean.addActionListener(this);
		
		
		doctorInput.add(panel);
		
		doctorInput.add(label);   //添加背景
		label.setBounds(0, 0, 1100, 700);
	}
	
	//数据库数据录入到表格中
	private void databaseSearch(String sql) {
		// TODO Auto-generated method stub
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
			rs=stmt.executeQuery(sql);
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==clean){
			tx1.setText("");
			tx2.setText("");
			tx8.setText("");
			tx10.setText("");
			tx11.setText("");
			sex.setSelectedIndex(0);
			age.setSelectedIndex(0);
			position.setSelectedIndex(0);
			educated.setSelectedIndex(0);
			department.setSelectedIndex(0);
			showDate1.setText("单击选择日期");
		}
		if(e.getSource()==save){
			if(tx1.getText().equals("")||tx2.getText().equals("")||tx8.getText().equals("")||
			   tx10.getText().equals("")||tx11.getText().equals("")||sex.getSelectedIndex()==0||
			   age.getSelectedIndex()==0||position.getSelectedIndex()==0||educated.getSelectedIndex()==0||
			   department.getSelectedIndex()==0||showDate1.getText().equals("单击选择日期")){
				JOptionPane.showMessageDialog(null, "请输完整的医生信息！", "错误", JOptionPane.ERROR_MESSAGE);
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
				String DrId=tx1.getText();
				try{
					con=connect.getConnection();   //创建一个数据库连接
					Statement stmt=con.createStatement();   //创建一个数据库会话对象
					ResultSet rs=stmt.executeQuery("select * from Doctor where DrId='"+DrId+"'");   //SQL语句
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "该医生编号已存在，请重新输入!", "错误", JOptionPane.ERROR_MESSAGE);
						rs.close();
					}
					else{
						int ok=JOptionPane.showConfirmDialog(null, "是否保存该医生信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
						if(ok==JOptionPane.YES_OPTION){
							//添加信息到数据库
							String sql="INSERT INTO Doctor(DrId,DrName,DrSex,DrAge,DrPos,DrEdu,DeptName,DrNative,DrDate,DrPhone,Password)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement parepare=con.prepareStatement(sql);
							parepare.setString(1, tx1.getText());
							parepare.setString(2, tx2.getText());
							parepare.setString(3, sex.getSelectedItem().toString());
							parepare.setString(4, age.getSelectedItem().toString());
							parepare.setString(5, position.getSelectedItem().toString());
							parepare.setString(6, educated.getSelectedItem().toString());
							parepare.setString(7, department.getSelectedItem().toString());
							parepare.setString(8, tx8.getText());
							parepare.setString(9, showDate1.getText());
							parepare.setString(10, tx10.getText());
							parepare.setString(11, tx11.getText());
							parepare.executeUpdate();
							
							String data[]=new String[]{tx1.getText(),tx2.getText(),sex.getSelectedItem().toString(),age.getSelectedItem().toString(),
									                   position.getSelectedItem().toString(),educated.getSelectedItem().toString(),
									                   department.getSelectedItem().toString(),tx8.getText(),showDate1.getText(),
									                   tx10.getText(),tx11.getText()};
							dtm.addRow(data);   //在表格添加一行刚添加的数据
							JOptionPane.showMessageDialog(null, "录入成功");
							tx1.setText("");
							tx2.setText("");
							tx8.setText("");
							tx10.setText("");
							tx11.setText("");
							sex.setSelectedIndex(0);
							age.setSelectedIndex(0);
							position.setSelectedIndex(0);
							educated.setSelectedIndex(0);
							department.setSelectedIndex(0);
							showDate1.setText("单击选择日期");
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}

}
