import net.datafaker.Faker
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

//Значение по умолчанию для поля "Имя"
private const val DEFAULT_FIRST_NAME = "Имя"

//Значение по умолчанию для поля "Фамилия"
private const val DEFAULT_LAST_NAME = "Фамилия"

//Значение по умолчанию для поля "Отчество"
private const val DEFAULT_MIDDLE_NAME = "Отчество"

//Значение по умолчанию для поля "Пол"
private const val DEFAULT_GENDER = "Пол"

//Значение по умолчанию для поля "Возраст"
private const val DEFAULT_AGE = "Возраст"

//Значение по умолчанию для поля "Дата рождения"
private const val DEFAULT_BIRTH_DATE = "Дата рождения"

//Значение по умолчанию для поля "Место рождения"
private const val DEFAULT_BIRTH_PLACE = "Место рождения"

//Значение по умолчанию для поля "Индекс"
private const val DEFAULT_ZIP_CODE = "Индекс"

//Значение по умолчанию для поля "Страна"
private const val DEFAULT_COUNTRY = "Страна"

//Значение по умолчанию для поля "Область"
private const val DEFAULT_STATE = "Область"

//Значение по умолчанию для поля "Город"
private const val DEFAULT_CITY = "Город"

//Значение по умолчанию для поля "Улица"
private const val DEFAULT_STREET_NAME = "Улица"

//Значение по умолчанию для поля "Дом"
private const val DEFAULT_BUILDING_NUMBER = "Дом"

//Значение по умолчанию для поля "Квартира"
private const val DEFAULT_SECONDARY_ADDRESS = "Квартира"

//Минимальный возраст для генерации данных возраста
private const val MIN_AGE = 18

//Максимальный возраст для генерации данных
private const val MAX_AGE = 99

//Шаблон даты в формате "день-месяц-год"
private const val PATTERN_DATE = "dd-MM-yyyy"

//Язык, используемый для генерации случайных адресов
private const val DEFAULT_LOCALE = "ru-RU"

//Регулярное выражение для удаления ведущих нулей из числового значения
private const val ZEROES_REGEX_PATTERN = "^0{1,2}"

//Регулярное выражение для извлечения почтового индекса и страны из адреса
private const val ZIP_CODE_REGEX_PATTERN = """^(\d+)\s+([^\d,]+)"""

// Список суффиксов для удаления из названий улиц и квартир
private val suffixes = arrayOf("ул\\.", "улица", "проспект", "пр\\.", "площадь", "пл\\.", "кв\\.")

//Регулярное выражение для удаления суффиксов из названия улицы
private val SUFFIXES_REGEX_PATTERN = "(${suffixes.joinToString("|")})[ .]*"

//Класс генерации случайных адресов
open class MyFakerAddressProvider : Faker(Locale(DEFAULT_LOCALE)) {
    //Объект класса Address, предоставляющий методы генерации адресов
    private val address: net.datafaker.providers.base.Address = this.address()

    //Место рождения человека, заданное по умолчанию
    var birthPlace: String = DEFAULT_BIRTH_PLACE

    //Почтовый индекс адреса, заданный по умолчанию
    var zipCode: String = DEFAULT_ZIP_CODE

    //Страна, заданная по умолчанию
    var personCountry: String = DEFAULT_COUNTRY

    //Штат/область, заданные по умолчанию
    var state: String = DEFAULT_STATE

    //Город, заданный по умолчанию
    var city: String = DEFAULT_CITY

    //Название улицы, заданное по умолчанию
    var streetName: String = DEFAULT_STREET_NAME

    //Номер здания, заданный по умолчанию
    var buildingNumber: String = DEFAULT_BUILDING_NUMBER

    //Дополнительный адрес (квартира, офис и т.д.), заданный по умолчанию
    var secondaryAddress: String = DEFAULT_SECONDARY_ADDRESS

    //Метод для генерации случайных адресов
    fun generateFakeAddress() {
        birthPlace = address.city()
        val zipCodeAndCountry = extractZipCodeAndCountry(address().fullAddress())
        zipCode = zipCodeAndCountry.first.toString()
        personCountry = zipCodeAndCountry.second.toString()
        state = address.state()
        city = address.city()
        streetName = removeSuffixesFromAddress(address.streetName())
        buildingNumber = removeLeadingZeroesFromAddress(address.buildingNumber())
        val secondaryAddressWithoutSuffixes = removeSuffixesFromAddress(address.secondaryAddress())
        secondaryAddress = removeLeadingZeroesFromAddress(secondaryAddressWithoutSuffixes)
    }

    //Метод для удаления суффиксов из названия улицы
    private fun removeSuffixesFromAddress(address: String): String {
        return address.replace(Regex(SUFFIXES_REGEX_PATTERN, RegexOption.IGNORE_CASE), "").trim()
    }

    //метод для удаления ведущих нулей из числового значения
    private fun removeLeadingZeroesFromAddress(number: String): String {
        val regex = Regex(ZEROES_REGEX_PATTERN)
        return number.replace(regex, "")
    }

    //Метод для извлечения почтового индекса и страны из адреса
    private fun extractZipCodeAndCountry(inputString: String): Pair<String?, String?> {
        val regex = Regex(ZIP_CODE_REGEX_PATTERN)
        val matchResult = regex.find(inputString)
        return matchResult?.let {
            val (number, country) = it.destructured
            Pair(number.trim(), country.trim())
        } ?: Pair(null, null)
    }
}

//Класс для хранения списков мужских и женских имен, отчеств и фамилий
data class NameData(
    var maleFirstName: List<String>,
    val maleMiddleName: List<String>,
    val maleLastName: List<String>,
    val femaleFirstName: List<String>,
    val femaleMiddleName: List<String>,
    val femaleLastName: List<String>,
) {
    private constructor() : this(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
}

//Абстрактный класс, представляющий человека с определенными характеристиками
//(имя, фамилия, отчество, возраст, пол, дата рождения)
abstract class Person(
    var firstName: String = DEFAULT_FIRST_NAME,
    var lastName: String = DEFAULT_LAST_NAME,
    var middleName: String = DEFAULT_MIDDLE_NAME,
    var age: String = DEFAULT_AGE,
    var gender: String = DEFAULT_GENDER,
    var birthDate: String = DEFAULT_BIRTH_DATE,
) : MyFakerAddressProvider() {
    // Создаем список ключей
    private val keys = mutableListOf(
        firstName, lastName, middleName, age, gender, birthDate, birthPlace,
        zipCode, personCountry, state, city, streetName, buildingNumber, secondaryAddress,
    )

    // Метод для получения списка всех ключей
    internal fun getKeys(): MutableList<String> {
        return keys
    }
}

open class DataGenerator(private var data: NameData) : Person() {

    // Метод для получения значения поля объекта Person по ключу
    fun getValueByKey(key: String?): String? {
        return when (key) {
            DEFAULT_FIRST_NAME -> firstName
            DEFAULT_LAST_NAME -> lastName
            DEFAULT_MIDDLE_NAME -> middleName
            DEFAULT_AGE -> age
            DEFAULT_GENDER -> gender
            DEFAULT_BIRTH_DATE -> birthDate
            DEFAULT_BIRTH_PLACE -> birthPlace
            DEFAULT_ZIP_CODE -> zipCode
            DEFAULT_COUNTRY -> personCountry
            DEFAULT_STATE -> state
            DEFAULT_CITY -> city
            DEFAULT_STREET_NAME -> streetName
            DEFAULT_BUILDING_NUMBER -> buildingNumber
            DEFAULT_SECONDARY_ADDRESS -> secondaryAddress
            else -> null
        }
    }

    //  Метод генерирует случайные значения полей объекта Person
    //  и заполняет словарь, который отображает ключи на значения
    fun generate() {
        gender = generateGender()
        firstName = generateFirstNameList(gender).random().toString()
        lastName = generateLastNameList(gender).random().toString()
        middleName = generateMiddleNameList(gender).random().toString()
        val pairBirtDateAndAge = generateBirthdayAndAge()
        birthDate = pairBirtDateAndAge.first
        age = pairBirtDateAndAge.second.toString()
        generateFakeAddress()
        val userMap = mutableMapOf<String, String>()
        for (key in getKeys()) {
            when (key) {
                DEFAULT_FIRST_NAME -> userMap[key] = firstName
                DEFAULT_LAST_NAME -> userMap[key] = lastName
                DEFAULT_MIDDLE_NAME -> userMap[key] = middleName
                DEFAULT_AGE -> userMap[key] = age
                DEFAULT_GENDER -> userMap[key] = gender
                DEFAULT_BIRTH_DATE -> userMap[key] = birthDate
                DEFAULT_BIRTH_PLACE -> userMap[key] = birthPlace
                DEFAULT_ZIP_CODE -> userMap[key] = zipCode
                DEFAULT_COUNTRY -> userMap[key] = personCountry
                DEFAULT_STATE -> userMap[key] = state
                DEFAULT_CITY -> userMap[key] = city
                DEFAULT_STREET_NAME -> userMap[key] = streetName
                DEFAULT_BUILDING_NUMBER -> userMap[key] = buildingNumber
                DEFAULT_SECONDARY_ADDRESS -> userMap[key] = secondaryAddress
            }
        }
    }

    // Метод генерирует список имен, соответствующий полу
    private fun generateFirstNameList(gender: String): List<String> {
        return if (gender == "МУЖ") data.maleFirstName else data.femaleFirstName
    }

    // Метод генерирует список фамилий, соответствующий полу
    private fun generateLastNameList(gender: String): List<String> {
        return if (gender == "МУЖ") data.maleLastName else data.femaleLastName
    }

    // Метод генерирует список отчеств, соответствующих полу
    private fun generateMiddleNameList(gender: String): List<String> {
        return if (gender == "МУЖ") data.maleMiddleName else data.femaleMiddleName
    }

    // Метод генерирует случайный пол
    private fun generateGender(): String {
        return if (Random().nextBoolean()) "МУЖ" else "ЖЕН"
    }

    //    Метод генерирует случайную дату рождения в соответствии с возрастом
//    и вычисляет возраст на основе даты рождения и текущей даты
    private fun generateBirthdayAndAge(): Pair<String, Int> {
        // Вычисляем границы возможных дней рождения для требуемого возраста
        val maxBirthDate = LocalDate.now().minusYears(MIN_AGE.toLong())
        val minBirthDate = LocalDate.now().minusYears(MAX_AGE.toLong() + 1)
        // Генерируем случайную дату рождения
        val birthDate = Random().nextLong(
            minBirthDate.toEpochDay(),
            maxBirthDate.toEpochDay()
        ).let { LocalDate.ofEpochDay(it) }
        // Форматируем дату рождения в требуемый формат
        val formattedBirthDate = birthDate.format(DateTimeFormatter.ofPattern(PATTERN_DATE))
        // Вычисляем возраст на основе даты рождения и текущей даты
        val age = Period.between(birthDate, LocalDate.now()).years
        return Pair(formattedBirthDate, age)
    }
}


