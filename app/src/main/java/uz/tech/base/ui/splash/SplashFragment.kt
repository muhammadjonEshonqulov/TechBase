package uz.tech.base.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.tech.base.basethings.BaseFragment
import uz.tech.base.R
import uz.tech.base.databinding.FragmentSplashBinding
import uz.tech.base.utils.Prefs
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