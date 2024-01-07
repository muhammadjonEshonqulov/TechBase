package uz.tech.base.basethings

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import uz.tech.base.utils.lg

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {
    private var isUseBackPress = true

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        return binding.root
    }


    var loaderInterfacee: LoaderInterface? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            loaderInterfacee = activity as LoaderInterface
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity error")
        }
    }

    fun closeLoader() {
        loaderInterfacee?.closeLoader()
    }

    fun showLoader() {
        loaderInterfacee?.showLoader()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, e ->
            if (keyCode == KeyEvent.KEYCODE_BACK && e.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.FLAG_SOFT_KEYBOARD) {
                isUseBackPress = true
                onBackPressed()
                return@setOnKeyListener isUseBackPress
            } else return@setOnKeyListener false
        }
        onViewCreatedd(view, savedInstanceState)
    }

    abstract fun onViewCreatedd(view: View, savedInstanceState: Bundle?)

    open fun onBackPressed() {
        isUseBackPress = false
    }

    fun finish() {
        findNavController().popBackStack()
    }

    fun <T> finish(key: String, value: T) {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(key, value)
        navController.popBackStack()
    }

    fun hideKeyBoard() {
        val view = activity?.currentFocus ?: View(activity)
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyBoard(view: EditText) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun snackBar(message: String) {
        try {
            binding.root.let {
                val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                val textView: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text)
                textView.maxLines = 4
                snackbar.show()
            }
        } catch (e: Exception) {
            lg("error in snackbar " + e.message.toString())
        }
    }
}