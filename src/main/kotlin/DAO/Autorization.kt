package DAO

import Interface.Editable
import Interface.Userable
import entities.User
import kotlin.io.path.Path

class Authorization {
    companion object {
        private const val USERS_DIR = "users"
        private fun logIn(userable: Userable): Boolean {
            val file = Path(Editable.INFO_DIR, USERS_DIR, userable.getLog()).toFile()
            if (!file.exists()) {
                println("No such user")
                return false
            } else {
                val correctPassword = Encoder.decode(file.readText())
                if (userable.getPassword() == correctPassword) {
                    return true
                }
            }
            println("Wrong password")
            return false
        }

        private fun signIn(userable: Userable): Boolean {
            val file = Path(Editable.INFO_DIR, USERS_DIR, userable.getLog()).toFile()
            if (file.exists()) {
                println("User already exists")
                return false
            } else {
                file.parentFile.parentFile.mkdir()
                file.parentFile.mkdir()
                file.writeText(Encoder.encode(userable.getPassword()))
                return true
            }
        }

        fun authorization() : Boolean {
            println("1. Log in")
            println("2. Sign in")
            val option = readln()
            when (option) {
                "1" -> return logIn(getUser())
                "2" -> return signIn(getUser())
                else -> authorization()
            }
            return false
        }

        private fun getUser() : User {
            print("Enter log: ")
            val log = readln()
            print("Enter password: ")
            val password = readln()
            return User(log, password)
        }
    }
}