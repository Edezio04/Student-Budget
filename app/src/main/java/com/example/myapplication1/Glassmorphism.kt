package com.example.myapplication1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * High-clarity Glassmorphism background with isolated ambient glowing gradient shapes.
 * Uses offscreen compositing layers so ambient light effects do NOT downsample or blur fonts.
 */
@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE0E7FF), // Soft indigo
                        Color(0xFFF3E8FF), // Soft purple
                        Color(0xFFFCE7F3)  // Soft pink
                    )
                )
            )
    ) {
        // Ambient Glowing Circle 1 (Top Right) - Isolated to offscreen layer
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 100.dp, y = (-50).dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF818CF8).copy(alpha = 0.6f),
                            Color(0xFF818CF8).copy(alpha = 0.2f),
                            Color(0xFF818CF8).copy(alpha = 0.0f)
                        )
                    )
                )
        )

        // Ambient Glowing Circle 2 (Middle Left) - Isolated to offscreen layer
        Box(
            modifier = Modifier
                .size(340.dp)
                .offset(x = (-120).dp, y = 220.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFF472B6).copy(alpha = 0.55f),
                            Color(0xFFF472B6).copy(alpha = 0.2f),
                            Color(0xFFF472B6).copy(alpha = 0.0f)
                        )
                    )
                )
        )

        // Ambient Glowing Circle 3 (Bottom Right) - Isolated to offscreen layer
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 80.dp, y = 520.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF38BDF8).copy(alpha = 0.55f),
                            Color(0xFF38BDF8).copy(alpha = 0.18f),
                            Color(0xFF38BDF8).copy(alpha = 0.0f)
                        )
                    )
                )
        )

        // Screen Content Layer - Crisp, un-blurred hardware layer
        content()
    }
}

/**
 * Frosted Glass Card with high contrast fill and sharp crisp borders.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    backgroundColor: Color = Color.White.copy(alpha = 0.78f),
    borderColor: Color = Color.White.copy(alpha = 0.95f),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.graphicsLayer {
            clip = true
            this.shape = shape
        },
        shape = shape,
        color = backgroundColor,
        border = BorderStroke(
            width = 1.25.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    borderColor,
                    borderColor.copy(alpha = 0.35f)
                )
            )
        ),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

/**
 * Dark Frosted Glass Card for high-contrast emphasis with sharp text.
 */
@Composable
fun DarkGlassCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.graphicsLayer {
            clip = true
            this.shape = shape
        },
        shape = shape,
        color = Color(0xFF0F172A).copy(alpha = 0.88f),
        border = BorderStroke(
            width = 1.25.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF818CF8).copy(alpha = 0.7f),
                    Color(0xFF38BDF8).copy(alpha = 0.25f)
                )
            )
        ),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}
