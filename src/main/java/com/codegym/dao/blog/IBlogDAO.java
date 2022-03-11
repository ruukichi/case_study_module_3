package com.codegym.dao.blog;

import com.codegym.dao.IGeneralDAO;
import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.model.User;

import java.util.List;
import java.util.Map;

public interface IBlogDAO extends IGeneralDAO<Blog> {
    Category findCategoryByBlogId (int id);
    String getUserNameByBlogId (int id);
    Map<Integer, String>getMap_userId_userName();
    List<Blog> findAllBlogByUserId (int user_id);
    List<Blog> findAllBlogByCategoryID (int category_id);
    List<Blog> findAllBlogByUsername (String username);
}
