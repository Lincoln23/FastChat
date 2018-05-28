package co.FastApps.FastChat.Dao;

import co.FastApps.FastChat.Entity.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository("mysql")
public class AWS_RDS_dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class ResultRowMapper implements RowMapper<ResultType> {

        @Override
        public ResultType mapRow(ResultSet resultSet, int i) throws SQLException {
            ResultType resultType = new ResultType();
            resultType.setId(resultSet.getInt("id"));
            resultType.setType(resultSet.getString("Type"));
            resultType.setText(resultSet.getString("Text"));
            resultType.setPhone(resultSet.getString("Phone"));
            resultType.setEmail(resultSet.getString("Email"));
            resultType.setCompany(resultSet.getString("Company"));
            return resultType;
        }
    }


    public List<ResultType> getAll(){
        final String sql = "SELECT * FROM Customers";
        List<ResultType> result = jdbcTemplate.query(sql, new ResultRowMapper());
        return result;
    }

    public ResultType getPerson(String name){
        final String sql = "SELECT * FROM Customers WHERE TEXT = ?";
        final ResultType result = jdbcTemplate.queryForObject(sql, new ResultRowMapper(), name);
        return result;
    }
}
