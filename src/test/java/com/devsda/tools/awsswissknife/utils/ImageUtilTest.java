package com.devsda.tools.awsswissknife.utils;

import org.junit.Test;

import javax.imageio.ImageIO;

public class ImageUtilTest {

    @Test
    public void convertToByteBufferTest() throws Exception {

        System.out.println(ImageUtil.convertToByteBuffer("/Users/hijhamb/Downloads/mdl_party.jpg"));

    }
}
