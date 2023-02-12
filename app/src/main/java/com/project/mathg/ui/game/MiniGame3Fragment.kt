package com.project.mathg.ui.game

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.mathg.R
import com.project.mathg.databinding.FragmentMiniGame3Binding
import kotlin.random.Random

class MiniGame3Fragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentMiniGame3Binding? = null
    private val binding get() = _binding!!
    private var image: ImageView? = null
    private var level = 1
    private var operator: Char? = null
    private var randFrom: Int = 1
    private var randUntil: Int = 10
    private var rand1: Int = Random.nextInt(randFrom, randUntil)
    private var rand2: Int = Random.nextInt(randFrom, randUntil)
    private var rand12: Int = rand1 + rand2
    private var exp = "$rand1 $operator $rand2"
    private var randCorrectAnswerDialog =
        arrayListOf("آفرین", "احسنت", "عالی بود", "فوق العاده بود", "محشر بود")
    private var randWrongAnswerDialog =
        arrayListOf("مطمئنی?", "دوباره سعی کن!", "فکر نکنم!")
    private var randOperator: Int = Random.nextInt(0, 3)
    private var score = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiniGame3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("record", Context.MODE_PRIVATE)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}