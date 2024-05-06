import com.joanet.pizzalgustmobile.*
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var call: Call<Login>

    lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivity = MainActivity()
        mainActivity.apiService = apiService
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `login with valid credentials`() {
        // Mock del objeto LoginRequest
        val loginRequest = Login("bwayne@gotham.com", "batman", "", "", "", "", false, "", "")

        // Mock de la respuesta exitosa del servicio API
        val loginResponse = Login("bwayne@gotham.com", "batman", "", "", "", "", false, "", "token")
        val response = Response.success(loginResponse)

        // Configurar el comportamiento del apiService para devolver el objeto Call mockeado
        every { apiService.getUserData(any()) } returns call
        every { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<Login>>()
            callback.onResponse(call, response)
        }

        // Llamar a la función login
        mainActivity.login(loginRequest.email, loginRequest.password)

        // Verificar que se llame al método adecuado en el apiService
        verify { apiService.getUserData(any()) }

        // Verificar que se muestra el mensaje correcto (esto debe adaptarse según tu implementación real)
        // Por ejemplo, verifica que se muestre una tostada con el mensaje "¡Correcto!"
        // o que se redirija a la actividad correcta.
        // También puedes verificar que se guarda el token en las preferencias compartidas.
    }
}
