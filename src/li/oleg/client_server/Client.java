package li.oleg.client_server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		
		Client clientApp = new Client();
		String hostName = "OlegLi";
		int port = 6969;
		
		Socket client = clientApp.createSocket(hostName, port);		
		if(client != null) 	clientApp.readWriteOps(client);	 
	}
	
	//Creating a client's socket which is connected to specified Server on specified port.
	private Socket createSocket(String hostName,int port) {
		
		System.out.println("[Client] : Establishing a connection to " + hostName + " on port : " + port + ".");
		
		try {
			Socket client = new Socket(hostName,port);
			System.out.println("[Client] : Connection succesfully established with " + client.getRemoteSocketAddress());
			return client;
		 }
		catch(IOException e) {
			System.out.println("[Client] : Couldn't establish connection with specified address!!!");
			e.printStackTrace();
		}	
		return null;
	}

	//Performing communication with server's socket.
	private void readWriteOps(Socket client) {
			
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))){
		
			String serverMsg;
			
			mainLoop:
			while(true) {
				
				serverMsg = reader.readLine();
				System.out.println(serverMsg);	
				
				if(serverMsg.equals("Bye")) {
					System.out.println("Disconnecting from server!!!");
					break mainLoop;
				}
								
				writer.write(getUserMessage());
				writer.newLine();  //Buffered writer must write newLine() to denote of end of the line if on another end BufferedReader reading the stream with readLine().
				writer.flush();						
			}			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
	//Getting message from user.(From Keyboard).
	private String getUserMessage() {
	
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
}
