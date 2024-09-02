import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 4337);
            System.out.println("Connected to server.");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);

            for (int i = 0; i < 3; i++) {
                System.out.print("Enter your guess (1-10):");
                int guess = scanner.nextInt();

                // Send the guess to the server
                outputStream.write(Integer.toString(guess).getBytes());

                // Receive and print the server's response
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                String serverResponse = new String(buffer, 0, bytesRead);
                System.out.println(serverResponse);
                
                if(serverResponse.contains("You Win!")) {
                	
                	break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
