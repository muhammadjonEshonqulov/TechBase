//package uz.tsul.mobile.base
//
//import android.content.Context
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.text.Html
//import android.view.LayoutInflater
//import androidx.appcompat.app.AlertDialog
//import uz.tsul.mobile.R
//import uz.tsul.mobile.databinding.DialogMessageBinding
//import uz.teach.utils.blockClickable
//import uz.tsul.mobile.utils.theme.ClassicTheme
//import uz.tsul.mobile.utils.theme.ThemeManager
//
//class MessageDialog : AlertDialog {
//
//    var cancel_listener_onclick: (() -> Unit)? = null
//    var binding: DialogMessageBinding
//
//    fun setOnCancelClick(l: (() -> Unit)?) {
//        cancel_listener_onclick = l
//    }
//
//    constructor(context: Context, title: String, message: String) : super(context) {
//        this.setCancelable(true)
//        val view = LayoutInflater.from(context).inflate(R.layout.dialog_message, null, false)
//        binding = DialogMessageBinding.bind(view)
//        val theme = ThemeManager(binding.root.context).currentTheme
//        if (theme.id == ClassicTheme().id) {
//            binding.logoutBack.setCardBackgroundColor(theme.backgroundColor)
//        } else {
//            binding.logoutBack.setCardBackgroundColor(theme.colorPrimary)
//        }
//        binding.messageTittle.text = title
//        binding.message.text = Html.fromHtml(message)
//
//        binding.messageTittle.setTextColor(theme.textColor)
//        binding.message.setTextColor(theme.defTextColor)
//        view?.apply {
//            binding.cancel.setOnClickListener {
//                it.blockClickable()
//                cancel_listener_onclick?.invoke()
//            }
//        }
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        setView(view)
//    }
//}