/* Yusuf Simitci
 * Computer Science Projects
 * Peer-to-Peer Chat
 * 
 * auto generated using WindowBuilder
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;


public class Chat_Window extends JFrame {

	private JPanel contentPane;
	private JTextField YourIPTextField;
	private JTextField YourPortTextField;
	private JTextField OtherIPTextField;
	private JTextField OtherPortTextField;
	private JTextField EnterTextField;
	private static Chat_Client chatClient;
	private static JTextArea HistoryTextArea;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					//
					// Create a chat client to handle communication 
					//
					Chat_Window frame = new Chat_Window();
					frame.setVisible(true);
					chatClient = new Chat_Client(HistoryTextArea);
					chatClient.start();
					frame.setIPAddress();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setIPAddress()
	{
		YourIPTextField.setText(chatClient.getIP());
		YourPortTextField.setText("" + chatClient.getLocalPort());
	}
	
	/**
	 * Create the frame.
	 */
	public Chat_Window() {
		setTitle("Peer-to-Peer Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		YourIPTextField = new JTextField();
		YourIPTextField.setEditable(false);
		YourIPTextField.setBounds(93, 11, 86, 20);
		contentPane.add(YourIPTextField);
		YourIPTextField.setColumns(10);
		
		JLabel lblYourIp = new JLabel("Your IP");
		lblYourIp.setBounds(10, 14, 46, 14);
		contentPane.add(lblYourIp);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(186, 14, 46, 14);
		contentPane.add(lblPort);
		
		YourPortTextField = new JTextField();
		YourPortTextField.setEditable(false);
		YourPortTextField.setBounds(218, 11, 86, 20);
		contentPane.add(YourPortTextField);
		YourPortTextField.setColumns(10);
		
		JLabel lblConnectToIp = new JLabel("Connect to IP");
		lblConnectToIp.setBounds(10, 39, 79, 20);
		contentPane.add(lblConnectToIp);
		
		OtherIPTextField = new JTextField();
		OtherIPTextField.setBounds(93, 39, 86, 20);
		contentPane.add(OtherIPTextField);
		OtherIPTextField.setColumns(10);
		
		JLabel lblPort_1 = new JLabel("Port");
		lblPort_1.setBounds(186, 42, 46, 14);
		contentPane.add(lblPort_1);
		
		OtherPortTextField = new JTextField();
		OtherPortTextField.setBounds(218, 39, 86, 20);
		contentPane.add(OtherPortTextField);
		OtherPortTextField.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBackground(new Color(255, 222, 173));
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//
				// connect to the given IP and Port
				//
				chatClient.setOtherIP(OtherIPTextField.getText());
				chatClient.setOtherPort(Integer.parseInt(OtherPortTextField.getText()));
				chatClient.connect();
			}
		});
		btnConnect.setBounds(324, 38, 89, 23);
		contentPane.add(btnConnect);
		
		EnterTextField = new JTextField();
		EnterTextField.setBounds(27, 230, 324, 20);
		contentPane.add(EnterTextField);
		EnterTextField.setColumns(10);
		
		JButton SendButten = new JButton("Send");
		SendButten.setBackground(new Color(255, 222, 173));
		SendButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				//
				// Take a String from the user, and sending it to the other Client
				//
				chatClient.send(EnterTextField.getText());
				EnterTextField.setText("");
			}
		});
		SendButten.setBounds(361, 229, 63, 21);
		contentPane.add(SendButten);
		
		HistoryTextArea = new JTextArea();
		HistoryTextArea.setBackground(new Color(255, 250, 205));
		HistoryTextArea.setEditable(false);
		//HistoryTextArea.setBounds(27, 85, 371, 124);
		//scrollPane.add(HistoryTextArea);
		
		JScrollPane scrollPane = new JScrollPane(HistoryTextArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 78, 397, 141);
		contentPane.add(scrollPane);
		
		
		
		
	}
}
