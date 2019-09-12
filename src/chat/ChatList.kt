/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains list of all chats. Public chat is a property of this chat list and added to the list from server on start up.
 * id of public chat is 0 and all unique id of other chats starts from 1 and counts up.
 */

package chat

import Users
import interfaces.MyObserver

object ChatList {

    private val _chatList= mutableMapOf<Long, Chat>()

    object PublicChat : Chat(0, mutableSetOf(),"Group")

    fun addChat(chat: Chat) {  _chatList.put(chat.id, chat) }

    //get chat by name of participant on it
    fun getChat(chatIds:HashSet<Long>, participants:MutableSet<String>): Chat?  {
        val chat = chatIds.mapNotNull { _chatList.get(it) }.filter { it.participants == participants }
        return if(chat.isNotEmpty()) chat.component1() else null
    }

    fun getChatById(chatId: Long):Chat? {
        return _chatList.get(chatId)
    }

    fun getLatestMessages(chatIds: HashSet<Long>):String {
        if(_chatList.keys.containsAll(chatIds))
        return chatIds.mapNotNull { _chatList.get(it) }
                .map{ it.getLatestMessage() }
                .filterNotNull()
                .sortedByDescending { it.chatMessage.dateTime }
                .joinToString(System.lineSeparator())
        else
            return "Invalid chat ids."
    }

    fun getTopChatter(): String {
        val messageCount = mutableMapOf<String, Int>()
        Users.getUserNames().forEach {
            val user = it
            var count = 0
            _chatList.forEach { _, chat ->
                count += chat.getMessagecountForUser(user)
            }
            messageCount.put(user, count)
        }

        val maxEntry = messageCount.maxBy { it.value }
        if(maxEntry?.value ==0 ) return "No messages sent yet"
        return   "User: ${maxEntry?.key} message count: ${maxEntry?.value}"
    }

    fun getHistoryForUser(chatIds: HashSet<Long>): String {
       return chatIds.mapNotNull{ _chatList.get(it) }.map{ it.toString() }.joinToString (System.lineSeparator())
    }


    fun getLatestMessagesForApp(chatIds: HashSet<Long>):String {
        if(_chatList.keys.containsAll(chatIds))
            return chatIds.mapNotNull { _chatList.get(it) }
                    .map{ it.getLastMessageForApp() }
                    .filterNotNull()
                    .sortedByDescending { it.chatMessage.dateTime }
                    .map { it.toStringForApp() }
                    .joinToString(System.lineSeparator())
        else
            return "Invalid chat ids."
    }

    fun replaceObserver(oldObserver: MyObserver<ChatMessage>, newObserver: MyObserver<ChatMessage>) {
        _chatList.forEach { _, u ->
            u.replaceObserver(oldObserver,newObserver)
        }
    }
}