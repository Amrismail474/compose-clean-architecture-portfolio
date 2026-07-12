package com.example.uisamples.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SampleEventCard(
    val title: String,
    val membersCount: String
)

@Composable
fun RegisteredEventOnHome(
    eventCard: SampleEventCard,
    onRegisterClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.88.dp, Color(0xFFE4E4E7), RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(146.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE4E4E7))
        ) {
      
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(24.dp),
                tint = Color.Gray
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = eventCard.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "A calm space to share your thoughts and learn simple coping habits.",
                    fontSize = 11.sp,
                    color = Color(0xFF71717B),
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        tint = Color(0xFFBC6C25),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(text = "24th Jan", color = Color(0xFF71717B), fontSize = 10.sp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        tint = Color(0xFFBC6C25),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(text = "7:00 PM", color = Color(0xFF71717B), fontSize = 10.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.width(48.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .padding(start = (index * 12).dp)
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE4E4E7))
                                .border(1.5.dp, Color.White, CircleShape)
                        )
                    }
                }
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${eventCard.membersCount} Registered already.",
                    color = Color(0xFF71717B),
                    fontSize = 10.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(12.dp))
                    .border(0.5.dp, Color(0xFFFFEDD5), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Registered",
                    color = Color(0xFFBC6C25),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventCard() {
    Box(Modifier.padding(16.dp)) {
        RegisteredEventOnHome(
            eventCard = SampleEventCard("Billie Eilish Concert", "12k+")
        )
    }
}
