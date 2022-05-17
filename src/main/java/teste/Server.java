package teste;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
//        String greeting = in.readLine();
//        if ("hello server".equals(greeting)) {
//            out.println("hello client");
//        } else {
//            out.println("unrecognised greeting");
//        }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void receiveFile() throws IOException {
        FileInputStream fis = new FileInputStream(in.readLine());
        FileOutputStream fos = new FileOutputStream("C:\\temp\\testSV.csv");
        byte[] bytes = new byte[1024];
        fis.read(bytes, 0, bytes.length);
        fos.write(bytes, 0, bytes.length);
//        in = new BufferedReader(new FileReader(in.readLine()));
//        String strCurrentLine;
//        while ((strCurrentLine = in.readLine()) != null) {
//            System.out.println(strCurrentLine);
//        }
    }

    public void sendFile(String path) {
        File file = new File(path);
        out.println(file);
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
                case 1:
                    System.out.println("Servidor iniciando, esperando o cliente se conectar...");
                    sv.start(12345);
                    break;
                case 2:
                    System.out.println("Gostaria de enviar ou receber arquivos?");
                    System.out.println("1: Enviar.");
                    System.out.println("2: Receber.");
                    System.out.println("Digite um número para escolher: ");
                    int fileOption = sc.nextInt();
                    switch (fileOption){
                        case 1:
                            System.out.println("Cole o caminho do arquivo: ");
                            String path = sc.next();
                            System.out.println("Servidor iniciando, esperando o cliente se conectar...");
                            sv.start(12345);
                            sv.sendFile(path);
                        case 2:
                            System.out.println("Servidor iniciando, esperando o cliente se conectar...");
                            sv.start(12345);
                            sv.receiveFile();
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
