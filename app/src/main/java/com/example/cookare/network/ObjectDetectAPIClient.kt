package com.example.cookare.network

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import com.android.volley.toolbox.Volley
import com.example.example.DetectResultResponse
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class ObjectDetectAPIClient() {
    companion object {
        const val VISION_API_PRODUCT_MAX_RESULT = 10

        const val VISION_API_URL =
            "https://vision.googleapis.com/v1"
        const val VISION_API_KEY = "AIzaSyCM5yUvSJpr2DZidA7qEg6dqSJSjCZNQYM"
        const val VISION_API_PROJECT_ID = "odml-codelabs"
        const val VISION_API_LOCATION_ID = "us-east1"
        const val VISION_API_PRODUCT_SET_ID = "product_set0"
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun annotateImage(image: Bitmap):Set<String>{
        // val apiSource = TaskCompletionSource<List<DetectResultResponse>>()

        val base64 = convertBitmapToBase64(image)

        // println("base64: " + base64)

        val requestJson = JSONObject("""
            {
              "requests": [
                {
                  "image": {
                    "content": """".trimIndent() + base64 + """"
                  },
                  "features": [
                    {
                      "maxResults": $VISION_API_PRODUCT_MAX_RESULT,
                      "type": "OBJECT_LOCALIZATION"
                    },
                  ]
                }
              ]
            }
        """.trimIndent())

        // println("requestJson: " + requestJson.toString())

        val payload = requestJson.toString()
        val requestBody = payload.toRequestBody()
        val request = Request.Builder()
            .url("$VISION_API_URL/images:annotate?key=$VISION_API_KEY")
            .post(requestBody)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        // println("Response: " + response)
        val body = response?.body?.string()
        // println("Body: " + body)

        val gson = GsonBuilder().create()
        var ObjectDetection = gson.fromJson(body, DetectResultResponse::class.java)

        var objectNames = mutableSetOf<String>()

        for (item in ObjectDetection.responses[0].localizedObjectAnnotations){
            objectNames.add(item.name.toString())
        }

        // println("objectNames: " + objectNames)
        // println(ObjectDetection.objectDetectResults?.localizedObjectAnnotations)

        return objectNames
    }

}
