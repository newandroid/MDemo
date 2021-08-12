package com.css.kotlintest

import org.junit.Test

/**
 * higher-order function
 * fold (also termed reduce, accumulate, aggregate, compress, or inject)
 */
class KotlinFold {
    fun defaultParam(name: String = "css", subName: String) {
        println("name:$name subName:$subName")
    }

    fun mulParam(vararg name: String) {
        name.forEach { print("$it ") }
    }


    @Test
    fun run() {
//        defaultParam()//error
//        defaultParam("cen")//error
        //named param
        defaultParam(subName = "cen")

        mulParam("zhangsan", "lisi", "wangwu")
        println()

        infix fun Int.sh1(x: Int): Int {
            return this * x
        }

        println(5 shl 2)
    }

    @Test
    fun real() {
        val items = listOf(1, 2, 3, 4, 5)
        val fold = items.fold(0, { aac: Int, i: Int ->
            println("aac =$aac,i = $i")
            val result = aac + i
            println("result=$result")
            result
        })
        println("fold=$fold")

        val jointToString = items.fold("Elements:", { aac, i -> aac + " " + i })
        println(jointToString)

        val product = items.fold(1, Int::times)
        println(product)
    }

    @Test
    fun funtionPrograme() {
//        arrayOf(1, 2, 3).forEach(KotlinFold::mPrint)
        val oho: () -> Unit = { println("null") }
        funParent(oho)
    }

    @Test
    fun instanceFun() {
        val arrayOf = arrayOf(1, 2, 3)
        val mPrint: (Int) -> Unit = { message -> println(message) }
        arrayOf.forEach(mPrint)

        arrayOf.forEach(this::fuck)
//        arrayOf.forEach(KotlinFold::fuck)
        arrayOf.forEach {
            println(it)
        }
    }

    fun fuck(message: Int): Unit {
        println(message)
    }


    @Test
    fun funInvoke() {
        var kotlinFold = KotlinFold()
        kotlinFold.funParent(this::empty222)
    }

    fun empty222() {
        println("empty")
    }


    fun funParent(empty: () -> Unit) {
        println("funParent")
        empty()
    }
    @Test
    fun callTest(){
        call {
            println(it)
        }
    }
    fun call(callInner: (index: Int) -> Unit) {
        println("call")
        callInner(0)
    }


}