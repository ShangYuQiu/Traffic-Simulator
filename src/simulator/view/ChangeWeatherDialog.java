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

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{

	private static final long serialVersionUID = 1L;

	private int state;
	private JComboBox<Road> roadBox;
	private DefaultComboBoxModel<Road> roadBoxModel;
	private DefaultComboBoxModel<Weather> weatherBoxModel;
	private JComboBox<Weather> weatherBox;

	//private DefaultComboBoxModel<Object> ticksModel;
	private JSpinner ticks;
	
	public ChangeWeatherDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	private void initGUI(){
		
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		setContentPane(panelMain);
		
		// ComboBoxs
		JLabel infoMsg = new JLabel("Elija un road y un weather");
		infoMsg.setAlignmentX(CENTER_ALIGNMENT);
		panelMain.add(infoMsg);
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		panelMain.add(viewsPanel);
		
		viewsPanel.add(new JLabel("Roads:"));
		roadBoxModel = new DefaultComboBoxModel<Road>();
		roadBox = new JComboBox<Road>(roadBoxModel);
		viewsPanel.add(roadBox);
		
		viewsPanel.add(new JLabel("Weather:"));
		weatherBoxModel = new DefaultComboBoxModel<Weather>();
		weatherBox = new JComboBox<Weather>(weatherBoxModel);
		viewsPanel.add(weatherBox);

		for (Weather w : Weather.values()) {
			weatherBoxModel.addElement(w);
		}
		
		 // Spinner de ticks
		viewsPanel.add(new JLabel("ticks:"));
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		viewsPanel.add(ticks);
		
		JPanel ButtonPanel = new JPanel();
		panelMain.add(ButtonPanel);
		
		// Cancel boton
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				state = 0;
			}		
		});
		
		ButtonPanel.add(cancelButton);
		
		// Ok boton
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roadBox.getSelectedItem() != null) {
					state = 1;
					setVisible(false);
					
				}
			}			
		});

		ButtonPanel.add(okButton);
		setPreferredSize(new Dimension(400, 200));
		pack();
		//this.add(panelMain);
	}	
	public int open(RoadMap roadMap) {
		
		for(Road r : roadMap.getRoads()) {
			roadBoxModel.addElement(r);	
		}
		setVisible(true);		
		return this.state;
	}
	
	public int getTicks() {
		return (Integer)ticks.getValue();
	}
	
	public Road getRoad() {
		return (Road)roadBoxModel.getSelectedItem();
	}
	
	public Weather getWeather() {
		return (Weather)weatherBoxModel.getSelectedItem();
	}
	
}
