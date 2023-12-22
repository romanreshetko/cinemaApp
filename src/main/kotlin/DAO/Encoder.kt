package DAO

class Encoder {
    companion object {
        fun encode(password : String) : String {
            var code : String = ""
            for (char in password) {
                code = code.plus(char + 1)
            }
            return code
        }

        fun decode(code : String) : String {
            var password : String = ""
            for (char in code) {
                password = password.plus(char - 1)
            }
            return password
        }
    }
}