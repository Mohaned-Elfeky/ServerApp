import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientInput extends Thread{

	Socket socket;
	
	public ClientInput(Socket socket) {
		this.socket=socket;
	}
	
	@Override
	public void run() {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("type 'exit' any time to end connection  with server");
		while(true && !socket.isClosed()) {
		String in=sc.nextLine();
		if(in.equals("exit")) {
			try {
				socket.close();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		
			}
		}
	}
		sc.close();
		System.out.println("ended connedction with server");
		
	}
}
