package com.fc.query.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.config.security.LoginUser;
import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.query.member.model.MemberQuery;
import com.fc.query.member.model.MemberQuery.InterestProductList;
import com.fc.query.member.model.MemberQuery.InterestStoreList;
import com.fc.query.member.service.QueryMemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/member")
@Api(tags = "회원 조회 관련 API")
public class MemberQueryApi {
	private QueryMemberService queryMemberService;
	
	@ApiOperation("이메일 중복 확인")
	@GetMapping("{email}/exist")
	public ResponseEntity<Boolean> existByEmail(@PathVariable Email email){
		boolean existByEmail = queryMemberService.existByEmail(email);
		return new ResponseEntity<>(existByEmail, HttpStatus.OK);
	}
	
	@ApiOperation("자신의 주소값 가져오기")
	@GetMapping("address")
	public ResponseEntity<MemberQuery.Address> getAddress(
			@ApiIgnore
			@LoginUser Member loginMember
		){
		MemberQuery.Address addressOfMember = queryMemberService.getAddressOfMember(loginMember.getEmail());
		return new ResponseEntity<>(addressOfMember, HttpStatus.OK);
	}
	
	@ApiOperation("관심 업체 목록 가져오기")
	@GetMapping("interest-store")
	public ResponseEntity<InterestStoreList> getInterestStoreList(
			@ApiIgnore
			@LoginUser Member loginMember
		){
		/**
		 * 페이징
		 */
		InterestStoreList interestStoreList = queryMemberService.getInterestStoreList(loginMember.getEmail());
		return new ResponseEntity<>(interestStoreList, HttpStatus.OK);
	}
	
	@ApiOperation("관심 의류 목록 가져오기")
	@GetMapping("interest-product")
	public ResponseEntity<InterestProductList> getInterestProductList(
			@ApiIgnore
			@LoginUser Member loginMember
		){
		/**
		 * 페이징
		 */
		InterestProductList interestProductList = queryMemberService.getInterestProductList(loginMember.getEmail());
		return new ResponseEntity<>(interestProductList, HttpStatus.OK);
	}
}
