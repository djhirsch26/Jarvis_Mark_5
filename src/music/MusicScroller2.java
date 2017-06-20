package music;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MusicScroller2 extends JPanel{

	private int XPosOnLine=0;
	private int barHeight=5;
	private AudioFile af;
	private Timer t;
	private boolean isRunning=false;

	public MusicScroller2(AudioFile af) {
		this.af=af;
		initMouseListener();
		repaint();
	}

	public void start() {
		isRunning=true;
		ActionListener TTask = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double curFrac = ((double) af.getCurrentPos())/af.getSize();
				if(!af.isFinished() && isRunning) {
					setPos(curFrac);
					repaint();
					System.out.println("Im repainting: music scroller bar");
				}
				else {
					System.out.println("in the else part: music scroller bar\t" + t.isRunning());
					setPos(curFrac);
					repaint();
					if(!isRunning) {
						((Timer)e.getSource()).stop();
					}
				}

			}
		};
		t = new Timer(500,TTask);
		t.start();
		repaint();
	}

	public void stop() {
		//Redundant but useful as a concurrency check
		t.stop();
		repaint();
		System.out.println("STOP: music scroller bar");
		isRunning=false;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, this.getWidth(), barHeight);
		g.setColor(Color.red);
		g.fillOval(XPosOnLine, 0, barHeight, barHeight);
	}


	public void setPos(double frac) {
		XPosOnLine = (int)(frac * (this.getWidth()-barHeight*2));
	}

	private int getBarWidth() {
		return this.getWidth();
	}

	public boolean isRunning() {
		return isRunning;
	}

	private void initMouseListener() {
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("MOUSE PRESSED\nMouse clicked at: " + e.getX());
				if(e.getX()>0 && e.getX()<getBarWidth()) {
					af.setPosition((int)(af.getSize() * ((double)e.getX())/getBarWidth()));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("MOUSE Released");
			}
		});
	}
}
