package com.dci.demo.dao.rowmapper;

import com.dci.demo.model.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRowMapper implements RowMapper<Image> {

    public Image mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Image image = new Image();
        image.setId(resultSet.getInt("id"));
        image.setFileName(resultSet.getString("filename"));
        image.setContentType(resultSet.getString("contentType"));
        image.setContents(resultSet.getBytes("contents"));

        return image;
    }
}