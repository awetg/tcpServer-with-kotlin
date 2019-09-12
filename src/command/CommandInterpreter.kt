/**
 *@author Awet Ghebreslassie , ID= 1706242
 *
 *This file contains class CommandInterpreter which handles the IO operation of each connected client.
 * A new thread and CommandInterpreter instance is created for every connected client.
 * User details property are included in CommandInterpreter instead of using 'User' class,
 * to avoid confusion with list of users in 'Users.kt' file which holds list of CommandInterpreter,
 * CommandInterpreter is regarded as user for easy casting to MyObserver instance or CommandInterpreter instance.
 */

package command

import chat.Chat
import chat.ChatList
import chat.ChatMessage
import utilities.Constants
import interfaces.MyObserver
import Users
import SendPushNotification
import java.io.*
import java.util.*
import kotlin.collections.HashSet

class CommandInterpreter(inputStream: InputStream, outputStream: OutputStream ) : MyObserver<ChatMessage>, Runnable {

    //util property
    private val _cmd = Regex("^:\\w")
    private val _split = Regex("\\s+")
    //this variable is used for marking end of output stream instead of closing socket
    private val _EOS = System.lineSeparator() + "EOS:)#"

    //main property
    private val _scanner = Scanner(inputStream)
    private val _printStream = PrintStream(outputStream)

    //user details property
    private var _name = "unkown"
    private var _online: Boolean = true
    private var _fcmToken = ""
    private var _haveToken: Boolean= false
    var loggedIn = false
    var chatList: HashSet<Long> = hashSetOf()
    var currentChat: Chat = ChatList.PublicChat
    var currentChatName: String = ChatList.PublicChat.name

    override fun run () {
        open()
        while (_online) {
            try {
                val input = _scanner.nextLine().trim()
                if (!isCommand(input)) sendMessage(input)
            } catch (e:Exception) {
                println(e.message)
                quit()
            }
        }
        close()
    }

    /**
     * public methods
     * All public methods are getters and setters for user details. This approach is used instead of kotlin getter and setter,
     * in order to make distinction between user details and CommandInterpreter
     */
    fun getUserName(): String = _name

    fun getToken(): String = _fcmToken

    fun addToken(token:String) {
        _fcmToken = token
        _haveToken = true
        _printStream.println(ChatMessage("Welcome to group chat.", _name, currentChat.id,currentChatName).toStringForApp() + _EOS)
    }

    fun haveToken(): Boolean = _haveToken

    fun quit()  {loggedIn=false; _online=false}

    fun updateChatList(chatId: Long) {chatList.add(chatId)}

    fun logOut() {loggedIn = false}

    fun logIn(userName:String)  {
        _name=userName
        loggedIn=true
        _online = true
        val oldUser = Users.getUser(userName)
        if(oldUser != null) {
            _haveToken = oldUser._haveToken
            _fcmToken = oldUser.getToken()
            chatList = oldUser.chatList
            val oldObserver = Users.getObserver(userName)
            if(oldObserver != null)
            ChatList.replaceObserver(oldObserver,this)
            Users.replaceUser(userName,oldUser,this)
        }
    }

    //observer method
    override fun getNotified(message: ChatMessage) {
        if(loggedIn) {
            if(_haveToken)
                _printStream.println(message.toStringForApp() + _EOS)
            else
                _printStream.println(message)
        }
        else {
            if(_haveToken){
                SendPushNotification(_fcmToken, message).invoke()
            }
        }
    }

    //private methods

    /**
     * @param input - the user input
     * This method check if the input contains command, if input contains command all returns are true
     * if input contains command, it is checked against list of commands, if not found invalid command is printed to client
     * if command is found and user is loggedIn(username is set) command is executed, else it tries to set the username
     */
    private fun isCommand(input:String): Boolean {
        if(input.contains(_cmd)) {
            val inputList = input.split(_split)
            val enteredCommand = inputList[0].toUpperCase()
            val commandParameters = inputList.drop(1)
            val command = commandList.get(enteredCommand)

            if (command != null) {
                if (loggedIn) {
                    printToClient(command.invoke(commandParameters, this))
                    return true
                } else {
                    if(enteredCommand == ":USER" || enteredCommand== ":LOGIN") printToClient(command.invoke(commandParameters, this) + _EOS)
                    else printToClient(Constants.USERNAME_NOTSET + _EOS)
                    return true
                }
            } else {
                printToClient(Constants.INVALID_COMMAND + enteredCommand + Constants.HELP + _EOS)
                return  true
            }
        }
        return false
    }

    private fun sendMessage(input: String) = if(loggedIn) currentChat.addMessage(ChatMessage(input, _name, currentChat.id,currentChatName)) else printToClient(Constants.USERNAME_NOTSET)

    private fun printToClient(message: String) = if(_haveToken) _printStream.println(message + _EOS) else _printStream.println(message)

    private fun close() =_printStream.close()

    private fun open() {
        printToClient(Constants.WELLCOME + _EOS)
        chatList.add(0)
        currentChat.addObserver(this)
    }
}
