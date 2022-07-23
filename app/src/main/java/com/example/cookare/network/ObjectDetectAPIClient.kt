package com.example.cookare.network

import android.content.Context
import android.graphics.Bitmap
import com.android.volley.toolbox.Volley
import com.example.cookare.model.ObjectDetectResults
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ObjectDetectAPIClient(context: Context) {
    companion object {
        const val VISION_API_PRODUCT_MAX_RESULT = 10

        const val VISION_API_URL =
            "https://vision.googleapis.com/v1"
        const val VISION_API_KEY = "AIzaSyCM5yUvSJpr2DZidA7qEg6dqSJSjCZNQYM"
        const val VISION_API_PROJECT_ID = "odml-codelabs"
        const val VISION_API_LOCATION_ID = "us-east1"
        const val VISION_API_PRODUCT_SET_ID = "product_set0"
    }


    fun annotateImage(image: Bitmap?){
        val apiSource = TaskCompletionSource<List<ObjectDetectResults>>()
        // val apiTask = apiSource.task

        // Craft the request body JSON.

        val requestJson = JSONObject("""{"requests":[{"image":{"imageUri": "https://cloud.google.com/vision/docs/images/bicycle_example.png"},"features": [{"type": ""OBJECT_LOCALIZATION"","maxResults": $VISION_API_PRODUCT_MAX_RESULT}],}]}""")

        println("requestJson: " + requestJson.toString())

        val payload = requestJson.toString()
        val requestBody = payload.toRequestBody()
        val request = Request.Builder()
            .url("$VISION_API_URL/images:annotate?key=$VISION_API_KEY")
            .post(requestBody)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        println("Response: " + response)
        val body = response?.body?.string()
        println("Body: " + body)

        // return apiTask
    }

}