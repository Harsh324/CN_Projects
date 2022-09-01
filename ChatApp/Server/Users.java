package ChatApp.Server;

/*
 * Importing important packages
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Users extends Thread
{
    private Socket socket;
	private Server server;
	private PrintWriter writer;

	public Users(Socket socket, Server server) 
    {
		this.socket = socket;
		this.server = server;
	}

    public void Start()
    {
        try
        {
            InputStream Input = socket.getInputStream();
            BufferedReader Reader = new BufferedReader(new InputStreamReader(Input));

            OutputStream Output = socket.getOutputStream();
			writer = new PrintWriter(Output, true);

            String User_id = Reader.readLine();
            server.addUser_ids(User_id);

            String Message = "User Connected - " + User_id;
            server.Notify(Message, this);

            String Client_message = Reader.readLine();

            while(!Client_message.equals("Thank You"))
            {
                Client_message = Reader.readLine();
                Message = "[ " + User_id + " ] -> " + Client_message;
                server.Notify(Message, this);
            }

            server.Remove_User(User_id, this);
            socket.close();

            Message = User_id + " has quitted ";
            server.Notify(Message, this);

        }
        catch(IOException Ex)
        {
            System.out.println("Error in connecting with the server int Users file" + Ex.getMessage());
            Ex.printStackTrace();
        }
    }

    void SendMessage(String message) 
    {
		writer.println(message);
	}

    
    void PrintUsers()
    {
        if(server.IfUsers())
        {
            writer.println("Active users are : " + server.getUser_ids());
        }
        else
        {
            writer.println("Active users list is empty");
        }
    }
}
