package org.d3if0123.posyanducare2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0123.posyanducare2.R
import org.d3if0123.posyanducare2.database.AnakDb
import org.d3if0123.posyanducare2.ui.theme.PosyanduCare2Theme
import org.d3if0123.posyanducare2.util.ViewModelFactory

const val KEY_ID_ANAK = "idAnak"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = AnakDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember {
        mutableStateOf("")
    }
    var usia by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("D3IF-46-01")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getAnak(id) ?: return@LaunchedEffect
        if (data != null) {
            nama = data.nama
            usia = data.usia
            gender = data.gender
        } else {
            navController.popBackStack()
        }
    }

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
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_anak))
                    else
                        Text(text = stringResource(id = R.string.edit_anak))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || usia == "" || gender == "" || usia < (0).toString() || usia > 5.toString()) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(nama, usia, gender)
                        } else {
                            viewModel.update(id, nama, usia, gender)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false })
                        {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormAnak(
            name = nama,
            onNameChange = { nama = it },
            usia = usia,
            onUsiaChange = { usia = it },
            gender = gender,
            onGenderChange = { gender = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(R.string.lainnya), tint = MaterialTheme.colorScheme.primary)
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(id = R.string.hapus)) },
            onClick = {
                expanded = false
                delete()
            }
        )
    }
}

@Composable
fun FormAnak(
    name: String, onNameChange: (String) -> Unit,
    usia: String, onUsiaChange: (String) -> Unit,
    gender: String, onGenderChange: (String) -> Unit,
    modifier: Modifier
) {
    val radioOptions = listOf(
        stringResource(id = R.string.gender1),
        stringResource(id = R.string.gender2),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) } ,
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = usia,
            onValueChange = { onUsiaChange(it) } ,
            label = { Text(text = stringResource(R.string.usia_anak)) },
            trailingIcon = { stringResource(id = R.string.satuan_tinggi) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                ClassOption(
                    label = text,
                    isSelected = gender == text,
                    onOptionSelected = { onGenderChange(it) },
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick = { onGenderChange(text) },
                            role = Role.RadioButton
                        )
                )
            }
        }
    }
}

@Composable
fun ClassOption(label: String, isSelected: Boolean, onOptionSelected: (String) -> Unit, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = { onOptionSelected(label) })
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 1.dp)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    PosyanduCare2Theme {
        DetailScreen(rememberNavController())
    }
}