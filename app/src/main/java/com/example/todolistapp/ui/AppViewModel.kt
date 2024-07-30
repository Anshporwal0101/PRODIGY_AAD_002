package com.example.todolistapp.ui

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.ToDoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AppViewModel(app : Application): AndroidViewModel(app)
{
    private val _newitems = MutableStateFlow<List<ToDoItem>>(emptyList())
    val newItems : StateFlow<List<ToDoItem>> get() = _newitems.asStateFlow()

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "Items")

    private val context = app.applicationContext
    private val saveItemsKey = stringPreferencesKey("Save_Items")

    private val _popUp = MutableStateFlow<Boolean>(false)
    val popUp : StateFlow<Boolean> get() = _popUp.asStateFlow()

    private val _itemTitle = MutableStateFlow<String>("")
    val itemTitle : StateFlow<String> get() = _itemTitle.asStateFlow()

    private val _menuEnabled = MutableStateFlow<Boolean>(false)
    val menuEnabled : StateFlow<Boolean> get() = _menuEnabled.asStateFlow()

    private val _itemContent = MutableStateFlow<String>("")
    val itemContent : StateFlow<String> get() = _itemContent.asStateFlow()

    private suspend fun saveItemToDataStore()
    {
        context.dataStore.edit { preferences ->
            preferences[saveItemsKey] = Json.encodeToString(_newitems.value)
        }
    }

    private suspend fun loadSaveItemFromDataStore()
    {
        val fullData = context.dataStore.data.first()
        val saveItemJson = fullData[saveItemsKey]
        if(saveItemJson.isNullOrEmpty())
        {
            _newitems.value = saveItemJson?.let { Json.decodeFromString(it) }!!
        }
    }

    fun addItem(newItem : ToDoItem)
    {
        _newitems.value = _newitems.value + newItem
        viewModelScope.launch {
            saveItemToDataStore()
        }
    }
    fun removeItem(newitem : ToDoItem)
    {
        _newitems.value = _newitems.value - newitem
        viewModelScope.launch {
            saveItemToDataStore()
        }
    }

    fun showPopUp(value : Boolean)
    {
        _popUp.value = value
    }

    fun setTitleName(value : String)
    {
        _itemTitle.value = value
    }

    fun setMenuEnabled(value : Boolean)
    {
        _menuEnabled.value = value
    }

    fun setItemContent(value : String)
    {
        _itemContent.value = value
    }

    fun getSaveItem()
    {
        viewModelScope.launch {
            try {
                loadSaveItemFromDataStore()
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        }
    }

    init {
        getSaveItem()
    }
}