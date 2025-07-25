package boho.lottonumbergenerator.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.common.event.MemberRegisterSuccessEvent;
import boho.lottonumbergenerator.common.security.UsernameDuplicateException;
import boho.lottonumbergenerator.domain.dto.MemberRegisterRequest;
import boho.lottonumbergenerator.domain.entity.member.MemberRole;
import boho.lottonumbergenerator.domain.entity.member.Role;
import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.repository.MemberRepository;
import boho.lottonumbergenerator.repository.MemberRoleRepository;
import boho.lottonumbergenerator.service.AuthService;
import boho.lottonumbergenerator.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Value("${ADMIN_PASSWORD}")
	private String password;

	private static final String ADMIN = "admin";
	private final RoleService roleService;
	private final MemberRepository memberRepository;
	private final MemberRoleRepository memberRoleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void createAdminIfNotFound() {
		memberRepository.findByUsername(ADMIN)
			.ifPresentOrElse(this::logMemberAlreadyExists,
				() -> logMemberRegistered(
					memberRepository.save(
						Member.createDefaultMember(ADMIN, passwordEncoder.encode(password)))));

		Member admin = getAdmin();
		Role adminRole = roleService.getAdminRole();

		memberRoleRepository.findByMemberAndRole(admin, adminRole)
			.ifPresentOrElse(this::logMemberRoleAlreadyExists,
				() -> logMemberRoleRegistered(
					memberRoleRepository.save(new MemberRole(admin, adminRole))));
	}

	@Override
	@Transactional
	public void registerMember(MemberRegisterRequest request) {

		memberRepository.findByUsername(request.username())
			.ifPresent(member -> {
				throw new UsernameDuplicateException(member.getUsername() + "은(는) 이미 존재하는 아이디입니다!");
			});

		Member member = memberRepository.save(Member.from(request));
		log.info("New Member Registered - ID: [{}] | username: [{}]", member.getId(), member.getUsername());

		Role memberRole = roleService.getMemberRole();
		memberRoleRepository.save(new MemberRole(member, memberRole));

		eventPublisher.publishEvent(new MemberRegisterSuccessEvent(member));
	}

	@Override
	@Transactional
	public void updateLastLoginDate(String username) {
		memberRepository.findByUsername(username)
			.ifPresent(Member::updateLastLoginDate);
	}

	private Member getAdmin() {
		return memberRepository.findByUsername(ADMIN)
			.orElseThrow(() -> new EntityNotFoundException("[" + ADMIN + "] member not fount"));
	}

	private void logMemberAlreadyExists(Member member) {
		log.info("[{}] member already exists", member.getUsername());
	}

	private void logMemberRegistered(Member member) {
		log.info("[{}] member registered", member.getUsername());
	}

	private void logMemberRoleAlreadyExists(MemberRole memberRole) {
		log.info("[{}] member already has [{}] role", memberRole.getMember().getUsername(),
			memberRole.getRole().getRoleName());
	}

	private void logMemberRoleRegistered(MemberRole memberRole) {
		log.info("[{}] role has been assigned to [{}] member", memberRole.getRole().getRoleName(),
			memberRole.getMember().getUsername());
	}
}
