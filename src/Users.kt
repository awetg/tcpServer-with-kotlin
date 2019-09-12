/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains 'Users' class. This object/singleton keeps list of users in mapof(username, CommandInterpreter)
 * keeping track of user as CommandInterpreter instance helps with returning user as observer or CommandInterpreter
 */

import chat.ChatMessage
import command.CommandInterpreter
import interfaces.MyObserver
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

object Users {
    private val _users = mutableMapOf<String, CommandInterpreter>()

    fun getUserNames()= _users.keys

    fun getUser(userName: String):CommandInterpreter? = _users.get(userName)

    fun getObserver(userName: String): MyObserver<ChatMessage>? = _users.get(userName)

    fun addUser(userName:String, user: CommandInterpreter):Boolean {
        return if(!_users.keys.contains(userName)){
        _users.put(userName,user)
            true
        } else false
    }

    fun replaceUser(userName: String,oldUser:CommandInterpreter,newUser:CommandInterpreter) = _users.replace(userName,oldUser,newUser)

    override fun toString(): String {
        return _users.keys.joinToString(System.lineSeparator())
    }

    fun toStringForApp(): String {
        return _users.keys.joinToString(" ")
    }

    fun usersExist(users: HashSet<String>): Boolean {
       return  _users.keys.containsAll(users)
    }

    fun userIsLoggedIn(user:String):Boolean {
        return _users.get(user)?.loggedIn?:false
    }
}