package com.devsda.tools.awsswissknife.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImageUtil {

    public static ByteBuffer convertToByteBuffer(String imageLocation) throws IOException{

        BufferedImage originalImage = ImageIO.read(new File(imageLocation));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        ByteBuffer buf = ByteBuffer.wrap(imageInByte);
        return buf;

    }
}
