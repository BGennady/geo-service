package ru.netology.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

// активируем расширение Mockito для JUnit 5;
@ExtendWith(MockitoExtension.class)

public class MessageSenderImplTest {

    @Mock
    // мок для GeoService
    GeoService geoService;

    @Mock
    // мок для LocalizationService
    LocalizationService localizationService;

    @InjectMocks
    // внедряем моки в MessageSenderImpl
    MessageSenderImpl messageSender;

    Map<String, String> headers;

    @BeforeEach
    // создаем пустой объект HashMap для заголовка headers под каждый тест;
    public void setUp() {
        headers = new HashMap<>();
    }

    @Test
    void testToCheckRussianIp() {
        // устанавливаем заголовок с российским IP
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
        // настраиваем моки: для российского IP возвращается Location с Country.RUSSIA;
        when(geoService.byIp("172.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        // настраиваем LocalizationService: для России возвращаем русский текст;
        when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        // проверяем, что для российского IP возвращается русский текст;
        String message = messageSender.send(headers);
        assertEquals("Добро пожаловать", message);
    }

    @Test
    void testToCheckEanglishIp() {
        // устанавливаем заголовок с американским IP
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");
        // настраиваем моки: для американского IP возвращается Location с Country.USA;
        when(geoService.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, null, 0));
        // настраиваем LocalizationService: для США возвращаем английский текст;
        when(localizationService.locale(Country.USA)).thenReturn("Welсome");
        // проверяем, что для американского IP возвращается английский текст;
        String message = messageSender.send(headers);
        assertEquals("Welсome", message);
    }
}
