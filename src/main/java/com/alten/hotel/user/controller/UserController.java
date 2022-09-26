package com.alten.hotel.user.controller;

import com.alten.hotel.common.controller.DefaultController;
import com.alten.hotel.user.dto.UserDTO;
import com.alten.hotel.user.dto.UserRecord;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController extends DefaultController<UserDTO, UserRecord> {
}
