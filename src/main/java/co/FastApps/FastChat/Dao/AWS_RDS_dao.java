package co.FastApps.FastChat.Dao;

import co.FastApps.FastChat.Entity.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository("mysql")
public class AWS_RDS_dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ResultType> getCompanies(String Type, String name) {
        final String sql = "SELECT * FROM Companies WHERE " + Type + " = + ?";
        final List<ResultType> result = jdbcTemplate.query(sql, new ResultSetExtractor<List<ResultType>>() {
            @Override
            public List<ResultType> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<ResultType> tmp = new ArrayList<>();
                while(resultSet.next()) {
                    ResultType resultType = new ResultType();
                    resultType.setId(resultSet.getInt("id"));
                    resultType.setName(resultSet.getString("Organization"));
                    resultType.setDateFounded(resultSet.getString("date-Founded"));
                    resultType.setCity(resultSet.getString("Location"));
                    tmp.add(resultType);
                    System.out.println(tmp);
                }
                return tmp;
            }
        },name);
        return result;
    }
    public List<ResultType> getContact(String name) {
        final String sql = "SELECT * FROM Contacts WHERE Location = ?";
        List<ResultType> result = jdbcTemplate.query(sql, new ResultSetExtractor<List<ResultType>>() {
            @Override
            public List<ResultType> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<ResultType> tmp = new ArrayList<>();
                while (resultSet.next()){
                    ResultType resultType = new ResultType();
                    resultType.setId(resultSet.getInt("id"));
                    resultType.setName(resultSet.getString("Name"));
                    resultType.setCompany(resultSet.getString("Organization"));
                    resultType.setPhone(resultSet.getString("Phone"));
                    resultType.setEmail(resultSet.getString("Email"));
                    resultType.setCity(resultSet.getString("Location"));
                    tmp.add(resultType);
                    System.out.println(tmp);
                }
                return tmp;
            }
        },name);
        return result;
    }

}
