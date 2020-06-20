import java.awt.*; 
import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class PatientInput extends JFrame implements ActionListener{
	
	JPanel patientInput=new JPanel();
	private JLabel inputTitle =new JLabel("病人档案信息录入");
	private JLabel te1,te2,te3,te4,te5,te6,te7,te8,te9,te10,te11;
	private JTextField tx1,tx2,tx6,tx7,tx9;
	private JButton save,clean;
	private Font teFont=new Font("宋体",Font.BOLD,15);
	private JComboBox sex,age,inHospital;
	private JScrollPane jsp;
	private String str5[],str4[],str8[];
	private Timedate dateChooser1,dateChooser2,dateChooser3;
	private JTextField date1,date2;
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	private Pattern pattern=Pattern.compile("[0-9]*"); 
	 
	public PatientInput(){
		//病人信息录入界面设置
		patientInput.setLayout(null);
		ImageIcon background = new ImageIcon("F:/课程设计/right_bg.jpg");  //背景图片 
		JLabel label = new JLabel(background);
		
		inputTitle.setFont(new Font("宋体",Font.BOLD,50));
		inputTitle.setBounds(60, 10, 1000, 50);
		patientInput.add(inputTitle);
	    
		te1=new JLabel("病人ID:");
		te2=new JLabel("姓名:");
		te3=new JLabel("出生年月:");
		te4=new JLabel("性别:");
		te5=new JLabel("年龄:");
		te6=new JLabel("出生地:");
		te7=new JLabel("籍贯:");	
		te8=new JLabel("是否住院:");
		te9=new JLabel("预交费用:");
		te10=new JLabel("住院时间:");
		te11=new JLabel("出院时间:");
		
		tx1=new JTextField();
		tx2=new JTextField();
		tx6=new JTextField();
		tx7=new JTextField();
		tx9=new JTextField();
		
		//年龄下拉框设置
		str5=new String[132];
		str5[0]="";
		str5[1]="0.5";
		for(int i=2;i<=131;i++){
			str5[i]=String.valueOf(i-1);
		}
		age=new JComboBox(str5);
		age.setFont(teFont);
		
		//性别下拉框设置
		str4=new String[]{"","男","女"};
		sex=new JComboBox(str4);
		sex.setFont(teFont);
		
		//住院情况设置
		str8=new String[]{"","是","否"};
		inHospital=new JComboBox(str8);
		inHospital.setFont(teFont);
		inHospital.addActionListener(this);
		
		//住院时间设置
		dateChooser2= Timedate.getInstance("yyyy-MM-dd");
		date2= new JTextField("单击选择日期");
        date2.setFont(new Font("",1,20));
        dateChooser2.register(date2);
	    patientInput.add(date2);
	    
		//出院时间设置
	   
	    dateChooser1= Timedate.getInstance("yyyy-MM-dd");
		date1= new JTextField("单击选择日期");
        date1.setFont(new Font("",1,20));
        dateChooser1.register(date1);
	    patientInput.add(date1);
	
		te1.setBounds(50,100,150,40);
		te1.setFont(teFont);
		te2.setBounds(450,100,150,40);
		te2.setFont(teFont);
		te3.setBounds(50,150,150,40);
		te3.setFont(teFont);
		te4.setBounds(450,150,150,40);
		te4.setFont(teFont);
		te5.setBounds(50, 200,150,40);
		te5.setFont(teFont);
		te6.setBounds(450,200,150,40);
		te6.setFont(teFont);
		te7.setBounds(50,250,150,40);
		te7.setFont(teFont);
		te8.setBounds(450,250,150,40);
		te8.setFont(teFont);
		te9.setBounds(50,300,150,40);
		te9.setFont(teFont);
		te10.setBounds(450,300,150,40);
		te10.setFont(teFont);
		te11.setBounds(450,350,150,40);
		te11.setFont(teFont);
		
		tx1.setBounds(200,105,150, 30);
		tx1.setFont(teFont);
		tx2.setBounds(580, 105,150, 30); 
		tx2.setFont(teFont);
		age.setBounds(200, 205, 150, 30);
		sex.setBounds(580, 155, 150, 30);
		date1.setBounds(200, 155,150, 30);
		tx6.setBounds(580, 205, 150, 30);
		tx6.setFont(teFont);
		tx7.setBounds(200, 255, 150, 30);
		tx7.setFont(teFont);
		inHospital.setBounds(580, 255, 150, 30);
		tx9.setBounds(200, 305, 150, 30);
		tx9.setFont(teFont);
		date2.setBounds(580, 305, 150, 30);
		
		te10.setVisible(false);
		te11.setVisible(false);
		date2.setVisible(false);
	
		
		save=new JButton("保存");
		clean=new JButton("清空");
		
		patientInput.add(te1);patientInput.add(te2);patientInput.add(te3);
		patientInput.add(te4);patientInput.add(te5);patientInput.add(te6);
		patientInput.add(te7);patientInput.add(te8);patientInput.add(te9);
		patientInput.add(te10);patientInput.add(te11);
		
		patientInput.add(tx1);patientInput.add(tx2);patientInput.add(age);
		patientInput.add(sex);patientInput.add(tx6);
		patientInput.add(tx7);patientInput.add(inHospital);patientInput.add(tx9);
		
		patientInput.add(save);
		patientInput.add(clean);
		save.setBounds(500,450,100,40); 
		save.addActionListener(this);
		clean.setBounds(650,450,100,40);
		clean.addActionListener(this);
		
		patientInput.add(label);
		label.setBounds(0, 0, 1100, 700);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==clean){
			date1.setText("单击选择日期");
			date2.setText("单击选择日期");
			
			tx1.setText("");
			tx2.setText("");
			tx6.setText("");
			tx7.setText("");
			tx9.setText("");
			age.setSelectedIndex(0);
			sex.setSelectedIndex(0);
			inHospital.setSelectedIndex(0);
		}
		if(e.getSource()==inHospital){
			if(inHospital.getSelectedIndex()==0){
				te10.setVisible(false);
				
				date2.setVisible(false);
				
			}
			if(inHospital.getSelectedIndex()==1){
				te10.setVisible(true);
				
				date2.setVisible(true);
				
			}
			if(inHospital.getSelectedIndex()==2){
				te10.setVisible(false);
				date2.setVisible(false);
			
			}
	    }
		if(e.getSource()==save){
			if(tx1.getText().trim().equals("")||tx2.getText().trim().equals("")||
			   date1.getText().equals("单击选择日期")||age.getSelectedIndex()==0||
			   sex.getSelectedIndex()==0||inHospital.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "请输入完整的病人信息！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx1.getText().length()<6||tx1.getText().length()>18||pattern.matcher(tx1.getText()).matches()==false){
				JOptionPane.showMessageDialog(null, "请输入正确的病人ID！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx6.getText().length()>10||tx7.getText().length()>10){
				JOptionPane.showMessageDialog(null, "出生地或籍贯不能超过10个汉字！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			else if(inHospital.getSelectedIndex()==1&&date2.getText().equals("单击选择日期")){
				
					JOptionPane.showMessageDialog(null, "请选择住院日期！", "错误", JOptionPane.ERROR_MESSAGE);
				
			}
			else{
				String PaId=tx1.getText();
				try{
					con=connect.getConnection();   //创建一个数据库连接
					Statement stmt=con.createStatement();   //创建一个数据库会话对象
					ResultSet rs=stmt.executeQuery("select * from Patient where PaId='"+PaId+"'");   //SQL语句
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "该病人已存在，请重新输入!", "错误", JOptionPane.ERROR_MESSAGE);
						rs.close();
					}
					else{
						int ok=JOptionPane.showConfirmDialog(null, "是否保存该病人信息？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
						if(ok==JOptionPane.YES_OPTION){
							//添加信息到数据库
							String sql="INSERT INTO Patient(PaId,PaName,PaBir,PaSex,PaAge,PaPlace,PaNative,PaYN,PaPay,PaIn) values (?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement parepare=con.prepareStatement(sql);
							parepare.setString(1, tx1.getText());
							parepare.setString(2, tx2.getText());
							parepare.setString(3, date1.getText());
							parepare.setString(4, sex.getSelectedItem().toString());
							parepare.setString(5, age.getSelectedItem().toString());
							parepare.setString(6, tx6.getText());
							parepare.setString(7, tx7.getText());
							parepare.setString(8, inHospital.getSelectedItem().toString());
							parepare.setString(9, tx9.getText());
							if(inHospital.getSelectedIndex()==1){
								parepare.setString(10, date2.getText());
								//parepare.setString(11, date3.getText());
							}
							else{
								parepare.setString(10,null);
								//parepare.setString(11,null);
							}
							
							parepare.executeUpdate();
							JOptionPane.showMessageDialog(null, "录入成功");
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}
}
