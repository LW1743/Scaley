package Actions;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadFile {

    public String filePath;

    public LoadFile() {
        selectFile();
    }

    public void selectFile() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "jpeg", "png");
        jFileChooser.setFileFilter(fileNameExtensionFilter);

        int response = jFileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION) {
            filePath = jFileChooser.getSelectedFile().getPath();
        }

    }
}
