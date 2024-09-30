package ru.netology.sender;

import java.util.Map;

import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

public class MessageSenderImpl implements MessageSender {

    // заголовок, откуда будет извлекаться IP-адрес;
    public static final String IP_ADDRESS_HEADER = "x-real-ip";
    // сервис для определения страны по IP;
    private final GeoService geoService;
    // cервис для определения языка сообщения на основе страны;
    private final LocalizationService localizationService;

    // конструктор, инициализирующий сервисы GeoService и LocalizationService;
    public MessageSenderImpl(GeoService geoService, LocalizationService localizationService) {
        this.geoService = geoService;
        this.localizationService = localizationService;
    }

    // метод отправки сообщения;
    public String send(Map<String, String> headers) {
        // получаем IP-адрес из заголовков;
        String ipAddress = String.valueOf(headers.get(IP_ADDRESS_HEADER));
        // проверяем, что IP-адрес не пустой;
        if (ipAddress != null && !ipAddress.isEmpty()) {
            // определяем локацию по IP-адресу;
            Location location = geoService.byIp(ipAddress);
            // выводим сообщение в консоль;
            System.out.printf("Отправлено сообщение: %s", localizationService.locale(location.getCountry()));
            // возвращаем сообщение на языке страны;
            return localizationService.locale(location.getCountry());
        }
        // если IP-адрес отсутствует, сообщение на английском.
        return localizationService.locale(Country.USA);
    }
}
