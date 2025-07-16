import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class PreviewRenderer extends JPanel {

    MainFrame mainFrame;
    Vector2D imageMaxima = new Vector2D();

    public PreviewRenderer(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(!Objects.equals(mainFrame.selectedFilePath, "")) {
            BufferedImage bufferedImage;
            try {
                System.out.println(mainFrame.selectedFilePath);
                bufferedImage = ImageIO.read(new File(mainFrame.selectedFilePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getImageMaxima(bufferedImage, super.getWidth(), super.getHeight());
            g2.drawImage(bufferedImage, 0, 0, imageMaxima.getX(), imageMaxima.getY(), null);
            System.out.println("X: " + super.getX() + "  Y: " + super.getY() + "  Width: " + imageMaxima.getX() + "  Height: " + imageMaxima.getY() );
        }

        g2.dispose();


    }

    private void getImageMaxima(BufferedImage bufferedImage, int panelWidth, int panelHeight) {
        int height, width;
        float scaleFactor;

        height = bufferedImage.getHeight();
        width = bufferedImage.getWidth();

        if(width >= height) {
            scaleFactor = (float) panelWidth /width;
        }else {
            scaleFactor = (float) panelHeight /height;
        }

        System.out.println("panelWidth: " + panelWidth + "  panelHeight: " + panelHeight + "  Final Width: " + (width));
        imageMaxima.setX(Math.round(scaleFactor*width));
        imageMaxima.setY(Math.round(scaleFactor*height));

    }


}
