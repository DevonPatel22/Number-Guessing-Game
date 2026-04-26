public class Main {
    //Creates and starts a new server
    public static void main(String[] args){
        Server server = new Server(8080);
        server.run();
    }

}
