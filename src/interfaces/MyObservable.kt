/**
 * @author Awet Ghebreslassie , ID= 1706242
 */

package interfaces

interface MyObservable<T>{
    fun notifyObservers(message:T)
    fun addObserver(observer: MyObserver<T>):Boolean
    fun removeObserver(observer: MyObserver<T>):Boolean
}