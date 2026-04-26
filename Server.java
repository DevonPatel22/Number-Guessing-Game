import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server {
    //Private variables for socker, port, list of printwriters and the hidden number
    private ServerSocket serverSocket;
    private int port;
    private List<PrintWriter> clientList;
    private int hiddenNumber;

    public Server(){
        //Open server at port 8080 and make a new list of clients
        this.port = 8080;
        openServerSocket(8080);
        clientList = Collections.synchronizedList(new LinkedList<>());
    }

    public Server(int port){
        //Start Server using port inputted by user
        System.out.println("Starting Server");
        this.port = port;
        openServerSocket(port);
        System.out.println("Ready for Clients");
        clientList = Collections.synchronizedList(new LinkedList<>());
    }

    private void openServerSocket(int port){
        //Calls to open the server with the socket
        //Utilized in constructors
        System.out.println("Opening port: " + port);
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.out.println("Could not open server on port " + port);
            throw new RuntimeException(e);
        }
    }

    public void run(){
        //calls to set a number
        String message;
        setNumber();

        //wait for connection to clients
        while(true){
            System.out.println("Waiting for clients");
            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Issue in Connection");
                throw new RuntimeException(e);
            }
            System.out.println("Client connected");

            System.out.println("Connected");

            //Makes new thread for the client that joins
            Thread newClientThread = new Thread(new ClientHandler(clientSocket, this));
            newClientThread.start();

        }

    }
    public synchronized int getNumber(){
        //getter for hiddenNumber
        return this.hiddenNumber;
    }

    public synchronized void setNumber(){
        //Resets hiddenNumber with new value
        int min = 1;
        int max = 1000000;
        this.hiddenNumber = (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public void sendToAll(String message) {
        //Sends a message to all of the joined clients
        synchronized(clientList) {
            for(PrintWriter writer : clientList) {
                writer.println(message);
            }
        }
    }

    public void removeClient(PrintWriter writer){
        //Removes a client from list
        clientList.remove(writer);
    }

    public void addClient(PrintWriter writer){
        //adds a client to the list
        clientList.add(writer);
    }
}