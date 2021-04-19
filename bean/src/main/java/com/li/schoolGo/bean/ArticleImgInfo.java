package com.li.schoolGo.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "article_img_info")
public class ArticleImgInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String articleId;

    private String url;

}
