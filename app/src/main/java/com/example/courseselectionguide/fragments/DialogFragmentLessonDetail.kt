package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.courseselectionguide.databinding.DialogFragmentLessonDetailBinding

class DialogFragmentLessonDetail: DialogFragment() {
    private lateinit var binding: DialogFragmentLessonDetailBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentLessonDetailBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context)
        return dialog.create()
    }
}