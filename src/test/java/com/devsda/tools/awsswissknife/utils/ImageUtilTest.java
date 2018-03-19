package com.devsda.tools.awsswissknife.utils;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

public class ImageUtilTest {

    @Test
    public void convertToByteBufferTest() throws Exception {

        System.out.println(ImageUtil.convertToByteBuffer("/Users/hijhamb/Downloads/mdl_party.jpg"));

    }

    @Test
    public void convertToIamgeTest() throws Exception {

        ByteBuffer byteBuffer = ImageUtil.convertToByteBuffer("/Users/hijhamb/Downloads/mdl_party.jpg");

        ImageUtil.covertToImage(byteBuffer, "/Users/hijhamb/Desktop/test.jpg");

    }
}
