package com.example.lifestyleapp.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.CalculateData
import com.example.lifestyleapp.common.TAG_XX
import com.example.lifestyleapp.common.UserDataModel
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.android.synthetic.main.fragment_summary.goal_tv
import kotlinx.android.synthetic.main.fragment_summary.name_tv
import java.lang.Exception


private const val USER_NAME = "first_name"
private const val AGE = "age"
private const val CITY = "city"
private const val COUNTRY = "country"
private const val HEIGHT = "height"
private const val WEIGHT = "weight"
private const val IS_MALE = "is_male"
private const val BMI = "BMI"
private const val BMR = "BMR"
private const val DAILY = "daily"
private const val GOAL = "goal"
private const val ACTIVITY_LEVEL = "activity_level"
private const val IMAGE = "image"

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : Fragment() {
    private var userName: String? = null
    private var age: String? = null
    private var city: String? = null
    private var country: String? = null
    private var height: String? = null
    private var weight: String? = null
    private var sex: String? = null
    private var bmi: String? = null
    private var bmr: String? = null
    private var daily: String? = null
    private var goal: String? = null
    private var activityLevel: String? = null
    private var imgPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(USER_NAME)
            age = it.getString(AGE)
            city = it.getString(CITY)
            country = it.getString(COUNTRY)
            height = it.getString(HEIGHT)
            weight = it.getString(WEIGHT)
            sex = it.getString(IS_MALE)
            bmi = it.getString(BMI)
            bmr = it.getString(BMR)
            daily = it.getString(DAILY)
            goal = it.getString(GOAL)
            activityLevel = it.getString(ACTIVITY_LEVEL)
            imgPath = it.getString(IMAGE)
        }
        // Log.d(TAG_XX, "$user_name $age $city $country $height $weight $sex $goal")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        // Log.d(TAG_XX, "Initialize the fragment")
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userName != "null") name_tv.text = userName
        if (age != "null") age_tv.text = age
        if (city != "null" && country != "null") {
            val loc = "$city, $country"
            location_tv.text = loc
        }
        if (sex != "null") {
            if (sex == "true") sex_tv.text = getString(R.string.male)
            else sex_tv.text = getString(R.string.female)
        }
        if (height != "null") h_tv.text = height
        if (weight != "null") w_tv.text = weight
        if (bmr != "null") bmr_tv.text = bmr

        if (bmi != "null") {
            val bmiConcat = "BMI: $bmi"
            bmi_tv.text = bmiConcat
        }
        if (daily != "null") dc_tv.text = daily

        if (goal != "null") {
            val weightChange = goal?.toDouble()
            if (weightChange != null) {
                if (weightChange > 0) goal_tv.text = getString(R.string.gain)
                else if (weightChange == 0.0) goal_tv.text = getString(R.string.maintain)
                else goal_tv.text = getString(R.string.loss)
            }
        }

        if (imgPath != null && imgPath != "null") {
            // Log.d(TAG_XX, imgPath!!)
            try {
                val thumbnailImage = BitmapFactory.decodeFile(imgPath)
                thumb_iv.setImageBitmap(thumbnailImage)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        if (activityLevel != "null") activity_level_tv.text = activityLevel
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(usr: UserDataModel, calcu: CalculateData) =
            SummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, usr.userName)
                    putString(AGE, usr.age.toString())
                    putString(IS_MALE, usr.male.toString())
                    putString(COUNTRY, usr.country)
                    putString(CITY, usr.city)
                    putString(HEIGHT, usr.heightInches.toString())
                    putString(WEIGHT, usr.weightLbs.toString())
                    putString(ACTIVITY_LEVEL, usr.activityLevel)
                    putString(BMI, calcu.BMI.toString())
                    putString(BMR, calcu.BMR.toString())
                    putString(DAILY, calcu.daily.toString())
                    putString(GOAL, usr.weightChangeGoalPerWeek.toString())
                    putString(IMAGE, usr.profilePicturePath)
                }
            }
    }
}