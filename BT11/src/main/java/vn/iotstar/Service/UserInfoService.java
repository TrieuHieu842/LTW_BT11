package vn.iotstar.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.iotstar.Config.UserInfoUserDetails;
import vn.iotstar.Entity.UserInfo;
import vn.iotstar.Repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	UserInfoRepository repository;
	public UserInfoService(UserInfoRepository userInfoRepository) {
		this.repository= userInfoRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo= repository.findByName(username);
		return userInfo.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found: " + username));
	}

}
