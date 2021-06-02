package com.fc.query.product.infra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fc.domain.member.read.Member;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product.ProductState;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductImage;
import com.fc.domain.product.ProductImage.ProductImageType;
import com.fc.domain.product.read.Product;
import com.fc.domain.product.read.QProduct;
import com.fc.query.product.model.ProductQuery;
import com.fc.query.product.model.ProductQuery.ProductDetail;
import com.fc.query.product.model.ProductQuery.ProductList;
import com.fc.query.product.model.ProductQuery.ProductListData;
import com.fc.query.product.model.ProductSearch;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class JdbcProductRepository implements ProductRepository{

	@Autowired
	private JdbcTemplate template;
	
	@PersistenceContext
	private EntityManager em;
	
	private QProduct product = QProduct.product;
	
	@Override
	public ProductQuery.ProductList findAll(Owner owner, ProductSearch dto, Member loginMember) {
		List<String> params = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;{
			add(owner.getEmail());
		}};
		
		StringBuilder selectCountSqlBuilder = new StringBuilder("SELECT COUNT(*) AS totalCount ");
		StringBuilder selectListSqlBuilder = new StringBuilder("SELECT `product_id`, `category`, `create_date_time`, `price`, `sizes`, `tags`, `title`,");
		selectListSqlBuilder.append(" (SELECT `path` FROM `product_images` WHERE `product_id` = `product`.`product_id` AND `type` = 'MAIN') AS mainImage ");
		
		if(loginMember != null) {
			selectListSqlBuilder.append(", SELECT COUNT(`id`) ");
			selectListSqlBuilder.append("FROM `member_product_interest` ");
			selectListSqlBuilder.append("WHERE `member_product_interest`.`member_email` = ? ");
			selectListSqlBuilder.append("AND `member_product_interest`.`id` = `product`.`product_id`) >= 1, 'true','false') AS interest ");
			params.add(loginMember.getEmail().getValue());
		}
		
		selectListSqlBuilder.append("FROM `product` ");
		selectCountSqlBuilder.append("FROM `product` ");
		
		StringBuilder whereSqlBuilder = new StringBuilder("WHERE `email` = ? ");
		whereSqlBuilder.append("AND `state` = 'SELL' ");
		
		String category = dto.getCategory();
		if(category != null && !category.isEmpty()) {
			whereSqlBuilder.append("AND `category` = ? ");
			params.add(category);
		}
		
		StringBuilder limitSqlBuilder = new StringBuilder("LIMIT " + dto.getPage() * dto.getSize() + "," + dto.getSize());
		
		Object[] paramArray = params.toArray();
		
		List<ProductListData> productList = template.query(selectListSqlBuilder.toString() + whereSqlBuilder.toString() + limitSqlBuilder.toString()
			, new RowMapper<ProductQuery.ProductListData>() {
			@Override
			public ProductListData mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductListData productList = ProductListData.builder()
					.productId(rs.getString("product_id"))
					.category(rs.getString("category"))
					.title(rs.getString("title"))
					.mainImagePath(rs.getString("mainImage"))
					.size(rs.getString("sizes"))
					.price(rs.getInt("price"))
					.build();
				if(loginMember != null) {
					productList.addInterestState(Boolean.parseBoolean(rs.getString("interest")));
				}
				return productList;
			}
		}, paramArray);
		
		long totalCount = template.queryForObject(selectCountSqlBuilder.toString() + whereSqlBuilder.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("totalCount");
			}
		},paramArray);
		return new ProductList(productList, totalCount);
	}


	@Override
	public List<ProductListData> findNewProducts(Owner owner) {
		List<String> params = Arrays.asList(owner.getEmail());
		StringBuilder sqlBuilder = new StringBuilder("SELECT `product_id`, `category`, `create_date_time`, `price`, `sizes`, `tags`, `title`,");
		sqlBuilder.append(" (SELECT `path` FROM `product_images` WHERE `product_id` = `product`.`product_id` AND `type` = 'MAIN') AS mainImage ");
		sqlBuilder.append("FROM `product` ");
		sqlBuilder.append("WHERE `email` = ? ");
		sqlBuilder.append("AND `state` = 'SELL' ");
		sqlBuilder.append("ORDER BY `create_date_time` DESC ");
		sqlBuilder.append("limit 0, 6");
		
		return template.query(sqlBuilder.toString(), new RowMapper<ProductQuery.ProductListData>() {
			@Override
			public ProductListData mapRow(ResultSet rs, int rowNum) throws SQLException {
				return ProductListData.builder()
					.productId(rs.getString("product_id"))
					.category(rs.getString("category"))
					.title(rs.getString("title"))
					.mainImagePath(rs.getString("mainImage"))
					.size(rs.getString("sizes"))
					.price(rs.getInt("price"))
					.build();
			}
		}, params.toArray());
	}
	
	@Override
	public Optional<ProductDetail> findDetailByProductId(ProductId productId, Member loginMember) {
		List<String> params = Arrays.asList(productId.getId());

		StringBuilder sqlBuilder = new StringBuilder("SELECT `product`.`product_id`, `category`, `create_date_time`, `price`, `sizes`, `tags`, `title`, `image_id`, `type`, `path`, ");
		
		sqlBuilder.append("(SELECT COUNT(`id`) ");
		sqlBuilder.append("FROM `member_product_interest` ");
		sqlBuilder.append("WHERE `member_product_interest`.`id` = `product`.`product_id`) AS interestCnt ");
		
		if(loginMember != null) {
			sqlBuilder.append(", `IF((` ");
			sqlBuilder.append("SELECT COUNT(`id`) ");
			sqlBuilder.append("FROM `member_product_interest` ");
			sqlBuilder.append("WHERE `member_product_interest`.`member_email` = ? ");
			sqlBuilder.append("AND `member_product_interest`.`id` = `product`.`product_id`) >= 1, 'true','false') AS interest ");
			params.add(0, loginMember.getEmail().getValue());
		}
		
		sqlBuilder.append("FROM `product` LEFT JOIN `product_images` ON `product`.`product_id` = `product_images`.`product_id` ");
		sqlBuilder.append("WHERE `product`.`product_id` = ? AND `state` = 'SELL'");
		
		List<ProductImage> images = new ArrayList<>();
		List<ProductDetail> result = template.query(sqlBuilder.toString(),new RowMapper<ProductDetail>() {
			@Override
			public ProductDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductImage image = new ProductImage(
						rs.getString("image_id"), 
						rs.getString("path"), 
						ProductImageType.valueOf(rs.getString("type"))
					);
				images.add(image);
				ProductDetail productDetail = ProductDetail.builder()
						.productId(rs.getString("product_id"))
						.title(rs.getString("title"))
						.tags(rs.getString("tags"))
						.price(rs.getInt("price"))
						.size(rs.getString("sizes"))
						.interestCnt(rs.getLong("interestCnt"))
						.build();
				if(loginMember != null) {
					productDetail.addInterestState(Boolean.parseBoolean(rs.getString("interest")));
				}
				return productDetail;
			}
		},params.toArray());
		if(result != null && result.size() != 0) {
			ProductDetail productDetail = result.get(0);
			productDetail.addImages(images);
			return Optional.of(productDetail);
		}
		return Optional.ofNullable(null);
	}


	@Override
	public boolean existByProductId(ProductId productId) {
		JPAQuery<Product> query = new JPAQuery<>(em);
		return query.from(product).where(product.id.eq(productId).and(product.state.eq(ProductState.SELL))).fetchCount() >= 1 ? true : false;
	}
}
