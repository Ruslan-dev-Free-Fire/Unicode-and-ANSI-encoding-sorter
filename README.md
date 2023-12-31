# File Encoding Sorter (Java)

**File Encoding Sorter** is a Java application that helps you organize and sort text files based on their character encodings. It can automatically detect the encoding of each text file and move them to the appropriate folders according to the specified encoding-to-folder mapping.

For the Python version of this project, please visit the [Python branch](https://github.com/Ruslan-dev-Free-Fire/Unicode-and-ANSI-encoding-sorter/tree/python_version).

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
- [Usage](#usage)
  - [Configuration](#configuration)
- [Possible Issues](#possible-issues)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed on your system.

### Configuration

Before running the application, you need to configure the encoding-to-folder mapping. Open the `FileEncodingSorter.java` file and modify the `encodingToFolderMap` in the `static` block to specify the desired mappings for character encodings and target folders.

```java
static {
    // Add encoding-to-folder mappings here
    encodingToFolderMap.put("ISO-8859-1", "ISO-8859-1");
    encodingToFolderMap.put("ANSI", "ANSI");
    encodingToFolderMap.put("Windows-1251", "ANSI");
    encodingToFolderMap.put("Shift_JIS", "ANSI");
    encodingToFolderMap.put("windows-1252", "Windows-1252");
    encodingToFolderMap.put("UTF-16 LE", "UTF8");
    encodingToFolderMap.put("UTF-16 BE", "UTF8");
    encodingToFolderMap.put("UTF-8", "UTF8");
    // Add more mappings as needed
}
```

## Usage

1. Compile the Java code if you haven't already:

   ```bash
   javac FileEncodingSorter.java
   ```

2. Run the application:

   ```bash
   java FileEncodingSorter
   ```

   The application will scan the specified folder for `.txt` files, detect their encodings, and sort them into the corresponding folders based on your configuration.

## Possible Issues

Please note that the accuracy of encoding detection may not always be perfect, especially for certain ANSI and UTF8 `.txt` files. Apache Tika's detection algorithms may incorrectly label some files as `ISO-8859-1` or `Windows-1252` when they are actually in `ANSI` or `UTF-8` encoding.

If you encounter files that are incorrectly labeled, you can manually move them to the correct folders after running the application.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push your changes to your fork.
5. Submit a pull request to the original repository.

For major changes, please open an issue first to discuss your ideas or changes.

## License

This project is licensed under the [MIT License](LICENSE).
