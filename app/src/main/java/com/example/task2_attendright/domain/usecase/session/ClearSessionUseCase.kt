import com.example.task2_attendright.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClearSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        sessionRepository.clearSession()
    }
}