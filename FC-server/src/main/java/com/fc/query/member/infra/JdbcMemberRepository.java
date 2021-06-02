package com.fc.query.member.infra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.domain.member.read.QMember;
import com.fc.query.member.model.MemberQuery;
import com.fc.query.member.model.MemberQuery.Address;
import com.fc.query.member.model.MemberQuery.InterestProductData;
import com.fc.query.member.model.MemberQuery.InterestProductList;
import com.fc.query.member.model.MemberQuery.InterestStoreData;
import com.fc.query.member.model.MemberQuery.InterestStoreList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class JdbcMemberRepository implements MemberRepository{

	@Autowired
	private JdbcTemplate template;
	
	@PersistenceContext
	private EntityManager em;
	
	private QMember member = QMember.member;
	
	@Override
	public Optional<Member> findByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return Optional.ofNullable(
				query
				.select(Projections.constructor(Member.class, 
						member.email,
						member.password(),
						member.rule
					))
				.from(member)
				.where(member.email.eq(email)).fetchOne());
	}

	@Override
	public boolean existByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return query.from(member).where(member.email.eq(email)).fetchCount() == 0 ? false : true;
	}

	@Override
	public Optional<Address> findAddressByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return Optional.ofNullable(
				query
				.select(Projections.constructor(Address.class, 
						member.address().longtitude,
						member.address().letitude,
						member.address().province,
						member.address().city,
						member.address().neighborhood
					))
				.from(member)
				.where(member.email.eq(email))
				.fetchOne()
			);
	}

	@Override
	public InterestStoreList findInterestStoreListByEmail(
			Email email
		) {
		@SuppressWarnings("serial")
		ArrayList<String> params = new ArrayList<String>() {{
			add(email.getValue());
		}};
		
		StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(`owner`) AS totalCount ");
		
		StringBuilder selectSqlBuilder = new StringBuilder("SELECT `owner`, `longtitude`, `letitude`, `path`, `business_name`, ");
		selectSqlBuilder.append("CONCAT((SELECT `name` FROM `store_tags` WHERE `store_owner` = `store`.`owner`), \",\") AS tags ");
		countSqlBuilder.append("FROM `store` ");
		selectSqlBuilder.append("FROM `store` ");
		
		StringBuilder whereSqlBuilder = new StringBuilder("WHERE `owner` IN ");
		whereSqlBuilder.append("(SELECT `email` ");
		whereSqlBuilder.append("FROM `member_store_interest` ");
		whereSqlBuilder.append("WHERE `member_email` = ?) ");

		Object[] paramArray = params.toArray();
		
		long totalCount = template.queryForObject(countSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("totalCount");
			}
		}, paramArray);
		
		List<InterestStoreData> interestStoreList = template.query(selectSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<MemberQuery.InterestStoreData>() {
			@Override
			public InterestStoreData mapRow(ResultSet rs, int rowNum) throws SQLException {
				return InterestStoreData.builder()
						.mainImage(rs.getString("path"))
						.businessName(rs.getString("business_name"))
						.longtitude(rs.getDouble("longtitude"))
						.letitude(rs.getDouble("letitude"))
						.tags(rs.getString("tags"))
						.build();
			}
		}, paramArray);
		
		return new InterestStoreList(interestStoreList, totalCount);
	}

	@Override
	public InterestProductList findInterestProductListByEmail(Email email) {
		@SuppressWarnings("serial")
		ArrayList<String> params = new ArrayList<String>() {{
			add(email.getValue());
		}};
		
		StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(`product_id`) AS totalCount ");
		
		StringBuilder selectSqlBuilder = new StringBuilder("SELECT ");
		selectSqlBuilder.append("(SELECT `path` FROM `product_images` WHERE `product_images`.`product_id` = `product`.`product_id` AND `product_images`.`type` = 'MAIN') AS mainImage, ");
		selectSqlBuilder.append("`title`, `price`, `tags`, ");
		selectSqlBuilder.append("(SELECT `business_name` FROM `store` WHERE `store`.`owner` = `product`.`email`) AS businessName ");
		
		countSqlBuilder.append("FROM `product` ");
		selectSqlBuilder.append("FROM `product` ");
		
		StringBuilder whereSqlBuilder = new StringBuilder("WHERE `product_id` IN ");
		whereSqlBuilder.append("(SELECT `id` ");
		whereSqlBuilder.append("FROM `member_product_interest`");
		whereSqlBuilder.append("WHERE `member_email` = ?)");
		
		Object[] paramArray = params.toArray();
		
		long totalCount = template.queryForObject(countSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("totalCount");
			}
		},paramArray);
		
		List<InterestProductData> interestProductList = template.query(selectSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<InterestProductData>() {
			@Override
			public InterestProductData mapRow(ResultSet rs, int rowNum) throws SQLException {
				return InterestProductData
						.builder()
						.mainImage(rs.getString("mainImage"))
						.title(rs.getString("title"))
						.price(rs.getInt("price"))
						.tags(rs.getString("tags"))
						.storeName(rs.getString("businessName"))
						.build();
			}
		},paramArray);
		return new InterestProductList(interestProductList, totalCount);
	}

}
