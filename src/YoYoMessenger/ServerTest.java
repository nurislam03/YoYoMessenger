package YoYoMessenger;

import javax.swing.SwingUtilities;

public class ServerTest 
{
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				Server sad = new Server();
				sad.initGUI();
			}
		});	
	}
}

