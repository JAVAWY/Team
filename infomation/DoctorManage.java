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
	private JLabel manageTitle=new JLabel("ҽ��������Ϣ����");
	private final String []columnNames={"ҽ�����","ҽ������","�Ա�","����","ְ��","ѧ��","��������","����","��ְ����","�绰����"};
	private static JTable table;
	private static  DefaultTableModel dtm;
	private JScrollPane tableScroll=new JScrollPane();
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	 private JLabel la1,la2;
	 private JTextField tx1;
	 Font f1=new Font("����", Font.CENTER_BASELINE,25);
	 String o;
	public DoctorManage(){
		//��������
		doctorManage.setLayout(null);
		ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg"); 
		JLabel label = new JLabel(background);
		
		//��������
		manageTitle.setFont(new Font("����",Font.BOLD,50));
		manageTitle.setBounds(60, 10, 1000, 50);
		doctorManage.add(manageTitle);
		
		//��ѯ����������
		final JPanel queryPanel=new JPanel();
		queryPanel.setLayout(null);
		queryPanel.setBorder(new TitledBorder(null, "��ѡ����Ҫ���еĲ���", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,null, Color.red));
		queryPanel.setBounds(70, 70, 590, 80);
		queryPanel.setOpaque(false);

	//��ǩ����
		la1=new JLabel("ҽ������ͳ�ƣ�");
		la1.setFont(f1);
		la2=new JLabel("��");
		la2.setFont(f1);
		
		
		tx1=new JTextField();
		//��ť����
		query=new JButton("������ѯ ");
		query.setBounds(90, 95, 100,45);
		queryAll=new JButton("ȫ����ѯ");
		queryAll.setBounds(240, 95, 100,45);
		modify=new JButton("�޸�");
		modify.setBounds(390, 95, 100,45);
		delete=new JButton("ɾ��");
		delete.setBounds(540, 95,100,45);
        la1.setBounds(700, 60, 200, 45);
        tx1.setBounds(700, 105, 50, 35);
        tx1.setEditable(false);
        la2.setBounds(760, 98, 50, 50);
		//�������
		tableScroll.setBounds(new  Rectangle(70,150,935,355)); 
		defaultTableModel();   
		setTableColumnCenter();
		setbgcolor();
		doctorManage.add(tableScroll);
		tableScroll.setViewportView(table);
		table.getTableHeader().setReorderingAllowed(false);   //�в����϶�
		table.getTableHeader().setResizingAllowed(false);   //�п��ܸı�
		
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
		
		
		
		//������
		doctorManage.add(la2);
		doctorManage.add(la1);
		doctorManage.add(tx1);
		doctorManage.add(delete);
		doctorManage.add(modify);
		doctorManage.add(query);
		doctorManage.add(queryAll);
		doctorManage.add(queryPanel);
		
		//��Ӽ�����
		query.addActionListener(this);
		delete.addActionListener(this);
		modify.addActionListener(this);
		queryAll.addActionListener(this);
		
		
		
		String	sql="select * from Doctor";
		databaseSearch(sql);
		
		//��ӱ���
		doctorManage.add(label);
		label.setBounds(0, 0, 1100, 700);
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
	
	//�����ݿ����ݴ�����
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
		if(e.getSource()==modify){   //�޸Ĳ�������
			if(table.getSelectedRowCount()!=1){
				JOptionPane.showMessageDialog(null, "��ѡ��һ��ҽ����Ϣ�����޸ģ�", "����", JOptionPane.ERROR_MESSAGE);
			}
			else{
				try{
					con=connect.getConnection();   //����һ�����ݿ�����
					Statement stmt1=con.createStatement();   //����һ�����ݿ�Ự����
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
		if(e.getSource()==query){    //������ѯ��������
			DoctorQuery dig=new DoctorQuery();
			dig.s.setVisible(true);
		}
		if(e.getSource()==queryAll){   //ȫ����ѯ��������
			try{
				con=connect.getConnection();   //����һ�����ݿ�����
				String sql="select * from Doctor";
				databaseSearch(sql);
				if(table.getRowCount()!=0){
					JOptionPane.showMessageDialog(null, "��ѯ�ɹ���");
				}
				else{
					JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ����Ϣ��");
				}
				
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		if(e.getSource()==delete){   //ɾ����������
			try{
				con=connect.getConnection();   //����һ�����ݿ�����
				Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
				int selectCount=table.getSelectedRowCount();
				if(selectCount==0){
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ������Ϣ��");
				}
				else if(selectCount==1){
					int ok=JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ����ҽ����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						int row=table.getSelectedRow();
						String drId=(String) table.getValueAt(row, 0);
						String sql="delete from Doctor where DrId='"+drId+"'";
						stmt.executeUpdate(sql);
						dtm.removeRow(row);
						JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					}
				}
				else{
					int ok=JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ����ѡ"+selectCount+"��ҽ����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						for(int i=1;i<=selectCount;i++){
							int row1=table.getSelectedRow();
							String drId=(String)table.getValueAt(row1, 0);
							String sql="delete from Doctor where DrId='"+drId+"'";
							stmt.executeUpdate(sql);
							dtm.removeRow(row1);
						}
						JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
	}
	
	

	//ҽ����Ϣ�޸ĶԻ�������+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class DoctorModify implements ActionListener{
		private JDialog s=new JDialog();
		private JLabel la0,la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;
		private JTextField tx1,tx2,tx8,tx10,txWait;
		private JPasswordField tx11;
		private JButton confirm,cancel;
		private Font laFont=new Font("����",Font.BOLD,15);
		private JComboBox sex,age,position,educated,department;
		private String str3[],str4[],str5[],str6[],str7[];
		private JTextField showDate1 = new JTextField("����ѡ������");
		private Timedate dateChooser1 = Timedate.getInstance("yyyy-MM-dd");
		private Pattern pattern=Pattern.compile("[0-9]*"); 
		private java.sql.Connection con=null;
		
		private int row=table.getSelectedRow();
		Font f2=new Font("����", Font.BOLD, 25);
		public DoctorModify(){
		
			ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg"); 
			JLabel label = new JLabel(background);
			s.setModal(true);
			s.setSize(950,300);
			s.setResizable(false);
			s.setLocationRelativeTo(null);
			s.setLayout(null);
			la0=new JLabel("ҽ����Ϣ�޸�");  
			la0.setFont(f2);
			la1=new JLabel("ҽ�����:");   //s��ǩ����
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
			tx1=new JTextField();
			tx1.setEditable(false);
			tx2=new JTextField();   
			tx8=new JTextField();   
			tx10=new JTextField();   
			tx11=new JPasswordField(); 
			txWait=new JTextField();
			confirm=new JButton("ȷ��");
			cancel=new JButton("ȡ��");
			
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
			
			//�Ա�����������
			str3=new String[]{"","��","Ů"};
		    sex=new JComboBox(str3);
		    sex.setBounds(620, 65, 100, 25);
		    sex.setFont(laFont);
		    
		    //��������������
			str4=new String[54];
			str4[0]="";
			for(int i=1;i<=53;i++){
				str4[i]=String.valueOf(i+17);
			}
			age=new JComboBox(str4);
			age.setBounds(800, 65, 100, 25);
			age.setFont(laFont);
			
			//ְ������������
			str5=new String[]{"","ҽʦ","����ҽʦ","������ҽʦ","����ҽʦ"};
			position=new JComboBox(str5);
			position.setBounds(80, 115, 100, 25);
			position.setFont(laFont);
			
			//ѧ������������
			str6=new String[]{"","��ר","����","˶ʿ","��ʿ","��ʿ��"};
			educated=new JComboBox(str6);
			educated.setBounds(260, 115, 100, 25);
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
			department.setBounds(470, 115, 125, 25);
			department.setFont(laFont);
			
			//��ְʱ������
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
			
			//��Ӽ�����
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
				int ok=JOptionPane.showConfirmDialog(null, "�Ƿ��޸ĸ�ҽ����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(ok==JOptionPane.YES_OPTION){
					try{
						con=connect.getConnection();   //����һ�����ݿ�����
						Statement stmt1=con.createStatement();   //����һ�����ݿ�Ự����
						Statement stmt2=con.createStatement();   //����һ�����ݿ�Ự����
						
						String SQL2=("update Medical_records set DrName='"+tx2.getText()+"',DeptName='"+department.getSelectedItem().toString()+"'where DrId='"+tx1.getText()+"'");
						
						//��������
						if(tx1.getText().equals("")||tx2.getText().equals("")||tx8.getText().equals("")||
						   tx10.getText().equals("")||tx11.getText().equals("")||sex.getSelectedIndex()==0||
						   age.getSelectedIndex()==0||position.getSelectedIndex()==0||educated.getSelectedIndex()==0||
						   department.getSelectedIndex()==0||showDate1.getText().equals("����ѡ������")){
							JOptionPane.showMessageDialog(null, "���������Ƶ�ҽ����Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
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
								String sql="update Doctor set DrName='"+tx2.getText()+"',DrSex='"+sex.getSelectedItem().toString()+"',DrAge='"+age.getSelectedItem().toString()+"',DrPos='"+position.getSelectedItem().toString()+"',"
										+ "DrEdu='"+educated.getSelectedItem().toString()+"',DeptName='"+department.getSelectedItem().toString()+"',DrNative='"+tx8.getText()+"',DrDate='"+showDate1.getText()+"',DrPhone='"+tx10.getText()+"',"
												+ "Password='"+tx11.getText()+"' where DrId='"+tx1.getText()+"'";
								
								stmt1.executeUpdate(sql);
								int row2=table.getSelectedRow();
								String []data = new String[]{tx1.getText(),tx2.getText(),sex.getSelectedItem().toString(),age.getSelectedItem().toString(),position.getSelectedItem().toString(),educated.getSelectedItem().toString(),department.getSelectedItem().toString(),tx8.getText(),showDate1.getText(),tx10.getText()};
								dtm.removeRow(row2);
								dtm.insertRow(row2, data);
								
		          				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
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
	
	//ҽ����Ϣ��ѯ�Ի�������+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class DoctorQuery implements ActionListener{
		
		JDialog s=new JDialog();
		private JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;
		private JTextField tx1,tx2,tx9;
		private JComboBox jcbb3,jcbb4,jcbb5,jcbb6,jcbb7,jcbb8;
		private JButton query,cancel;
		private String str3[],str4[],str5[],str6[],str7[],str8[];
		private JLabel title=new JLabel("����������Ҫ��ѯ��������Ϣ��");
		private Font laFont=new Font("����",Font.BOLD,15);
		private JTextField date10 = new JTextField("����ѡ������");
		private JTextField date12 = new JTextField("����ѡ������");
		private Timedate dateChooser10 = Timedate.getInstance("yyyy-MM-dd");
		private Timedate dateChooser12 = Timedate.getInstance("yyyy-MM-dd");
		
		public DoctorQuery(){
			ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg");  //����ͼƬ 
			JLabel label = new JLabel(background);
			title.setFont(new Font("����",Font.BOLD,30));
			title.setBounds(30, 10, 900, 50);
			s.add(title);
			
			la1=new JLabel("���:");
			la2=new JLabel("����:");
			la3=new JLabel("�Ա�:");
			la4=new JLabel("����:");
			la5=new JLabel("��");
			la6=new JLabel("ְ��:");
			la7=new JLabel("ѧ��:");
			la8=new JLabel("��������:");
			la9=new JLabel("����:");
			la10=new JLabel("��ְ����:");
			la11=new JLabel("��");
			query=new JButton("��ѯ");
			cancel=new JButton("ȡ��");
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
			str3=new String[]{"","��","Ů"};
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
			str6=new String[]{"","ҽʦ","����ҽʦ","������ҽʦ","����ҽʦ"};
			jcbb6=new JComboBox(str6);
			jcbb6.setBounds(120, 265, 120, 25);
			jcbb6.setFont(laFont);
			
			la7.setBounds(40, 310, 100, 40);
			la7.setFont(laFont);
			str7=new String[]{"","��ר","����","˶ʿ","��ʿ","��ʿ��"};
			jcbb7=new JComboBox(str7);
			jcbb7.setBounds(120, 315, 120, 25);
			jcbb7.setFont(laFont);
			
			la8.setBounds(40, 360, 100, 40);
			la8.setFont(laFont);
			str8=new String[]{""};
			jcbb8=new JComboBox(str8);
			try{
				con=connect.getConnection();   //����һ�����ݿ�����
				Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
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
			
			//��Ӽ�����
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
				   date10.getText().trim().equals("����ѡ������")&&date12.getText().trim().equals("����ѡ������")){
					JOptionPane.showMessageDialog(null, "��������Ҫ��ѯ��������Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
				}
				else if(!tx1.getText().trim().equals("")){
					try{
						con=connect.getConnection();   //����һ�����ݿ�����
						//Statement st1=con.createStatement();   //����һ�����ݿ�Ự����
						String drId=tx1.getText().trim();
						String sql1="select * from Doctor where DrId='"+drId+"'";
						databaseSearch(sql1);
						if(table.getRowCount()!=0){
							JOptionPane.showMessageDialog(null, "��ѯ�ɹ���");
						}
						else{
							JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ����Ϣ��");
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
								JOptionPane.showMessageDialog(null, "���䷶Χ�밴С����ѡ��", "����", JOptionPane.ERROR_MESSAGE);
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
						if(!drDate1.equals("����ѡ������")&&!drDate2.equals("����ѡ������")){
							try{
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								Date dt1 = df.parse(date10.getText());
					            Date dt2 = df.parse(date12.getText());
					            if(dt1.getTime()>dt2.getTime()){
					            	JOptionPane.showMessageDialog(null, "���ڷ�Χ�밴�ȵ���ѡ��", "����", JOptionPane.ERROR_MESSAGE);
					            }
					            else {
					            	sql+=" and DrDate between '"+drDate1+"' and '"+drDate2+"'";
					            }
							}catch(Exception e1){
								e1.printStackTrace();
							}
						}
						if(!drDate1.equals("����ѡ������")&&drDate2.equals("����ѡ������")){
							sql+=" and DrDate='"+drDate1+"'";
						}
						if(drDate1.equals("����ѡ������")&&!drDate2.equals("����ѡ������")){
							sql+=" and DrDate='"+drDate2+"'";
						}
						if(!drName.equals("")||!drSex.equals("")||!drAge1.equals("")||!drAge2.equals("")||
						   !drPos.equals("")||!drEdu.equals("")||!drDept.equals("")||!drNative.equals("")||
						   !drDate1.equals("����ѡ������")||!drDate2.equals("����ѡ������")){
							con=connect.getConnection();   //����һ�����ݿ�����
							databaseSearch(sql);
							if(table.getRowCount()!=0){
								JOptionPane.showMessageDialog(null, "��ѯ�ɹ���");
							}
							else{
								JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ����Ϣ��");
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
