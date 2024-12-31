package com.example.demo.controllers.cart;

import com.example.demo.entities.order.Order;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Controller;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Controller
public class CartGraphQLController {
    @QueryMapping
    public Window<Order> customerOrders(
            @Argument("clientId") long clientId, ScrollSubrange subrange) {

        ScrollPosition scrollPosition = subrange.position().orElse(ScrollPosition.offset());
        Limit limit = Limit.of(subrange.count().orElse(10));

        return new Window<Order>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public List<Order> getContent() {
                return List.of();
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public ScrollPosition positionAt(int index) {
                return null;
            }

            @Override
            public <U> Window<U> map(Function<? super Order, ? extends U> converter) {
                return null;
            }

            @Override
            public Iterator<Order> iterator() {
                return null;
            }
        };
    }
}
