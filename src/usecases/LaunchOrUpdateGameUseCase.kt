package usecases

import data.DataInputOutput
import kotlinx.coroutines.runBlocking
import model.ExecutionStatus
import tasks.JBombInstaller

class LaunchOrUpdateGameUseCase : UseCase<ExecutionStatus> {
    override suspend fun invoke(): ExecutionStatus {
        val latestVersionRepository = runBlocking {
            GetLatestVersionUseCase().invoke()
        }

        val latestVersionLocal = DataInputOutput.version

        println("Local version is $latestVersionLocal")

        return when {
            latestVersionLocal != latestVersionRepository -> {
                JBombInstaller().invoke()
            }

            else -> {
                println("JBomb is updated, version $latestVersionRepository")
                JBombLaunchUseCase().invoke()
                ExecutionStatus.READY_TO_LAUNCH
            }
        }
    }
}