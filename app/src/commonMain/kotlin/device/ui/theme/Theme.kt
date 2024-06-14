package device.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.brew.bpl.stalk.app.generated.resources.Res
import io.github.brew.bpl.stalk.app.generated.resources.SUITE_Variable
import org.jetbrains.compose.resources.Font


val colorDark = darkColorScheme(
    primary = Color(0xFF634FA6),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFFF5F3FE)
)

val colorLight = lightColorScheme(
    primary = Color(0xFF634FA6),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFFF5F3FE)
)

val suiteFontFamily
    @Composable get() = FontFamily(Font(Res.font.SUITE_Variable))

val typography
    @Composable get() = Typography(
    bodyMedium = TextStyle(
        fontFamily = suiteFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
expect fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
)
