package com.fc.query.store.infra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fc.domain.store.Owner;
import com.fc.domain.store.Store.StoreState;
import com.fc.domain.store.read.QStore;
import com.fc.domain.store.read.Store;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreQuery.StoreList;
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
	public Optional<StoreQuery.StoreMainInfo> findByOwner(Owner owner) {
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
		sqlBuilder.append("FROM `store` ");
		sqlBuilder.append("WHERE `owner` = ? ");
		sqlBuilder.append("AND `state` != 'DELETE'");

		List<String> params = Arrays.asList(owner.getEmail());

		try {
		return Optional
				.ofNullable(template.queryForObject(sqlBuilder.toString(), new RowMapper<StoreQuery.StoreMainInfo>() {
					@Override
					public StoreMainInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
						return StoreMainInfo.builder()
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
	public List<StoreQuery.StoreList> findAll(StoreSearch dto) {
		StringBuilder sqlBuilder = new StringBuilder(
				"SELECT `store`.*, CONCAT(CONCAT((SELECT `name` FROM `store_tags` WHERE `store_owner` = `store`.`owner`),\",\")) AS 'tags' FROM `store` WHERE ");
		List<String> params = new ArrayList<>();
		boolean endFlag = false;

		String title = dto.getTitle();
		if (title != null && !title.isEmpty()) {
			appendAndCondition(sqlBuilder, endFlag);
			sqlBuilder.append("`business_name` like ?");
			params.add(title);
			endFlag = true;
		}

		String tag = dto.getTag();
		if (tag != null && !tag.isEmpty()) {
			appendAndCondition(sqlBuilder, endFlag);
			sqlBuilder.append("`owner` in (");
			sqlBuilder.append("SELECT `store_owner` ");
			sqlBuilder.append("FROM `store_tags` WHERE ");
			sqlBuilder.append("`name` = ?");
			params.add(tag);
			endFlag = true;
			sqlBuilder.append(")");
		}

		String province = dto.getProvince();
		if (province != null && !province.isEmpty()) {
			appendAndCondition(sqlBuilder, endFlag);
			sqlBuilder.append("`province` = ?");
			params.add(province);
			endFlag = true;
		}

		String neighborhood = dto.getNeighborhood();
		if (neighborhood != null && !neighborhood.isEmpty()) {
			appendAndCondition(sqlBuilder, endFlag);
			sqlBuilder.append("`neighborhood` = ?");
			params.add(neighborhood);
			endFlag = true;
		}

		String city = dto.getCity();
		if (city != null && !city.isEmpty()) {
			appendAndCondition(sqlBuilder, endFlag);
			sqlBuilder.append("`city` = ?");
			params.add(city);
			endFlag = true;
		}

		Integer distanceCoordinateDifference = dto.getDistanceCoordinateDifference();
		Integer letitude = dto.getLetitude();
		Integer longtitude = dto.getLongtitude();
		if (distanceCoordinateDifference != null && longtitude != null && letitude != null) {
			appendAndCondition(sqlBuilder, endFlag);
			params.add(Integer.toString(letitude));
			params.add(Integer.toString(longtitude));
			params.add(Integer.toString(letitude));
			params.add(Integer.toString(distanceCoordinateDifference));
			sqlBuilder.append(
					"(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(letitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(letitude)))) > ?");
		}
		return template.query(sqlBuilder.toString(), new RowMapper<StoreQuery.StoreList>() {
			@Override
			public StoreList mapRow(ResultSet rs, int rowNum) throws SQLException {
				return StoreQuery.StoreList.builder()
						.businessTitle(rs.getString("business_name"))
						.storeTags(rs.getString("tags"))
						.imagePath(rs.getString("path"))
						.weekdayStartTime(rs.getInt("weekday_start_time"))
						.weekdayEndTime(rs.getInt("weekday_end_time"))
						.weekendStartTime(rs.getInt("weekend_start_time"))
						.weekendEndTime(rs.getInt("weekend_end_time"))
						.holiday("holidays")
						.build();
			}
		}, params.toArray());
	}

	private void appendAndCondition(StringBuilder sqlBuilder, boolean endFlag) {
		if (endFlag) {
			sqlBuilder.append(" AND ");
		}
	}

}
