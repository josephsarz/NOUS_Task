package codegene.femicodes.noustask.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import codegene.femicodes.noustask.R
import codegene.femicodes.noustask.domain.AppResult
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UsersListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val usersListViewModel: UsersListViewModel by viewModels { viewModelFactory }

    private val filterListAdapter = ItemListAdapter { item, imageView ->
        val extras = FragmentNavigatorExtras(
            imageView to "artwork"
        )
        val action = UsersListFragmentDirections.actionItemFragmentToItemDetailsFragment(item)
        findNavController().navigate(action, extras)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = filterListAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        usersListViewModel.users.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                AppResult.Status.SUCCESS -> {
                    filterListAdapter.submitList(result?.data)
                    progress.visibility = View.GONE
                }
                AppResult.Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
                AppResult.Status.ERROR -> {
                    progress.visibility = View.GONE
                    Snackbar.make(parentLayout, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}