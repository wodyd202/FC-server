package com.fc.query.product.infra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fc.domain.product.Owner;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductImage;
import com.fc.domain.product.ProductImage.ProductImageType;
import com.fc.query.product.model.ProductQuery;
import com.fc.query.product.model.ProductSearch;
import com.fc.query.product.model.ProductQuery.ProductDetail;
import com.fc.query.product.model.ProductQuery.ProductList;

@Repository
public class JdbcProductRepository implements ProductRepository{

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public List<ProductQuery.ProductList> findAll(Owner owner, ProductSearch dto) {
		List<String> params = Arrays.asList(owner.getEmail());
		StringBuilder sqlBuilder = new StringBuilder("SELECT `product_id`, `category`, `create_date_time`, `price`, `sizes`, `tags`, `title`,");
		sqlBuilder.append(" (SELECT `path` FROM `product_images` WHERE `product_id` = `product`.`product_id` AND `type` = 'MAIN') AS mainImage ");
		sqlBuilder.append("FROM `product` ");
		sqlBuilder.append("WHERE `email` = ? ");
		sqlBuilder.append("AND `state` = 'SELL' ");
		
		String category = dto.getCategory();
		if(category != null && !category.isEmpty()) {
			sqlBuilder.append("AND `category` = ? ");
			params.add(category);
		}

		sqlBuilder.append("LIMIT " + dto.getPage() * dto.getSize() + "," + dto.getSize());
		
		return template.query(sqlBuilder.toString(), new RowMapper<ProductQuery.ProductList>() {
			@Override
			public ProductList mapRow(ResultSet rs, int rowNum) throws SQLException {
				return ProductList.builder()
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
	public Optional<ProductDetail> findDetailByProductId(ProductId productId) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT `product`.`product_id`, `category`, `create_date_time`, `price`, `sizes`, `tags`, `title`, `image_id`, `type`, `path` ");
		sqlBuilder.append("FROM `product` LEFT JOIN `product_images` ON `product`.`product_id` = `product_images`.`product_id` ");
		sqlBuilder.append("WHERE `product`.`product_id` = ? AND `state` = 'SELL'");
		
		List<String> params = Arrays.asList(productId.getId());
		
		List<ProductImage> images = new ArrayList<>();
		List<ProductDetail> result = template.query(sqlBuilder.toString(),new RowMapper<ProductDetail>() {
			@Override
			public ProductDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductImage image = new ProductImage(
						rs.getString("image_id"), 
						rs.getString("path"), 
						ProductImageType.valueOf(rs.getString("type")));
				images.add(image);
				return ProductDetail.builder()
						.productId(rs.getString("product_id"))
						.title(rs.getString("title"))
						.tags(rs.getString("tags"))
						.price(rs.getInt("price"))
						.size(rs.getString("sizes"))
						.build();
			}
		},params.toArray());
		if(result != null) {
			ProductDetail productDetail = result.get(0);
			productDetail.addImages(images);
			return Optional.of(productDetail);
		}
		return Optional.ofNullable(null);
	}

}
