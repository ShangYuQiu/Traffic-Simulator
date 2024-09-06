package simulator.control;

import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;

public class Controller {
	
	private TrafficSimulator tfSim;
	private Factory <Event> eventFact;

	
	public Controller (TrafficSimulator sim, Factory <Event>eventsFactory) {
		
		if ( sim == null || eventsFactory == null) { // comprueba las entradas
			throw new IllegalArgumentException(" Entradas invalidas, no pueden ser nulos");
		}
		
		this.tfSim = sim;
		this.eventFact = eventsFactory;
	}
	
	public void reset () { 
		tfSim.reset();
	}
	
	public void loadEvents(InputStream input) {
		
		JSONObject jo = new JSONObject(new JSONTokener(input));
		JSONArray e=jo.getJSONArray("events");
		
	     for(int i=0;i<e.length();i++) {// recoge las informaciones de input y crea los eventos
	    	 Event ev = eventFact.createInstance(e.getJSONObject(i));
	    	 tfSim.addEvent(ev); // añade el evento creado
	     }
	     
	}
	
	public void run (int n,OutputStream out ) {
		
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println(" \"states\" :[");
		
		for ( int i =0; i < n; i++) { // avanza la simulacion y muestra su estado
			tfSim.advance();
			p.print(tfSim.report());
			
			if ( i != n - 1) {
				p.println(",");
			}
		}
		
		p.println("]");
		p.println("}");
	}
	
	public void run(int n) {
		for(int i=n;i>0;i--) {
			tfSim.advance();
		}
	}
	
	public void addObserver(TrafficSimObserver o) {
		tfSim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		tfSim.removeObserver(o);
	}
	
	public void addEvent (Event e) {
		tfSim.addEvent(e);
	}
	
	
}
