/**
 *  @author Awet Ghebreslassie , ID= 1706242
 *
 *  This file contains all String output (String literals) of this application as constants
 */

package utilities

object Constants {
    //ERROR
    const val INVALID_COMMAND = "ERROR: Did not get "
    const val INVALID_INPUT = "ERROR: Invalid input"
    const val INVALID_ARGUMENT = "ERROR: Too many or too few argument.Enter :help for command list.SYNTAX_ERROR"
    const val HELP = ". Enter :help for command list"
    const val USERNAME_TAKEN = "ERROR: username not available.USERNAME_TAKEN"
    const val USERNAME_NOTSET = "ERROR: username not set.Use :user <username> set it."
    const val USERS_NOT_FOUND = "ERROR: Entered username(s) does not exist or misspelled.USER_NOT_FOUND"
    const val USERS_AlREADY_LOGGED = "ERROR: User is already logged in.USER_ALREADY_LOGGED"
    const val CHAT_NOT_FOUND ="ERROR: Chat not found"

    //INFO
    const val WELLCOME = "CONNECTED: Welcome to 2018 chat server.Please, enter your command."
    const val CLOSE = "DISCONNECTED: Closing..."
    const val USERNAME_SET = "LOGGED: Username set to "
    const val USER_LOGGED = "INFO: You are logged in."
    const val USER_LOGGED_OUT = "INFO: You are logged out"
    const val PUBLIC_CHAT = "CHAT_CHANGED: You are now public chat.  CHAT_ID:"
    const val NEW_CHAT = "CHAT_CHANGED: New private chat created. CHAT_ID:"
    const val OLD_CHAT = "CHAT_CHANGED: You are in previously created private chat. CHAT_ID:"
    const val TOKEN_ADDED = "INFO: Token added."
}

internal val commandSyntax = mapOf(
        ":USER" to "To set username :user <username>",
        ":USERS" to "To get list of users :users",
        ":LOGIN" to "To login  :login <username>",
        ":LOGOUT" to "To logout :logout <username>",
        ":QUIT" to "To exit/quit use :exit or :quit",
        ":TO" to "To send private messages use(with 1 or more usernames) :to <username1> [username2] ..",
        ":MESSAGES" to "To get all messages in current chat use :messages",
        ":LAST" to "To get the last message from a chat(optional argument) :last [chatId1] [chatId2] ..",
        ":LAST-ALL" to "To get last messages from all chats :last-all",
        ":CHATS" to "To get list of chats :chats",
        ":HISTORY" to "To get entire history :history",
        ":TOPCHATTER" to "To get top chatter :topchatter ",
        ":ADDTOKEN" to "To add mobile fcm token :addtoken <token>"
)