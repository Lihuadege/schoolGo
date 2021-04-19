package com.li.schoolGo.bean;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

//留言
@Data
@Table(name = "comment_info")
public class CommentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String content;

    private String articleId;

    private String userId;

    private String userNickName;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    //头像url
    private String avatar;

    private String commend;

    private String articleTitle;

    private Integer status;

}