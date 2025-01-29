package com.cesantia.elections.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    /**
     * Crea una cookie HTTP-only con un nombre y valor específicos.
     *
     * @param name     Nombre de la cookie.
     * @param value    Valor de la cookie.
     * @param maxAge   Tiempo de expiración en segundos.
     * @param response Respuesta HTTP para añadir la cookie.
     */
    public void addHttpOnlyCookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Asegúrate de usar HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    /**
     * Elimina una cookie existente configurando su valor a null y maxAge a 0.
     *
     * @param name     Nombre de la cookie.
     * @param response Respuesta HTTP para eliminar la cookie.
     */
    public void deleteCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Asegúrate de usar HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
