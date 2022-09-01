package ChatApp.Server;

/*
 * Importing Important packages
 */
import java.io.*;
import java.net.*;
import java.util.*;

/*
 * This class is the main class to handle the Server operations
 */
public class Server
{
    // Set to store the Current running threads
    private Set<Users> Threads = new HashSet<>();

    // Set to store the Active thread's user_Id.
    private Set<String> User_IDs = new HashSet<>();

    // Variable to store the Port number
    private int Port;

    /*
     * The Constructor of the Class Server that
     * takes the port number as input
     */
    public Server(int Port)
    {
        this.Port = Port;
    }

    /*
     * This method starts the server and continously
     * waits for the client to send request and accept it.
     */
    public void Run()
    {
        try(ServerSocket Socket_server = new ServerSocket(Port))
        {
            System.out.println("Server is listening at the Port : " + this.Port);

            while(true)
            {
                Socket socket = Socket_server.accept();
                System.out.println("User Connected.");

                Users User = new Users(socket, this);
                Threads.add(User);
                User.start();
            }
        }
        catch(IOException Ex)
        {
            System.out.println("Error in connecting with the server in Server file" + Ex.getMessage());
            Ex.printStackTrace();
        }
    }

    /*
     * Method to add User_ids to the User_ID Set.
     */
    void addUser_ids(String User_Id)
    {
		User_IDs.add(User_Id);
	}


    /*
     * Method to remove the User_id from the User_Id Set along with
     * Removing from the Thread Set.
     */
    void Remove_User(String User_Id, Users User) {
		boolean Flag = User_IDs.remove(User_Id);
		if(Flag) 
        {
			Threads.remove(User);
			System.out.println("User :- [ " + User_Id + " ] has quitted.");
		}
	}

    /*
     * Method to Broadcastt message from 
     * server to one of the client.
     */
    void Notify(String Message, Users excludeUser) 
    {
		for (Users It : Threads) 
        {
			if (It != excludeUser)
            {
				It.SendMessage(Message);
			}
		}
	}
    /*
     * method to get all active users list
     */
    Set<String> getUser_ids() {
		return this.User_IDs;
	}


    /*
     * Method to check if User_id set is not empty
     */
    boolean IfUsers()
    {
        if(this.User_IDs.isEmpty())
            return false;
        return true;
    }



    public static void main(String args[])
    {
        int Port;
        Port = Integer.parseInt(args[0]);

        Server server = new Server(Port);
        server.Run();
    }

}
