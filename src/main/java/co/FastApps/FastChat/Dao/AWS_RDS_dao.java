package co.FastApps.FastChat.Dao;

import co.FastApps.FastChat.Entity.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository("mysql")
public class AWS_RDS_dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    private static class ResultRowMapper implements RowMapper<ResultType> {
//
//        @Override
//        public ResultType mapRow(ResultSet resultSet, int i) throws SQLException {
//            ResultType resultType = new ResultType();
//            resultType.setId(resultSet.getInt("id"));
//            resultType.setType(resultSet.getString("Type"));
//            resultType.setText(resultSet.getString("Text"));
//            resultType.setPhone(resultSet.getString("Phone"));
//            resultType.setEmail(resultSet.getString("Email"));
//            resultType.setCompany(resultSet.getString("Company"));
//            return resultType;
//        }
//    }


//    public List<ResultType> getAll(){
//        final String sql = "SELECT * FROM Customers";
//        List<ResultType> result = jdbcTemplate.query(sql, new ResultRowMapper());
//        return result;
//    }

    public List<ResultType> getCompanies(String name) {
        final String sql = "SELECT * FROM Companies WHERE Name = '" + name + "'";
        final List<ResultType> result = jdbcTemplate.query(sql, new ResultSetExtractor<List<ResultType>>() {
            @Override
            public List<ResultType> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<ResultType> tmp = new ArrayList<>();
                while(resultSet.next()) {
                    ResultType resultType = new ResultType();
                    resultType.setId(resultSet.getInt("id"));
                    resultType.setName(resultSet.getString("Name"));
                    resultType.setDateFounded(resultSet.getString("date-Founded"));
                    tmp.add(resultType);
                    System.out.println(tmp);
                }
                return tmp;
            }
        });
        return result;
    }

    //need to care for sql injection attacks
    public List<ResultType> getContact(String name) {
        final String sql = "SELECT * FROM Contacts WHERE City = 'toronto'";
        List<ResultType> result = jdbcTemplate.query(sql, new ResultSetExtractor<List<ResultType>>() {
            @Override
            public List<ResultType> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<ResultType> tmp = new ArrayList<>();
                while (resultSet.next()){
                    ResultType resultType = new ResultType();
                    resultType.setId(resultSet.getInt("id"));
                    resultType.setName(resultSet.getString("Name"));
                    resultType.setCompany(resultSet.getString("Company"));
                    resultType.setPhone(resultSet.getString("Phone-number"));
                    resultType.setEmail(resultSet.getString("Email"));
                    resultType.setCity(resultSet.getString("City"));
                    tmp.add(resultType);
                    System.out.println(tmp);
                }
                return tmp;
            }
        });
        return result;
    }
}
