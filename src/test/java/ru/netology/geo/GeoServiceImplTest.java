package ru.netology.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// активируем расширение Mockito для JUnit 5;
@ExtendWith(MockitoExtension.class)
public class GeoServiceImplTest {

    private GeoService geoService;

    @BeforeEach
    void setUp() {
        geoService = new GeoServiceImpl();
    }

    @Test
    public void testByIpForRussianIP() {
        Location location = geoService.byIp("172.0.32.11");
        assertEquals("Moscow", location.getCity());
        assertEquals(Country.RUSSIA, location.getCountry());
        assertEquals("Lenina", location.getStreet());
        assertEquals(15, location.getBuiling());
    }

    @Test
    public void testByIpForUsaIP() {
        Location location = geoService.byIp("96.44.183.149");
        assertEquals("New York", location.getCity());
        assertEquals(Country.USA, location.getCountry());
        assertEquals("10th Avenue", location.getStreet());
        assertEquals(32, location.getBuiling());
    }

    @Test
    public void testByIpForLocalhost() {
        Location location = geoService.byIp("127.0.0.1");
        assertEquals(null, location.getCity());
        assertEquals(null, location.getCountry());
        assertEquals(null, location.getStreet());
        assertEquals(0, location.getBuiling());
    }

    @Test
    public void testByIpForUnknownIp() {
        Location location = geoService.byIp("123.45.67.89");
        assertNull(location);
    }
}
