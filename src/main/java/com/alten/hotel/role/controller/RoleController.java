package com.alten.hotel.role.controller;

import com.alten.hotel.common.controller.DefaultController;
import com.alten.hotel.role.dto.RoleDTO;
import com.alten.hotel.role.dto.RoleRecord;
import com.alten.hotel.role.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of bookings {@link RoleEntity}.
 * <p>
 * Almost all endpoints
 * have the response in {@link RoleRecord} type.
 *
 * @author luis.bonfa
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleController extends DefaultController<RoleDTO, RoleRecord> {
}
