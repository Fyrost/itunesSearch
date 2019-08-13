package com.example.myapplication.ui.utils


import org.threeten.bp.OffsetDateTime


fun String?.largerImage(int: Int): String = this?.replace("100x100","${int}x$int").toString()

fun String?.getYear() : String = OffsetDateTime.parse(this.toString()).year.toString()

fun Double?.priceFormat() : String = if (this != null) { if(this > 0)  "$ $this" else "FRee" } else "FREE"
