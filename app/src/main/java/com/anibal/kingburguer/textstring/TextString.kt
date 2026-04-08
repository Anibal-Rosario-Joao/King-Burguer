import androidx.compose.runtime.Composable

interface TextString {
    @Composable // Para Funcionar o @StringRes
    fun asString(): String
}