package com.project.mathg.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.mathg.R
import com.project.mathg.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("record", Context.MODE_PRIVATE)
        val highLevelMiniGame1 = sharedPreferences.getInt("levelMiniGame1", 0)
        val highScoreMiniGame1 = sharedPreferences.getInt("scoreMiniGame1", 0)
        val highLevelMiniGame2 = sharedPreferences.getInt("levelMiniGame2", 0)
        val highScoreMiniGame2 = sharedPreferences.getInt("scoreMiniGame2", 0)
        val highLevelMiniGame4 = sharedPreferences.getInt("levelMiniGame4", 0)
        val highScoreMiniGame4 = sharedPreferences.getInt("scoreMiniGame4", 0)
        binding.tvHighLevel1.text = highLevelMiniGame1.toString()
        binding.tvHighScore1.text = highScoreMiniGame1.toString()
        binding.tvHighLevel2.text = highLevelMiniGame2.toString()
        binding.tvHighScore2.text = highScoreMiniGame2.toString()
        binding.tvHighLevel4.text = highLevelMiniGame4.toString()
        binding.tvHighScore4.text = highScoreMiniGame4.toString()
        binding.imgGame1.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_MiniGame1Fragment)
        }
        binding.imgGame2.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_MiniGame2Fragment)
        }
        binding.imgGame4.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_MiniGame4Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
