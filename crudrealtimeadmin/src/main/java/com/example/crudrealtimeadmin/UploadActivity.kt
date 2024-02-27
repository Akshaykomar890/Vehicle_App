package com.example.crudrealtimeadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.crudrealtimeadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    lateinit var databasesReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            val ownerName = binding.uploadOwnerName.text.toString()
            val vehicleBrand = binding.uploadVehicleBrand.text.toString()
            val vehicleRTO = binding.uploadVehicleRTO.text.toString()
            val vehicleNumber = binding.uploadVehicleNumber.text.toString()
           binding.progressBar.visibility = View.VISIBLE
            binding.saveBtn.visibility = View.GONE

            databasesReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
            val vehicleData = Data(ownerName,vehicleBrand, vehicleRTO, vehicleNumber)

            databasesReference.child(vehicleNumber).setValue(vehicleData).addOnSuccessListener {
               binding.progressBar.visibility = View.GONE
                binding.uploadOwnerName.text.clear()
                binding.uploadVehicleBrand.text.clear()
                binding.uploadVehicleRTO.text.clear()
                binding.uploadVehicleNumber.text.clear()

                Toast.makeText(this@UploadActivity,"Successfully Uploaded",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@UploadActivity,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}