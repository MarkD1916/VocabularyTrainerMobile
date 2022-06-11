package com.example.vocabularytrainer.presentation.auth

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.ImagePainter
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.presentation.auth.components.*
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationViewModel
import com.example.vocabularytrainer.presentation.auth.registration.ValidationEvent
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.welcome.components.ImageWithSmallText
import com.example.vocabularytrainer.util.Constants.PASSWORD_REQUIRE
import com.example.vocabularytrainer.util.Constants.getCountryFlagUrl
import com.example.vocabularytrainer.util.pxToDp
import com.google.accompanist.pager.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState()
    var size by remember { mutableStateOf(Size.Zero) }
    val indicatorSetup = PagerIndicatorSetup(
        width = pxToDp(size.width),
        pageCount = pagerState.pageCount,
        indicatorPadding = 60
    ).listOfPositions

    val scope by mutableStateOf(rememberCoroutineScope())
    DisposableEffect(Unit) {
        onDispose {
            scope.cancel()
        }
    }
    val loginImageState = remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                    )
                )
                .background(MaterialTheme.colors.primary)
                .padding(
                    horizontal = 15.dp
                )
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 15.dp)
                .onGloballyPositioned { coordinates ->
                    size = coordinates.size.toSize()
                }
                .fillMaxWidth()
            ) {

                if (!indicatorSetup.isNullOrEmpty()) {
                    PagerIndicator(
                        pagerPosition = pagerState.currentPage,
                        targetPosition = pagerState.targetPage,
                        isScrollInProcess = pagerState.isScrollInProgress,
                        positionOfIndicator = indicatorSetup,
                        indicatorImageId = R.drawable.ic_baseline_local_pizza_24,
                        indicatorSize = 30.dp
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(
                        horizontal = 15.dp
                    )
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            ) {


                ImageWithSmallText(
                    imageIdFirstState = R.drawable.ic_baseline_lock_open_24,
                    imageIdSecondState = R.drawable.ic_baseline_lock_24,
                    imageState = loginImageState.value,
                    text = "Login\nand\npassword",
                    onClick = {
                        if (registrationViewModel.job?.isActive == false || registrationViewModel.job == null) {
                            registrationViewModel.job = scope.launch {
                                pagerState.animateScrollToPage(0)
                            }
                        }

                    }
                )
                ImageWithSmallText(
                    imageIdFirstState = R.drawable.ic_baseline_add_avatar,
                    text = "Profile\nand\nLanguage",
                    onClick = {
                        if (registrationViewModel.job?.isActive == false || registrationViewModel.job == null) {
                            registrationViewModel.job = scope.launch {
                                pagerState.animateScrollToPage(1)

                            }
                        }
                    }
                )

                ImageWithSmallText(
                    imageIdFirstState = R.drawable.ic_baseline_3p_24,
                    text = "\nPreview\n",
                    onClick = {
                        if (registrationViewModel.job?.isActive == false || registrationViewModel.job == null) {
                            registrationViewModel.job = scope.launch {
                                pagerState.animateScrollToPage(2)
                            }
                        }
                    }
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

        }

        PagerContent(pagerState = pagerState) {
            loginImageState.value = it
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class,
    dev.chrisbanes.snapper.ExperimentalSnapperApi::class
)
@Composable
private fun PagerContent(
    pagerState: PagerState,
    updateImage: (Boolean) -> Unit
) {
    CompositionLocalProvider(
        LocalOverScrollConfiguration provides null
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = 3,
            state = pagerState,
//            flingBehavior = ,
        ) { pager ->

            when (pager) {
                0 -> {
                    EmailPasswordSubView(updateImage)
                }
                1 -> {
                    ProfileSubView()
                }
                2 -> {
                    PreviewSubView()
                }
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EmailPasswordSubView(
    updateImage: (Boolean) -> Unit,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {


    val state = registrationViewModel.state
    val context = LocalContext.current

    val (focusRequester) = FocusRequester.createRefs()
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = context) {
        registrationViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {


                }
                is ValidationEvent.Error -> {

                }

            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                Modifier
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 50.dp)
                ) {
                    AuthTextField(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .focusRequester(focusRequester),
                        labelText = "Email address",
                        placeHolderText = "Enter your e-mail",
                        icon = Icons.Default.Email,
                        text = state.email,
                        onValueChange = {
                            registrationViewModel.onEvent(RegistrationEvent.OnEmailEnter(it))
                        },
                        isError = state.emailError != null,
                        readOnly = state.getReadOnlyValue(),
                        localFocusManager,
                        FocusDirection.Down,
                        ImeAction.Next
                    )

                    state.emailError?.let {
                        ErrorText(
                            modifier = Modifier.padding(horizontal = 30.dp),
                            errorMessage = state.emailError ?: "",
                            textColor = Color.Red
                        )
                    }


                    AuthTextField(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .focusRequester(focusRequester),
                        labelText = "Password",
                        placeHolderText = "Enter your password",
                        Icons.Default.Lock,
                        text = state.password,
                        onValueChange = {
                            registrationViewModel.onEvent(RegistrationEvent.OnPasswordEnter(it))
                        },
                        isError = state.passwordError != null,
                        readOnly = state.getReadOnlyValue(),
                        localFocusManager,
                        FocusDirection.Down,
                        ImeAction.Next
                    )

                    Row(
                        modifier = Modifier.padding(horizontal = 30.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(10.dp),
                            imageVector = Icons.Default.Info,
                            contentDescription = "error",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = PASSWORD_REQUIRE,
                            color = Color.Black,
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            )
                        )
                    }

                    state.passwordError?.let {
                        ErrorText(
                            modifier = Modifier.padding(horizontal = 30.dp),
                            errorMessage = it,
                            textColor = Color.Red
                        )
                    }
                    AuthTextField(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .focusRequester(focusRequester),
                        labelText = "Confirm Password",
                        placeHolderText = "Confirm your password",
                        Icons.Default.Lock,
                        text = state.confirmPassword,
                        onValueChange = {
                            registrationViewModel.onEvent(
                                RegistrationEvent.OnConfirmPasswordEnter(
                                    it
                                )
                            )
                        },
                        isError = state.confirmPasswordError != null,
                        readOnly = state.getReadOnlyValue(),
                        localFocusManager,
                        FocusDirection.Up,
                        ImeAction.Done
                    )
                    state.confirmPasswordError?.let {
                        ErrorText(
                            modifier = Modifier.padding(horizontal = 30.dp),
                            errorMessage = it,
                            textColor = Color.Red
                        )
                    }


                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            localFocusManager.clearFocus()
                            if (state.authResponseResult == null || state.authResponseResult is AuthResponseResult.Error) {
                                registrationViewModel.onEvent(RegistrationEvent.Submit)
                            }
                        }
                    ) {
                        when (state.authResponseResult) {
                            null -> {
                                Text(text = "Submit", color = Color.White)
                            }
                            is AuthResponseResult.Loading -> {
                                LoadAnimation()
                            }
                            is AuthResponseResult.Error -> {
                                Text(text = "Try Again", color = Color.White)
                            }
                            is AuthResponseResult.Success -> {
                                updateImage(true)
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .size(30.dp),
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "error",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    if (state.authResponseResult is AuthResponseResult.Error) {
                        ErrorText(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            errorMessage = state.authResponseResult.message,
                            textColor = Color.Red
                        )
                    }
                    if (state.authResponseResult is AuthResponseResult.Success) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "Great! Now you can optionally setup your public information!"
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class,
    coil.annotation.ExperimentalCoilApi::class
)
@Composable
private fun ProfileSubView(
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = registrationViewModel.state
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val (focusRequester) = FocusRequester.createRefs()
    val localFocusManager = LocalFocusManager.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .crossfade(true)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()
    val showImage = remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 50.dp)
            ) {
                AuthTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .focusRequester(focusRequester),
                    labelText = "First Name",
                    placeHolderText = "Enter your First Name",
                    Icons.Default.AccountBox,
                    text = state.firstName,
                    onValueChange = {
                        registrationViewModel.onEvent(
                            RegistrationEvent.OnFirstNameEnter(
                                it
                            )
                        )
                    },
                    isError = false,
                    readOnly = false,
                    localFocusManager,
                    FocusDirection.Up,
                    ImeAction.Done
                )

                AuthTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .focusRequester(focusRequester),
                    labelText = "Last Name",
                    placeHolderText = "Enter your Last Name",
                    Icons.Default.AccountBox,
                    text = state.lastName,
                    onValueChange = {
                        registrationViewModel.onEvent(
                            RegistrationEvent.OnLastNameEnter(
                                it
                            )
                        )
                    },
                    isError = false,
                    readOnly = false,
                    localFocusManager,
                    FocusDirection.Up,
                    ImeAction.Done
                )

                AuthTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .heightIn(min = 200.dp)
                        .focusRequester(focusRequester),
                    labelText = "Bio",
                    placeHolderText = "Enter your Bio",
                    Icons.Default.List,
                    text = state.bio,
                    onValueChange = {
                        registrationViewModel.onEvent(
                            RegistrationEvent.OnBioEnter(
                                it
                            )
                        )
                    },
                    isError = false,
                    readOnly = false,
                    localFocusManager,
                    FocusDirection.Up,
                    ImeAction.Done
                )

                val scope = rememberCoroutineScope()
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    visible = state.countryUrl == ""
                ) {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            localFocusManager.clearFocus()
                            scope.launch { bottomSheetState.show() }
                        }
                    ) {
                        Text(text = "Choose your Country")
                    }
                }




                CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                    val painter = rememberImagePainter(state.countryUrl)
                    AnimatedVisibility(
                        modifier = Modifier,
                        visible = if (showImage.value) {
                            when (painter.state) {
                                is ImagePainter.State.Empty,
                                is ImagePainter.State.Success,
                                -> true
                                is ImagePainter.State.Loading,
                                is ImagePainter.State.Error,
                                -> false
                            }
                        } else {
                            state.countryUrl.isNotBlank()
                        }

                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .padding(2.dp)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(MaterialTheme.colors.surface)
                                .fillMaxWidth()
                        ) {


                            Image(
                                painter = painter,
                                contentDescription = "SVG Image",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .align(Alignment.CenterVertically)
                                    .size(150.dp)
                            )


                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 10.dp),
                                onClick = {
                                    localFocusManager.clearFocus()
                                    scope.launch { bottomSheetState.show() }
                                }
                            ) {
                                Text(text = "Change")
                            }

                        }
                    }
                }


            }
        }
    }
    Box {

        val scope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(30.dp),
                    imageVector = if (bottomSheetState.currentValue == ModalBottomSheetValue.Expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "error"
                )

                Column(modifier = Modifier.heightIn(max = 500.dp)) {
                    SearchTextField(
                        text = registrationViewModel.searchRequest.value,
                        shouldHint = false,
                        onValueChange = {
                            registrationViewModel.searchRequest.value = it
                            registrationViewModel.collectCountry()
                        },
                        onSearch = {

                        },
                        focusManager = localFocusManager

                    )

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(registrationViewModel.searchResult.value.ifEmpty { registrationViewModel.countries }) {
                            CountryItem(
                                it, Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 10.dp)
                            ) {
                                localFocusManager.clearFocus()
                                showImage.value = true
                                registrationViewModel.onEvent(
                                    RegistrationEvent.OnCountryFlagSelected(
                                        getCountryFlagUrl(it)
                                    )
                                )
                                scope.launch { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden) }
                            }
                        }
                    }

                }

            }
        ) {}
    }
}

val locales: Array<String> = Locale.getISOCountries()

data class Country(
    val name: String,
    val code: String
)

fun getCountryList(): List<Country> {
    val list = arrayListOf<Country>()
    for (countryCode in locales) {
        val obj = Locale("", countryCode)
        list.add(Country(name = obj.displayCountry, code = obj.isO3Country))
    }
    return list
}

@Composable
private fun PreviewSubView(
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = registrationViewModel.state
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 50.dp)
    ) {
        Text(text = "Your private data")

        Text(text = "${state.email}")
        Text(text = "${state.password}")

        Text(text = "Your public data")

        Text(text = "${state.firstName}")
        Text(text = "${state.lastName}")
        Text(text = "${state.bio}")
        Text(text = "${state.countryUrl}")

    }
}

