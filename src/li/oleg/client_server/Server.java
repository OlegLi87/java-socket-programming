package li.oleg.client_server;

import java.util.Random;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Server {
	
	private ServerSocket serverSocket;

	public static void main(String[] args) {		
		
		int port = 6969,timeout = 10000;	
	    new Server(port,timeout);
	}
	
	//Constructor which will invoke creation method of  ServerSocket object.
	Server(int port,int timeout){
			
		if(createServerSocket(port,timeout) > 0) createConnection();
		else System.out.println("[Server] : Try to create a listener on another port!!!");
	}
	
	//Creating a Listener on specified port.
	private int createServerSocket(int port,int timeout) {
		
		System.out.println("[Server] : Creating a listener on port " + port);
		
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(timeout);
			return 1;			
		}
		catch(IOException e) {
			
			System.out.println("[Server] : Couldn't create a listener on port " + port + " !");
			e.printStackTrace();
		}		
		return -1;
	}
	
	//Communication between client and server.
	private void createConnection() {
		
		System.out.println("[Server] : Listener successfully created and listening on port " + serverSocket.getLocalPort());		
		
		List<String> clientMsg = new ArrayList<>();
		
		try(Socket server = serverSocket.accept();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()))){
			
			System.out.println("[Server] : Connection successfuly established with " + server.getRemoteSocketAddress());
			
			String[] serverMsg = {"Hello","My name is Server","Have a nice day","Bye"};		
			int count = 0;
			
			while(count < serverMsg.length) {
			
				writer.write(serverMsg[count++]);
				writer.newLine();
				writer.flush();
							
				clientMsg.add(reader.readLine());			
			}		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		if(clientMsg.size() > 0) writeToFile(clientMsg);	
	}
	
	//Writing client's messages to a local file.
	private void writeToFile(List<String> clientMsg) {
		
		try(BufferedWriter toFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/Users/micro/Desktop/Client's Messages.txt")))){
			
			for(String msg : clientMsg) {
				toFileWriter.write(msg);
				toFileWriter.newLine();
				toFileWriter.flush();
			}		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}

