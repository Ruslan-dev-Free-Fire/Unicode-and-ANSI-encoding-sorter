# File Encoding Sorter (Python)

**File Encoding Sorter (Python)** is a Python application that helps you organize and sort text files based on their character encodings. It can automatically detect the encoding of each text file and move them to the appropriate folders according to the specified encoding-to-folder mapping.

For the original Java version, please visit [here](https://github.com/Ruslan-dev-Free-Fire/Unicode-and-ANSI-encoding-sorter).

File Encoding Sorter WebApp- , please visit [here](https://github.com/Ruslan-dev-Free-Fire/File-Encoding-Sorter-WebApp-).

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

- Python installed on your system.
- The `chardet` library installed. You can install it using `pip install chardet`.

### Configuration

Before running the application, you need to configure the encoding-to-folder mapping. Open the `main.py` file and modify the `encoding_to_folder_map` dictionary to specify the desired mappings for character encodings and target folders.

```python
# Mapping encodings to folders
# noinspection PyDictDuplicateKeys
encoding_to_folder_map = {
     "ISO-8859-1": "ISO-8859-1",
     "ascii": "ANSI",
     "Windows-1251": "ANSI",
     "Shift_JIS": "Shift_JIS",
     "SHIFT_JIS": "Shift_JIS",
     "Windows-1252": "Windows-1252",
     "utf-8": "UTF-8",
     "UTF-16": "UTF-16",
     "utf-8": "UTF8",
     None: "Unknown" # Added for files with unknown encoding
}
```

## Usage

1. Run the Python script:

   ```bash
   python main.py
   ```

   The application will scan the specified folder for `.txt` files, detect their encodings, and sort them into the corresponding folders based on your configuration.

## Possible Issues

Please note that encoding detection accuracy may not always be perfect, especially for certain ANSI and UTF8 encoded .txt files. The encoding detection library may incorrectly mark some files as ISO-8859-1 or Windows-1252 when in fact they are ANSI or UTF-8 encoded.

If you encounter files that are incorrectly labeled, you can manually move them to the appropriate folders after running the application. In addition, if the file text contains the characters “„q”, “„y”, “„x”, “„~”, “„u”, “„ѓ”, “„Ѓ”, “„p”, “ „‚", "„Ђ", "„|", "„Ћ", "„S", then the file will be marked as Shift_JIS, regardless of the result obtained from the encoding detection library.

## Contributing

Contributions are welcome! If you'd like to contribute to this Python version of the project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push your changes to your fork.
5. Submit a pull request to the original repository.

For major changes, please open an issue first to discuss your ideas or changes.

## License

This project is licensed under the [MIT License](LICENSE).
