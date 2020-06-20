import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
public class PatientManage extends JFrame implements ActionListener{
	JPanel patientManage=new JPanel();;
	public JButton queryAll,query,modify,delete;
	private JLabel manageTitle=new JLabel("���˵�����Ϣ����");
	private final String []columnNames={"ID","����","��������","�Ա�","����","������","����","�Ƿ�סԺ","Ԥ������","סԺʱ��"};
	private static JTable table;
	private static  DefaultTableModel dtm;
	private JScrollPane tableScroll=new JScrollPane();
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	public PatientManage(){
		//��������
		patientManage.setLayout(null);
		ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg"); 
		JLabel label = new JLabel(background);
		
		//��������
		manageTitle.setFont(new Font("����",Font.BOLD,50));
		manageTitle.setBounds(60, 10, 1000, 50);
		patientManage.add(manageTitle);
		
		//��ѯ����������
		final JPanel queryPanel=new JPanel();
		queryPanel.setLayout(null);
		queryPanel.setBorder(new TitledBorder(null, "��ѡ����Ҫ���еĲ���", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,null, Color.red));
		queryPanel.setBounds(70, 70, 590, 80);
		queryPanel.setOpaque(false);
		
		//��ť����
		query=new JButton("������ѯ ");
		query.setBounds(90, 95, 100,45);
		queryAll=new JButton("ȫ����ѯ");
		queryAll.setBounds(240, 95, 100,45);
		modify=new JButton("�޸�");
		modify.setBounds(390, 95, 100,45);
		delete=new JButton("ɾ��");
		delete.setBounds(540, 95,100,45);

		//�������
		tableScroll.setBounds(new  Rectangle(70,150,935,355)); 
		defaultTableModel();   
		setTableColumnCenter();
		setbgcolor();
		patientManage.add(tableScroll);
		tableScroll.setViewportView(table);
		table.getTableHeader().setReorderingAllowed(false);   //�в����϶�
		table.getTableHeader().setResizingAllowed(false);   //�п��ܸı�
		
		//������
		patientManage.add(delete);
		patientManage.add(queryAll);
		patientManage.add(modify);
		patientManage.add(query);
		patientManage.add(queryPanel);
		
		//��Ӽ�����
		query.addActionListener(this);
		queryAll.addActionListener(this);
		delete.addActionListener(this);
		modify.addActionListener(this);
		
		//��ӱ���
		patientManage.add(label);
		label.setBounds(0, 0, 1100, 700);
		
		table.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
			   public void mouseClicked(MouseEvent e) {
				   if (e.getButton() == MouseEvent.BUTTON1) // ����������
				     if (e.getClickCount() == 2) { 
				    	
				    	 try{
								con=connect.getConnection();   //����һ�����ݿ�����
								Statement stmt1=con.createStatement();   //����һ�����ݿ�Ự����
								Statement stmt2=con.createStatement();  
								int row=table.getSelectedRow();
								String paId=(String) table.getValueAt(row, 0);
								String sql="select * from Patient where PaId='"+paId+"'";
								ResultSet rs=stmt1.executeQuery(sql);
								rs.next();
								PatientModify dig=new PatientModify();
								dig.tx1.setText(rs.getString("PaId"));
								dig.tx2.setText(rs.getString("PaName"));
								dig.date1.setText(rs.getString("PaBir"));
								dig.jcbb4.setSelectedItem(rs.getString("PaSex"));
								dig.jcbb5.setSelectedItem(rs.getString("PaAge"));
								dig.tx6.setText(rs.getString("PaPlace"));
								dig.tx7.setText(rs.getString("PaNative"));
								dig.jcbb8.setSelectedItem(rs.getString("PaYN"));
								dig.tx9.setText(rs.getString("PaPay"));
								dig.date2.setText(rs.getString("PaIn"));
								
								dig.txWait.setText(paId);
								dig.s.setVisible(true);
							}catch(Exception e1){
								e1.printStackTrace();
							}
				    
				     }    
				   }			   
				  });
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
			String[] data = new String[10];
			while (rs.next()){
				for (int j = 1; j <= 10; j++){
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
		if(e.getSource()==queryAll){   //ȫ����ѯ��������
			try{
				con=connect.getConnection();   //����һ�����ݿ�����
				Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
				String sql="select * from Patient";
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
					int ok=JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ���ò�����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						int row=table.getSelectedRow();
						String paId=(String) table.getValueAt(row, 0);
						String sql="delete from Patient where PaId='"+paId+"'";
						stmt.executeUpdate(sql);
						dtm.removeRow(row);
						JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					}
				}
				else{
					int ok=JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ����ѡ"+selectCount+"��������Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						for(int i=1;i<=selectCount;i++){
							int row1=table.getSelectedRow();
							String paId=(String)table.getValueAt(row1, 0);
							String sql="delete from Patient where PaId='"+paId+"'";
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
		if(e.getSource()==modify){   //�޸Ĳ�������
			if(table.getSelectedRowCount()!=1){
				JOptionPane.showMessageDialog(null, "��ѡ��һ�����Ϣ�����޸ģ�", "����", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try{
					con=connect.getConnection();   //����һ�����ݿ�����
					Statement stmt1=con.createStatement();   //����һ�����ݿ�Ự����
					Statement stmt2=con.createStatement();  
					int row=table.getSelectedRow();
					String paId=(String) table.getValueAt(row, 0);
					String sql="select * from Patient where PaId='"+paId+"'";
					ResultSet rs=stmt1.executeQuery(sql);
					rs.next();
					PatientModify dig=new PatientModify();
					dig.tx1.setText(rs.getString("PaId"));
					dig.tx2.setText(rs.getString("PaName"));
					dig.date1.setText(rs.getString("PaBir"));
					dig.jcbb4.setSelectedItem(rs.getString("PaSex"));
					dig.jcbb5.setSelectedItem(rs.getString("PaAge"));
					dig.tx6.setText(rs.getString("PaPlace"));
					dig.tx7.setText(rs.getString("PaNative"));
					dig.jcbb8.setSelectedItem(rs.getString("PaYN"));
					dig.tx9.setText(rs.getString("PaPay"));
					dig.date2.setText(rs.getString("PaIn"));
					
					dig.txWait.setText(paId);
					dig.s.setVisible(true);
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}	
		}
		if(e.getSource()==query){
			PatientQuery dig=new PatientQuery();
			dig.s.setVisible(true);
		}
	}
	
	//������Ϣ�޸ĶԻ�������++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		class PatientModify implements ActionListener{

			private JDialog s=new JDialog();
			private JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11;
			private JTextField tx1,tx2,tx6,tx7,tx9,txWait;
			private JComboBox jcbb4,jcbb5,jcbb8;
			private JButton confirm,cancel;
			private Timedate dateChooser1,dateChooser2,dateChooser3;
			private JTextField date1,date2;
			private String str4[],str5[],str8[];
			private Font laFont=new Font("����",Font.BOLD,15);
			private java.sql.Connection con=null;
			 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
			private Pattern pattern=Pattern.compile("[0-9]*"); 
			private int row=table.getSelectedRow();
			
			public PatientModify(){
				ImageIcon background = new ImageIcon("image/right_bg.jpg"); 
				JLabel label = new JLabel(background);
				s.setModal(true);
				s.setSize(500,650);
				s.setResizable(false);
				s.setLocationRelativeTo(null);
				s.setLayout(null);
				
				la1=new JLabel("����ID:");
				la2=new JLabel("����:");
				la3=new JLabel("��������:");
				la4=new JLabel("�Ա�:");
				la5=new JLabel("����:");
				la6=new JLabel("������:");
				la7=new JLabel("����:");	
				la8=new JLabel("�Ƿ�סԺ:");
				la9=new JLabel("Ԥ������:");
				la10=new JLabel("סԺʱ��:");
				
				tx1=new JTextField();
				tx2=new JTextField();
				tx6=new JTextField();
				tx7=new JTextField();
				tx9=new JTextField();
				txWait=new JTextField();
				confirm=new JButton("ȷ��");
				cancel=new JButton("ȡ��");
				tx1.setEditable(false);
				//������������
				dateChooser1= Timedate.getInstance("yyyy-MM-dd");
				date1= new JTextField("����ѡ������");
		        dateChooser1.register(date1);
			    s.add(date1);
				
				//�Ա�����������
				str4=new String[]{"","��","Ů"};
				jcbb4=new JComboBox(str4);
				jcbb4.setFont(laFont);
				
				//��������������
				str5=new String[132];
				str5[0]="";
				str5[1]="0.5";
				for(int i=2;i<=131;i++){
					str5[i]=String.valueOf(i-1);
				}
				jcbb5=new JComboBox(str5);
				jcbb5.setFont(laFont);
				
				//סԺ�������
				str8=new String[]{"","��","��"};
				jcbb8=new JComboBox(str8);
				jcbb8.setFont(laFont);
				jcbb8.addActionListener(this);
				
				//סԺʱ������
				dateChooser2= Timedate.getInstance("yyyy-MM-dd");
				date2= new JTextField("����ѡ������");
		        dateChooser2.register(date2);
			    s.add(date2);
			    
				//��Ժʱ������
			   
				
				la1.setBounds(30, 20, 100, 40);
				la1.setFont(laFont);
				la2.setBounds(30, 70, 100, 40);
				la2.setFont(laFont);
				la3.setBounds(30, 120, 100, 40);
				la3.setFont(laFont);
				la4.setBounds(30, 170, 100, 40);
				la4.setFont(laFont);
				la5.setBounds(30, 220, 100, 40);
				la5.setFont(laFont);
				la6.setBounds(30, 270, 100, 40);
				la6.setFont(laFont);
				la7.setBounds(30, 320, 100, 40);
				la7.setFont(laFont);
				la8.setBounds(30, 370, 100, 40);
				la8.setFont(laFont);
				la9.setBounds(30, 420, 100, 40);
				la9.setFont(laFont);
				la10.setBounds(30, 470, 100, 40);
				la10.setFont(laFont);

				
				tx1.setBounds(130, 25, 120, 25);
				tx1.setFont(laFont);
				tx2.setBounds(130, 75, 120, 25);
				tx2.setFont(laFont);
				date1.setBounds(130, 125, 120, 25);
				date1.setFont(laFont);
				jcbb4.setBounds(130, 175, 120, 25);
				jcbb4.setFont(laFont);
				jcbb5.setBounds(130, 225, 120, 25);
				jcbb5.setFont(laFont);
				tx6.setBounds(130, 275, 120, 25);
				tx6.setFont(laFont);
				tx7.setBounds(130, 325, 120, 25);
				tx7.setFont(laFont);
				jcbb8.setBounds(130, 375, 120, 25);
				jcbb8.setFont(laFont);
				tx9.setBounds(130, 425, 120, 25);
				tx9.setFont(laFont);
				date2.setBounds(130, 475, 120, 25);
				date2.setFont(laFont);
				
				confirm.setBounds(110, 575, 100, 40);
				cancel.setBounds(280, 575, 100, 40);
				confirm.addActionListener(this);
				cancel.addActionListener(this);
				
				la10.setVisible(false);

				date2.setVisible(false);
				
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

			    s.add(tx1);
			    s.add(tx2);
			    s.add(date1);
			    s.add(jcbb4);
			    s.add(jcbb5);
			    s.add(tx6);
			    s.add(tx7);
			    s.add(jcbb8);
			    s.add(tx9);
			    s.add(date2);
			   
			    s.add(confirm);
			    s.add(cancel);
			    
				
				label.setBounds(0, 0, 500, 650);
				s.add(label);
			}
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==cancel){
					s.dispose();
				}
				if(e.getSource()==jcbb8){
					if(jcbb8.getSelectedIndex()==0){
						la10.setVisible(false);
						
						date2.setVisible(false);
						
					}
					if(jcbb8.getSelectedIndex()==1){
						la10.setVisible(true);

						date2.setVisible(true);
						
					}
					if(jcbb8.getSelectedIndex()==2){
						la10.setVisible(false);
						
						date2.setVisible(false);
						
					}
				}
				if(e.getSource()==confirm){
					String wait=txWait.getText();
					int ok=JOptionPane.showConfirmDialog(null, "�Ƿ��޸ĸò�����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						try{
							
							
							if(tx1.getText().trim().equals("")||tx2.getText().trim().equals("")||
							   date1.getText().equals("����ѡ������")||jcbb4.getSelectedIndex()==0||
							   jcbb5.getSelectedIndex()==0||jcbb8.getSelectedIndex()==0){
								JOptionPane.showMessageDialog(null, "�����������Ĳ�����Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
							}
							else if(tx1.getText().length()<6||tx1.getText().length()>18||pattern.matcher(tx1.getText()).matches()==false){
								JOptionPane.showMessageDialog(null, "��������ȷ�Ĳ���ID��", "����", JOptionPane.ERROR_MESSAGE);
							}
							else if(tx6.getText().length()>10||tx7.getText().length()>10){
								JOptionPane.showMessageDialog(null, "�����ػ򼮹᲻�ܳ���10�����֣�", "����", JOptionPane.ERROR_MESSAGE);
							}
							else if(jcbb8.getSelectedIndex()==1&&date2.getText().equals("����ѡ������"))
							{
									JOptionPane.showMessageDialog(null, "��ѡ��סԺ�ͳ�Ժ���ڣ�", "����", JOptionPane.ERROR_MESSAGE);
								
							}
							else{
									con=connect.getConnection();   //����һ�����ݿ�����
									Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
									
									String SQL=("update  Patient set PaName='"+tx2.getText()+"',PaBir='"+date1.getText()+"',PaSex='"+jcbb4.getSelectedItem().toString()+"',PaAge='"+jcbb5.getSelectedItem().toString()+"',PaPlace='"+tx6.getText()+"',PaNative='"+tx7.getText()+"',PaYN='"+jcbb8.getSelectedItem().toString()+"',PaPay='"+tx9.getText()+"',PaIn='"+date2.getText()+"'where PaId='"+tx1.getText()+"'");   //SQL���
                                   Statement stmt1=con.createStatement();   //����һ�����ݿ�Ự����
									
									String SQL1=("update Medical_records set PaName='"+tx2.getText()+"'where PaId='"+tx1.getText()+"'");
									try {
					     				stmt = con.createStatement();
					     				stmt.executeUpdate(SQL);
					     				 int row2=table.getSelectedRow();
					      				String []xiugai=new String []{tx1.getText(),tx2.getText(),date1.getText(),jcbb4.getSelectedItem().toString(),jcbb5.getSelectedItem().toString(),tx6.getText(),tx7.getText(),jcbb8.getSelectedItem().toString(),tx9.getText(),date2.getText()};

					      					JOptionPane.showMessageDialog(null,"�޸ĳɹ�","�޸ĳɹ�", JOptionPane.INFORMATION_MESSAGE, null);
					      					dtm.removeRow(row2);
					      					dtm.insertRow(row2, xiugai);	
					      					
					      					dtm.fireTableDataChanged();
					      					
					      					 s.dispose();
					      					try {
							     				stmt1 = con.createStatement();
							     				stmt1.executeUpdate(SQL1);
							    
					      					}
					      					catch(Exception e5){
					      						e5.printStackTrace();
					      					}
					      			}catch (SQLException e1) {
					      				// TODO Auto-generated catch block
					      				e1.printStackTrace();
					      			}
					  			
							}
						}catch (SQLException e2) {
		      				// TODO Auto-generated catch block
		      				e2.printStackTrace();
		      			}
					}
				}
			}
		}
		
	
		
	//������Ϣ������ѯ�Ի�������+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class PatientQuery implements ActionListener{
		
		JDialog s=new JDialog();
		private JLabel title=new JLabel("����������Ҫ��ѯ��������Ϣ��");
		private Font laFont=new Font("����",Font.BOLD,15);
		private JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11,la12;
		private JComboBox jcbb5,jcbb6,jcbb7,jcbb8;
		private String str5[],str6[],str8[];
		private JButton query,cancel;
		private JTextField tx1,tx2;
		private JTextField date3,date4,date9,date10,date11,date12;
		private Timedate dateChooser3,dateChooser4,dateChooser9,dateChooser10,dateChooser11,dateChooser12;
		
		public PatientQuery(){
			ImageIcon background = new ImageIcon("image/right_bg.jpg");  //����ͼƬ 
			JLabel label = new JLabel(background);
			title.setFont(new Font("����",Font.BOLD,30));
			title.setBounds(30, 10, 900, 50);
			s.add(title);
			
			la1=new JLabel("ID:");
			la2=new JLabel("����:");
			la3=new JLabel("��������:");
			la4=new JLabel("��");
			la5=new JLabel("�Ա�:");
			la6=new JLabel("����:");
			la7=new JLabel("��");
			la8=new JLabel("�Ƿ�סԺ:");
			la9=new JLabel("סԺʱ��:");
			la10=new JLabel("��");
			la11=new JLabel("סԺʱ��:");
			la12=new JLabel("��");
			tx1=new JTextField();
			tx2=new JTextField();
			date3= new JTextField("����ѡ������");
			date4= new JTextField("����ѡ������");
			date9= new JTextField("����ѡ������");
			date10= new JTextField("����ѡ������");
			date11= new JTextField("����ѡ������");
			date12= new JTextField("����ѡ������");
			dateChooser3=Timedate.getInstance("yyyy-MM-dd");
			dateChooser4=Timedate.getInstance("yyyy-MM-dd");
			dateChooser9=Timedate.getInstance("yyyy-MM-dd");
			dateChooser10=Timedate.getInstance("yyyy-MM-dd");
			dateChooser11=Timedate.getInstance("yyyy-MM-dd");
			dateChooser12=Timedate.getInstance("yyyy-MM-dd");
			query=new JButton("��ѯ");
			cancel=new JButton("ȡ��");
			
			//ID������
			la1.setBounds(40, 60, 100, 40);
			la1.setFont(laFont);
			tx1.setBounds(120, 65, 120, 25);
			tx1.setFont(laFont);
			la2.setBounds(40, 110, 100, 40);
			la2.setFont(laFont);
			tx2.setBounds(120, 115, 120, 25);
			tx2.setFont(laFont);
			
			//��������
			la3.setBounds(40, 160, 100, 40);
			la3.setFont(laFont);
			date3.setBounds(120, 165, 120, 25);
			dateChooser3.register(date3);
			date3.setFont(laFont);
			la4.setBounds(250, 160, 100, 40);
			la4.setFont(laFont);
			date4.setBounds(280, 165, 120, 25);
			dateChooser4.register(date4);
			date4.setFont(laFont);
			
			//�Ա�
			la5.setBounds(40, 210, 100, 40);
			la5.setFont(laFont);
			str5=new String[]{"","��","Ů"};
			jcbb5=new JComboBox(str5);
			jcbb5.setFont(laFont);
			jcbb5.setBounds(120, 215, 120, 25);
			
			//����
			la6.setBounds(40, 260, 100, 40);
			la6.setFont(laFont);
			str6=new String[132];
			str6[0]="";
			str6[1]="0.5";
			for(int i=2;i<=131;i++){
				str6[i]=String.valueOf(i-1);
			}
			jcbb6=new JComboBox(str6);
			jcbb6.setFont(laFont);
			jcbb6.setBounds(120, 265, 120, 25);
			la7.setBounds(250, 260, 100, 40);
			la7.setFont(laFont);
			jcbb7=new JComboBox(str6);
			jcbb7.setFont(laFont);
			jcbb7.setBounds(280, 265, 120, 25);
			
			//�Ƿ�סԺ
			la8.setBounds(40, 310, 100, 40);
			la8.setFont(laFont);
			str8=new String[]{"","��","��"};
			jcbb8=new JComboBox(str8);
			jcbb8.setFont(laFont);
			jcbb8.setBounds(120, 315, 120, 25);
			
			//סԺʱ��
			la9.setBounds(40, 360, 100, 40);
			la9.setFont(laFont);
			dateChooser9.register(date9);
			date9.setFont(laFont);
			date9.setBounds(120, 365, 120, 25);
			la10.setBounds(250, 360, 100, 40);
			la10.setFont(laFont);
			dateChooser10.register(date10);
			date10.setFont(laFont);
			date10.setBounds(280, 365, 120, 25);
			
			//��Ժʱ��
			la11.setBounds(40, 410, 100, 40);
			la11.setFont(laFont);
			dateChooser11.register(date11);
			date11.setFont(laFont);
			date11.setBounds(120, 415, 120, 25);
			la12.setBounds(250, 410, 100, 40);
			la12.setFont(laFont);
			dateChooser12.register(date12);
			date12.setFont(laFont);
			date12.setBounds(280, 415, 120, 25);
			
			//�����������
			date9.setEnabled(false);
			date10.setEnabled(false);
			date11.setEnabled(false);
			date12.setEnabled(false);
			
			query.setBounds(120, 460, 100, 40);
			cancel.setBounds(280, 460, 100, 40);
			
			//��Ӽ�����
			query.addActionListener(this);
			cancel.addActionListener(this);
			jcbb8.addActionListener(this);
			
			s.add(la1);
			s.add(tx1);
			s.add(la2);
			s.add(tx2);
			s.add(la3);
			s.add(date3);
			s.add(la4);
			s.add(date4);
			s.add(la5);
			s.add(jcbb5);
			s.add(la6);
			s.add(jcbb6);
			s.add(la7);
			s.add(jcbb7);
			s.add(la8);
			s.add(jcbb8);
			s.add(la9);
			s.add(date9);
			s.add(la10);
			s.add(date10);
			s.add(la11);
			s.add(date11);
			s.add(la12);
			s.add(date12);
			s.add(query);
			s.add(cancel);
			
			s.setModal(true);
			s.setSize(500,550);
			s.setResizable(false);
			s.setLocationRelativeTo(null);
			s.setLayout(null);
			
			label.setBounds(0, 0, 500, 550);
			s.add(label);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==cancel){
				s.dispose();
			}
			if(e.getSource()==jcbb8){
				if(jcbb8.getSelectedIndex()==0){
					date9.setEnabled(false);
					date10.setEnabled(false);
					date11.setEnabled(false);
					date12.setEnabled(false);
				}
				if(jcbb8.getSelectedIndex()==1){
					date9.setEnabled(true);
					date10.setEnabled(true);
					date11.setEnabled(true);
					date12.setEnabled(true);
				}
				if(jcbb8.getSelectedIndex()==2){
					date9.setEnabled(false);
					date10.setEnabled(false);
					date11.setEnabled(false);
					date12.setEnabled(false);
				}
			}
			if(e.getSource()==query){
				if(tx1.getText().trim().equals("")&&tx2.getText().trim().equals("")&&
				   date3.getText().trim().equals("����ѡ������")&&date4.getText().trim().equals("����ѡ������")&&
				   jcbb5.getSelectedIndex()==0&&jcbb6.getSelectedIndex()==0&&
				   jcbb7.getSelectedIndex()==0&&jcbb8.getSelectedIndex()==0&&
				   date9.getText().trim().equals("����ѡ������")&&date10.getText().trim().equals("����ѡ������")&&
				   date11.getText().trim().equals("����ѡ������")&&date12.getText().trim().equals("����ѡ������")){
					JOptionPane.showMessageDialog(null, "��������Ҫ��ѯ��������Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
				}
				else if(!tx1.getText().trim().equals("")){
					try{
						con=connect.getConnection();   //����һ�����ݿ�����
						String paId=tx1.getText().trim();
						String sql1="select * from Patient where PaId='"+paId+"'";
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
				else{
					try{
						String sql="select * from Patient where 1=1";
						String paName=tx2.getText().trim();
						String paBir1=date3.getText();
						String paBir2=date4.getText();
						String paSex=jcbb5.getSelectedItem().toString();
						String paAge1=jcbb6.getSelectedItem().toString();
						String paAge2=jcbb7.getSelectedItem().toString();
						String paYN=jcbb8.getSelectedItem().toString();
						String paIn1=date9.getText();
						String paIn2=date10.getText();
						String paOut1=date11.getText();
						String paOut2=date12.getText();
						if(!paName.equals("")){
							sql+=" and PaName='"+paName+"'";
						}
						if(!paSex.equals("")){
							sql+=" and PaSex='"+paSex+"'";
						}
						if(!paAge1.equals("")&&paAge2.equals("")){
							sql+=" and PaAge='"+paAge1+"'";
						}
						if(paAge1.equals("")&&!paAge2.equals("")){
							sql+=" and PaAge='"+paAge2+"'";
						}
						if(!paAge1.equals("")&&!paAge2.equals("")){
							if(Integer.parseInt(paAge1)>Integer.parseInt(paAge2)){
								JOptionPane.showMessageDialog(null, "���䷶Χ�밴С����ѡ��", "����", JOptionPane.ERROR_MESSAGE);
							}
							else{
								sql+=" and PaAge between "+paAge1+" and "+paAge2+"";
							}
						}
						if(!paBir1.equals("����ѡ������")&&!paBir2.equals("����ѡ������")){
							try{
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								Date dt1 = df.parse(date10.getText());
					            Date dt2 = df.parse(date12.getText());
					            if(dt1.getTime()>dt2.getTime()){
					            	JOptionPane.showMessageDialog(null, "���ڷ�Χ�밴�ȵ���ѡ��", "����", JOptionPane.ERROR_MESSAGE);
					            }
					            else {
					            	sql+=" and DrDate between '"+paBir1+"' and '"+paBir2+"'";
					            }
							}catch(Exception e1){
								e1.printStackTrace();
							}
						}
						if(!paBir1.equals("����ѡ������")&&paBir2.equals("����ѡ������")){
							sql+=" and DrDate='"+paBir1+"'";
						}
						if(paBir1.equals("����ѡ������")&&!paBir2.equals("����ѡ������")){
							sql+=" and PaBir='"+paBir2+"'";
						}
						if(!paYN.equals("")){
							sql+=" and PaYN='"+paYN+"'";
							if(paYN.equals("��")){
								if(!paIn1.equals("����ѡ������")&&!paIn2.equals("����ѡ������")){
									try{
										DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
										Date dt1 = df.parse(date9.getText());
							            Date dt2 = df.parse(date10.getText());
							            if(dt1.getTime()>dt2.getTime()){
							            	JOptionPane.showMessageDialog(null, "���ڷ�Χ�밴�ȵ���ѡ��", "����", JOptionPane.ERROR_MESSAGE);
							            }
							            else {
							            	sql+=" and PaIn between '"+paIn1+"' and '"+paIn2+"'";
							            }
									}catch(Exception e1){
										e1.printStackTrace();
									}
								}
								if(!paIn1.equals("����ѡ������")&&paIn2.equals("����ѡ������")){
									sql+=" and PaIn='"+paIn1+"'";
								}
								if(paIn1.equals("����ѡ������")&&!paIn2.equals("����ѡ������")){
									sql+=" and PaIn='"+paIn2+"'";
								}
								if(!paOut1.equals("����ѡ������")&&!paOut2.equals("����ѡ������")){
									try{
										DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
										Date dt1 = df.parse(date11.getText());
							            Date dt2 = df.parse(date12.getText());
							            if(dt1.getTime()>dt2.getTime()){
							            	JOptionPane.showMessageDialog(null, "���ڷ�Χ�밴�ȵ���ѡ��", "����", JOptionPane.ERROR_MESSAGE);
							            }
							            else {
							            	sql+=" and PaOut between '"+paOut1+"' and '"+paOut2+"'";
							            }
									}catch(Exception e1){
										e1.printStackTrace();
									}
								}
								if(!paOut1.equals("����ѡ������")&&paOut2.equals("����ѡ������")){
									sql+=" and PaOut='"+paOut1+"'";
								}
								if(paOut1.equals("����ѡ������")&&!paOut2.equals("����ѡ������")){
									sql+=" and PaOut='"+paOut2+"'";
								}
							}
						}
						if(!paName.equals("")||!paBir1.equals("����ѡ������")||!paBir2.equals("����ѡ������")||
						   !paSex.equals("")||!paAge1.equals("")||!paAge2.equals("")||
						   !paYN.equals("")){
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
