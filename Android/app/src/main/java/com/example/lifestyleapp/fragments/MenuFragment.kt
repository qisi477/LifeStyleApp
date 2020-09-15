package com.example.lifestyleapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.UserDataModel
import kotlinx.android.synthetic.main.fragment_menu.*

private const val FIRST_NAME = "first_name"
private const val GOAL = "goal"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment(), View.OnClickListener{
    private var firstName: String? = null

    private var goal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstName = it.getString(FIRST_NAME)
            goal = it.getString(GOAL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onResume() {
        super.onResume()
        summary_lv.setOnClickListener(this)
        logout_lv.setOnClickListener(this)
        if (firstName != "null") {
            name_tv.text = firstName
        }
        if (goal != "null") {
            val weightChange = goal?.toDouble()
            if (weightChange != null) {
                if (weightChange > 0) goal_tv.text = getString(R.string.gain)
                else if (weightChange == 0.0) goal_tv.text = getString(R.string.maintain)
                else goal_tv.text = getString(R.string.loss)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(usr: UserDataModel, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_NAME, usr.firstName)
                    putString(GOAL, usr.weightChangeGoalPerWeek.toString())
                }
            }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.summary_lv -> {
            }
            R.id.logout_lv -> {
            }
        }
    }


    fun isTablet(): Boolean{
        return resources.getBoolean(R.bool.isTablet)
    }
}