package com.example.replika.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Email(
    val icon: @Composable () -> Unit,
    val sender: String,
    val subject: String,
    val message: String,
    val date: String
)

val sampleEmails = listOf(
    Email(
        icon = {
            Icon(
                Icons.Filled.AccountCircle,
                null,
                tint = Color.Blue,
                modifier = Modifier.size(50.dp)
            )
        },
        sender = "LinkedIn",
        subject = "Start a conversation with your new contact",
        message = "Let's start a conversation",
        date = "28th Sept"
    ),
    Email(
        icon = {
            Icon(
                Icons.Filled.AccountCircle,
                null,
                tint = Color(226, 135, 67),
                modifier = Modifier.size(50.dp)
            )
        },
        sender = "Amazon",
        subject = "Your Amazon Order of \"Morpho\"",
        message = "Amazon.co.uk Your Orders | Your Account",
        date = "27th Sept"
    ),
    Email(
        icon = {
            Icon(
                Icons.Filled.AccountCircle,
                null,
                tint = Color(61, 220, 132),
                modifier = Modifier.size(50.dp)
            )
        },
        sender = "Kotlin Weekly",
        subject = "Kotlin Weekly #373",
        message = "Articles using Klover for Effective Code Coverage in Kotlin Projects",
        date = "24th Sept"
    )
)

@Preview(widthDp = 300, heightDp = 600)
@Composable
fun CompactScreen(
) {
    ScaffoldedGmailScreen {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldedGmailScreen(backHome: () -> Unit) {
    Scaffold(
        floatingActionButton = { ComposeNew() },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            GmailScreen(sampleEmails, backHome = backHome)
        }
    }
}


@Composable
fun GmailScreen(emails: List<Email> = sampleEmails, backHome: () -> Unit) {
    var searchItem by remember { mutableStateOf("") }
    Column {
        GmailAppBar(backHome = backHome)
        GmailSearchBar(value = searchItem, onValueChange = { searchItem = it })
        Spacer(modifier = Modifier.size(16.dp))
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "Primary",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            emails.forEach { email ->
                EmailRow(emails = email)
                Spacer(modifier = Modifier.size(16.dp))
            }
//            ComposeNew()
        }
    }
}

@Composable
fun ComposeNew() {
    ExtendedFloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.Edit, null)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = "Compose")
    }
}

@Composable
fun EmailRow(emails: Email) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        emails.icon()
        Spacer(modifier = Modifier.size(4.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = emails.sender, fontWeight = FontWeight.Bold)
                Text(
                    text = emails.date, fontSize = 12.sp, modifier = Modifier
                        .requiredHeightIn(min = 21.dp)
                        .wrapContentHeight(Alignment.Bottom)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = emails.subject,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.weight(0.5f))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = emails.message,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(2f)
                )
                Icon(Icons.Outlined.FavoriteBorder, null, modifier = Modifier.weight(0.5f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmailSearchBar(value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = value,
            placeholder = {
                Text(
                    text = "Search in emails",
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            },
            onValueChange = { onValueChange(it) },
            singleLine = true,
            modifier = Modifier.weight(6f),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(225, 236, 242, 255),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    Icons.Filled.Menu, "Menu", modifier = Modifier
                        .weight(1f)
                        .size(30.dp)
                        .clickable { /*TODO*/ }
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Filled.AccountCircle, "Account Name", modifier = Modifier
                        .weight(1f)
                        .size(30.dp)
                        .clickable { /*TODO*/ }
                )
            }
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmailAppBar(backHome: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            "Gmail Inbox", color = Color(135, 187, 70, 255),
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton({}) {
            Icon(
                Icons.Filled.Close,
                "Close",
                tint = Color(0, 102, 139),
                modifier = Modifier.clickable { backHome() })
        }
    })
}