package com.garmin.garminkaptain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import kotlin.random.Random

object BookShop {
    private var allBooks = BookCategory.values().toMutableList()
    private val scope = CoroutineScope(Job() + Dispatchers.Default)
    private val events = MutableSharedFlow<List<Order>>()

    init {
        handleEvents()
    }

    private fun handleEvents() {
        events
            .onEach {
                println("\nProcessing ${it.size} orders")
                handleAllOrders(it)
            }
            .flowOn(Dispatchers.Default)
            .launchIn(scope)
    }

    private suspend fun handleAllOrders(orders: List<Order>) = orders.map { order ->
        scope.async {
            println(this)
            prepareOrder(order)
        }
    }

    private fun closeBookShop() = scope.cancel()

    private suspend fun prepareOrder(order: Order) {
        println("\nStart preparing order: ${order.number}")
        val deferredResults = order.books.map {
            scope.async {
                collectBook(it)
            }
        }
        deferredResults.awaitAll()
        packOrder(order)
    }

    private suspend fun collectBook(book: BookCategory) {
        println("Start collecting ${book.name} : ${book.processingTime / 1000} sec")
        delay(book.processingTime)
        println("Finished collecting ${book.name}")
    }

    private suspend fun packOrder(order: Order) {
        delay(4000)
        println("\nOrder ${order.number} is packed and ready to be sent!")
    }

    private fun startEmittingOrders() {
        repeat(10) {
            runBlocking {
                val numberOfOrders = Random.nextInt(1, 3)
                val orders = mutableListOf<Order>()
                (1..numberOfOrders).forEach { _ ->
                    val numberOfBooks = Random.nextInt(1, 5)
                    val orderedBooks = mutableListOf<BookCategory>()
                    val books = allBooks.toMutableList()
                    books.shuffle()
                    (1..numberOfBooks).forEach { _ ->
                        orderedBooks.add(books.removeFirst())
                    }
                    orders.add(Order(UUID.randomUUID(), orderedBooks))
                }

                events.emit(orders)
                delay(10000)
            }
        }
        closeBookShop()
    }

    fun startSimulation() {
        runBlocking {
            println("BookShop Opened!")
            startEmittingOrders()
            println("BookShop Closed!")
        }
    }
}


fun main() {
    BookShop.startSimulation()
}

data class Order(val number: UUID, val books: List<BookCategory>)

enum class BookCategory {
    ADVENTURE,
    MYSTERY,
    FANTASY,
    HISTORICAL,
    ROMANCE,
    ACTION,
    TECHNICAL;

    val processingTime: Long
        get() = when (this) {
            ADVENTURE -> 6000
            MYSTERY -> 5000
            FANTASY -> 4000
            HISTORICAL -> 3000
            ROMANCE -> 2000
            ACTION -> 1000
            TECHNICAL -> 1000
        }
}