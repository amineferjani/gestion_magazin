package tn.fmass.mg.Utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageManager {
    public static final long MAX_FILE_SIZE_BYTES = 1024* 1024;
    public static String saveImage(MultipartFile image,String chemin) {
        String imageUrl = null;

        try {
            File directory = new File("target/classes/static/images/"+chemin);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = image.getOriginalFilename();
            int lastDotIndex = fileName.lastIndexOf('.');
            Date date=new Date();
            Path filePath = Path.of("target/classes/static/images/"+ chemin+ File.separator +  date.getTime()+"."+fileName.substring(lastDotIndex + 1));

            if (image.getSize() > MAX_FILE_SIZE_BYTES) {
                BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
                double scalingFactor = (double) MAX_FILE_SIZE_BYTES / image.getSize();

                Thumbnails.of(bufferedImage)
                        .scale(scalingFactor)
                        .outputQuality(0.2)
                        .toFile(filePath.toFile());
            }
            else{
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            }
            imageUrl=filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }
    public static List<String> saveImages(List<MultipartFile> images, Long id,String chemin) {
        List<String> imageUrls = new ArrayList<>();
        int num=0;
        for (MultipartFile image : images) {
            try {
                File directory = new File("target/classes/static/images/"+chemin);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = image.getOriginalFilename();
                int lastDotIndex = fileName.lastIndexOf('.');
                num++;
                Date date=new Date();
                Path filePath = Path.of("target/classes/static/images/"+chemin + File.separator + id +num+ date.getTime()+"."+fileName.substring(lastDotIndex + 1));

                if (image.getSize() > MAX_FILE_SIZE_BYTES) {
                    BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
                    double scalingFactor = (double) MAX_FILE_SIZE_BYTES / image.getSize();

                    Thumbnails.of(bufferedImage)
                            .scale(scalingFactor)
                            .outputQuality(0.2)
                            .toFile(filePath.toFile());
                }
                else{
                    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                }
                imageUrls.add(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageUrls;
    }
}
