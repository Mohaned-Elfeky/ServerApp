import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ManagerHandler extends Thread {
	Server server;
	
	public ManagerHandler(Server server) {
		this.server=server;
	}
	
	
	@Override
	public void run() {
		System.out.println("listening to manager at port 5056");
		ServerSocket ss=null;
		try {
			 ss=new ServerSocket(5056);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Socket s = null;
		ObjectInputStream from_manager = null;
		try {
			s = ss.accept();
			System.out.println("manager is connected");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			from_manager = new ObjectInputStream(s.getInputStream());
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		
				while(true) {
					
					
					msg command_in=(msg) from_manager.readObject();
				
					String command=command_in.msg;
					if(command.equals("shutdown")) {
						this.server.shutdown=true;
						this.server.endConnections();
						s.close();
						break;
						
					}
			}
				
				System.out.println("server is shutdown");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
