package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final Color _BG_COLOR = Color.WHITE;
	private static final long serialVersionUID = 1L;
	private static final int _JRADIUS = 10;
	private static final Color _ROAD_COLOR = Color.BLACK;
	private static final Color ETIQ_ROAD_COLOR= Color.BLACK;
	private static final Color _RED_Light = Color.RED;
	private static final Color _GREEN_Light= Color.GREEN;
	private static final Color _STARTPOINT = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private RoadMap _map;
	private Image car;
	private Image sun;
	private Image cloud;
	private Image storm;
	private Image wind;
	private Image rain;
	
	public MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	
	private void initGUI() {
		car = loadImage("car.png");
		sun = loadImage("sun.png");
		cloud= loadImage("cloud.png");
		storm= loadImage("storm.png");
		wind= loadImage("wind.png");
		rain= loadImage("rain.png");
		
		setPreferredSize(new Dimension(300,200));
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getRoads().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		
		
		int x1 =50;
		int x2 = getWidth() - 100;
		int y = 50;
		for (int i = 0; i<_map.getRoads().size(); i++) {
			
			// circulo origen
			Color cruce_origin = _STARTPOINT;
			g.setColor(cruce_origin);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(_map.getRoads().get(i).getSrc().getId(), x1, y - 10);
			
			// etiqueta road
			g.setColor(ETIQ_ROAD_COLOR);
			g.drawString(_map.getRoads().get(i).getId(), x1 -30, y);
			g.setColor(_ROAD_COLOR);
			g.drawLine(x1, y, x2, y );
			
				// circulo final
			Color cruce_dest = _RED_Light;
			if (_map.getRoads().get(i).getDest().getGreenLightIndex() != -1 &&
					_map.getRoads().get(i).equals
					(_map.getRoads().get(i).getDest().getInRoads().
							get(_map.getRoads().get(i).getDest().getGreenLightIndex()))) {
				cruce_dest = _GREEN_Light;
			}
			
			g.setColor(cruce_dest);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(_map.getRoads().get(i).getDest().getId(), x2, y - 10);
			 // dibuja los vehiculos
			drawVehicles(g, x1, x2, y, _map.getRoads().get(i).getId());
			// dibuja el icono de weather
			g.drawImage(weatherImage(_map.getRoads().get(i).getWeather()) , x2+15, y -18, 32,32,this);
			
			// icono de contaminacion
			int c = (int) Math.floor(Math.min((double) _map.getRoads().get(i) .getTotalCO2()
					/(1.0 + (double) _map.getRoads().get(i).getContLimit()),1.0) /
					0.19);
			g.drawImage(loadImage("cont_" + c+ ".png"), x2 + 55, y - 18, 32,32, this);
			
			y+= 50;
		}
		
	}

	private void drawVehicles(Graphics g ,  int x1, int x2, int y, String roadId) {
		
		for (Vehicle v : _map.getRoad(roadId).getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) v.getRoad().getLength()));
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));
				
				g.drawImage(car, x, y - 8, 16, 16, this);
				g.drawString(v.getId(), x, y -8);
			}
		}
	}
	
	private Image weatherImage (Weather weather) {
		switch (weather) {
		case SUNNY:
			return sun;
		case CLOUDY:
			return cloud;
		case STORM:
			return storm;
		case RAINY:
			return rain;
		case WINDY:
			return wind;
		default: 
			return null;
		}
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
