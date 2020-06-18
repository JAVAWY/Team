import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainPanel extends JFrame implements MouseListener{
	//基本组件
	public CardLayout layout=new CardLayout();   //卡片布局方式
	JPanel top,left,bottom,right;
	JLabel head;
	Font f1=new Font("隶书", Font.BOLD, 54);
	Font f2=new Font("隶书", Font.BOLD, 25);
	//菜单栏组件
	private JPanel pNorth,pSouth,subMenuContainer;
	private JButton item1,item2,item3,item4,item5,item6,item7,item8,htn[],gtn[],btn[],ctn[],dtn[],etn[],ftn[];
	//时间组件
	private JPanel timePanel;
	private JLabel displayArea;
	private String DEFAULT_TIME_FORMAT ="yyyy-MM-dd HH:mm:ss"; //设置时间格式
	private String time;
	private int ONE_SECOND = 1000;   //设置时间间隔
	JLabel label1 = new JLabel("欢迎你：");
	public MainPanel(String title){

		super("医院管理系统（管理员界面）");
		
		String aa=Login.user1;   //获取登录的用户名，放在标签中，显示在上面板。
		JLabel label8=new JLabel(aa);
		
		
		//窗口图标设置
		Toolkit tk=getToolkit();
		Image icon=tk.getImage("F:/课程设计/icon.jpg");
		setIconImage(icon);
		
		//时间控件设置
		timePanel=new JPanel();
		displayArea = new JLabel();  
		configTimeArea();  
		timePanel.add(displayArea);
		timePanel.setBounds(1000, 0, 200, 50); 
		timePanel.setOpaque(false);   //时间面板设置为透明
		
		//组件基本设置
		left=new JPanel();
		left.setBounds(5, 150, 150, 515);   
		this.add(left);
		
		right=new JPanel();
		right.setLayout(layout);
		right.setBounds(155, 155, 1045, 510);
		HomePage s=new HomePage();
		right.add(s.homePage);
		this.add(right);
		
		bottom=new JPanel();
		bottom.setLayout(null); 
		bottom.setBounds(0,666,1200,35);
		bottom.setBackground(Color.WHITE); 
		bottom.add(timePanel);   //添加时间控件
		this.add(bottom);
		
		
		top=new JPanel();  //设置背景图片"F:/课程设计/top_bg.jpg"
			ImageIcon background = new ImageIcon("F:/课程设计/top_bg.jpg"); 
			JLabel label = new JLabel(background);
	        top.setLayout(null);
	        label.setBounds(0,0,1200,145);
			top.setBounds(0, 0, 1200, 145); 
			this.add(top);
			top.add(label8);
			label8.setBounds(1080,90, 100, 50);
			label8.setFont(f2);
			
		head=new JLabel("医院信息管理系统");
		head.setFont(f1);
		head.setBounds(375,40,500,75);
		top.add(head);
		
		top.add(label1);
		label1.setBounds(980, 90, 200, 50);
		label1.setFont(f2);
		  top.add(label);
		
		//Left面板折叠式菜单设置,三面板网格式布局
		pNorth=new JPanel();
		pNorth.setLayout(new GridLayout(8,1));
        pSouth=new JPanel();
        subMenuContainer=new JPanel();
        
        subMenuContainer.setLayout(new GridLayout(4,1));
        
        item1=new JButton("首页");              //设置按钮
        item2=new JButton("病人信息管理");       
        item3=new JButton("就医档案管理");
        item4=new JButton("医生信息管理");
        item5=new JButton("科室信息管理");
        item6=new JButton("价格管理");
        item7=new JButton("收费管理");
        item8=new JButton("系统设置");
       
        item1.setPreferredSize(new Dimension(150, 47));   //优先设置按钮大小
        item2.setPreferredSize(new Dimension(150, 47));
        item3.setPreferredSize(new Dimension(150, 47));
        item4.setPreferredSize(new Dimension(150, 47));
        item5.setPreferredSize(new Dimension(150, 47));
        item6.setPreferredSize(new Dimension(150, 47));
        item7.setPreferredSize(new Dimension(150, 47));
        item7.setPreferredSize(new Dimension(150, 47));
        item8.setPreferredSize(new Dimension(150, 47));
        
        item1.setContentAreaFilled(false);item2.setContentAreaFilled(false);   //设置为透明
        item3.setContentAreaFilled(false);item4.setContentAreaFilled(false);
        item5.setContentAreaFilled(false);item6.setContentAreaFilled(false);
        item7.setContentAreaFilled(false);item8.setContentAreaFilled(false);
        
        pNorth.add(item1); pNorth.add(item2); pNorth.add(item3);   //添加按钮
        pNorth.add(item4); pNorth.add(item5); pNorth.add(item6); 
        pNorth.add(item7);pNorth.add(item8);
        
        btn=new JButton[2];        //设置子功能按钮
	    btn[0]=new JButton("信息录入"); 
	    btn[1]=new JButton("其他操作");
	    for(int i=0;i<btn.length;i++){
	    	btn[i].setBackground(Color.WHITE);   //设置按钮颜色
	    	btn[i].setPreferredSize(new Dimension(150,30));//设置大小
	    	btn[i].addMouseListener(this);
	    }    
	    
	    ctn=new JButton[3];        
	    ctn[0]=new JButton("档案录入"); 
	    ctn[1]=new JButton("处方单"); 
	    ctn[2]=new JButton("其他操作"); 
	    for(int i=0;i<ctn.length;i++){
	    	ctn[i].setBackground(Color.WHITE);
	    	ctn[i].setPreferredSize(new Dimension(150,30));
	    	ctn[i].addMouseListener(this);
	    }
	    
	    dtn=new JButton[2];        
	    dtn[0]=new JButton("信息录入"); 
	    dtn[1]=new JButton("其他操作"); 
	    for(int i=0;i<dtn.length;i++){
	    	dtn[i].setBackground(Color.WHITE);
	    	dtn[i].setPreferredSize(new Dimension(150,30));
	    	dtn[i].addMouseListener(this);
	    }      
	    
	    etn=new JButton[1];        
	    etn[0]=new JButton("综合操作"); 
	    for(int i=0;i<etn.length;i++){
	    	etn[i].setBackground(Color.WHITE);
	    	etn[i].setPreferredSize(new Dimension(150,30));
	    	etn[i].addMouseListener(this);
	    }   
	    
	    ftn=new JButton[1];        
	    ftn[0]=new JButton("综合操作"); 
	    for(int i=0;i<ftn.length;i++){
	    	ftn[i].setBackground(Color.WHITE);
	    	ftn[i].setPreferredSize(new Dimension(150,30));
	    	ftn[i].addMouseListener(this);
	    }
	    gtn=new JButton[2];        
	    gtn[0]=new JButton("结账页面"); 
	    gtn[1]=new JButton("综合操作"); 
	    for(int i=0;i<gtn.length;i++){
	    	gtn[i].setBackground(Color.WHITE);
	    	gtn[i].setPreferredSize(new Dimension(150,30));
	    	gtn[i].addMouseListener(this);
	    }
	    htn=new JButton[1];        
	    htn[0]=new JButton("修改密码"); 
	    
	    for(int i=0;i<htn.length;i++){
	    	htn[i].setBackground(Color.WHITE);
	    	htn[i].setPreferredSize(new Dimension(150,30));
	    	htn[i].addMouseListener(this);
	    }
	   
	    	
        left.add(pNorth,"North");   //按钮添加到left面板中
	    left.add(subMenuContainer,"Center");
	    left.add(pSouth,"South");
	    
	    
		//监听器添加
	    item1.addMouseListener(this);
	    item2.addMouseListener(this);
	    item3.addMouseListener(this);
	    item4.addMouseListener(this);
	    item5.addMouseListener(this);
	    item6.addMouseListener(this);
	    item7.addMouseListener(this);
	    item8.addMouseListener(this);
	    
	    
		//窗体设置
		this.setLayout(null);  
		this.setSize(1200,730);  
		this.setLocationRelativeTo(null);   //窗口居中显示
		this.setVisible(true);   
		this.setResizable(false);   //窗体不可改变大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		MainPanel frm=new MainPanel("医院信息管理系统");
	}
	
	//时间控件方法
	private void configTimeArea(){
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new JLabelTimerTask(),new Date(), ONE_SECOND);
	}
	protected class JLabelTimerTask extends TimerTask{
	    SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
	    
		public void run() {
			time = dateFormatter.format(Calendar.getInstance().getTime());
			displayArea.setText(time);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==item1){
			this.add(right);
			HomePage s=new HomePage();   //调用主页的类。
			right.add(s.homePage, "homePage");
			layout.next(right);
		}
		 //设置折叠按钮功能，先把南面板清空，让后在给各面板设置布局方式，添加按钮也添加子功能按钮，清楚别的子功能按钮 
		if(e.getSource()==item2){
			pSouth.removeAll();   
			pNorth.setLayout(new GridLayout(2,1)); 
			pSouth.setLayout(new GridLayout(6,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pSouth.add(item3);
            pSouth.add(item4);
            pSouth.add(item5);
            pSouth.add(item6);
            pSouth.add(item7);
            pSouth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.add(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.remove(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.remove(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.remove(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.remove(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.remove(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item3){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(3,1)); 
			pSouth.setLayout(new GridLayout(5,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pSouth.add(item4);
            pSouth.add(item5);
            pSouth.add(item6);
            pSouth.add(item7);
            pSouth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.remove(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.add(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.remove(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.remove(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.remove(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.remove(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item4){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(4,1)); 
			pSouth.setLayout(new GridLayout(4,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pSouth.add(item5);
            pSouth.add(item6);
            pSouth.add(item7);
            pSouth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.remove(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.remove(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.add(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.remove(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.remove(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.remove(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item5){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(5,1)); 
			pSouth.setLayout(new GridLayout(3,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pSouth.add(item6);
            pSouth.add(item7);
            pSouth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.remove(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.remove(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.remove(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.add(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.remove(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.remove(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item6){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(6,1)); 
			pSouth.setLayout(new GridLayout(2,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pNorth.add(item6);
            pSouth.add(item7);
            pSouth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.remove(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.remove(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.remove(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.remove(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.add(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.remove(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		if(e.getSource()==item7){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(7,1)); 
			pSouth.setLayout(new GridLayout(1,1)); 
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pNorth.add(item6);
            pNorth.add(item7);
            pSouth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.remove(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.remove(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.remove(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.remove(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.remove(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.add(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		if(e.getSource()==item8){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(8,1)); 
			
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pNorth.add(item6);
            pNorth.add(item7);
            pNorth.add(item8);
            for(int i=0;i<btn.length;i++)
            	subMenuContainer.remove(btn[i]);
            for(int i=0;i<ctn.length;i++)
            	subMenuContainer.remove(ctn[i]);
            for(int i=0;i<dtn.length;i++)
            	subMenuContainer.remove(dtn[i]);
            for(int i=0;i<etn.length;i++)
                subMenuContainer.remove(etn[i]);
            for(int i=0;i<ftn.length;i++)
            	subMenuContainer.remove(ftn[i]);
            for(int i=0;i<gtn.length;i++)
            	subMenuContainer.remove(gtn[i]);
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.add(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		else if(e.getSource()==btn[0]){
			this.add(right);
         	PatientInput s=new PatientInput();	   
         	
         	right.add(s.patientInput,"patientInput");
         	layout.next(right);
		}
		else if(e.getSource()==btn[1]){
			this.add(right);
			PatientManage s=new PatientManage();
			
			right.add(s.patientManage, "patientManage");
			layout.next(right);
		}
		else if(e.getSource()==ctn[0]){
			this.add(right);
			MedicalRecordsInput s=new MedicalRecordsInput();
			right.add(s.medicalRecordsInput, "medicalRecordsInput");
			layout.next(right);
		}
		else if(e.getSource()==ctn[1]){
			this.add(right);
			drug s=new drug();
			right.add(s.chufangInput, "medicalRecordsInput");
			layout.next(right);
		}
		else if(e.getSource()==ctn[2]){
			this.add(right);
			JIUYIModifyQuery1 s=new JIUYIModifyQuery1("4");
			right.add(s.panel, "medicalRecordsInput");
			layout.next(right);
		}
		else if(e.getSource()==dtn[0]){
			this.add(right);
			DoctorInput s=new DoctorInput();
			right.add(s.doctorInput, "doctorInput");
			layout.next(right);
		}
		else if(e.getSource()==dtn[1]){
			this.add(right);
			DoctorManage s=new DoctorManage();
			right.add(s.doctorManage, "doctorManage");
			layout.next(right);
		}
		else if(e.getSource()==etn[0]){
			this.add(right);
			DepartmentManage s=new DepartmentManage();
			right.add(s.departmentManage, "departmentManage");
			layout.next(right);
		}
		else if(e.getSource()==ftn[0]){
			this.add(right);
			PriceManage s=new PriceManage();
			right.add(s.priceManage,"priceManage");
			layout.next(right);
		}
		else if(e.getSource()==gtn[0]){

	    	   this.add(right);
	    	   Charge t =new Charge();
	      	 
	      	 right.add(t.panel2,"第一0个面板");
	         	layout.next(right);
		}
		else if(e.getSource()==gtn[1]){

	    	   this.add(right);
	    	   ChargeQuery t =new ChargeQuery();
	      	 
	      	 right.add(t.panel2,"第一0个面板");
	         	layout.next(right);
		}	
		else if(e.getSource()==htn[0]){

	    	   this.add(right);
	    	   PassWordModify t =new PassWordModify("密码修改");
	      	 
	      	 right.add(t.panel2,"第一0个面板");
	         	layout.next(right);
	    	 
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	

}
