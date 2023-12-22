package DAO

import DAO.TimetableDao.Companion.returnTicket
import DAO.TimetableDao.Companion.sellTicket
import entities.TimeOfDay

class TicketsDao {

    companion object {
        fun operateTicket(sellReturn: Boolean) {
            val time = TimetableDao.getTime()
            println("The hall:")
            if (!TimetableDao.showHall(time)) {
                return
            }
            print("Enter row: ")
            val row = readln().toIntOrNull()
            print("Enter place: ")
            val place = readln().toIntOrNull()
            if (row == null || place == null) {
                println("Incorrect row or place")
                return
            }
            if (row > 10 || row < 1 || place > 10 || place < 1) {
                println("Incorrect row or place")
                return
            }
            if (sellReturn) {
                sellTicket(row - 1, place - 1, time)
            } else {
                returnTicket(row - 1, place - 1, time)
            }
        }
    }
}