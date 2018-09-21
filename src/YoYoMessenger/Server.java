package YoYoMessenger;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;


public class Server extends MessengerWindow implements Runnable
{
	public static String IP ="";
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private String userID;
	private String Disconnected = "";
	
	public Server() 
	{
		userID = "Server - ";
		chatBox.setEditable(false);
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
					public void actionPerformed(ActionEvent event) {
						userID = (JOptionPane.showInputDialog(null, "Input Your User ID:", "User ID Change", 3)).concat(" - ");
					}
				}
		);
		
		
		new Thread(this).start();
	}
	
	
	public void initGUI() 
	{
		super.setTitle("YoYo Messenger - Server");
		setSize(849, 565); // Changed the Size in order to Fit the Picture Perfectly 
		setResizable(true);
		setBackground(Color.LIGHT_GRAY);
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
		try {
			server = new ServerSocket(8000);
			while(true) {
				try {
					waitForConnection();
					setupStreams();
					getFile();
					whileChatting();
				}catch(Exception eo) {
					showMessage(userID + "\n ended the connection!");
				}finally {
					closeServer();
				}
			}
		}catch(Exception e) 	
		{
			e.printStackTrace();
		}
	}

	private void waitForConnection() throws Exception
	{
	      showMessage("\nWaiting connection... \n");
	      connection = server.accept();
	      showMessage("\nNow connected to " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws Exception
	{
	      output = new ObjectOutputStream(connection.getOutputStream());
	      output.flush();
	      input = new ObjectInputStream(connection.getInputStream());
	      showMessage("\n Streams are now setup! \n");
	}
	
	private void whileChatting() throws Exception
	{
	      String message = " You are now connected! ";
	      sendMessageMethod(message);
	      ableToType(true);
	      do{
	         try{
	            message = (String) input.readObject();
	            Disconnected = "";
	            showMessage("\n" + message);            
	           
	            int L = message.length();
	            if( L >= 3 )
	            {     	
		            char s1 = message.charAt(L-3);
		            char s2 = message.charAt(L-2);
		            char s3 = message.charAt(L-1); //System.out.println("0k1 "+message);
		            Disconnected = Disconnected+s1+s2+s3; //System.out.println("0k2"+Disconnected);
	            }	            
	         }catch(ClassNotFoundException e){
	            showMessage("\n Invalid Message!");
	         }
	      }while(!Disconnected.equalsIgnoreCase("BYE"));
	}
	
	private void closeServer()
	{
	      showMessage("\n Closing connection... \n");
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
	         messageBox.append("\n ERROR: Message sending failed");
	      }
	}
	
	private void showMessage(final String text)
	{
	      SwingUtilities.invokeLater(
	        new Runnable(){
	            public void run(){
	               messageBox.append(text);
	            }
	         }
	      );
	}
	
	private void ableToType(final boolean tof)
	{
	      SwingUtilities.invokeLater(
	         new Runnable(){
	            public void run(){
	               chatBox.setEditable(tof);
	            }
	         }
	      );
	}
	public  void getFile() throws IOException{
		//String IP = " ";
		File F1 = new File("IP.txt");
	     String s=  connection.getInetAddress().getHostName();
		PrintWriter p1 = new PrintWriter(new BufferedWriter(new FileWriter(F1, true)));
		p1.println(s);
		//p1.print();
		p1.close();
		
		Scanner in = new Scanner(F1);
		while(in.hasNext())
			IP = in.nextLine();
		in.close();
		
	
	}
}