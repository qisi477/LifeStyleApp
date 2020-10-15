package com.example.lifestyleapp.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import com.example.lifestyleapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_summary.*


//private const val USER_NAME = "first_name"
//private const val AGE = "age"
//private const val CITY = "city"
//private const val COUNTRY = "country"
//private const val HEIGHT = "height"
//private const val WEIGHT = "weight"
//private const val SEX = "sex"
//private const val BMI = "BMI"
//private const val BMR = "BMR"
//private const val DAILY = "daily"
//private const val GOAL = "goal"
//private const val ACTIVITY_LEVEL = "activity_level"
//private const val IMAGE = "image"

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : Fragment() {
//    private var userName: String? = null
//    private var age: String? = null
//    private var city: String? = null
//    private var country: String? = null
//    private var height: String? = null
//    private var weight: String? = null
//    private var sex: String? = null
//    private var bmi: String? = null
//    private var bmr: String? = null
//    private var daily: String? = null
//    private var goal: String? = null
//    private var activityLevel: String? = null
//    private var imgPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            userName = it.getString(USER_NAME)
//            age = it.getString(AGE)
//            city = it.getString(CITY)
//            country = it.getString(COUNTRY)
//            height = it.getString(HEIGHT)
//            weight = it.getString(WEIGHT)
//            sex = it.getString(SEX)
//            bmi = it.getString(BMI)
//            bmr = it.getString(BMR)
//            daily = it.getString(DAILY)
//            goal = it.getString(GOAL)
//            activityLevel = it.getString(ACTIVITY_LEVEL)
//            imgPath = it.getString(IMAGE)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userViewModel = activity?.application?.let { UserViewModel(application = it) }
        userViewModel?.allUsers?.observe(viewLifecycleOwner, Observer {
            // Log.d(TAG_XX, "User view model: ${it.size}")
            if (it.isNotEmpty()) {
                val usr = it[0]
                // Log.d(TAG_XX, "usr: ${usr.userName} ${usr.city}")
                val model = UserModel(usr)
                name_tv.text = usr.userName
                age_tv.text = usr.age.toString()
                val loc = "${usr.city}, ${usr.country}"
                location_tv.text = loc
                sex_tv.text = usr.sex?.toString()
                h_tv.text = usr.heightInches?.toString()
                w_tv.text = usr.weightLbs?.toString()
                bmr_tv.text = model.calculateBMR()?.toString()
                dc_tv.text = model.calculateDailyCaloriesNeededForGoal()?.toString()
                val weightChange = usr.weightChangeGoalPerWeek?.toDouble()
                if (weightChange != null) {
                    when {
                        weightChange > 0 -> goal_tv.text = getString(R.string.gain)
                        weightChange == 0.0 -> goal_tv.text = getString(R.string.maintain)
                        else -> goal_tv.text = getString(R.string.loss)
                    }
                }
                bmi_tv.text = model.calculateBMI()?.toString()
                Log.d(
                    TAG_XX,
                    "height: ${usr.heightInches} weight: ${usr.weightLbs} bmi: ${model.calculateBMI()}"
                )
                activity_level_tv.text = usr.activityLevel?.toString()
                val imgPath = usr.profilePicturePath
                if (imgPath != null && imgPath != "null") {
                    try {
                        val thumbnailImage = BitmapFactory.decodeFile(imgPath)
                        thumb_iv.setImageBitmap(thumbnailImage)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        })

//        if (userName != "null") name_tv.text = userName
//        if (age != "null") age_tv.text = age
//        if (city != "null" && country != "null") {
//            val loc = "$city, $country"
//            location_tv.text = loc
//        }
//        if (sex != "null") sex_tv.text = sex
//        if (height != "null") h_tv.text = height
//        if (weight != "null") w_tv.text = weight
//        if (bmr != "null") bmr_tv.text = bmr
//
//        if (bmi != "null") {
//            val bmiConcat = "BMI: $bmi"
//            bmi_tv.text = bmiConcat
//        }
//        if (daily != "null") dc_tv.text = daily
//
//        if (goal != "null") {
//            val weightChange = goal?.toDouble()
//            if (weightChange != null) {
//                when {
//                    weightChange > 0 -> goal_tv.text = getString(R.string.gain)
//                    weightChange == 0.0 -> goal_tv.text = getString(R.string.maintain)
//                    else -> goal_tv.text = getString(R.string.loss)
//                }
//            }
//        }
//
//        if (imgPath != null && imgPath != "null") {
//            try {
//                val thumbnailImage = BitmapFactory.decodeFile(imgPath)
//                thumb_iv.setImageBitmap(thumbnailImage)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//
//        if (activityLevel != "null") activity_level_tv.text = activityLevel
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SummaryFragment().apply {
//                arguments = Bundle().apply {
//                    putString(USER_NAME, usr.userName)
//                    putString(AGE, usr.age.toString())
//                    putString(SEX, usr.sex.toString())
//                    putString(COUNTRY, usr.country)
//                    putString(CITY, usr.city)
//                    putString(HEIGHT, usr.heightInches.toString())
//                    putString(WEIGHT, usr.weightLbs.toString())
//                    putString(ACTIVITY_LEVEL, usr.activityLevel)
//                    putString(BMI, calc.BMI.toString())
//                    putString(BMR, calc.BMR.toString())
//                    putString(DAILY, calc.daily.toString())
//                    putString(GOAL, usr.weightChangeGoalPerWeek.toString())
//                    putString(IMAGE, usr.profilePicturePath)
//                }
            }
    }
}