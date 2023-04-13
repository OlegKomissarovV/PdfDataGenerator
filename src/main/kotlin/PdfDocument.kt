import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

//Cодержит путь к файлу pdf, который будет создан.
private const val NAME_FILE = "./src/main/resources/sample.pdf"

//Cообщение о том, что файл был успешно создан и содержит путь к файлу.
private val CREATED_FILE_MSG: String = "Файл создан. Путь: ${File(NAME_FILE).relativeTo(File(".")).path}"

//Cодержит путь к файлу шрифта, используемого для создания pdf
private const val FONT_FILENAME = "./src/main/resources/arial.ttf"

//Размер шрифта, используемый в таблице
private const val FONT_SIZE = 6f

// Ширина таблицы в процентах от ширины страницы
private const val TABLE_WIDTH_PERCENTAGE = 100f

class PdfDocument(private val data: MutableList<DataGenerator>) : MyDocument {
    override fun create() {
        val pdfOutputFile = FileOutputStream(NAME_FILE)
        val myPdfDoc = Document(PageSize.A4.rotate())
        // Заголовок таблицы, полученный из DataGenerator
        val tableHeader = data[0].getKeys()
        // Создается таблица с заданным количеством столбцов
        val table = PdfPTable(tableHeader.size)
        // Устанавливается ширина таблицы в процентах от ширины страницы
        table.widthPercentage = TABLE_WIDTH_PERCENTAGE
        // Шрифт для отображения текста в таблице. Параметры шрифта включают название файла шрифта (FONT_FILENAME),
        // размер шрифта (FONT_SIZE), стиль шрифта (Font.NORMAL) и цвет шрифта (BaseColor.BLACK)
        // Файл шрифта используется в кодировке BaseFont.IDENTITY_H,
        // что означает, что он поддерживает символы национальных языков
        val font =
            Font(BaseFont.createFont(FONT_FILENAME, BaseFont.IDENTITY_H, true), FONT_SIZE, Font.NORMAL, BaseColor.BLACK)
        // Словарь, который связывает каждый заголовок таблицы со списком значений из каждого DataGenerator
        val lists = tableHeader.associateWith { mutableListOf<String?>() }
        data.forEach { user ->
            tableHeader.forEach { key ->
                lists[key]?.add(user.getValueByKey(key))
            }
        }
        // Создаются ячейки таблицы и добавляем их в таблицу
        val cellsHeader = mutableListOf<PdfPCell>()
        // Метод, который добавляет ячейки в таблицу
        addedCell(tableHeader, font, cellsHeader, Element.ALIGN_CENTER)
        for (i in 0 until data.size) {
            for (key in tableHeader) {
                addedCell(listOf(lists[key]?.get(i)), font, cellsHeader, Element.ALIGN_LEFT)
            }
        }
        addCellsToTable(cellsHeader, table)
        val pdfWriter = PdfWriter.getInstance(myPdfDoc, pdfOutputFile)
        myPdfDoc.apply {
            open()
            myPdfDoc.add(table)
            close()
            pdfWriter.close()
        }
        println(CREATED_FILE_MSG.printMessageInUTF8())
    }

    //  Метод добавляет ячейки в таблицу
    private fun addedCell(
        listData: List<String?>,
        font: Font,
        cells: MutableList<PdfPCell>,
        alignment: Int,
    ) {
        listData.forEach { header ->
            val cell = PdfPCell(Phrase(header, font))
            setCellAlignmentTable(cell, alignment)
            // Добавляем ячейки в таблицу
            cells.add(cell)
        }
    }

    //  Метод, который устанавливает горизонтальное выравнивание ячейки таблицы
    private fun setCellAlignmentTable(cell: PdfPCell, alignment: Int) {
        cell.horizontalAlignment = alignment
    }

    //  Метод, который добавляет ячейки в таблицу
    private fun addCellsToTable(
        cellsHeader: MutableList<PdfPCell>,
        table: PdfPTable,
    ) {
        for (cell in cellsHeader) {
            // Добавление ячеек в таблицу
            table.addCell(cell)
        }
    }
}

