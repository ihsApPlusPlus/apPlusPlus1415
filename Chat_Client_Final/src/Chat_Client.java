/* Yusuf Simitci
 * Computer Science Projects
 * Peer-to-Peer Chat
 * 
 */

import java.net.*;
import java.util.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.JTextArea;

//
// Chat_Client class that has client and server threads for communication
//
public class Chat_Client
{
   InetAddress OtherIP;
   int OtherPort;
   BufferedReader in;
   PrintWriter out;
   static int localPort;
   static JTextArea historyTextArea;
   Socket clientsocket;
   
   public Chat_Client(JTextArea textArea)
   {
	   historyTextArea = textArea;
   }
   
   public int getLocalPort()
   {
	   return localPort;
   }
   
   //
   // Converts the input String to an IP Address
   //
   public void setOtherIP(String inputIP)
   {
	   try
	   {
		   	OtherIP = InetAddress.getByName(inputIP);
	   }
	   catch (UnknownHostException e) 
       {
           throw new RuntimeException(e);
       }
   }
   
   public void setOtherPort(int inputPort)
   {
	   OtherPort = inputPort;
   }
   
   //
   // Starts the server thread
   //   
   public void start()   
   {
       ServerThread server = new ServerThread();

       server.start();
   }
   
   //
   // Creates a client socket connection
   //
   public void connect()
   {
	   try
       {       
           clientsocket = new Socket(OtherIP, OtherPort);
           in = new BufferedReader(new InputStreamReader(
               clientsocket.getInputStream()));
           out = new PrintWriter(clientsocket.getOutputStream(), true);
           out.println("Hello");
       }
       catch (IOException e) 
       {
                      
       }
   }
   
   //
   // Send a String to the other server 
   //
   public void send(String message)
   {
	   out.println(message);
	   Chat_Client.historyTextArea.append("You: " + message + "\n");
   }
   
   //
   // go through local network IPs and, get the first IP Address
   //
   public String getIP()
   {
	   String ip = "";
	   try {
           Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
           while (interfaces.hasMoreElements()) {
               NetworkInterface iface = interfaces.nextElement();
               // filters out 127.0.0.1 and inactive interfaces
               if (iface.isLoopback() || !iface.isUp())
                   continue;
   
               Enumeration<InetAddress> addresses = iface.getInetAddresses();
               while(addresses.hasMoreElements()) {
                   InetAddress addr = addresses.nextElement();
                   ip = addr.getHostAddress();
                   return ip;
               }
           }
       } 
	   catch (SocketException e) 
	   {
           throw new RuntimeException(e);
       }
	   return ip;
   }
   
   //
   // A server thread that accepts messages from clients
   //
   private static class ServerThread extends Thread
   {
	   ServerSocket listener;
	   
	   //
	   // opens the server socket and start a thread to handle it
	   //
	   public void run() 
	   {
		   try 
		   {
		        listener = new ServerSocket(0);	  
		        localPort = listener.getLocalPort();
		        		
	            while (true) 
	            {
	            	new Handler(listener.accept()).start();
		        }
		   }
           catch (Exception e)
 		   {
 		      throw new RuntimeException(e);
 		   }    
           finally 
           {
            	try 
            	{
					listener.close();
				} 
            	catch (IOException e) 
            	{
				  
				   throw new RuntimeException(e);
				}
	       }
	    }
	   
	   //
	   // Server socket handling thread
	   //
	   class Handler extends Thread {
	        private Socket socket;
	        private BufferedReader in;
	        private PrintWriter out;

	        //
	        // construct a new handler 
	        //
	        public Handler(Socket socket) {
	            this.socket = socket;
	        }

	        
	        public void run() 
	        {
	            try 
	            {
                    // Create character streams for the socket.
	                in = new BufferedReader(new InputStreamReader(
	                    socket.getInputStream()));
	                out = new PrintWriter(socket.getOutputStream(), true);

	                while (true) {
	                    String input = in.readLine();
	                    if (input == null) {
	                        return;
	                    }
	                    
	                    Chat_Client.historyTextArea.append("Other: " + input + "\n");
	                 }
	            } catch (IOException e) {
	                System.out.println(e);
	            } finally {
	                
	                if (out != null) {
	                   
	                }
	                try {
	                    socket.close();
	                } catch (IOException e) {
	                
	            }
	        }
	     }
	   }
   }
}
