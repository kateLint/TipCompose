package com.compose.tipcomposeproj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.tipcomposeproj.ui.theme.TipComposeprojTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipComposeprojTheme {
                myApp {
                    TopHeader()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopHeader(totalPerPerson: Double = 134.0){
    Surface (modifier = Modifier

        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(30.dp))),
        color = Color(0xFFE9D7F7)
    ){
        Column (modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total for person", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "$$totalPerPerson", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)

        }
    }
}

@Composable
fun myApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
       content()
    }
}

@Composable
fun GreetingPreview() {
    TipComposeprojTheme {
        myApp {
            Text(text = "Hello again")

        }
    }
}