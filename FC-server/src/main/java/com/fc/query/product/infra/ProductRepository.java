package com.fc.query.product.infra;

import java.util.List;
import java.util.Optional;

import com.fc.domain.member.read.Member;
import com.fc.domain.product.Owner;
import com.fc.domain.product.ProductId;
import com.fc.query.product.model.ProductQuery;
import com.fc.query.product.model.ProductSearch;

public interface ProductRepository {
	ProductQuery.ProductList findAll(Owner owner, ProductSearch dto, Member loginMember);
	
	List<ProductQuery.ProductListData> findNewProducts(Owner owner);
	
	Optional<ProductQuery.ProductDetail> findDetailByProductId(ProductId productId, Member loginMember);

	boolean existByProductId(ProductId productId);
}
