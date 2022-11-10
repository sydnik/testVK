package utils;

import aquality.selenium.core.logging.Logger;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ImageUtil {
    public static boolean equalsImage(String file, String url){
        try {
            BufferedImage firstImage = ImageIO.read(new URL(StringUtil.getFirstUrl(url)));
            BufferedImage secondImage = ImageIO.read(new File(file));
            return !new ImageDiffer().makeDiff(firstImage, secondImage).hasDiff();
        }catch (Exception e){
            Logger.getInstance().error("couldn't compare image\n" + e.getMessage());
        }
        return false;
    }
}
