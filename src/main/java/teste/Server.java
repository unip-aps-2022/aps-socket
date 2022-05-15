package teste;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
        FileOutputStream fos = new FileOutputStream("C:\\temp\\tttttttttttttt.csv");
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
            Server sv = new Server();
            sv.start(12345);
//            sv.sendFile("D:\\temp\\TESTEEEEE.pdf");
            sv.receiveFile();
//            System.out.println(sv.sendMessage("AI"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
