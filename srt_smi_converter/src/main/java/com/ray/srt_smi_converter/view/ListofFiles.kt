package com.ray.srt_smi_converter.view

import android.Manifest
import android.app.Activity
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat.getCallingActivity
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.view.adapter.CustomedAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import java.io.File
import java.util.ArrayList

class ListofFiles: ListFragment() {

    private lateinit var mSharedVM:SharedViewModel

    private var TAG:String = this.javaClass.simpleName.toString()

    protected var itemsInCurrentPath: MutableList<String>? = null
    protected var currentPath: MutableList<String>? = null
    protected var root: String? = null
    protected var mPreviousSelectedPath: String? = null
    protected lateinit var myPath: TextView
    protected lateinit var rootFile: File
    protected var files: Array<File>? = null
    protected var listView: View? = null

    // Storage Permissions
    protected val REQUEST_EXTERNAL_STORAGE = 1
    protected var PERMISSIONS_STORAGE =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    //this will store a path of folder that contains music(s) that most recently played.
    var lastSavePath: String? = null

    //RecyclerView Related variables
    private lateinit var mView:View
    private lateinit var recyclerView:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_list__of__files,container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Remove Title Bar
        activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //Remove Notification Bar
        activity?.getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //Verify Permission for Storage
        activity?.let { verifyStoragePermissions(it)}


        recyclerView = mView.findViewById(R.id.list_of_items_recycleview)
        val resource = R.layout.each_file
        val adapter: CustomedAdapter = this.context?.let { CustomedAdapter(it, resource, ) }


        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedVM.setDirectory("Testing Directory")

        //adHandler();
        start()
    }

    /*  protected void adHandler(){
        MobileAds.initialize(this, "ca-app-pub-5028162253404928~6582777309");
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdView mAdView = (AdView)findViewById(R.id.adView);
        mAdView.loadAd(request);
    }
*/
    protected fun start() {
        myPath = view?.findViewById(R.id.path) as TextView

        //If there was previously selected path, it will start from the selected path;
        if (mPreviousSelectedPath != null) {
            rootFile = File(mPreviousSelectedPath!!)
            getDir(rootFile)
        } else {

            //"/storage/" path will open directory in between Internal and External SD Cards within the device.
            //String secStore = System.getenv("SECONDARY_STORAGE");
            //not with s5
            //String secStore = getBaseContext().getFilesDir().getAbsolutePath();
            //"/storage/" works with note2 not with s5
            //Environment.getExternalStorageDirectory().getPath()
            // rootFile = new File(secStore);

            val testingRootFile = Environment.getRootDirectory().parentFile
            val parentFile = Environment.getExternalStorageDirectory()
            val samsungExSDPath = Environment.getExternalStorageDirectory().path
            val samsungFile: File// = new File(samsungExSDPath + "/external_sd/");;

            //Log.i(Tag, " currentRootFile is : " +testingRootFile.toString());
            // Log.i(Tag, " getExternalStorageDirectory() is : " +Environment.getExternalStorageDirectory().toString());
            //   Log.i(Tag, " Environment.getRootDirectory().getParentFile() is : " +Environment.getRootDirectory().getParentFile().toString());
            //  Log.i(Tag, " getExternalFilesDir(null)is : " +getExternalFilesDir(null).toString());

            if (android.os.Build.DEVICE.contains("Samsung") || android.os.Build.MANUFACTURER.contains("Samsung") || android.os.Build.DEVICE.contains("samsung") || android.os.Build.MANUFACTURER.contains("samsung")) {
                //Toast.makeText(this, "MANUFACTURER Name: " + Build.MANUFACTURER, Toast.LENGTH_SHORT).show();

                samsungFile = Environment.getRootDirectory().parentFile
                rootFile = samsungFile
                //                rootFile = new File("\"/storage/\"");
            } else {
                //Toast.makeText(this, "Non_SamSung_MANUFACTURER Name: " + Build.MANUFACTURER, Toast.LENGTH_SHORT).show();
                rootFile = testingRootFile
            }

            //rootFile = samsungFile;

            getDir(rootFile)
        }//If this is first time of selecting file.
    }

    protected fun getDir(startingFilePath: File) {

        itemsInCurrentPath = ArrayList()
        currentPath = ArrayList()

        //Display current directory location
        myPath.text = "Current Location: " + startingFilePath.path

        //files now has list of files in the current folder(directory)
        files = startingFilePath.listFiles()

        if (startingFilePath != rootFile) {
            if (startingFilePath.path !== "/storage/") {
                Log.v(TAG, "What the hell am i Doing here")
                //A folder that will redirect to previous path;
                itemsInCurrentPath!!.add(startingFilePath.parent)

                currentPath!!.add(rootFile.parent)
                currentPath!!.add(startingFilePath.parent)
            }
        }
        //Log.v("SourceListActivty.java", "Length of files:" +  files.length);

        try {
            if (files!!.size == 0) {
                Log.d(TAG, "Length of files is empty")
            } else {
                //Add all of files in the current Path/Folder to list
                for (i in files!!.indices) {
                    if (!files!![i].isHidden || files!![i].canRead()) {
                        itemsInCurrentPath!!.add(files!![i].parent + "/" + files!![i].name + "/")
                    }
                }
            }
        } catch (e: NullPointerException) {
            e.stackTrace
        }

        //R.layout.row_each_directory R.id.individual_file,itemsInCurrentPath
        //ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, itemsInCurrentPath);
        val fileList = CustomedAdapter(mView.context, android.R.layout.simple_list_item_1, itemsInCurrentPath, startingFilePath)
        listAdapter = fileList
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        //One of item in the current folder is selected
        val selected_file = File(itemsInCurrentPath!![position])

        if (selected_file.isDirectory) {
            if (selected_file.canRead()) {
                //Calling previouslySelectedPath() to store most recently visited folder
                //previouslySelectedPath(selected_file);
                //Log.v("SourceListActivity.java", "Last saved music fold was:" + mPreviousSelectedPath);
                getDir(selected_file)
            } else
            //Double caution for selecting non-readable file (which was sorted in getDir();
                Toast.makeText(this@SourceListActivity, "It cannot be read", Toast.LENGTH_SHORT)

        } else if (selected_file.path.endsWith(".mp3") || selected_file.path.endsWith(".mp4") || selected_file.path.endsWith(".mkv") ||
                selected_file.path.endsWith(".avi") || selected_file.path.endsWith(".wmv") || selected_file.path.endsWith(".mpg")) {
            val returnIntent = getIntent()//new Intent();
            returnIntent.putExtra("resultMediaFile", selected_file.path)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        } else
            Toast.makeText(this@SourceListActivity, "It is not a directory", Toast.LENGTH_SHORT)//Once selected file is a media file, then it return to parent activity.
    }

    //This method save most recent path that user looked.
    protected fun previouslySelectedPath(previousPath: File) {
        if (previousPath.path.endsWith(".mp3"))
            mPreviousSelectedPath = previousPath.parent
        else
            mPreviousSelectedPath = previousPath.toString()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}
