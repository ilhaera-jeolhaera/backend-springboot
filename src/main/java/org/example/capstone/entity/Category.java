package org.example.capstone.entity;

public enum Category {
  FREE,
  BLOG,
  SHARE,
  QNA;

  public static Category from(String value) {
    try {
      return Category.valueOf(value.toUpperCase());
    } catch (Exception e) {
      throw new IllegalArgumentException("올바르지 않은 카테고리입니다" + value);
    }
  }

}
