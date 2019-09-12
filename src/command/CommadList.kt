/**
 *  *  *@author Awet Ghebreslassie , ID= 1706242
 *
 *  This file contains list of all supported commands.
 *  the list is map of commands as String and lambdas.
 *  Lambdas were chosen over inheritance for commands, this helped with cleaner and shorter code when implementing commands.
 *  Functionality will be added as per need when implementing Android client application
 *  :LAST command returns the last message from the current chat or,
 *  if one or more chat id is given as parameter last message from those chats will be returned.
 *  :LAST-ALL will return last message from all chats observed by this user
 *  :MESSAGE will return all messages in the current chat.
 *  :HISTORY will return all messages from all the chats this particular user observes
 *  :CHATS will return list of chat ids
 *  :TO command is used to talk in private chats, it accept one or more user names, new private chat created if this users haven't talked before.
 */

package command

internal val commandList = mapOf<String,(commandParams: List<String>, user:CommandInterpreter)->String>(
        ":USER" to setUser,
        ":USERS" to users,
        ":LOGIN" to logIn,
        ":LOGOUT" to logOut,
        ":QUIT" to quit,
        ":EXIT" to quit,
        ":TO" to to,
        ":MESSAGES" to messages,
        ":LAST-ALL" to lastAll,
        ":CHATS" to chatList,
        ":HISTORY" to history,
        ":TOPCHATTER" to topChatter,
        ":ADDTOKEN" to addToken,
        ":HELP" to help,
        ":TOCHAT" to toChat
//        ":LAST" to lastFrom

)