/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme

data class Puppy(
    val name: String,
    val summary: String,
    val description: String
)

val puppies = mapOf(
    "C++" to Puppy(
        "C++",
        "Quite Powerful Monster",
        "One of the most popular language. "
    ),
    "Perl" to Puppy(
        "Perl",
        "Old Pal",
        "the most fastest way to make quick hacks like as oneliner"
    ),
    "Python" to Puppy(
        "Python",
        "For Almost All Purpose",
        "Quite usable for exploratory data analysis because of Jupyter notebook"
    ),
    "Kotlin" to Puppy(
        "Kotlin",
        "Newcomer",
        "learning for the challenge just right now"
    )
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Header()
            NavHost(navController, startDestination = "itemsView") {
                composable("itemsView") { ItemsView(navController) }
                composable("detailView/{puppyKey}") {
                    backStackEntry ->
                    DetailView(navController, backStackEntry.arguments?.getString("puppyKey"))
                }
            }
        }
    }
}

@Composable
fun Header() {
    val typography = MaterialTheme.typography
    Text(
        "My Puppies",
        style = typography.h2
    )
}

@Composable
fun ItemsView(navController: NavHostController)
{
    for ((_, puppy) in puppies) {
        Item(navController, puppy)
    }
}

@Composable
fun Item(navController: NavHostController, puppy: Puppy) {
    val clickableModifier = Modifier
        .clickable(onClick = { navController.navigate("detailView/${puppy.name}") })
    val typography = MaterialTheme.typography
    Row(verticalAlignment = Alignment.CenterVertically, modifier = clickableModifier) {
        Text(puppy.name,
            fontWeight = FontWeight.Bold,
            style = typography.h2)
        Spacer(Modifier.size(16.dp))
        Column(){
            Text(puppy.summary,
                style = typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Text(puppy.description,
                style = typography.body2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun DetailView(navController: NavHostController, puppyKey: String?) {
    val puppy = puppies[puppyKey]
    val typography = MaterialTheme.typography
    Column(modifier = Modifier.padding(24.dp)) {
        Text(puppy!!.name,
            fontWeight = FontWeight.Bold,
            style = typography.h2)
        Text(puppy.summary, // assert before
            style = typography.body1)
        Text(puppy.description, // assert before
             style = typography.body2)
        Button(onClick = {
            navController.navigate("itemsView")}){
            Text("Back")
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
