package com.compose.tipcomposeproj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.compose.tipcomposeproj.ui.theme.TipComposeprojTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipComposeprojTheme {
                myApp {
                    Text(text = "Hello again")
                }
            }
        }
    }
}

@Composable
fun myApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {

    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipComposeprojTheme {
        myApp {
            Text(text = "Hello again")

        }
    }
}