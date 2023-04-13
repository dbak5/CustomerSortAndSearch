package objects

import java.io.Serializable

/*
Name: DaHye Baker
ID: 30063368
Assessment 2: Activity 1
*/

class Customer (var name: String) : Serializable {
    private var id: Int
    var email: String? = null
    var mobile: String? = null

    companion object {
        private var counter = 0
    }

    init{
       id = ++counter
    }

    constructor(name: String, email: String?, mobile: String?) : this(name) {
        this.id = id
        this.email = email
        this.mobile = mobile
    }

    // Displays customer properties
    fun displayCustomer() {
        println("--> ${this.name}, ${this.email}, ${this.mobile}")
    }

    // Search customer properties to return boolean
    fun contains(searchItem: String, searchType: String): Boolean {
        val value: Boolean = when (searchType){
            "name" -> {
                name.uppercase().contains(searchItem)
            }

            "email" ->{
                email?.uppercase()?.contains(searchItem) == true
            }

            "mobile" ->{
                mobile?.uppercase()?.contains(searchItem) == true
            }
            else -> {
                false
            }
        }
        return value
    }

}