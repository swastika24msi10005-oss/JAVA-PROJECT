package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RecursiveFileUtils {

    public static long calculateDirectorySize(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            return Files.size(path);
        }

        long total = 0;

        try (Stream<Path> paths = Files.list(path)) {
            for (Path p : paths.collect(java.util.stream.Collectors.toList())) {
                if (Files.isDirectory(p)) {
                    total += calculateDirectorySize(p); // recursive call
                } else {
                    total += Files.size(p);
                }
            }
        }

        return total;
    }
}
