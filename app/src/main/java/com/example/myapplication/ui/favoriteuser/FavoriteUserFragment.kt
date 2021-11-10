package com.example.myapplication.ui.favoriteuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Either
import com.example.myapplication.ui.main.utilities.InjectorUtils

class FavoriteUserFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteUserFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private val viewAdapter: FavoriteUserAdapter by lazy {
        FavoriteUserAdapter(mutableListOf())
    }

    private val viewModel: FavoriteUserViewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            InjectorUtils.provideFavoriteViewModelFactory(
                requireActivity().application
            )
        ).get(FavoriteUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favorite_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.favoriteUser
            .observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        is Either.Left -> Toast.makeText(
                            requireContext(),
                            it.left,
                            Toast.LENGTH_LONG
                        ).show()
                        is Either.Right -> it.right?.let { it1 -> viewAdapter.submitList(it1) }
                    }
                }
            )
    }
}