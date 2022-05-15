package teste;

import java.io.*;
import java.net.Socket;

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

    public void sendFile(String path){
        File file = new File(path);
        out.println(file);
    }

    public void receiveFile() throws IOException {
        in = new BufferedReader(new FileReader(in.readLine()));
        String strCurrentLine;
        while ((strCurrentLine = in.readLine()) != null) {
            System.out.println(strCurrentLine);
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.startConnection("localhost", 12345);
//        c.receiveFile();
        c.sendFile("D:\\temp\\testSV.csv");
//        System.out.println(c.sendMessage("OII"));
//        System.out.println(c.sendMessage("hello server"));
    }
}

