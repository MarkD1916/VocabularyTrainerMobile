package com.example.vocabularytrainer.domain.home.use_case

data class HomeUseCases(
    val getAllGroup: GetAllGroup,
    val deleteGroup: DeleteGroup,
    val addGroup: AddGroup,
    val syncWords: SyncWords
)
