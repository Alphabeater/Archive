package machine
import java.lang.Exception

/*Not sure what to do for stage 6/6, stage 5/6 works.
I don't know how state design patterns work, instructions are unclear.*/

open class CoffeeMachine(var water: Int, var milk: Int, var beans: Int, var cups: Int, var money: Int) {

    fun buy(option: Int) {
        when (option) {
            1 -> makeCoffee(waterNeeded = 250, beansNeeded = 16, cost = 4) //espresso
            2 -> makeCoffee(350, 75, 20, 7) //latte
            3 -> makeCoffee(200, 100, 12, 6) //cappuccino
            else -> throw Exception("Invalid option.")
        }
    }

    private fun makeCoffee(waterNeeded: Int, milkNeeded: Int = 0, beansNeeded: Int, cost: Int) {
        if (cups > 0) {
            if (waterNeeded > water) throw Exception("Sorry, not enough water!")
            else if (milkNeeded > milk) throw Exception("Sorry, not enough milk!")
            else if (beansNeeded > beans) throw Exception("Sorry, not enough coffee beans!")
            else {
                println("I have enough resources, making you a coffee!")
                water -= waterNeeded
                milk -= milkNeeded
                beans -= beansNeeded
                money += cost
                cups -= 1
            }
        } else throw Exception("Sorry, not enough cups!")
    }

    fun fill(addWater: Int, addMilk: Int, addBeans: Int, addCups: Int) {
        water += addWater
        milk += addMilk
        beans += addBeans
        cups += addCups
    }

    fun take() {
        println("I gave you $$money")
        money = 0
    }

    fun remaining() = println("\nThe coffee machine has:\n" +
        "$water of water\n" +
        "$milk of milk\n" +
        "$beans of coffee beans\n" +
        "$cups of disposable cups\n" +
        "$$money of money")
}

fun main() {
    val coffeeMachine = CoffeeMachine(400, 540, 120, 9, 550)

    loop@do {
        print("\nWrite action (buy, fill, take, remaining, exit): ")
        try {
            when (readLine()!!) {
                "buy" -> {
                    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                    val option = readLine()!!
                    if (option == "back") continue@loop
                    coffeeMachine.buy(option.toInt())
                    continue@loop
                }
                "fill" -> {
                    print("Write how many ml of water do you want to add: ")
                    val water = readLine()!!.toInt()
                    print("Write how many ml of milk do you want to add: ")
                    val milk = readLine()!!.toInt()
                    print("Write how many grams of coffee beans do you want to add: ")
                    val beans = readLine()!!.toInt()
                    print("Write how many disposable cups of coffee do you want to add: ")
                    val cups = readLine()!!.toInt()
                    coffeeMachine.fill(water, milk, beans, cups)
                    continue@loop
                }
                "take" -> {
                    coffeeMachine.take()
                    continue@loop
                }
                "remaining" -> {
                    coffeeMachine.remaining()
                    continue@loop
                }
                "exit" -> break@loop
                else -> {
                    throw Exception("Invalid option.")
                }
            }
        } catch (e: Exception) {
            println(e.message)
            continue@loop
        }
    } while (true)

}
