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

public class MainPanel11 extends JFrame implements MouseListener{
	//�������
	public static String namew;
	public static String user;
	public static  String user1;
	public static String password;
	public CardLayout layout=new CardLayout();   //��Ƭ���ַ�ʽ
	JPanel top,left,bottom,right;
	JLabel head;
	Font f1=new Font("����", Font.BOLD, 54);
	Font f2=new Font("����", Font.BOLD, 25);
	//�˵������
	private JPanel pNorth,pSouth,subMenuContainer;
	private JButton item1,item2,item3,item4,item5,item6,item7,item8,htn[],gtn[],btn[],ctn[],dtn[],etn[],ftn[];
	//ʱ�����
	private JPanel timePanel;
	private JLabel displayArea;
	private String DEFAULT_TIME_FORMAT ="yyyy-MM-dd HH:mm:ss"; //����ʱ���ʽ
	private String time;
	private int ONE_SECOND = 1000;   //����ʱ����
	JLabel label1 = new JLabel("��ӭ�㣺");
	public MainPanel11(String title){

		super("ҽԺ����ϵͳ��ҽ�����棩");
		
		String aa=Login.user1;   //��ȡ��¼���û��������ڱ�ǩ�У���ʾ������塣
		JLabel label8=new JLabel(aa);
		
		
		//����ͼ������
		Toolkit tk=getToolkit();
		Image icon=tk.getImage("F:/�γ����/icon.jpg");
		setIconImage(icon);
		
		//ʱ��ؼ�����
		timePanel=new JPanel();
		displayArea = new JLabel();  
		configTimeArea();  
		timePanel.add(displayArea);
		timePanel.setBounds(1000, 0, 200, 50); 
		timePanel.setOpaque(false);   //ʱ���������Ϊ͸��
		
		//�����������
		
		
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
		bottom.add(timePanel);   //���ʱ��ؼ�
		this.add(bottom);
		
		
		top=new JPanel();  //���ñ���ͼƬ"F:/�γ����/top_bg.jpg"
			ImageIcon background = new ImageIcon("F:/�γ����/top_bg.jpg"); 
			JLabel label = new JLabel(background);
	        top.setLayout(null);
	        label.setBounds(0,0,1200,145);
			top.setBounds(0, 0, 1200, 145); 
			this.add(top);
			top.add(label8);
			label8.setBounds(1080,90, 100, 50);
			label8.setFont(f2);
			
		head=new JLabel("ҽԺ��Ϣ����ϵͳ");
		head.setFont(f1);
		head.setBounds(375,40,500,75);
		top.add(head);
		
		top.add(label1);
		label1.setBounds(980, 90, 200, 50);
		label1.setFont(f2);
		  top.add(label);
		
		//Left����۵�ʽ�˵�����,���������ʽ����
		pNorth=new JPanel();
		pNorth.setLayout(new GridLayout(7,1));
        pSouth=new JPanel();
        subMenuContainer=new JPanel();
        
        subMenuContainer.setLayout(new GridLayout(4,1));
        
        item1=new JButton("��ҳ");              //���ð�ť
        item2=new JButton("������Ϣ����");       
        item3=new JButton("��ҽ��������");
        item4=new JButton("ҽ����Ϣ����");
        item5=new JButton("������Ϣ����");
        item6=new JButton("�۸����");
       
        item8=new JButton("ϵͳ����");
       
        item1.setPreferredSize(new Dimension(150, 47));   //�������ð�ť��С
        item2.setPreferredSize(new Dimension(150, 47));
        item3.setPreferredSize(new Dimension(150, 47));
        item4.setPreferredSize(new Dimension(150, 47));
        item5.setPreferredSize(new Dimension(150, 47));
        item6.setPreferredSize(new Dimension(150, 47));

       
        item8.setPreferredSize(new Dimension(150, 47));
        
        item1.setContentAreaFilled(false);item2.setContentAreaFilled(false);   //����Ϊ͸��
        item3.setContentAreaFilled(false);item4.setContentAreaFilled(false);
        item5.setContentAreaFilled(false);item6.setContentAreaFilled(false);
      item8.setContentAreaFilled(false);
        
        pNorth.add(item1); pNorth.add(item2); pNorth.add(item3);   //��Ӱ�ť
        pNorth.add(item4); pNorth.add(item5); pNorth.add(item6); 
        pNorth.add(item8);
        
        btn=new JButton[2];        //�����ӹ��ܰ�ť
	    btn[0]=new JButton("��Ϣ¼��"); 
	    btn[1]=new JButton("��������");
	    for(int i=0;i<btn.length;i++){
	    	btn[i].setBackground(Color.WHITE);   //���ð�ť��ɫ
	    	btn[i].setPreferredSize(new Dimension(150,30));//���ô�С
	    	btn[i].addMouseListener(this);
	    }    
	    
	    ctn=new JButton[3];        
	    ctn[0]=new JButton("����¼��"); 
	    ctn[1]=new JButton("������"); 
	    ctn[2]=new JButton("��������"); 
	    for(int i=0;i<ctn.length;i++){
	    	ctn[i].setBackground(Color.WHITE);
	    	ctn[i].setPreferredSize(new Dimension(150,30));
	    	ctn[i].addMouseListener(this);
	    }
	    
	    dtn=new JButton[1];        
	    
	    dtn[0]=new JButton("��������"); 
	    for(int i=0;i<dtn.length;i++){
	    	dtn[i].setBackground(Color.WHITE);
	    	dtn[i].setPreferredSize(new Dimension(150,30));
	    	dtn[i].addMouseListener(this);
	    }      
	    
	    etn=new JButton[1];        
	    etn[0]=new JButton("�ۺϲ���"); 
	    for(int i=0;i<etn.length;i++){
	    	etn[i].setBackground(Color.WHITE);
	    	etn[i].setPreferredSize(new Dimension(150,30));
	    	etn[i].addMouseListener(this);
	    }   
	    
	    ftn=new JButton[1];        
	    ftn[0]=new JButton("�ۺϲ���"); 
	    for(int i=0;i<ftn.length;i++){
	    	ftn[i].setBackground(Color.WHITE);
	    	ftn[i].setPreferredSize(new Dimension(150,30));
	    	ftn[i].addMouseListener(this);
	    }
	   
	    htn=new JButton[1];        
	    htn[0]=new JButton("�޸�����"); 
	    
	    for(int i=0;i<htn.length;i++){
	    	htn[i].setBackground(Color.WHITE);
	    	htn[i].setPreferredSize(new Dimension(150,30));
	    	htn[i].addMouseListener(this);
	    }
	   
	    	
        left.add(pNorth,"North");   //��ť��ӵ�left�����
	    left.add(subMenuContainer,"Center");
	    left.add(pSouth,"South");
	    
	    
		//���������
	    item1.addMouseListener(this);
	    item2.addMouseListener(this);
	    item3.addMouseListener(this);
	    item4.addMouseListener(this);
	    item5.addMouseListener(this);
	    item6.addMouseListener(this);
	   
	    item8.addMouseListener(this);
	    
	    
		//��������
		this.setLayout(null);  
		this.setSize(1200,730);  
		this.setLocationRelativeTo(null);   //���ھ�����ʾ
		this.setVisible(true);   
		this.setResizable(false);   //���岻�ɸı��С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		MainPanel11 frm=new MainPanel11("ҽԺ��Ϣ����ϵͳ");
	}
	
	//ʱ��ؼ�����
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
			HomePage s=new HomePage();   //������ҳ���ࡣ
			right.add(s.homePage, "homePage");
			layout.next(right);
		}
		 //�����۵���ť���ܣ��Ȱ��������գ��ú��ڸ���������ò��ַ�ʽ����Ӱ�ťҲ����ӹ��ܰ�ť���������ӹ��ܰ�ť 
		if(e.getSource()==item2){
			pSouth.removeAll();   
			pNorth.setLayout(new GridLayout(2,1)); 
			pSouth.setLayout(new GridLayout(5,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pSouth.add(item3);
            pSouth.add(item4);
            pSouth.add(item5);
            pSouth.add(item6);
           
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
           
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item3){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(3,1)); 
			pSouth.setLayout(new GridLayout(4,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pSouth.add(item4);
            pSouth.add(item5);
            pSouth.add(item6);
           
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
          
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item4){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(4,1)); 
			pSouth.setLayout(new GridLayout(3,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pSouth.add(item5);
            pSouth.add(item6);
            
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
           
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item5){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(5,1)); 
			pSouth.setLayout(new GridLayout(2,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pSouth.add(item6);
           
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
         
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item6){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(6,1)); 
			pSouth.setLayout(new GridLayout(1,1));
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pNorth.add(item6);
           
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
          
            for(int i=0;i<htn.length;i++)
            	subMenuContainer.remove(htn[i]);
            validate();
            getContentPane().repaint();
		}
		
		if(e.getSource()==item8){
			pSouth.removeAll();
			pNorth.setLayout(new GridLayout(7,1)); 
			
			pNorth.add(item1);
			pNorth.add(item2);
            pNorth.add(item3);
            pNorth.add(item4);
            pNorth.add(item5);
            pNorth.add(item6);
           
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
			s.delete.setEnabled(false);
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
			s.button3.setEnabled(false);
			right.add(s.panel, "medicalRecordsInput");
			layout.next(right);
		}
		
		else if(e.getSource()==dtn[0]){
			this.add(right);
			DoctorManage s=new DoctorManage();
			s.delete.setEnabled(false);
			
			right.add(s.doctorManage, "doctorManage");
			layout.next(right);
		}
		else if(e.getSource()==etn[0]){
			this.add(right);
			DepartmentManage s=new DepartmentManage();
			s.delete.setEnabled(false);
			s.modify.setEnabled(false);
			s.save.setEnabled(false);
			right.add(s.departmentManage, "departmentManage");
			layout.next(right);
		}
		else if(e.getSource()==ftn[0]){
			this.add(right);
			PriceManage s=new PriceManage();
			s.delete.setEnabled(false);
			s.modify.setEnabled(false);
			s.save.setEnabled(false);
			right.add(s.priceManage,"priceManage");
			layout.next(right);
		}
	
		else if(e.getSource()==htn[0]){

	    	   this.add(right);
	    	   PassWordModify t =new PassWordModify("�����޸�");
	      	 
	      	 right.add(t.panel2,"��һ0�����");
	         	layout.next(right);
	    	 
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	

}
