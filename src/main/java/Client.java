import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
//        new Client().sendMessage("localhost", 12345);
//        new Client().receiveFileFromServer("localhost", 9543);
        new Client().sendFileToServer("localhost", 9543, "C:\\temp\\tttttttttttttt.csv");
    }

    private void sendMessage(String serverAddress, int port) {
        boolean liga = true;
        try {
            System.out.println("Abrindo");
            Socket socket = new Socket(serverAddress, port);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Abrido");
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            System.out.println("Server: " + entrada.readObject());
            while (liga) {
                Scanner sc = new Scanner(System.in);
                System.out.println("ligado com santa catarina");
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

    private void receiveFileFromServer(String serverAddress, int port) {
        try {
            Socket socket = new Socket(serverAddress, port);
            OutputStream os = socket.getOutputStream();
            System.out.println("Conectado no servidor: " + socket.getInetAddress().getHostAddress());
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            FileOutputStream fos = new FileOutputStream("C:\\temp\\tttttttttttttt.csv");
            is.read(bytes, 0, bytes.length);
            fos.write(bytes, 0, bytes.length);
            System.out.println("Arquivo recebido.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFileToServer(String serverAddress, int port, String filePath) {
        try {
            Socket socket = new Socket(serverAddress, port);
            System.out.println("Conectado no servidor: " + socket.getInetAddress().getHostAddress());
            FileInputStream fis = new FileInputStream(filePath);
            byte[] bytes = new byte[1024];
            OutputStream os = socket.getOutputStream();
            fis.read(bytes, 0, bytes.length);
            os.write(bytes, 0, bytes.length);
            System.out.println("Arquivo enviado!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//server respondendo request