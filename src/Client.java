import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	int port;
	
	public Client(int port) throws ClassNotFoundException, IOException {
		
		this.port=port;
		ObjectInputStream client_in=null;
		ObjectOutputStream client_out=null;
		
			
			Socket s=null;
			try {
				
				s = new Socket("127.0.0.1",5055);
				 
				 client_out=new ObjectOutputStream(s.getOutputStream());
				System.out.println("connected to server");
				
				
		
				
			} catch (IOException e) {
				
				System.out.println("couldnt connect to the server");
				e.printStackTrace();
			}
			
			Scanner sc=new Scanner(System.in);
			System.out.println("enter heat or light");
			String type=sc.next();
			client_out.writeObject(new msg(type));
			ClientInput ci=new ClientInput(s);
			
			ci.start();
			client_in=new ObjectInputStream(s.getInputStream());
			
			
			if(type.equals("heat")) {
			while(true&&!s.isClosed()) {
				msg from_server=(msg)client_in.readObject();
				String from_server_msg=from_server.msg;
				System.out.println(from_server.msg);
				
				int reading=Integer.parseInt(from_server_msg.substring(0,2));
				
				
				
				if(20<reading&&reading<23) {
					System.out.println("fan is set to high");
				}
				
				if(23<reading&&reading<25) {
					System.out.println("fan is set to medium");
				}				
				if(reading>25) {
					System.out.println("fan is set to high");
				}
				
			}
			
			}
			
			else {
				while(true && !s.isClosed()) {
					msg from_server=(msg)client_in.readObject();
					String from_server_msg=from_server.msg;
					System.out.println(from_server.msg);
					
					int reading=Integer.parseInt(from_server_msg.split(" ")[0]);
					
					
					
					if(15<reading&&reading<40) {
						System.out.println("brightness is set to high");
					}
					
					if(40<reading&&reading<100) {
						System.out.println("fan is set to medium");
					}				
					if(reading>100) {
						System.out.println("brightness is set to low");
					}
					
				}
				
			}
			
		
	}
	
		
	
	public static void main(String[] args) {
	
		try {
			try {
				Client client=new Client(5032);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				System.out.println("error here");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
