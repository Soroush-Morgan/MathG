package com.project.mathg.ui.game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.mathg.R
import com.project.mathg.adapter.GVAdapter
import com.project.mathg.databinding.FragmentMiniGame3Binding
import kotlin.random.Random

class MiniGame3Fragment : Fragment() {
    private lateinit var itemGV: GridView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var timer: CountDownTimer
    private var _binding: FragmentMiniGame3Binding? = null
    private val binding get() = _binding!!
    private var level = 1
    private var list = arrayListOf<Pair<String, Int>>()
    private var operator: Char? = null
    private var randFrom: Int = 1
    private var randUntil: Int = 10
    private var rand1: Int = Random.nextInt(randFrom, randUntil)
    private var rand2: Int = Random.nextInt(randFrom, randUntil)
    private var rand12: Int = rand1 + rand2
    private var exp = "$rand1 $operator $rand2"
    private var randOperator: Int = Random.nextInt(0, 3)
    private var score = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiniGame3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    timer.cancel()
                    findNavController().navigate(R.id.action_MiniGame3Fragment_to_HomeFragment)
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
                val highLevel = sharedPreferences.getInt("levelMiniGame3", 0)
                val highScore = sharedPreferences.getInt("scoreMiniGame3", 0)
                if (highLevel <= level) {
                    sharedPreferences.edit().putInt("levelMiniGame3", level).apply()
                }
                if (highScore <= score) {
                    sharedPreferences.edit().putInt("scoreMiniGame3", score).apply()
                }
                findNavController().navigate(R.id.action_MiniGame3Fragment_to_HomeFragment)
            }
        }
        itemGV = binding.table
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
        exp = "$rand1 $operator $rand2"
        list.add(Pair(exp, rand12))
        list()
        val itemAdapter = GVAdapter(itemList = list)
        itemGV.adapter = itemAdapter
        timer.start()
    }

    private fun list() {
        listGenerator()
        Log.e("soroush", list.size.toString())
    }

    private fun listGenerator() {
        list.forEach {
            rand1 = Random.nextInt(randFrom, randUntil)
            rand2 = Random.nextInt(randFrom, randUntil)
            randOperator = Random.nextInt(0, 4)
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
            exp = "$rand1 $operator $rand2"
            if (it.second != rand12 && list.size <= 8)
                list.add(Pair(exp, rand12))
        }
    }

    override fun onDestroyView() {
        timer.cancel()
        _binding = null
        super.onDestroyView()
    }
}