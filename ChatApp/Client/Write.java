import java.io.*;
import java.net.*;


public class Write extends Thread{

    private PrintWriter Writer;
    private Socket socket;
    private Client client;


    public Write(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;

		try 
        {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} 
        catch (IOException ex) 
        {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

    public void run() 
    {
		Console console = System.console();

		String userName = console.readLine("\nEnter your name: ");
		client.setUser_id(userName);
		writer.println(userName);

		String text;

		do 
        {
			text = console.readLine("[" + userName + "]: ");
			writer.println(text);

		} 
        while (!text.equals("Exit"));

		try 
        {
			socket.close();
		} 
        catch (IOException ex) 
        {
			System.out.println("Error writing to server: " + ex.getMessage());
		}
	}




    
}
