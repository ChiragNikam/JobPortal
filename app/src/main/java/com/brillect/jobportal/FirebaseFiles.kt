package com.brillect.jobportal

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow

class FirebaseFiles {
    val storageRef = FirebaseStorage.getInstance().reference

    // upload pdf
    fun uploadDoc(
        uri: Uri?,
        passPdfUrl: (String) -> Unit,
        passPercentTransferred: (Int) -> Unit
    ) {

        val filePath =
            storageRef.child("resume").child(Firebase.auth.currentUser?.uid.toString() + ".pdf")
        if (uri != null) {
            filePath.putFile(uri)
                .addOnProgressListener {
                    val percentTransferred = (100.0 * it.bytesTransferred / it.totalByteCount)
                    Log.d("upload_status", "status: $percentTransferred")
                    passPercentTransferred(percentTransferred.toInt())
                }
                .addOnCompleteListener {
                    // get download url for adding it to db
                    filePath.downloadUrl.addOnCompleteListener {
                        passPdfUrl(it.result.toString())
                    }
                }
                .addOnFailureListener {
                    val exception = it.message
                    Log.d("file_upload_err", exception.toString())
                }
        }
    }
}