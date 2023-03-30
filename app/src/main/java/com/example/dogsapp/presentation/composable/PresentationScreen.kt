package com.example.dogsapp.presentation.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import com.example.dogsapp.R
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.dogsapp.Constant
import kotlinx.coroutines.delay

@Composable
fun PresentationScreen(navController: NavController) {
    var isMessageVisible by remember { mutableStateOf(false) }
    var isAvatarVisible by remember { mutableStateOf(false) }

    var pulse by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        if (pulse) 1.1f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BoxWithConstraints {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paws_background),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .scale(3f)
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.tint_color).copy(alpha = 0.8f))

                ) {
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.Bottom

                    ) {
                        AnimatedVisibility(
                            visible = isAvatarVisible,
                            enter = slideInVertically(
                                initialOffsetY = { -it },
                                animationSpec = tween(durationMillis = 500)
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .scale(1.5f)
                            )
                        }

                        AnimatedVisibility(
                            visible = isMessageVisible,
                            enter = slideInVertically(
                                initialOffsetY = { -it },
                                animationSpec = tween(durationMillis = 500)
                            )
                        ) {
                            Surface(
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomEnd = 16.dp,
                                    bottomStart = 4.dp
                                ), color = Color.White,
                                modifier = Modifier
                                    .padding(8.dp)
                                    //.padding(top = 24.dp)
                                    .fillMaxWidth()
                                    .alpha(0.8f)

                            ) {
                                val welcomeText = stringResource(R.string.welcome_text_1)
                                val annotatedString = buildAnnotatedString {
                                    pushStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Italic
                                        )
                                    )
                                    append(welcomeText.substringBefore("."))
                                    pop()
                                    append(welcomeText.substringAfter("."))
                                }
                                Text(
                                    text = annotatedString,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(16.dp)

                                )
                            }
                        }
                    }
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.BottomCenter,
                    ) {
                        Box(
                            Modifier
                                .wrapContentHeight()
                                .background(
                                    color = colorResource(id = R.color.button_color).copy(alpha = 0.5f),
                                    shape = CircleShape
                                ),

                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .scale(scale)
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        color = colorResource(id = R.color.button_color).copy(
                                            alpha = 0.6f
                                        ),
                                        shape = CircleShape
                                    )
                                    .clickable { navController.navigate(Constant.ROUTE_IMAGE_GRID) },

                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.button_start_text).uppercase(),
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }
                        }

                        LaunchedEffect(Unit) {
                            pulse = true
                            delay(1500)
                            pulse = false
                        }
                    }

                    LaunchedEffect(true) {
                        delay(1000L)
                        isAvatarVisible = true
                        delay(50L)
                        isMessageVisible = true
                    }
                }
            }
        }
    }
}