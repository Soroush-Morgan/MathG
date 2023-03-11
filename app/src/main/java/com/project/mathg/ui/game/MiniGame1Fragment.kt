package com.project.mathg.ui.game

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
import com.project.mathg.databinding.FragmentMiniGame1Binding
import kotlin.random.Random

class MiniGame1Fragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var timer: CountDownTimer
    private var animation: Animation? = null
    private var _binding: FragmentMiniGame1Binding? = null
    private val binding get() = _binding!!
    private var level = 1
    private var operator1: Char? = null
    private var operator2: Char? = null
    private var randFrom: Int = 1
    private var randUntil: Int = 10
    private var rand1: Int = Random.nextInt(randFrom, randUntil)
    private var rand2: Int = Random.nextInt(randFrom, randUntil)
    private var rand12 = 0
    private var rand3: Int = Random.nextInt(randFrom, randUntil)
    private var rand4: Int = Random.nextInt(randFrom, randUntil)
    private var rand34 = 0
    private var exp1 = "$rand1 $operator1 $rand2"
    private var exp2 = "$rand3 $operator2 $rand4"
    private var randCorrectAnswerDialog = arrayListOf("آفرین", "احسنت", "عالی بود", "فوق العاده بود", "محشر بود")
    private var randWrongAnswerDialog = arrayListOf("مطمئنی?", "دوباره سعی کن!", "فکر نکنم!")
    private var randOperator: Int = Random.nextInt(0, 16)
    private var score = 0
    private var lSlider = Slide()
    private var rSlider = Slide()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiniGame1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    timer.cancel()
                    findNavController().navigate(R.id.action_MiniGame1Fragment_to_HomeFragment)
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
                val highLevel = sharedPreferences.getInt("levelMiniGame1", 0)
                val highScore = sharedPreferences.getInt("scoreMiniGame1", 0)
                if (highLevel <= level) {
                    sharedPreferences.edit().putInt("levelMiniGame1", level).apply()
                }
                if (highScore <= score) {
                    sharedPreferences.edit().putInt("scoreMiniGame1", score).apply()
                }
                findNavController().navigate(R.id.action_MiniGame1Fragment_to_HomeFragment)
            }
        }
        timer.start()
        binding.tvResult.visibility = View.INVISIBLE
        questions()
    }

    private fun operatorChecker() {
        when (randOperator) {
            0 -> {
                rand12 = rand1 + rand2
                rand34 = rand3 + rand4
                operator1 = '+'
                operator2 = '+'
            }
            1 -> {
                rand12 = rand1 + rand2
                rand34 = rand3 - rand4
                operator1 = '+'
                operator2 = '-'
            }
            2 -> {
                rand12 = rand1 + rand2
                rand34 = rand3 * rand4
                operator1 = '+'
                operator2 = '*'
            }
            3 -> {
                rand12 = rand1 + rand2
                rand34 = rand3 / rand4
                operator1 = '+'
                operator2 = '/'
            }
            4 -> {
                rand12 = rand1 - rand2
                rand34 = rand3 + rand4
                operator1 = '-'
                operator2 = '+'
            }
            5 -> {
                rand12 = rand1 - rand2
                rand34 = rand3 - rand4
                operator1 = '-'
                operator2 = '-'
            }
            6 -> {
                rand12 = rand1 - rand2
                rand34 = rand3 * rand4
                operator1 = '-'
                operator2 = '*'
            }
            7 -> {
                rand12 = rand1 - rand2
                rand34 = rand3 / rand4
                operator1 = '-'
                operator2 = '/'
            }
            8 -> {
                rand12 = rand1 * rand2
                rand34 = rand3 + rand4
                operator1 = '*'
                operator2 = '+'
            }
            9 -> {
                rand12 = rand1 * rand2
                rand34 = rand3 - rand4
                operator1 = '*'
                operator2 = '-'
            }
            10 -> {
                rand12 = rand1 * rand2
                rand34 = rand3 * rand4
                operator1 = '*'
                operator2 = '*'
            }
            11 -> {
                rand12 = rand1 * rand2
                rand34 = rand3 / rand4
                operator1 = '*'
                operator2 = '/'
            }
            12 -> {
                rand12 = rand1 / rand2
                rand34 = rand3 + rand4
                operator1 = '/'
                operator2 = '+'
            }
            13 -> {
                rand12 = rand1 / rand2
                rand34 = rand3 - rand4
                operator1 = '/'
                operator2 = '-'
            }
            14 -> {
                rand12 = rand1 / rand2
                rand34 = rand3 * rand4
                operator1 = '/'
                operator2 = '*'
            }
            15 -> {
                rand12 = rand1 / rand2
                rand34 = rand3 / rand4
                operator1 = '/'
                operator2 = '/'
            }
        }
    }

    private fun problemsGenerator(boolean: Boolean) {
        binding.tvEquation1.visibility = View.GONE
        binding.tvEquation2.visibility = View.GONE
        if (score % 20 == 0 && boolean) {
            level++
            randFrom++
            randUntil++
            rand1 = Random.nextInt(randFrom, randUntil)
            rand2 = Random.nextInt(randFrom, randUntil)
            rand3 = Random.nextInt(randFrom, randUntil)
            rand4 = Random.nextInt(randFrom, randUntil)
            binding.tvLevel.text = level.toString()
        } else {
            rand1 = Random.nextInt(randFrom, randUntil)
            rand2 = Random.nextInt(randFrom, randUntil)
            rand3 = Random.nextInt(randFrom, randUntil)
            rand4 = Random.nextInt(randFrom, randUntil)
        }
        randOperator = Random.nextInt(0, 16)
        operatorChecker()
        exp1 = "$rand1 $operator1 $rand2"
        exp2 = "$rand3 $operator2 $rand4"
        binding.tvEquation1.text = exp1
        binding.tvEquation2.text = exp2
        binding.tvEquation1.visibility = View.VISIBLE
        binding.tvEquation2.visibility = View.VISIBLE
    }

    private fun questions() {
        operatorChecker()
        exp1 = "$rand1 $operator1 $rand2"
        exp2 = "$rand3 $operator2 $rand4"
        binding.tvEquation1.text = exp1
        binding.tvEquation2.text = exp2
        binding.imgGreaterThan.setOnClickListener {
            if (rand12 > rand34) {
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
            problemsGenerator(rand12 > rand34)
        }
        binding.imgLessThan.setOnClickListener {
            if (rand12 < rand34) {
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
            problemsGenerator(rand12 < rand34)
        }
        binding.imgEqual.setOnClickListener {
            if (rand12 == rand34) {
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
            problemsGenerator(rand12 == rand34)
        }
    }

    override fun onDestroyView() {
        timer.cancel()
        _binding = null
        super.onDestroyView()
    }
}