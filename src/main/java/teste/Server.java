package teste;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

public class Server {
    private Socket clientSocket;
    private ServerSocket server;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        clientSocket = server.accept();
        System.out.printf("Conectado no client: %s\n", clientSocket.getInetAddress().getHostAddress());
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
//        return in.readLine();
    }

    public void readMessage() throws IOException {
        System.out.printf("Mensagem do cliente: %s", in.readLine());
    }

    public void receiveFile() throws IOException {
        byte[] decode = Base64.getDecoder().decode(in.readLine());
        FileUtils.writeByteArrayToFile(new File("C:\\temp\\CC5_4P_PAULISTA.pdf"), decode);
    }

    //        D:\temp\CC5_4P_PAULISTA.pdf
    public void sendFile(String path) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of(path)));
        out.println(base64);
        System.out.println("Mandei");
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        server.close();
    }

    public static void main(String[] args) {
        try {
//            sv.sendFile("D:\\temp\\TESTEEEEE.pdf");
            Server sv = new Server();
            Scanner sc = new Scanner(System.in);
            System.out.println("Bem vindo(a)! O que deseja fazer?");
            System.out.println("1: Trocar mensagens.");
            System.out.println("2: Trocar arquivos.");
            System.out.print("Digite um número: ");
            int option = sc.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.println("Servidor iniciando, esperando o cliente se conectar...");
                    sv.start(12345);
                    System.out.println("Digite uma mensagem: ");
                    sv.sendMessage(sc.next());
                    sv.readMessage();
                }
                case 2 -> {
                    System.out.println("Gostaria de enviar ou receber arquivos?");
                    System.out.println("1: Enviar.");
                    System.out.println("2: Receber.");
                    System.out.println("Digite um número para escolher: ");
                    int fileOption = sc.nextInt();
                    switch (fileOption) {
                        case 1 -> {
                            System.out.println("Cole o caminho do arquivo: ");
                            String path = sc.next();
                            System.out.println("Servidor iniciando, esperando o cliente se conectar...");
                            sv.start(12345);
                            sv.sendFile(path);
                        }
                        case 2 -> {
                            System.out.println("Servidor iniciando, esperando o cliente se conectar...");
                            sv.start(12345);
                            sv.receiveFile();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
