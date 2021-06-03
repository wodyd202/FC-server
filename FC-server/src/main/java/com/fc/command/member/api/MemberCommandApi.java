package com.fc.command.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.member.MemberService;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.command.member.infra.validator.ChangePasswordValidator;
import com.fc.command.member.infra.validator.CreateMemberValidator;
import com.fc.command.member.model.MemberCommand;
import com.fc.config.security.LoginUser;
import com.fc.domain.member.Member;
import com.fc.domain.member.StoreOwner;
import com.fc.domain.member.StoreProductId;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/member")
@Api(tags = "회원 상태 관련 API")
public class MemberCommandApi {
	private MemberService memberService;
	private CreateMemberValidator createMemberValidator;
	private ChangePasswordValidator changePasswordValidator; 
	private ChangeAddressValidator changeAddressValidator;
	private AddressDetailGetter addressGetter;
	
	@ApiOperation("상품 관심 주기")
	@PostMapping("{targetProductId}/product/interest")
	public ResponseEntity<StoreProductId> execute(
			@PathVariable StoreProductId targetProductId,
			@ApiIgnore
			@LoginUser com.fc.domain.member.read.Member loginMember
			){
		memberService.interestProduct(loginMember.getEmail(), targetProductId);
		return new ResponseEntity<>(targetProductId, HttpStatus.OK);
	} 
	
	@ApiOperation("업체 관심 주기")
	@PostMapping("{targetStoreOwner}/store/interest")
	public ResponseEntity<StoreOwner> execute(
			@PathVariable StoreOwner targetStoreOwner,
			@ApiIgnore
			@LoginUser com.fc.domain.member.read.Member loginMember
		){
		memberService.interestStore(loginMember.getEmail(), targetStoreOwner);
		return new ResponseEntity<>(targetStoreOwner, HttpStatus.OK);
	} 
	
	@ApiOperation("회원가입")
	@PostMapping
	public ResponseEntity<Member> execute(
			@RequestBody MemberCommand.CreateMember command
		){
		Member createMember = memberService.create(createMemberValidator, command);
		return new ResponseEntity<>(createMember,HttpStatus.CREATED);
	}
	
	@ApiOperation("비밀번호 변경")
	@PutMapping("password")
	public ResponseEntity<Member> execute(
			@RequestBody MemberCommand.ChangePassword command, 
			@ApiIgnore
			@LoginUser com.fc.domain.member.read.Member loginMember
		){
		Member member = memberService.changePassword(changePasswordValidator, loginMember.getEmail(), command);
		return new ResponseEntity<>(member, HttpStatus.OK);
	}

	@ApiOperation("주소 변경")
	@PutMapping("address")
	public ResponseEntity<Member> execute(
			@RequestBody MemberCommand.ChangeAddress command,
			@ApiIgnore
			@LoginUser com.fc.domain.member.read.Member loginMember
		){
		Member member = memberService.changeAddress(changeAddressValidator, addressGetter, loginMember.getEmail(), command);
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
}
