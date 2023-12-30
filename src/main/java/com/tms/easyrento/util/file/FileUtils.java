package com.tms.easyrento.util.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/23/23 6:01 AM
 */

public class FileUtils {
    private static final String ROOT_DIR = System.getProperty("user.home");
    public FileUtils() {
        throw new RuntimeException("Utility Method! Cannot Instantiate");
    }

    public static FileResponse saveFile(MultipartFile file, String fileLocation) throws IOException {
        Path filePath = Path.of(ROOT_DIR + File.separator + fileLocation);
        if(!Files.exists(filePath))
            Files.createDirectory(filePath);

        String fileName = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if(originalFileName != null)
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        String fileDestination = filePath + File.separator + fileName + "." + extension;

        Files.copy(file.getInputStream(), Path.of(fileDestination));

        return FileResponse.builder()
                .fileName(fileName + "." + extension)
                .filePath(fileDestination)
                .fileSize((short) (file.getSize()/1000))
                .originalFileName(file.getOriginalFilename())
                .build();
    }


    @Getter
    @Setter
    @Builder
    public static class FileResponse {
        private String fileName;
        private String originalFileName;
        private Short fileSize;
        private String filePath;
    }
}
