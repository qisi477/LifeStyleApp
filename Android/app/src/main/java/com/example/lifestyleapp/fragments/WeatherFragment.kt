package com.example.lifestyleapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import kotlinx.android.synthetic.main.fragment_weather.*
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val CITY = "city"
private const val COUNTRY = "country"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment(), DownloadCallback<String> {
    private var city: String? = null
    private var country: String? = null
    private var weather: Weather? = null

    private var networkFragment: NetworkFragment? = null


    private var downloading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString(CITY)
            country = it.getString(COUNTRY)
        }

        networkFragment =
            NetworkFragment.getInstance(
                fragmentManager = parentFragmentManager,
                "https://www.google.com"
            )


        weather = getWeather(Location(city = city, country = country))
        Log.d(TAG_WEATHER, "$city $country")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG_CH, "Initialize Weather Fragment")
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (city != "null" && country != "null") {
            val loc = "$city, $country"
            location_tv.text = loc
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(usr: UserDataModel) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(COUNTRY, usr.country)
                    putString(CITY, usr.city)
                }
            }
    }

    override fun updateFromDownload(result: String?) {
        if (weather != null) {
            temperature_tv.text = weather!!.mainWeather.tempKelvin.toString()
        } else {
            temperature_tv.text = "NA"
        }
    }

    override fun getActiveNetworkInfo(): NetworkInfo {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo!!
    }

    override fun onProgressUpdate(progressCode: Int, percentComplete: Int) {
        when (progressCode) {
            // You can add UI behavior for progress updates here.
            ERROR -> {
            }
            CONNECT_SUCCESS -> {
            }
            GET_INPUT_STREAM_SUCCESS -> {
            }
            PROCESS_INPUT_STREAM_IN_PROGRESS -> {
            }
            PROCESS_INPUT_STREAM_SUCCESS -> {
            }
        }
    }

    override fun finishDownloading() {
        downloading = false
        networkFragment?.cancelDownload()
    }
}

const val ERROR = -1
const val CONNECT_SUCCESS = 0
const val GET_INPUT_STREAM_SUCCESS = 1
const val PROCESS_INPUT_STREAM_IN_PROGRESS = 2
const val PROCESS_INPUT_STREAM_SUCCESS = 3

interface DownloadCallback<T> {

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     */
    fun updateFromDownload(result: T?)

    /**
     * Get the device's active network status in the form of a NetworkInfo object.
     */
    fun getActiveNetworkInfo(): NetworkInfo

    /**
     * Indicate to callback handler any progress update.
     * @param progressCode must be one of the constants defined in DownloadCallback.Progress.
     * @param percentComplete must be 0-100.
     */
    fun onProgressUpdate(progressCode: Int, percentComplete: Int)

    /**
     * Indicates that the download operation has finished. This method is called even if the
     * download hasn't completed successfully.
     */
    fun finishDownloading()
}

private const val TAG = "NetworkFragment"
private const val URL_KEY = "UrlKey"

class NetworkFragment : Fragment() {
    private var callback: DownloadCallback<String>? = null
    private var downloadTask: DownloadTask? = null
    private var urlString: String? = null

    companion object {
        /**
         * Static initializer for NetworkFragment that sets the URL of the host it will be
         * downloading from.
         */
        fun getInstance(fragmentManager: FragmentManager, url: String): NetworkFragment {
            val networkFragment = NetworkFragment()
            val args = Bundle()
            args.putString(URL_KEY, url)
            networkFragment.arguments = args
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit()
            return networkFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        urlString = arguments?.getString(URL_KEY)
        //...
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // Host Activity will handle callbacks from task.
        callback = context as? DownloadCallback<String>
    }

    override fun onDetach() {
        super.onDetach()
        // Clear reference to host Activity to avoid memory leak.
        callback = null
    }

    override fun onDestroy() {
        // Cancel task when Fragment is destroyed.
        cancelDownload()
        super.onDestroy()
    }

    /**
     * Start non-blocking execution of DownloadTask.
     */
    fun startDownload() {
        cancelDownload()
        callback?.also {
            downloadTask = DownloadTask(it).apply {
                execute(urlString)
            }
        }
    }

    /**
     * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
     */
    fun cancelDownload() {
        downloadTask?.cancel(true)
    }

    //...
}

/**
 * Implementation of AsyncTask designed to fetch data from the network.
 */
private class DownloadTask(callback: DownloadCallback<String>) :
    AsyncTask<String, Int, DownloadTask.Result>() {

    private var callback: DownloadCallback<String>? = null

    init {
        setCallback(callback)
    }

    fun setCallback(callback: DownloadCallback<String>) {
        this.callback = callback
    }

    /**
     * Wrapper class that serves as a union of a result value and an exception. When the download
     * task has completed, either the result value or exception can be a non-null value.
     * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
     */
    class Result {
        var resultValue: String? = null
        var exception: Exception? = null

        constructor(resultValue: String) {
            this.resultValue = resultValue
        }

        constructor(exception: Exception) {
            this.exception = exception
        }
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    override fun onPreExecute() {
        if (callback != null) {
            val networkInfo = callback?.getActiveNetworkInfo()
            if (!networkInfo!!.isConnected || (networkInfo.type != ConnectivityManager.TYPE_WIFI
                        && networkInfo.type != ConnectivityManager.TYPE_MOBILE)
            ) {
                // If no connectivity, cancel task and update Callback with null data.
                callback?.updateFromDownload(null)
                cancel(true)
            }
        }
    }

    /**
     * Defines work to perform on the background thread.
     */
    override fun doInBackground(vararg urls: String): DownloadTask.Result? {
        var result: Result? = null
        if (!isCancelled && urls.isNotEmpty()) {
            val urlString = urls[0]
            result = try {
                val url = URL(urlString)
                val resultString = downloadUrl(url)
                if (resultString != null) {
                    Result(resultString)
                } else {
                    throw IOException("No response received.")
                }
            } catch (e: Exception) {
                Result(e)
            }

        }
        return result
    }

    /**
     * Updates the DownloadCallback with the result.
     */
    override fun onPostExecute(result: Result?) {
        callback?.apply {
            result?.exception?.also { exception ->
                updateFromDownload(exception.message)
                return
            }
            result?.resultValue?.also { resultValue ->
                updateFromDownload(resultValue)
                return
            }
            finishDownloading()
        }
    }

    /**
     * Override to add special behavior for cancelled AsyncTask.
     */
    override fun onCancelled(result: Result) {}
}

/**
 * Given a URL, sets up a connection and gets the HTTP response body from the server.
 * If the network request is successful, it returns the response body in String form. Otherwise,
 * it will throw an IOException.
 */
@Throws(IOException::class)
private fun downloadUrl(url: URL): String? {
    var connection: HttpsURLConnection? = null
    return try {
        connection = (url.openConnection() as? HttpsURLConnection)
        connection?.run {
            // Timeout for reading InputStream arbitrarily set to 3000ms.
            readTimeout = 3000
            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connectTimeout = 3000
            // For this use case, set HTTP method to GET.
            requestMethod = "GET"
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            doInput = true
            // Open communications link (network traffic occurs here).
            connect()
//            publishProgress(CONNECT_SUCCESS)
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: $responseCode")
            }
            // Retrieve the response body as an InputStream.
//            publishProgress(GET_INPUT_STREAM_SUCCESS, 0)
            inputStream?.let { stream ->
                // Converts Stream to String with max length of 500.
                readStream(stream, 500)
            }
        }
    } finally {
        // Close Stream and disconnect HTTPS connection.
        connection?.inputStream?.close()
        connection?.disconnect()
    }
}


/**
 * Converts the contents of an InputStream to a String.
 */
@Throws(IOException::class, UnsupportedEncodingException::class)
fun readStream(stream: InputStream, maxReadSize: Int): String? {
    val reader = InputStreamReader(stream, "UTF-8")
    val rawBuffer = CharArray(maxReadSize)
    val buffer = StringBuffer()
    var readSize: Int = reader.read(rawBuffer)
    var maxReadBytes = maxReadSize
    while (readSize != -1 && maxReadBytes > 0) {
        if (readSize > maxReadBytes) {
            readSize = maxReadBytes
        }
        buffer.append(rawBuffer, 0, readSize)
        maxReadBytes -= readSize
        readSize = reader.read(rawBuffer)
    }
    return buffer.toString()
}

