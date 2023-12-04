package pl.kondziet.springbackend.application.domain.service;

import org.junit.jupiter.api.Test;
import pl.kondziet.springbackend.application.domain.model.entity.Balance;
import pl.kondziet.springbackend.application.domain.model.entity.Money;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SortedContainerTest {

    @Test
    void testSortedBalanceContainerOperations() {
        List<Balance> balances = generateBalances();

        SortedContainer<Balance> container = SortedContainer.of(balances);

        assertEquals(20, container.size());
        assertFalse(container.isEmpty());
        assertEquals(new Money("USD", BigDecimal.valueOf(98.76)), container.getMax().getMoney());
        assertEquals(new Money("USD", BigDecimal.valueOf(12.10)), container.getMin().getMoney());

        assertEquals(new Money("USD", BigDecimal.valueOf(98.76)), container.get(0).getMoney());
        assertEquals(new Money("USD", BigDecimal.valueOf(98.20)), container.get(1).getMoney());
        assertEquals(new Money("USD", BigDecimal.valueOf(89.32)), container.get(2).getMoney());
        assertEquals(new Money("USD", BigDecimal.valueOf(89.12)), container.get(3).getMoney());

        container.updateMax(new Balance(new UserId(UUID.fromString("e66c6de1-b09a-4d3e-9eb4-3a5d72fc7a3d")), new Money("USD", BigDecimal.valueOf(123.45))));
        assertEquals(new Money("USD", BigDecimal.valueOf(123.45)), container.getMax().getMoney());

        container.updateMin(new Balance(new UserId(UUID.fromString("2aeb18f8-05c6-4e15-8cb3-983e77d862cf")), new Money("USD", BigDecimal.valueOf(1.00))));
        assertEquals(new Money("USD", BigDecimal.valueOf(1.00)), container.getMin().getMoney());

        assertEquals(new Money("USD", BigDecimal.valueOf(123.45)), container.removeMax().getMoney());
        assertEquals(19, container.size());

        assertEquals(new Money("USD", BigDecimal.valueOf(1.00)), container.removeMin().getMoney());
        assertEquals(18, container.size());

        container.updateMin(new Balance(new UserId(UUID.fromString("2aeb18f8-05c6-4e15-8cb3-983e77d862cf")), new Money("USD", BigDecimal.valueOf(50.25))));
        assertEquals(new Money("USD", BigDecimal.valueOf(50.25)), container.get(8).getMoney());

        container.updateMax(new Balance(new UserId(UUID.fromString("6785a0b4-6c8f-4cf9-9c36-ef3525d3a211")), new Money("USD", BigDecimal.valueOf(8.35))));
        assertEquals(new Money("USD", BigDecimal.valueOf(8.35)), container.get(17).getMoney());
    }


    @Test
    void testEmptySortedBalanceContainer() {
        SortedContainer<Balance> container = SortedContainer.of(List.<Balance>of());

        assertEquals(0, container.size());
        assertTrue(container.isEmpty());

        assertThrows(IllegalStateException.class, container::getMax);
        assertThrows(IllegalStateException.class, container::getMin);
        assertThrows(IllegalStateException.class, container::removeMax);
        assertThrows(IllegalStateException.class, container::removeMin);
    }

    @Test
    void testIndexOutOfBoundsException() {
        SortedContainer<Balance> container = SortedContainer.of(generateBalances());

        assertThrows(IndexOutOfBoundsException.class, () -> container.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> container.get(20));
    }

    private List<Balance> generateBalances() {
        return List.of(
                new Balance(new UserId(UUID.fromString("2aeb18f8-05c6-4e15-8cb3-983e77d862cf")), new Money("USD", BigDecimal.valueOf(12.20))),
                new Balance(new UserId(UUID.fromString("74a3a370-dbc8-4cd8-979d-52935a2f12c2")), new Money("USD", BigDecimal.valueOf(45.67))),
                new Balance(new UserId(UUID.fromString("23ef178d-365c-43f8-80b4-4285394e15c1")), new Money("USD", BigDecimal.valueOf(89.32))),
                new Balance(new UserId(UUID.fromString("5c0f6a65-0eb3-4912-8fc3-13b89340dd0d")), new Money("USD", BigDecimal.valueOf(23.45))),
                new Balance(new UserId(UUID.fromString("8c2fe64e-5a29-4b09-b329-73cfe6bf4a53")), new Money("USD", BigDecimal.valueOf(56.78))),
                new Balance(new UserId(UUID.fromString("b6f714a2-d759-4df5-822c-034e6e3e66e3")), new Money("USD", BigDecimal.valueOf(98.76))),
                new Balance(new UserId(UUID.fromString("91b9fadc-3057-4dd4-9c57-148e2bb88242")), new Money("USD", BigDecimal.valueOf(34.21))),
                new Balance(new UserId(UUID.fromString("c1c0b49d-4c92-4395-a715-9bb652e07869")), new Money("USD", BigDecimal.valueOf(67.89))),
                new Balance(new UserId(UUID.fromString("da4c73a7-c8da-4d8b-b506-92d717e1a078")), new Money("USD", BigDecimal.valueOf(12.10))),
                new Balance(new UserId(UUID.fromString("f4d04a4d-63e9-487f-aed3-006fbfbba39e")), new Money("USD", BigDecimal.valueOf(45.67))),
                new Balance(new UserId(UUID.fromString("58e2e154-5ff2-4a11-b8a7-13b7a3a6e885")), new Money("USD", BigDecimal.valueOf(87.50))),
                new Balance(new UserId(UUID.fromString("41c8c1f7-3994-4d9e-9f85-8b8b140cbe58")), new Money("USD", BigDecimal.valueOf(23.45))),
                new Balance(new UserId(UUID.fromString("4e732752-15b4-46cd-9a61-5278e9ee0db7")), new Money("USD", BigDecimal.valueOf(56.78))),
                new Balance(new UserId(UUID.fromString("6785a0b4-6c8f-4cf9-9c36-ef3525d3a211")), new Money("USD", BigDecimal.valueOf(98.20))),
                new Balance(new UserId(UUID.fromString("9b7262cb-44f5-400c-86cf-59fe689f35b8")), new Money("USD", BigDecimal.valueOf(34.21))),
                new Balance(new UserId(UUID.fromString("d1d5bf0f-15a4-494d-bdc6-810d1e24ee43")), new Money("USD", BigDecimal.valueOf(67.89))),
                new Balance(new UserId(UUID.fromString("0fe0f6c7-5f6c-4c42-b8ec-02ea994e0d9a")), new Money("USD", BigDecimal.valueOf(12.34))),
                new Balance(new UserId(UUID.fromString("9f15f94b-7448-4b06-9d5d-829c37a37272")), new Money("USD", BigDecimal.valueOf(45.67))),
                new Balance(new UserId(UUID.fromString("3e6a6c79-33d7-4293-a183-822539b85a06")), new Money("USD", BigDecimal.valueOf(89.12))),
                new Balance(new UserId(UUID.fromString("e66c6de1-b09a-4d3e-9eb4-3a5d72fc7a3d")), new Money("USD", BigDecimal.valueOf(23.45)))
        );


    }


}