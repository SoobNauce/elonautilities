package rift.elonautilities

import java.math.BigInteger
//import java.util.*



//private val rgen: Random = java.util.Random()

//private fun rnd(maxVal: Int): Int = rgen.nextInt(maxVal)

fun main(args: Array<String>){
    // setup
    //val nTrials: Long = 10000
    val input = java.util.Scanner(System.`in`)

    // brute force chance of success
    print("Literacy >>")
    val literacy = input.nextInt()

    print("Attribute >>")
    val attribute = input.nextInt()

    print("Difficulty >>")
    val difficulty = input.nextInt()

    val r1: Long = literacy.toLong() * attribute.toLong() * 4L + 250L
    val r2: Long = difficulty.toLong() + 1L

    var successes: BigInteger = BigInteger.valueOf(0)
    var trials: BigInteger = BigInteger.valueOf(0)

    var i = 0L
    var j: Long

    while(i < r1){

        j = 0
        while(j < r2){
            // z = i - j
            successes += if(i > j) BigInteger.ONE else BigInteger.ZERO
            trials++

            j++
        }
        i++
    }

    print("""For literacy($literacy), attribute($attribute), difficulty($difficulty)
            |   r1($r1), r2($r2), success rate $successes/$trials = ${successes / trials}""".trimMargin())
}