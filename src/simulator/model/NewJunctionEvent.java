package simulator.model;

public class NewJunctionEvent extends Event{

	private String _id;
	private LightSwitchingStrategy strategySemf;
	private DequeuingStrategy strategyElem;
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy
			lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		
			super(time);
			
			this._id = id;
			this.strategySemf = lsStrategy;
			this.strategyElem = dqStrategy;
			this.x = xCoor;
			this.y = yCoor;
	}

	@Override
	void execute(RoadMap map) { // crea el junction y lo añade al mapa
		
		
		Junction j = new Junction(_id, strategySemf, strategyElem, x, y);
		
		map.addJunction(j);
		
	}

	@Override
	public String toString() {
		return "New Junction '"+_id+"'";
	}
}
