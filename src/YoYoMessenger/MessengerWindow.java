package YoYoMessenger;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MessengerWindow extends JFrame {
	// Menu Bar
	JMenuBar menuBar;
	JMenu Profile;
	JMenu help;
	
	// Menu Item
	JMenuItem setUserId;
	JMenuItem exit;
	JMenuItem howTo;
	JMenuItem about;
	JSeparator Separator1,Separator2;
	
	// Arena 
	JPanel messagePanel;
	JPanel chatPanel;
	JLabel messageBoxLabel;
	JLabel chatBoxLabel;
	JTextArea messageBox;
	JTextArea chatBox;
	JButton sendMessage;
	JMenu connectedwith;
	JMenuItem Ip;
	public MessengerWindow() 
	{
		
		setTitle("YoYo Messenger");
		//setLayout(null);
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon("/Users/nurislam/HackWay/Photos/Amazing/YoYo.jpg")));
		//Menu design starts
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 648, 21);
		setJMenuBar(menuBar);
		
		//
		Profile = new JMenu("Profile");
		menuBar.add(Profile);
		
		setUserId = new JMenuItem("Set User ID...");
		Profile.add(setUserId);
		
		connectedwith = new JMenu("Connected With");
		menuBar.add(connectedwith);
		
		Ip = new JMenuItem("Client IP");
		connectedwith.add(Ip);
	
		//
		help = new JMenu("Help!");
		menuBar.add(help);
		
		howTo = new JMenuItem("How to...");
		help.add(howTo);
		
		Separator1 = new JSeparator();
		help.add(Separator1);
		
		about = new JMenuItem("About");
		help.add(about);
		
		//
		exit = new JMenuItem("Exit");
		menuBar.add(exit);				
		//Menu design ends
		
		
		//Messaging area design starts
		messageBoxLabel = new JLabel("Message Window");
		messageBoxLabel.setBounds(8, 23, 108, 21);
		
		add(messageBoxLabel);
		
		messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.setBounds(8, 43, 625, 218);
		messageBox = new JTextArea("No One is Connected...");
		messageBox.setFont(new Font("MonoSpace", 1, 14));
		messageBox.setLineWrap(true);
		messageBox.setWrapStyleWord(true);
		messageBox.setEditable(false);
		//
		messageBox.setBackground(Color.YELLOW);
		//
		messagePanel.add(messageBox, BorderLayout.CENTER);
		messagePanel.add(new JScrollPane(messageBox), BorderLayout.CENTER);
		add(messagePanel);
		
		chatBoxLabel = new JLabel("Chat Box");
		chatBoxLabel.setBounds(8, 281, 64, 14);
		add(chatBoxLabel);
		
		chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		chatPanel.setBounds(8, 297, 625, 74);
		
		
		chatBox = new JTextArea();
		chatBox.setFont(new Font("MonoSpace", 1, 14));
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		chatBox.setEditable(true);
		//
		chatBox.setBackground(Color.pink);
		//
		chatBox.setCaretColor(Color.RED);
		chatPanel.add(chatBox, BorderLayout.CENTER);
		chatPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
		add(chatPanel);
		
		sendMessage = new JButton("Send Message");
		sendMessage.setBounds(506, 380, 129, 23);
		add(sendMessage);
		//Messaging area design ends
		
		exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);			
			}
		});
		
		howTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				JOptionPane.showMessageDialog(null, "In Order to Connect with Server you have to know Server IP Address", "How To", 1);
			}
		});
		
		about.addActionListener( new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				JOptionPane.showMessageDialog(null, "Created By \nNur Islam \nIfrat Mitul", "about",1);
			}
		});
		
		Ip.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent event) {
			  JOptionPane.showMessageDialog(null, Server.IP, "IP History", 1);
				
			  
			}
		});
	}
}
