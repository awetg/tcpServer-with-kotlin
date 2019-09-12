/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains 'UniqueID' object(singleton). It is used to generate unique ids.
 * This was implemented over java's UUID because it provides enough functionality for the purpose, and can give simplicity and
 * easier will be easier to debug.
 */

package utilities

object UniqueID{
    private var id:Long = 1
    fun getID():Long = id++
}