package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Client {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    Client(Socket socket){
        this.socket = socket;
    }

    Client(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream){
        this(socket);
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    void close(){
        try {
            socket.close();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e){
        }
    }
    void setOutputStream(ObjectOutputStream oos){
        outputStream = oos;
    }
    void setInutStream(ObjectInputStream ois){
        inputStream = ois;
    }
    public ObjectInputStream getInputStream() {
        return inputStream;
    }
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
    public Socket getSocket() {
        return socket;
    }
}
