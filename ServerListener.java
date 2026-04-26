import java.io.BufferedReader;
import java.io.IOException;

public class ServerListener implements Runnable{

    private BufferedReader buffer;

    //Coonstructor with a bufferedReader
    public ServerListener(BufferedReader buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        //Read what is in the buffered reader, and while it is not empty, send it to the server
        String message = null;
        try {
            message = buffer.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while(message != null){
            System.out.println(message);
            try {
                message = buffer.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
