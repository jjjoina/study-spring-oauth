package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.entity.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면
// 로그인되면 security session을 만들어준다. (Security ContextHolder)
// Security Session -> Authentication -> UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	/**
	 * @return 해당 사용자의 권한
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	/**
	 * @return 사용자의 비밀번호
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * @return 계정 만료 안 됨 여부
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @return 계정 잠금 안 됨 여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @return 비밀번호 최신 여부
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @return 계정 활성화 여부
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
}
