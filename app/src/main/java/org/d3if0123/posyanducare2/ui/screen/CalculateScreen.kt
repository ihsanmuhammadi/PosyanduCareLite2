package org.d3if0123.posyanducare2.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3if0123.posyanducare2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.hitung_aplikasi))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@SuppressLint("StringFormatInvalid")
@Composable
fun ScreenContent(modifier: Modifier) {
    var tinggiBadan by rememberSaveable {
        mutableStateOf("")
    }
    var tinggiBadanError by rememberSaveable {
        mutableStateOf(false)
    }

    var usiaAnak by rememberSaveable {
        mutableStateOf("")
    }
    var usiaAnakError by rememberSaveable {
        mutableStateOf(false)
    }

    val radioOptions = listOf(
        stringResource(id = R.string.gender1),
        stringResource(id = R.string.gender2)
    )
    var gender by rememberSaveable {
        mutableStateOf(radioOptions[0])
    }

    var kategori by rememberSaveable {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tinggiBadan,
            onValueChange = { tinggiBadan = it },
            label = { Text(text = stringResource(id = R.string.tb_anak)) },
            isError = tinggiBadanError,
            trailingIcon = { IconPicker(tinggiBadanError, stringResource(id = R.string.satuan_tinggi)) },
            supportingText = { ErrorHint(tinggiBadanError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = usiaAnak,
            onValueChange = { usiaAnak = it },
            label = { Text(text = stringResource(id = R.string.usia_anak)) },
            isError = usiaAnakError,
            trailingIcon = { IconPicker(usiaAnakError, stringResource(id = R.string.satuan_waktu)) },
            supportingText = { ErrorHint(usiaAnakError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = gender == text,
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick = { gender = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Row {
            Button(
                onClick = {
                    tinggiBadan = ""
                    tinggiBadanError = false
                    usiaAnak = ""
                    usiaAnakError = false
                    kategori = 0
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    text = stringResource(R.string.reset),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    tinggiBadanError = (tinggiBadan == "" || tinggiBadan == "0" || tinggiBadan.contains(","))
                    usiaAnakError = (usiaAnak == "" || usiaAnak > "5" || usiaAnak.contains(".") || usiaAnak.contains(","))
                    if (tinggiBadanError || usiaAnakError) return@Button

                    kategori = getKategori(tinggiBadan.toFloat(), usiaAnak.toInt(), gender == radioOptions[0])
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    text = stringResource(id = R.string.hitung),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        if (kategori != 0) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(id = R.string.intro_hasil),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(kategori).uppercase(),
                style = MaterialTheme.typography.headlineMedium
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template, tinggiBadan, usiaAnak, gender, context.getString(kategori).uppercase())
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(text = stringResource(R.string.bagikan), color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

private fun getKategori(tb: Float, age: Int, isMale: Boolean): Int {
    return if (isMale) {
        if (age == 0) {
            when {
                tb < 46.1 -> R.string.pendek
                tb > 55.6 -> R.string.tinggi
                else -> R.string.normal
            }
        } else if (age == 1) {
            when {
                tb < 71.0 -> R.string.pendek
                tb > 82.9 -> R.string.tinggi
                else -> R.string.normal
            }
        } else if (age == 2) {
            when {
                tb < 81.7 -> R.string.pendek
                tb > 97.0 -> R.string.tinggi
                else -> R.string.normal
            }
        } else if (age == 3) {
            when {
                tb < 88.7 -> R.string.pendek
                tb > 107.2 -> R.string.tinggi
                else -> R.string.normal
            }
        } else if (age == 4) {
            when {
                tb < 94.9 -> R.string.pendek
                tb > 115.9 -> R.string.tinggi
                else -> R.string.normal
            }
        }
        else {
            when {
                tb < 100.7 -> R.string.pendek
                tb > 123.9 -> R.string.tinggi
                else -> R.string.normal
            }
        }
    } else {
        if (age == 0) {
            when {
                tb < 45.4 -> R.string.pendek
                tb > 54.7 -> R.string.tinggi
                else -> R.string.normal
            }
        } else if (age == 1) {
            when {
                tb < 68.9 -> R.string.pendek
                tb > 81.7 -> R.string.tinggi
                else -> R.string.normal
            }
        } else if (age == 2) {
            when {
                tb < 80.0 -> R.string.pendek
                tb > 96.1 -> R.string.tinggi
                else -> R.string.normal
            }
        }
        else if (age == 3) {
            when {
                tb < 87.4 -> R.string.pendek
                tb > 106.5 -> R.string.tinggi
                else -> R.string.normal
            }
        }
        else if (age == 4) {
            when {
                tb < 94.1 -> R.string.pendek
                tb > 115.7 -> R.string.tinggi
                else -> R.string.normal
            }
        }
        else {
            when {
                tb < 99.9 -> R.string.pendek
                tb > 123.7 -> R.string.tinggi
                else -> R.string.normal
            }
        }
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.invalid))
    }
}