package boho.lottonumbergenerator.common.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class MemberDetails implements UserDetails {

	private final Long id;
	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;
	// private final boolean accountNonExpired;
	// private final boolean accountNonLocked;
	// private final boolean credentialsNonExpired;
	private final boolean enabled;

	public MemberDetails(Long id, String username, String password, List<GrantedAuthority> authorities, boolean enabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
