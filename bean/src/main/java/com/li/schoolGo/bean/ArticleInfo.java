package com.li.schoolGo.bean;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "article_info")
public class ArticleInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userId;

    private String userNickName;

    private String userHeadImg;

    private String title;

    private String mainBody;

    //点赞
    private String commend;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    private Integer status;

    @Transient
    private List<CommentInfo> commentInfoList;

    @Transient
    private List<ArticleImgInfo> articleImgInfoList;

}
