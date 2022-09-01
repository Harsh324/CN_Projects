package ChatApp.Client;

import java.net.*;
import java.io.*;

public class Read extends Thread {

    private Socket socket;
    private BufferedReader Reader;
    private Client client;

    public Read(Socket socket, Client client) 
    {
		this.socket = socket;
		this.client = client;

		try 
        {
			InputStream input = socket.getInputStream();
			Reader = new BufferedReader(new InputStreamReader(input));
		} 
        catch (IOException ex) 
        {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}


    public void run() 
    {
		while (true) 
        {
			try {
				String get_message = Reader.readLine();
				System.out.println("\n" + get_message);

				if (client.getUser_id() != null) {
					System.out.print("[" + client.getUser_id() + "]: ");
				}
			} 
            catch (IOException ex) 
            {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}

    
}
