package com.aotain.mytest.usefultool.file;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/18
 */
public class IOUtilsTest {

    @Test
    public void test() throws Exception {
        InputStream in = new URL( "http://commons.apache.org" ).openStream();
        try {
            System.out.println( IOUtils.toString( in ) );
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
