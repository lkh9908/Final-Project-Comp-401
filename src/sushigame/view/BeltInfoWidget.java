package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401sushi.IngredientPortion;
import comp401sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.Chef;

import java.awt.color.*;

public class BeltInfoWidget extends JPanel implements ActionListener{
	// This class create the buttons needed for additional information
	// for food on each spot
	
	private JButton typeBt;
	
	private JButton chefBt;
	
	private JButton ageBt;
	
	private Belt belt;
	
	private int index;
	
	public BeltInfoWidget(Belt belt, int index) {
		this.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel button_panel = new JPanel();
		
		this.belt = belt;
		this.index = index;
		
		typeBt = new JButton("Type");
		typeBt.setActionCommand("type");
		typeBt.addActionListener(this);
		button_panel.add(typeBt);
		
		chefBt = new JButton("Chef");
		chefBt.setActionCommand("chef");
		chefBt.addActionListener(this);
		button_panel.add(chefBt);
		
		ageBt = new JButton("Age");
		ageBt.setActionCommand("age");
		ageBt.addActionListener(this);
		button_panel.add(ageBt);
		
		add(button_panel);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		Plate ing = belt.getPlateAtPosition(index);
		
		if (ing != null) {
			if (cmd.equals("type")) {
				String t = ing.getContents().getClass().getName().substring(13);
				String info = "";
				if (t.toLowerCase().equals("sashimi") ||
						t.toLowerCase().equals("nigiri")) {
					// for sashimi and nigiri, specify name and type
					
					info = t + ", which contains: " +
						ing.getContents().getIngredients()[0].getName();
				} else if (t.toLowerCase().equals("roll")){
					// For rolls, specify name, ingredients, and amount of each ingredient
					
					String ingre = "";
					IngredientPortion[] rollIngre = ing.getContents().getIngredients().clone();
					for (int j = 0; j < ing.getContents().getIngredients().length; j++) {
						ingre += "\r\n" + rollIngre[j].getName() + ": " + 
					String.format("%.2f", rollIngre[j].getAmount())
								+ "oz.";
					}
					info = ing.getContents().getName() + ", which contains: \n" + ingre;
				}
				JOptionPane.showMessageDialog(typeBt, "The type of the plate is: \n" + info);
			} else if (cmd.equals("chef")) {
				JOptionPane.showMessageDialog(typeBt, "The chef of the plate is: \n" + 
			ing.getChef().getName());
			} else if (cmd.equals("age")) {
				JOptionPane.showMessageDialog(typeBt, "The age of the plate is: \n" + 
			belt.getAgeOfPlateAtPosition(index));
			}
		}
	}

}
