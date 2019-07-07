import java.awt.*;

public class Thread extends DataSender {
    Thread(int x, int y) {
        super(x, y);
    }

    Thread(int x, int y, int r) {
        super(x, y, r);
    }

    @Override
    public void update() {
        send();
        color = Color.GREEN;
    }

    @Override
    public void send() {
        if (toSend > 0 && receiver != null) {
            receiver.update(); // Update a receiver
            receiver.receive(); // Then send a message
            toSend--; // After sending a message decrease a number of message to be sent
        }
    }

    @Override
    public void receive() {
        color = Color.RED;
        toSend++;
    }
}
