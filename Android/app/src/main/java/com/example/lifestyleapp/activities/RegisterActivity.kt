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
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.UserDataModel
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    val REQUEST_IMAGE_CAPTURE = 1

    //val REQUEST_CODE = 100
    lateinit var currentPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("RegisterPage", "Enter the register activity")
        setContentView(R.layout.activity_register)

        register_label_bt?.setOnClickListener(this)
        takePicture_label_bt?.setOnClickListener(this)
        //upload_label_bt?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.register_label_bt -> {
                val user = readInput()
                // check input
                if (checkInput(user)) {
                    LocalData(this).saveUser(user)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.takePicture_label_bt -> {
                dispatchTakePictureIntent()
            }
//            R.id.upload_label_bt -> {
//                openGalleryForImage()
//            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
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
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true
        return false
    }

    fun readInput(): UserDataModel {
        val user = UserDataModel(
            userName = name_label_et.text.toString(),
            age = age_label_et.text.toString().toInt(),
            city = city_label_et.text.toString(),
            country = country_label_et.text.toString(),
            male = (sex_label_et.text.toString() == "Male"),
            heightInches = h_label_et.text.toString().toInt(),
            weightLbs = w_label_et.text.toString().toInt(),
            profilePicturePath = currentPhotoPath
        )
        return user
    }

    fun checkInput(user: UserDataModel?): Boolean {
        if (user?.userName == null || user.userName == "") {
            name_label_et.error = "User name is required."
            return false
        }
        if (user.age == null) {
            age_label_et.error = "Age is required"
            return false
        }
        if (user.city == null || user.city == "") {
            city_label_et.error = "City is required."
            return false
        }
        if (user.country == null || user.country == "") {
            country_label_et.error = "Country is required"
            return false
        }
        if (user.male == null) {
            name_label_et.error = "Sex is required"
            return false
        }
        if (user.weightLbs == null) {
            w_label_et.error = "Weight is required"
            return false
        }
        if (user.heightInches == null) {
            h_label_et.error = "Height is required"
            return false
        }
        if (user.profilePicturePath == null) {
            h_label_et.error = "Please take a picture as your profile picture."
            return false
        }
        return true
    }

//    private fun openGalleryForImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_CODE)
//    }


}