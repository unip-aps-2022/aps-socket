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
        String resp = in.readLine();
        if (resp != null) {
            System.out.printf("Mensagem do servidor: %s", resp);
        }
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


    public void receiveFile() throws IOException {
        byte[] decode = Base64.getDecoder().decode(in.readLine());
        FileUtils.writeByteArrayToFile(new File("C:\\temp\\CC5_4P_PAULISTA.pdf"), decode);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("""
                Inicializando a central de comunicação da Secretaria de Estado do Meio Ambiente...
                Este dispositivo Client tem como finalidade realizar uma conexão direta com o servidor da Secretaria de Estado do Meio Ambiente para
                realizar a troca de informações das equipes de inspeções enviadas para o Rio Tietê com a equipe interna da Secretaria. Por meio deste,
                é possível trocar mensagens de texto ou arquivos com o servidor.
                Atenção: Apenas realize a comunicação quando necessário, pois
                apenas uma equipe pode realizar a comunicação por vez, fazendo com que a outra equipe que precise se comunicar tenha que aguardar.
                                """);
        Client c = new Client();
        boolean stop = false;
        Scanner sc = new Scanner(System.in);
        c.startConnection("localhost", 12345);
        System.out.println("O que deseja fazer: (Apenas um tipo de informação pode ser trocada por conexão)");
        System.out.println("Trocar mensagens: 1");
        System.out.println("Trocar arquivo: 2");
        int option = c.readInt(sc);
        while (!stop) {
            switch (option) {
                case 1 -> {
                    System.out.println("Conectado...");
                    c.readMessage();
                    System.out.println("\nDigite uma mensagem(ou 'sair' para encerrar: ");
                    String msg = sc.nextLine();
                    if ("sair".equals(msg)) {
                        c.stopConnection();
                        stop = true;
                    }
                    c.sendMessage(msg);
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
                            System.out.println("Conectado...");
                            System.out.println("Esperando cliente se conectar...");
                            c.sendFile(path);
                        }
                        case 2 -> {
                            System.out.println("Conectado...");
                            c.receiveFile();
                        }
                    }
                }
            }
        }
    }
}

