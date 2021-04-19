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

@Data
@Table(name = "banner_info")
/**
 * 轮播图对应javaBean，考量应该是数量在2-5之间最为合适
 */
public class BannerInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String imgUrl;

    private String imgName;

    private String requestUrl;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date deleteTime;

    private Integer status;

}
