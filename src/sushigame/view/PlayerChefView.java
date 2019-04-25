package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri;
import comp401sushi.Nigiri.NigiriType;
import comp401sushi.Plate;
import comp401sushi.RedPlate;
import comp401sushi.RicePortion;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;
import comp401sushi.Sashimi.SashimiType;
import sushigame.model.AlreadyPlacedThisRotationException;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_nigiri;
	private int belt_size;

	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton sushi_button = new JButton("Make a new plate of sushi");
		sushi_button.setActionCommand("sushi");
		sushi_button.addActionListener(this);
		add(sushi_button);

		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("sushi")) {
			int spotInt = 0;
			String color = "";
			Sushi sushi = null;
			int fOt = 0;

			// ask for position
			String spot = JOptionPane.showInputDialog("Near what position do you "
					+ "want to place the plate(enter a number between "
					+ "0 and 19)?");
			spotInt = Integer.parseInt(spot);

			// ask for color of plate
			int res_plate = 0;
			String[] options_plate = {"red", "green", "blue", "gold"};
			res_plate = JOptionPane.showOptionDialog(null, "Which plate do you want?", 
					"Select an option", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
					null, options_plate, options_plate[0]); 
			switch (res_plate) {
			case 0:
				color = "red";
				break;
			case 1:
				color = "green";
				break;
			case 2:
				color = "blue";
				break;
			case 3:
				color = "gold";
				String[] fiveOrTen = {"5", "10"};
				fOt = JOptionPane.showOptionDialog(null, "For gold plate, choose between"
						+ " 5 and 10 dollars for price.", 
						"Select an option", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						null, fiveOrTen, fiveOrTen[0]); 
				break;
			}

			// ask for type of sushi
			int res_sushi = 0;
			String[] options = {"sashimi", "nigiri", "roll"};
			res_sushi = JOptionPane.showOptionDialog(null, "Which kind of sushi?", "Select an option", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
					null, options, options[0]); 
			// Sashimi
			if (res_sushi == 0) {
				int res_sashimi = 0;
				String[] options_sashimi = {"tuna", "yellowtail", "eel", "crab", "shrimp"};
				res_sashimi = JOptionPane.showOptionDialog(null, "Which kind of sashimi?", "Select an option", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						null, options_sashimi, options_sashimi[0]); 
				switch (res_sashimi) {
				case 0:
					sushi = new Sashimi (SashimiType.TUNA);
					break;
				case 1:
					sushi = new Sashimi (SashimiType.YELLOWTAIL);
					break;
				case 2:
					sushi = new Sashimi (SashimiType.EEL);
					break;
				case 3:
					sushi = new Sashimi (SashimiType.CRAB);
					break;
				case 4:
					sushi = new Sashimi (SashimiType.SHRIMP);
					break;
				}
			} else if (res_sushi == 1) {
			// nigiri
				int res_nigiri = 0;
				String[] options_nigiri = {"tuna", "yellowtail", "eel", "crab", "shrimp"};
				res_nigiri = JOptionPane.showOptionDialog(null, "Which kind of sashimi?", "Select an option", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						null, options_nigiri, options_nigiri[0]); 
				switch (res_nigiri) {
				case 0:
					sushi = new Nigiri (NigiriType.TUNA);
					break;
				case 1:
					sushi = new Nigiri (NigiriType.YELLOWTAIL);
					break;
				case 2:
					sushi = new Nigiri (NigiriType.EEL);
					break;
				case 3:
					sushi = new Nigiri (NigiriType.CRAB);
					break;
				case 4:
					sushi = new Nigiri (NigiriType.SHRIMP);
					break;
				}
			} else if (res_sushi == 2) {
			// roll
				String roll_name = JOptionPane.showInputDialog(
						"(Optional)What is the name of your roll?");
				
				// Name = "random roll" if no input
				if (roll_name.equals("")) {
					roll_name = "Random Roll";
				}
				
				int res_roll = 0;
				
				double[] portionsList = new double[8];
				int confirm = 0;
				boolean flag = false;
				while (confirm == 0) {
					confirm = JOptionPane.showConfirmDialog(null, "Add one ingredient?");
					// check if the player is creating a roll with no ingredient
					if ((confirm == 1 || confirm == 2) && flag == false) {
						JOptionPane.showMessageDialog(null, "Cannot create a roll with no ingredient");
						confirm = 0;
					}
					
					if (confirm == 1 || confirm == 2) {
						break;
					}
					String[] options_roll = {"avocado", "crab", "eel", "rice", "seaweed", "shrimp",
							"tuna", "yellowtail"};
					res_roll = JOptionPane.showOptionDialog(null, "What is the first ingredient"
							+ "for the roll?", "Select an option", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
							null, options_roll, options_roll[0]);
					IngredientPortion temp = null;
					
					String ingre_qty = JOptionPane.showInputDialog(
							"How much (oz.) would you like for this ingredient?");
					double qtyDouble = Double.parseDouble(ingre_qty);
					
					if (qtyDouble > 1.5) {
						JOptionPane.showMessageDialog(null, "Quantity cannot be more than 1.5oz.");
					} else if (portionsList[res_roll] + qtyDouble > 1.5){
						JOptionPane.showMessageDialog(null, "Quantity cannot be more than 1.5oz.");
					} else {
						portionsList[res_roll] += qtyDouble;
						flag = true;
					}
				}
				
				// initialize array of ingredients for roll object
				int leng = 0;
				for(int i = 0; i < portionsList.length; i++) {
					if (portionsList[i] != 0) {
						leng++;
					}
				}
				
				ArrayList <IngredientPortion> ingredientList = new ArrayList();
				
				IngredientPortion temp = null;
				if (portionsList[0] != 0) {
					temp = new AvocadoPortion(portionsList[0]);
					ingredientList.add(temp);
				}
				if (portionsList[1] != 0) {
					temp = new CrabPortion(portionsList[1]);
					ingredientList.add(temp);
				}
				if (portionsList[2] != 0) {
					temp = new EelPortion(portionsList[2]);
					ingredientList.add(temp);
				}
				if (portionsList[3] != 0) {
					temp = new RicePortion(portionsList[3]);
					ingredientList.add(temp);
				}
				if (portionsList[4] != 0) {
					temp = new SeaweedPortion(portionsList[4]);
					ingredientList.add(temp);
				}
				if (portionsList[5] != 0) {
					temp = new ShrimpPortion(portionsList[5]);
					ingredientList.add(temp);
				}
				if (portionsList[6] != 0) {
					temp = new TunaPortion(portionsList[6]);
					ingredientList.add(temp);
				}
				if (portionsList[7] != 0) {
					temp = new YellowtailPortion(portionsList[7]);
					ingredientList.add(temp);
				}
				
				
				IngredientPortion[] roll_result_list = new IngredientPortion[leng];
				int result_list_index = 0;
				for(IngredientPortion x: ingredientList) {
					roll_result_list[result_list_index] = x;
					result_list_index++;
				}
				
				Roll roll_result = new Roll(roll_name, roll_result_list);
				sushi = roll_result;
			}

			if (color.equals("red")){
				makeRedPlateRequest(sushi, spotInt);
			} else if (color.equals("green")) {
				makeGreenPlateRequest(sushi, spotInt);
			} else if (color.equals("blue")) {
				makeBluePlateRequest(sushi, spotInt);
			} else if (color.equals("gold")) {
				if (fOt == 0) {
					makeGoldPlateRequest(sushi, spotInt, 5);
				} else if (fOt == 1) {
					makeGoldPlateRequest(sushi, spotInt, 10);
				}
			}
		}


	}
}
