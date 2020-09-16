package com.example.lifestyleapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.Signals
import com.example.lifestyleapp.common.TAG_XX
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
    private var dataParser: DataParser? = null
    private var goal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstName = it.getString(FIRST_NAME)
            goal = it.getString(GOAL)
        }
    }

    interface DataParser {
        fun dataHandler(command: Signals)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataParser = context as DataParser
        } catch (e :ClassCastException) {
            throw java.lang.ClassCastException("$context must implement DataParser Interface")
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
        fun newInstance(usr: UserDataModel) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_NAME, usr.userName)
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
                Log.d(TAG_XX, "Click summary linear view")
                dataParser?.dataHandler(Signals.SUMMARY) ?: return
            }
            R.id.logout_lv -> {
                Log.d(TAG_XX, "Click logout linear view")
                val localData = LocalData(activity)
                localData.deleteUser()
                dataParser?.dataHandler(Signals.LOGOUT) ?: return
            }
        }
    }
}