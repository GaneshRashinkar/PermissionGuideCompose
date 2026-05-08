package com.ganeshrashinkar.permissionsguidecompose

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>("")

    fun dismissDialog(){
        visiblePermissionDialogQueue.removeAt(0)
    }

    fun onPermissionResult(
        permission:String,
        isGranded: Boolean
    )
    {
        if(!isGranded && !visiblePermissionDialogQueue.contains(permission)){
            visiblePermissionDialogQueue.add(permission)
        }
    }
}