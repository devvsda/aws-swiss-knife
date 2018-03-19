package com.devsda.tools.awsswissknife.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
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

    public static void covertToImage(ByteBuffer byteBuffer, String destinationLocation) throws Exception {

        byte[] bytes = byteBuffer.array(); // Your image bytes

        OutputStream stream = new FileOutputStream(new File(destinationLocation)); // Your output

        BufferedImage image = createRGBImage(bytes, 400, 400);

        try {
            ImageIO.write(image, "BMP", stream);
        }
        finally {
            stream.close();
        }

    }

    private static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
        DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
        ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width,
                height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
    }
}
