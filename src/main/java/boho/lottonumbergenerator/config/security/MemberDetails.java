package boho.lottonumbergenerator.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class MemberDetails implements UserDetails {

	private final MemberLoginProcessingPrincipal principal;
	private final List<GrantedAuthority> authorities;

	public MemberDetails(MemberLoginProcessingPrincipal principal, List<GrantedAuthority> authorities) {
		this.principal = principal;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return principal.password();
	}

	@Override
	public String getUsername() {
		return principal.username();
	}
}
