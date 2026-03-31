package com.anibal.kingburguer.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibal.kingburguer.R
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingTextField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    offuscate: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
){
    KingTextField(
        value = TextFieldValue(value, selection = TextRange(value.length)),
        label,
        placeholder,
        keyboardType,
        imeAction,
        offuscate,
        error,
        modifier,
        trailingIcon,
        onValueChange = {
            onValueChange(it.text)}
        )
}
@Composable
fun KingTextField(
    value: TextFieldValue,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    offuscate: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        maxLines = 1,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(label)
            )
        },
        placeholder = {
            Text(
                text = stringResource(placeholder)
            )
        },
        isError = error != null,
        supportingText = {
            error?.let{ msg ->
                Text(msg)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        trailingIcon = trailingIcon,
        visualTransformation = if (offuscate)
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun KingTextFieldPreview() {
    KingBurguerTheme {
      KingTextField(
          value = "todo",
          label = R.string.email,
          placeholder = R.string.hint_email,
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next,
          error = "Erro de Teste",
          modifier = Modifier.padding(horizontal = 20.dp)
      ) { }
    }
}