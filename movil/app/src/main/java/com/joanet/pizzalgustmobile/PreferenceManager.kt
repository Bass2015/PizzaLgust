import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val AUTH_TOKEN_KEY = "AuthToken"

    fun init(context: Context) {
        getSharedPreferences(context)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, authToken: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(AUTH_TOKEN_KEY, authToken)
        editor.apply()
    }

    fun getAuthToken(context: Context): String {
        return getSharedPreferences(context).getString(AUTH_TOKEN_KEY, "") ?: ""
    }

    fun clearAuthToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(AUTH_TOKEN_KEY)
        editor.apply()
    }
}
