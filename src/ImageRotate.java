import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

/**
 * It was used to rotate images of weapons, but never used when playing.
 */
public class ImageRotate {


    public BufferedImage rotateImage(BufferedImage image, double theta, Color backgroundColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        double angle = theta * Math.PI / 180; // 度转弧度
        double[] xCoords = getX(width / 2, height / 2, angle);
        double[] yCoords = getY(width / 2, height / 2, angle);
        int WIDTH = (int) (xCoords[3] - xCoords[0]);
        int HEIGHT = (int) (yCoords[3] - yCoords[0]);
        BufferedImage resultImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                int x = i - WIDTH / 2;
                int y = HEIGHT / 2 - j;
                double radius = Math.sqrt(x * x + y * y);
                double angle1;
                if (y > 0) {
                    angle1 = Math.acos(x / radius);
                } else {
                    angle1 = 2 * Math.PI - Math.acos(x / radius);
                }
                x = (int) (radius * Math.cos(angle1 - angle));
                y = (int) (radius * Math.sin(angle1 - angle));
                if (x < (width / 2) & x > -(width / 2) & y < (height / 2) & y > -(height / 2)) {
                    int rgb = image.getRGB(x + width / 2, height / 2 - y);
                    resultImage.setRGB(i, j, rgb);
                }else {
                    int rgb = ((0 & 0xff) << 24) | ((backgroundColor.getRed() & 0xff) << 16) | ((backgroundColor.getGreen() & 0xff) << 8)
                            | ((backgroundColor.getBlue() & 0xff));
                    resultImage.setRGB(i, j, rgb);
                }
            }
        }
        return resultImage;
    }

    // Gets the Y-direction coordinates of the four corners after rotation
    private double[] getY(int i, int j, double angle) {
        double results[] = new double[4];
        double radius = Math.sqrt(i * i + j * j);
        double angle1 = Math.asin(j / radius);
        results[0] = radius * Math.sin(angle1 + angle);
        results[1] = radius * Math.sin(Math.PI - angle1 + angle);
        results[2] = -results[0];
        results[3] = -results[1];
        Arrays.sort(results);
        return results;
    }

    // Gets the x-direction coordinates of the four corners after rotation
    private double[] getX(int i, int j, double angle) {
        double results[] = new double[4];
        double radius = Math.sqrt(i * i + j * j);
        double angle1 = Math.acos(i / radius);
        results[0] = radius * Math.cos(angle1 + angle);
        results[1] = radius * Math.cos(Math.PI - angle1 + angle);
        results[2] = -results[0];
        results[3] = -results[1];
        Arrays.sort(results);
        return results;
    }

    public static void main(String[] argus)throws Exception {
        String[] img = {"img/weapons/weapon00.png", "img/weapons/weapon10.png", "img/weapons/weapon20.png"};
//
//        String[] img1={"img/weapons/2weapon1.png","img/weapons/2weapon2.png","img/weapons/weapon3.png","img/weapons/2weapon4.png","img/weapons/2weapon5.png","img/weapons/2weapon6.png"};
        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j <=7; j++) {
            File input = new File(img[i]);
            File output = new File("img/weapons/weapon"+String.valueOf(i)+String.valueOf(8)+".png");

            BufferedImage image = ImageIO.read(input);
            Color bgColor = new Color(255, 255, 255);
            BufferedImage result = new ImageRotate().rotateImage(image, 8*22.5, bgColor);
            ImageIO.write(result, "png", output);
//            }
        }
    }

}
