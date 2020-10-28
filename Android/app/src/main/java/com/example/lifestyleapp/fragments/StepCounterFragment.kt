package com.example.lifestyleapp.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.StepDataModel
import com.example.lifestyleapp.common.TAG_XX
import com.example.lifestyleapp.viewmodels.StepViewModel
import kotlinx.android.synthetic.main.fragment_step_counter.*
import kotlin.math.abs


/**
 * A simple [Fragment] subclass.
 * Use the [StepCounterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StepCounterFragment : Fragment(), View.OnClickListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounter: Sensor? = null
    private var linearAccelerometer: Sensor? = null
    private val mThreshold = 4.0
    private var stepViewModel: StepViewModel? = null
    private var steps = 0
    private var activated = false

    //Previous positions
    private var last_x = 0.0
    private var last_y = 0.0
    private var last_z = 0.0
    private var now_x = 0.0
    private var now_y = 0.0
    private var now_z = 0.0
    private var mNotFirstTime = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reset_btn.setOnClickListener(this)
        stepViewModel = activity?.application?.let { StepViewModel(application = it) }
        stepViewModel?.steps?.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                val stepToShow = it[0]
                step_tv.text = stepToShow.step++.toString()
                steps = stepToShow.step
            }
        })
    }


    override fun onResume() {
        super.onResume()
        linearAccelerometer?.also { la ->
            sensorManager.registerListener(
                linearAccelerometerListener,
                la,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(stepListener)
        sensorManager.unregisterListener(linearAccelerometerListener)
        activated = false
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StepCounterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private val stepListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                steps += event.values[0].toInt()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            return
        }
    }


    private val linearAccelerometerListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {

            //Get the acceleration rates along the y and z axes
            now_x = sensorEvent.values[0].toDouble()
            now_y = sensorEvent.values[1].toDouble()
            now_z = sensorEvent.values[2].toDouble()
            if (mNotFirstTime) {
                val dx: Double = abs(last_x - now_x)
                val dy: Double = abs(last_y - now_y)
                val dz: Double = abs(last_z - now_z)

                //Check if the values of acceleration have changed on any pair of axes
                if (dx > mThreshold && dy > mThreshold ||
                    dx > mThreshold && dz > mThreshold ||
                    dy > mThreshold && dz > mThreshold
                ) {
                    try {
                        val notification =
                            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val r = RingtoneManager.getRingtone(
                            activity?.applicationContext,
                            notification
                        )
                        r.play()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    if (!activated) {
                        stepCounter?.also { step ->
                            sensorManager.registerListener(
                                stepListener,
                                step,
                                SensorManager.SENSOR_DELAY_NORMAL
                            )
                        }
                        activated = !activated
                        Log.d(TAG_XX, "Active")
                    } else {
                        sensorManager.unregisterListener(stepListener)
                        stepViewModel?.insertStep(StepDataModel(0, steps))
                        activated = !activated
                        Log.d(TAG_XX, "Stop")
                    }
                }
            }
            last_x = now_x
            last_y = now_y
            last_z = now_z
            mNotFirstTime = true

        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {
            return
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reset_btn -> {
                Log.d(TAG_XX, "Reset Btn")
                step_tv.text = "0"
                steps = 0
                stepViewModel?.insertStep(StepDataModel(0, steps))
            }
        }
    }

}