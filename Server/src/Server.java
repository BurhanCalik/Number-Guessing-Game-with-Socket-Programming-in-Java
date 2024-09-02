import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(4337);
            System.out.println("Server is listening on port 4337...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Generate a random number between 1 and 10 for each client
                int myNumber = new Random().nextInt(10) + 1;

                handleClient(clientSocket, myNumber);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, int myNumber) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            int count = 1;
            int health = 2;
            while (count == 1) {
                
                // Read the client's guess
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                int clientGuess = Integer.parseInt(new String(buffer, 0, bytesRead).trim());

                System.out.println("Client guessed: " + clientGuess);

                if (clientGuess < myNumber && health > 0) {
                    outputStream.write("The number is greater than your guess.\n".getBytes());
                    health = health - 1;
                } else if (clientGuess > myNumber && health > 0) {
                    outputStream.write("The number is less than your guess.\n".getBytes());
                    health = health - 1;
                } else if (clientGuess == myNumber) {
                    outputStream.write("You Win!\n".getBytes());
                    System.out.println("Client Won the Game!");
                    count = 0;
                    health = 0;
                    break;
                }
                else if (health == 0 && clientGuess != myNumber) {
                    outputStream.write("Game Over, You Lose!\n".getBytes());
                    
                }
                
            }

        } catch (IOException e) {
            
        }
    }
}
