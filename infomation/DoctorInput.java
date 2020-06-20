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
	private JLabel inputTitle =new JLabel("ҽ��������Ϣ¼��");
	private JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;	
	private JTextField tx1,tx2,tx8,tx10;
	private JPasswordField tx11;
	public JButton save,clean;
	private JComboBox sex,age,position,educated,department;
	private String str3[],str4[],str5[],str6[],str7[];
	private final String columnNames[];
	private JTextField showDate1 = new JTextField("����ѡ������");
	private Timedate dateChooser1 = Timedate.getInstance("yyyy-MM-dd");
	private Font laFont=new Font("����",Font.BOLD,15);
	private JScrollPane JScrollPane1=new JScrollPane();
	private static JTable table;
	private static  DefaultTableModel dtm;
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	private Pattern pattern=Pattern.compile("[0-9]*"); 
	
	public DoctorInput(){
		//ҽ����Ϣ¼���������
		doctorInput.setLayout(null);
		ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg");  //����ͼƬ 
		JLabel label = new JLabel(background);
		inputTitle.setFont(new Font("����",Font.BOLD,50));
		inputTitle.setBounds(60, 10, 1000, 50);
		doctorInput.add(inputTitle);
		
		//¼������������
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "¼�����", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red));
		panel.setBounds(60, 310, 950,200);
		panel.setOpaque(false);
		
		la1=new JLabel("ҽ�����:");   //��ǩ����
		la2=new JLabel("ҽ������:");
		la3=new JLabel("�Ա�:");
		la4=new JLabel("����:");
		la5=new JLabel("ְ��:");
		la6=new JLabel("ѧ��:");
		la7=new JLabel("��������:");
		la8=new JLabel("����:");
		la9=new JLabel("��ְ����:");
		la10=new JLabel("�绰����:");
		la11=new JLabel("ϵͳ����:");
		
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
		
		save=new JButton("����");
		clean=new JButton("���");
		save.setBounds(630, 163, 150, 30);
		clean.setBounds(780, 163, 150, 30);
		
		//�Ա�����������
		str3=new String[]{"","��","Ů"};
	    sex=new JComboBox(str3);
	    sex.setBounds(620, 25, 100, 25);
	    sex.setFont(laFont);
	    
	    //��������������
		str4=new String[54];
		str4[0]="";
		for(int i=1;i<=53;i++){
			str4[i]=String.valueOf(i+17);
		}
		age=new JComboBox(str4);
		age.setBounds(800, 25, 100, 25);
		age.setFont(laFont);
		
		//ְ������������
		str5=new String[]{"","ҽʦ","����ҽʦ","������ҽʦ","����ҽʦ"};
		position=new JComboBox(str5);
		position.setBounds(80, 75, 100, 25);
		position.setFont(laFont);
		
		//ѧ������������
		str6=new String[]{"","��ר","����","˶ʿ","��ʿ","��ʿ��"};
		educated=new JComboBox(str6);
		educated.setBounds(260, 75, 100, 25);
		educated.setFont(laFont);
		
		//������������������
		str7=new String[]{""};
		department=new JComboBox(str7);
		try{
			con=connect.getConnection();   //����һ�����ݿ�����
			Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
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
		
		//��ְʱ������
		showDate1.setFont(new Font("",1,20));
		dateChooser1.register(showDate1);
		panel.add(showDate1);
		showDate1.setBounds(110, 125, 150, 30);
		
		//�������
		columnNames=new String[]{"ҽ�����","ҽ������","�Ա�","����","ְ��","ѧ��","��������","����","��ְ����","�绰����"};
		defaultTableModel();
		setTableColumnCenter();
		setbgcolor();
		JScrollPane1.setBounds(new  Rectangle(60,70,935,230));
		doctorInput.add(JScrollPane1);
		JScrollPane1.setViewportView(table);   //����һ���ӿڣ�����б�Ҫ������������ͼ
		table.getTableHeader().setReorderingAllowed(false);   //�в����϶�
		table.getTableHeader().setResizingAllowed(false);   //�п��ܸı�
		

		String	sql="select * from Doctor";
		databaseSearch(sql);
		
		
		
		//������
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
		
		//��Ӽ�����
		save.addActionListener(this);
		clean.addActionListener(this);
		
		
		doctorInput.add(panel);
		
		doctorInput.add(label);   //��ӱ���
		label.setBounds(0, 0, 1100, 700);
	}
	
	//���ݿ�����¼�뵽�����
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
	//���ñ�񲻿ɱ༭
	public void defaultTableModel(){
		dtm=new DefaultTableModel(columnNames,0);
		table=new JTable(dtm){
			  public boolean isCellEditable(int row, int column){
				  return false;
			  }
		  };
	}
	
	//���ñ�����ݾ�����ʾ
	public void setTableColumnCenter(){  
	    DefaultTableCellRenderer r = new DefaultTableCellRenderer();     
	    r.setHorizontalAlignment(JLabel.CENTER);     
	    table.setDefaultRenderer(Object.class, r);  
	}

	//���ñ����б�����ɫ��ͬ
	public static void setbgcolor(){
		try{
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer(){
				public Component getTableCellRendererComponent(JTable table,
		                  Object value, boolean isSelected, boolean hasFocus,
		                  int row, int column){
					if(row%2 == 0)
						setBackground(new Color(223,220,239));   //���������е�ɫ
					else if(row%2 == 1)
						setBackground(Color.white);    //����ż���е�ɫ
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
			showDate1.setText("����ѡ������");
		}
		if(e.getSource()==save){
			if(tx1.getText().equals("")||tx2.getText().equals("")||tx8.getText().equals("")||
			   tx10.getText().equals("")||tx11.getText().equals("")||sex.getSelectedIndex()==0||
			   age.getSelectedIndex()==0||position.getSelectedIndex()==0||educated.getSelectedIndex()==0||
			   department.getSelectedIndex()==0||showDate1.getText().equals("����ѡ������")){
				JOptionPane.showMessageDialog(null, "����������ҽ����Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx1.getText().length()!=4||pattern.matcher(tx1.getText()).matches()==false){
				JOptionPane.showMessageDialog(null, "������4λ��ҽ����ţ�", "����", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx2.getText().length()>10){
				JOptionPane.showMessageDialog(null, "���ֳ��Ȳ��ܳ���10�����֣�", "����", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx8.getText().length()>10){
				JOptionPane.showMessageDialog(null, "���᳤�Ȳ��ܳ���10�����֣�", "����", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx10.getText().length()!=11||pattern.matcher(tx10.getText()).matches()==false){
				JOptionPane.showMessageDialog(null, "������11λ���ֻ����룡", "����", JOptionPane.ERROR_MESSAGE);
			}
			else if(tx11.getText().length()<6||tx11.getText().length()>8||pattern.matcher(tx11.getText()).matches()==false){
				JOptionPane.showMessageDialog(null, "������6-8λ��������!", "����", JOptionPane.ERROR_MESSAGE);
			}
			else{
				String DrId=tx1.getText();
				try{
					con=connect.getConnection();   //����һ�����ݿ�����
					Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
					ResultSet rs=stmt.executeQuery("select * from Doctor where DrId='"+DrId+"'");   //SQL���
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "��ҽ������Ѵ��ڣ�����������!", "����", JOptionPane.ERROR_MESSAGE);
						rs.close();
					}
					else{
						int ok=JOptionPane.showConfirmDialog(null, "�Ƿ񱣴��ҽ����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
						if(ok==JOptionPane.YES_OPTION){
							//�����Ϣ�����ݿ�
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
							dtm.addRow(data);   //�ڱ�����һ�и���ӵ�����
							JOptionPane.showMessageDialog(null, "¼��ɹ�");
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
							showDate1.setText("����ѡ������");
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}

}
