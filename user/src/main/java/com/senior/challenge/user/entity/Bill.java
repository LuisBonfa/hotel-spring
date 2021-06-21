package com.senior.challenge.user.entity;

import com.senior.challenge.user.dto.BillDTO;
import com.senior.challenge.user.persistence.Updatable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Entity
@Table(name="bill")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Bill extends Updatable {

    @NotNull(message = "Reserva é Obrigatória")
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false, foreignKey = @ForeignKey(name = "bill_booking_fk"))
    private Booking booking;

    @Column(name="dailies_price")
    private Double dailiesPrice;

    @Type(type = "jsonb")
    @Column(name="products_data", columnDefinition = "jsonb")
    private Map<String, Integer> products;

    @Column(name="products_price")
    private Double productsPrice;

    @Column(name="garage_price")
    private Double garagePrice;

    @Column(name="total")
    private Double total;

    @Column(name="status")
    private String status;

    public static Bill create(BillDTO billDTO){
        return new ModelMapper().map(billDTO, Bill.class);
    }
}
