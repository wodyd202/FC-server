package com.fc.query.store.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fc.domain.store.Owner;
import com.fc.domain.store.read.QStore;
import com.fc.domain.store.read.Store;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreSearch;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class JdbcStoreRepository implements StoreRepository {

	@Autowired
	private DataSource dataSource;

	@PersistenceContext
	private EntityManager em;

	private QStore store = QStore.store;

	@Override
	public Optional<Store> findByOwner(Owner owner) {
		JPAQuery<Store> query = new JPAQuery<>(em);
		return Optional.ofNullable(query.from(store).where(store.owner.eq(owner)).fetchOne());
	}

	@Override
	public Optional<Store> findDetailByOwner(Owner owner) {
		JPAQuery<Store> query = new JPAQuery<>(em);
		return Optional.ofNullable(query.from(store).where(store.owner.eq(owner)).fetchOne());
	}

	@Override
	public List<StoreQuery.StoreList> findAll(StoreSearch dto) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT `store`.*, CONCAT(CONCAT((SELECT `name` FROM `store_tags` WHERE `store_owner` = `store`.`owner`),\",\")) AS 'tags' FROM `store` WHERE ");
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
		if(distanceCoordinateDifference != null && longtitude != null && letitude != null) {
			appendAndCondition(sqlBuilder, endFlag);
			params.add(Integer.toString(letitude));
			params.add(Integer.toString(longtitude));
			params.add(Integer.toString(letitude));
			params.add(Integer.toString(distanceCoordinateDifference));
			sqlBuilder.append("(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(letitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(letitude)))) > ?");
		}
		
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<StoreQuery.StoreList> stores = new ArrayList<>();
		try(
			Connection connection = dataSource.getConnection();
		){
			stmt = connection.prepareStatement(sqlBuilder.toString());
			for(int i = 0;i<params.size();i++) {
				stmt.setString(i + 1, params.get(i));
			}
			resultSet = stmt.executeQuery();
			
			while(resultSet.next()) {
				stores.add(StoreQuery.StoreList.builder()
						.businessTitle(resultSet.getString("business_name"))
						.storeTags(resultSet.getString("tags"))
						.imagePath(resultSet.getString("path"))
						.weekdayStartTime(resultSet.getInt("weekday_start_time"))
						.weekdayEndTime(resultSet.getInt("weekday_end_time"))
						.weekendStartTime(resultSet.getInt("weekend_start_time"))
						.weekendEndTime(resultSet.getInt("weekend_end_time"))
						.holiday("holidays")
						.build());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			}catch (Exception e) {}
			try {
				if(stmt != null) {
					stmt.close();
				}
			}catch (Exception e) {}
		}
		return stores;
	}

	private void appendAndCondition(StringBuilder sqlBuilder, boolean endFlag) {
		if (endFlag) {
			sqlBuilder.append(" AND ");
		}
	}
}
