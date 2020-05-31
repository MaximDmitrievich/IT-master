package highlevellangs.code.hw.repos.simple.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import highlevellangs.code.hw.repos.simple.entity.Project;

public class ProjectPostgresRepository implements Repository<Project> {
    String url = "";
    String sql_query;

    @Override
    public Iterable<Project> findAll(){
        List<Project> projects = new ArrayList<Project>();
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            sql_query  = "select * from Projects";
            ResultSet rs = statement.executeQuery(sql_query);
            while (rs.next()){
                projects.add(new Project(rs.getInt("proj_id"),
                        rs.getString("proj_name"),
                        rs.getString("proj_duration"),
                        rs.getString("proj_date_start"),
                        rs.getString("proj_date_end"))
                );
            }
            conn.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return projects;
    }

    @Override
    public void save(Project entries) {
        if (entries.getProjectId() < 0) {
            insert(entries);
        } else {
            update(entries);
        }
    }

    @Override
    public Project findById(int id) {
        Project projects;
        try {
            sql_query = "select * from Projects where proj_id = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql_query);
            pstmt.setInt(1, id);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                projects = new Project(rs.getInt("proj_id"), rs.getString("proj_name"), rs.getString("proj_duration"), rs.getString("proj_date_start"), rs.getString("proj_date_end"));
            } else {
                projects = null;
            }
            conn.close();
        } catch (SQLException e){
            throw new IllegalStateException(e);
        }
        return projects;
    }

    @Override
    public void delete(Project entries) {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
            sql_query = "delete from Projects where proj_id = ?";
            final PreparedStatement pstmt = conn.prepareStatement(sql_query);
            pstmt.setInt(1, entries.getProjectId());
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(Project proj){
        try {
            Connection conn = DriverManager.getConnection(url);
            sql_query = "update Projects set proj_name = ?, proj_duration = ?, proj_date_start = ?, proj_date_end = ? where proj_id = ?";
            final PreparedStatement pstmt = conn.prepareStatement(sql_query);
            pstmt.setString(1, proj.getProjectName());
            pstmt.setString(2, proj.getProjectDuration());
            pstmt.setString(1, proj.getProjectBegin());
            pstmt.setString(1, proj.getProjectDone());
            pstmt.setInt(1, proj.getProjectId());
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(Project proj){
        try {
            Connection conn = DriverManager.getConnection(url);
            sql_query = "insert into Projects(proj_name, proj_duration, proj_date_start, proj_date_end) values(?,?,?,?);";
            final PreparedStatement pstmt = conn.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, proj.getProjectName());
            pstmt.setString(2, proj.getProjectDuration());
            pstmt.setString(3, proj.getProjectBegin());
            pstmt.setString(4, proj.getProjectDone());
            pstmt.executeUpdate();
            try (ResultSet genKeys = pstmt.getGeneratedKeys()) {
                if (genKeys.next()){
                    proj.setProjectId(genKeys.getInt(1));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}