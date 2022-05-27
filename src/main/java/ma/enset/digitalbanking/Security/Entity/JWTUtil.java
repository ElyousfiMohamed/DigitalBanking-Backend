package ma.enset.digitalbanking.Security.Entity;

import java.util.Date;

public class JWTUtil {
    public static final String SECRET= "secret";
    public static final String AUTH_HEADER= "Authorization";
    public static final long EXPIRE_ACCESS= 60000;
    public static final long EXPIRE_REFRESH= 60000 * 15;
    public static final String TOKEN_PREFIX= "Bearer ";
}
