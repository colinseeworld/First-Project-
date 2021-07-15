package com.example.jsone3_categoryfood.ext

import android.widget.Toast
import com.example.jsone3_categoryfood.MainApplication
import com.google.android.material.textfield.TextInputEditText


fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MainApplication.instance, this, duration).show()
}

var String.show
    set(value) {}
    get() = run { showToast() }


fun String?.isNotNull(): Boolean {
    return this.isNullOrEmpty()
}

fun TextInputEditText.isNullHint(): Boolean{
    return if (this.text.toString().isNotEmpty()){
        true
    }else{
        "Please enter ${this.hint.toString()}".show
        false
    }

}

