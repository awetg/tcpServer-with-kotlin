/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains all commands as lambda functions.
 */

package command

import chat.ChatList
import chat.Chat
import utilities.Constants
import utilities.UniqueID
import Users
import utilities.commandSyntax

val setUser = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.size == 1) {
        if(Users.addUser(commandParams[0], user)) {
            user.logIn(commandParams[0])
            Constants.USERNAME_SET + commandParams[0]
        } else Constants.USERNAME_TAKEN
    } else Constants.INVALID_ARGUMENT
}

val users = { commandParams: List<String>, user: CommandInterpreter ->
    if(commandParams.isEmpty()) {
        if(!user.haveToken()) Users.toString() else "USERS: " + Users.toStringForApp()
    } else Constants.INVALID_ARGUMENT
}

val logIn = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.size == 1) {
        if(Users.usersExist(commandParams.toHashSet())){
            if(!Users.userIsLoggedIn(commandParams[0])) {
                user.logIn(commandParams[0])
                Constants.USER_LOGGED
            }
            else Constants.USERS_AlREADY_LOGGED
        } else {

            Constants.USERS_NOT_FOUND
        }
    } else Constants.INVALID_ARGUMENT
}

val logOut = {commandParams: List<String>, user:CommandInterpreter ->
    if (commandParams.isEmpty()) {
        user.logOut()
        Constants.USER_LOGGED_OUT
    } else Constants.INVALID_ARGUMENT
}

val quit = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.isEmpty()) {
        user.quit()
        Constants.CLOSE
    } else Constants.INVALID_ARGUMENT
}

val to = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.isNotEmpty()) {

        if(commandParams[0] == "all"){
            user.currentChat= ChatList.PublicChat
            user.currentChatName = ChatList.PublicChat.name
            Constants.PUBLIC_CHAT + 0
        } else if(!Users.usersExist(commandParams.toHashSet())) Constants.USERS_NOT_FOUND

       else {
            val participant = (listOf(user.getUserName()) + (commandParams)).toMutableSet()
            if (participant.size < 2) Constants.USERS_NOT_FOUND
            else {
                val chat = ChatList.getChat(user.chatList, participant)
                if (chat != null) {
                    user.currentChatName = chat.name
                    user.currentChat = chat
                    Constants.OLD_CHAT + chat.id
                } else {
                    val chatName = participant.asSequence().sorted().joinToString(",")
                    val newChat = Chat(UniqueID.getID(), participant, chatName)
                    ChatList.addChat(newChat)
                    user.currentChatName = chatName
                    user.currentChat = newChat
                    Constants.NEW_CHAT + newChat.id
                }
            }
        }
    } else Constants.INVALID_ARGUMENT
}

val toChat = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.isNotEmpty()) {
        val chatId = commandParams.component1().toLong()
        if(user.chatList.contains(chatId)) {
            val chat = ChatList.getChatById(chatId)
            if(chat != null){
                user.currentChatName = chat.name
                user.currentChat = chat
                Constants.OLD_CHAT + chat.id
            } else Constants.CHAT_NOT_FOUND
        } else Constants.CHAT_NOT_FOUND
    } else Constants.INVALID_ARGUMENT
}

val messages = { commandParams: List<String>, user: CommandInterpreter ->
    if(commandParams.isEmpty()) {
        if(!user.haveToken())user.currentChat.toString() else user.currentChat.toStringForApp()
    } else Constants.INVALID_ARGUMENT
}

//val lastFrom = { commandParams: List<String>, user: CommandInterpreter ->
//    if(commandParams.isNotEmpty()) {
//        if(commandParams.all { it.toLongOrNull() != null }) {
//            val chatIds = commandParams.map { it.toLong() }
//            ChatList.getLatestMessages(chatIds.toHashSet())
//        } else Constants.INVALID_INPUT
//    } else if(commandParams.isEmpty()) user.currentChat.getLatestMessage()
//    else Constants.INVALID_ARGUMENT
//}

val lastAll = { commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.isEmpty()) {
        if(!user.haveToken())ChatList.getLatestMessages(user.chatList) else ChatList.getLatestMessagesForApp(user.chatList)
    } else Constants.INVALID_ARGUMENT
}

val history = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.isEmpty()) {
        ChatList.getHistoryForUser(user.chatList)
    } else Constants.INVALID_ARGUMENT
}

val chatList = {commandParams: List<String>, user:CommandInterpreter ->
    if(commandParams.isEmpty()) {
        user.chatList.map { it.toString()}.joinToString(System.lineSeparator())
    } else Constants.INVALID_ARGUMENT
}

val topChatter = { commandParams: List<String>, _: CommandInterpreter ->
    if (commandParams.isEmpty()) {
        ChatList.getTopChatter()
    } else Constants.INVALID_ARGUMENT
}

val addToken = {commandParams: List<String>, user:CommandInterpreter ->
    if (commandParams.size == 1) {
        user.addToken(commandParams[0])
        Constants.TOKEN_ADDED
    } else Constants.INVALID_ARGUMENT
}

val help = { _: List<String>, _:CommandInterpreter ->
    commandSyntax.values.joinToString(System.lineSeparator())
}

