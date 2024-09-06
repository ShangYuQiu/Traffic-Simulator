package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel  extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 */
	private Controller ctrl;
	private static final long serialVersionUID = 1L;
	private List<Vehicle> vehiclesList;
	private String[] colName = {"Id","Status","Iterinary","CO2 Class", "Max.Speed", "Speed", "Total CO2", "Distances"}; 
	
	
	public VehiclesTableModel(Controller _ctrl) {
		ctrl = _ctrl;
		vehiclesList = new ArrayList<Vehicle>();
		ctrl.addObserver(this);
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
		return vehiclesList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = vehiclesList.get(rowIndex).getId();
			break;
		case 1:
			switch (vehiclesList.get(rowIndex).getStatus()) {
			case PENDING:
			s = "Pending";
			break;
			case TRAVELING:
			s = vehiclesList.get(rowIndex).getRoad().getId() + ":" +vehiclesList.get(rowIndex).getLocation();
			break;
			case WAITING:
			s = "Waiting:"+vehiclesList.get(rowIndex).getRoad().getDest().getId();
			break;
			case ARRIVED:
			s = "Arrived";
			break;
			}
			break;
		case 2:

			s = "[";
			if (vehiclesList.get(rowIndex).getItinerary().size() !=0 ) {
			
			for ( int i = 0; i< vehiclesList.get(rowIndex).getItinerary().size()-1; i++) {
				s += vehiclesList.get(rowIndex).getItinerary().get(i).getId()+ ",";
			
			}
			
			s+= vehiclesList.get(rowIndex).getItinerary().get(
					vehiclesList.get(rowIndex).getItinerary().size() -1).getId();
			}
			s+= "]";
			break;
		case 3:
			s = vehiclesList.get(rowIndex).getContClass();
			break;
		case 4:
			s = vehiclesList.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = vehiclesList.get(rowIndex).getSpeed();
			break;
		case 6:
			s = vehiclesList.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = vehiclesList.get(rowIndex).getLocation();
			break;
		}

		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		vehiclesList = map.getVehicles();
		fireTableDataChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		vehiclesList = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		vehiclesList = map.getVehicles();
		fireTableDataChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		vehiclesList = map.getVehicles();
		fireTableDataChanged();
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
