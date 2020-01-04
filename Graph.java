package DesmosusingJava;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

@SuppressWarnings("serial")//a simple graphing tool
public class Graph extends JFrame implements Runnable {//this does not support parametric or polar equations
    private int [] xpoints;
    private int [] ypoints;
    private int startx=0;
    private int starty=0;
	private int fps=120;
	private boolean canvas=true;
	private Polygon graph;
	private Color currentcolor=Color.black;
	private int len;
	public Graph (int len) {
		this.setTitle("A simple graphing tool");
		this.setSize(800,800);
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.xpoints=new int[len];
		this.ypoints =new int [len];
		this.len=len;
		graph=new Polygon();
		Thread t=new Thread(this);
		t.start();
		this.setFocusable(true);
	}
	public Graph(int len, int fps) {
		this(len);
		this.fps=fps;
	}
	@SuppressWarnings("unused")
	public static void main(String [] args) {
		Graph g=new Graph(790,60);//num xpoints ==num ypoints?
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long time=System.nanoTime();
		double convert=1000000000.0/this.fps;//(nanoseconds*seconds/6o frames)
		double delta=0;
		while(true) {
			long current =System.nanoTime();
			delta+=(current-time)/convert; //we get the number of frames per second by doing this
			while(delta>=1) {
			//	this.draw((Graphics2D)));
				this.update();
				delta--;
			}
			this.render();
			time=current;
		}
		
	}
	

	private void render() {
		BufferStrategy buffs=this.getBufferStrategy();
		if(buffs==null) {
			this.createBufferStrategy(3);
			buffs=this.getBufferStrategy();
		}
		Graphics g=buffs.getDrawGraphics();
		if(this.canvas) {
			g.setColor(this.getBackground());
			g.fillRect(0,0, this.getWidth(),this.getHeight());
		}
		this.draw((Graphics2D) g);
		g.dispose();
		buffs.show();
	}
	private void update() {
		this.currentcolor=Color.black;
		if(startx<this.graph.xpoints.length && starty<this.graph.ypoints.length) {
		this.graph.xpoints[startx]=startx;
		/*This is the default function used*/this.graph.ypoints[starty]=(int) ( (50*(Math.sin((double)startx*(Math.PI/30)))+400)); 
	

		startx++;
		starty++;
	}
		else {
			startx=0;
			starty=0;
			
		}
	}
	public void draw(Graphics2D win) {
		win.setColor(this.currentcolor);
		this.graph.xpoints=this.xpoints;
		this.graph.ypoints=this.ypoints;
		this.graph.npoints=this.len;
		win.drawPolyline(this.graph.xpoints, this.graph.ypoints,this.graph.npoints);
		//this is much better
		
	}
}
