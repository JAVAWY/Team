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
	private JLabel manageTitle=new JLabel("������Ϣ�ۺϲ���");
	private JLabel la1,la2,la3,la4,la5,la6;
	private JTextField tx1,tx2,tx3,tx4,tx5,tx6;
	public JButton save,query,modify,delete;
	private Font laFont=new Font("����",Font.BOLD,15);
	private JComboBox jcbb1;
	private String str1[]={"��ѯȫ��", "�����ұ�Ų�ѯ", "���������Ʋ�ѯ"};
	private final String []columnNames={"���ұ��","��������","��������","���ҵ绰"};
	private JScrollPane JScrollPane1=new JScrollPane();
	private java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	private static JTable table;
    private static  DefaultTableModel dtm;
    private Pattern pattern=Pattern.compile("[0-9]*"); 
	
	public DepartmentManage(){
		//��������
		departmentManage.setLayout(null);
		ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg"); 
		JLabel label = new JLabel(background);
		
		//��������
		manageTitle.setFont(new Font("����",Font.BOLD,50));
		manageTitle.setBounds(60, 10, 1000, 50);
		departmentManage.add(manageTitle);
		
		//¼������������
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "¼�����", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red));
		panel.setBounds(45, 65, 550, 150);
		panel.setOpaque(false);
		
	    la1=new JLabel("���ұ�ţ�");
		la2=new JLabel("�������ƣ�");
		la3=new JLabel("�������Σ�");
		la4=new JLabel("���ҵ绰��");
		tx1=new JTextField();
		tx2=new JTextField();
		tx3=new JTextField();
		tx4=new JTextField();
		save=new JButton("����");
		
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
		
		//��ѯ�����������
		final JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBorder(new TitledBorder(null, "��ѯ����", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red));
		panel1.setBounds(620, 65, 380, 150);
		panel1.setOpaque(false);
		
		query=new JButton("��ѯ");
		la5=new JLabel("���ұ��:");
		la6=new JLabel("��������:");
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
		
		//�������
		defaultTableModel();   //���ñ�񲻿ɱ༭
		setTableColumnCenter();   //���ñ�����ݾ�����ʾ
		setbgcolor();    //���ñ����в�ͬ��ɫ
		JScrollPane1.setBounds(new  Rectangle(45,230,850,270));
		JScrollPane1.setViewportView(table);   //����һ��������������б�Ҫ������������ͼ
		//table.getColumnModel().getColumn(0).setMinWidth(40);
		//table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getTableHeader().setReorderingAllowed(false);   //�в����϶�
		table.getTableHeader().setResizingAllowed(false);   //�п��ܸı�
		departmentManage.add(JScrollPane1);  
		
		//��ť����
		modify=new JButton("�޸�");
		delete=new JButton("ɾ��");
		//reflash=new JButton("ˢ��");
		modify.setBounds(910, 230, 80, 40);
		delete.setBounds(910, 300, 80, 40);
		//reflash.setBounds(910, 370, 80, 40);
		departmentManage.add(modify);
		departmentManage.add(delete);
		//departmentManage.add(reflash);
		
		//��Ӽ�����
		save.addActionListener(this);
		delete.addActionListener(this);
		query.addActionListener(this);
		modify.addActionListener(this);
		//reflash.addActionListener(this);
		jcbb1.addActionListener(this);
		
		//��ӱ���
		departmentManage.add(label);
		label.setBounds(0, 0, 1100, 700);
	}
	//���ñ�񲻿ɱ༭
	private void defaultTableModel(){
		dtm=new DefaultTableModel(columnNames,0);
		table=new JTable(dtm){
			  public boolean isCellEditable(int row, int column){
				  return false;
			  }
		  };
	}
	
	//���ñ�����ݾ�����ʾ
	private void setTableColumnCenter(){  
	    DefaultTableCellRenderer r = new DefaultTableCellRenderer();     
	    r.setHorizontalAlignment(JLabel.CENTER);     
	    table.setDefaultRenderer(Object.class, r);  
	}

	//���ñ����б�����ɫ��ͬ
	private static void setbgcolor(){
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
		
		if(e.getSource()==save){   //¼�����
			 //������Ϣ����Ϊ��
			if(tx1.getText().equals("")||tx2.getText().equals("")||tx4.getText().equals("")){
				JOptionPane.showMessageDialog(null, "�����������Ŀ�����Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
			}
			//���Ϊ3λ����
			else if(tx1.getText().length()>3||pattern.matcher(tx1.getText()).matches()==false){
				JOptionPane.showMessageDialog(null, "��������ȷ��3λ�����ұ��!", "����", JOptionPane.ERROR_MESSAGE);
			}
			//���ֳ��Ȳ��ܳ���20���ַ�
			else if(tx2.getText().length()>10 || tx3.getText().length()>10){
				JOptionPane.showMessageDialog(null, "���ֳ��Ȳ��ܳ���10�����֣�", "����", JOptionPane.ERROR_MESSAGE);
			}
			 //�绰����Ϊ8λ����
			else if(pattern.matcher(tx4.getText()).matches()==false||tx4.getText().length()!=8){  
				JOptionPane.showMessageDialog(null, "��������ȷ��8λ���绰���룡", "����", JOptionPane.ERROR_MESSAGE);
			}
			
			else{
				String deptNo=tx1.getText().trim();
				String deptName=tx2.getText();
				String deptPhone=tx4.getText().trim();
				try{
					con=connect.getConnection();   //����һ�����ݿ�����
					Statement st1=con.createStatement();   //����һ�����ݿ�Ự����
					Statement st2=con.createStatement();   //����һ�����ݿ�Ự����
					Statement st3=con.createStatement();   //����һ�����ݿ�Ự����
					ResultSet rs1=st1.executeQuery("select * from Department where DeptNo='"+deptNo+"'");   //SQL���
					ResultSet rs2=st2.executeQuery("select * from Department where DeptName='"+deptName+"'");
					ResultSet rs3=st3.executeQuery("select * from Department where DeptPhone='"+deptPhone+"'");
					
					if(rs1.next()){   //�жϽ����rs�Ƿ��м�¼�����ҽ�ָ�����һλ
						JOptionPane.showMessageDialog(null, "�ÿ��Һ��Ѵ��ڣ�����������!", "����", JOptionPane.ERROR_MESSAGE);
						st1.close();
					}
					else if(rs2.next()){   
						JOptionPane.showMessageDialog(null, "�ÿ������Ѵ��ڣ�����������!", "����", JOptionPane.ERROR_MESSAGE);
						st2.close();
					}
					else if(rs3.next()){  
						JOptionPane.showMessageDialog(null, "�ÿ��ҵ绰�����Ѵ��ڣ�����������!", "����", JOptionPane.ERROR_MESSAGE);
						st3.close();
					}
					
					else{
						int ok=JOptionPane.showConfirmDialog(null, "�Ƿ񱣴�ÿ�����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
						if(ok==JOptionPane.YES_OPTION){
							try{
								//��Ϣ��ӵ����ݿ�
								String sql="INSERT INTO Department(DeptNo,DeptName,DrName,DeptPhone)VALUES(?,?,?,?)";
								PreparedStatement parepare=con.prepareStatement(sql);
								parepare.setString(1, tx1.getText());
	              				parepare.setString(2, tx2.getText());
	              				parepare.setString(3, tx3.getText());
	              				parepare.setString(4, tx4.getText());
	              				parepare.executeUpdate();
	              				
	              				String []data = new String[]{tx1.getText(),tx2.getText(),tx3.getText(),tx4.getText()};
	              				dtm.addRow(data);   //�ڱ�����һ�и���ӵ�����
	              				JOptionPane.showMessageDialog(null, "¼��ɹ�");
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
		
		if(e.getSource()==query){   //��ѯ����
			try{
				con=connect.getConnection();   //����һ�����ݿ�����
				if(jcbb1.getSelectedIndex()==0){   //ȫ����ѯ
					String sql="select * from Department";
					databaseSearch(sql);
					if(table.getRowCount()!=0){
						JOptionPane.showMessageDialog(null, "��ѯ�ɹ���");
					}
					else{
						JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ����Ϣ��");
					}
				}
				if(jcbb1.getSelectedIndex()==1){   //��Ų�ѯ
					if(tx5.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "��������ȷ�Ŀ��ұ�ţ�", "����", JOptionPane.ERROR_MESSAGE);
					}
					else{
						String deptNo=tx5.getText().trim();
						String sql="select * from Department where DeptNo like'%"+deptNo+"%'";
						databaseSearch(sql);
						if(table.getRowCount()!=0){
							JOptionPane.showMessageDialog(null, "��ѯ�ɹ���");
							tx5.setText("");
						}
						else{
							JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ����Ϣ��");
						}
					}
				}
				if(jcbb1.getSelectedIndex()==2){   //���Ʋ�ѯ
					if(tx6.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "��������ȷ�Ŀ������ƣ�", "����", JOptionPane.ERROR_MESSAGE);
					}
					else{
						String deptName=tx6.getText();
						String sql="select * from Department where DeptName like '%"+deptName+"%'";
						databaseSearch(sql);
						if(table.getRowCount()!=0){
							JOptionPane.showMessageDialog(null, "��ѯ�ɹ���");
							tx6.setText("");
						}
						else{
							JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ����Ϣ��");
						}
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		if(e.getSource()==delete){   //ɾ������
			try{
				con=connect.getConnection();   //����һ�����ݿ�����
				Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
				int selectCount=table.getSelectedRowCount();
				if(selectCount==0){
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ������Ϣ��");
				}
				else if(selectCount==1){
					int ok=JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ���ÿ�����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						int row=table.getSelectedRow();
						String deptNo=(String) table.getValueAt(row, 0);
						String sql="delete from Department where DeptNo='"+deptNo+"'";
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
							String deptNo=(String)table.getValueAt(row1, 0);
							String sql="delete from Department where DeptNo='"+deptNo+"'";
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
		
		//�޸Ĳ�������
		if(e.getSource()==modify){
			if(table.getSelectedRowCount()!=1){
				JOptionPane.showMessageDialog(null, "��ѡ��һ�������Ϣ�����޸ģ�", "����", JOptionPane.ERROR_MESSAGE);
			}
			else{
				try{
					con=connect.getConnection();   //����һ�����ݿ�����
					Statement stmt1=con.createStatement();   //����һ�����ݿ�Ự����
					
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
	
	//������Ϣ�޸Ĳ����Ի�������++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class DepartmentModify implements ActionListener{
		
		public JDialog s=new JDialog();
		public JLabel la1,la2,la3,la4;
		public JTextField tx1,tx2,tx3,tx4,txWait;
		public JButton confirm,cancel;
		public Font laFont=new Font("����",Font.BOLD,15);
		public int row=table.getSelectedRow();
		
		public DepartmentModify(){
			ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg"); 
			JLabel label = new JLabel(background);
			s.setModal(true);
			s.setSize(500,250);
			s.setResizable(false);
			s.setLocationRelativeTo(null);
			s.setLayout(null);
			
			
			la1=new JLabel("���ұ�ţ�");
			la2=new JLabel("�������ƣ�");
			la3=new JLabel("�������Σ�");
			la4=new JLabel("���ҵ绰��");
			tx1=new JTextField();
			tx2=new JTextField();
			tx3=new JTextField();
			tx4=new JTextField();
			txWait=new JTextField();
			confirm=new JButton("ȷ��");
			cancel=new JButton("ȡ��");
			
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
				
				int ok=JOptionPane.showConfirmDialog(null, "�Ƿ��޸ĸÿ�����Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(ok==JOptionPane.YES_OPTION){
					try{
						con=connect.getConnection();   //����һ�����ݿ�����
						Statement stmt=con.createStatement();   //����һ�����ݿ�Ự����
						 //������Ϣ����Ϊ��
						if(tx2.getText().equals("")||tx4.getText().equals("")){
							JOptionPane.showMessageDialog(null, "�����������Ŀ�����Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
						}
						//���Ϊ3λ����
						else if(tx1.getText().length()>3||pattern.matcher(tx1.getText()).matches()==false){
							JOptionPane.showMessageDialog(null, "��������ȷ��3λ�����ұ��!", "����", JOptionPane.ERROR_MESSAGE);
						}
						//���ֳ��Ȳ��ܳ���20���ַ�
						else if(tx2.getText().length()>10 || tx3.getText().length()>10){
							JOptionPane.showMessageDialog(null, "���ֳ��Ȳ��ܳ���10�����֣�", "����", JOptionPane.ERROR_MESSAGE);
						}
						 //�绰����Ϊ8λ����
						else if(pattern.matcher(tx4.getText()).matches()==false||tx4.getText().length()!=8){  
							JOptionPane.showMessageDialog(null, "��������ȷ��8λ���绰���룡", "����", JOptionPane.ERROR_MESSAGE);
						}

						else{
							
							int row2=table.getSelectedRow();
						String sql="update Department   set DeptName='"+tx2.getText().trim()+"',DrName='"+tx3.getText().trim()+"',DeptPhone='"+tx4.getText().trim()+"'where DeptNo='"+tx1.getText().trim()+"'  ";
						stmt.executeUpdate(sql);  
						String []data = new String[]{tx1.getText(),tx2.getText(),tx3.getText(),tx4.getText()};
						dtm.removeRow(row2);
						dtm.insertRow(row2, data);
						
          				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
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
