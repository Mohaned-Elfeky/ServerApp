import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {

	
	ServerSocket server_socket;
	int port;
	Server server;
	
	public ConnectionListener(ServerSocket server_socket,int port,Server server) {
		this.server_socket=server_socket;
		this.port=port;
		this.server=server;
		
	}
	
	@Override
	public void run() {
		
		System.out.println("listening to clients at port "+port);
		
	
		
		while(true && !server.shutdown) {
			

			
			Socket s=null;
		try {
			s=this.server_socket.accept();
			server.connected_clients.add(s);
			
			System.out.println("a client is connected");
			
			ClientHandler ch=new ClientHandler(s,this.server);
			ch.start();
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("stopped listening to connections");
//			e.printStackTrace();
		}
		
		}
		System.out.println("stopped listening");
	}
	
}
