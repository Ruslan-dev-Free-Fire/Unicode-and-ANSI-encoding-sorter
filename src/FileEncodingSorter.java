import org.apache.tika.detect.*;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileEncodingSorter {
    private static final Logger logger = LoggerFactory.getLogger(FileEncodingSorter.class);
    private static final String folderPath = "C:\\Unicode and ANSI encoding sorter\\Sorter folder";
    private static final Map<String, String> encodingToFolderMap = new HashMap<>();

    static {
        // Заполните маппинг кодировок и соответствующих папок здесь
        encodingToFolderMap.put("UTF-8", "UTF8_Files");
        encodingToFolderMap.put("ISO-8859-1", "ISO8859_1_Files");
        encodingToFolderMap.put("ANSI", "ANSI_Files");
        encodingToFolderMap.put("UTF-16 LE", "UTF-16 LE_Files");
        encodingToFolderMap.put("UTF-16 BE", "UTF-16 BE_Files");
        // Добавьте другие кодировки и соответствующие папки по мере необходимости
    }

    public static void main(String[] args) {
        createSortingFolders();
        sortFilesByEncoding();
    }

    private static void createSortingFolders() {
        for (String folderName : encodingToFolderMap.values()) {
            File folder = new File(folderPath, folderName);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    logger.info("Folder '{}' created successfully.", folder.getAbsolutePath());
                } else {
                    logger.error("Failed to create folder '{}'.", folder.getAbsolutePath());
                }
            }
        }
    }

    private static void sortFilesByEncoding() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        // Используйте Apache Tika для определения кодировки файла
                        FileInputStream inputStream = new FileInputStream(file);
                        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream);
                        Metadata metadata = new Metadata();
                        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, file.getName());

                        EncodingDetector detector = new DefaultEncodingDetector();
                        Charset charset = detector.detect(tikaInputStream, metadata);

                        // Определите папку на основе кодировки
                        String encoding = charset.name();
                        String targetFolderName = encodingToFolderMap.get(encoding);

                        // Переместите файл в соответствующую папку
                        if (targetFolderName != null) {
                            File targetFolder = new File(folder, targetFolderName);
                            if (!targetFolder.exists()) {
                                if (!targetFolder.mkdirs()) {
                                    logger.error("Failed to create target folder: {}", targetFolder.getAbsolutePath());
                                    continue; // Пропустить этот файл и перейти к следующему
                                }
                            }
                            File targetFile = new File(targetFolder, file.getName());
                            if (!file.renameTo(targetFile)) {
                                logger.error("Failed to move file from '{}' to '{}'", file.getAbsolutePath(), targetFile.getAbsolutePath());
                            }
                        }
                    } catch (IOException e) {
                        logger.error("An error occurred while processing a file:", e);
                    }
                }
            }
        }
    }
}