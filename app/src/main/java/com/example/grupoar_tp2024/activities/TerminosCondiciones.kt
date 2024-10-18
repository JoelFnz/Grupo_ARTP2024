package com.example.grupoar_tp2024.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.grupoar_tp2024.R

class TerminosCondiciones : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.terminos_condiciones,null)
    dialog.setView(view)
        .setTitle("Terminos y Condiciones")
        .setNegativeButton("Cerrar"){dialog, _ -> dialog.dismiss()}
        return dialog.create()

    }
}