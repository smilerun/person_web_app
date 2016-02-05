package com.bnaqica.person.util;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class DatabaseUtils {
	public static <T> Optional<T> queryForOptional(JdbcOperations jdbcOperations, String sql, RowMapper<T> rowMapper, Object... args) {
        return optionalFromList(jdbcOperations.query(sql, rowMapper, args));
    }

    public static <T> Optional<T> queryForOptional(JdbcOperations jdbcOperations, String sql, Class<T> klass, Object... args) {
        return optionalFromList(jdbcOperations.queryForList(sql, klass, args));
    }

    public static <T> Optional<T> queryForOptional(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql, SqlParameterSource params, RowMapper<T> rowMapper) {
        return optionalFromList(namedParameterJdbcTemplate.query(sql, params, rowMapper));
    }

    public static <T> Optional<T> queryForOptional(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql, SqlParameterSource params, Class<T> klass) {
        return optionalFromList(namedParameterJdbcTemplate.queryForList(sql, params, klass));
    }

    private static <T> Optional<T> optionalFromList(List<T> objects) {
        if (objects.isEmpty()) return Optional.empty();
        return Optional.ofNullable(objects.get(0));
    }

    public static Long insertReturningId(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql, SqlParameterSource params) {
        return insertReturning(namedParameterJdbcTemplate, sql, params, "id");
    }

    public static Long insertReturning(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql, SqlParameterSource params, String... idNames) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, idNames);
        Number key = keyHolder.getKey();

        return key == null ? null : key.longValue();
    }

    public static Timestamp sqlSetTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return Timestamp.valueOf(dateTime);
    }

    public static Date sqlSetDate(LocalDate date) {
        if (date == null) return null;
        return Date.valueOf(date);
    }

    public static LocalDateTime sqlGetLocalDateTime(ResultSet rs, String name) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(name);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public static LocalDateTime sqlGetLocalDateTime(ResultSet rs, int column) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(column);
        return optionalLocalDateTime(timestamp);
    }

    public static LocalDateTime optionalLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public static LocalDate sqlGetLocalDate(ResultSet rs, String name) throws SQLException {
        Date date = rs.getDate(name);
        return date == null ? null : date.toLocalDate();
    }

    public static LocalDate sqlGetLocalDate(ResultSet rs, int column) throws SQLException {
        Date date = rs.getDate(column);
        return date == null ? null : date.toLocalDate();
    }

    public static String sqlSetBoolean(boolean value) {
        return value ? "y" : "n";
    }

    public static Boolean sqlGetBoolean(ResultSet rs, String name) throws SQLException {
        String stringValue = rs.getString(name);
        return stringValue == null ? null : stringValue.equalsIgnoreCase("y");
    }

    public static Boolean sqlGetBoolean(ResultSet rs, int column) throws SQLException {
        String stringValue = rs.getString(column);
        return stringValue == null ? null : stringValue.equalsIgnoreCase("y");
    }

    public static boolean attributeHasScalarValue(Map.Entry<String, Object> e) {
        Object value = e.getValue();
        if (value == null) {
            return false;
        }
        Class<?> valueClass = value.getClass();
        return !Collection.class.isAssignableFrom(valueClass) && !Map.class.isAssignableFrom(valueClass);
    }

    public static final Function<String, String> stringValue = (s) -> "".equals(s) ? null : s;

    public static Long sqlGetLong(ResultSet rs, int column) throws SQLException {
        Long value = rs.getLong(column);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }

    public static Long sqlGetLong(ResultSet rs, String columnName) throws SQLException {
        Long value = rs.getLong(columnName);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }
}
