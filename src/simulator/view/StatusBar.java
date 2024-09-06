package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller ctrl;
	private int time;
	private String event;
	private JLabel time_label;
	private JLabel mensaje_label;
	
	public StatusBar ( Controller control) {
		ctrl =  control;
		ctrl.addObserver(this);
		this.time = 0;
		initGUI();
	}

	private void initGUI(){
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		 time_label = new JLabel("Time:" + time);
		 mensaje_label = new JLabel(event); 
		
		this.add(time_label);
		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setBackground(Color.GRAY);
		separator.setPreferredSize(new Dimension(10, 30));
		
		this.add(separator);
		this.add(mensaje_label);
	}	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.time = time;
		event = "";
		time_label.setText("Time:" + time);
		mensaje_label.setText(event);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.time = time;
		event = "";
		time_label.setText("Time:" + time);
		mensaje_label.setText(event);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.time = time;
		event = "Event Added" + "(" + e.toString() + ")";
		time_label.setText("Time:" + time);
		mensaje_label.setText(event);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.time = time;
		event = "";
		time_label.setText("Time:" + time);
		mensaje_label.setText(event);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.time = time;
		event = "";
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
}
