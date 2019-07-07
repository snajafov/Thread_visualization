import java.awt.*;

public abstract class DataSender {
    DataSender receiver; // Receiver of a data sender(thread for buffer and vice versa)
    Color color = Color.GREEN; // Color of a data sender based on a readiness
    int toSend = 0; // Number of messages to send

    private int x; // X position
    private int y; // Y position
    private int d = 50; // Diameter

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getD() {
        return d;
    }

    DataSender(int x, int y) {
        this.x = x;
        this.y = y;
    }

    DataSender(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.d = r;
    }

    void setReceiver(DataSender receiver) {
        this.receiver = receiver;
    }

    public abstract void update(); // Update a state of a data sender

    public abstract void send(); // Send a message to a receiver

    public abstract void receive(); // Receive a message

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, d, d);
    } // Draw a data sender
}
