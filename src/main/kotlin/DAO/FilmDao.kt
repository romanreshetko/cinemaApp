package DAO

import Interface.Editable.Companion.INFO_DIR
import entities.Film
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.Charset
import kotlin.io.path.Path

class FilmDao {

    companion object {
        private const val FILM_DIR = "films"


        fun show() {
            val dir = Path(INFO_DIR, FILM_DIR).toFile()
            val files = dir.listFiles()
            for (file in files) {
                println(file.name)
            }
        }


        private fun deleteFile(name: String) {
            val file = Path(INFO_DIR, FILM_DIR, name).toFile()
            if (file.exists()) {
                file.delete()
            }
        }

        private fun saveToFile(film: Film) {
            val file = Path(INFO_DIR, FILM_DIR, film.name).toFile()
            val json = Json.encodeToString(film)
            file.parentFile.parentFile.mkdir()
            file.parentFile.mkdir()
            file.writeText(json, Charset.defaultCharset())
        }

        fun edit() {
            println("Choose editing option:")
            println("1. Add film")
            println("2. Remove film")
            val option = readln()
            when (option) {
                "1" -> saveToFile(getFilm())
                "2" -> deleteFile(getName())
                else -> edit()
            }
        }

        private fun getFilm(): Film {
            print("Enter name of the film: ")
            val name = readln()
            print("Enter duration of the film in minutes: ")
            var dur = readln().toIntOrNull()
            while (dur == null) {
                println("Wrong duration")
                print("Enter duration of the film in minutes: ")
                dur = readln().toIntOrNull()
            }
            return Film(name, dur)
        }

        private fun getName(): String {
            print("Enter name of the film: ")
            return readln()
        }

        fun filmByName(name: String): Film {
            val file = Path(INFO_DIR, FILM_DIR, name).toFile()
            if (!file.exists()) {
                throw RuntimeException("No such film in library")
            }
            val json = file.readText(Charset.defaultCharset())
            return Json.decodeFromString<Film>(json)
        }
    }

}