import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class ProjectModify extends JFrame implements ActionListener,ItemListener{
	JButton button6=new JButton("����");
	JButton button7=new JButton("ȷ��");
	JButton button8=new JButton("ɾ��");
	 private String columnNames2[]={"����","����","����","����","������λ","���","�������"};
	 private String columnNames3[]={"����","����","����","������λ","���"};
	
	private  JComboBox box1,box2 ;
	JPanel panel2=new JPanel();
	public static JTable table2,table3;
	public static  DefaultTableModel dtm2,dtm3;
	 private JScrollPane JScrollPane3=new JScrollPane();
	 private JScrollPane JScrollPane5=new JScrollPane();
	 String y;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	ProjectModify(String Stitle){
		super(Stitle);
		panel2.setLayout(null);
		
		 ImageIcon ic;               //��ťͼƬ
		  ic = new ImageIcon("F:/�γ����/right_bg.jpg"); 
		  JLabel label = new JLabel(ic);//�ѱ���ͼƬ��ʾ��һ����ǩ����
		  
		
		
		
		
		
		dtm2=new DefaultTableModel(columnNames2,0){//dtm2��ҩ���շѱ��ģ��
			  public boolean isCellEditable(int row, int column)
			  {
				  if(column==1||column==3) return true;//����ǿ��Ա༭����
	     		 //if(rowIndex!=0) return false;
	     		 return false;
				  }//��������༭ }
			  };
			  
			  String fontSize1[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "12"};
			  table2 = new JTable(dtm2);//JScrollPane4       ��Ŀ��
			  JScrollPane JScrollPane4 = new JScrollPane(table2); 
			  TableColumn a1 = table2.getColumn("����");
			  TableColumn a2 = table2.getColumn("����");
		        JTextField box3 = new JTextField();  
		        box2 = new JComboBox(fontSize1);
		        box2.addActionListener(this); 
		         box2.addItemListener(this);
		 
		        box3.getDocument().addDocumentListener(new DocumentListener() 
		         { 
		             @Override
		             public void removeUpdate(DocumentEvent e) {
		            System.out.println("removeUpdate");
		            updata_combobox();
		             }
		              
		             @Override
		             public void insertUpdate(DocumentEvent e) {
		             	 System.out.println("insertUpdate");
		             	 updata_combobox();
		             }
		              
		             @Override
		             public void changedUpdate(DocumentEvent e) {
		             	System.out.println("changedUpdate");
		             	updata_combobox();
		             }
		             
		             private void updata_combobox(){
		                 String s1=null;
		                 s1=box3.getText();
		                 System.out.println(s1);
		                 JScrollPane5.setVisible(true);
		                 String sql="select * from Price where PeName like  '%"+s1+"%'" ;
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
			 
		         
		         
			  
		         final JPanel panel = new JPanel();
		 		panel.setLayout(null);
		 		panel.setBorder(new TitledBorder(null, "������Ŀ��", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		 		panel.setBounds(20, 150,530, 180);
		 		panel.setBackground(Color.WHITE);
		 		panel.add(JScrollPane4);
		 		JScrollPane4.setBounds(10,20, 400, 150);
		 		panel2.add(panel);
		 		button6.setBounds(420, 20, 100, 40);
		 		panel.add(button6);
		 		button7.setBounds(420, 70, 100, 40);
		 		panel.add(button7);
		 		button8.setBounds(420, 120, 100, 40);
		 		panel.add(button8);
		 		button6.addActionListener(this);
		 		button7.addActionListener(this);
		 		button8.addActionListener(this);
		 		dtm3=new DefaultTableModel(columnNames3,0);// ��Ŀ��ϸ��
				  table3=new JTable(dtm3){
					  public boolean isCellEditable(int row, int column)
					  {
						  return false;
						  }//��������༭ }
					  };
					  
					  JScrollPane5.setViewportView(table3);
					  panel2.add(JScrollPane5);
					  JScrollPane5.setBounds(30, 50, 400, 100);
					  JScrollPane5.setVisible(false);
						
						String SQL1="select * from Price where PeClass='�����'or PeClass='ҩƷ��'" ;
						   databaseSearch1(SQL1,5);
		 		
		 		JScrollPane4.setViewportView(table2);
		 		box3.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
					   public void mouseClicked(MouseEvent e) {
						   if (e.getButton() == MouseEvent.BUTTON1) // ����������				    
						    	 JScrollPane5.setVisible(true);  
						   }
						  });
		 		
		 		button8.addMouseListener(new MouseAdapter(){  //ɾ����ťʵ��ɾ����¼�Ĺ���
					 public void mouseClicked(MouseEvent e) {
						 int row= table2.getSelectedRow();//���ѡ��Ҫɾ������
						 Connection con;
						
							  con=connect.getConnection();
						       Statement stmt; 
						    String   val = (String) table2.getValueAt(row, 6);
						    String   val1 = (String) table2.getValueAt(row, 0);
							String 	sql="delete from DrugTable where MrId='"+val+"'and PeNo='"+val1+"'";
							try {
								stmt = con.createStatement();
								stmt.executeUpdate(sql);
								button6.setEnabled(true);
								JOptionPane.showMessageDialog(null," ɾ���ɹ���","ע��",JOptionPane.INFORMATION_MESSAGE);
							}catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
							 if(row!=-1){  //����ж��Ƿ���ѡ�е���
								 dtm2.removeRow(row); }   //���ɾ��ָ���� 
							 
					 }
				});
		 		
		 		table3.addMouseListener(new MouseAdapter() {//����TABLE˫������¼�
					   public void mouseClicked(MouseEvent e) {
						   if (e.getButton() == MouseEvent.BUTTON1) // ����������
						     if (e.getClickCount() == 2) { 
						    	 int o=table3.getSelectedRow();
						    	 int row=table2.getSelectedRow();
						    	String ao=(String) table3.getValueAt(o, 1);
						    	String bo=(String) table3.getValueAt(o, 0);
						    	String co=(String) table3.getValueAt(o, 2);	    	
						    	String eo=(String) table3.getValueAt(o, 4);
						    	String eo1=(String) table3.getValueAt(o, 3);
						    	System.out.println(ao);
						    	box3.setText(ao);
						    	table2.setValueAt(bo, row, 0);	
						    	table2.setValueAt(co, row, 2);
						    	table2.setValueAt(eo, row, 5);
						    	table2.setValueAt(eo1, row, 4);
						    					    	
					         	JScrollPane5.setVisible(false);
						     }    
						   }			   
						  });

		 		panel2.add(label);
		 		label.setBounds(0, 0, 600, 400);
		 		this.add(panel2);
				this.setSize(600,400);   //���ô��ڴ�С
				this.setResizable(false);    //���ò��ɵ������ڴ�С
			    this.setLocationRelativeTo(null);    
			    this.setVisible(true);
		 		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==button7){
			try{
				String s=(String)box2.getSelectedItem();
				 int i= Integer.valueOf(s).intValue();
				Connection con;
			
			  con=connect.getConnection();
			  int row=table2.getSelectedRow();
			String b=  (String)table2.getValueAt(row, 3);
		       
		     String sql="INSERT INTO DrugTable(PeNo,PeName,PePrice,PeNumber,PeUnit,PeClass,MrId)VALUES(?,?,?,?,?,?,?)";
		   PreparedStatement parepare=con.prepareStatement(sql);
		 parepare.setString(1, (String) table2.getValueAt(row, 0));
		 parepare.setString(2, (String) table2.getValueAt(row, 1));
		parepare.setString(3, (String) table2.getValueAt(row, 2));
		parepare.setString(4, (String) table2.getValueAt(row, 3));
		parepare.setString(5, (String) table2.getValueAt(row, 4));
		parepare.setString(6, (String) table2.getValueAt(row, 5));
		parepare.setString(7, (String) table2.getValueAt(row, 6));
		
		if(i<=0||b==""){
		JOptionPane.showMessageDialog(null, "��������С��0��Ϊ��","����",JOptionPane.INFORMATION_MESSAGE);

		}

		else{
			parepare.executeUpdate();
		JOptionPane.showMessageDialog(null, "¼��ɹ�","¼��ɹ�",JOptionPane.INFORMATION_MESSAGE);
	    
		
	  
		
		button6.setEnabled(true);
	}
		}catch(Exception et){
			et.printStackTrace();
		}
			}
		}
		
	
	public  void databaseSearch1(String SQL1, int i) {
		// TODO Auto-generated method stub
		Connection con;
		
		  con=connect.getConnection();
		  ResultSet rs;
	       try{
				int rowcount = dtm3.getRowCount() - 1;
				if (rowcount != -1) {
					for (int i1 = rowcount; i1 >= 0; i1--) {
						dtm3.removeRow(i1); // ɾ��Jtable�е�������
					}
					dtm3.setRowCount(0); // ��Jtable�е�������Ϊ��
				}		
				Statement stmt=con.createStatement();
			    rs=stmt.executeQuery(SQL1);
			    String[] data = new String[5];
				while (rs.next()) {
					for (int j = 1; j <= 5; j++) {
						data[j - 1] = rs.getString(j); // ȡ�����ݿ��е�����װ�ص�������
					}
					dtm3.addRow(data); // ��Jtabl
					
				}
				  
			    con.close();
					//���ñ����б���ɫ�����б���ɫ��ͬ��	
	}catch(Exception err){
		
		
	}
		
		
	}
	
	
	@Override
	
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		 
	        
	      
		
	}

	public void addrow(JTable table) {
		// TODO Auto-generated method stub
				int row=table.getSelectedRow();
				
				String b=(String) table.getValueAt(row,0);
				
				
				
				button6.addActionListener(new ActionListener(){//����¼�  
			        public void actionPerformed(ActionEvent e){  
			           	String []da1={"",""};
			           	String []rowValues =da1; 
			         
			        dtm2.addRow(rowValues);  //���һ��             
			       int row1 = table2.getRowCount()-1;
			       table2.setRowSelectionInterval(row1, row1);                      
			          table2.setValueAt(b, row1, 6);
			         
					button6.setEnabled(false);
					
			        }  
			});
	}
	public void databaseSearch2(String SQL, int i) {
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
			    rs=stmt.executeQuery(SQL);
			    String[] data = new String[7];
				while (rs.next()) {
					for (int j = 1; j <=7; j++) {
						data[j - 1] = rs.getString(j); // ȡ�����ݿ��е�����װ�ص�������
					}
					dtm2.addRow(data); // ��Jtabl
					
				}
				  
			    con.close();
					//���ñ����б���ɫ�����б���ɫ��ͬ��	
	}catch(Exception err){
		
		
	}
		
	}

}
