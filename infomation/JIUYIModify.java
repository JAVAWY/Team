import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class JIUYIModify extends JFrame implements ActionListener{
	private static final String KeyValue = null;
	public JLabel la0,la1,la2,la3,la4,la5,la6,la7,la8,la9;
	public JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9;
	public JButton button;Font f2=new Font("隶书", Font.BOLD, 25);
	public JComboBox jcbb2,comboBox,jcombobox1;
	 public java.sql.Connection con=null;
	 SqlLogin.jdbc connect=new SqlLogin().new jdbc();
	JIUYIModify(){
		JPanel panel2=new JPanel();
		 ImageIcon ic;               //按钮图片
		  ic = new ImageIcon("F:/课程设计/right_bg.jpg"); 
		  JLabel label = new JLabel(ic);//把背景图片显示在一个标签里面
		  
		panel2.setLayout(null);
		button=new JButton("保存");
		la0=new JLabel("修改就医档案");
		la1=new JLabel("就医档案编号:");
		la2=new JLabel("病人编号 :");
		la3=new JLabel("病人姓名 :");
		la4=new JLabel("录入时间 :");
		la5=new JLabel("医生编号 :");
		la6=new JLabel("医生姓名 :");
		la7=new JLabel("就医科室 :");
		la8=new JLabel("病因 :");
		la9=new JLabel("");
		
		
		t1=new JTextField();
		t2=new JTextField();
		t3=new JTextField();
		t4=new JTextField();
		t5=new JTextField();
		t6=new JTextField();
		t7=new JTextField();
		t8=new JTextField();
		
		
		
		
		t1.setBounds(90, 150, 100, 40);	
		 t1.setEditable(false);
		t2.setBounds(290,150,100,40);
		t3.setBounds(90,200,100,40);
		t4.setBounds(90,250,100,40);
		t5.setBounds(290, 250	, 80	, 40);
		t6.setBounds(90,300,120,40);
		t7.setBounds(290,300,120,40);
		t8.setBounds(290,200,120,40);
		
		t3.setEditable(false);
		t4.setEditable(false);
		t5.setEditable(false);
		t6.setEditable(false);
		t2.setEditable(false);
		
		la0.setBounds(10, 10, 200, 50);
		la0.setFont(f2);
		la1.setBounds(20,150,100,50);
		la2.setBounds(220,150,100,50);
		la3.setBounds(20,200,100,50);
		la4.setBounds(220,200,100,50);
		la5.setBounds(20,250,100,50);
		la6.setBounds(220,250,100,50);
		la7.setBounds(20,300,100,50);
		la8.setBounds(220,300,100,50);
		la9.setBounds(20,350,100,50);
		
		
		
		
		
	    Vector model1 = new Vector();
	    String locationid1 = "";
	    String locationname1 = "";
	    String locationdept1 = "";
	    try {
	    	
	      con=connect.getConnection();
	      Statement st = con.createStatement();
	      String sql = "select DrId, DrName,DeptName from Doctor";
	      ResultSet rss = st.executeQuery(sql);
	      while (rss.next()) {
	        locationid1 = rss.getString("DrName");
	        locationname1 = rss.getString("DrId");
	        locationdept1 = rss.getString("DeptName");
	        KeyValue Itemlocation1 = new KeyValue(locationname1, locationid1, locationdept1);
	        model1.addElement(Itemlocation1);
	      }

	    } catch (SQLException e) {
	      e.printStackTrace();
	    } 
	   
	    jcbb2 = new JComboBox(model1);
	    jcbb2.addActionListener(this);
	   
		
	
		jcbb2.setBounds(290, 250, 100, 40);
		
		
		panel2.add(jcbb2);
		panel2.add(button);
		button.setBounds(350, 400, 80, 50);
		button.addActionListener(this);
		label.setBounds(0, 0, 500, 500);
		
		panel2.add(la0);panel2.add(la1);panel2.add(la2);panel2.add(la3);panel2.add(la4);
		panel2.add(la5);panel2.add(la6);panel2.add(la7);panel2.add(la8);panel2.add(la9);
		panel2.add(t1);panel2.add(t2);panel2.add(t3);panel2.add(t4);panel2.add(t5);
		panel2.add(t6);panel2.add(t7);panel2.add(t8);panel2.add(label);
		this.add(panel2);
		this.setSize(450,500);   //设置窗口大小
		this.setResizable(false);    //设置不可调整窗口大小
	    this.setLocationRelativeTo(null);    
	    this.setVisible(true);
	    
	    
		
	}

	public void openDialog(JTable table, DefaultTableModel dtm) {
		// TODO Auto-generated method stub
		int rown;int coln;
		rown =  table.getSelectedRow();
	    coln=  table.getSelectedColumn();
	    String value0=(String) table.getValueAt(rown,0);
		String value1=(String) table.getValueAt(rown,1);
		String value2=(String) table.getValueAt(rown,2);
		String value3=(String) table.getValueAt(rown,3);
		String value4=(String) table.getValueAt(rown,4);
		String value5=(String) table.getValueAt(rown,5);
		String value6=(String) table.getValueAt(rown,6);
		String value7=(String) table.getValueAt(rown,7);
		
		
		

		t1.setText(value0);
		t2.setText(value1);
		t3.setText(value2);
		t4.setText(value4);
		t5.setText(value5);
		t6.setText(value6);
		t7.setText(value7);
		t8.setText(value3);
		
		
		
		button.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			Object aa=jcbb2.getSelectedItem();
 			   String se=aa.toString();
 			Connection con;
 		
 			  con=connect.getConnection();
 		       Statement stmt; 
 				String sql="update  Medical_records set PaId='"+t2.getText().trim()+"',PaName='"+t3.getText().trim()+"',MrDate='"+t8.getText().trim()+"',DrId='"+t4.getText().trim()+"',DrName='"+jcbb2.getSelectedItem().toString()+"',DeptName='"+t6.getText().trim()+"',SickCause='"+t7.getText().trim()+"' where MrId='"+t1.getText().trim()+"'";
 				try {
     				stmt = con.createStatement();
     				stmt.executeUpdate(sql);
     				JOptionPane.showMessageDialog(null,"修改成功","修改成功", JOptionPane.INFORMATION_MESSAGE, null);
     			    int row2=table.getSelectedRow();
     				String []xiugai=new String []{t1.getText(),t2.getText(),t3.getText(),t8.getText(),t4.getText(),jcbb2.getSelectedItem().toString(),t6.getText(),t7.getText()};

     					
     					dtm.removeRow(row2);
     					dtm.insertRow(row2, xiugai);	
     					
     					dtm.fireTableDataChanged();
     					
     					 dispose();

     				
     			}catch (SQLException e1) {
     				// TODO Auto-generated catch block
     				e1.printStackTrace();
     			}
 			
    			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(jcbb2==e.getSource()){
			KeyValue item1 =(KeyValue) jcbb2.getSelectedItem();
			   String a=item1.getId();
			   String b=item1.getdept();
			   t6.setText(b);
			    t4.setText(a);
			    
			 
		}
		
	}
	
	class KeyValue {
	    public String id;
	    public String name;
	    public String dept;
	    public KeyValue(String id, String name,String dept) {
	      this.id = id;
	      this.name = name;
	      this.dept = dept;
	    }

	    public KeyValue(Object selectedItem) {
	    	
	    	// TODO Auto-generated constructor stub
		}

		public String getId() {
	      return id;
	    }

	    public String getName() {
	      return name;
	    }
	    public String getdept() {
		      return dept;
		    }

	    public String toString() {
	      return name;
	    }
 }

}
