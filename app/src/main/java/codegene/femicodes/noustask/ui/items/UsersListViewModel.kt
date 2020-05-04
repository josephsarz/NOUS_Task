package codegene.femicodes.noustask.ui.items

import androidx.lifecycle.ViewModel
import codegene.femicodes.noustask.repository.UsersRepository
import javax.inject.Inject

class UsersListViewModel @Inject constructor(private val repository: UsersRepository) :
    ViewModel() {

    val users by lazy {
        repository.observeItems()
    }


}