package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 */
	private Controller ctrl;
	private static final long serialVersionUID = 1L;
	private List<Road> roadList;
	private String[] colName = {"Id", "Lenght", "Weather", "Max.Speed","Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller control) {
		ctrl = control;
		ctrl.addObserver(this);
		roadList = new ArrayList<Road>();	
	}
	
	
	@Override
	public String getColumnName( int c) {
		// TODO Auto-generated method stub
		return this.colName [c];
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colName.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return roadList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object s = null;
		switch (columnIndex) {
		case 0:
			
			s = roadList.get(rowIndex).getId();
			break;
		case 1:
			s = roadList.get(rowIndex).getLength();
			break;
		case 2:
			s = roadList.get(rowIndex).getWeather().toString();
			break;
		case 3:
			s = roadList.get(rowIndex).getMaxSpeed();
			break;

		case 4:
			s = roadList.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = roadList.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = roadList.get(rowIndex).getContLimit();
			break;
		
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		roadList = map.getRoads();
		fireTableDataChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		roadList = map.getRoads();
		fireTableDataChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		roadList = map.getRoads();
		fireTableDataChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		roadList = map.getRoads();
		fireTableDataChanged();
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
