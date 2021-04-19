package com.li.schoolGo.bean;


import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "school_info")
public class SchoolInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String parentId;

    private String name;

    @Transient
    private String parentName;

}
