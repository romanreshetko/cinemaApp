import DAO.*
import entities.Menu

fun main() {
    var authorizated = false
    while (!authorizated) {
        authorizated = Authorization.authorization()
    }

    val menu = Menu()
    menu.show()

    var option = readln()
    while (option != "8") {
        dispatch(option)
        menu.show()
        option = readln()
    }
    println("Program is finished")
}

fun dispatch(option : String) {
    when (option) {
        "1" -> FilmDao.show()
        "2" -> FilmDao.edit()
        "3" -> TicketsDao.operateTicket(true)
        "4" -> TicketsDao.operateTicket(false)
        "5" -> TimetableDao.show()
        "6" -> TimetableDao.edit()
        "7" -> ControllerDao.controllTicket()
        "8" -> println("Program is finished")
        else -> println("Incorrect input")
    }

}