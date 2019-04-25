package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import comp401sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private JLabel[] belt_labels;
	private BeltInfoWidget[] belt_widgets;

	private JPanel list_panel;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);

		list_panel = new JPanel();
		list_panel.setLayout(new GridLayout(0, 1));


		// one label about the name and color of the plate is
		// set up in this class
		belt_labels = new JLabel[belt.getSize()];
		belt_widgets = new BeltInfoWidget[belt.getSize()];

		for (int i = 0; i < belt.getSize(); i++) {
			JPanel item_panel = new JPanel();
			JLabel plabel = new JLabel("No plate is placed on this spot yet");

			plabel.setMinimumSize(new Dimension(200, 20));
			plabel.setPreferredSize(new Dimension(200, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.GRAY);
			item_panel.add(plabel, BorderLayout.WEST);
			belt_labels[i] = plabel;

			belt_widgets[i] = new BeltInfoWidget(belt, i);
			item_panel.add(belt_widgets[i], BorderLayout.EAST);
			
			list_panel.add(item_panel);
		}

		JScrollPane scrollPane = new JScrollPane(list_panel);
		JScrollBar bar = scrollPane.getVerticalScrollBar();
		bar.setPreferredSize(new Dimension(40, 0));
		scrollPane.setPreferredSize(new Dimension(800, 600));
		add(scrollPane, BorderLayout.CENTER);


		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JLabel plabel = belt_labels[i];
			BeltInfoWidget ing = belt_widgets[i];

			if (p == null) {
				plabel.setText("No plate is placed on this spot yet");
				plabel.setBackground(Color.GRAY);
			} else {
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED); break;
				case GREEN:
					plabel.setBackground(Color.GREEN); break;
				case BLUE:
					plabel.setBackground(Color.BLUE); break;
				case GOLD:
					plabel.setBackground(Color.YELLOW); break;
				}

				plabel.setText(p.getContents().getName());
			}
		} 
	}
}
