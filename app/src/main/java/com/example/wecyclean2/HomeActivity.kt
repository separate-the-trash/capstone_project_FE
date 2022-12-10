package com.example.wecyclean2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.ComponentActivity.*
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.*
import com.example.wecyclean2.ui.theme.Wecyclean2Theme
import kotlinx.coroutines.launch
import java.util.*


//@Composable
//fun Home (navController: NavController,uid:String){
//    Column(
//        Modifier
//            .fillMaxSize()
//            .background(c_main_green),
//    ) {
//        Column {
//            Text(text = "username님 , 안녕하세요",color= Color.White, fontFamily = notosanskr , fontWeight = FontWeight.Bold, fontSize = 30.sp)
//        }
//    }
//
//}




@OptIn(ExperimentalMaterialApi::class)
@Composable

fun Home(navController: NavController,markerList: MutableList<MarkerModel>,alarmManager:AlarmManager,intent:Intent,context: Context) {

    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    var searchState by remember { mutableStateOf("")}
    val scope = rememberCoroutineScope()
    BottomDrawer(
        drawerShape = RoundedCornerShape(50.dp,50.dp,0.dp,0.dp),
        gesturesEnabled = when {drawerState.isClosed==true -> false else -> true},
        drawerState = drawerState,
        drawerContent = {
            // 드로어 내용
            drawbleContent(alarmManager,intent,context)
        }
    ) {
        // 화면 내용
        Column(
            Modifier
                .fillMaxSize()
                .background(c_main_green),
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(20.dp),verticalArrangement = Arrangement.SpaceBetween){

                Column(Modifier.weight(0.1f)){
                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = uid+"님 , 안녕하세요",
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            ),
                            color= Color.White,
                            fontFamily = notosanskr ,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        IconButton(onClick = {scope.launch { drawerState.open()}}) {
                            Icon(
                                painter = painterResource(id = R.drawable.bell),
                                modifier = Modifier.size(30.dp),
                                tint = Color.White,
                                contentDescription = null

                            )
                        }
                    }

//                    Text(
//                        text = "오늘도 좋은 하루 되세요.",
//                        style = TextStyle(
//                            platformStyle = PlatformTextStyle(
//                                includeFontPadding = false
//                            )
//                        ),
//                        color= Color.White,
//                        fontFamily = notosanskr ,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 18.sp
//                    )
                }

//                Column(
//                    modifier = Modifier
//                        .weight(0.3f)
//                        .padding(horizontal = 20.dp, vertical = 10.dp),
//                    verticalArrangement = Arrangement.Bottom
//                ) {
//                    AutoComplete()
//
////                    SimpleTextField(
////                        placeholderText = "분리수거 할 물건을 입력해보세요",
////                        textAlign = Alignment.CenterVertically,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .weight(0.5f)
////
////                            .padding(horizontal = 10.dp)
////                            .background(Color.White,shape = RoundedCornerShape(4.dp))
////                                ,
////                        value = searchState,
////                        textStyle = TextStyle.Default.copy(fontFamily = notosanskr, fontWeight = FontWeight.Normal ,fontSize = 18.sp),
////                        onValueChange = {
////                            searchState = it
////                        },
////                        singleLine = true,
////                        trailingIcon = {
////                        }
////                    )
//                }
                Column(Modifier.weight(0.9f)) {
                    AutoComplete(navController)

                    Spacer(modifier = Modifier.weight(0.15f))


                    Button (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .shadow(8.dp, RoundedCornerShape(8.dp)),
                        onClick = {getMap(markerList,navController)} ,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    )   {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.map_icon),
                                contentDescription ="",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "내 주변의 쓰레기통 찾기",
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                ),
                                color= Color.Black,
                                fontFamily = notosanskr ,
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "지도로 내 근처 쓰레기통을 찾아보세요",
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                ),
                                color= c_main_gray,
                                fontFamily = notosanskr ,
                                fontWeight = FontWeight.Thin,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.15f))


                    Button (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .shadow(8.dp, RoundedCornerShape(8.dp)),
                        onClick = {navController.navigate("Camera")} ,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    )   {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.camera_icon),
                                contentDescription ="",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "카메라로 분류하기",
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                ),
                                color= Color.Black,
                                fontFamily = notosanskr ,
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "쓰레기 분류 방법을 알려드려요",
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                ),
                                color= c_main_gray,
                                fontFamily = notosanskr ,
                                fontWeight = FontWeight.Thin,
                                fontSize = 14.sp
                            )

                        }
                    }

                    Spacer(modifier = Modifier.weight(0.15f))

                    Button (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .shadow(8.dp, RoundedCornerShape(8.dp)),
                        onClick = {navController.navigate("Community")} ,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    )   {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.board_icon),
                                contentDescription ="",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.width(20.dp))
                            Column() {
                                Text(
                                    text = "게시판",
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    ),
                                    color= Color.Black,
                                    fontFamily = notosanskr ,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "분리수거 정보를 공유해보세요",
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    ),
                                    color= c_main_gray,
                                    fontFamily = notosanskr ,
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.15f))

                    Button (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .shadow(8.dp, RoundedCornerShape(8.dp)),
                        onClick = {navController.navigate("Reward")} ,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    )   {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.coin_icon),
                                contentDescription ="",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.width(20.dp))
                            Column() {
                                Text(
                                    text = "리워드",
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    ),
                                    color= Color.Black,
                                    fontFamily = notosanskr ,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "포인트를 교환해보세요",
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    ),
                                    color= c_main_gray,
                                    fontFamily = notosanskr ,
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

            }
        }
    }

}


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun drawbleContent(alarmManager: AlarmManager,intent: Intent,context2:Context) {

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(Color.White),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.5f)
                    .height(5.dp)
                    .background(c_light_gray)
            ){}
            Text(
                modifier = Modifier.padding(0.dp,10.dp,0.dp,10.dp),
                text = "나의 분리수거 날짜",
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = Color.Black,
                fontFamily = notosanskr,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                recycleDay.forEachIndexed() { index, day ->
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {

                            recycleDayCheck[index] = !recycleDayCheck[index]
                            recyclePushCheck.value = false
                            //Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show()
                        },
                        text = day,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        color = if(recycleDayCheck[index]) c_main_green else c_main_gray,
                        fontFamily = notosanskr,
                        fontWeight = FontWeight.Normal,
                        fontSize = 45.sp
                    )
                }

            }
            ShowTimePicker()
            Divider(Modifier.padding(0.dp, 10.dp,0.dp,10.dp),
                color = c_main_gray, thickness = 1.dp)

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.padding(0.dp,0.dp,0.dp,0.dp),
                    text = "분리수거 푸시 알림",
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = Color.Black,
                    fontFamily = notosanskr,
                    fontWeight = FontWeight.Thin,
                    fontSize = 14.sp
                )
                Switch(checked = recyclePushCheck.value, onCheckedChange = {
                    recyclePushCheck.value = it
                    if(it == true) {
                        setAlarms(alarmManager,context2,intent)
                        Toast.makeText(context, "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }
}

@Composable
fun ShowTimePicker() {

    FullHoursNumberPicker(
        modifier= Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        dividersColor = c_main_gray,
        value = pickerValue.value,
        onValueChange = {
            pickerValue.value= it as FullHours
            recyclePushCheck.value = false
        },
        hoursDivider = {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                text = "시"
            )
        },
        minutesDivider = {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                text = "분"
            )
        },
        hoursRange = 0..23

    )

}


@Composable
fun AutoComplete(navController: NavController) {

    val categories = listOf(
        "나무젓가락",
        "칫솔",
        "기저귀",
        "테이프",
        "고무장갑",
        "컵라면용기",
        "라텍스",
        "식칼",
        "소주병",
        "맥주병",
        "스티로폼",
        "후라이팬",
    )

    var category by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }


//    // Category Field
//    Column(
//        modifier = Modifier
//            .padding(30.dp)
//            .fillMaxWidth()
//            .clickable(
//                interactionSource = interactionSource,
//                indication = null,
//                onClick = {
//                    expanded = false
//                }
//            )
//    ) {

        Column(modifier = Modifier.fillMaxWidth().clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {
//                expanded = false
            }
        )) {

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .shadow(8.dp,RoundedCornerShape(15.dp))
                        .border(
                            width = 1.8.dp,
                            color = c_sub_green,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .background(Color.White,shape = RoundedCornerShape(15.dp))

                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = category,
                    onValueChange = {
                        category = it
                        expanded = true
                    },
                    placeholder  ={Text("분리수거 할 물건을 검색해보세요.")},
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if(categories.contains(category)){
                                navController.navigate("Search/${category}")
                            }

                        }),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }
            // on below line we are creating a box to display box.

            AnimatedVisibility(visible = expanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
                        elevation = 15.dp,
                        shape = RoundedCornerShape(0.dp,0.dp,10.dp,10.dp)
                    ) {

                        LazyColumn(
                            modifier = Modifier.heightIn(min=150.dp,max = 150.dp),
                        ) {

                            if (category.isNotEmpty()) {
                                items(
                                    categories.filter {
                                        it.lowercase()
                                            .contains(category.lowercase()) || it.lowercase()
                                            .contains("기타")
                                    }
                                        .sorted()
                                ) {
                                    CategoryItems(title = it) { title ->
                                        category = title
                                        expanded = false
                                    }
                                }
                            } else {
                                items(
                                    categories.sorted()
                                ) {
                                    CategoryItems(title = it) { title ->
                                        category = title
                                        expanded = false
                                    }
                                }
                            }

                        }

                    }


            }

        }

    }

@Composable
fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }

}

// 알람을 갱신합니다.
fun setAlarms(alarmManager: AlarmManager,context:Context,intent: Intent) {

    Log.e("",pickerValue.value.hours.toString())
    Log.e("",pickerValue.value.minutes.toString())

    for(i in 0..4) {
        if(recycleDayCheck[i] == true){
            Log.e("",i.toString()+"set alarm")
            val pendingIntent = PendingIntent.getBroadcast(
                context, i, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA).apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.DAY_OF_WEEK,i+2)
                set(Calendar.HOUR_OF_DAY,pickerValue.value.hours)
                set(Calendar.MINUTE,pickerValue.value.minutes)
                set(Calendar.SECOND,0)
            }
            Log.e("",calendar.time.toString())
//            alarmManager.set(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 1000 * 5,
//                pendingIntent)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
//                AlarmManager.INTERVAL_DAY,
                pendingIntent)
        }
    }
}