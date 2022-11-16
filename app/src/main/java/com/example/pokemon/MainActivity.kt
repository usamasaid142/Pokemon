package com.example.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
//    private fun isValidData(
//        serialmacinescan: String,
//        serialIMEIScan: String,
//        serialThemeScan: String,
//        traderNumber: String,
//        commit: String
//    ): Boolean {
//        var isvalid = true
//
//        if (serialmacinescan.trim().isNullOrEmpty()) {
//            binding.etScanserial.error = "required"
//            isvalid = false
//        } else {
//            binding.etScanserial.error = null
//            isvalid = true
//        }
//        if (serialIMEIScan.trim().isNullOrEmpty()) {
//            binding.etScanIMEI.error = "required"
//            isvalid = false
//        } else {
//            binding.etScanIMEI.error = null
//            isvalid = true
//        }
//        if (serialThemeScan.trim().isNullOrEmpty()) {
//            binding.etScantheme.error = "required"
//            isvalid = false
//        } else {
//            binding.etScantheme.error = null
//            isvalid = true
//        }
//        if (traderNumber.trim().isNullOrEmpty()) {
//            binding.etTrader.error = "required"
//            isvalid = false
//        } else {
//            binding.etTrader.error = null
//            isvalid = true
//        }
//        if (commit.trim().isNullOrEmpty()) {
//            binding.etCommit.error = "required"
//            isvalid = false
//        } else {
//            binding.etCommit.error = null
//            isvalid = true
//        }
//
//        return isvalid
//    }
}