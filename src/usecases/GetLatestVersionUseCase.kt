package usecases

import network.JBombHttp
import values.JBombUrls

class GetLatestVersionUseCase: UseCase<String> {
    override suspend fun invoke(): String {
        return JBombHttp.get<String>(JBombUrls.JBombVersionUrl).data.orEmpty().also {
            println("Latest version from ${JBombUrls.JBombVersionUrl} is $it")
        }
    }
}