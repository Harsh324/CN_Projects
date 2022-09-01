package ChatApp.Client;


import java.net.*;
import java.io.*;


public class Client {

    private String Host;
    private int Port;
    private String User_id;

    public Client(String host, int port)
    {
        this.Host = host;
        this.Port = port;
    }


    public void Run() 
    {
		try 
        {
			Socket socket = new Socket(this.Host, this.Port);

			System.out.println("Connected to the Server");

			new Read(socket, this).start();
			new Write(socket, this).start();

		} 
        catch (UnknownHostException ex) 
        {
			System.out.println("Server not found: " + ex.getMessage());
		} 
        catch (IOException ex) 
        {
			System.out.println("I/O Error: " + ex.getMessage());
		}

	}


    void setUser_id(String userName) 
    {
		this.User_id = userName;
	}

	String getUser_id() 
    {
		return this.User_id;
	}


    public static void main(String[] args) {
		// Input the Host
		String host = args[0];

        // Input the port
		int port = Integer.parseInt(args[1]);

		Client client = new Client(host, port);
		client.Run();
	}
    
}
