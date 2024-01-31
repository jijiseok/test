package firsttest;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Windept2 extends JFrame {

	JTextField tf1 = new JTextField(5);
	JTextField tf2 = new JTextField(5);
	JTextField tf3 = new JTextField(5);
	JTextField tf4 = new JTextField(5);
	JTextField tf5 = new JTextField(5);
	JTextField tf6 = new JTextField(5);
	JTextField tf7 = new JTextField(5);
	JTextField tf8 = new JTextField(5);

	JButton bt1 = new JButton("전체 내용");
	JButton bt2 = new JButton("입력");
	JButton bt3 = new JButton("이름 검색");
	JButton bt4 = new JButton("수정");
	JButton bt5 = new JButton("삭제");

	JTextArea ta = new JTextArea(10, 40);
	
	
	Connection conn;
	Statement stmt;

	public Windept2() {
		String url = "jdbc:mysql://localhost:3306/firm";
		String id = "root";
		String pass = "mysql";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel lb1 = new JLabel("empno:");
		JLabel lb2 = new JLabel("ename:");
		JLabel lb3 = new JLabel("job:");
		JLabel lb4 = new JLabel("mgr:");
		JLabel lb5 = new JLabel("hiredate:");
		JLabel lb6 = new JLabel("sal:");
		JLabel lb7 = new JLabel("comm:");
		JLabel lb8 = new JLabel("deptno:");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel(new FlowLayout()); // FlowLayout 왼쪽부터 채우기
		jp1.add(bt1);
		jp1.add(bt2);
		jp1.add(bt3);
		jp1.add(bt4);
		jp1.add(bt5);

		con.add(jp1, BorderLayout.SOUTH); // 남쪽에 두기
		JScrollPane scroll = new JScrollPane(ta); // ta에 스크롤바 만들기
		JPanel jp2 = new JPanel(new FlowLayout()); // 판넬을 만들기?
		jp2.add(scroll); // 판넬에 붙이기
		con.add(jp2, BorderLayout.CENTER);

		JPanel jp3 = new JPanel(new FlowLayout());
		con.add(jp3, BorderLayout.NORTH);
		jp3.add(lb1);
		jp3.add(tf1);
		jp3.add(lb2);
		jp3.add(tf2);
		jp3.add(lb3);
		jp3.add(tf3);
		jp3.add(lb4);
		jp3.add(tf4);
		jp3.add(lb5);
		jp3.add(tf5);
		jp3.add(lb6);
		jp3.add(tf6);
		jp3.add(lb7);
		jp3.add(tf7);
		jp3.add(lb8);
		jp3.add(tf8);

		this.setTitle("Emp 관리");
		this.setLocation(500, 400);
		this.setSize(1000, 500);
		this.setVisible(true);

		bt1.addActionListener(new ActionListener() { // 익명의 객체생성

			@Override
			public void actionPerformed(ActionEvent e) {
				select();

			}
		});

		bt2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
				clearTextField();
				select();
			}
		});

		bt3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		bt4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		
		bt5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				delete();
			
				
				
			}
		});

	}

	private void clearTextField() {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
	}

	public void select() {
		String sql = "select empno, ename, job,mgr,hiredate,sal,comm,deptno from emp";// "select *from Emp";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ta.setText("");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");

				String str = String.format("%d,%s,%s,%d,%s,%f,%f,%d\n", empno, ename, job, mgr, hiredate, sal, comm,
						deptno);
				// format이 문자열을 하나의 스트링으로 만들어서 str로 보낸다

				ta.append(str);// ta에다가 추가
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				tf5.setText("");
				tf6.setText("");
				tf7.setText("");
				tf8.setText("");

			}
		}
//			} else {
//				tf1.setText("");
//				tf2.setText("");
//				tf3.setText("");
//				ta.append("해당자료 없습니다.");
//			}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insert() {
		String sql = String.format("insert into emp values ('%s','%s','%s','%s','%s','%s','%s','%s')", tf1.getText(),
				tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), tf6.getText(), tf7.getText(),
				tf8.getText());
		try {
			
			int res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void search() {
		String sql = ("select * from emp where ename = '" + tf2.getText()+"'" );
		
		try {
			ResultSet rs = stmt.executeQuery(sql);
			
			ta.setText("");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				
				String str = String.format("%d,%s,%s,%d,%s,%f,%f,%d\n", empno, ename, job, mgr, hiredate, sal, comm,
																												deptno);
			
				ta.append(str);
				tf1.setText(empno+ "");
				tf2.setText(ename);
				tf3.setText(job);
				tf4.setText(mgr+ "");
				tf5.setText(hiredate);
				tf6.setText(sal+ "");
				tf7.setText(comm+ "");
				tf8.setText(deptno+ "");

			}
		}


		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update() {

		String sql = String.format(
				"update emp set ename='"+tf2.getText()+"',job='"+tf3.getText()+"',mgr='"+tf4.getText()
				+"',hiredate='"+tf5.getText()+"',sal='"+tf6.getText()+"',comm='"+tf7.getText()+
				"',deptno='"+tf8.getText()+"' where empno ="+tf1.getText()+" ");
		
		
		try {
			
			int res = stmt.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public void delete() {

		String sql = String.format( "delete from emp where empno = " +  tf1.getText());
		
		try {
			
			int res = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Windept2();
	}
}
