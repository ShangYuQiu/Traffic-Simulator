package simulator.model;


public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, 
			int maxSpeed, int contLimit, int length, Weather weather) {
		
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		
		int x = 0;
		
		switch ( weather) { // dependiendo del tiempo se reducira mas o menos cont
			
		case WINDY:
			x=10;
			break;
			
		case STORM:
			x = 10;
			break;
			
		default:
			x = 2;
			break;
		}
		
		if ( (getTotalCO2() - x) < 0) {
			totalContamination = 0;
		}
		else {
			totalContamination -= x;
		}
	}

	@Override
	void updateSpeedLimit() {// actualiza la velocidad maxima actual a maxSpeed permitido
		currentSpeedLimit = _maxSpeed;
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		
		int f = v.getContClass(); // grado de cont del vehiculo
		int s = this.currentSpeedLimit; //limite velocidad actual
		int r = (int) (((11.0 - f) / 11.0) * s); 
		
		return r;
	}

}
