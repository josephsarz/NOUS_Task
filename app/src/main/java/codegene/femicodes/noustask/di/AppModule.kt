package codegene.femicodes.noustask.di

import androidx.lifecycle.ViewModel
import codegene.femicodes.noustask.ui.items.UsersListFragment
import codegene.femicodes.noustask.ui.items.UsersListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class AppModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun personFragment(): UsersListFragment

    @Binds
    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    abstract fun bindsItemsViewModel(usersListViewModel: UsersListViewModel): ViewModel

}