package com.fc.query.store.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.command.store.infra.StoreTagRepository;
import com.fc.config.security.LoginUser;
import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.StoreTag;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreQuery.StoreList;
import com.fc.query.store.model.StoreSearch;
import com.fc.query.store.service.QueryStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/store")
@Api(tags = "업체 조회 관련 API")
public class StoreQueryApi {
	private QueryStoreService queryStoreService;
	private StoreTagRepository storeTagRepository;
	
	@ApiOperation("업체 태그 목록 가져오기")
	@GetMapping("tags")
	public ResponseEntity<List<StoreTag>> getTags(){
		return new ResponseEntity<>(storeTagRepository.findAll(), HttpStatus.OK);
	}

	@ApiOperation("업체 상세 가져오기")
	@GetMapping("{owner}")
	public ResponseEntity<StoreQuery.StoreMainInfo> getStore(
			@PathVariable Owner owner, 
			@ApiIgnore
			@LoginUser Member loginMember) {
		StoreQuery.StoreMainInfo findStore = queryStoreService.findByOwner(owner, loginMember);
		return new ResponseEntity<>(findStore, HttpStatus.OK);
	}

	@ApiOperation("업체 리스트 가져오기")
	@GetMapping
	public ResponseEntity<StoreQuery.StoreList> getAll(
			StoreSearch dto,
			@ApiIgnore
			@LoginUser Member loginMember) {
		StoreList storeList = queryStoreService.findAll(dto, loginMember);
		return new ResponseEntity<>(storeList, HttpStatus.OK);
	}
}
