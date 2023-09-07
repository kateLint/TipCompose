package com.compose.tipcomposeproj.util

fun calculateTotalTip(totalBill: Double, tipProcentage: Int): Double {
    return if(totalBill > 1 && totalBill.toString().isNotEmpty())
        (totalBill * tipProcentage)/100 else 0.0
}

fun  calculateTotalPerPerson(totalBill: Double, splitBy: Int,
                             tipPercentage: Int):Double{
    val bill = calculateTotalTip(totalBill  =totalBill, tipProcentage = tipPercentage) + totalBill

    return (bill/splitBy)
}