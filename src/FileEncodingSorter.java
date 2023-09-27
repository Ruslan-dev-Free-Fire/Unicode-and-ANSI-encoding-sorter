import org.apache.tika.detect.*;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.io.FilenameFilter;

public class FileEncodingSorter {
    private static final Logger logger = LoggerFactory.getLogger(FileEncodingSorter.class);
    private static final String folderPath = "C:\\Unicode and ANSI encoding sorter\\Sorter folder";
    private static final Map<String, String> encodingToFolderMap = new HashMap<>();
    private static final String txtFileExtension = ".txt";

    static {
        // Заполните маппинг кодировок и соответствующих папок здесь
        encodingToFolderMap.put("ISO-8859-1", "ANSI");
        encodingToFolderMap.put("ANSI", "ANSI");
        encodingToFolderMap.put("Windows-1251", "ANSI");
        encodingToFolderMap.put("Shift_JIS", "ANSI");
        encodingToFolderMap.put("windows-1252", "UTF8");
        encodingToFolderMap.put("UTF-16 LE", "UTF8");
        encodingToFolderMap.put("UTF-16 BE", "UTF8");
        encodingToFolderMap.put("UTF-8", "UTF8");
        // Добавьте другие кодировки и соответствующие папки по мере необходимости
    }

    public static void main(String[] args) {
        // Сначала получим список .txt файлов в папке
        File folder = new File(folderPath);
        File[] txtFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(txtFileExtension);
            }
        });

        if (txtFiles != null) {
            int numberOfTxtFiles = txtFiles.length;
            logger.info("Number of .txt files in the folder: {}", numberOfTxtFiles);

            // Затем можно приступить к созданию папок и сортировке файлов
            createSortingFolders();
            copyFilesByEncoding();
        } else {
            logger.info("No .txt files found in the folder.");
        }
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

    private static void copyFilesByEncoding() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(txtFileExtension)) {
                    try {
                        logger.info("Processing file: {}", file.getAbsolutePath());

                        FileInputStream inputStream = new FileInputStream(file);
                        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream);
                        Metadata metadata = new Metadata();
                        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, file.getName());

                        EncodingDetector detector = new DefaultEncodingDetector();
                        Charset charset = detector.detect(tikaInputStream, metadata);

                        String encoding;
                        if (charset == null) {
                            // Handle files with unknown encoding
                            logger.error("Unable to detect encoding for file: {}", file.getAbsolutePath());
                            continue;
                        } else {
                            encoding = charset.name();
                            // Add this line to log the detected encoding
                            logger.info("Detected encoding for file '{}': {}", file.getAbsolutePath(), encoding);
                        }

                        // Если файл обнаружен как ANSI, но не содержит не-ASCII символов,
                        // предположим, что это на самом деле UTF-8.
                        if ("ANSI".equals(encoding) && isAscii(file)) {
                            encoding = "UTF-8";
                        }

                        String targetFolderName = encodingToFolderMap.get(encoding);

                        if (targetFolderName != null) {
                            File targetFolder = new File(folder, targetFolderName);
                            if (!targetFolder.exists()) {
                                if (!targetFolder.mkdirs()) {
                                    logger.error("Failed to create target folder: {}", targetFolder.getAbsolutePath());
                                    continue;
                                }
                            }
                            File targetFile = new File(targetFolder, file.getName());
                            Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            logger.info("File '{}' copied to '{}'.", file.getAbsolutePath(), targetFile.getAbsolutePath());
                        }
                    } catch (IOException e) {
                        logger.error("An error occurred while processing a file:", e);
                    }
                }
            }
        }
    }

    private static boolean isAscii(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        for (byte b : bytes) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }
}