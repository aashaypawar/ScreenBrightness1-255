package com.example.ao

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Global.getInt
import android.provider.Settings.Secure.getInt
import android.widget.Button
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get turn off screen button
        // Get turn off screen button
        val turnOffScreenButton: Button = findViewById(R.id.turn_off_screen_button)
        turnOffScreenButton.setOnClickListener { // Get app context object.
            val context = applicationContext

            // Check whether has the write settings permission or not.
            val settingsCanWrite = hasWriteSettingsPermission(context)

            // If do not have then open the Can modify system settings panel.
            if (!settingsCanWrite) {
                changeWriteSettingsPermission(context)
            } else {
                changeScreenBrightness(context, 1)
            }
        }

        // Get turn on screen button

        // Get turn on screen button
        val turnOnScreenButton: Button = findViewById(R.id.turn_on_screen_button)
        turnOnScreenButton.setOnClickListener {
            val context = applicationContext

            // Check whether has the write settings permission or not.
            val settingsCanWrite = hasWriteSettingsPermission(context)

            // If do not have then open the Can modify system settings panel.
            if (!settingsCanWrite) {
                changeWriteSettingsPermission(context)
            } else {
                changeScreenBrightness(context, 255)
            }
        }
    }
    // Check whether this app has android write settings permission.
    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasWriteSettingsPermission(context: Context): Boolean {
        var ret = true
        // Get the result from below code.
        ret = Settings.System.canWrite(context)
        return ret
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private fun changeWriteSettingsPermission(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        context.startActivity(intent)
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private fun changeScreenBrightness(context: Context, screenBrightnessValue: Int) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue)

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    }
}