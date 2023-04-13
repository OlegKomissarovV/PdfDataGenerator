import org.yaml.snakeyaml.Yaml
import java.io.File

//Создание экземпляра класса File для парсинга файла yml, содержащего списки мужских и женских ФИО
val file = File("./src/main/resources/personal-names.yml")

//Класс предназначен для генерации Pdf-файлов с данными о людях
class PdfGenerator(private var count: Int) {
    // Метод, который генерирует Pdf-файл с данными о людях
    fun generatePdf() {
        val yamlParser = Yaml()
        val dataGeneratorsList = mutableListOf<DataGenerator>()
        val inputStream = file.inputStream()
        val nameData = yamlParser.loadAs(inputStream, NameData::class.java)
        repeat(count) {
            val dataGenerator = DataGenerator(nameData)
            dataGenerator.generate()
            dataGeneratorsList.add(dataGenerator)
        }
        val pdfDocument: MyDocument = PdfDocument(dataGeneratorsList)
        pdfDocument.create()
    }
}
