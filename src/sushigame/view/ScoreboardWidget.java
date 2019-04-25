package sushigame.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	private int which_sort_option;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);

		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML());

		// allow player to choose how they want to sort the chefs
		JButton chef_sort_button = new JButton("Sort the list of chefs");
		chef_sort_button.setActionCommand("chefs");
		chef_sort_button.addActionListener(this);
		add(chef_sort_button, BorderLayout.NORTH);

		// default: sort by balance
		which_sort_option = 0;
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}

		switch(which_sort_option) {
		case 0:
			// sort chefs by balance (highest to lowest).
			Arrays.sort(chefs, new HighToLowBalanceComparator());

			for (Chef c : chefs) {
				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
			}

			return sb_html;
		case 1:
			// sort chefs by food consumed (highest to lowest).
			Arrays.sort(chefs, new HighToLowFoodSoldComparator());

			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getWeightConsumed()*100.0)/100.0 + "oz." + ") <br>";
			}

			return sb_html;
		case 2:
			// sort chefs by food spoiled (lowest to highest).
			Arrays.sort(chefs, new LowToHighFoodSpoiledComparator());

			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getWeightSpoiled()*100.0)/100.0 + "oz." + ") <br>";
			}

			return sb_html;
		}
		// default: sort by balance
		Arrays.sort(chefs, new HighToLowBalanceComparator());

		for (Chef c : chefs) {
			sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
		}

		return sb_html;
	}

	public void refresh() {
		display.setText(makeScoreboardHTML());		
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("chefs")) {
			String[] options_chef = {"sorted by balance", "sorted by food sold",
			"sorted by food spoiled"};
			int choice_chef = JOptionPane.showOptionDialog(null, "List of chefs", 
					"Select an sorting option", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
					null, options_chef, options_chef[0]); 
			if (choice_chef == 0) {
				which_sort_option =  0;
			} else if (choice_chef == 1) {
				which_sort_option =  1;
			} else if (choice_chef == 2) {
				which_sort_option = 2;
			}
			refresh();
		}
	}
}
