import objects.Customer
import java.io.*
import kotlin.system.exitProcess

/*
Name: DaHye Baker
ID: 30063368
Assessment 2: Activity 1
*/

fun main() {

    // Exception for no customer found when searched
    class CustomerNotFound(message: String): Exception(message)

    //region Create arraylist
    var customerList: MutableList<Customer> = ArrayList()
    var sorted = false

    // Create customers
    val customer1 = Customer( "Banana Bread")
    val customer2 = Customer("Mango","apple@gmail.com", "03541365")
    val customer3 = Customer("Banana", "Kiwi@gmail.com", "04355131")
    val customer4 = Customer("Apple Pie")
    val customer5 = Customer("Kiwifruit")

    // Add customers to arraylist
    customerList.add(customer1)
    customerList.add(customer2)
    customerList.add(customer3)
    customerList.add(customer4)
    customerList.add(customer5)

    //endregion

    //region Functions

    // Function to display all the customers in the list
    fun displayCustomers(customerList: MutableList<Customer>) {
        println("\nThere are ${customerList.count()} customers:")
        customerList.forEach { it.displayCustomer() }
    }

    // Function to sort the customer list
    fun sortCustomers(customerList: MutableList<Customer>): List<Customer> {
        sorted = true
        return customerList.sortedWith(compareBy { it.name })
    }

    fun searchCustomer(customerList: MutableList<Customer>, searchType: String){
        println("\nPlease enter the customer $searchType to search")

        val searchItem = readln().uppercase()
        val sortedCustomerList = sortCustomers(customerList)
        val resultsList: MutableList<Customer> = ArrayList()

        // Loop through list and search for a customer
        // by property (searchType)
        // with the user input (searchItem)
        // add customer to results list
        for (customer in sortedCustomerList) {
            if (customer.contains(searchItem, searchType)) {
                resultsList.add(customer)
            }
        }

        // If there are no results, return exception
        // If there are results, display them
        if (resultsList.isEmpty()){
            throw CustomerNotFound("No customer(s) found, please try again")
        }
        else {
            println("\nResults found:")
            for (customer in resultsList)
            {
                customer.displayCustomer()
            }
        }
    }

    // Function to store array of objects in a binary file
    fun saveFile() {
        try {
            val fileOut = FileOutputStream("customers.dat")
            val objectOut = ObjectOutputStream(fileOut)
            objectOut.writeObject(customerList)
            fileOut.close()
            println("Successfully saved binary file\n")
        } catch (e: IOException) {
            println(e)
        }
    }

    // Reads the object from the binary file and displays the objects.Customer contents on the screen
    fun openFile(){
        try {
            val fileIn = FileInputStream("customers.dat")
            val objectIn = ObjectInputStream(fileIn) // input stream
            val sortedCustomerList = objectIn.readObject() as ArrayList<*>
            fileIn.close()
            customerList = sortedCustomerList as ArrayList<Customer>
            println("File opened\n")
            displayCustomers(customerList)
        } catch (e: FileNotFoundException) {
            println("File not found")
        }
    }

    // Function for the menu, taking user inputs
    fun menuOptions() {
        val NAME = "name"
        val EMAIL = "email"
        val MOBILE = "mobile"

        println("\n1 --> Display the customer details\n" +
                "2 --> Sort customer by name\n" +
                "3 --> Search customer by $NAME\n" +
                "4 --> Search customer by $EMAIL\n" +
                "5 --> Search customer by $MOBILE\n" +
                "6 --> Save customers to binary file\n" +
                "7 --> Read customers from file\n" +
                "8 --> Quit program\n\n" +
                "Select number:")

        // Take user input and run functions
        try {
            when (readln().toInt()) {

                1 -> {
                    if (sorted) {
                        displayCustomers(sortCustomers(customerList) as MutableList<Customer>)
                    } else {
                        displayCustomers(customerList)
                    }
                    menuOptions()
                }

                2 -> {
                    sortCustomers(customerList)
                    println("\nCustomers sorted")
                    menuOptions()
                }

                3 -> {
                    try{
                        searchCustomer(customerList, NAME)
                    } catch (e: CustomerNotFound){
                        println("Exception: $e")
                    }
                    menuOptions()
                }

                4 -> {
                    try{
                        searchCustomer(customerList, EMAIL)
                    } catch (e: CustomerNotFound){
                        println("Exception: $e")
                    }
                    menuOptions()
                }

                5 -> {
                    try{
                        searchCustomer(customerList, MOBILE)
                    } catch (e: CustomerNotFound){
                        println("Exception: $e")
                    }
                    menuOptions()
                }

                6 -> {
                    saveFile()
                    menuOptions()
                }

                7 -> {
                    openFile()
                    menuOptions()
                }

                8 -> {
                    exitProcess(0)
                }

                else -> {
                    println("\nInvalid input, please try again")
                    menuOptions()
                }
            }
        } catch (e: NumberFormatException) {
            println("\nInvalid input, please try again")
            menuOptions()
        }
    }

    //endregion

    menuOptions()
}
