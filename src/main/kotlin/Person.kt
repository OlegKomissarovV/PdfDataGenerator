
//Абстрактный класс, представляющий человека с определенными характеристиками
//(имя, фамилия, отчество, возраст, пол, дата рождения)
abstract class Person(var firstName: String = DEFAULT_FIRST_NAME,
                      var lastName: String = DEFAULT_LAST_NAME,
                      var middleName: String = DEFAULT_MIDDLE_NAME,
                      var age: String = DEFAULT_AGE,
                      var gender: String = DEFAULT_GENDER,
                      var birthDate: String = DEFAULT_BIRTH_DATE,
) : MyFakerAddressProvider() {
    abstract fun generateData()
}