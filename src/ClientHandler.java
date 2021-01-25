import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.print.attribute.standard.Severity;

public class ClientHandler extends Thread {

	ObjectOutputStream server_out;
	ObjectInputStream server_in;
	
	Server server;
	Socket s;
	
	
	
	public ClientHandler( Socket s,Server server) throws IOException {
		this.s = s;
		this.server=server;
		
		server_out=new ObjectOutputStream(s.getOutputStream());
		System.out.println("assigned out");
		server_in=new ObjectInputStream(s.getInputStream());
		System.out.println("assigned in");
	}
	
	@Override
	public void run() {
		
		String from_client=null;
		

		
		
		try {
			msg obj_from_client=(msg)server_in.readObject();
			from_client=obj_from_client.msg;
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			sendData(from_client);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

	private  void sendData(String type) throws InterruptedException {
		int n=2000;
		String path="C:\\Users\\mohaned\\eclipse-workspace\\Temprature_server\\sensor_data.csv";
		try {
			BufferedReader br=new BufferedReader(new FileReader(path));
			br.readLine();
			
			
			while(true && s.isConnected()) {
				
				if(server.shutdown) {
					break;
				}
				
				if(type.equals("heat")) {
					String line=br.readLine();
					
					if(line==null) {
						System.out.println("reseting reader");
						 br=new BufferedReader(new FileReader(path));
						 br.readLine();
						 line=br.readLine();
					}
					String temp=line.split(",")[9];
					server_out.writeObject(new msg(temp+"C"));
					sleep(n);
				
				}
				
				if(type.equals("light")) {
					String line=br.readLine();
					if(line==null) {
						System.out.println("reseting reader");
						 br=new BufferedReader(new FileReader(path));
						 br.readLine();
						 line=br.readLine();
					}
					String temp=line.split(",")[7];
					server_out.writeObject(new msg(temp+" Lumin"));
					sleep(n);
				
				}
				
				
			}
			
			
			System.out.println("client disconnected");
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("couldnt read file");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
