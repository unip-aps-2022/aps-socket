import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        new Client().sendMessage("localhost", 12345);
    }

    private void sendMessage(String serverAddress, int port) {
        boolean liga = true;
        try {
            Socket socket = new Socket(serverAddress, port);
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Server: " + entrada.readObject());
            while (liga) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Digite a mensagem('sair' para fechar conexão): ");
                String message = sc.nextLine();
                saida.flush();
                saida.writeObject(message);
                if ("sair".equals(message)) {
                    System.out.println("Conexão fechada!");
                    saida.close();
                    entrada.close();
                    socket.close();
                    liga = false;
                }
                String serverMessage = (String) entrada.readObject();
                System.out.println("Message: " + serverMessage);
                if ("sair".equals(serverMessage)) {
                    System.out.println("Conexão fechada!");
                    saida.close();
                    entrada.close();
                    socket.close();
                    liga = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void receiveFileFromFileServer(String serverAddress, int port) {
        try {
            byte[] bytes = new byte[1024];
            Socket socket = new Socket(serverAddress, port);
            System.out.println("Conectado no servidor: " + socket.getInetAddress().getHostAddress());
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:\\temp\\tttttttttttttt.csv");
            is.read(bytes, 0, bytes.length);
            fos.write(bytes, 0, bytes.length);
            System.out.println("Arquivo recebido.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//server respondendo request