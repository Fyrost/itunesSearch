package com.example.myapplication.ui.utils

import org.threeten.bp.OffsetDateTime

fun String?.largerImage(): String = this?.replace("100x100","600x600").toString()

fun String?.getYear() : String = OffsetDateTime.parse(this.toString()).year.toString()


fun Double?.priceFormat() : String = if (this != null) { if(this > 0)  "$ $this" else "FRee" } else "FREE"
