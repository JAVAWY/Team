import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;




public class drug  extends JFrame implements ActionListener{
	JPanel chufangInput =new JPanel();
	 private  JButton button1,button2,button3,button4,button5,button6,button7,button8;
	private JLabel inputTitle =new JLabel("������¼��");
	private JLabel la0,la1,la2,la3,la4,la5,la6,la7,la8;
	private Font laFont=new Font("����",Font.BOLD,15);
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	private Pattern pattern=Pattern.compile("[0-9]*"); 
	 private java.sql.Connection con=null;
	 private JLabel t1,t2;
	 private static JTable table1,table2,table3,table4,table5;
	 private static  DefaultTableModel dtm1,dtm2,dtm3,dtm4,dtm5;
	 private JScrollPane JScrollPane=new JScrollPane();
	 private JScrollPane JScrollPane1=new JScrollPane();
	 private JScrollPane JScrollPane4=new JScrollPane();
	 private JScrollPane JScrollPane5=new JScrollPane();
	 private String columnNames1[]={"����","����","����","����","������λ","���","��������"};
	 private String columnNames2[]={"����","����","����","������λ","���"};
	 private String columnNames3[]={"��������","���˱��","��������"};
	 private JTextField tx1,tx2,tx3,tx4,tx5;
	  private  JComboBox box1,box2,box5 ;
	 public drug(){
		 chufangInput.setLayout(null);
        
		 
		 dtm1=new DefaultTableModel(columnNames1,0){//dtm2����Ŀ�շѱ��ģ��
			  public boolean isCellEditable(int row, int column)
			  {
				  if(column==1||column==3) return true;//����ǿ��Ա༭����
	     		 //if(rowIndex!=0) return false;
	     		 return false;
				  }//��������༭ }
			  };
			
			  String fontSize1[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "12"};
			  table1 = new JTable(dtm1);//JScrollPane4       ��Ŀ��
			  JScrollPane JScrollPane = new JScrollPane(table1); 
			  TableColumn a1 = table1.getColumn("����");
			  TableColumn a2 = table1.getColumn("����");
		        JTextField box3 = new JTextField();  
		        box2 = new JComboBox(fontSize1);
		        box3.getDocument().addDocumentListener(new DocumentListener() 
		         { 
		             @Override
		             public void removeUpdate(DocumentEvent e) {
		           
		            updata_combobox();
		             }
		              
		             @Override
		             public void insertUpdate(DocumentEvent e) {
		             	
		             	 updata_combobox();
		             }
		              
		             @Override
		             public void changedUpdate(DocumentEvent e) {
		             	
		             	updata_combobox();
		             }
		             
		             private void updata_combobox(){
		                 String s1=null;
		                 s1=box3.getText();
		                 System.out.println(s1);
		                 JScrollPane1.setVisible(true);
		                 String sql="select * from Price where PeName like  '%"+s1+"%'and PeClass='������'" ;
		        		     databaseSearch1(sql,5);
		        		     
		                }
		               });
		        
		        
		        
		        
		        
		        
		        box3.setEditable(true);
		         DefaultCellEditor dce2 = new DefaultCellEditor(box3);   
		         a1.setCellEditor(dce2);  
		           
		         box2.setEditable(true);box2.setMaximumRowCount(5);     
		         DefaultCellEditor dce3 = new DefaultCellEditor(box2);   
		         a2.setCellEditor(dce3);     
		         box2.addActionListener(this); 
		        
		         dtm2=new DefaultTableModel(columnNames2,0);// ��Ŀ��ϸ��
				  table2=new JTable(dtm2){
					  public boolean isCellEditable(int row, int column)
					  {
						  return false;
						  }//��������༭ }
					  };
					  String sql="select * from Price where PeClass='������'" ;
					   databaseSearch1(sql,5);
					   
			   JScrollPane1.setViewportView(table2);
				chufangInput.add(JScrollPane1);
				JScrollPane1.setBounds(70, 150, 400, 100);
				JScrollPane1.setVisible(false);
				
				
				
				
				//������ҩ��
				 dtm3=new DefaultTableModel(columnNames1,0){//dtm3��ҩ���շѱ��ģ��
					  public boolean isCellEditable(int row, int column)
					  {
						  if(column==1||column==3) return true;//����ǿ��Ա༭����
			     		 //if(rowIndex!=0) return false;
			     		 return false;
						  }//��������༭ }
					  };
					
					  table3 = new JTable(dtm3);//      
					  JScrollPane JScrollPane3 = new JScrollPane(table3); 
					  TableColumn b1 = table3.getColumn("����");
					  TableColumn b2 = table3.getColumn("����");
				        JTextField box4 = new JTextField();  
				        box5 = new JComboBox(fontSize1);
				  
				        box4.getDocument().addDocumentListener(new DocumentListener() 
				         { 
				             @Override
				             public void removeUpdate(DocumentEvent e) {
				           
				            updata_combobox();
				             }
				              
				             @Override
				             public void insertUpdate(DocumentEvent e) {
				             	
				             	 updata_combobox();
				             }
				              
				             @Override
				             public void changedUpdate(DocumentEvent e) {
				             	
				             	updata_combobox();
				             }
				             
				             private void updata_combobox(){
				                 String s1=null;
				                 s1=box4.getText();
				                
				                 JScrollPane4.setVisible(true);
								  String sql1="select * from Price where PeName like '%"+s1+"%' and PeClass='�����'or PeName like '%"+s1+"%' and PeClass='ҩƷ��'" ;
				        		     databaseSearch2(sql1,5);
				                }
				               });
				        
				        dtm4=new DefaultTableModel(columnNames2,0);// ҩ����ϸ��
						  table4=new JTable(dtm4){
							  public boolean isCellEditable(int row, int column)
							  {
								  return false;
								  }//��������༭ }
							  };
							  String sql1="select * from Price where PeClass='�����'or PeClass='ҩƷ��'" ;
							   databaseSearch2(sql1,5);
							   
					   JScrollPane4.setViewportView(table4);
						chufangInput.add(JScrollPane4);
						JScrollPane4.setBounds(550, 150, 400, 100);
						JScrollPane4.setVisible(false);
				        
				        
				        
				        
				        box4.setEditable(true);
				         DefaultCellEditor dce1 = new DefaultCellEditor(box4);   
				         b1.setCellEditor(dce1);  
				           
				         box2.setEditable(true);box2.setMaximumRowCount(5);     
				         DefaultCellEditor dce0 = new DefaultCellEditor(box5);   
				         b2.setCellEditor(dce0);     
				      
				         
				         dtm5=new DefaultTableModel(columnNames3,0);// ��Ŀ��ϸ��
						  table5=new JTable(dtm5){
							  public boolean isCellEditable(int row, int column)
							  {
								  return false;
								  }//��������༭ }
							  };
							  String sql2="select * from Medical_records where MrId not in(select MrId from DrugTable)" ;
			        		     databaseSearch3(sql2,3);
				        
	        		     JScrollPane5.setViewportView(table5);
							chufangInput.add(JScrollPane5);
							JScrollPane5.setBounds(120,100, 300, 100);
							JScrollPane5.setVisible(false);
							
	        		    
		 //���ñ���
			ImageIcon background = new ImageIcon("F:/�γ����/right_bg.jpg"); 
			JLabel label = new JLabel(background);
			inputTitle.setFont(new Font("����",Font.BOLD,30));
			inputTitle.setBounds(60, 10, 1000, 50);
			chufangInput.add(inputTitle);
			;
			//���ÿؼ�
			la1=new JLabel("�������:");
			la2=new JLabel("���˱��:");
			la3=new JLabel("��������:");
			t1=new JLabel("��Ŀ��");
			t2=new JLabel("ҩ�ﵥ");
			tx1=new JTextField();
			tx2=new JTextField();
			tx3=new JTextField();
			button1=new JButton("����");
			button2=new JButton("ȷ��");
			button3=new JButton("�޸�");
			button4=new JButton("ɾ��");
			button5=new JButton("����");
			button6=new JButton("ȷ��");
			button7=new JButton("�޸�");
			button8=new JButton("ɾ��");
			button2.addActionListener(this);
			button6.addActionListener(this);
			//����ť��Ӽ����¼�
			button1.addActionListener(new ActionListener(){//����¼�  
	            public void actionPerformed(ActionEvent e){  
	               	String []da1={"",""};
	               	String []rowValues =da1;
	               	
	            dtm1.addRow(rowValues);  //���һ��             
	          
				button1.setEnabled(false);
				
	            }  
		});
			button5.addActionListener(new ActionListener(){//ҩ�������¼�  
	            public void actionPerformed(ActionEvent e){  

	               String []da={"",""}; 
	               String []rowValues =da; 
	            dtm3.addRow(rowValues);  //���һ��             
	           
	           
				button5.setEnabled(false);
				
	            }  
		});
			button4.addMouseListener(new MouseAdapter(){  //ɾ����ťʵ��ɾ����¼�Ĺ���
				 public void mouseClicked(MouseEvent e) {
					 int row= table1.getSelectedRow();//���ѡ��Ҫɾ������
					 Connection con;
						 
						  con=connect.getConnection();
					       Statement stmt; 
					    String   val = (String) table1.getValueAt(row, 6);
					    String   val1 = (String) table1.getValueAt(row, 0);
						String 	sql="delete from DrugTable where MrId='"+val+"'and PeNo='"+val1+"'";
						try {
							stmt = con.createStatement();
							stmt.executeUpdate(sql);
							button1.setEnabled(true);
							
						}catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						 if(row!=-1){  //����ж��Ƿ���ѡ�е���
							 dtm1.removeRow(row); }   //���ɾ��ָ���� 
						 
				 }
			});
			button8.addMouseListener(new MouseAdapter(){  //ɾ����ťʵ��ɾ����¼�Ĺ���
				 public void mouseClicked(MouseEvent e) {
					 int row= table3.getSelectedRow();//���ѡ��Ҫɾ������
					 Connection con;
						  
						  con=connect.getConnection();
					       Statement stmt; 
					    String   val = (String) table3.getValueAt(row, 6);
					    String   val1 = (String) table3.getValueAt(row, 0);
						String 	sql="delete from DrugTable where MrId='"+val1+"'and PeNo='"+val+"'";
						try {
							stmt = con.createStatement();
							stmt.executeUpdate(sql);
							button5.setEnabled(true);
							
						}catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						 if(row!=-1){  //����ж��Ƿ���ѡ�е���
							 dtm3.removeRow(row); }   //���ɾ��ָ���� 
						 
				 }
			});
			box3.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // ����������			    
					    	 JScrollPane1.setVisible(true);     
					   }
			});
			box4.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // ����������			    
					    	 JScrollPane4.setVisible(true);     
					   }
			});
			
			table2.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // ����������
					     if (e.getClickCount() == 2) { 
					    	 String xingming=tx1.getText();
					    	 int o=table2.getSelectedRow();
					    	 int row=table1.getSelectedRow();
					    	String ao=(String) table2.getValueAt(o, 1);
					    	String bo=(String) table2.getValueAt(o, 0);
					    	String co=(String) table2.getValueAt(o, 2);
					    	String do1=(String) table2.getValueAt(o, 3);
					    	String eo=(String) table2.getValueAt(o, 4);
					    	
					    	box3.setText(ao);
					    	table1.setValueAt(bo, row, 0);
					    	table1.setValueAt(do1, row, 4);
					    	table1.setValueAt(co, row, 2);
					    	table1.setValueAt(eo, row, 5);
					    	table1.setValueAt(xingming, row, 6);
					    	
					    	JScrollPane1.setVisible(false);
					    	
					    	
					    	
					     }    
					   }			   
					  });
			tx1.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // ����������			    
					    	 JScrollPane5.setVisible(true);     
					   }
			});
			
			 tx1.getDocument().addDocumentListener(new DocumentListener() 
	         { 
	             @Override
	             public void removeUpdate(DocumentEvent e) {
	           
	            updata_combobox();
	             }
	              
	             @Override
	             public void insertUpdate(DocumentEvent e) {
	             	
	             	 updata_combobox();
	             }
	              
	             @Override
	             public void changedUpdate(DocumentEvent e) {
	             	
	             	updata_combobox();
	             }
	             
	             private void updata_combobox(){
	                 String s1=null;
	                 s1=tx1.getText();
	                
	                 JScrollPane5.setVisible(true);
	                 String sql2="select * from Medical_records where MrId like '%"+s1+"%'and MrId not in(select MrId from DrugTable)" ;
	        		     databaseSearch3(sql2,3);
	                }
	               });
			
	        

			table4.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // ����������
					     if (e.getClickCount() == 2) { 
					    	 String xingming=tx1.getText();
					    	 int o=table4.getSelectedRow();
					    	 int row=table3.getSelectedRow();
					    	String ao=(String) table4.getValueAt(o, 1);
					    	String bo=(String) table4.getValueAt(o, 0);
					    	String co=(String) table4.getValueAt(o, 2);
					    	String do1=(String) table4.getValueAt(o, 3);
					    	String eo=(String) table4.getValueAt(o, 4);
					    	
					    	box4.setText(ao);
					    	table3.setValueAt(bo, row, 0);
					    	table3.setValueAt(do1, row, 4);
					    	table3.setValueAt(co, row, 2);
					    	table3.setValueAt(eo, row, 5);
					    	table3.setValueAt(xingming, row, 6);
					    	
					    	JScrollPane4.setVisible(false);
					    	
					    	
					    	
					     }    
					   }			   
					  });
			table5.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // ����������
					     if (e.getClickCount() == 2) { 
					    	
					    	 int o=table5.getSelectedRow();
					    	 
					    	String ao=(String) table5.getValueAt(o, 1);
					    	String bo=(String) table5.getValueAt(o, 0);
					    	String co=(String) table5.getValueAt(o, 2);
					    	
					    	
					    	tx1.setText(bo);
					    	tx2.setText(ao);
					    	tx3.setText(co);
					    	
					    	JScrollPane5.setVisible(false);
					    	
					    	
					    	
					     }    
					   }			   
					  });
			
			
			
		 //��ӿؼ�������Լ����ÿؼ�λ��
			chufangInput.add(la1);
			chufangInput.add(la2);
			chufangInput.add(la3);

			chufangInput.add(tx1);
			chufangInput.add(tx2);
			chufangInput.add(tx3);
			chufangInput.add(button1);
			chufangInput.add(button2);
			chufangInput.add(button3);
			chufangInput.add(button4);
			chufangInput.add(button5);
			chufangInput.add(button6);
			chufangInput.add(button7);
			chufangInput.add(button8);
			chufangInput.add(t1);
			chufangInput.add(t2);
			button1.setBounds(460, 250, 70, 50);
			button2.setBounds(460, 300, 70, 50);
			button3.setBounds(460, 350, 70, 50);
			button4.setBounds(460, 400, 70, 50);
			button5.setBounds(950, 250, 70, 50);
			button6.setBounds(950, 300, 70, 50);
			button7.setBounds(950, 350, 70, 50);
			button8.setBounds(950, 400, 70, 50);
			t1.setBounds(50, 200, 100, 50);
			t2.setBounds(520, 200, 100, 50);
			la1.setBounds(50, 70, 100, 40);
			la2.setBounds(300	, 70, 100, 40);
			la3.setBounds(500	, 70, 100, 40);
		 tx1.setBounds(120, 75, 100, 30);
		 tx2.setBounds(370, 75, 100, 30);
		 tx3.setBounds(570, 75, 100, 30);
		 chufangInput.add(JScrollPane);
		 JScrollPane.setBounds(60, 250, 400, 200);
		 chufangInput.add(JScrollPane3);
		 JScrollPane3.setBounds(550, 250, 400, 200);
		 chufangInput.add(label);
			label.setBounds(0, 0, 1100, 700);
	 }
	private void databaseSearch3(String sql2, int i) {
		// TODO Auto-generated method stub
		Connection con;
	
		  con=connect.getConnection();
		  ResultSet rs;
		
		   
	       try{
				int rowcount = dtm5.getRowCount() - 1;
				if (rowcount != -1) {
					for (int i1 = rowcount; i1 >= 0; i1--) {
						dtm5.removeRow(i1); // ɾ��Jtable�е�������
					}
					dtm5.setRowCount(0); // ��Jtable�е�������Ϊ��
				}		
				Statement stmt=con.createStatement();
			    rs=stmt.executeQuery(sql2);
			    String[] data = new String[3];
				while (rs.next()) {
					for (int j = 1; j <= 3; j++) {
						data[j - 1] = rs.getString(j); // ȡ�����ݿ��е�����װ�ص�������
					}
					dtm5.addRow(data); // ��Jtabl
					
				}
				  
			    con.close();
					//���ñ����б���ɫ�����б���ɫ��ͬ��	
	}catch(Exception err){
	}
	}
	private void databaseSearch2(String sql1, int i) {
		// TODO Auto-generated method stub
		Connection con;
		
		  con=connect.getConnection();
		  ResultSet rs;
		
		   
	       try{
				int rowcount = dtm4.getRowCount() - 1;
				if (rowcount != -1) {
					for (int i1 = rowcount; i1 >= 0; i1--) {
						dtm4.removeRow(i1); // ɾ��Jtable�е�������
					}
					dtm4.setRowCount(0); // ��Jtable�е�������Ϊ��
				}		
				Statement stmt=con.createStatement();
			    rs=stmt.executeQuery(sql1);
			    String[] data = new String[5];
				while (rs.next()) {
					for (int j = 1; j <= 5; j++) {
						data[j - 1] = rs.getString(j); // ȡ�����ݿ��е�����װ�ص�������
					}
					dtm4.addRow(data); // ��Jtabl
					
				}
				  
			    con.close();
					//���ñ����б���ɫ�����б���ɫ��ͬ��	
	}catch(Exception err){
	}
	}
	private void databaseSearch1(String sql, int i) {
		// TODO Auto-generated method stub

		
		Connection con;
		  
		  con=connect.getConnection();
		  ResultSet rs;
		
		   
	       try{
				int rowcount = dtm2.getRowCount() - 1;
				if (rowcount != -1) {
					for (int i1 = rowcount; i1 >= 0; i1--) {
						dtm2.removeRow(i1); // ɾ��Jtable�е�������
					}
					dtm2.setRowCount(0); // ��Jtable�е�������Ϊ��
				}		
				Statement stmt=con.createStatement();
			    rs=stmt.executeQuery(sql);
			    String[] data = new String[5];
				while (rs.next()) {
					for (int j = 1; j <= 5; j++) {
						data[j - 1] = rs.getString(j); // ȡ�����ݿ��е�����װ�ص�������
					}
					dtm2.addRow(data); // ��Jtabl
					
				}
				  
			    con.close();
					//���ñ����б���ɫ�����б���ɫ��ͬ��	
	}catch(Exception err){
	}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==button2){
			try{
				String s=(String)box2.getSelectedItem();
				 int i= Integer.valueOf(s).intValue();
				Connection con;
		
			  con=connect.getConnection();
			 
		       int row=table1.getSelectedRow();
		     String sql="INSERT INTO DrugTable(PeNo,PeName,PePrice,PeNumber,PeUnit,PeClass,MrId)VALUES(?,?,?,?,?,?,?)";
		   PreparedStatement parepare=con.prepareStatement(sql);
		 parepare.setString(1, (String) table1.getValueAt(row, 0));
		 parepare.setString(2, (String) table1.getValueAt(row, 1));
		parepare.setString(3, (String) table1.getValueAt(row, 2));
		parepare.setString(4, (String) table1.getValueAt(row, 3));
		parepare.setString(5, (String) table1.getValueAt(row, 4));
		parepare.setString(6, (String) table1.getValueAt(row, 5));
		parepare.setString(7, (String) table1.getValueAt(row, 6));
		
		String w=(String) table1.getValueAt(row, 3);
		String w1=(String) table1.getValueAt(row, 1);
		if(table1.getValueAt(row, 0).equals("")){
			JOptionPane.showMessageDialog(null, "������������Ϣ","����",JOptionPane.INFORMATION_MESSAGE);

		}
		else if(w1==""){
		JOptionPane.showMessageDialog(null, "������ҩ������","����",JOptionPane.INFORMATION_MESSAGE);

		}
		else if(i<=0||w==null){
		JOptionPane.showMessageDialog(null, "��������Ϊ�ջ���С��0","����",JOptionPane.INFORMATION_MESSAGE);

		}
		else{
			parepare.executeUpdate();
		JOptionPane.showMessageDialog(null, "¼��ɹ�","¼��ɹ�",JOptionPane.INFORMATION_MESSAGE);
	    button1.setEnabled(true);
	}
		}catch(Exception et){
			et.printStackTrace();
		}
			}
		
			
		else if(e.getSource()==button6){
			try{
			String s=(String)box5.getSelectedItem();
			 int i= Integer.valueOf(s).intValue();
			Connection con;
		
		  con=connect.getConnection();
		 
	       int row=table3.getSelectedRow();
	     String sql="INSERT INTO DrugTable(PeNo,PeName,PePrice,PeNumber,PeUnit,PeClass,MrId)VALUES(?,?,?,?,?,?,?)";
	   PreparedStatement parepare=con.prepareStatement(sql);
	 parepare.setString(1, (String) table3.getValueAt(row, 0));
	 parepare.setString(2, (String) table3.getValueAt(row, 1));
	parepare.setString(3, (String) table3.getValueAt(row, 2));
	parepare.setString(4, (String) table3.getValueAt(row, 3));
	parepare.setString(5, (String) table3.getValueAt(row, 4));
	parepare.setString(6, (String) table3.getValueAt(row, 5));
	parepare.setString(7, (String) table3.getValueAt(row, 6));
	
	String w=(String) table3.getValueAt(row, 3);
	String w1=(String) table3.getValueAt(row, 1);
	if(w1==""){
	JOptionPane.showMessageDialog(null, "������ҩ������","����",JOptionPane.INFORMATION_MESSAGE);

	}
	else if(i<=0||w==null){
	JOptionPane.showMessageDialog(null, "��������Ϊ�ջ���С��0","����",JOptionPane.INFORMATION_MESSAGE);

	}
	else{
		parepare.executeUpdate();
	JOptionPane.showMessageDialog(null, "¼��ɹ�","¼��ɹ�",JOptionPane.INFORMATION_MESSAGE);
    button5.setEnabled(true);
}
	}catch(Exception et){
		et.printStackTrace();
	}
		}
	}
	 
}
