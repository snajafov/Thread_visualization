import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {

    long nextSend = 0;

    static final int WIDTH = 570, HEIGHT = 480; // Width and height of a window
    Timer timer = new Timer(1, this); // Timer to update a new frame
    static Thread[] threads = new Thread[4]; // An array of threads
    static Buffer[] buffers = new Buffer[3]; // An array of buffers

    @Override
    protected void paintComponent(Graphics g) { // Method to paint on a canvas
        timer.start(); // Start a timer so that we can have an animation
        // Drawing a white background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // Drawing and updating threads and drawing arrows between threads and their receiver buffers
        for (Thread th : threads) {
            if (th.receiver != null) {
                arrow(g, th);
            }
            th.update();
            th.draw(g);
        }

        // Drawing and updating buffers and drawing arrows between buffers and their receiver threads
        for (Buffer buf : buffers) {
            if (buf.receiver != null) {
                arrow(g, buf);
            }
            buf.draw(g);
            buf.update();
        }

        // Send a data if you have sent it more than 3 seconds before this moment
        if (nextSend < System.currentTimeMillis()) {
            threads[0].receive();
            nextSend = System.currentTimeMillis() + 3000;
        }
    }

    public void arrow(Graphics g, DataSender ds1) {
        // Changing a weight of a stroke
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        // Calculating all the variables needed for drawing
        DataSender ds2 = ds1.receiver;
        int x1 = ds1.getX() + ds1.getD() / 2;
        int x2 = ds2.getX() + ds2.getD() / 2;
        int y1 = ds1.getY() + ds1.getD() / 2;
        int y2 = ds2.getY() + ds2.getD() / 2;
        Color color = ds2.color;
        g.setColor(color);

        g.drawLine(x1, y1, x2, y2);// Drawing a line between thread and buffer
        g2.setStroke(new BasicStroke(1));// Resetting the weight of a stroke
    }

    public static void main(String[] args) {
        // Initializing an array of threads and an array of buffers
        threads[0] = new Thread(50, HEIGHT / 2 - 100);
        threads[1] = new Thread(190, HEIGHT / 2 - 100);
        threads[2] = new Thread(330, HEIGHT / 2 - 100);
        threads[3] = new Thread(470, HEIGHT / 2 - 100);
        buffers[0] = new Buffer(120, HEIGHT * 3 / 4 - 100);
        buffers[1] = new Buffer(260, HEIGHT * 3 / 4 - 100);
        buffers[2] = new Buffer(400, HEIGHT * 3 / 4 - 100);
        threads[0].setReceiver(buffers[0]);
        buffers[0].setReceiver(threads[1]);
        threads[1].setReceiver(buffers[1]);
        buffers[1].setReceiver(threads[2]);
        threads[2].setReceiver(buffers[2]);
        buffers[2].setReceiver(threads[3]);

        JFrame jf = new JFrame();
        Main jp = new Main();
        jf.setSize(WIDTH, HEIGHT);
        jf.setTitle("Threads Simulation");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.add(jp);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint(); // Each frame repaint
    }
}
