package com.bookhaven.ecom.services;

import java.util.List;

import com.bookhaven.ecom.dto.CategoryDto;
import com.bookhaven.ecom.entity.Category;

public interface CategoryService {
	Category createcategory(CategoryDto categoryDto);
	List<Category> getAllCategories();
}
