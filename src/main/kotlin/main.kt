import org.andstatus.personsmerger.IdModelThree
import org.andstatus.personsmerger.Person

fun main(args: Array<String>) {

    val person1 = Person("Иван", "Иванович", "Иванов", "01.01.1950", "1111", "2222", "3333", "", "4444", "5555", "")
    val person2 = Person("Иван", "Иванович", "Иванов", "01.01.1950", "1111", "2222", "3333", "", "6666", "6666", "")
    val merge: Boolean = IdModelThree.trained.identify(person1, person2).merge

    println("Using trained model decided: " + if (merge) "merge" else "don't merge")
}