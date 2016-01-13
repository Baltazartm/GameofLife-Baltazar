import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JSlider;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;;


public class GoL extends JFrame {

	final int wth=80, hth=80;
	boolean[] [] currentMove = new boolean[hth] [wth];
	boolean[] [] nextMove = new boolean[hth] [wth];
	boolean strt;
	Image offScrImg;
	Graphics offScrGraph;
	
	JPanel jPnl;
	JButton jBtn;
	JButton jBtn2;
	JSlider speed;
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 700;
	static final int FPS_INIT = 0;	
	long delay = 0;
	Timer time;
	TimerTask task;
	
	// The Game of life algorithm
	
	private boolean rules(int i, int j){
		int friends = 0;
		if(j>0){
			if(currentMove[i][j-1]) friends++;
			if(i>0) if(currentMove[i-1][j-1]) friends++;
			if(i<hth-1) if(currentMove[i+1][j-1]) friends++;
		}
		if(j<wth-1){
			if(currentMove[i][j+1]) friends++;
			if(i>0) if(currentMove[i-1][j+1]) friends++;
			if(i<hth-1) if(currentMove[i+1][j+1]) friends++;
		}
		if(i>0) if(currentMove[i-1][j]) friends++;
		if(i<hth-1) if(currentMove[i+1][j]) friends++;
		if(friends == 3) return true;
		if(currentMove[i][j] && friends == 2) return true;
		return false;
	}
	
	public void rpaint(){
		offScrGraph.setColor(jPnl.getBackground());
		offScrGraph.fillRect(0, 0, jPnl.getWidth(), jPnl.getHeight());
		for(int i=0; i<hth; i++){
			for(int j=0; j<wth; j++){
				if(currentMove[i][j]){
					offScrGraph.setColor(Color.YELLOW);
					int x = j * jPnl.getWidth()/wth;
					int y = i * jPnl.getHeight()/hth;
					offScrGraph.fillRect(x, y, jPnl.getWidth()/wth, jPnl.getHeight()/hth);
				}
			}
		}
		// draw grid
		offScrGraph.setColor(Color.BLACK);
		for(int i=1;i<hth;i++){
			int y = i * jPnl.getHeight()/hth;
			offScrGraph.drawLine(0, y, jPnl.getWidth(), y);
		}
		for(int j=1;j<wth;j++){
			int x=j*jPnl.getWidth()/wth;
			offScrGraph.drawLine(x, 0, x, jPnl.getHeight());
		}
		jPnl.getGraphics().drawImage(offScrImg, 0, 0, jPnl);
	}
	


private void initComponents(){
	
	jPnl = new JPanel();
	jBtn = new javax.swing.JButton();
	jBtn2 = new javax.swing.JButton();
	
	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	
	jPnl.setBackground(new Color(102, 102, 102));
	jPnl.addMouseMotionListener(new MouseMotionAdapter() {
		public void mouseDragged(java.awt.event.MouseEvent evnt){
			jPnlMouseDragged(evnt);
		}
	});
	jPnl.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent evnt) {
			jpnlMouseClicked(evnt);
		}
	});
	jPnl.addComponentListener(new java.awt.event.ComponentAdapter() {
		public void componentResized(java.awt.event.ComponentEvent evnt){
			jPnlComponentResized(evnt);
		}
	});
	
	
	javax.swing.GroupLayout jPnlLayout = new javax.swing.GroupLayout(jPnl);
	jPnl.setLayout(jPnlLayout);
	jPnlLayout.setHorizontalGroup(jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGap(0, 0, Short.MAX_VALUE)
			);
	jPnlLayout.setVerticalGroup(
			jPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGap(0, 293, Short.MAX_VALUE)
			);
	
	//start button
	jBtn.setText("Start Life");
	jBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evnt) {
			// TODO Auto-generated method stub
			jBtnActionPerformed(evnt);
		}
	});
	
	// reset button
	jBtn2.setText("Destroy");
	jBtn2.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evnt) {
			// TODO Auto-generated method stub
			jBtn2ActionPerformed(evnt);
		}
	});
	
	// speed slider
	
	JLabel speedLabel = new JLabel("speed", JLabel.CENTER);
	speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	speed = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
	
	
	
	
			
	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	layout.setHorizontalGroup(
		layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
					.addComponent(jPnl, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(speed, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addGroup(layout.createSequentialGroup()
								.addGap(59)
								.addComponent(speedLabel)))
						.addPreferredGap(ComponentPlacement.RELATED, 640, Short.MAX_VALUE)
						.addComponent(jBtn, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jBtn2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap())
	);
	layout.setVerticalGroup(
		layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jPnl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(jBtn2)
							.addComponent(jBtn)))
					.addGroup(layout.createSequentialGroup()
						.addGap(6)
						.addComponent(speedLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(speed, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap())
	);
    getContentPane().setLayout(layout);

    pack();

	}


/**
 * Create the frame.
 */
public GoL() {
	initComponents();
	offScrImg = createImage(jPnl.getWidth(), jPnl.getHeight());
	offScrGraph = offScrImg.getGraphics();
	time = new Timer();
	task = new TimerTask(){
		
		public void run(){
			if(strt){
				for(int i=0;i<hth;i++){
					for(int j=0;j<wth;j++){
						nextMove[i][j] = rules(i,j);
				}
			}
				for(int i=0;i<hth;i++){
					for(int j=0;j<wth;j++){
						currentMove[i][j] = nextMove[i][j];
				}
			}
				rpaint();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	// speed slider listener
	speed.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent evnt) {
			JSlider source = (JSlider)evnt.getSource();
			if(source.getValueIsAdjusting()){
				delay = (long)source.getValue();
				System.out.println(delay);
			}
		}
	});
	time.scheduleAtFixedRate(task, 0, 100);
	//System.out.println("gol ");
	rpaint();
}



	private void jpnlMouseClicked(MouseEvent evnt){
		int j = wth * evnt.getX() /jPnl.getWidth();
		int i = hth * evnt.getY() /jPnl.getHeight();
		if(SwingUtilities.isLeftMouseButton(evnt)){
			currentMove[i][j] = true;
		}else currentMove[i][j] = false;
		rpaint();
	}
	private void jPnlComponentResized(ComponentEvent evnt){
		offScrImg = createImage(jPnl.getWidth(), jPnl.getHeight());
		offScrGraph = offScrImg.getGraphics();
		rpaint();
	}
	private void jBtnActionPerformed(ActionEvent evnt){
		strt = !strt;
		if(strt) jBtn.setText("Pause");
		else jBtn.setText("Start life");
		rpaint();
	}
	private void jBtn2ActionPerformed(ActionEvent evnt){
		currentMove = new boolean[hth][wth];
		rpaint();
	}
	private void jPnlMouseDragged(MouseEvent evnt){
		int j = wth * evnt.getX() /jPnl.getWidth();
		int i = hth * evnt.getY() /jPnl.getHeight();
		if(SwingUtilities.isLeftMouseButton(evnt)){
			currentMove[i][j] = true;
		}else currentMove[i][j] = false;
		rpaint();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch (ClassNotFoundException ex){
			java.util.logging.Logger.getLogger(GoL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}catch (InstantiationException ex){
			java.util.logging.Logger.getLogger(GoL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}catch (IllegalAccessException ex){
			java.util.logging.Logger.getLogger(GoL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}catch (javax.swing.UnsupportedLookAndFeelException ex){
			java.util.logging.Logger.getLogger(GoL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GoL frame = new GoL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}