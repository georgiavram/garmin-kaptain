package com.garmin.kotlinbasics

interface CardOperations {
    fun pay(amount: Int): Boolean
    fun withdraw(amount: Int): Boolean
    fun deposit(amount: Int)
    fun checkBalance()
}

data class CreditCard(var balance: Double, val limit: Int) : CardOperations {
    private fun applyPaymentBonus(amount: Int) {
        balance += amount * BONUS
    }

    override fun pay(amount: Int): Boolean {
        return if (balance - amount < -limit) {
            false
        } else {
            balance -= amount
            applyPaymentBonus(amount)
            true
        }
    }

    override fun withdraw(amount: Int): Boolean {
        return if (balance - amount < -limit) {
            false
        } else {
            balance -= amount
            true
        }
    }

    override fun deposit(amount: Int) {
        balance += amount
    }

    override fun checkBalance() {
        println("Credit card status: $balance RON")
    }

    companion object {
        private const val BONUS = 0.1
    }
}

data class DebitCard(var balance: Double) : CardOperations {
    private fun spendMoney(amount: Int): Boolean {
        return if (balance - amount < 0) {
            false
        } else {
            balance -= amount
            true
        }
    }

    override fun pay(amount: Int): Boolean = spendMoney(amount)

    override fun withdraw(amount: Int): Boolean = spendMoney(amount)

    override fun deposit(amount: Int) {
        balance += amount
    }

    override fun checkBalance() {
        println("Debit card status: $balance RON")
    }
}

fun main() {
    val debitCard = DebitCard(balance = 355.5)
    val creditCard = CreditCard(balance = 100.0, limit = 1000)

    debitCard.withdraw(100)
    debitCard.checkBalance()
    debitCard.pay(50)
    debitCard.checkBalance()

    creditCard.pay(10)
    creditCard.checkBalance()
    creditCard.pay(500)
    creditCard.checkBalance()
    creditCard.pay(500)
    creditCard.checkBalance()
}