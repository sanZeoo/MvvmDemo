package com.sumjin.mvvmdemokt.api

import java.lang.Exception
import java.lang.RuntimeException

data class ApiException(val code:Int, override val message: String?):RuntimeException() {
}