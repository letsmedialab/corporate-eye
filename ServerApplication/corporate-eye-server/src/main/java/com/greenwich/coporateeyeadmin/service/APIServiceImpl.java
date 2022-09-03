package com.greenwich.coporateeyeadmin.service;

import static com.greenwich.coporateeyeadmin.util.GeneralUtils.SHA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenwich.coporateeyeadmin.dto.client.ClientUserDto;
import com.greenwich.coporateeyeadmin.dto.client.RestrictedEmailDto;
import com.greenwich.coporateeyeadmin.dto.client.RestrictedFileDto;
import com.greenwich.coporateeyeadmin.dto.client.RestrictedKeywordDto;
import com.greenwich.coporateeyeadmin.dto.client.RestrictedProcessDto;
import com.greenwich.coporateeyeadmin.dto.client.RestrictedUrlDto;
import com.greenwich.coporateeyeadmin.model.CUser;
import com.greenwich.coporateeyeadmin.model.RestrictedEmail;
import com.greenwich.coporateeyeadmin.model.RestrictedFile;
import com.greenwich.coporateeyeadmin.model.RestrictedKeyword;
import com.greenwich.coporateeyeadmin.model.RestrictedProcess;
import com.greenwich.coporateeyeadmin.model.RestrictedUrl;
import com.greenwich.coporateeyeadmin.model.interfaces.RestrictedModel;
import com.greenwich.coporateeyeadmin.repo.GroupRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedEmailRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedFileRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedKeywordRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedProcessRepository;
import com.greenwich.coporateeyeadmin.repo.RestrictedUrlRepository;
import com.greenwich.coporateeyeadmin.repo.UserRepository;
import com.greenwich.coporateeyeadmin.util.GeneralUtils;


@Service
public class APIServiceImpl implements APIService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private RestrictedKeywordRepository keywordRepository;

	@Autowired
	private RestrictedProcessRepository processRepository;

	@Autowired
	private RestrictedEmailRepository emailRepository;

	@Autowired
	private RestrictedUrlRepository urlRepository;

	@Autowired
	private RestrictedFileRepository fileRepository;

	@Override
	public ClientUserDto checkUser(String userName, String password) {

		Optional<CUser> user = userRepository.findByUsername(userName);

		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

		List<Long> groupIds = user.get().getGroups().stream().map(u -> u.getId()).collect(Collectors.toList());

		if (user.isPresent() && bc.matches(password, user.get().getPassword())) {
			return new ClientUserDto(userName, password, user.get().getName(), user.get().getEnabled(), groupIds);
		} else {
			return new ClientUserDto(userName, password, "No User", false, groupIds);
		}
	}

	@Override
	public String prepareEmailConfig(String username) {

		List<RestrictedEmail> emailConfigs = emailRepository.findByEnabled(true);

		CUser user = userRepository.findByUsername(username).get();

		List<RestrictedEmailDto> emailConfigDto = emailConfigs.stream()
				.filter(email -> isRuleApplicableForUser(user, email)).map(APIServiceImpl::mapToDto)
				.collect(Collectors.toList());

		return GeneralUtils.convertToJson(emailConfigDto);

	}

	@Override
	public String prepareUrlConfig(String username) {

		List<RestrictedUrl> config = urlRepository.findByEnabled(true);

		CUser user = userRepository.findByUsername(username).get();

		List<RestrictedUrlDto> configDto = config.stream().filter(e -> isRuleApplicableForUser(user, e))
				.map(APIServiceImpl::mapToDto).collect(Collectors.toList());

		return GeneralUtils.convertToJson(configDto);

	}

	@Override
	public String prepareProcessConfig(String username) {

		List<RestrictedProcess> config = processRepository.findByEnabled(true);

		CUser user = userRepository.findByUsername(username).get();

		List<RestrictedProcessDto> configDto = config.stream().filter(e -> isRuleApplicableForUser(user, e))
				.map(APIServiceImpl::mapToDto).collect(Collectors.toList());

		return GeneralUtils.convertToJson(configDto);

	}

	@Override
	public String prepareKeywordConfig(String username) {

		List<RestrictedKeyword> config = keywordRepository.findByEnabled(true);

		CUser user = userRepository.findByUsername(username).get();

		List<RestrictedKeywordDto> configDto = config.stream().filter(e -> isRuleApplicableForUser(user, e))
				.map(APIServiceImpl::mapToDto).collect(Collectors.toList());

		return GeneralUtils.convertToJson(configDto);

	}

	@Override
	public String prepareFileConfig(String username) {

		List<RestrictedFile> config = fileRepository.findByEnabled(true);

		CUser user = userRepository.findByUsername(username).get();

		List<RestrictedFileDto> configDto = config.stream().filter(e -> isRuleApplicableForUser(user, e))
				.map(APIServiceImpl::mapToDto).collect(Collectors.toList());

		return GeneralUtils.convertToJson(configDto);

	}

	private Boolean isRuleApplicableForUser(CUser user, RestrictedModel email) {
		if (email.getGroups().isEmpty() && email.getUsers().isEmpty()) {
			return !email.getRestrictGroupsByDefault();
		} else if (email.getGroups().isEmpty() && !email.getUsers().isEmpty()) {
			return !email.getRestrictUsersByDefault() && email.getUsers().contains(user);

		} else if (!email.getGroups().isEmpty() && email.getUsers().isEmpty()) {
			if (email.getRestrictGroupsByDefault()) // not applicable for pres groups
			{
				return !email.getGroups().stream().anyMatch(user.getGroups()::contains);
			} else {
				return email.getGroups().stream().anyMatch(user.getGroups()::contains);
			}
		} else {
			if (!email.getRestrictGroupsByDefault()) // applicable for groips
			{
				return (email.getGroups().stream().anyMatch(user.getGroups()::contains)
						&& !email.getUsers().contains(user)); // check group

			} else {

				return (!email.getGroups().stream().anyMatch(user.getGroups()::contains))
						|| (email.getGroups().stream().anyMatch(user.getGroups()::contains)
								&& email.getUsers().contains(user));

			}
		}

//		if(!email.getRestrictGroupsByDefault()) //applicable for groips
//		{
//			if(email.getGroups().stream().anyMatch(user.getGroups()::contains)) //check group
//			{
//				if(!email.getUsers().contains(user))
//				{
//					return true;
//				}
//			
//			}
//		}
//		else 
//		{
//			if(email.getGroups().stream().anyMatch(user.getGroups()::contains)) //not applicable fo grps
//			{
//				
//				if(email.getUsers().contains(user))
//				{
//					return true;
//				}
//			}
//		}		
	}

	private static RestrictedEmailDto mapToDto(RestrictedEmail dob) {
		return new RestrictedEmailDto(dob.getId(), dob.getRestrictedEmail(), dob.getPolicyName());
	}

	private static RestrictedUrlDto mapToDto(RestrictedUrl dob) {
		return new RestrictedUrlDto(dob.getId(), dob.getRestrictedUrl(), dob.getPolicyName());
	}

	private static RestrictedKeywordDto mapToDto(RestrictedKeyword dob) {
		List<String> keywords = new ArrayList<>();
		try {
			keywords = Arrays.asList(dob.getRestrictedKeyword().split(","));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new RestrictedKeywordDto(dob.getId(), keywords, dob.getRestrictedRegex(), dob.getPolicyName());
	}

	private static RestrictedFileDto mapToDto(RestrictedFile dob) {
		return new RestrictedFileDto(dob.getId(), dob.getRestrictedFileName(), dob.getHashValue().toLowerCase(), dob.getPolicyName());
	}

	private static RestrictedProcessDto mapToDto(RestrictedProcess dob) {
		return new RestrictedProcessDto(dob.getId(), dob.getRestrictedProcess(), dob.getPolicyName());
	}

	@Override
	public String getHashUpdateValues(String username) {
		Map<String, String> data = new HashMap<>();
		
		data.put(RuleId.EMAIL.name(), SHA(emailRepository.count() + username));
		data.put(RuleId.URL.name(), SHA(urlRepository.count() + username));
		data.put(RuleId.FILE.name(), SHA(fileRepository.count() + username));
		data.put(RuleId.KEYWORD.name(), SHA(keywordRepository.count() + username));
		data.put(RuleId.PROCESS.name(), SHA(processRepository.count() + username));
	
		
		return GeneralUtils.convertToJson(data);

	}

	enum RuleId {
		PROCESS, URL, FILE, KEYWORD, EMAIL
	}
	
	
}
