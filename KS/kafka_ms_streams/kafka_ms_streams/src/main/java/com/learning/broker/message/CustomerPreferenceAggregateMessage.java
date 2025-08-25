package com.learning.broker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPreferenceAggregateMessage {

    private Map<String, String> wishlistItems;

    private Map<String, String> shoppingCartItems;

    // a standard way of writing date + time like YYYY-MM-DDTHH:mm:ss.
    public void putShoppingCarItem(String itemName, OffsetDateTime lastDateTime) {
        shoppingCartItems.put(itemName, DateTimeFormatter.ISO_DATE_TIME.format(lastDateTime));
    }

    public void putWishlistItem(String itemName, OffsetDateTime lastDateTime) {
        wishlistItems.put(itemName, DateTimeFormatter.ISO_WEEK_DATE.format(lastDateTime));
    }                                        // represent Year + Week number + Day of week.
}
