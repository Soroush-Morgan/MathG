package com.project.mathg.ui.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Slide
import android.transition.TransitionManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.mathg.R
import com.project.mathg.databinding.FragmentMiniGame4Binding
import kotlin.random.Random

class MiniGame4Fragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var timer: CountDownTimer
    private var animation: Animation? = null
    private var _binding: FragmentMiniGame4Binding? = null
    private val binding get() = _binding!!
    private var level = 1
    private var operator: Char? = null
    private var randFrom: Int = 1
    private var randUntil: Int = 10
    private var rand1: Int = Random.nextInt(randFrom, randUntil)
    private var rand2: Int = Random.nextInt(randFrom, randUntil)
    private var rand12 = 0
    private var exp = "$rand1 $operator $rand2"
    private var randCorrectAnswer = Random.nextInt(0, 4)
    private var randCorrectAnswerDialog = arrayListOf("آفرین", "احسنت", "عالی بود", "فوق العاده بود", "محشر بود")
    private var randWrongAnswerDialog = arrayListOf("مطمئنی?", "دوباره سعی کن!", "فکر نکنم!")
    private var randOperator: Int = Random.nextInt(0, 4)
    private var score = 0
    private var lSlider = Slide()
    private var rSlider = Slide()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiniGame4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    timer.cancel()
                    findNavController().navigate(R.id.action_MiniGame4Fragment_to_HomeFragment)
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("record", Context.MODE_PRIVATE)
        var timeCount = 60.0
        timer = object : CountDownTimer(60000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = String.format("%.1f", timeCount)
                timeCount -= 0.1
            }

            override fun onFinish() {
                val highLevel = sharedPreferences.getInt("levelMiniGame4", 0)
                val highScore = sharedPreferences.getInt("scoreMiniGame4", 0)
                if (highLevel <= level) {
                    sharedPreferences.edit().putInt("levelMiniGame4", level).apply()
                }
                if (highScore <= score) {
                    sharedPreferences.edit().putInt("scoreMiniGame4", score).apply()
                }
                findNavController().navigate(R.id.action_MiniGame4Fragment_to_HomeFragment)
            }
        }
        timer.start()
        binding.tvResult.visibility = View.INVISIBLE
        questions()
    }

    @SuppressLint("SetTextI18n")
    private fun answersChecker() {
        when (randCorrectAnswer) {
            0 -> {
                binding.btnAnswer1.text = rand12.toString()
                binding.btnAnswer2.text = (rand12 + Random.nextInt(1, 4)).toString()
                binding.btnAnswer3.text = (rand12 - Random.nextInt(1, 4)).toString()
                binding.btnAnswer4.text = (rand12 + Random.nextInt(4, 6)).toString()
            }
            1 -> {
                binding.btnAnswer1.text = (rand12 + Random.nextInt(1, 4)).toString()
                binding.btnAnswer2.text = rand12.toString()
                binding.btnAnswer3.text = (rand12 - Random.nextInt(1, 4)).toString()
                binding.btnAnswer4.text = (rand12 + Random.nextInt(4, 6)).toString()
            }
            2 -> {
                binding.btnAnswer1.text = (rand12 + Random.nextInt(1, 4)).toString()
                binding.btnAnswer2.text = (rand12 - Random.nextInt(1, 4)).toString()
                binding.btnAnswer3.text = rand12.toString()
                binding.btnAnswer4.text = (rand12 + Random.nextInt(4, 6)).toString()
            }
            3 -> {
                binding.btnAnswer1.text = (rand12 + Random.nextInt(1, 4)).toString()
                binding.btnAnswer2.text = (rand12 - Random.nextInt(1, 4)).toString()
                binding.btnAnswer3.text = (rand12 + Random.nextInt(4, 6)).toString()
                binding.btnAnswer4.text = rand12.toString()
            }
        }
    }

    private fun operatorChecker() {
        when (randOperator) {
            0 -> {
                rand12 = rand1 + rand2
                operator = '+'
            }
            1 -> {
                rand12 = rand1 - rand2
                operator = '-'
            }
            2 -> {
                rand12 = rand1 * rand2
                operator = '*'
            }
            3 -> {
                rand12 = rand1 / rand2
                operator = '/'
            }
        }
    }

    private fun problemsGenerator(boolean: Boolean) {
        binding.tvEquation.visibility = View.GONE
        if (score % 10 == 0 && boolean) {
            level++
            randFrom++
            randUntil++
            rand1 = Random.nextInt(randFrom, randUntil)
            rand2 = Random.nextInt(randFrom, randUntil)
            binding.tvLevel.text = level.toString()
        } else {
            rand1 = Random.nextInt(randFrom, randUntil)
            rand2 = Random.nextInt(randFrom, randUntil)
        }
        randOperator = Random.nextInt(0, 4)
        operatorChecker()
        exp = "$rand1 $operator $rand2"
        randCorrectAnswer = Random.nextInt(0, 4)
        answersChecker()
        binding.tvEquation.text = exp
        binding.tvEquation.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun questions() {
        operatorChecker()
        randCorrectAnswer = Random.nextInt(0, 4)
        answersChecker()
        exp = "$rand1 $operator $rand2"
        binding.tvEquation.text = exp
        binding.btnAnswer1.setOnClickListener {
            if (randCorrectAnswer == 0) {
                binding.tvResult.text = randCorrectAnswerDialog[Random.nextInt(0, 5)]
                binding.tvResult.setTextColor(Color.parseColor("#006400"))
                rSlider.slideEdge = Gravity.END
                TransitionManager.beginDelayedTransition(binding.constraintLayout, rSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
                binding.tvScore.text = score.inc().toString()
                score++
            } else {
                binding.tvResult.text = randWrongAnswerDialog[Random.nextInt(0, 3)]
                binding.tvResult.setTextColor(Color.parseColor("#640000"))
                lSlider.slideEdge = Gravity.START
                TransitionManager.beginDelayedTransition(binding.constraintLayout, lSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
            }
            problemsGenerator(randCorrectAnswer == 0)
        }
        binding.btnAnswer2.setOnClickListener {
            if (randCorrectAnswer == 1) {
                binding.tvResult.text = randCorrectAnswerDialog[Random.nextInt(0, 5)]
                binding.tvResult.setTextColor(Color.parseColor("#006400"))
                rSlider.slideEdge = Gravity.END
                TransitionManager.beginDelayedTransition(binding.constraintLayout, rSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
                binding.tvScore.text = score.inc().toString()
                score++
            } else {
                binding.tvResult.text = randWrongAnswerDialog[Random.nextInt(0, 3)]
                binding.tvResult.setTextColor(Color.parseColor("#640000"))
                lSlider.slideEdge = Gravity.START
                TransitionManager.beginDelayedTransition(binding.constraintLayout, lSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
            }
            problemsGenerator(randCorrectAnswer == 1)
        }
        binding.btnAnswer3.setOnClickListener {
            if (randCorrectAnswer == 2) {
                binding.tvResult.text = randCorrectAnswerDialog[Random.nextInt(0, 5)]
                binding.tvResult.setTextColor(Color.parseColor("#006400"))
                rSlider.slideEdge = Gravity.END
                TransitionManager.beginDelayedTransition(binding.constraintLayout, rSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
                binding.tvScore.text = score.inc().toString()
                score++
            } else {
                binding.tvResult.text = randWrongAnswerDialog[Random.nextInt(0, 3)]
                binding.tvResult.setTextColor(Color.parseColor("#640000"))
                lSlider.slideEdge = Gravity.START
                TransitionManager.beginDelayedTransition(binding.constraintLayout, lSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
            }
            problemsGenerator(randCorrectAnswer == 2)
        }
        binding.btnAnswer4.setOnClickListener {
            if (randCorrectAnswer == 3) {
                binding.tvResult.text = randCorrectAnswerDialog[Random.nextInt(0, 5)]
                binding.tvResult.setTextColor(Color.parseColor("#006400"))
                rSlider.slideEdge = Gravity.END
                TransitionManager.beginDelayedTransition(binding.constraintLayout, rSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
                binding.tvScore.text = score.inc().toString()
                score++
            } else {
                binding.tvResult.text = randWrongAnswerDialog[Random.nextInt(0, 3)]
                binding.tvResult.setTextColor(Color.parseColor("#640000"))
                lSlider.slideEdge = Gravity.START
                TransitionManager.beginDelayedTransition(binding.constraintLayout, lSlider)
                binding.tvResult.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                binding.tvResult.startAnimation(animation)
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvResult.startAnimation(animation)
                binding.tvResult.visibility = View.GONE
            }
            problemsGenerator(randCorrectAnswer == 3)
        }
    }

    override fun onDestroyView() {
        timer.cancel()
        _binding = null
        super.onDestroyView()
    }
}
