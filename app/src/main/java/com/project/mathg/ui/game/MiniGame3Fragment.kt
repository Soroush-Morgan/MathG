package com.project.mathg.ui.game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mathg.adapter.RecyclerViewAdapter
import com.project.mathg.databinding.FragmentMiniGame3Binding
import com.project.mathg.model.ListItem
import com.project.mathg.R
import kotlin.random.Random

@Suppress("DEPRECATION")
class MiniGame3Fragment : Fragment(), RecyclerViewAdapter.ItemChecker {
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentMiniGame3Binding? = null
    private val binding get() = _binding!!
    private var clickedCounter = 0
    private var health = 5
    private var level = 1
    private var operator: Char? = null
    private var randFrom: Int = 1
    private var randUntil: Int = 10
    private var rand1: Int = Random.nextInt(randFrom, randUntil)
    private var rand2: Int = Random.nextInt(randFrom, randUntil)
    private var rand12 = 0
    private var exp = "$rand1 $operator $rand2"
    private var randOperator: Int = Random.nextInt(0, 3)
    private var list = ArrayList<ListItem>()
    private var pairList = arrayListOf<Pair<String, Int>>()
    private var firstClicked: ListItem? = null
    private var firstPosition: Int? = null
    private var secondClicked: ListItem? = null
    private var secondPosition: Int? = null
    private var answerId = 0
    private var adapter = RecyclerViewAdapter(list, this)
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
                    findNavController().navigate(R.id.action_MiniGame3Fragment_to_HomeFragment)
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("record", Context.MODE_PRIVATE)

        list()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
    }

    private fun clearClick() {
        android.os.Handler().postDelayed({
            firstClicked = null
            firstPosition = null
            secondClicked = null
            secondPosition = null
            binding.tvEquation1.text = "?"
            binding.tvEquation2.text = "?"
        }, 1000)
    }

    private fun gameOver() {
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

    private fun list() {
        operatorChecker()
        exp = "$rand1 $operator $rand2"
        pairList.add(Pair(exp, rand12))
        listGenerator()
        pairList.forEach {
            if (answerId <= 7) {
                list.add(
                    ListItem(
                        it.first, answerId,
                        isCardBack = false,
                        isClickable = false,
                        isMatch = false
                    )
                )
                list.add(
                    ListItem(
                        it.second.toString(),
                        answerId,
                        isCardBack = false,
                        isClickable = false,
                        isMatch = false
                    )
                )
            }
            answerId++
        }
        list.shuffle()
    }

    private fun listGenerator() {
        rand1 = Random.nextInt(randFrom, randUntil)
        rand2 = Random.nextInt(randFrom, randUntil)
        randOperator = Random.nextInt(0, 4)
        operatorChecker()
        exp = "$rand1 $operator $rand2"
        if (pairList.size <= 7) {
            if (pairList.none {
                    it.second == rand12
                }) {
                pairList.add(Pair(exp, rand12))
            }
            listGenerator()
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
                if (rand1 % rand2 == 0) {
                    rand12 = rand1 / rand2
                    operator = '/'
                } else {
                    rand12 = rand1 + rand2
                    operator = '+'
                }
            }
        }
    }

    override fun clicked(listItem: ListItem, position: Int) {
        when (clickedCounter) {
            0 -> {
                clickedCounter++
                firstClicked = listItem
                firstPosition = position
                listItem.isClickable = false
                binding.tvEquation1.text = listItem.answer
            }
            1 -> {
                if (listItem.isClickable) {
                    clickedCounter++
                    secondClicked = listItem
                    secondPosition = position
                    listItem.isClickable = false
                    binding.tvEquation2.text = listItem.answer
                }
            }
        }
        if (clickedCounter == 2) {
            if (firstClicked?.id == secondClicked?.id) {
                binding.tvScore.text = score.inc().toString()
                score++
                firstClicked?.isClickable = false
                secondClicked?.isClickable = false
                firstClicked?.isCardBack = false
                secondClicked?.isCardBack = false
                firstClicked?.isMatch = true
                secondClicked?.isMatch = true
                clearClick()
            } else if (health == 0) {
                gameOver()
            } else {
                binding.tvHealth.text = health.dec().toString()
                health--
                firstClicked?.answer = "?"
                secondClicked?.answer = "?"
                adapter.notifyItemChanged(firstPosition!!)
                adapter.notifyItemChanged(secondPosition!!)
                firstClicked?.isClickable = true
                secondClicked?.isClickable = true
                firstClicked?.isCardBack = true
                secondClicked?.isCardBack = true
                firstClicked?.isMatch = false
                secondClicked?.isMatch = false
                clearClick()
            }
            clickedCounter = 0
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
