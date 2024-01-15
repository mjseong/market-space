package com.juneox.marketspace.utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    public static List<Path> getFilePaths(String directoryPath) throws IOException {

        Path path = Paths.get(directoryPath);

        if(!Files.isDirectory(path)){
            throw new NotDirectoryException(path.toString());
        }

        List<Path> filePaths = Files.walk(path)
                .filter(f-> Files.isRegularFile(f))
                .collect(Collectors.toList());

        if(filePaths.isEmpty()){
            throw new DirectoryNotEmptyException(path.toString());
        }

        return filePaths;
    }
}
