package boho.lottonumbergenerator.common.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.domain.entity.member.StatusType;
import boho.lottonumbergenerator.repository.MemberRepository;
import boho.lottonumbergenerator.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final MemberRoleRepository memberRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("No Member found with username: " + username));

		List<GrantedAuthority> authorities = memberRoleRepository.findByMember(member)
			.stream()
			.map(memberRole -> new SimpleGrantedAuthority(memberRole.getRole().getRoleName()))
			.collect(Collectors.toList());

		return new MemberDetails(
			member.getId(),
			member.getUsername(),
			member.getPassword(),
			authorities,
			member.getStatus() != StatusType.WITHDRAWN);
	}
}
