package ru.development.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import ru.development.models.*
import ru.development.network.Api
import kotlin.time.Duration.Companion.days

@Composable
fun ReadersListView() {
    val coroutineScope = rememberCoroutineScope()

    val readersList = remember { mutableStateListOf<RegisterCredential>() }

    var errorMsg: String? by remember { mutableStateOf(null) }

    ReadersListView(
        list = readersList,
        onUpdateData = {
            coroutineScope.launch {
                val res = Api.getReadersWithCredential()
                if (res.isSuccess) {
                    readersList.clear()
                    readersList.addAll(res.getOrThrow())
                } else errorMsg =
                    (res.exceptionOrNull() ?: UnknownError()).message ?: "Ошибка получения списка читателей"
            }
        }
    )

    LaunchedEffect(Unit) {
        val res = Api.getReadersWithCredential()
        if (res.isSuccess) {
            readersList.clear()
            readersList.addAll(res.getOrThrow())
        } else {
            res.exceptionOrNull()?.printStackTrace()

            errorMsg = (res.exceptionOrNull() ?: UnknownError()).message ?: "Ошибка получения списка читателей"
        }
    }

    errorMsg?.let { msg ->
        DialogWithMessage(msg, onDismissRequest = { errorMsg = null })
    }
}

@Composable
fun ReadersListView(list: List<RegisterCredential>, onUpdateData: () -> Unit) {
    var selectedItem: RegisterCredential? by remember { mutableStateOf(null) }

    var searchQuery by remember { mutableStateOf("") }

    var showAddUserDialog by remember { mutableStateOf(false) }

    var editableUser: RegisterCredential? by remember { mutableStateOf(null) }

    var errorMsg: String? by remember { mutableStateOf(null) }

    val scope = rememberCoroutineScope()

    var showUserOrdersDialog by remember { mutableStateOf(false) }

    var sortedIndex: Int? by remember { mutableStateOf(null) }

    Column(modifier = Modifier.background(Color.White).padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(20.dp))
        SearchPanel(query = searchQuery, onValueChange = { searchQuery = it })
        Spacer(Modifier.height(20.dp))

        TableRow(
            "Номер билета",
            "Фамилия",
            "Имя",
            "Отчество",
            "Адрес",
            "Телефон",
            "Серия номер паспорта",
            "Дата рождения",
            "Лимит",
            isSelected = false,
            onClickCell = { index -> sortedIndex = index }
        )

        Column(Modifier.weight(1F)) {
            list.filter { filterReaderCondition(it.user.type as Reader, searchQuery) }
                .sortByFiledIndex(sortedIndex)
                .forEach { credential ->
                    with((credential.user.type as Reader)) {
                        TableRow(
                            ticketNumber.toString(),
                            info.secondName,
                            info.firstName,
                            info.fatherName,
                            info.address,
                            info.phoneNumber,
                            passportSerialNumber,
                            info.birthday.toString(),
                            limitOfBooks.toString(),
                            isSelected = credential == selectedItem,
                            onClickRow = {
                                selectedItem = if (selectedItem == credential) null
                                else credential
                            }
                        )
                    }
                }
        }

        Spacer(Modifier.height(20.dp))

        selectedItem?.let { selected ->
            Row {
                TextButton("Редактировать", onClick = {
                    editableUser = selected
                    showAddUserDialog = true
                })
                Spacer(Modifier.width(20.dp))
                TextButton("Удалить", onClick = {
                    scope.launch {
                        val result = Api.deleteUser(selected.user.id)
                        if (result.isSuccess) {
                            selectedItem = null
                            onUpdateData()
                        } else errorMsg = "Произошла ошибка при удалении пользователя!"
                    }
                })
                Spacer(Modifier.width(20.dp))
                TextButton("Выданные книги", onClick = { showUserOrdersDialog = true })
            }
        }

        if (selectedItem == null) {
            TextButton("Создать", onClick = {
                editableUser = null
                showAddUserDialog = true
            })
        }
    }

    if (showAddUserDialog) AddUserDialog(
        onDismissRequest = {
            editableUser = null
            showAddUserDialog = false
        },
        onUpdateData = onUpdateData,
        existed = editableUser
    )

    selectedItem?.let { selected ->
        if (showUserOrdersDialog) UserOrdersDialog(
            userId = selected.user.id,
            reader = selected.user.type as Reader,
            onDismissRequest = { showUserOrdersDialog = false },
            onUpdateData = onUpdateData
        )
    }
}


@Composable
fun AddUserDialog(
    onDismissRequest: () -> Unit,
    onUpdateData: () -> Unit,
    existed: RegisterCredential?
) = Dialog(onDismissRequest = onDismissRequest) {
    val scope = rememberCoroutineScope()

    val user = existed?.user
    val type = (user?.type as? Reader)
    val info = type?.info
    val credential = existed?.login

    val scrollState = rememberScrollState()

    var secondname by remember { mutableStateOf(info?.secondName ?: "") }
    var firstname by remember { mutableStateOf(info?.firstName ?: "") }
    var fathername by remember { mutableStateOf(info?.fatherName ?: "") }
    var address by remember { mutableStateOf(info?.address ?: "") }
    var phone by remember { mutableStateOf(info?.phoneNumber ?: "") }
    var passportData by remember { mutableStateOf(type?.passportSerialNumber ?: "") }
    var limitOfBooks by remember { mutableStateOf(type?.limitOfBooks ?: 10) }
    var bithday by remember {
        mutableStateOf(
            info?.birthday ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                .run { Birthday(year, monthNumber, dayOfMonth) }
        )
    }

    var login by remember { mutableStateOf(credential?.login ?: "") }
    var password by remember { mutableStateOf(credential?.password ?: "") }

    var errorMsg: String? by remember { mutableStateOf(null) }

    Column(
        Modifier.background(Color.White, RoundedCornerShape(16.dp))
            .padding(40.dp)
            .height(500.dp)
            .verticalScroll(scrollState)
    ) {
        type?.let {

        }

        TitleWithInput(title = "Фамилия", value = secondname, onValueChange = { secondname = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Имя", value = firstname, onValueChange = { firstname = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Отчество", value = fathername, onValueChange = { fathername = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Адрес", value = address, onValueChange = { address = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Серия номер пасспорта", value = passportData, onValueChange = { passportData = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Телефон", value = phone, onValueChange = { phone = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        DataSelection(title = "Дата рождения", value = bithday, onValueChange = { bithday = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(40.dp))

        TitleWithInput(title = "Логин", value = login, onValueChange = { login = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Пароль", value = password, onValueChange = { password = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        Row {
            TextButton(
                text = "Сохранить",
                onClick = {
                    scope.launch {
                        val newReader = User(
                            id = user?.id ?: -1,
                            type = Reader(
                                info = PeopleInfo(secondname, firstname, fathername, address, phone, bithday),
                                ticketNumber = type?.ticketNumber ?: -1,
                                limitOfBooks = limitOfBooks,
                                passportSerialNumber = passportData
                            )
                        )

                        val newCreation = LoginCredential(login, password)

                        val registerCredential = RegisterCredential(newCreation, newReader)

                        val result = Api.register(registerCredential)
                        if (result.isSuccess) {
                            onUpdateData()
                            onDismissRequest()
                        } else {
                            result.exceptionOrNull()?.printStackTrace()
                            errorMsg = "Ошибка сохранения данных пользователя"
                        }
                    }
                }
            )
            Spacer(Modifier.width(20.dp))
            TextButton("Отмена", onClick = onDismissRequest)
        }
    }

    errorMsg?.let { DialogWithMessage(it) { errorMsg = null } }
}

@Composable
fun DataSelection(modifier: Modifier = Modifier, title: String, value: Birthday, onValueChange: (Birthday) -> Unit) {

    val dialogState = rememberMaterialDialogState()

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(title, modifier = Modifier.width(100.dp))

        Text(value.toString(), modifier = Modifier.width(100.dp))

        TextButton("Выбрать", onClick = { dialogState.show() })
    }

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker(LocalDate(value.year, value.month, value.day)) { date ->
            onValueChange(Birthday(date.year, date.monthNumber, date.dayOfMonth))
        }
    }
}

fun filterReaderCondition(type: Reader, query: String): Boolean {
    return type.toString().lowercase().contains(query.lowercase())
}

@Composable
fun UserOrdersDialog(
    userId: Int,
    reader: Reader,
    onDismissRequest: () -> Unit,
    onUpdateData: () -> Unit
) = Dialog(onDismissRequest = onDismissRequest) {

    val userOrders = remember { mutableStateListOf<BookOrder>() }

    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    var errorMsg: String? by remember { mutableStateOf(null) }

    var searchQuery by remember { mutableStateOf("") }


    Column(modifier = Modifier.background(Color.White).padding(40.dp)) {
        Spacer(Modifier.height(20.dp))
        Row { Text("Читатель:"); Spacer(Modifier.width(20.dp)); Text(reader.info.fullname) }
        Spacer(Modifier.height(20.dp))

        SearchPanel(searchQuery, onValueChange = { searchQuery = it })

        Spacer(Modifier.height(20.dp))


        Column(
            modifier = Modifier.weight(1F)
                .height(200.dp)
                .verticalScroll(scrollState)
        ) {
            userOrders.filter { filterOrdersCondition(searchQuery, it) }.forEach { bookOrder ->
                OrderView(
                    order = bookOrder,
                    giveUp = {
                        scope.launch {
                            val result = Api.removeOrder(bookOrder.info.id)
                            if (result.isSuccess) {
                                loadUserOrders(userId = userId, bookOrder = userOrders, onError = { errorMsg = it })
                                onUpdateData()
                            } else {
                                result.exceptionOrNull()?.printStackTrace()
                                errorMsg = "Произошла ошибка"
                            }
                        }
                    }
                )
            }

            if (userOrders.isEmpty()) {
                Text("Список книг пуст!", Modifier.align(Alignment.CenterHorizontally), fontSize = 20.sp)
            }
        }
        Spacer(Modifier.height(20.dp))
        TextButton("Назад", onClick = onDismissRequest)
    }

    errorMsg?.let { DialogWithMessage(it) { errorMsg = null } }

    LaunchedEffect(Unit) {
        loadUserOrders(userId = userId, bookOrder = userOrders, onError = { errorMsg = it })
    }
}

private suspend fun loadUserOrders(userId: Int, bookOrder: MutableList<BookOrder>, onError: (String) -> Unit) {
    val result = Api.getBookOrderList(userId)

    if (result.isSuccess) {
        val list = result.getOrThrow()
        bookOrder.clear()
        bookOrder.addAll(list)
    } else {
        result.exceptionOrNull()?.printStackTrace()
        onError("Ошибка получения списка выданных книг")
    }
}

@Composable
fun OrderView(order: BookOrder, giveUp: () -> Unit) {

    val displayTime = remember {
        val dateStartInS = order.info.dateStartInS
        val dateEndInS = order.info.dateEndInS

        val start = Instant.fromEpochSeconds(dateStartInS).toLocalDateTime(TimeZone.currentSystemDefault())
        val end = Instant.fromEpochSeconds(dateEndInS).toLocalDateTime(TimeZone.currentSystemDefault())

        "${start.year}.${start.monthNumber}.${start.dayOfMonth} - ${end.year}.${end.monthNumber}.${end.dayOfMonth}"
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = order.book.info)

        Spacer(Modifier.width(20.dp))

        Text(text = displayTime)

        Spacer(Modifier.weight(1F))

        TextButton("Сдать", onClick = giveUp)
    }
}

@Composable
fun OrderBookDialog(
    bookInfo: BookInfo,
    onDismissRequest: () -> Unit,
    onUpdateData: () -> Unit
) = Dialog(onDismissRequest = onDismissRequest) {
    val scope = rememberCoroutineScope()

    val readers = remember { mutableStateListOf<User>() }
    val scrollState = rememberScrollState()

    var selectedReader: User? by remember { mutableStateOf(null) }

    var daysInput by remember { mutableStateOf("") }

    var errorMsg: String? by remember { mutableStateOf(null) }

    Column(Modifier.background(Color.White).padding(40.dp)) {
        Column(Modifier.height(500.dp).verticalScroll(scrollState)) {
            readers.forEach { reader ->
                UserInfoView(
                    reader = reader,
                    onClick = {
                        selectedReader = if (selectedReader == reader) null
                        else reader
                    },
                    isSelected = selectedReader == reader
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Количество дней", value = daysInput, onValueChange = { daysInput = it })

        Spacer(Modifier.height(20.dp))

        Row {
            selectedReader?.let { selected ->
                TextButton("Выдать", onClick = {
                    scope.launch {
                        if (bookInfo.count <= 0) {
                            errorMsg = "Книг больше нет"
                            return@launch
                        }

                        val daysInt = daysInput.toIntOrNull()
                        if (daysInt == null) {
                            errorMsg = "Введенно некорректно количтво дней!"
                            return@launch
                        }

                        val currentInstant = Clock.System.now()
                        val nextInstant = currentInstant.plus(daysInt.days)

                        val currentSeconds = currentInstant.epochSeconds
                        val nextSeconds = nextInstant.epochSeconds

                        val orderInfo = OrderInfo(dateStartInS = currentSeconds, dateEndInS = nextSeconds)
                        val bookIfWithOrder = BookIdWithOrder(bookInfo.id, orderInfo)
                        val userOrder = UserIdToBookIdWithOrderRef(selected.id, bookIfWithOrder)

                        val result = Api.createOrder(userOrder)
                        if (result.isSuccess) {
                            onUpdateData()
                            onDismissRequest()
                        } else {
                            result.exceptionOrNull()?.printStackTrace()
                            errorMsg = "Произошла ошибка"
                        }

                    }
                })
                Spacer(Modifier.width(20.dp))
            }
            TextButton("Отмена", onClick = onDismissRequest)
        }
    }

    errorMsg?.let { DialogWithMessage(it) { errorMsg = null } }

    LaunchedEffect(Unit) {
        val result = Api.getReaders()
        if (result.isSuccess) readers.addAll(result.getOrThrow())
        else result.exceptionOrNull()?.printStackTrace()
    }
}

@Composable
fun UserInfoView(reader: User, onClick: () -> Unit, isSelected: Boolean) {
    val type = reader.type as Reader

    Row(
        modifier = Modifier
            .run { if (isSelected) background(Color.Cyan) else this }
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Text("${type.ticketNumber} ${type.info.fullname}")
        Spacer(Modifier.width(20.dp))
    }
}

@Composable
fun SearchPanel(query: String, onValueChange: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Поиск")

        Spacer(modifier = Modifier.width(20.dp))

        OutlinedTextField(
            value = query,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.weight(1F).padding(0.dp)
        )
    }
}

private fun filterOrdersCondition(query: String, bookOrder: BookOrder): Boolean {
    return bookOrder.toString().lowercase().contains(query.lowercase())
}

private fun List<RegisterCredential>.sortByFiledIndex(index: Int?): List<RegisterCredential> {
    if (index == null) return this

    return when (index) {
        0 -> sortedBy { (it.user.type as Reader).ticketNumber }
        8 -> sortedBy { (it.user.type as Reader).limitOfBooks }
        else -> sortedBy { credential ->
            val user = credential.user
            val type = user.type as Reader
            val info = type.info

            when (index) {
                1 -> info.secondName
                2 -> info.firstName
                3 -> info.fatherName
                4 -> info.address
                5 -> info.phoneNumber
                6 -> type.passportSerialNumber
                7 -> info.birthday.toString()
                else -> {
                    throw RuntimeException("Неизвестный индекс '$index' для сортировки по полям!!")
                }
            }
        }
    }
}