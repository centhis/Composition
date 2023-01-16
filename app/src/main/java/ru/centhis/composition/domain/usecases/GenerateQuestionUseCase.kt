package ru.centhis.composition.domain.usecases

import ru.centhis.composition.domain.entity.Question
import ru.centhis.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }
}