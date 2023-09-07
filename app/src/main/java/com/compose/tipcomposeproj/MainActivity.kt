package com.compose.tipcomposeproj

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.tipcomposeproj.components.InputField
import com.compose.tipcomposeproj.ui.theme.TipComposeprojTheme
import com.compose.tipcomposeproj.util.calculateTotalPerPerson
import com.compose.tipcomposeproj.util.calculateTotalTip
import com.compose.tipcomposeproj.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipComposeprojTheme {
                myApp {
                 //   TopHeader()
                    mainContent()
                }
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun mainContent(){

    val splitByState = remember {
        mutableStateOf(1)
    }
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    val range = IntRange(start = 1, endInclusive = 100)

    Column(modifier = Modifier.padding(all = 12.dp)) {
        BillForm(splitByState = splitByState,
            tipAmountState = tipAmountState,
            range = range,
            totalPerPersonState = totalPerPersonState){
        }
    }

}
//@Preview(showBackground = true)
@Composable
fun TopHeader(totalPerPerson: Double = 134.0){
    Surface (modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ){
        Column (modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total for person", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.padding(5.dp))

            Text(text = "$$total", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)

        }
    }
}

@Composable
fun myApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
       content()
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             range: IntRange = 1..100,
             splitByState: MutableState<Int>,
             tipAmountState: MutableState<Double>,
             totalPerPersonState: MutableState<Double>,
             onValChange: (String) -> Unit ={}){


    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }


    val tipProcentage = (sliderPositionState.value * 100).toInt()



    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    )
    {
        Column(modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

            InputField(valueState = totalBillState,
                labelID = "EnterBill" ,
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions

                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()

                })

            if(validState) { //
              Row (modifier = Modifier.padding(3.dp),
                  horizontalArrangement = Arrangement.Start)
              {
                    Text(text = "Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End){
                        RoundIconButton( imageVector = Icons.Default.Remove,
                            onclick = {
                                //Decrement
                                splitByState.value =
                                    if(splitByState.value > 1) splitByState.value - 1 else 1
                                totalPerPersonState.value = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercentage = tipProcentage)

                            })

                        Text(text = "${splitByState.value}", modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp))
                        RoundIconButton( imageVector = Icons.Default.Add,
                            onclick = {
                                if(splitByState.value < range.last){
                                    splitByState.value = splitByState.value +1
                                }
                                totalPerPersonState.value = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercentage = tipProcentage)
                            })
                    }

              }

                  //Tip Row
                Row (modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)){
                    Text(text = "Tip", modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    tipAmountState.value =
                        calculateTotalTip(totalBill = totalBillState.value.toDouble(), tipProcentage = tipProcentage)
                    Text(text = "$ ${tipAmountState.value}", modifier = Modifier.align(Alignment.CenterVertically))
                }
                //Tip procentege column
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$tipProcentage %")
                    Spacer(modifier = Modifier.height(14.dp))

                    //Slider

                    Slider(value = sliderPositionState.value,
                        onValueChange = { newVal ->
                        sliderPositionState.value = newVal
                            tipAmountState.value =
                                calculateTotalTip(totalBill = totalBillState.value.toDouble(), tipProcentage = tipProcentage)

                            totalPerPersonState.value = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                splitBy = splitByState.value,
                                tipPercentage = tipProcentage)

                    }, modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        //steps = 5,
                         onValueChangeFinished = {

                        })
                }


            }else{
                Box{}
            }
        }
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