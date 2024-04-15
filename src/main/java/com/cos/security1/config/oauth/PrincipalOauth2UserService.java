package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.entity.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;

	// 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// registrationId로 어떤 OAuth로 로그인했는지 확인 가능
		System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
		System.out.println("userRequest.getAccessToken().getTokenValue() = " + userRequest.getAccessToken().getTokenValue());

		OAuth2User oAuth2User = super.loadUser(userRequest);
		/*
		구글 로그인 버튼 클릭
		-> 구글 로그인 창
		-> 로그인을 완료
		-> code를 return(OAuth-Client 라이브러리)
		-> AccessToken 요청

		userRequest 정보
		-> loadUser 함수
		-> 구글로부터 회원 프로필 받아준다.
		 */
		System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

		// 회원가입을 강제로 진행해볼 예정
		String provider = userRequest.getClientRegistration().getRegistrationId();	// google
		String providerId = oAuth2User.getAttribute("sub");
		String username = provider + "_" + providerId;	// google_117557184669791125793
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oAuth2User.getAttribute("email");
		String role = "ROLE_USER";

		User userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			userEntity = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.role(role)
				.provider(provider)
				.providerId(providerId)
				.build();
			userRepository.save(userEntity);
		}

		return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
	}
}
