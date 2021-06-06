package com.fc.query.store.infra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store.StoreState;
import com.fc.domain.store.read.QStore;
import com.fc.domain.store.read.Store;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreQuery.StoreList;
import com.fc.query.store.model.StoreQuery.StoreListData;
import com.fc.query.store.model.StoreQuery.StoreMainInfo;
import com.fc.query.store.model.StoreSearch;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class JdbcStoreRepository implements StoreRepository {

	@Autowired
	private JdbcTemplate template;

	@PersistenceContext
	private EntityManager em;

	private QStore store = QStore.store;

	@Override
	public boolean existByOnwer(Owner owner) {
		JPAQuery<Store> query = new JPAQuery<>(em);
		return query.from(store)
				.where(store.owner.eq(owner)
						.and(store.state.ne(StoreState.CLOSED))
						.and(store.state.ne(StoreState.NOT_SELL)))
				.fetchCount() == 1 ? true : false;
	}
	
	@Override
	public Optional<StoreQuery.StoreMainInfo> findByOwner(Owner owner, Member loginMember) {
		List<String> params = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;{
			add(owner.getEmail());
		}};

		StringBuilder sqlBuilder = new StringBuilder("SELECT ");
		sqlBuilder.append("`business_name`, ");
		sqlBuilder.append("`longtitude`, ");
		sqlBuilder.append("`letitude`, ");
		sqlBuilder.append("`province`, ");
		sqlBuilder.append("`city`, ");
		sqlBuilder.append("`neighborhood`, ");
		sqlBuilder.append("`first`, ");
		sqlBuilder.append("`second`, ");
		sqlBuilder.append("`third`, ");
		sqlBuilder.append("`weekday_start_time`, ");
		sqlBuilder.append("`weekday_end_time`, ");
		sqlBuilder.append("`weekend_start_time`, ");
		sqlBuilder.append("`weekend_end_time`, ");
		sqlBuilder.append("`holidays` ");
		
		if(loginMember != null) {
			sqlBuilder.append(", `IF((` ");
			sqlBuilder.append("SELECT COUNT(`email`) ");
			sqlBuilder.append("FROM `member_store_interest` ");
			sqlBuilder.append("WHERE `member_store_interest`.`member_email` = ? ");
			sqlBuilder.append("AND `member_store_interest`.`email` = `store`.`owner`) >= 1, 'true','false') AS interest ");
			params.add(0, loginMember.getEmail().getValue());
		}
		
		sqlBuilder.append("FROM `store` ");
		sqlBuilder.append("WHERE `owner` = ? ");
		sqlBuilder.append("AND `state` != 'DELETE'");

		try {
		return Optional
				.ofNullable(template.queryForObject(sqlBuilder.toString(), new RowMapper<StoreQuery.StoreMainInfo>() {
					@Override
					public StoreMainInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
						StoreMainInfo storeMainInfo = StoreMainInfo.builder()
								.businessTitle(rs.getString("business_name"))
								.longtitude(rs.getDouble("longtitude"))
								.letitude(rs.getDouble("letitude"))
								.province(rs.getString("province"))
								.city(rs.getString("city"))
								.neighborhood(rs.getString("neighborhood"))
								.phone(rs.getString("first") + "-" + rs.getString("second") + "-" + rs.getString("third"))
								.weekdayStartTime(rs.getInt("weekday_start_time"))
								.weekdayEndTime(rs.getInt("weekday_end_time"))
								.weekendStartTime(rs.getInt("weekend_start_time"))
								.weekendEndTime(rs.getInt("weekend_end_time"))
								.holiday(rs.getString("holidays"))
								.build();
						if(loginMember != null) {
							storeMainInfo.addInterestState(Boolean.parseBoolean(rs.getString("interest")));
						}
						return storeMainInfo;
					}

				}, params.toArray()));
		}catch (EmptyResultDataAccessException  e) {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public Optional<Store> findDetailByOwner(Owner owner) {
		JPAQuery<Store> query = new JPAQuery<>(em);
		return Optional.ofNullable(query.from(store).where(store.owner.eq(owner)).fetchOne());
	}

	@Override
	public StoreQuery.StoreList findAll(StoreSearch dto, Member loginMember) {
		List<String> params = new ArrayList<>();

		StringBuilder selectCountSqlBuilder = new StringBuilder("SELECT count(*) AS totalCount ");
		StringBuilder selectListSqlBuilder = new StringBuilder("SELECT `store`.*, ");
		selectListSqlBuilder.append("(SELECT GROUP_CONCAT(`name`) FROM `store_tags` WHERE `store_owner` = `store`.`owner`) AS tags, ");
		selectListSqlBuilder.append("(SELECT COUNT(`email`) FROM `member_store_interest` WHERE `member_store_interest`.`member_email` = `store`.`owner`) AS interestCnt ");
		
		if(loginMember != null) {
			selectListSqlBuilder.append(", `IF((` ");
			selectListSqlBuilder.append("SELECT COUNT(`email`) ");
			selectListSqlBuilder.append("FROM `member_store_interest` ");
			selectListSqlBuilder.append("WHERE `member_store_interest`.`member_email` = ? ");
			selectListSqlBuilder.append("AND `member_store_interest`.`email` = `store`.`owner`) >= 1, 'true','false') AS interest ");
			params.add(loginMember.getEmail().getValue());
		}
		
		selectCountSqlBuilder.append("FROM `store` ");
		selectListSqlBuilder.append("FROM `store` ");
		
		StringBuilder whereSqlBuilder = new StringBuilder();
		
		whereSqlBuilder.append("WHERE `state` = 'SELL' ");

		String title = dto.getTitle();
		if (title != null && !title.isEmpty()) {
			whereSqlBuilder.append("AND `business_name` like ? ");
			params.add(title);
		}

		String tag = dto.getTag();
		if (tag != null && !tag.isEmpty()) {
			whereSqlBuilder.append("`owner` in (");
			whereSqlBuilder.append("SELECT `store_owner` ");
			whereSqlBuilder.append("FROM `store_tags` WHERE ");
			whereSqlBuilder.append("`name` = ?");
			whereSqlBuilder.append(") ");
			params.add(tag);
		}

		String province = dto.getProvince();
		if (province != null && !province.isEmpty()) {
			whereSqlBuilder.append("AND `province` = ?");
			params.add(province);
		}

		String neighborhood = dto.getNeighborhood();
		if (neighborhood != null && !neighborhood.isEmpty()) {
			whereSqlBuilder.append("AND `neighborhood` = ? ");
			params.add(neighborhood);
		}

		String city = dto.getCity();
		if (city != null && !city.isEmpty()) {
			whereSqlBuilder.append("AND `city` = ? ");
			params.add(city);
		}

		Integer distanceCoordinateDifference = dto.getDistanceCoordinateDifference();
		Double letitude = dto.getLetitude();
		Double longtitude = dto.getLongtitude();
		if (distanceCoordinateDifference != null && longtitude != null && letitude != null) {
			params.add(Double.toString(letitude));
			params.add(Double.toString(longtitude));
			params.add(Double.toString(letitude));
			params.add(Integer.toString(distanceCoordinateDifference));
			whereSqlBuilder.append(
					"AND (6371*ACOS(COS(RADIANS(?))*COS(RADIANS(letitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(letitude)))) <= ?");
		}
		
		Object[] paramArray = params.toArray();
		long totalCount = template.queryForObject(selectCountSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("totalCount");
			}
			
		}, paramArray);
		List<StoreListData> storeListData = template.query(selectListSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<StoreQuery.StoreListData>() {
			@Override
			public StoreListData mapRow(ResultSet rs, int rowNum) throws SQLException {
				StoreListData storeList = StoreQuery.StoreListData.builder()
						.businessTitle(rs.getString("business_name"))
						.storeTags(rs.getString("tags"))
						.imagePath(rs.getString("path"))
						.weekdayStartTime(rs.getInt("weekday_start_time"))
						.weekdayEndTime(rs.getInt("weekday_end_time"))
						.weekendStartTime(rs.getInt("weekend_start_time"))
						.weekendEndTime(rs.getInt("weekend_end_time"))
						.holiday("holidays")
						.interestCnt(rs.getInt("interestCnt"))
						.build();
				if(loginMember != null) {
					storeList.addInterestState(Boolean.parseBoolean(rs.getString("interest")));
				}
				return storeList;
			}
		}, paramArray);
		return new StoreList(storeListData, totalCount);
	}

}
