import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
//        new Server().sendFile(9543, "D:\\temp\\testSV.csv");
//        new Server().startServer(12345);
//        new Server().receiveFileFromClient(9543);
        Scanner sc = new Scanner(System.in);
        System.out.println("Bem vindo(a)! O que deseja fazer?");
        System.out.println("1: Trocar mensagens");
        System.out.println("2: Trocar arquivos");
        System.out.print("Digite um número: ");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                System.out.println("Servidor iniciando, esperando conexão...");
                new Server().startServer(12345);
            case 2:
                new Server().sendFileToClient(9543, "D:\\temp\\conteudo.txt");
        }
    }

    private void startServer(int port) {
        try {
            boolean liga = true;
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            saida.flush();
            saida.writeObject("Conectado no servidor");
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
            while (liga) {
                String clientMessage = (String) entrada.readObject();
                System.out.println("Mensagem: " + clientMessage);
                if ("sair".equals(clientMessage)) {
                    entrada.close();
                    saida.close();
                    socket.close();
                    server.close();
                    System.out.println("Conexão fechada!");
                    System.exit(0);
                }
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
                    System.out.println("Conexão fechada!");
                    System.exit(0);
                }

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void sendFileToClient(int port, String filePath) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("Conectado: " + socket.getInetAddress().getHostAddress());
            InputStream is = socket.getInputStream();
            FileInputStream fis = new FileInputStream(filePath);
            byte[] bytes = new byte[1024];
            OutputStream os = socket.getOutputStream();
            os.write(bytes, 0, bytes.length);
            System.out.println("Arquivo enviado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFileFromClient(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            String home = System.getProperty("user.home") + "/Downloads/test2.csv";
            System.out.println(home);
//            File file = new File(home+"/Downloads/" + "test.csv");
            FileOutputStream fos = new FileOutputStream(home);
            byte[] bytes = new byte[1024];
            is.read(bytes, 0, bytes.length);
            fos.write(bytes, 0, bytes.length);
            System.out.println("Arquivo recebido!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}