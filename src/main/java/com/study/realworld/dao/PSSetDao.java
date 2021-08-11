package com.study.realworld.dao;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PSSetDao {
    public static class PSSForInts implements PreparedStatementSetter {
        private Integer[] value;

        public PSSForInts(Integer... value) {
            this.value = value;
        }

        public void setValues(PreparedStatement ps) throws SQLException {

            for (int i = 0; i < value.length; i++) {
                ps.setInt((i + 1), this.value[i]);
            }
        }
    }

    public static class PSSForStrings implements PreparedStatementSetter {
        private String[] value;

        public PSSForStrings(String... value) {
            this.value = value;
        }

        public void setValues(PreparedStatement ps) throws SQLException {

            for (int i = 0; i < value.length; i++) {
                ps.setString((i + 1), this.value[i]);
            }
        }
    }

    public static class PSSForIntsStrings implements PreparedStatementSetter {
        int intCount;
        int stringCount;
        Object[] objects;

        public PSSForIntsStrings(int intCount, int stringCount, Object... objects) {
            this.intCount = intCount;
            this.stringCount = stringCount;
            this.objects = objects;
        }

        public void setValues(PreparedStatement ps) throws SQLException {
            int pos = 1;

            for (int i = 0; i < intCount; i++) ps.setInt(pos++, (Integer) objects[i]);
            for (int i = 0; i < stringCount; i++) ps.setString(pos++, (String) objects[i + intCount]);
        }
    }

    public static class PSSForStringsInts implements PreparedStatementSetter {
        int stringCount;
        int intCount;
        Object[] objects;

        public PSSForStringsInts(int stringCount, int intCount, Object... objects) {
            this.stringCount = stringCount;
            this.intCount = intCount;
            this.objects = objects;
        }

        public void setValues(PreparedStatement ps) throws SQLException {
            int pos = 1;

            for (int i = 0; i < stringCount; i++) ps.setString(pos++, (String) objects[i]);
            for (int i = 0; i < intCount; i++) ps.setInt(pos++, (Integer) objects[i + stringCount]);
        }
    }
}
