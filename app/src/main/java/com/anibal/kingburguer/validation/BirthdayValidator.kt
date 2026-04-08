package com.anibal.kingburguer.validation

import TextString
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirthdayValidator : Validator() {
    private val pattern = "##/##/####"

    var result: String = ""
        private set

    override fun validate(input1: String, input2: String): TextString? {
        val currentBirthday = input1
        val birthday = input2
        result = Mask(pattern, currentBirthday, birthday)

        if (result.isBlank()) {
            return ResourceString(R.string.error_field_blank)
        }

        // valida se está completo no formato da máscara
        if (result.length != pattern.length) {
            return ResourceString(R.string.birthday_invalid)
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
            isLenient = false
        }

        return try {
            val date = sdf.parse(result) ?: return ResourceString(R.string.birthday_invalid)

            val now = Date()
            if (date.after(now)) {
                ResourceString(R.string.birthday_future)
            } else {
                null
            }
        } catch (e: ParseException) {
            ResourceString(R.string.birthday_invalid)
        }
    }
}