import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.io.IOException;

public class SimpleImageEncryption {

    public static void encryptImage(String inputImagePath, String outputImagePath, int key) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputImagePath));
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

               
                red = red ^ key;
                green = green ^ key;
                blue = blue ^ key;

                int encryptedPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, encryptedPixel);
            }
        }
        ImageIO.write(image, "png", new File(outputImagePath));
    }

    public static void decryptImage(String inputImagePath, String outputImagePath, int key) throws IOException {
        // Decryption is the same process as encryption due to the XOR operation
        encryptImage(inputImagePath, outputImagePath, key);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose (E)ncrypt or (D)ecrypt: ");
        String choice = scanner.nextLine().toUpperCase();

        System.out.print("Enter input image path: ");
        String inputImagePath = scanner.nextLine();

        System.out.print("Enter output image path: ");
        String outputImagePath = scanner.nextLine();

        System.out.print("Enter encryption/decryption key (integer): ");
        int key = scanner.nextInt();

        try {
            if (choice.equals("E")) {
                encryptImage(inputImagePath, outputImagePath, key);
                System.out.println("Image encrypted and saved to " + outputImagePath);
            } else if (choice.equals("D")) {
                decryptImage(inputImagePath, outputImagePath, key);
                System.out.println("Image decrypted and saved to " + outputImagePath);
            } else {
                System.out.println("Invalid choice! Please choose 'E' for encryption or 'D' for decryption.");
            }
        } catch (IOException e) {
            System.out.println("Error processing the image: " + e.getMessage());
        }

        scanner.close();
    }
}
