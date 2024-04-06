import com.ocado.basket.BasketSplitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketSplitterTest {
    private BasketSplitter basketSplitter;

    @BeforeEach
    void setUp() {
        String absolutePathToConfigFile = "src/main/resources/config.json";
        basketSplitter = new BasketSplitter(absolutePathToConfigFile);
    }

    @Test
    void splitTest1() {
        List<String> items = Arrays.asList("Cookies Oatmeal Raisin", "Tea - Apple Green Tea", "Sugar - Cubes", "Garlic - Peeled", "Dried Peach");

        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(2, result.size());
        assertEquals(1, result.get("Pick-up point").size());
        assertEquals(4, result.get("Same day delivery").size());
    }

    @Test
    void splitTest2() {
        List<String> items = Arrays.asList("Onions - White", "Cabbage - Nappa", "Wine - Sherry Dry Sack, William", "Carbonated Water - Raspberry", "Nantucket Apple Juice", "Dried Peach", "Chickhen - Chicken Phyllo", "Cheese - Sheep Milk", "Apples - Spartan", "Dc Hikiage Hira Huba");

        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(4, result.size());
        assertEquals(5, result.get("Pick-up point").size());
        assertEquals(2, result.get("Mailbox delivery").size());
        assertEquals(2, result.get("In-store pick-up").size());
        assertEquals(1, result.get("Same day delivery").size());
    }

    @Test
    void splitTest3() {
        List<String> items = Arrays.asList("Cheese - Sheep Milk", "Chickhen - Chicken Phyllo", "Cake - Miini Cheesecake Cherry", "Garlic - Peeled");

        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(4, result.size());
        assertEquals(1, result.get("Pick-up point").size());
        assertEquals(1, result.get("Courier").size());
        assertEquals(1, result.get("Mailbox delivery").size());
        assertEquals(1, result.get("Same day delivery").size());
    }

    @Test
    void splitTest4() {
        List<String> items = Arrays.asList("Cheese - Sheep Milk", "Chickhen - Chicken Phyllo", "Cake - Miini Cheesecake Cherry", "Garlic - Peeled");

        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(4, result.size());
        assertEquals(1, result.get("Pick-up point").size());
        assertEquals(1, result.get("Courier").size());
        assertEquals(1, result.get("Mailbox delivery").size());
        assertEquals(1, result.get("Same day delivery").size());
    }

    @Test
    void splitTest5() {
        List<String> items = Arrays.asList("Chocolate - Unsweetened", "Beer - Alexander Kieths, Pale Ale", "Sole - Dover, Whole, Fresh", "Pork Ham Prager", "Pork Ham Prager", "Wine - Port Late Bottled Vintage", "Salt - Rock, Course");

        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(2, result.size());
        assertEquals(6, result.get("In-store pick-up").size());
        assertEquals(1, result.get("Parcel locker").size());
    }

    @Test
    void getBestDeliveryMethodTest() {
        assertEquals("Pick-up point", basketSplitter.getBestDeliveryMethod(Map.of("Pick-up point", 3, "Courier", 2, "Mailbox delivery", 1, "Next day shipping", 1)));
        assertEquals("Courier", basketSplitter.getBestDeliveryMethod(Map.of("Pick-up point", 2, "Courier", 3, "Mailbox delivery", 1, "Next day shipping", 1)));
        assertEquals("Mailbox delivery", basketSplitter.getBestDeliveryMethod(Map.of("Pick-up point", 2, "Courier", 2, "Mailbox delivery", 3, "Next day shipping", 2)));
        assertEquals("Next day shipping", basketSplitter.getBestDeliveryMethod(Map.of("Pick-up point", 1, "Courier", 2, "Mailbox delivery", 1, "Next day shipping", 4)));
    }
}
