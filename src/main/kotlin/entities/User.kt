package entities

import Interface.Userable

class User(private val log: String, private val password: String) : Userable {
    override fun getLog(): String {
        return log
    }

    override fun getPassword(): String {
        return password
    }

}