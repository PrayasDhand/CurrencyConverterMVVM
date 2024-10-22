import androidx.compose.ui.input.key.key
import com.example.currencyconvertormvvm.data.local.dao.CurrencyRateDao
import com.example.currencyconvertormvvm.data.local.mapper.toCurrencyRate
import com.example.currencyconvertormvvm.data.local.mapper.toCurrencyRateEntity
import com.example.currencyconvertormvvm.data.remote.CurrencyApi
import com.example.currencyconvertormvvm.data.remote.dto.ToCurrencyRates
import com.example.currencyconvertormvvm.domain.model.CurrencyRate
import com.example.currencyconvertormvvm.domain.model.Resource
import com.example.currencyconvertormvvm.domain.model.Resource.Success
import com.example.currencyconvertormvvm.domain.model.Resource.Error
import com.example.currencyconvertormvvm.domain.model.Resource.Loading
import com.example.currencyconvertormvvm.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

//..Rest of the imports

class CurrencyRepositoryImpl @Inject constructor (
    private val api: CurrencyApi,
    private val dao: CurrencyRateDao,
) : CurrencyRepository {

    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> =
        flow {

            val localCurrencyRates = getLocalCurrencyRates()
            emit(Success(localCurrencyRates))

            // Emit loading state initially

            try {
                val newRates = getRemoteCurrencyRates()
                updateLocalCurrencyRates(newRates)

                emit(Success(newRates))


            } catch (e: IOException) {
                emit(Error("Couldn't reach server. Check your internet connection.",
                    data = localCurrencyRates))
            } catch (e: Exception) {
                emit(Error("An unexpected error occurred: ${e.message}"))
            }
        }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return dao.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.ToCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }


}