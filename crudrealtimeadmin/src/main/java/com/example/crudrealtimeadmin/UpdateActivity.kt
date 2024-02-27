package com.example.crudrealtimeadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.crudrealtimeadmin.databinding.ActivityUpdateBinding
import com.example.crudrealtimeadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    lateinit var databaseReference:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateBtn.setOnClickListener {
            val vehicleNumber = binding.updateVehicleNumber.text.toString()
            val ownerName = binding.updateOwnerName.text.toString()
            val vehicleBrand = binding.updateVehicleBrand.text.toString()
            val vehicleRTO = binding.updateVehicleRTO.text.toString()
            binding.progressBar.visibility = View.VISIBLE
            binding.updateBtn.visibility = View.GONE
            update(vehicleNumber,ownerName,vehicleBrand,vehicleRTO)
        }

    }
    private fun update(vehicleNumber:String, ownerName:String, brand:String, RTO:String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")

        val vehicleData = mapOf<String,String>("ownerName" to ownerName,"vehicleBrand" to brand,"vehicleRTo" to RTO)

        databaseReference.child(vehicleNumber).updateChildren(vehicleData).addOnSuccessListener {
            binding.updateOwnerName.text.clear()
            binding.updateVehicleBrand.text.clear()
            binding.updateVehicleNumber.text.clear()
            binding.updateVehicleRTO.text.clear()
            binding.progressBar.visibility = View.GONE
            binding.updateBtn.visibility  = View.VISIBLE
            Toast.makeText(this@UpdateActivity,"Updated",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this@UpdateActivity,"Unable to update",Toast.LENGTH_SHORT).show()
        }

    }
}