package uz.tech.base.basethings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import uz.tech.base.R
import uz.tech.base.databinding.DialogEditBinding
import uz.tech.base.utils.blockClickable

class EditDialog : AlertDialog {


    var submit_listener_onclick: (() -> Unit)? = null
    var binding: DialogEditBinding

    fun setOnSubmitClick(l: (() -> Unit)?) {
        submit_listener_onclick = l
    }

    constructor(context: Context) : super(context) {
        this.setCancelable(true)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null, false)
        binding = DialogEditBinding.bind(view)

        view?.apply {

            binding.submit.setOnClickListener {
                it.blockClickable()
                submit_listener_onclick?.invoke()
            }
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setView(view)
    }
}