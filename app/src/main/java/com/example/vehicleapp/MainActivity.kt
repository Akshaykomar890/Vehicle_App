package com.example.vehicleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.vehicleapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchBtn.setOnClickListener {
            binding.progressBar.visibility =View.VISIBLE
            binding.searchBtn.visibility =View.GONE
            val searchVehicleNumber:String = binding.searchNumber.text.toString()
            if(searchVehicleNumber.isNotEmpty()){
                readData(searchVehicleNumber)
            }
            else{
                Toast.makeText(this@MainActivity,"Please enter vehicle number",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                binding.searchBtn.visibility = View.VISIBLE
            }
        }



    }
    fun readData(vehicleNumber:String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).get().addOnSuccessListener {
            if (it.exists()){
                val ownerName = it.child("ownerName").value
                val vehicleBrand = it.child("vehicleBrand").value
                val vehicleRTO = it.child("vehicleRTO").value
                Toast.makeText(this@MainActivity,"Results Found",Toast.LENGTH_SHORT).show()
                binding.searchNumber.text.clear()
                binding.readOwnerName.text = ownerName.toString()
                binding.readVehicleBrand.text = vehicleBrand.toString()
                binding.readVehicleRTO.text = vehicleRTO.toString()
                binding.progressBar.visibility = View.GONE
                binding.searchBtn.visibility  = View.VISIBLE
            }
            else{
                Toast.makeText(this@MainActivity,"Vehicle Number does not exist",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                binding.searchBtn.visibility  = View.VISIBLE
            }
        }.addOnFailureListener {
            Toast.makeText(this@MainActivity,"Something went wrong",Toast.LENGTH_SHORT).show()

        }
    }
}