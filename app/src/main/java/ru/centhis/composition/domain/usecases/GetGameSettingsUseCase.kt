package ru.centhis.composition.domain.usecases

import ru.centhis.composition.domain.entity.GameSettings
import ru.centhis.composition.domain.entity.Level
import ru.centhis.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }
}