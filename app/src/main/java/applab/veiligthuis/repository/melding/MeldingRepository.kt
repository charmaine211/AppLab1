package applab.veiligthuis.repository.melding



import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun getMeldingen(paths: List<String>, meldingType: MeldingType): Flow<List<Melding?>>
    fun getMelding(meldingKey: String, meldingType: MeldingType): Flow<Melding>
    fun insertOrUpdateMelding(melding: Melding)
    fun deleteMelding(melding: Melding)
}