//Сообщение, которое попросит пользователя ввести число человек
private const val ENTER_COUNT_MSG = "Введите число человек: "

//Минимальное число человек, которое может быть сгенерировано
private const val COUNT_MIN = 1

//Максимальное число человек, которое может быть сгенерировано
private const val COUNT_MAX = 30

//Сообщение об ошибке, которое будет выведено, если пользователь ввел неверный ввод
private const val INVALID_INPUT_MSG = "Неверный ввод"

//Сообщение об ошибке, которое будет выведено, если пользователь ввел число, выходящее за диапазон от COUNT_MIN до COUNT_MAX
private const val COUNT_RANGE_MSG = "Число человек должно быть в промежутке между $COUNT_MIN и $COUNT_MAX"

fun main() {
    generatePdfFromUserInput()
}

// Extension функция, которая выводит сообщение в консоль в кодировке UTF-8
fun String.printMessageInUTF8(): String {
    val byteArray = this.toByteArray(Charsets.UTF_8)
    return String(byteArray, Charsets.UTF_8)
}

// Метод генерирует Pdf-файл на основе введенных пользователем данных
private fun generatePdfFromUserInput() {
    try {
        println(ENTER_COUNT_MSG.printMessageInUTF8())
        readlnOrNull()?.toIntOrNull()?.let { count ->
            if (count in COUNT_MIN..COUNT_MAX) {
                PdfGenerator(count).generatePdf()
            } else {
                println(COUNT_RANGE_MSG.printMessageInUTF8())
            }
        } ?: println(INVALID_INPUT_MSG.printMessageInUTF8())
    } catch (e: Exception) {
        println("Ошибка: ${e.message}".printMessageInUTF8())
    }
}