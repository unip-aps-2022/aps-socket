package aps;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
    }

    public void readMessage() throws IOException {
        System.out.printf("Mensagem do cliente: %s", in.readLine());
    }

    public void receiveFile() throws IOException {
        byte[] decode = Base64.getDecoder().decode(in.readLine());
        FileUtils.writeByteArrayToFile(new File("C:/temp/Livro Texto.pdf"), decode);
    }

    //        D:/temp/CC5_4P_PAULISTA.pdf
    //        D:/temp/Livro Texto.pdf
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

    public int readInt(Scanner scanner) {
        int option = -1;
        while (option <= 0 || option > 2) {
            try {
                System.out.print("Escolha uma opção: ");
                option = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException ignored) {

            }
        }
        return option;
    }

    public static void main(String[] args) {
        try {
            System.out.println("""
                    Inicializando a central de comunicação da Secretaria de Estado do Meio Ambiente...
                    Este servidor tem como finalidade realizar a troca de infomações entre as\s
                    equipes de inspeções enviadas para o Rio Tietê e a Secretaria.\s
                    """);
            Server sv = new Server();
            sv.start(12345);
            boolean stop = false;
            Scanner sc = new Scanner(System.in);
            System.out.println("O que deseja fazer: (Apenas um tipo de informação pode ser trocada por conexão)");
            System.out.println("Enviar mensagens: 1");
            System.out.println("Enviar arquivo: 2");
            int option = sv.readInt(sc);
            while (!stop) {
                switch (option) {
                    case 1 -> {
                        System.out.println("\nDigite uma mensagem(ou 'sair' para encerrar): ");
                        String msg = sc.nextLine();
                        if ("sair".equals(msg)) {
                            sv.stop();
                            stop = true;
                        }
                        sv.sendMessage(msg);
                        sv.readMessage();
                    }
                    case 2 -> {
                        System.out.println("Gostaria de enviar ou receber arquivos?");
                        System.out.println("1: Enviar.");
                        System.out.println("2: Receber.");
                        System.out.println("Digite um número para escolher: ");
                        int fileOption = sv.readInt(sc);
                        switch (fileOption) {
                            case 1 -> {
                                System.out.println("Cole o caminho do arquivo: ");
                                String path = sc.nextLine();
                                sv.sendFile(path);
                                stop = true;
                            }
                            case 2 -> {
                                sv.receiveFile();
                                stop = true;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
