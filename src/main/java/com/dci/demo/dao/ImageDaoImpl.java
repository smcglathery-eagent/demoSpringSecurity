package com.dci.demo.dao;

import com.dci.demo.dao.rowmapper.ImageRowMapper;
import com.dci.demo.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;

@Repository
public class ImageDaoImpl extends JdbcDaoSupport implements ImageDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() { setDataSource(dataSource);}

    public int addImage(MultipartFile multipartFile, String contentType) throws IOException {

        final String query = "INSERT INTO images " +
                "(fileName, contentType, contents) " +
                "VALUES (:filename, :contentType, :contents)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("filename", multipartFile.getOriginalFilename());
        mapSqlParameterSource.addValue("contentType", contentType);
        mapSqlParameterSource.addValue("contents", multipartFile.getBytes());

        return jdbcTemplate.update(query, mapSqlParameterSource);
    }

    public Image getImageByName(String fileName) {

        final String query = "SELECT id, fileName, contentType, contents " +
                "FROM images " +
                "WHERE fileName = :fileName";

        MapSqlParameterSource mapSqlParameterSource =  new MapSqlParameterSource();
        mapSqlParameterSource.addValue("fileName", fileName);

        return jdbcTemplate.queryForObject(query, mapSqlParameterSource, new ImageRowMapper());
    }
}