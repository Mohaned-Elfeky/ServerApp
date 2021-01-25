import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	int port;
	

	ServerSocket server_socket;
	boolean shutdown;
	ArrayList<Socket> connected_clients;
	 ArrayList<ClientInput> connected_inputs=new ArrayList<ClientInput>();
	
	
	public Server(int port) {
		this.port=port;
		shutdown=false;
		connected_clients=new ArrayList<Socket>();
		
		
		try {
			server_socket=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("failed to setup server socket");
		}
	}
	
	public void initializeServer() {
		ConnectionListener cl = new ConnectionListener(server_socket, port,this);
		cl.start();
		
		ManagerHandler mh=new ManagerHandler(this);
		mh.start();
		
		
		
	}
	
	public   void endConnections() {



		for(int i=0;i<this.connected_clients.size();i++) {
		try {
			this.connected_clients.get(i).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		try {
			server_socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ended all connections");
	}
	
	
//	public static Server getActiveServer() {
//		return this;
//	}
	
	public static void main(String [] args) {
	
		Server server=new Server(5055);         
		server.initializeServer();
	}
	
}
