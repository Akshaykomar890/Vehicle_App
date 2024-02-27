package com.example.crudrealtimeadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.crudrealtimeadmin.databinding.ActivityDeleteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteBtn.setOnClickListener {
            val vehicleNumber = binding.searchNumberDelete.text.toString()
            binding.progressBar.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.GONE
            if (vehicleNumber.isNotEmpty()){
                delete(vehicleNumber)
            }
            else{
                Toast.makeText(this,"Please Enter Vehicle Number",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun delete(vehicleNumber:String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).removeValue().addOnSuccessListener {
            binding.searchNumberDelete.text.clear()
            Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show()
            binding.deleteBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener {
            Toast.makeText(this,"Unable to delete",Toast.LENGTH_SHORT).show()
        }


    }
}