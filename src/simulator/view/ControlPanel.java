package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	/**
	 * 
	 */
	
	private Controller ctrl;
	private int time;
	private RoadMap roadMap;
	
	private JButton loadEvent;
	private JButton changeWeather;
	private JButton changeCont;
	private JButton run;
	private JButton pause;
	private JButton exit;
	private JSpinner ticks;
	private JFileChooser fileChooser;
	private boolean _stopped;
	private static final long serialVersionUID = 1L;
	public ControlPanel ( Controller control) {
		ctrl = control;
		
		ctrl.addObserver(this);
		initGUI();
	}
	
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		JToolBar tb = new JToolBar();
		this.add(tb, BorderLayout.PAGE_START);
		
		// fileChooser button
		fileChooser = new JFileChooser();
		loadEvent = new JButton();
		loadEvent.setToolTipText("Carga del fichero de eventos");		
		loadEvent.setIcon(loadImage("resources/icons/open.png"));
		loadEvent.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		
		tb.add(loadEvent);
		tb.addSeparator();
		
		 // change Class Contamination Button
		changeCont = new JButton();
		changeCont.setToolTipText("Cambio de la Clase de Contaminación de un Vehículo");
		changeCont.setIcon(loadImage("resources/icons/co2class.png"));
		changeCont.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2Class();
				
			}
		});
		
		tb.add(changeCont);
		tb.addSeparator();
		
		// change Weather Button
		changeWeather = new JButton();
		changeWeather.setToolTipText("Cambio de condiciones atmosfericas de una carretera");
		changeWeather.setIcon(loadImage("resources/icons/weather.png"));
		changeWeather.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeWeather();
				
			}
		});
		tb.add(changeWeather);
		tb.addSeparator();
		
		// run button
		run = new JButton();
		run.setToolTipText("Ejecuta el simulador");
		run.setIcon(loadImage("resources/icons/run.png"));
		run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				start();
				run_sim((Integer)ticks.getValue());
				
			}
		});
		tb.add(run);
		tb.addSeparator();
		
		// pause button
		pause = new JButton();
		pause.setToolTipText("Parar el simulador");
		pause.setIcon(loadImage("resources/icons/stop.png"));
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop_sim();
			}
		});
		tb.add(pause);
		tb.addSeparator();
		
		// Spinner ticks
		tb.add(new JLabel ("Ticks:"));
		 ticks= new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		 ticks.setMaximumSize(new Dimension(80, 40));
		 ticks.setMinimumSize(new Dimension(80, 40));
		 ticks.setPreferredSize(new Dimension(80, 40));
		 tb.add(ticks);
		 tb.addSeparator();
		
		 // exit button
		exit = new JButton();
		exit.setToolTipText("Salir del programa");
		exit.setIcon(loadImage("resources/icons/exit.png"));
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( JOptionPane.showOptionDialog ( null, "Estas seguro de salir?", "Exit", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, new String[] {"Si" , "No"}, "No" ) == JOptionPane.YES_OPTION
						) {
					System.exit(0);
				}
				
			}
		});
		
		tb.add(Box.createGlue());
		tb.addSeparator();
		tb.add(exit);
		
	}
	
	private void loadFile() {
		
		
		if (fileChooser.showOpenDialog(this.getParent()) == JFileChooser.APPROVE_OPTION) {
			
			try {
				File f = fileChooser.getSelectedFile();
				ctrl.reset();
				ctrl.loadEvents(new FileInputStream(f));
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error", e.getMessage(),JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
	
	private void changeCO2Class() {
		ChangeCO2ClassDialog cd = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));
		
		if ( cd.open(roadMap) == 1) {
			
			List<Pair<String, Integer>> parejaList = new ArrayList<Pair<String,Integer>>();
			
			Pair<String, Integer> p = new Pair<String, Integer>( cd.getVehicle().getId(), cd.getCO2Class());
			parejaList.add(p);
			
			try {
				ctrl.addEvent( new SetContClassEvent(time +cd.getTicks(), parejaList));
			}
			
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	private void changeWeather() {
		
		ChangeWeatherDialog cw= new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this));
		
		if (cw.open(roadMap)== 1) {
			
			List<Pair<String, Weather>> parejaList = new ArrayList<Pair<String,Weather>>();
			
			Pair<String, Weather> p = new Pair<String, Weather>( cw.getRoad().getId(), cw.getWeather());
			parejaList.add(p);
			
			try {
				ctrl.addEvent( new SetWeatherEvent(time +cw.getTicks(), parejaList));
			}
			
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	private void start() {
		_stopped=false;
		enableToolBar(false);
	}
	
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		}
		
		else {
			enableToolBar(true);
			_stopped = true;
		}
	}
		
	private void enableToolBar(boolean b) {
		run.setEnabled(b);
		exit.setEnabled(b);
		changeCont.setEnabled(b);
		changeWeather.setEnabled(b);
		loadEvent.setEnabled(b);
	}


	private void stop_sim() {
			_stopped = true;
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
		this.time = time;
	}
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.roadMap = map;
		this.time = time;
		
	}
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.roadMap = map;
		this.time = time;
	}
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.roadMap = map;
		this.time = time;
		
	}
	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.roadMap = map;
		this.time = time;
	}
	@Override
	public void onError(String msg) {
		
	}
}
