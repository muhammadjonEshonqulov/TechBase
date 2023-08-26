package uz.teach.base.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.teach.base.basethings.BaseFragment
import uz.teach.base.R
import uz.teach.base.databinding.FragmentSplashBinding
import uz.teach.base.utils.Prefs
import uz.tsul.mobile.utils.navigateSafe
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    @Inject
    lateinit var prefs: Prefs

    override fun onViewCreatedd(view: View, savedInstanceState: Bundle?) {


        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                if (prefs.get(prefs.token, "") != "") {
                    findNavController().navigateSafe(R.id.action_splashFragment_to_student_mainFragment)
                } else {
                    findNavController().navigateSafe(R.id.action_splashFragment_to_loginFragment)
                }


            }
        }.start()
    }


}