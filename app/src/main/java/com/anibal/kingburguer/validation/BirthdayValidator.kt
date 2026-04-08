
package com.anibal.kingburguer.validation

import TextString
import android.icu.text.SimpleDateFormat
import androidx.core.net.ParseException
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString
import java.util.Date
import java.util.Locale

class BirthdayValidator: Validator2 {
    override fun validate(pattern: String, current: String, result: String): TextString? {
        if (result.isBlank()){
            return ResourceString(R.string.error_field_blank)
        }
        // o numero precisa ser igual da mascara = invalida
        if(result.length != pattern.length){
            return ResourceString(R.string.birthday_invalid)
        }

        //Não validar a data 30/02/2000
        try {
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run {
                isLenient = false //  Não deixa pegar datas aproximada ou por aproximação
                parse(result)
            }?.also {
                //Não validar a data futura
                val now = Date()
                if (it.after(now)){
                    return ResourceString(R.string.birthday_future)
                }
            }
           // formState = formState.copy(
           //     birthday = FieldState(field = result, error = null)
          //  )

        }catch (e: ParseException){
            //Não validar a data 30/02/2000
            return ResourceString(R.string.birthday_invalid)
        }
        return null
    }
}