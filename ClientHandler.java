import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private BufferedReader clientReader;
    private Server server;
    private PrintWriter writer;

    public ClientHandler(Socket clientSocket, Server server){
        //Make a buffered reader to receive the inputStream of the client socket
        try {
            clientReader = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.server = server;

        //Make a Print Writer to output to the client
        try {
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
            server.addClient(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int parseToInt(String message) {
        //Takes the users input and parses it to an int for testing if the guess is too high or low
        try {
            return Integer.parseInt(message);
        } catch (NumberFormatException e) {
            writer.println("Enter a valid number");
            return -1;
        }
    }


    @Override
    public void run(){

        String message = null;
        try{
            //receive message from client
            System.out.println("Waiting for Message");
            String clientName = clientReader.readLine();


            while((message = clientReader.readLine()) != null){
                //parse to int and compare to hiddenNumber
                int guess = parseToInt(message);
                if(guess == -1){
                    continue;
                }
                if(guess == server.getNumber()){
                    writer.println("Your number: " + guess + " was Correct!");
                    server.sendToAll("Game over, "+ clientName + " won, the number was " + server.getNumber());
                    server.setNumber();
                } else if (guess > server.getNumber()) {
                    writer.println("Your number: " + guess + " was Too High");
                }else{
                    writer.println("Your number: " + guess + " was Too Low");
                }
            }
        }
        catch (IOException e){
            server.removeClient(writer);
            System.out.println("Client Disconnected");
        }
    }
}
