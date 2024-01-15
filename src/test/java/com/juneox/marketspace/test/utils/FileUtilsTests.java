package com.juneox.marketspace.test.utils;

import com.juneox.marketspace.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FileUtilsTests {

    @Test
    void getFilesTest() throws IOException {
        List<Path> paths =  FileUtils.getFilePaths("./sample");
        Assertions.assertEquals(1, paths.size());
    }
}
