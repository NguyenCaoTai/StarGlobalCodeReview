package com.example.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.ui.favoriteuser.FavoriteUserActivity
import com.example.myapplication.ui.main.utilities.InjectorUtils
import com.example.myapplication.ui.main.utilities.SwipeLeftRightGestureListener

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var dataBinding: ViewDataBinding

    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(
            requireContext(),
            SwipeLeftRightGestureListener(
                onSwipeLeft = { viewModel.getRandomUser() },
                onSwipeRight = { viewModel.addCurrentUserToFavorite() }
            )
        )
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            InjectorUtils.provideMainViewModelFactory(
                requireActivity().application
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.main_fragment,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding
            .apply {
                setVariable(BR.userInfo, viewModel)
                setVariable(
                    BR.handler,
                    object : UserInfoHandler {
                        override fun execute(type: UserInfoType) {
                            viewModel.onUserInfoAction(type)
                        }
                    }
                )
            }

        view.findViewById<View>(R.id.viewUserCard)
            .setOnTouchListener { p0, motionEvent -> gestureDetector.onTouchEvent(motionEvent) }

        view.findViewById<View>(R.id.btnFavoriteUser)
            .setOnClickListener {
                startActivity(
                    Intent(requireActivity(), FavoriteUserActivity::class.java)
                )
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getRandomUser()

        viewModel.messageInfo
            .observe(
                viewLifecycleOwner,
                Observer { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
            )
    }
}