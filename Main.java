import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Create scanner and get the client's name
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = scnr.nextLine();


        //Connect to the server socket
        Socket socket;
        try {
            socket = new Socket("localhost", 8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Make a PrintWriter for the userInput thread argument
        PrintWriter writer;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Make a BufferedReader for the serverListener thread argument
        BufferedReader buffer;
        try {
            buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.println(name);

        //make and start threads

        Thread userInput = new Thread(new UserInput(writer, scnr));
        Thread serverListener = new Thread(new ServerListener(buffer));

        userInput.start();
        serverListener.start();

    }
}