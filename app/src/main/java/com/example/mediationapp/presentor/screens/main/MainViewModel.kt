package com.example.mediationapp.presentor.screens.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediationapp.domain.model.BlockElement
import com.example.mediationapp.domain.model.FeelingsElement
import com.example.mediationapp.domain.model.MeditationElement
import com.example.mediationapp.data.repository.BlockRepository
import com.example.mediationapp.data.repository.FeelingsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class MainViewModel : ViewModel() {

    private val blockRepository = BlockRepository()
    private val feelingsRepository = FeelingsRepository()

    val blockList =  MutableLiveData<List<BlockElement>>()
    val feelingsList = MutableLiveData<List<FeelingsElement>>()

    fun getList(){
        blockRepository.loadBlockItems()
        viewModelScope.launch{
            blockRepository.sharedList
                .catch { e ->
                    if (e !is IOException) throw e // rethrow all but IOException
                    Log.e("UserProfileViewModel", e.toString())
                }
                .collect{
                    blockList.postValue(it)
                }
        }
        feelingsRepository.loadFeelingItems()
        viewModelScope.launch {
            feelingsRepository.sharedList.collectLatest {
                feelingsList.postValue(it)
            }
        }
    }
    fun createBlocks(){
        for (i in 0..5){
            val block = BlockElement(
                Random().nextInt(),
                listOfTitle[i],
                listOfDescription[i],
                litOfMainText[i],
                listOfUrl[i]
            )
            blockRepository.addItem(block)
        }
    }
    fun createFeelings(){
        for (i in 0..5){
            val feelingsItem = FeelingsElement(
                Random().nextInt(),
                listOfFeelngsTitle[i],
                listOfFeelingsUrl[i]
            )
            feelingsRepository.addItem(feelingsItem)
        }
    }
    companion object{
        val listOfUrl = listOf(
            "https://www.stateofmind.it/wp-content/uploads/2020/07/Psicologo-delle-cure-primare-riflessioni-sulla-figura-e-disegno-di-legge.jpeg",
            "https://hub.allergosan.com/wp-content/uploads/2021/03/1200x1200-darm-ernaehrung-768x768-1.png",
            "https://growingapp.org/wp-content/uploads/2019/11/ottico.png",
            "https://pcdn.columbian.com/wp-content/uploads/2021/06/0615_fea_meditation.jpg",
            "https://www.manueladelgustopsicologa.it/wp-content/uploads/2016/02/illustrazione-di-concetto-di-meditazione_23-2148276160.jpg",
            "https://www.forumsalute.it/media/ckeditor/1531838332_intestino_felice.jpg",
        )
        val litOfMainText = listOf(
            "MainText", "MainText","MainText","MainText","MainText","MainText",
        )
        val listOfDescription = listOf(
        "Когнитивные искажения. Способы решения",
        "Правильное питание - как залог душевного равновесия",
        "Иследования собственного Я ",
        "Медитация. Основы для начинающих",
        "Техники медитации для успокоения",
        "Как справиться со стрессом после празников ?"
        )
        val listOfTitle = listOf(
            "Когнитивные искажения",
        "Питание",
        "Иследования",
        "Медитация",
        "Техники",
        "Праздники")
        val listOfFeelngsTitle = listOf(
            "Спокойным",
            "Расслабленным",
            "Сосредоточенным",
            "Взволнованным",
            "Воодушевленным",
            "Фрустрация",
        )
        val listOfFeelingsUrl = listOf(
            "https://anakaliantra.com.br/wp-content/uploads/2021/09/guru-2.png",
            "https://anakaliantra.com.br/wp-content/uploads/2021/09/evolucao-espiritual.png",
            "https://anakaliantra.com.br/wp-content/uploads/2021/09/moca-aprendendo.png",
            "https://anakaliantra.com.br/wp-content/uploads/2021/09/Sem-Titulo-1-3.png",
            "https://anakaliantra.com.br/wp-content/uploads/2021/09/curso-online.png",
            "https://anakaliantra.com.br/wp-content/uploads/2021/09/livros-educativos.png"
        )
    }


}