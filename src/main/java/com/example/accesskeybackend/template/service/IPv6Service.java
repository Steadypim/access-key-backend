package com.example.accesskeybackend.template.service;

import org.springframework.stereotype.Service;
import java.net.*;

/**
 * Сервис проверки поддержки IPv6 сайтом
 */
@Service
public class IPv6Service {
    /**
     * В методе checkIPv6Support мы принимаем URL сайта в виде строки siteUrl,
     * затем создаем объект URL из этой строки.
     * Далее мы получаем объект InetAddress для хоста сайта с помощью метода getByName и проверяем,
     * является ли он экземпляром класса Inet6Address с помощью оператора instanceof.
     * @param siteUrl URL/домен сайта для проверки
     * @return Если хост сайта поддерживает IPv6, то метод вернет true, иначе - false.
     */
    public boolean checkIPv6Support(String siteUrl) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName(siteUrl);
            for (InetAddress address : addresses) {
                if (address instanceof Inet6Address) {
                    return true;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }
}