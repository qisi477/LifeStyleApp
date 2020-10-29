package com.example.lifestyleapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageUploadFileOptions
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.UserDataModel
import com.example.lifestyleapp.viewmodels.UserViewModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val requestImageCapture = 1

    //val REQUEST_CODE = 100
    private var currentPhotoPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("RegisterPage", "Enter the register activity")
        setContentView(R.layout.activity_register)

        register_label_bt?.setOnClickListener(this)
        takePicture_label_bt?.setOnClickListener(this)
        //upload_label_bt?.setOnClickListener(this)

        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }

    }

    private fun uploadFile(file:File) {

        Amplify.Storage.uploadFile(
            "ExampleKey",
            file,
            StorageUploadFileOptions.defaultInstance(),
            { progress -> Log.i("MyAmplifyApp", "Fraction completed: ${progress.fractionCompleted}") },
            { result -> Log.i("MyAmplifyApp", "Successfully uploaded: ${result.getKey()}") },
            { error -> Log.e("MyAmplifyApp", "Upload failed", error) }
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.takePicture_label_bt -> {
                dispatchTakePictureIntent()
            }
            R.id.register_label_bt -> {
                if (checkInputData()) {
                    val user = readInput()
                    LocalData(this).saveUser(user)
                    val localData = LocalData(this)
                    localData.register()
                    val userViewModel = UserViewModel(application)
                    userViewModel.setNewUser(user)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    // build json file
                    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
                    val jsonTutsListPretty: String = gsonPretty.toJson(user)
                    val file=File(user.userName);
                    file.writeText(jsonTutsListPretty)
                    // upload file
                    uploadFile(file);
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null)
            startActivityForResult(takePictureIntent, requestImageCapture)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestImageCapture && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            //Open a file and write to it
            if (isExternalStorageWritable()) {
                currentPhotoPath = saveImage(imageBitmap)
            } else {
                Toast.makeText(this, "External storage not writable.", Toast.LENGTH_SHORT).show()
            }
        }
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
//            image_label_im.setImageURI(data?.data) // handle chosen image
//            var bitmap = (image_label_im.drawable as BitmapDrawable).bitmap
//        }
    }

    private fun saveImage(finalBitmap: Bitmap): String {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file: File = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            val thumbnailImage = BitmapFactory.decodeFile(file.absolutePath)
            image_label_im.setImageBitmap(thumbnailImage)
            Toast.makeText(this, "file saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state)
            return true
        return false
    }

    private fun readInput(): UserDataModel {
        return UserDataModel(
            userName = name_label_et.text.toString(),
            age = if (age_label_et?.text.toString() == "") {
                null
            } else {
                age_label_et?.text.toString().toInt()
            },
            city = city_label_et?.text.toString(),
            country = country_label_et?.text.toString(),
            sex = sex_label_et?.text.toString().toLowerCase(Locale.ROOT),
            heightInches = if (h_label_et?.text.toString() == "") {
                null
            } else {
                h_label_et?.text.toString().toInt()
            },
            weightLbs = if (w_label_et?.text.toString() == "") {
                null
            } else {
                w_label_et?.text.toString().toInt()
            },
            profilePicturePath = currentPhotoPath
        )
    }

    private fun checkInputData(): Boolean {
        if (name_label_et?.text.toString() == "") {
            Toast.makeText(this, "User Name is required.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}