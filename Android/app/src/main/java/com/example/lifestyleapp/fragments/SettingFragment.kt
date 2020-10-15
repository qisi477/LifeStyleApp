package com.example.lifestyleapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.UserDataModel
import com.example.lifestyleapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

//private const val HEIGHT = "height"
//private const val WEIGHT = "weight"
//private const val GOAL = "goal"
//private const val ACTIVITY_LEVEL = "activity_level"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment(), View.OnClickListener {
    private var height: Int? = null
    private var weight: Int? = null
    private var activityLevel: String? = null
    private var goal: Float? = null
    private var settingData: SettingData? = null

    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            height = it.getString(HEIGHT)
//            weight = it.getString(WEIGHT)
//            goal = it.getString(GOAL)
//            activityLevel = it.getString(ACTIVITY_LEVEL)
//        }
    }

    interface SettingData {
        //fun settingDataHandler(height: String, weight: String, goal: String, activityLevel: String)
        fun settingDataHandler()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            settingData = context as SettingData
        } catch (e: ClassCastException) {
            throw java.lang.ClassCastException("$context must implement DataParser Interface")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finish_btn.setOnClickListener(this)
        goalSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                if (progress < 3 || progress > 7)
                    Toast.makeText(
                        context,
                        "Change more than 2 lbs\nDon't over do it",
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                return
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                return
            }
        })
        userViewModel = activity?.application?.let { UserViewModel(it) }
        userViewModel?.allUsers?.observe(viewLifecycleOwner, Observer {
            // Log.d(TAG_XX, "User view model: ${it.size}")
            if (it.isNotEmpty()) {
                val usr = it[0]
                height = usr.heightInches
                weight = usr.weightLbs
                activityLevel = usr.activityLevel?.toString()
                goal = usr.weightChangeGoalPerWeek
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SettingFragment.
         */
        @JvmStatic
        fun newInstance() =
            SettingFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ACTIVITY_LEVEL, usr.activityLevel)
//                    putString(HEIGHT, usr.heightInches.toString())
//                    putString(WEIGHT, usr.weightLbs.toString())
//                    putString(GOAL, usr.weightChangeGoalPerWeek.toString())
//                }
            }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.finish_btn -> {
                if (current_weight_tf.text.toString() != "") {
                    weight = current_weight_tf.text.toString().toInt()
                }
                if (current_height_tf.text.toString() != "") {
                    height = current_height_tf.text.toString().toInt()
                }
                goal = (goalSeekBar.progress - 5).toString().toFloat()
                activityLevel = if (active_switch.isChecked) "Active" else "Sedentary"
                if (userViewModel?.allUsers?.value?.isNotEmpty()!!) {
                    val usr = userViewModel!!.allUsers.value?.get(0)
                    usr?.weightLbs = weight
                    usr?.heightInches = height
                    usr?.weightChangeGoalPerWeek = goal
                    usr?.activityLevel = activityLevel
                    userViewModel!!.setNewUser(usr!!)
                }
                settingData?.settingDataHandler()
            }
        }
    }
}