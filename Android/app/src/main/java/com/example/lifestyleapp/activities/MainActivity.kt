package com.example.lifestyleapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageUploadFileOptions
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.Signals
import com.example.lifestyleapp.common.currentSignals
import com.example.lifestyleapp.common.fakeUser2
import com.example.lifestyleapp.fragments.*
import com.example.lifestyleapp.viewmodels.UserViewModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(), MenuFragment.DataParser, SettingFragment.SettingData,
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val localData = LocalData(this)
        if (!localData.getRegister()) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        menu_bt?.setOnClickListener(this)
        if (isTablet()) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            val usr = localData.getUser() ?: fakeUser2
            fragmentTransaction.replace(
                R.id.frame_master,
                MenuFragment.newInstance(),
                "menu_frag"
            )
            fragmentTransaction.commit()
        }
        dataHandler(currentSignals)

//        val item = Todo.builder()
//                .name("Finish quarterly taxes")
//                .priority(Priority.HIGH)
//                .description("Taxes are due for the quarter next week")
//                .build()

//        Amplify.DataStore.save(
//                item,
//                { success -> Log.i("Tutorial", "Saved item: " + success.item().name) },
//                { error -> Log.e("Tutorial", "Could not save item to DataStore", error) }
//        )

//        Amplify.DataStore.query(
//                Todo::class.java,
//                Where.matches(
//                        Todo.PRIORITY.eq(Priority.HIGH)
//                ),
//                { todos ->
//                    while (todos.hasNext()) {
//                        val todo = todos.next()
//                        val name = todo.name;
//                        val priority: Priority? = todo.priority
//                        val description: String? = todo.description
//
//                        Log.i("Tutorial", "==== Todo ====")
//                        Log.i("Tutorial", "Name: $name")
//
//                        if (priority != null) {
//                            Log.i("Tutorial", "Priority: $priority")
//                        }
//
//                        if (description != null) {
//                            Log.i("Tutorial", "Description: $description")
//                        }
//                    }
//                },
//                { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
//        )

//        Amplify.DataStore.observe(Todo::class.java,
//                { Log.i("Tutorial", "Observation began.") },
//                { Log.i("Tutorial", it.item().toString()) },
//                { Log.e("Tutorial", "Observation failed.", it) },
//                { Log.i("Tutorial", "Observation complete.") }
//        )

    }

//    fun uploadWithTransferUtility() {
//        val KEY = "AKIA55S7EGV7FJZ5PZRP"
//        val SECRET = "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"
//        val credentials = BasicAWSCredentials(KEY, SECRET)
//        val s3Client = AmazonS3Client(credentials)
//        val transferUtility = TransferUtility.builder()
//            .context(applicationContext)
//            .awsConfiguration(AWSMobileClient.getInstance().configuration)
//            .s3Client(s3Client)
//            .build()
//        val uploadObserver = transferUtility.upload(
//            "s3Folder/s3Key.txt",
//            File("/path/to/file/localFile.txt")
//        )
//
//        // Attach a listener to the observer to get state update and progress notifications
//        uploadObserver.setTransferListener(object : TransferListener {
//            override fun onStateChanged(id: Int, state: TransferState) {
//                if (TransferState.COMPLETED == state) {
//                    // Handle a completed upload.
//                    Log.d("YourActivity", "Upload Completed")
//                }
//            }
//
//            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
//                val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
//                val percentDone = percentDonef.toInt()
//                Log.d(
//                    "YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
//                            + " bytesTotal: " + bytesTotal + " " + percentDone + "%"
//                )
//            }
//
//            override fun onError(id: Int, ex: Exception) {
//                // Handle errors
//                Log.d("YourActivity", "Upload Error$ex")
//            }
//        })
//
//        // If you prefer to poll for the data, instead of attaching a
//        // listener, check for the state and progress in the observer.
//        if (TransferState.COMPLETED == uploadObserver.state) {
//            // Handle a completed upload.
//            Log.d("YourActivity", "Upload Completed")
//        }
//        Log.d("YourActivity", "Bytes Transferrred: " + uploadObserver.bytesTransferred)
//        Log.d("YourActivity", "Bytes Total: " + uploadObserver.bytesTotal)
//    }
//


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.menu_bt -> {
//                val localData = LocalData(this)
//                val usr = localData.getUser() ?: fakeUser2
                val menuFragment = MenuFragment.newInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, menuFragment, "menu_frag")
                fragmentTransaction.commit()
            }
        }
    }

    private fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.isTablet)
    }

    override fun dataHandler(command: Signals) {
        when (command) {
            Signals.SUMMARY -> {
                currentSignals = Signals.SUMMARY
//                val localData = LocalData(this)
//                val usr = localData.getUser() ?: fakeUser2
//                val userModel = UserModel(usr)
//                val calculateData = CalculateData(
//                    userModel.calculateBMI(),
//                    userModel.calculateBMR(),
//                    userModel.calculateDailyCaloriesNeededForGoal()
//                )
                val summaryFragment = SummaryFragment.newInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
                fragmentTransaction.commit()
            }
            Signals.LOGOUT -> {
                currentSignals = Signals.SUMMARY
                val localData = LocalData(this)
                localData.unregister()
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            Signals.WEATHER -> {
                currentSignals = Signals.WEATHER
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val weatherFragment = WeatherFragment.newInstance(usr)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, weatherFragment, "weather_frag")
                fragmentTransaction.commit()
            }
            Signals.HIKE -> {
                currentSignals = Signals.SUMMARY
//                val localData = LocalData(this)
//                val usr = localData.getUser() ?: fakeUser2
                val userViewModel = application?.let { UserViewModel(it) }
                var city = userViewModel?.allUsers?.value?.get(0)?.city ?: "me"
                userViewModel?.allUsers?.observe(this, Observer {
                    if (it.isNotEmpty()) {
                        val usr = it[0]
                        if (usr.city != null && usr.city != "null") {
                            city = usr.city!!
                        }
                    }
                })
                val gmmIntentUri = Uri.parse("geo:0,0?q=trails near $city")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
            Signals.SETTING -> {
                currentSignals = Signals.SETTING
//                val localData = LocalData(this)
//                val usr = localData.getUser() ?: fakeUser2
                val settingFragment = SettingFragment.newInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, settingFragment, "summary_frag")
                fragmentTransaction.commit()
            }
            Signals.STEP -> {
                currentSignals = Signals.STEP
                val stepCounterFragment = StepCounterFragment.newInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, stepCounterFragment, "step_frag")
                fragmentTransaction.commit()
            }
        }
    }

//    override fun settingDataHandler(
//        height: String,
//        weight: String,
//        goal: String,
//        activityLevel: String
//    ) {
//        val localData = LocalData(this)
//        val usr = localData.getUser() ?: fakeUser2
//        if (height != "") usr.heightInches = height.toInt()
//        if (weight != "") usr.weightLbs = weight.toInt()
//        if (goal != "") usr.weightChangeGoalPerWeek = goal.toFloat()
//        if (activityLevel != "") usr.activityLevel = activityLevel
//        val userModel = UserModel(usr)
//        val calculateData = CalculateData(
//            userModel.calculateBMI(),
//            userModel.calculateBMR(),
//            userModel.calculateDailyCaloriesNeededForGoal()
//        )
//        localData.saveUser(usr)
//        val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
//        fragmentTransaction.commit()
//    }


    override fun settingDataHandler() {
        val summaryFragment = SummaryFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
        fragmentTransaction.commit()
    }
}
