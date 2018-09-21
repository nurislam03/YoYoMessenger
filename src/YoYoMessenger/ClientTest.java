package YoYoMessenger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ClientTest {
	public static void main(String[] args) {
		final String host = JOptionPane.showInputDialog("Enter the IP adress, you want to connect to:");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Client happy = new Client(host);
				happy.initGUI();
			}
		});
	}
}
