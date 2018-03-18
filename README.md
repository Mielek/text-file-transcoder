# Text file Transcoder

Text file Transcoder is a simple console application to transcode text files.

## Installation

Installation require:
- Java 8+
- JAVA_PATH set to jdk location
- Maven 3+
- PATH need to contain maven location

Installation steps:
```
git clone https://github.com/Mielek/text-file-transcoder.git
cd text-file-transcoder
mvn clean build
```

To create runnable jar file:
```
mvn package
```

## Usage

You can run application from jar file or directly from maven. This examples are based on maven execution.

##### Help
```
mvn exec:java -Dexec.args="--help"
```

##### Charset list
```
mvn exec:java -Dexec.args="--list-charset
```

##### Invocation example
```
mvn exec:java -Dexec.args="-f test-file.txt -d Cp1250 -r result-file.txt -e UTF-8"
```

## Credits

Created by Rafal Mielowski.
