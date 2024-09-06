package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;


	private int state;
	private JComboBox<Vehicle> vehicleBox;
	private DefaultComboBoxModel<Vehicle> vehicleBoxModel;
	private DefaultComboBoxModel<Integer> contBoxModel;
	private JComboBox<Integer> contBox;
	private JSpinner ticks;
	
	public ChangeCO2ClassDialog(Frame frame){	
		super(frame, true);
		initGUI();
	}
	
	private void initGUI(){
		JPanel panelMain = new JPanel();
		
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		setContentPane(panelMain);
		
		// ComboBoxs
		JLabel infoMsg = new JLabel("Elija un vehiculo y una clase de contaminacion");
		infoMsg.setAlignmentX(CENTER_ALIGNMENT);
		panelMain.add(infoMsg);
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		panelMain.add(viewsPanel);
		
		viewsPanel.add(new JLabel("Vehicles:"));
		vehicleBoxModel = new DefaultComboBoxModel<Vehicle>();
		vehicleBox = new JComboBox<Vehicle>(vehicleBoxModel);
		viewsPanel.add(vehicleBox);
		
		viewsPanel.add(new JLabel("CO2 Class:"));
		contBoxModel = new DefaultComboBoxModel<Integer>();
		contBox = new JComboBox<Integer>(contBoxModel);
		
		for ( int i = 0; i < 11; i++) {
			contBoxModel.addElement(i);
		}
		
		viewsPanel.add(contBox);
		
		// Spinner de ticks
		viewsPanel.add(new JLabel("Ticks:"));
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		viewsPanel.add(ticks);
		
		JPanel ButtonPanel = new JPanel();
		panelMain.add(ButtonPanel);
		
		// Boton Cancel
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				state = 0;
			}		
		});
		
		ButtonPanel.add(cancelButton);
		
		//Boton Ok
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vehicleBox.getSelectedItem() != null) {
					state = 1;
					setVisible(false);
					
				}
			}			
		});

		ButtonPanel.add(okButton);
		
		setPreferredSize(new Dimension(400, 200));
		pack();
	}	
	public int open(RoadMap roadMap) {
		
		for(Vehicle v : roadMap.getVehicles()) {
			vehicleBoxModel.addElement(v);	
		}
		setVisible(true);		
		return this.state;
	}
	
	public int getTicks() {
		return (Integer)ticks.getValue();
	}
	
	public Vehicle getVehicle() {
		return (Vehicle)vehicleBoxModel.getSelectedItem();
	}
	
	public int getCO2Class() {
		return (Integer)contBoxModel.getSelectedItem();
	}
}
