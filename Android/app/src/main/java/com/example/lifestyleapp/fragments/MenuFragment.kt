package com.example.lifestyleapp.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.Signals
import com.example.lifestyleapp.common.TAG_XX
import com.example.lifestyleapp.common.UserDataModel
import com.example.lifestyleapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_menu.*

//private const val FIRST_NAME = "first_name"
//private const val IMAGE = "image"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment(), View.OnClickListener {
//    private var firstName: String? = null
    private var dataParser: DataParser? = null
//    private var imgPath: String? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            firstName = it.getString(FIRST_NAME)
//            imgPath = it.getString(IMAGE)
//        }
    }

    interface DataParser {
        fun dataHandler(command: Signals)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataParser = context as DataParser
        } catch (e: ClassCastException) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = activity?.application?.let { UserViewModel(it) }
        userViewModel?.allUsers?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                val usr = it[0]
                name_tv.text = usr.userName
                val imgPath = usr.profilePicturePath
                if (imgPath != null && imgPath != "null") {
                    // Log.d(TAG_XX, imgPath!!)
                    try {
                        val thumbnailImage = BitmapFactory.decodeFile(imgPath)
                        avatar_iv.setImageBitmap(thumbnailImage)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        })
        summary_lv.setOnClickListener(this)
        logout_lv.setOnClickListener(this)
        goal_lv.setOnClickListener(this)
        weather_lv.setOnClickListener(this)
        hikes_lv.setOnClickListener(this)
//        if (firstName != "null") {
//            name_tv.text = firstName
//        }
//
//        if (imgPath != null && imgPath != "null") {
//            // Log.d(TAG_XX, imgPath!!)
//            try {
//                val thumbnailImage = BitmapFactory.decodeFile(imgPath)
//                avatar_iv.setImageBitmap(thumbnailImage)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MenuFragment().apply {
                arguments = Bundle().apply {
//                    putString(FIRST_NAME, usr.userName)
//                    putString(IMAGE, usr.profilePicturePath)
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
                //Log.d(TAG_XX, "Click summary linear layout")
                dataParser?.dataHandler(Signals.SUMMARY) ?: return
            }
            R.id.logout_lv -> {
                //Log.d(TAG_XX, "Click logout linear layout")
                val localData = LocalData(activity)
                localData.deleteUser()
                dataParser?.dataHandler(Signals.LOGOUT) ?: return
            }
            R.id.goal_lv -> {
                Log.d(TAG_XX, "Click setting linear layout")
                dataParser?.dataHandler(Signals.SETTING) ?: return
            }
            R.id.hikes_lv -> {
                dataParser?.dataHandler(Signals.HIKE) ?: return
            }
            R.id.weather_lv -> {
                dataParser?.dataHandler((Signals.WEATHER)) ?: return
            }
        }
    }
}