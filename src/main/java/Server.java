import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        new Server().startServer(12345);
    }

    private void startServer(int port) {
        try {
            boolean liga = true;
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            saida.flush();
            saida.writeObject("Conectado no servidor");
            while (liga) {
//                if(!socket.isConnected()) System.out.println("Client desconectado!");
                String clientMessage = (String) entrada.readObject();
                System.out.println("Mensagem: " + clientMessage);
                Scanner sc = new Scanner(System.in);
                System.out.print("Digite a mensagem('sair' para fechar conexão): ");
                String message = sc.nextLine();
                saida.flush();
                saida.writeObject(message);
                if ("sair".equals(message)) {
                    entrada.close();
                    saida.close();
                    socket.close();
                    server.close();
                    liga = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(int serverPort, String filePath) {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket socket = serverSocket.accept();
            System.out.println("Conectado: " + socket.getInetAddress().getHostAddress());
            FileInputStream fis = new FileInputStream(filePath);
            byte[] bytes = new byte[1024];
            System.out.println(fis.read(bytes, 0, bytes.length));
            OutputStream os = socket.getOutputStream();
            os.write(bytes, 0, bytes.length);
            System.out.println("Arquivo enviado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}