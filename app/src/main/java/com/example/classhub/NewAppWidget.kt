package com.example.classhub

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Handler
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    private val handler = Handler()
    private val delayMillis = 1000L // 1 second

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val componentName = ComponentName(context, NewAppWidget::class.java)

        // Schedule a repeating update every second
        handler.post(object : Runnable {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                // Update the widget's content
                updateAppWidget(context, appWidgetManager, appWidgetIds)

                // Schedule the next update
                handler.postDelayed(this, delayMillis)
            }
        })
    }

    override fun onDisabled(context: Context) {
        // Remove any scheduled updates when the last widget is removed
        handler.removeCallbacksAndMessages(null)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: IntArray
) {
    val db = FirebaseFirestore.getInstance()

    val dayOfWeek = java.time.LocalDate.now().dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault())

    val currentTime = java.text.SimpleDateFormat("hh:mm", java.util.Locale.getDefault()).format(java.util.Date())

    val currentTime1 = java.text.SimpleDateFormat("hhmm", java.util.Locale.getDefault()).format(java.util.Date())

    val checkCurrentTime = currentTime

    val noClassDay = arrayOf("Sunday", "Saturday")


    if (dayOfWeek == noClassDay[0] || dayOfWeek == noClassDay[1]){

        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        val someValue = "No Class"
        views.setTextViewText(R.id.currentTime, currentTime)
        views.setTextViewText(R.id.subjectTxt, someValue)

        // Update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)

    }
    else {

        if (currentTime < )

        val docRef = db.collection("CSEab").document(dayOfWeek)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val data = document.data // Access your data here
                    val someValue = data?.get(currentTime) as String // Replace with your data field
                    val views = RemoteViews(context.packageName, R.layout.new_app_widget)

                    // Update the TextView in your widget with the Firestore data
                    views.setTextViewText(R.id.subjectTxt, someValue)
                    views.setTextViewText(R.id.currentTime, currentTime)

                    // Update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors here
            }

    }


}
