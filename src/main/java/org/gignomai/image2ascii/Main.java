package org.gignomai.image2ascii;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Main {

    public static final String IMAGE_FILE_NAME = "1854_40px.jpg";

    public static void main(String[] args) {

        try {
            Optional<InputStream> resourceAsStream = Optional.ofNullable(Main.class.getClassLoader().getResourceAsStream(IMAGE_FILE_NAME));
            BufferedImage image = ImageIO.read(resourceAsStream.orElseThrow(IOException::new));

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    result.append(getPixelDarknessAsAsciiChar(image.getRGB(j, i))).append(" ");
                }
                result.append("\n");
            }
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static char getPixelDarknessAsAsciiChar(int pixel) {
        char[] map = " .,:;ox%#@".toCharArray();
        // char[] map = "@#%xo;:,. ".toCharArray();

        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        int pixelValue = (red + green + blue) / 3;

        Double mapIndexPixelValue = (pixelValue - 1) / 25.5d;

        return map[mapIndexPixelValue.intValue()];
    }

}