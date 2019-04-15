package server.model.entities;

public class Ping extends Message {
    public Ping() {
        super(null, null, Type.PING);
    }
}
