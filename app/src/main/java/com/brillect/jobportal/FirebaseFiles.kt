package com.brillect.jobportal

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.firebase.storage.FirebaseStorage

class FirebaseFiles {
    val storageRef = FirebaseStorage.getInstance().reference

    // upload pdf
    private fun uploadDoc(uri: Uri?, pdfUrl: MutableState<String>) {

        val filePath = storageRef.child("resume").child("TestDoc.pdf")
        if (uri != null) {
            filePath.putFile(uri).addOnCompleteListener {
                filePath.downloadUrl.addOnCompleteListener {
                    pdfUrl.value = it.toString()
                    Log.d("update_url", it.toString())
                }
            }
        }
    }
}