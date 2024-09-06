package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 */
	private Controller ctrl;
	private List<Junction> junctionsList;
	private String [] colName = {"Id", "Green", "Queues"};
	private static final long serialVersionUID = 1L;

	public JunctionsTableModel( Controller control) {
		ctrl = control;
		ctrl.addObserver(this);
		junctionsList = new ArrayList<Junction>();
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
		return junctionsList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object s = null; 
		
		switch (columnIndex) {
		case 0:
			s = junctionsList.get(rowIndex).getId();
			break;
		case 1:
			
			int r = junctionsList.get(rowIndex).getGreenLightIndex();
			
			if ( r == -1) {
				s = "NONE";
			}
			
			else {
				s = junctionsList.get(rowIndex).getInRoads().get(r).getId();
			}
			
			break;
		case 2:

			s = "";
			if (!junctionsList.get(rowIndex).getInRoads().isEmpty()) {
				
				for ( int i = 0 ; i < junctionsList.get(rowIndex).getInRoads().size(); i++ ) {
					
					Road ro = junctionsList.get(rowIndex).getInRoads().get(i);
					
					s = ro.getId() + ":[";
					
					for ( Vehicle vehicle : junctionsList.get(rowIndex).getCola()
							.get(junctionsList.get(rowIndex).getInRoads().get(i))) {
						s += vehicle.getId();
					}
					
					s += "]";
				}
			}
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		junctionsList = map.getJunctions();
		fireTableDataChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		junctionsList = map.getJunctions();
		fireTableDataChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		junctionsList = map.getJunctions();
		fireTableDataChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		junctionsList = map.getJunctions();
		fireTableDataChanged();
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
