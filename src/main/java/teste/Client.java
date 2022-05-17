package teste;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void sendFile(String path) throws FileNotFoundException {
        File file = new File(path);
        OutputStream os = new FileOutputStream(file);
        out.println(file);
    }

    public void receiveFile() throws IOException {
        FileInputStream fis = new FileInputStream(in.readLine());
        FileOutputStream fos = new FileOutputStream("C:\\temp\\TESTEEEEE.pdf");
        byte[] bytes = new byte[1024];
        fis.read(bytes, 0, bytes.length);
        fos.write(bytes, 0, bytes.length);
//        in = new BufferedReader(new FileReader(in.readLine()));
//        String strCurrentLine;
//        while ((strCurrentLine = in.readLine()) != null) {
//            System.out.println(strCurrentLine);
//        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
//        c.startConnection("localhost", 12345);
//        c.sendFile("D:\\temp\\TESTEEEEE.pdf");
//        System.out.println(c.sendMessage("OII"));
//        System.out.println(c.sendMessage("hello server"));
        Scanner sc = new Scanner(System.in);
        System.out.println("Bem vindo(a)! O que deseja fazer?");
        System.out.println("1: Trocar mensagens.");
        System.out.println("2: Trocar arquivos.");
        System.out.print("Digite um número: ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Servidor iniciando, esperando conexão...");
                c.startConnection("localhost", 12345);
                break;
            case 2:
                System.out.println("Gostaria de enviar ou receber arquivos?");
                System.out.println("1: Enviar.");
                System.out.println("2: Receber.");
                System.out.println("Digite um número para escolher: ");
                int fileOption = sc.nextInt();
                switch (fileOption) {
                    case 1:
                        System.out.println("Cole o caminho do arquivo: ");
                        String path = sc.next();
                        System.out.println("Servidor iniciando!");
                        System.out.println("Esperando cliente se conectar...");
                        c.startConnection("localhost", 12345);
                        c.sendFile(path);
                    case 2:
                        System.out.println("Servidor iniciando!");
                        System.out.println("Esperando o cliente se conectar...");
                        c.startConnection("localhost", 12345);
                        c.receiveFile();
                }
        }
    }
}

