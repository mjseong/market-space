package com.juneox.marketspace.test.utils;

import com.juneox.marketspace.utils.HashUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class HashUtilsTests {

    @Test
    void getHashFileTest() throws NoSuchAlgorithmException, IOException {
        String expectedHash = "72ebb9d51173a40e746bd588a5151f80071d8cdd85c8e4a4ad432dc068c8b3b9";
        String actualHashed = HashUtils.getHashS256WithFile(Path.of("./sample/sample2021limit60.csv"));
        Assertions.assertEquals(expectedHash, actualHashed);
    }
}
