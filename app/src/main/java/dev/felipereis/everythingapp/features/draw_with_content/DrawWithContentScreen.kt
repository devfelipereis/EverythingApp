package dev.felipereis.everythingapp.features.draw_with_content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

@Composable
fun DrawWithContentScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .drawWithContent {
                    // Draw a blue circle before the content
                    drawCircle(
                        color = Color.Blue,
                        radius = 100f,
                        center = Offset(20f, 10f)
                    )
                    // Draw the original content
                    drawContent()
                    // Draw a red border after the content
                    drawRect(
                        topLeft = Offset(30f, 40f),
                        color = Color.Red,
                        size = Size(height = 60f, width = size.width),
                        style = Fill,
                    )
                }
        ) {
            Text("Draw with content")
        }
    }
}