package teste;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
//        return in.readLine();
    }

    public void readMessage() throws IOException {
        System.out.printf("Mensagem do servidor: %s", in.readLine());
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void sendFile(String path) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of(path)));
        out.println(base64);
        System.out.println("Mandei");
    }


    public void receiveFile() throws IOException {
        byte[] decode = Base64.getDecoder().decode(in.readLine());
        FileUtils.writeByteArrayToFile(new File("C:\\temp\\CC5_4P_PAULISTA.pdf"), decode);
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        Scanner sc = new Scanner(System.in);
        System.out.println("Bem vindo(a)! O que deseja fazer?");
        System.out.println("1: Trocar mensagens.");
        System.out.println("2: Trocar arquivos.");
        System.out.print("Digite um número: ");
        int option = sc.nextInt();
        switch (option) {
            case 1 -> {
                System.out.println("Conectando...");
                c.startConnection("localhost", 12345);
                c.readMessage();
                System.out.println("\nDigite uma mensagem: ");
                c.sendMessage(sc.next());
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
                        System.out.println("Conectando...");
                        System.out.println("Esperando cliente se conectar...");
                        c.startConnection("localhost", 12345);
                        c.sendFile(path);
                    }
                    case 2 -> {
                        System.out.println("Conectando...");
                        c.startConnection("localhost", 12345);
                        c.receiveFile();
                    }
                }
            }
        }
    }
}

