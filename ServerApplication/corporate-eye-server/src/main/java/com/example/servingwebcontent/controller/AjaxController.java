package com.example.servingwebcontent.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servingwebcontent.ServingWebContentApplication;
import com.example.servingwebcontent.dto.GroupDTO;
import com.example.servingwebcontent.dto.GroupEditDTO;
import com.example.servingwebcontent.dto.RestrictedKeywordDTO;
import com.example.servingwebcontent.dto.RestrictedProcessDTO;
import com.example.servingwebcontent.model.CGroup;
import com.example.servingwebcontent.model.CUser;
import com.example.servingwebcontent.model.RestrictedKeyword;
import com.example.servingwebcontent.model.RestrictedProcess;
import com.example.servingwebcontent.repo.GroupRepository;
import com.example.servingwebcontent.repo.RestrictedKeywordRepository;
import com.example.servingwebcontent.repo.RestrictedProcessRepository;
import com.example.servingwebcontent.repo.UserRepository;
import com.example.servingwebcontent.util.GeneralUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("ajax")
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

	@GetMapping(value = "checkUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkUsername(@RequestParam(name = "username", required = false, defaultValue = "") String username) {

		Optional<CUser> user = userRepository.findByUsername(username);

		return String.valueOf(user.isPresent());
	}
	
	@GetMapping(value = "checkgroupname", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkGroupName(@RequestParam(name = "groupname", required = false, defaultValue = "") String groupname) {

		Optional<CGroup> group = groupRepository.findByGroupName(groupname);

		return String.valueOf(group.isPresent());
	}
	
	@GetMapping(value = "checkPolicyname", produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkPolicyName(@RequestParam(name = "policyname", required = false, defaultValue = "") String policyName) {

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
	public String getUserDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id) throws JsonProcessingException {

		Optional<CUser> user = userRepository.findById(id);
		
		return GeneralUtils.convertToJson(user.get());
	}
	
	@GetMapping(value = "getGroupDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGroupDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id) throws JsonProcessingException {

		Optional<CGroup> group = groupRepository.findById(id);
		
		
		
		return GeneralUtils.convertToJson(new GroupEditDTO(group.get()));
	}
	
	@GetMapping(value = "getKeywordDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getKeywordDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id) throws JsonProcessingException {

		Optional<RestrictedKeyword> keyWord = keywordRepository.findById(id);	
		
		
		return GeneralUtils.convertToJson(keyWord.get());
	}
	
	
	@GetMapping(value = "getProcessDataById", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProcessDataById(@RequestParam(name = "id", required = false, defaultValue = "") Long id) throws JsonProcessingException {

		Optional<RestrictedProcess> data = processRepository.findById(id);	
		
		
		return GeneralUtils.convertToJson(data.get());
	}
	
	@PostMapping(value = "addUser" ,consumes = MediaType.APPLICATION_JSON_VALUE )
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

			Set<CUser> users  = userRepository.findAllByUsernameIn(group.getUsernames().keySet());
			
			
			
			
			CGroup newGroup = new CGroup();
			
			newGroup.setGroupName(group.getGroupName());
			newGroup.setUsers(users);
			
			final CGroup lewGroup = groupRepository.save(newGroup);		
		
			List<CUser> nusers = users.stream().map(u ->{ u.getGroups().add(lewGroup); return u;}).collect(Collectors.toList());
			
			
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

			Set<CUser> users  = userRepository.findAllByUsernameIn(group.getUserNames().keySet());
			
			
			
			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());
			
			RestrictedKeyword keyword = new RestrictedKeyword();
			
			keyword.setAllowedGroups(groups);
			keyword.setAllowedUsers(users);
			keyword.setEnabled(group.getEnabled());
			keyword.setPolicyName(group.getPolicyName());
			keyword.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			keyword.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			keyword.setRestrictedKeyword(group.getKeywordName());
			keyword.setRestrictedRegex(group.getRegEx());
			
			users.stream().forEach(u -> u.getKeywords().add(keyword));
			groups.stream().forEach(u -> u.getKeywords().add(keyword));
			
			//userRepository.saveAll(users);
			//groupRepository.saveAll(groups);
			
			keywordRepository.save(keyword);
		
		} catch (Exception ex) {
			
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	@PostMapping("addProcess")
	public String addProcess(@RequestBody RestrictedProcessDTO group) {
		try {

			Set<CUser> users  = userRepository.findAllByUsernameIn(group.getUserNames().keySet());
			
			
			
			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());
			
			RestrictedProcess keyword = new RestrictedProcess();
			
			keyword.setAllowedGroups(groups);
			keyword.setAllowedUsers(users);
			keyword.setEnabled(group.getEnabled());
			keyword.setPolicyName(group.getPolicyName());
			keyword.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			keyword.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			keyword.setRestrictedProcess(group.getProcessName());
			
			
			users.stream().forEach(u -> u.getProcesses().add(keyword));
			groups.stream().forEach(u -> u.getProcesses().add(keyword));
			
			//userRepository.saveAll(users);
			//groupRepository.saveAll(groups);
			
			processRepository.save(keyword);
		
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

			if(user.getPassword().trim().length()>0)
			{
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
			
			Set<CUser> users  = userRepository.findAllByUsernameIn(group.getUsernames().keySet());
			
			cgroup.setGroupName(group.getGroupName());
			
			
			cgroup.getUsers().stream().forEach(u -> {
				if(!users.contains(u))
				{
					u.getGroups().remove(cgroup);
					userRepository.save(u);
				}
			});
			
			cgroup.getUsers().removeIf(u -> !users.contains(u));
			
			cgroup.getUsers().addAll(users);
			
			groupRepository.save(cgroup);
			
			Set<CUser> newUsers = users.stream().map(u -> { u.getGroups().add(cgroup); return u;}).collect(Collectors.toSet());
			
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

			Set<CUser> users  = userRepository.findAllByUsernameIn(group.getUserNames().keySet());
			
			
			
			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());
			
			RestrictedKeyword keyword = keywordRepository.findById(group.getId()).get();
			
			keyword.setAllowedGroups(groups);
			keyword.setAllowedUsers(users);
			keyword.setEnabled(group.getEnabled());
			keyword.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			keyword.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			keyword.setRestrictedKeyword(group.getKeywordName());
			keyword.setRestrictedRegex(group.getRegEx());
			
			users.stream().forEach(u -> u.getKeywords().add(keyword));
			groups.stream().forEach(u -> u.getKeywords().add(keyword));
			
			//userRepository.saveAll(users);
			//groupRepository.saveAll(groups);
			
			keywordRepository.save(keyword);
		
		} catch (Exception ex) {
			
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	@PostMapping("updateProcess")
	public String updateProcess(@RequestBody RestrictedProcessDTO group) {
		
		try {

			Set<CUser> users  = userRepository.findAllByUsernameIn(group.getUserNames().keySet());
			
			
			
			Set<CGroup> groups = groupRepository.findAllByGroupNameIn(group.getGroupNames());
			
			RestrictedProcess process = processRepository.findById(group.getId()).get();
			
			process.setAllowedGroups(groups);
			process.setAllowedUsers(users);
			process.setEnabled(group.getEnabled());
			process.setRestrictGroupsByDefault(group.getRestrictGroupsByDefault());
			process.setRestrictUsersByDefault(group.getRestrictUsersByDefault());
			process.setRestrictedProcess(group.getProcessName());
			
			users.stream().forEach(u -> u.getProcesses().add(process));
			groups.stream().forEach(u -> u.getProcesses().add(process));
		
			
			processRepository.save(process);
		
		} catch (Exception ex) {
			
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	@PostMapping("deleteUser/{userId}")
	public String deleteUser(@PathVariable (required = true) Long userId) {
		try {
			
			CUser user = userRepository.findById(userId).get();
			
			Set<CGroup> groups = user.getGroups().stream().map(g -> {g.getUsers().remove(user); return g;}).collect(Collectors.toSet());
			
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
	public String deleteGroup(@PathVariable (required = true) Long groupId) {
		try {
			
			CGroup group = groupRepository.findById(groupId).get();
			
			Set<CUser> users = group.getUsers().stream().map(u -> {u.getGroups().remove(group) ;return u;}).collect(Collectors.toSet());
			
			userRepository.saveAll(users);
			
			groupRepository.deleteById(groupId);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	@PostMapping("deleteKeyword/{keywordId}")
	public String deleteKeyword(@PathVariable (required = true) Long keywordId) {
		try {	
						
			keywordRepository.deleteById(keywordId);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	@PostMapping("deleteProcess/{keywordId}")
	public String deleteProcess(@PathVariable (required = true) Long processId) {
		try {	
						
			processRepository.deleteById(processId);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "failed";
		}

		return "success";

	}
	
	
}
