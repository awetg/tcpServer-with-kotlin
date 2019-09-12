/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains class 'Chat'. Chat represent a conversation two or more users have.
 * chat is observable entity.
 *
 */

package chat

import interfaces.MyObservable
import interfaces.MyObserver
import Users

/**
 * @property id is a generated id, custom id generator used instead of java's UUID
 * @property participants usernames of users participating in this chat, will be automatically added as observers on constructor
 */
open class Chat(val id:Long, val participants: MutableSet<String>, val name: String): MyObservable<ChatMessage> {

    private val _observers = mutableSetOf<MyObserver<ChatMessage>>()
    private val _messages = mutableListOf<ChatMessage>()

    init {
        participants.forEach{_observers.add(Users.getObserver(it)!!)}
        participants.forEach { Users.getUser(it)?.updateChatList(id) }
    }

    override fun addObserver(observer: MyObserver<ChatMessage>) = _observers.add(observer)

    override fun removeObserver(observer: MyObserver<ChatMessage>) = _observers.remove(observer)

    override fun notifyObservers(message: ChatMessage) =  _observers.forEach { it.getNotified(message) }


    fun addMessage(message: ChatMessage) {
        _messages.add(message)
        notifyObservers(message)
    }

    fun getLatestMessage():LastMessage? {
        return if(_messages.isNotEmpty()) LastMessage(name,_messages.last()) else null
    }

    fun getMessagecountForUser(user: String): Int {
            return _messages.fold(0){ acc,message-> if(message.sender == user) (acc + 1) else acc}
    }

    override fun toString(): String {
        return _messages.map { it.toString() }.joinToString(System.lineSeparator())
    }

    fun toStringForApp(): String {
        return  _messages.map { it.toStringForApp() }.joinToString(System.lineSeparator())
    }

    fun getLastMessageForApp(): LastMessage? {
        return if(_messages.isNotEmpty()) LastMessage(name, _messages.last()) else null
    }

    fun replaceObserver(oldObserver: MyObserver<ChatMessage>,newObserver: MyObserver<ChatMessage>) {
        _observers.remove(oldObserver)
        _observers.add(newObserver)
    }
}