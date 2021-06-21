package com.senior.challenge.user.dto;

import com.senior.challenge.user.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private Booking booking;
    private Double dailiesPrice;
    private Map<String, Object> products;
    private Double productsPrice;
    private Double garagePrice;
    private Double total;
    private String status;
}
