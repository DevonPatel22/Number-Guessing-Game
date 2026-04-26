import java.io.PrintWriter;
import java.util.Scanner;

public class UserInput implements Runnable{

    private PrintWriter writer;
    private Scanner scnr;


    //Constructor with a PrintWriter and Scanner
    public UserInput(PrintWriter writer, Scanner scnr){
        this.writer = writer;
        this.scnr = scnr;
    }

    @Override
    public void run() {

        //have the user guess until they enter -1
        String guess = "";
        while(!guess.equals("-1")){
            System.out.println("Make a guess (1 - 1,000,000)");
            guess = scnr.nextLine();
            if(guess.equals("-1")){
                break;
            }
            writer.println(guess);
        }
        //exit program when they enter -1
        System.exit(0);
    }
}
