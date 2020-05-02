package com.blaese.error.manager.security;

import com.blaese.error.manager.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtUserFactory {

	public static com.blaese.error.manager.security.JwtUser create(User user) {
		return new com.blaese.error.manager.security.JwtUser(user.getId(), user.getEmail(), user.getPassword(), null);
	}

}
