package simulator.model;


public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, 
			int maxSpeed, int contLimit, int length,
			Weather weather) {
		
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int x = 0;
		
		switch ( weather) { // reduce la cont dependiendo del tiempo
		
		case SUNNY: 
			x = 2;
			break;
		
		case CLOUDY: 
			x = 3;
			break;
			
		case RAINY:
			x = 10;
			break;
			
		case WINDY:
			x=15;
			break;
			
		case STORM:
			x = 20;
			break;
			
		}
		
		if ( (getTotalCO2() - x) < 0) {
			totalContamination = 0;
		}
		else {
		totalContamination = (int) (((100.0 - x)/100.0)* totalContamination);
		}
	}

	@Override
	void updateSpeedLimit() {
		
		if ( totalContamination >= contaminationAlarmLimit) { // si cont. total supera a la permitida se reducira la velidad 
		currentSpeedLimit = (int) (_maxSpeed * 0.5);
		}
		
		else {
			currentSpeedLimit = _maxSpeed;
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {

		int speed = 0;
		
		v.setSpeed(currentSpeedLimit);// actualiza la velocidad del vehiculo a la permita actual
			 
		if ( weather == Weather.STORM) {// si el tiempo es STORM se hara otra reduccion extra
				v.setSpeed(currentSpeedLimit * 8 / 10); 
			}

		speed = v.getSpeed();

		
		return speed;
	}

}
