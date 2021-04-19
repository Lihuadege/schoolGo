package com.li.schoolGo.bean;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Table(name = "goods_info")
public class GoodsInfo implements Serializable {

    @Id
    @Column
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //用这个注解代替上面的，取回生成的主键
    @KeySql(useGeneratedKeys = true, dialect = IdentityDialect.MYSQL)
    private String id;

    private String userId;

    private String sysUserId;

    private String title;

    private String categoryId;

    @Transient
    private String categoryName;

    private String coverImg;

    private String detailDesc;

    private List<GoodsImgInfo> goodsImgInfoList;

    private Integer status;

    private Double price;

    private Integer isPersonal;

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;


}
