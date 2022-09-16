package com.afauria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.afauria.DBHelper;
import com.afauria.entity.ClassScoreCount;
import com.afauria.entity.Student;

public class StudentDao {
	public List<Student> getStudents() {
		List<Student> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		try {
			// 这里只计算语数英，写太多没什么用
			PreparedStatement pst = conn.prepareStatement(
					"select *,(chinese+math+english) as total_score, (chinese+math+english) / 3 as avg_score from tb_stud order by clazz ASC, stud_num ASC");
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				Student stud = new Student();
				stud.setId(result.getInt("stud_id"));
				stud.setStudNum(result.getInt("stud_num"));
				stud.setName(result.getString("name"));
				stud.setClazz(result.getString("clazz"));
				stud.setGender(result.getInt("gender"));
				stud.setChinese(result.getInt("chinese"));
				stud.setMath(result.getInt("math"));
				stud.setEnglish(result.getInt("english"));
				stud.setPolitics(result.getInt("politics"));
				stud.setHistory(result.getInt("history"));
				stud.setGeography(result.getInt("geography"));
				stud.setPhysics(result.getInt("physics"));
				stud.setChemistry(result.getInt("chemistry"));
				stud.setBiology(result.getInt("biology"));
				stud.setTotalScore(result.getInt("total_score"));
				stud.setAvgScore(result.getDouble("avg_score"));
				list.add(stud);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getAllClazz() {
		List<String> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		try {
			PreparedStatement pst = conn.prepareStatement("select clazz from tb_stud group by clazz");
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				list.add(result.getString("clazz"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ClassScoreCount> getCountByCourse(String course) {
		List<ClassScoreCount> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		try {
			// prepareStatement中不能动态设置表名、列名，需要拼接字符串，否则会添加自动引号为'chinese'，导致查询结果错误
			PreparedStatement pst = conn.prepareStatement("select clazz, count(case when " + course
					+ " between 90 and 100 then 1 end) as m90, count(case when " + course
					+ " between 80 and  89 then 1 end) as m80, count(case when " + course
					+ " between 60 and  79 then 1 end) as m60, count(case when " + course
					+ " between 0 and  59 then 1 end) as m0, count(*) as total from tb_stud group by clazz");
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				ClassScoreCount c = new ClassScoreCount();
				c.setClazz(result.getString("clazz"));
				c.setM90(result.getInt("m90"));
				c.setM80(result.getInt("m80"));
				c.setM60(result.getInt("m60"));
				c.setM0(result.getInt("m0"));
				c.setTotal(result.getInt("total"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void updateScore(int id, Object v, String course) {
		Connection conn = DBHelper.getConnection();
		try {
			// prepareStatement中不能动态设置表名、列名，需要拼接字符串
			PreparedStatement pst = conn.prepareStatement("update tb_stud set " + course + "=? where stud_id=?");
			pst.setObject(1, v);
			pst.setInt(2, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
