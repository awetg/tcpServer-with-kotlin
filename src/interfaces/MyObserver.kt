/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 */

package interfaces


interface MyObserver<T>{
    fun getNotified(message:T)
}