package com.greenwich.coporateeyeadmin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greenwich.coporateeyeadmin.ServingWebContentApplication;
import com.greenwich.coporateeyeadmin.dto.GroupDTO;
import com.greenwich.coporateeyeadmin.dto.GroupEditDTO;
import com.greenwich.coporateeyeadmin.dto.RestrictedEmailDTO;
import com.greenwich.coporateeyeadmin.dto.RestrictedFileDTO;
import com.greenwich.coporateeyeadmin.dto.RestrictedKeywordDTO;
import com.greenwich.coporateeyeadmin.dto.RestrictedProcessDTO;
import com.greenwich.coporateeyeadmin.dto.RestrictedUrlDTO;
import com.greenwich.coporateeyeadmin.model.CGroup;
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

@RestController
@RequestMapping("admin/ajax")
public class AjaxController {

	private Logger logger = LoggerFactory.getLogger(ServingWebContentApplication.class);

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

	@GetMapping(value = "checkUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkUsername(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		Optional<CUser> user = userRepository.findByUsername(username);

		return String.valueOf(user.isPresent());
	}

	@GetMapping(value = "checkgroupname", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkGroupName(
			@RequestParam(name = "groupname", required = false, defaultValue = "") String groupname) {

		Optional<CGroup> group = groupRepository.findByGroupName(groupname);

		return String.valueOf(group.isPresent());
	}

	@GetMapping(value = "checkPolicyname", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkPolicyName(
			@RequestParam(name = "policyname", required = false, defaultValue = "") String policyName) {

		Optional<RestrictedKeyword> rkeys = keywordRepository.findByPolicyName(policyName);

		return String.valueOf(rkeys.isPresent());
	}

	@GetMapping(value = "getUserDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUserDetails(@RequestParam(name = "keyword", required = true, defaultValue = "") String keyword) {

		List<CUser> users = userRepository.search(keyword);

		return GeneralUtils.convertToJson(users);
	}

	@GetMapping(value = "getGroupDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGroupDetails(@RequestParam(name = "keyword", required = true, defaultValue = "") String keyword) {

		List<CGroup> groups = groupRepository.search(keyword);

		return GeneralUtils.convertToJson(groups);
	}

	@GetMapping(value = "getUserDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUserDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<CUser> user = userRepository.findById(id);

		return GeneralUtils.convertToJson(user.get());
	}

	@GetMapping(value = "getGroupDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGroupDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<CGroup> group = groupRepository.findById(id);

		return GeneralUtils.convertToJson(new GroupEditDTO(group.get()));
	}

	@GetMapping(value = "getKeywordDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getKeywordDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<RestrictedKeyword> keyWord = keywordRepository.findById(id);

		return GeneralUtils.convertToJson(keyWord.get());
	}

	@GetMapping(value = "getProcessDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProcessDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<RestrictedProcess> data = processRepository.findById(id);

		return GeneralUtils.convertToJson(data.get());
	}

	@GetMapping(value = "getUrlDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUrlDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<RestrictedUrl> data = urlRepository.findById(id);

		return GeneralUtils.convertToJson(data.get());
	}

	@GetMapping(value = "getFileDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getFileDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<RestrictedFile> data = fileRepository.findById(id);

		return GeneralUtils.convertToJson(data.get());
	}

	@GetMapping(value = "getEmailDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmailDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id)
			throws JsonProcessingException {

		Optional<RestrictedEmail> data = emailRepository.findById(id);

		return GeneralUtils.convertToJson(data.get());
	}

	@PostMapping(value = "addUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@RequestBody CUser user) {
		try {

			user.setPassword(GeneralUtils.bCryptEncode(user.getPassword()));
			userRepository.save(user);

		} catch (Exception ex) {
			return "failed";
		}

		return "success";

	}

	@PostMapping("addgroup")
	public String addGroup(@RequestBody GroupDTO group) {
		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUsernames().keySet());

			CGroup newGroup = new CGroup();

			newGroup.setGroupName(group.getGroupName());
			newGroup.setUsers(users);

			final CGroup lewGroup = groupRepository.save(newGroup);

			List<CUser> nusers = users.stream().map(u -> {
				u.getGroups().add(lewGroup);
				return u;
			}).collect(Collectors.toList());

			userRepository.saveAll(nusers);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("addKeyword")
	public String addKeyword(@RequestBody RestrictedKeywordDTO group) {
		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedKeyword data = new RestrictedKeyword();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setPolicyName(group.getPolicyName());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedKeyword(group.getKeywordName());
			data.setRestrictedRegex(group.getRegEx());

			users.stream().forEach(u -> u.getKeywords().add(data));
			groups.stream().forEach(u -> u.getKeywords().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			keywordRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("addFile")
	public String addFile(@RequestBody RestrictedFileDTO group) {
		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedFile data = new RestrictedFile();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setPolicyName(group.getPolicyName());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedFileName(group.getFileName());
			data.setHashValue(group.getHash());

			users.stream().forEach(u -> u.getFiles().add(data));
			groups.stream().forEach(u -> u.getFiles().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			fileRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("addProcess")
	public String addProcess(@RequestBody RestrictedProcessDTO group) {
		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedProcess data = new RestrictedProcess();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setPolicyName(group.getPolicyName());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedProcess(group.getProcessName());

			users.stream().forEach(u -> u.getProcesses().add(data));
			groups.stream().forEach(u -> u.getProcesses().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			processRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("addUrl")
	public String addUrl(@RequestBody RestrictedUrlDTO group) {
		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedUrl data = new RestrictedUrl();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setPolicyName(group.getPolicyName());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedUrl(group.getUrlName());

			users.stream().forEach(u -> u.getUrls().add(data));
			groups.stream().forEach(u -> u.getUrls().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			urlRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("addEmail")
	public String addEmail(@RequestBody RestrictedEmailDTO group) {
		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedEmail data = new RestrictedEmail();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setPolicyName(group.getPolicyName());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedEmail(group.getEmailName());

			users.stream().forEach(u -> u.getEmails().add(data));
			groups.stream().forEach(u -> u.getEmails().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			emailRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateUser")
	public String updateUser(@RequestBody CUser user) {
		try {

			CUser currentUser = userRepository.findById(user.getId()).get();

			currentUser.setName(user.getName());
			currentUser.setRole(user.getRole());
			currentUser.setEnabled(user.getEnabled());

			if (user.getPassword().trim().length() > 0) {
				currentUser.setPassword(GeneralUtils.bCryptEncode(user.getPassword()));
			}

			userRepository.save(currentUser);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateGroup")
	public String updateGroup(@RequestBody GroupDTO group) {
		try {

			CGroup cgroup = groupRepository.findById(group.getId()).get();

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUsernames().keySet());

			cgroup.setGroupName(group.getGroupName());

			cgroup.getUsers().stream().forEach(u -> {
				if (!users.contains(u)) {
					u.getGroups().remove(cgroup);
					userRepository.save(u);
				}
			});

			cgroup.getUsers().removeIf(u -> !users.contains(u));

			cgroup.getUsers().addAll(users);

			groupRepository.save(cgroup);

			Set<CUser> newUsers = users.stream().map(u -> {
				u.getGroups().add(cgroup);
				return u;
			}).collect(Collectors.toSet());

			userRepository.saveAll(newUsers);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateKeyword")
	public String updateKeyword(@RequestBody RestrictedKeywordDTO group) {

		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedKeyword data = keywordRepository.findById(group.getId()).get();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedKeyword(group.getKeywordName());
			data.setRestrictedRegex(group.getRegEx());

			users.stream().forEach(u -> u.getKeywords().add(data));
			groups.stream().forEach(u -> u.getKeywords().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			keywordRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateFile")
	public String updateFile(@RequestBody RestrictedFileDTO group) {

		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedFile data = fileRepository.findById(group.getId()).get();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedFileName(group.getFileName());
			data.setHashValue(group.getHash());

			users.stream().forEach(u -> u.getFiles().add(data));
			groups.stream().forEach(u -> u.getFiles().add(data));

			// userRepository.saveAll(users);
			// groupRepository.saveAll(groups);

			fileRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateProcess")
	public String updateProcess(@RequestBody RestrictedProcessDTO group) {

		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedProcess data = processRepository.findById(group.getId()).get();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedProcess(group.getProcessName());

			users.stream().forEach(u -> u.getProcesses().add(data));
			groups.stream().forEach(u -> u.getProcesses().add(data));

			processRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateEmail")
	public String updateEmail(@RequestBody RestrictedEmailDTO group) {

		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedEmail data = emailRepository.findById(group.getId()).get();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedEmail(group.getEmailName());

			users.stream().forEach(u -> u.getEmails().add(data));
			groups.stream().forEach(u -> u.getEmails().add(data));

			emailRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("updateUrl")
	public String updateUrl(@RequestBody RestrictedUrlDTO group) {

		try {

			Set<CUser> users = userRepository.findAllByUsernameIn(group.getUserNames().keySet());

			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());

			RestrictedUrl data = urlRepository.findById(group.getId()).get();

			data.setGroups(groups);
			data.setUsers(users);
			data.setEnabled(group.getEnabled());
			data.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			data.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			data.setRestrictedUrl(group.getUrlName());

			users.stream().forEach(u -> u.getUrls().add(data));
			groups.stream().forEach(u -> u.getUrls().add(data));

			urlRepository.save(data);

		} catch (Exception ex) {

			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteUser/{userId}")
	public String deleteUser(@PathVariable(required = true) Long userId) {
		try {

			CUser user = userRepository.findById(userId).get();

			Set<CGroup> groups = user.getGroups().stream().map(g -> {
				g.getUsers().remove(user);
				return g;
			}).collect(Collectors.toSet());

			groupRepository.saveAll(groups);

			user.setGroups(new HashSet<>());

			userRepository.save(user);

			userRepository.deleteById(userId);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteGroup/{groupId}")
	public String deleteGroup(@PathVariable(required = true) Long groupId) {
		try {

			CGroup group = groupRepository.findById(groupId).get();

			Set<CUser> users = group.getUsers().stream().map(u -> {
				u.getGroups().remove(group);
				return u;
			}).collect(Collectors.toSet());

			userRepository.saveAll(users);

			groupRepository.deleteById(groupId);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteKeyword/{keywordId}")
	public String deleteKeyword(@PathVariable(required = true) Long keywordId) {
		try {
			deleteModel(keywordId,RestrictedKeyword.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteProcess/{keyId}")
	public String deleteProcess(@PathVariable(required = true) Long keyId) {
		try {
			deleteModel(keyId,RestrictedProcess.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteEmail/{keyId}")
	public String deleteEmail(@PathVariable(required = true) Long keyId) {
		try {
			deleteModel(keyId,RestrictedEmail.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteUrl/{keyId}")
	public String deleteUrl(@PathVariable(required = true) Long keyId) {
		try {
			deleteModel(keyId,RestrictedUrl.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	@PostMapping("deleteFile/{keyId}")
	public String deleteFile(@PathVariable(required = true) Long keyId) {
		try {
			deleteModel(keyId,RestrictedFile.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}

	private void deleteModel(Long keyId,Class fileType) {


		JpaRepository repo = null;

		if (fileType.equals(RestrictedEmail.class)) {
			repo = emailRepository;
		} else if (fileType.equals(RestrictedFile.class)) {
			repo = fileRepository;
		} else if (fileType.equals(RestrictedKeyword.class)) {
			repo = keywordRepository;
		} else if (fileType.equals(RestrictedUrl.class)) {
			repo = urlRepository;
		} else if (fileType.equals(RestrictedProcess.class)) {
			repo = processRepository;
		}

		Set<CGroup> groups;

		RestrictedModel restrictedFile = (RestrictedModel) repo.findById(keyId).get();
		groups = restrictedFile.getGroups();
		groups.stream().forEach(g -> g.getFiles().remove(restrictedFile));
		restrictedFile.setGroups(new HashSet<CGroup>());
		restrictedFile.setGroups(new HashSet<CGroup>());

		groupRepository.saveAll(groups);

		repo.deleteById(keyId);

	}

	@GetMapping(value = "getUserNames", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public String getUserNames(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {

		Set<CUser> user = userRepository.findByUsernameStartsWithOrNameStartsWithOrRoleStartsWith(keyword, keyword,
				keyword);

		StringBuilder sb = new StringBuilder();

		if (user.isEmpty()) {
			sb.append(
					"<a  class=\"list-group-item list-group-item-action suggestion-list-item\"  style=\"cursor:pointer\">No Users Found</a>");
		} else {
			user.stream().forEach(u -> sb.append(
					"<a  class=\"list-group-item list-group-item-action suggestion-list-item\" onClick='setUser(\""
							+ u.getUsername() + "\",\"" + u.getName() + "\")' style=\"cursor:pointer\">" + u.getName()
							+ "(" + u.getUsername() + ")" + "</a>"));
		}

		return sb.toString();
	}
}
