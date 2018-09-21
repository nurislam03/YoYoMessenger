package YoYoMessenger;

import java.io.*;
import java.net.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;


public class Client extends MessengerWindow implements Runnable
{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	private String userID;
	private String Disconnected = "";
	
	public Client(String host) 
	{
		userID = "Client - ";
		serverIP = host;
		chatBox.setEditable(true);
		sendMessage.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent event)
					{
						sendMessageMethod(chatBox.getText());
			            chatBox.setText("");
			        }
				});
		setUserId.addActionListener(				
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent event) 
					{
						userID = (JOptionPane.showInputDialog(null, "Input Your User ID:", "User ID Change", 3)).concat(" - ");
					}
				}
		);
		new Thread(this).start();
	}
	
	public void initGUI() 
	{
		super.setTitle("YoYo Messenger - Client");
		setSize(849, 565);
		setResizable(true);
		//setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		chatBox.requestFocusInWindow();
		setVisible(true);
	}
	
	@Override
	public void run() 
	{
		startRunning();
	}
	
	public void startRunning()
	{
	      try{
	         connectToServer();
	         setupStreams();
	         whileChatting();
	      }catch(EOFException eo){
	         showMessage("\nClient terminated connection");
	      }catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         closeServer();
	      }
	}
	
	private void connectToServer() throws Exception
	{
	      showMessage("\nAttempting connection... \n");
	      connection = new Socket(InetAddress.getByName(serverIP), 8000);
	      showMessage("\nConnected to: " + connection.getInetAddress().getHostName() );
	}
	
	private void setupStreams() throws Exception
	{
	      output = new ObjectOutputStream(connection.getOutputStream());
	      output.flush();
	      input = new ObjectInputStream(connection.getInputStream());
	      showMessage("\nYour streams are now ready to go! \n");
	}
	
	private void whileChatting() throws Exception
	{
	      ableToType(true);
	      do{
	         try{
	            message = (String) input.readObject();
	            showMessage("\n" + message);
	            
	            int L = message.length();
	            Disconnected = "";
	            if( L >= 3 )
	            {     	
		            char s1 = message.charAt(L-3);
		            char s2 = message.charAt(L-2);
		            char s3 = message.charAt(L-1);
		            Disconnected = Disconnected+s1+s2+s3; //System.out.println("ok"+Disconnected);
	            }
	         }catch(Exception e){
	            showMessage("\nMessenger doesn't recognize that object type");
	         }
	      }while(!Disconnected.equalsIgnoreCase("BYE"));
	}
	
	private void closeServer()
	{
	      showMessage("\nClosing connection...");
	      ableToType(false);
	      try{
	         output.close();
	         input.close();
	         connection.close();
	      }catch(Exception e){
	         e.printStackTrace();
	      }
	}
	
	private void sendMessageMethod(String message)
	{
	      try{
	         output.writeObject(userID + message);
	         output.flush();
	         showMessage("\n" + userID + message);
	      }catch(Exception e){
	         messageBox.append("\nError!");
	      }
	}
	
	private void showMessage(final String m)
	{
	      SwingUtilities.invokeLater(
	         new Runnable()
	         {
	            public void run()
	            {
	               messageBox.append(m);
	            }
	         }
	      );
	}
	
	private void ableToType(final boolean tof)
	{
	      SwingUtilities.invokeLater(
	         new Runnable()
	         {
	            public void run()
	            {
	               chatBox.setEditable(tof);
	            }
	         }
	      );     
	}
}
