import java.awt.*;

public class Buffer extends DataSender {
    private long nextReceive = 0;

    Buffer(int x, int y) {
        super(x, y);
    }

    Buffer(int x, int y, int r) {
        super(x, y, r);
    }

    @Override
    public void update() {
        send();

        if (nextReceive < System.currentTimeMillis()) { // If the last message is processed the buffer is ready for a new one
            color = Color.GREEN;
        }
    }

    @Override
    public void send() {
        if (nextReceive < System.currentTimeMillis() && toSend > 0) {
            // If the buffer have a message to send and it is free then send a message
            receiver.receive();
            toSend--;
        }
    }

    @Override
    public void receive() {
        if (nextReceive < System.currentTimeMillis() && toSend == 0) {
            // If the buffer is free and has no message to send then receive a new message
            color = Color.RED;
            nextReceive = System.currentTimeMillis() + (long) (Math.random() * 2000);
            toSend++;
        }
    }
}
