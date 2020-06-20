import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JIUYIModifyQuery1 extends JFrame implements ActionListener{
	
	public JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9;
	public JTextField tx1,tx2,tx3,tx4,tx5,tx6,tx7;
	public static  DefaultTableModel dtm;
	Font f2=new Font("隶书", Font.BOLD, 30);
	Font f1=new Font("草书", Font.CENTER_BASELINE, 30);
	 public static JTable table;
	 String o;
	 private JComboBox jcbb1;
	 private JScrollPane JScrollPane=new JScrollPane();
	public JPanel panel=new JPanel();
	JButton button1=new JButton("查询");
	JButton button2=new JButton("修改");
	JButton button3=new JButton("删除");
	JButton button4=new JButton("诊疗明细操作");
	JButton button5=new JButton("用药明细操作");
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	JIUYIModifyQuery1(String Stitle){
		super(Stitle);
		panel.setLayout(null);
		 this.add(panel);
		 ImageIcon ic;               //按钮图片
		  ic = new ImageIcon("F:/课程设计/right_bg.jpg"); 
		  JLabel label = new JLabel(ic);//把背景图片显示在一个标签里面
		  
		 panel.add(label);
		 String columnNames[]={"就医档案编号","病人编号","病人姓名","就医档案录入时间","医生编号","医生姓名","所属科室","病因"};
		 dtm = new DefaultTableModel(columnNames,0){
        	public boolean isCellEditable(int rowIndex,int columnIndex) {
        		return false;
        		} 	
        };
  
        table = new JTable(dtm);
        JScrollPane.setViewportView(table);
        JScrollPane.setBounds(new  Rectangle(40,60,870,250));

        String SQL="select * from Medical_records" ;
		   databaseSearch(SQL,8);
        
		   //
			la5=new JLabel("病例编号：");
			la6=new JLabel("病人姓名：");
			la7=new JLabel("就医档案综合操作");
			la8=new JLabel("病人人数统计：");
			la9=new JLabel("人");
			la7.setFont(f2);
			la8.setFont(f1);
			la9.setFont(f1);
			tx5=new JTextField();
			tx6=new JTextField();
			tx7=new JTextField();
			tx7.setFont(f1);
			
			button2.setBounds(400, 400, 100, 50);
			button3.setBounds(600, 400, 100, 50);
			button4.setBounds(910,60, 100, 100);
			button5.setBounds(910,200, 100, 100);
			
			la7.setBounds(40, 0, 300, 50);
			la8.setBounds(800,350, 250, 50);
			la9.setBounds(870, 395, 50, 50);
			tx7.setBounds(800, 400, 60, 35);
			
			
			button4.addActionListener(this);
			button5.addActionListener(this);
			button2.addActionListener(this);
			panel.add(button4);	
			panel.add(button3);	
			panel.add(button2);	
			panel.add(button5);	
			panel.add(JScrollPane);	
			panel.add(la7);	
			panel.add(tx7);	
			panel.add(la8);
			panel.add(la9);
			tx7.setEditable(false);
		//设置表格隔行颜色
			setbgcolor();
		   
		   final JPanel panel1 = new JPanel();
			panel1.setLayout(null);
			panel1.setBackground(Color.YELLOW);
			panel1.setBorder(new TitledBorder(null, "查询操作", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			panel1.setBounds(50, 350, 300, 150);
			panel.add(panel1); 
			button1.setBounds(220, 80, 60, 40);
			panel1.add(button1);
			button1.setBackground(Color.white);
			la5.setBounds(20, 73, 80, 50);
			la6.setBounds(20, 73, 80, 50);
			la5.setVisible(false);
			tx6.setVisible(false);
			la6.setVisible(false);
			tx5.setVisible(false);
			panel1.add(la5);
			panel1.add(la6);
			tx5.setBounds(90, 80, 120, 35);
			panel1.add(tx5);
			tx6.setBounds(90, 80, 120, 35);
			panel1.add(tx6);
			
			Connection con;
			  con=connect.getConnection();
			  ResultSet rs;
			try{
				String sql1 = "select Count(*) from Medical_records";
	        	Statement stmt=con.createStatement();
			    rs=stmt.executeQuery(sql1);
	        	while(rs.next()){
	        		 o=rs.getString(1);
	        		
	        	}
				tx7.setText(o);
				
				
			}catch(Exception r){
				r.printStackTrace();
			}
			
			
			

			jcbb1 = new JComboBox();
			jcbb1.addActionListener(new ActionListener() {//下拉框添加事件，用于显示相应的查询文本框，以及标签
				public void actionPerformed(final ActionEvent e) {
					Object SelectName=jcbb1.getSelectedItem();
					   String selectNamecontent=SelectName.toString();
					  
					   if(selectNamecontent.equals("请选择查询种类"))
					   {
						   la5.setVisible(false);
						   la6.setVisible(false);
						   tx5.setVisible(false);
						   tx6.setVisible(false);
						   System.out.println("什么也不做");
						   return;
					   }
					   else if(selectNamecontent.equals("病例编号"))
					   {
						   la5.setVisible(true);
						   tx5.setVisible(true);
						   tx5.setText("");
						   la6.setVisible(false);
						   tx6.setVisible(false);
						   return;
					   }else if(selectNamecontent.equals("病人姓名"))
					   {
						   tx5.setVisible(false);
						   la5.setVisible(false);
						   tx6.setVisible(true);
						   la6.setVisible(true);
						   tx6.setText("");
						   return;
					   }
					  
					   //++++++++++++++++++++++++++++++++++++++++++++++++++
				}
			});
			
			jcbb1.setModel(new DefaultComboBoxModel(new String[] {"请选择查询种类", "病例编号", "病人姓名"}));
			jcbb1.setBounds(20, 28, 135, 25);
			panel1.add(jcbb1);

			//添加背景
			panel.add(label);
			label.setBounds(0, 0, 1100, 700);
		
			button3.addMouseListener(new MouseAdapter(){  //删除按钮实现删除记录的功能
				 public void mouseClicked(MouseEvent e) {
					 int row= table.getSelectedRow();//这句选择要删除的行
					 Connection con;
						  con=connect.getConnection();
					       Statement stmt; 
					    String   val = (String) table.getValueAt(row, 0);
						String 	sql="delete from Medical_records where MrId='"+val+"'";					
						try {
							stmt = con.createStatement();
							stmt.executeUpdate(sql);
							   JOptionPane.showMessageDialog(null,"  删除成功！","成功",JOptionPane.INFORMATION_MESSAGE);
						}catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						 if(row!=-1){  //这句判断是否有选中的行
							dtm.removeRow(row); }   //这句删除指定行 	 
				 }
			});
			
			table.addMouseListener(new MouseAdapter() {//设置TABLE双击鼠标事件
				   public void mouseClicked(MouseEvent e) {
					   if (e.getButton() == MouseEvent.BUTTON1) // 单击鼠标左键
					     if (e.getClickCount() == 2) { 
					    	 JIUYIModify c=new JIUYIModify();
					    	c.openDialog(table,dtm);
					    
					     }    
					   }			   
					  });
			button1.addMouseListener(new MouseAdapter() {//查询按钮添加鼠标事件，对应相应的查询功能
				public void mouseClicked(final MouseEvent e) {
					System.out.println("Good idea!!!");
					   Object SelectName=jcbb1.getSelectedItem();
					   String selectNamecontent=SelectName.toString();
					   if(selectNamecontent.equals("请选择查询种类"))
					   {
						   System.out.println("什么也不做");
						   return;
					   }
					   else if(selectNamecontent.equals("病例编号"))
					   {
						   if(tx5.getText().equals(""))
						   {
							   JOptionPane.showMessageDialog(null,"  请输入病例编号！","注意",JOptionPane.ERROR_MESSAGE);
							   return;
						   }
						   String sickName=tx5.getText().trim();
					       String SQL="select * from Medical_records where MrId like '%"+sickName+"%'";
						   databaseSearch(SQL,8);
						   
						   return;
					   }
					   
					   else if(selectNamecontent.equals("病人姓名"))
					   {
						   if(tx6.getText().equals(""))
						   {
							   JOptionPane.showMessageDialog(null,"  请输入病人姓名！","注意",JOptionPane.ERROR_MESSAGE);
							   return;
						   }
						   String sickCaseID=tx6.getText().trim();
						 
						String SQL="select * from Medical_records where PaName like '%"+sickCaseID+"%'";
						   databaseSearch(SQL,8);
						   return;
					   }
				}

				
			});
			
			
	}
	private void setbgcolor() {
		// TODO Auto-generated method stub
		//设置表格隔行背景颜色不同
		
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
	private void databaseSearch(String SQL, int i) {
		// TODO Auto-generated method stub
		Connection con;
	
		  con=connect.getConnection();
		  ResultSet rs;
		
		    try{
			}catch(Exception e1){
				e1.printStackTrace();
			}
	       try{
				int rowcount = dtm.getRowCount() - 1;
				if (rowcount != -1) {
					for (int i1 = rowcount; i1 >= 0; i1--) {
						dtm.removeRow(i1); // 删除Jtable中的所有行
					}
					dtm.setRowCount(0); // 将Jtable中的行数设为零
				}		
				Statement stmt=con.createStatement();
			    rs=stmt.executeQuery(SQL);
			    String[] data = new String[8];
				while (rs.next()) {
					for (int j = 1; j <= 8; j++) {
						data[j - 1] = rs.getString(j); // 取出数据库中的数组装载到数组中
					}
					dtm.addRow(data); // 在Jtabl
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==button2){
			JIUYIModify t=new JIUYIModify();
			t.openDialog(table, dtm);
			
		}
		else if(e.getSource()==button4){
			chufangModify y=new chufangModify("诊疗明细");
			
			 int row= table.getSelectedRow();
				String r=(String) table.getValueAt(row,0);
				 String SQL1="select * from DrugTable where MrId='"+r+"'and PeClass='其他类'" ;
				y.databaseSearch2(SQL1, 6);
				y.addrow(table);
		}
		else if(e.getSource()==button5){
			ProjectModify y=new ProjectModify("用药明细");
			
			 int row= table.getSelectedRow();
				String r=(String) table.getValueAt(row,0);
			
				 String SQL1="select * from DrugTable where MrId='"+r+"' and PeClass='药品类'or MrId='"+r+"'and PeClass='诊断类'" ;
				y.databaseSearch2(SQL1, 7);
				y.addrow(table);
		}
	}

	
	
	
	
	
}
