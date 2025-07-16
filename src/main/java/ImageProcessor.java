import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ImageProcessor {

    String imagePath;

    public ImageProcessor(String imagePath) {
        this.imagePath = imagePath;
    }

    public BufferedImage imageScaler(float setScale) throws IOException {

        BufferedImage inputImage;
        BufferedImage outputImage = null;
        float groupedPixels;

        int inputWidth, inputHeight, newWidth, newHeight;

        if(!imagePath.isEmpty()) {
            inputImage = ImageIO.read(new File(imagePath));
            inputWidth = inputImage.getWidth();
            inputHeight = inputImage.getHeight();


            groupedPixels = 100/setScale;
            newWidth = (int) Math.floor(inputWidth/groupedPixels);
            newHeight = (int) Math.floor(inputHeight/groupedPixels);
            System.out.println("New Height: " + newHeight);
            outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

            int currentX, currentY;
            System.out.println(inputImage.getRGB(1,1));
            for(int i=0; i < newHeight; i++) {
                currentY = (int) Math.floor(i*groupedPixels);

                for(int j=0; j < newWidth; j++) {
                    currentX = (int) Math.floor(j*groupedPixels);
                    int[][] argbArray = new int[4][(int) Math.floor(groupedPixels*groupedPixels)];


                    for(int gI=0; gI < groupedPixels; gI++) {

                        for(int gJ=0; gJ < groupedPixels; gJ++) {
                            byte[] input = new byte[4];
                            int value  = inputImage.getRGB((currentX+gJ), (currentY+gI) );
                            ByteBuffer.wrap(input).putInt(value);
                            //System.out.println( "X: " + (currentX+gJ) + "  Y: " + (currentY+gI) + "  Value: " + value + "  ByteBuffer: " + input[0] + " " + input[1] + " " + input[2] + " " + input[3] );
                            argbArray[0][gI+gJ] = (int) input[0];
                            argbArray[1][gI+gJ] = (int) input[1];
                            argbArray[2][gI+gJ] = (int) input[2];
                            argbArray[3][gI+gJ] = (int) input[3];
                        }
                    }

                    int[] medianArray = new int[4];

                    for(int a=0; a < 4; a++) {

                        int value = 0;
                        int bVal = (int) Math.floor(groupedPixels*groupedPixels);
                        System.out.println("bVal: " + bVal);
                        for(int b=0; b < bVal; b++) {
                            value += argbArray[a][b];

                        }
                        System.out.println("Value: " + value);
                        System.out.println((float) (Math.abs(value)/bVal));
                        medianArray[a] =Math.round(Math.abs(value)/bVal);
                        System.out.println("Array at: " + a + "  is: " + medianArray[a]);
                    }

                    ByteBuffer median = ByteBuffer.allocate(32).putInt( medianArray[0]).putInt( medianArray[1]).putInt( medianArray[2]).putInt( medianArray[3]);
                    System.out.println("median: " + median.getInt());
                    outputImage.setRGB(j, i, median.getInt());
                }

            }

        }
            return outputImage;
        }




}

