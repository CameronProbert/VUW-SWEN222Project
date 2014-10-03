package catgame.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class HelperMethods {



	// ================= Helper Methods ====================

	/**
	 * Creates and returns a button
	 * 
	 * @param text
	 *            The text to be on the button
	 * @param size
	 *            The size of the button
	 * @return
	 */
	public static JButton createButton(String text, Dimension size) {
		JButton button = new JButton();
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setSize(size);
		button.setPreferredSize(size);
		button.setText(text);
		return button;
	}

	/**
	 * Creates a dialog asking if the user would like to quit the program
	 */
	public static void confirmQuit() {
		if (confirmationDialog("Confirm quit", "Are you sure you want to quit?")) {
			System.exit(0);
		}
	}

	/**
	 * Creates a dialog asking the user for confirmation
	 * 
	 * @param title
	 *            The title of the dialog box
	 * @param message
	 *            The message of the dialog box
	 * @return A boolean of the user's choice
	 */
	public static boolean confirmationDialog(String title, String message) {
		return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
				message, title, JOptionPane.OK_CANCEL_OPTION);

	}

	/**
	 * Creates a dialog that asks for user input
	 * 
	 * @param title
	 *            The title of the dialog box
	 * @param message
	 *            The message of the dialog box
	 * @return The users input as a string
	 */
	public static String stringInputDialog(String title, String message) {
		String string = "";
		do {
			string = (String) JOptionPane.showInputDialog(null, message, title,
					JOptionPane.PLAIN_MESSAGE, null, null, "");
		} while (string == null || string.equals(""));

		return string;
	}
	
	/**
	 * Creates a slider that returns a number
	 * @return
	 */
	public static int getNumSliderDialog(String title, int minNum, int maxNum, int defaultNum){
		// Get total number of players
		JPanel playerPanel = new JPanel();
		playerPanel.setPreferredSize(new Dimension(200, 200));
		JSlider slider = new JSlider(JSlider.HORIZONTAL, minNum, maxNum, defaultNum);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		// Dialog asking how many players there are
		if (JOptionPane.CLOSED_OPTION == JOptionPane.showConfirmDialog(null,
				slider, title,
				JOptionPane.PLAIN_MESSAGE)) {
			System.exit(0);
		}
		return slider.getValue();
	}

	public static void textDialog(String title, String message) {
		JOptionPane.showConfirmDialog(null,
				message, title,
				JOptionPane.PLAIN_MESSAGE);
	}

}
