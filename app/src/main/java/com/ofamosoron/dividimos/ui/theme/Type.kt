package com.ofamosoron.dividimos.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ofamosoron.dividimos.R

val montserratFamily = FontFamily(
    Font(R.font.monserrat, FontWeight.Normal),
    Font(R.font.monserrat, FontWeight.Light),
    Font(R.font.monserrat, FontWeight.Medium),
    Font(R.font.monserrat, FontWeight.SemiBold),
    Font(R.font.monserrat, FontWeight.Bold),
)

val firaSansFamily = FontFamily(
    Font(R.font.fira_sans, FontWeight.Normal),
    Font(R.font.fira_sans, FontWeight.Light),
    Font(R.font.fira_sans, FontWeight.Medium),
    Font(R.font.fira_sans, FontWeight.SemiBold),
    Font(R.font.fira_sans, FontWeight.Bold),
)

val barlowFamily = FontFamily(
    Font(R.font.barlow, FontWeight.Normal),
    Font(R.font.barlow, FontWeight.Light),
    Font(R.font.barlow, FontWeight.Medium),
    Font(R.font.barlow, FontWeight.SemiBold),
    Font(R.font.barlow, FontWeight.Bold),
)


val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)