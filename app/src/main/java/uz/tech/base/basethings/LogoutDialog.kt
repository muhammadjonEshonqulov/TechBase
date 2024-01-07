package uz.tech.base.basethings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import uz.tech.base.R
import uz.tech.base.databinding.DialogLogutBinding
import uz.tech.base.utils.blockClickable

class LogoutDialog : AlertDialog {



    var submit_listener_onclick: (() -> Unit)? = null
    var cancel_listener_onclick: (() -> Unit)? = null
    var binding: DialogLogutBinding

    fun setOnSubmitClick(l: (() -> Unit)?) {
        submit_listener_onclick = l
    }


    fun setOnCancelClick(l: (() -> Unit)?) {
        cancel_listener_onclick = l
    }

    constructor(context: Context) : super(context) {
        this.setCancelable(true)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_logut, null, false)
        binding = DialogLogutBinding.bind(view)

//        val theme = ThemeManager(binding.root.context).currentTheme
//        if (theme.id == ClassicTheme().id) {
//            binding.logoutBack.setCardBackgroundColor(theme.backgroundColor)
//        } else {
//            binding.logoutBack.setCardBackgroundColor(theme.colorPrimary)
//        }
//        binding.logoutTittle.setTextColor(theme.textColor)
//        binding.logoutMessage.setTextColor(theme.defTextColor)
        view?.apply {

            binding.logoutSubmit.setOnClickListener {
                it.blockClickable()
                submit_listener_onclick?.invoke()
            }
            binding.cancelLogout.setOnClickListener {
                it.blockClickable()
                cancel_listener_onclick?.invoke()
            }
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setView(view)
    }
}