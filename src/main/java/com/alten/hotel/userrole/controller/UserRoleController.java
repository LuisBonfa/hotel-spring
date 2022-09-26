package com.alten.hotel.userrole.controller;

import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.controller.DefaultController;
import com.alten.hotel.userrole.dto.UserRoleDTO;
import com.alten.hotel.userrole.dto.UserRoleRecord;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of bookings {@link BookingEntity}.
 * <p>
 * Almost all endpoints
 * have the response in {@link BookingRecord} type.
 *
 * @author luis.bonfa
 */
@CrossOrigin
@RestController
@RequestMapping("/user-role")
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleController extends DefaultController<UserRoleDTO, UserRoleRecord> {
}
