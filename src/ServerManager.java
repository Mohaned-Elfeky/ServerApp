import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerManager extends Thread {
	
	int port;
	ObjectInputStream manager_in=null;
	ObjectOutputStream manager_out=null;
	
	public ServerManager(int port) throws IOException, InterruptedException {
		this.port=port;
	
		
		
		Socket s=null;
		
		try {
			s=new Socket("127.0.0.1",port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("manager couldnt connect");
			e.printStackTrace();
		}
		
		Scanner sc= new Scanner(System.in);

		System.out.println("start managing server:");
		System.out.println("type 'shutdown' to shut the server down");
		manager_out=new ObjectOutputStream(s.getOutputStream());
		while(true) {
			String command=sc.nextLine();		
			if(command.equals("shutdown")) {
				System.out.println("shutting down");
				manager_out.writeObject(new msg(command));
				sleep(1000);
				break;
				
		}		
			else {
				System.out.println(command+"is not a command");
				manager_out.writeObject(new msg(command));
			}
		
		}
		
//		System.out.println("manager exited");
	}

	
	public static void main(String[] args) throws IOException, InterruptedException {
		ServerManager sm=new ServerManager(5056);
	}
	
}
