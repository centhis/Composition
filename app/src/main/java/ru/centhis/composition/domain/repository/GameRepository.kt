package ru.centhis.composition.domain.repository

import ru.centhis.composition.domain.entity.GameSettings
import ru.centhis.composition.domain.entity.Level
import ru.centhis.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}