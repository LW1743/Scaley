package Actions;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportFile {

    public String filePath;

    public ExportFile() {

    }

    public void saveImage(BufferedImage outputImage) throws IOException {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "jpeg", "png");
        jFileChooser.setFileFilter(fileNameExtensionFilter);

        int response = jFileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION) {
            filePath = jFileChooser.getSelectedFile().getPath();
            ImageIO.write(outputImage, "png", new File(filePath));
        }

    }
}
