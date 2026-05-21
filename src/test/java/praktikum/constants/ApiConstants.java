package praktikum.constants;

public class ApiConstants {

    // Base URL
    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    public static final String REGISTER = "/api/auth/register";
    public static final String LOGIN = "/api/auth/login";
    public static final String LOGOUT = "/api/auth/logout";
    public static final String USER = "/api/auth/user"; // GET/PATCH/DELETE
    public static final String TOKEN = "/api/auth/token"; // Обновление токена

    public static final String AUTH = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";

    public static final String APPLICATION_JSON = "application/json";

    public static final int DEFAULT_TIMEOUT = 10;
}