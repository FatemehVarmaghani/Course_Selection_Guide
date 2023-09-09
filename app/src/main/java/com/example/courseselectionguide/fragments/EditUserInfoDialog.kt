package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.courseselectionguide.databinding.DialogEditUserInfoBinding

class EditUserInfoDialog: DialogFragment() {
    private lateinit var binding: DialogEditUserInfoBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context)
        binding = DialogEditUserInfoBinding.inflate(layoutInflater, null, false)
        dialog.setView(binding.root)
        return dialog.create()
    }
}