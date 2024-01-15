package com.example.socialize20.Utils

import android.app.ProgressDialog
import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

//This fun will get the image and set it over our firebase storage
fun uploadImage(uri: Uri, foldername:String, callback:(String?) -> Unit){
    var imageURl :String?=null
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageURl = it.toString()
                callback(imageURl)
            }
        }
}
//This fun will get the Video and set it over our firebase storage
fun uploadVideo(uri: Uri, foldername:String,progressDialog: ProgressDialog, callback:(String?) -> Unit){
    var imageURl :String?=null
//    we'll create a progress Dialog here which will tell how much of reel is uploaded
    progressDialog.setTitle("Uploading . . . .")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageURl = it.toString()
                progressDialog.dismiss()
                callback(imageURl)
            }
        }
        .addOnSuccessListener {
//            Variable to get how much value is uploaded till
            val uploadedValue = (100.0 * it.bytesTransferred / it.totalByteCount).toInt()
//            val uploadedValue : Long = it.bytesTransferred/it.totalByteCount
//            showing it over the progressDialog
            progressDialog.setMessage("Uploaded $uploadedValue %")

        }
}