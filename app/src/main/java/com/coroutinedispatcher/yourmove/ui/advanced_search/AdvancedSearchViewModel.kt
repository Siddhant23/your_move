package com.coroutinedispatcher.yourmove.ui.advanced_search

import androidx.lifecycle.*
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.utils.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdvancedSearchViewModel @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yuGiOhDao: YuGiOhDao
) : ViewModel() {

    private var level: Int? = null
    private var race: String? = null
    private var type: String? = null
    private var atkPoints: Int? = null
    private var deffPoints: Int? = null
    private var cardName: String? = null
    private val _cardObjectEvent = MutableLiveData<Event<String>>()
    val cardObjectEvent: LiveData<Event<String>> = _cardObjectEvent

    val levelData: LiveData<List<String>> = liveData {
        emit(listOf("Level", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
    }

    val typesLiveData: LiveData<List<String>> =
        liveData(appCoroutineDispatchers.ioDispatchers) {
            val allTypes = yuGiOhDao.selectAllTypes().toMutableList()
            allTypes.add(0, "Type")
            withContext(appCoroutineDispatchers.mainDispatcher) {
                emit(allTypes.toList())
            }
        }

    val racesLiveData: LiveData<List<String>> =
        liveData(appCoroutineDispatchers.ioDispatchers) {
            val allRaces = yuGiOhDao.selectAllRaces().toMutableList()
            allRaces.add(0, "Race")
            withContext(appCoroutineDispatchers.mainDispatcher) {
                emit(allRaces.toList())
            }
        }

    fun setLevel(level: String) {
        this.level = level.toIntOrNull()
    }

    fun setRace(race: String) {
        this.race = race
    }

    fun setType(type: String) {
        this.type = type
    }

    fun instantiateSearch(
        attack: String,
        defence: String,
        name: String
    ) {
        viewModelScope.launch(appCoroutineDispatchers.ioDispatchers) {
            this@AdvancedSearchViewModel.atkPoints = attack.toIntOrNull()
            this@AdvancedSearchViewModel.deffPoints = defence.toIntOrNull()
            this@AdvancedSearchViewModel.cardName = if (name.isEmpty()) null else name
            var selectionQuery = "SELECT * FROM yugioh_cards "
            this@AdvancedSearchViewModel.cardName?.let {
                selectionQuery += "AND name LIKE '%$it%' "
            }
            this@AdvancedSearchViewModel.atkPoints?.let {
                selectionQuery += "AND atk = $it "
            }
            this@AdvancedSearchViewModel.deffPoints?.let {
                selectionQuery += "AND def = $it "
            }
            this@AdvancedSearchViewModel.level?.let {
                selectionQuery += "AND level = $it "
            }
            this@AdvancedSearchViewModel.type?.let {
                selectionQuery += "AND type = '$it' "
            }
            this@AdvancedSearchViewModel.race?.let {
                selectionQuery += "AND race = '$it' "
            }
            val finalSelectionQuery = selectionQuery.replaceFirst("AND", "WHERE")
            _cardObjectEvent.postValue(Event(finalSelectionQuery))
        }
    }
}