package ru.centhis.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import ru.centhis.composition.R
import ru.centhis.composition.databinding.FragmentGameFinishedBinding
import ru.centhis.composition.domain.entity.GameResult
import java.lang.RuntimeException

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    companion object {
        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        if (gameResult.winner)
            binding.emojiResult.setImageResource(R.drawable.ic_smile)
        else
            binding.emojiResult.setImageResource(R.drawable.ic_sad)
        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.tv_required_answers),
            gameResult.gameSettings.minCountOfRightAnswers
        )
        binding.tvScoreAnswers.text = String.format(
            getString(R.string.tv_score_answers),
            gameResult.countOfRightAnswers
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.tv_required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers
        )
        binding.tvScorePercentage.text = String.format(
            getString(R.string.tv_score_percentage),
            getPercentOfRightAnswers()
        )

    }

    private fun getPercentOfRightAnswers() = with(gameResult){
        if (countOfQuestions == 0){
            0
        } else{
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}