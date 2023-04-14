# About the Project
This repository was created as part of the "QA Mobile" course at Tinkoff Fintech, Spring 2023.

## Project Description
### PdfDataGenerator
PdfDataGenerator is a console application written in Kotlin that generates a PDF file with fake personal data using a YAML configuration file. The application uses the iText library to generate the PDF files and the Java Faker and Data Faker libraries to generate the fake data.

## Data to generate:

Personal data - name, last name, patronymic, age, gender (M or F), date of birth, place of birth (city);
Place of residence - six-digit postal code, country, region, city, street, house, apartment.

## Requirements:

1. The file should have one sheet with a table containing the generated data for n people, where n is an integer specified by the user, 1 <= n <= 30;
2. All text data should be in Russian;
3. All names, surnames and other values should be adequate, random character sets are not allowed;
4. The date in the file should be written in the format "DD-MM-YYYY";
5. Names, surnames and patronymics should match the gender, for example, female names with male patronymics are not allowed, nor are male names with female gender;
6. The date of birth and age should also correspond to each other;
7. After the file is created, the log should output the message "Файл создан. Путь: here we output the full path to the file".

## Example Output File:

| Имя    | Фамилия   | Отчество     | Возраст | Пол  | Дата рождения | Место рождения | Индекс | Страна | Область        | Город          | Улица               | Дом  | Квартира |
| ------ | ---------| ------------ | ------- | ---- | ------------- | -------------- | ------ | ------ | -------------- | --------------| ------------------ | ---- |----------|
| Тарас  | Горев    | Тимофеевич   | 52      | МУЖ  | 02-05-1970    | Азнакаево      | 409782 | Россия | Орловская      | Гороховец      | Альняш             | 187  | 128      |
| Диана  | Миронова | Филипповна   | 64      | ЖЕН  | 17-02-1959    | Стародуб       | 614352 | Россия | Магаданская    | Белый          | У.Г.Нохсорова      | 227  | 21       |

## Project Team
- Oleg Komissarov - QA Engineer

## Environment:

- Package: org.example
- JDK 17 Oracle OpenJDK version 17.0.6
- Apache Maven 3.9.1

## Kotlin:

- Kotlin version 1.8.20-release-327 (JRE 17.0.6+9-LTS-190)
- Language level 17 – Sealed types, always-strict floating-point semantics
- Kotlin Target platform JVM 11
- API version 1.8
- Language version 1.8

## Source Folders:

- `src/main/kotlin`

## Resource Folders:

- `src/main/resources`

## File Paths:

- Path to YAML file for parsing containing lists of male and female first and last names and patronymics: `./src/main/resources/personal-names.yml`
- Path to output PDF file containing randomly generated personal data: `./src/main/resources/sample.pdf`
- Path to TrueType font file used when generating the PDF file: `./src/main/resources/arial.ttf`
- Path to the main executable file for running the console application: `./src/main/kotlin/Main.kt`
- Path to the file for generating data: `./src/main/kotlin/Data.kt`
- Path to the file for interface `MyDocument`: `./src/main/kotlin/MyDocument.kt`
- Path to the file for generating PDF document: `./src/main/kotlin/PdfDocument.kt`
- Path to the file for generating PDF file with generated data: `./src/main/kotlin/PdfGenerator.kt`
- Path to the file for abstract class `Person`: `./src/main/kotlin/Person.kt`

## Dependencies

The following dependencies are required to build and run the application:

- **IText Core 5.5**
  - Maven:
    ```
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>
    ```
  - This library is used to generate the PDF files.

- **Java Faker 1.0.2**
  - Maven:
    ```
    <dependency>
        <groupId>com.github.javafaker</groupId>
        <artifactId>javafaker</artifactId>
        <version>1.0.2</version>
    </dependency>
    ```
  - This library is used to generate fake personal data.

- **Data Faker 1.8.1**
  - Maven:
    ```
    <dependency>
        <groupId>net.datafaker</groupId>
        <artifactId>datafaker</artifactId>
        <version>1.8.1</version>
    </dependency>
    ```
  - This library is used to generate additional fake data.

All dependencies are listed in the pom.xml file located in the project's root directory.

## Installation

To build the project with Maven and run the application, follow these steps:

1. Make sure you have JDK 17 Oracle OpenJDK version 17.0.6 and Apache Maven 3.9.1 installed on your computer.

2. Clone the repository to your computer:

```bash
git clone https://github.com/OlegKomissarovV/PdfDataGenerator.git
```

3. Navigate to the project folder:

```bash
cd PdfDataGenerator
```

4. Run the command 
```bash
mvn package
``` 
in the terminal to build the project. Maven will download all the dependencies specified in the pom.xml file and build the project.

5. Run the application using the command: 
```bash
mvn exec:java
```
Maven will execute the `Main.kt` class and output the result to the console.

## Usage

To use the application, run the command 
```bash
mvn exec:java
```
in the terminal, followed by the path to the YAML file, path to the output PDF file, and path to the font TTF file, separated by spaces. For example:

```bash
mvn exec:java -Dexec.args="./src/main/resources/personal-names.yml \
./src/main/resources/sample.pdf \
./src/main/resources/arial.ttf"
```

The application will prompt you to enter the number of people for which the data should be generated. Once you enter the value, the application will generate the data and create a PDF file with the specified number of people's data. The file will be saved to the specified output file path, and the full path to the file will be output to the console.

