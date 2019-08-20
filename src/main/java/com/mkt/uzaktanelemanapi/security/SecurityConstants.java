package com.mkt.uzaktanelemanapi.security;

import com.mkt.uzaktanelemanapi.tools.BEAR;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 423_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/" + BEAR.version + "/user";
    public static final String LOGIN_URL = "/" + BEAR.version + "/user/login";
}
