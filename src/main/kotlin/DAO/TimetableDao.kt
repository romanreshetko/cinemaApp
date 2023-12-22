package DAO

import Interface.Editable.Companion.INFO_DIR
import entities.Film
import entities.Session
import entities.TimeOfDay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import kotlin.io.path.Path

class TimetableDao {
    companion object {
        private const val TIMETABLE_DIR = "timetable"
        private var timetable = mutableListOf<Session>()

        private fun updateFromFile() {
            val file = Path(INFO_DIR, TIMETABLE_DIR, "sessions").toFile()
            if (!file.exists()) {
                timetable = mutableListOf<Session>()
                return
            }
            val json = file.readText(Charset.defaultCharset())
            timetable = Json.decodeFromString<MutableList<Session>>(json)
        }

        private fun updateFile() {
            val file = Path(INFO_DIR, TIMETABLE_DIR, "sessions").toFile()
            file.parentFile.parentFile.mkdir()
            file.parentFile.mkdir()
            val json = Json.encodeToString(timetable)
            file.writeText(json, Charset.defaultCharset())
        }

        fun show() {
            updateFromFile()
            sort()
            for (session in timetable) {
                println("Name: ${session.film.name}, start: ${session.start.toString()}")
            }
        }

        private fun add(session: Session) {
            updateFromFile()
            timetable.add(session)
            sort()
            updateFile()
        }

        fun delete(time: TimeOfDay) {
            for (session in timetable) {
                if (session.start.hours == time.hours && session.start.minutes == time.minutes) {
                    timetable.remove(session)
                    break
                }
            }
            updateFile()
        }

        private fun clear() {
            timetable.clear()
            updateFile()
        }

        fun edit() {
            println("Choose editing option:")
            println("1. Add session")
            println("2. Remove session")
            println("3. Clear timetable")
            val option = readln()
            try {
                when (option) {
                    "1" -> add(getSession())
                    "2" -> delete(getTime())
                    "3" -> clear()
                    else -> edit()
                }
            } catch (ex: RuntimeException) {
                println(ex.message)
            }
        }

        private fun getSession(): Session {
            print("Enter name of the film: ")
            val name = readln()
            var film: Film
            try {
                film = FilmDao.filmByName(name)
            } catch (ex: RuntimeException) {
                throw ex
            }
            return Session(film, getTime())
        }

        fun getTime(): TimeOfDay {
            print("Enter start time of the session. Hour: ")
            var hour = readln().toIntOrNull()
            while (hour == null) {
                println("Wrong hour")
                print("Hour: ")
                hour = readln().toIntOrNull()
            }
            print("Minute: ")
            var minute = readln().toIntOrNull()
            while (minute == null) {
                println("Wrong minute")
                print("Minute: ")
                minute = readln().toIntOrNull()
            }
            return TimeOfDay(hour, minute)
        }

        private fun sort() {
            timetable.sortWith(compareBy({ it.start.hours }, { it.start.minutes }))
        }

        fun sellTicket(row: Int, place: Int, time: TimeOfDay) {
            updateFromFile()
            for (session in timetable) {
                if (session.start.hours == time.hours && session.start.minutes == time.minutes) {
                    if (session.hall[row][place] == 0) {
                        session.hall[row][place] = 1
                        println("Ticket is sold")
                        updateFile()
                    } else {
                        println("Place is already taken")
                    }
                }
            }
        }

        fun returnTicket(row: Int, place: Int, time: TimeOfDay) {
            updateFile()
            for (session in timetable) {
                if (session.start.hours == time.hours && session.start.minutes == time.minutes) {
                    if (session.hall[row][place] == 1) {
                        session.hall[row][place] = 0
                        println("Ticket is returned")
                        updateFile()
                    } else if (session.hall[row][place] == 0) {
                        println("Place is not taken ")
                    } else {
                        println("Person already in the hall")
                    }
                }
            }
        }

        fun checkTicket(row: Int, place: Int, time: TimeOfDay) {
            updateFile()
            for (session in timetable) {
                if (session.start.hours == time.hours && session.start.minutes == time.minutes) {
                    if (session.hall[row][place] == 1) {
                        session.hall[row][place] = 2
                        println("Person entered hall")
                        updateFile()
                    } else {
                        println("No such ticket")
                    }
                }
            }
        }

        fun showHall(time: TimeOfDay): Boolean {
            updateFromFile()
            for (session in timetable) {
                if (session.start.hours == time.hours && session.start.minutes == time.minutes) {
                    session.showHall()
                    return true
                }
            }
            println("No such session")
            return false
        }
    }
}