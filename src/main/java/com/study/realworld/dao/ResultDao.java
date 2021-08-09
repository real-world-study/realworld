package com.study.realworld.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ResultDao {
    public static class RSEForResult implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            ResultSetMetaData rsMeta;
            int z;
            Map<String, Object> row = null;

            if (rs.next() == true) {
                rsMeta = rs.getMetaData();

                row = new HashMap<String, Object>();

                rs.first();

                for (z = 1; z <= rsMeta.getColumnCount(); z++) {
                    row.put(rsMeta.getColumnLabel(z), rs.getObject(z));
                }
            }

            return row;
        }
    }

    @SuppressWarnings("unchecked")
    public static class RSEForResultSet implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            ResultSetMetaData rsMeta;
            int size;
            int i;
            int z;
            Map<String, Object>[] row = null;

            rsMeta = rs.getMetaData();

            rs.last();

            size = rs.getRow();

            if (size > 0) {
                row = new Map[size];

                rs.first();

                for (i = 0; i < size; i++) {
                    row[i] = new HashMap<String, Object>();

                    for (z = 1; z <= rsMeta.getColumnCount(); z++) {
                        row[i].put(rsMeta.getColumnLabel(z), rs.getObject(z));
                    }

                    rs.next();
                }
            }

            return row;
        }
    }

    public static class RSEForResultListSet implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            ResultSetMetaData rsMeta;
            int size;
            int i;
            int z;
            List<Map<String, Object>> list = null;

            rsMeta = rs.getMetaData();

            rs.last();

            size = rs.getRow();

            if (size > 0) {
                list = new ArrayList<Map<String, Object>>();

                rs.first();

                for (i = 0; i < size; i++) {
                    Map<String, Object> row = new HashMap<String, Object>();

                    for (z = 1; z <= rsMeta.getColumnCount(); z++) {
                        row.put(rsMeta.getColumnLabel(z), rs.getObject(z));
                    }
                    list.add(row);

                    rs.next();
                }
            }

            return list;
        }
    }

    public static class RSEForInt implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs.next() == true) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public static class RSEForString implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs.next() == true) {
                return rs.getString(1);
            } else {
                return null;
            }
        }
    }

    public static class RSEForBoolean implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs.next() == true) {
                return rs.getBoolean(1);
            } else {
                return false;
            }
        }
    }

    public static class RSEForIntList implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs.next() == true) {
                List<Integer> lst = new ArrayList<Integer>();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    lst.add(rs.getInt(i));
                }

                return lst;
            } else {
                return null;
            }
        }
    }


    public static class RSEForStringList implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs.next() == true) {
                List<String> lst = new ArrayList<String>();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    lst.add(rs.getString(i));
                }

                return lst;
            } else {
                return null;
            }
        }
    }

    public static class RSEForIntListFromRows implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            List<Integer> lst = new ArrayList<Integer>();

            while (rs.next()) {
                lst.add(rs.getInt(1));
            }

            return lst;
        }
    }
}
