package _healthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import dbutil.MySqlConnectionProvider;

public class MessageBoard extends JFrame {
	private DefaultTableModel tableModel;
	private JTable table;
	private String loginId;
	private JLabel contentLabel;
	private JTextArea contentTextArea;
	private Font customFont;
	private int newRowNumber;

	public MessageBoard(String loginId) {
		this.loginId = loginId;
		setResizable(false); // 창 크기 고정
		setTitle("게시판");
		setSize(934, 534);
		setLocationRelativeTo(null); // 화면 중앙에 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("로그인한 ID:" + loginId);
		
		customFont = new Font("휴먼편지체", Font.PLAIN, 15);
		
		initializeTable();
		addComponents();
		loadMessage();
	}

	private void initializeTable() { // 테이블 설정 메서드 테이블은 게시물 등록되는
		tableModel = new DefaultTableModel(new Object[] { "", "아이디", "내용", "날짜", "좋아요", "" }, 0) {
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 5) {
					return Boolean.class;
				} else {
					return super.getColumnClass(columnIndex);
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5;
			}
		};

		table = new JTable(tableModel);
		table.setRowHeight(50); // 테이블 셀 크기 !!!!!!!!!!!!!!!!!!!!!!!!
		table.setFont(customFont); // 원하는 폰트 설정

		for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(customFont));
        }
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		ImageIcon unselectedIcon = new ImageIcon("src/image/like2.png"); // 여기인거같아~~~~
		ImageIcon selectedIcon = new ImageIcon("src/image/liked2.png"); // 여기인거같아~~~~
		table.getColumnModel().getColumn(5).setCellRenderer(new ToggleButtonRenderer(selectedIcon));
		table.getColumnModel().getColumn(5).setCellEditor(new ToggleButtonEditor());
		
		
	}
	
	class CustomCellRenderer extends DefaultTableCellRenderer {
	    private Font font;

	    public CustomCellRenderer(Font font) {
	        this.font = font;
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        cellComponent.setFont(font); // 폰트 설정
	        return cellComponent;
	    }
	}
	private void addComponents() {
		getContentPane().setLayout(null);

		JButton btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(MessageBoard.class.getResource("/image/뒤로가기.png")));
		btnBack.setBounds(5, 13, 39, 23);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Main(loginId);
			}
		});

		JLabel lblNewLabel_2 = new JLabel("게시판");
		lblNewLabel_2.setFont(new Font("휴먼편지체", Font.BOLD, 25));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(56, 9, 73, 30);
		getContentPane().add(lblNewLabel_2);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 46, 931, 436);
		getContentPane().add(scrollPane);

		JButton addButton = new JButton("");
		addButton.setIcon(new ImageIcon(MessageBoard.class.getResource("/image/게시판글쓰기.png")));
		addButton.setBounds(0, 482, 931, 25);
		addButton.setContentAreaFilled(false);
		addButton.setBorderPainted(false);
		addButton.setFocusPainted(false);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 글쓰기 다이얼로그 열기
				openWriteDialog();
			}
		});

		getContentPane().add(addButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MessageBoard.class.getResource("/image/큰초록바.png")));
		lblNewLabel.setBounds(0, 0, 824, 47);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MessageBoard.class.getResource("/image/큰초록바.png")));
		lblNewLabel_1.setBounds(823, 0, 108, 47);
		getContentPane().add(lblNewLabel_1);
	}

	private void openWriteDialog() {
		JDialog dialog = new JDialog(this, "글쓰기", true);
		dialog.setSize(400, 200);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(this);

		Color customColor = new Color(255, 250, 215);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(customColor); // RGB 값을 사용하여 배경색 설정
		Font labelFont = new Font("휴먼편지체", Font.PLAIN, 16); // 적절한 폰트와 크기로 설정
		contentLabel = new JLabel(" 내용: (255자까지 입력 가능합니다)");
		contentLabel.setFont(labelFont); // 폰트 설정
		contentLabel.setPreferredSize(new Dimension(contentLabel.getPreferredSize().width, 40)); // 세로 크기 조절
		Font labelFont2 = new Font("휴먼편지체", Font.PLAIN, 16);
		contentTextArea = new JTextArea();
		contentTextArea.setFont(labelFont2); // 폰트 설정
		contentTextArea.setLineWrap(true); // 줄 바꿈 활성화
		contentTextArea.setWrapStyleWord(true); // 단어 단위 줄 바꿈 활성화
		JScrollPane scrollPane = new JScrollPane(contentTextArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 수직 스크롤 자동 표시

		JButton submitButton = new JButton("등록");
		Dimension buttonSize = new Dimension(400, 25);
		submitButton.setPreferredSize(buttonSize);
		submitButton.setContentAreaFilled(false);
		ImageIcon icon = new ImageIcon(getClass().getResource("/image/글쓰기등록.png"));
		submitButton.setIcon(icon);
		submitButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String content = contentTextArea.getText();
		        if (!content.isEmpty()) {
		            addMessageToTable(content);
		            dialog.dispose();
		        } else {
		            JOptionPane.showMessageDialog(dialog, "내용을 입력하세요.", "알림", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});

		panel.add(contentLabel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(submitButton, BorderLayout.SOUTH);

		dialog.getContentPane().add(panel);
		dialog.setVisible(true);
	}

	private int addMessageToTable(String content) {
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    String id = loginId;

	    if (id != null && !id.isEmpty() && content != null && !content.isEmpty()) {
	        newRowNumber = tableModel.getRowCount() + 1; // 새로운 행 번호 설정
	        
	        Object[] rowData = { newRowNumber, id, content, currentDateTime, 0, false }; // 새로운 행 데이터 생성
	        tableModel.insertRow(0, rowData);
	        return newRowNumber; // newRowNumber 값을 반환
	    } else {
	        JOptionPane.showMessageDialog(this, "로그인 정보 또는 내용이 올바르지 않습니다.", "에러", JOptionPane.ERROR_MESSAGE);
	        return -1; // 유효하지 않은 newRowNumber 반환
	    }
	}



	// 데이터베이스에서 게시글과 좋아요 버튼 상태 불러오기
	private void loadMessage() {
		try (Connection connection = MySqlConnectionProvider.getConnection()) {
			String sql = "SELECT messageboard.*, user_likes.is_liked " + 
		             "FROM messageboard " + 
		             "LEFT JOIN user_likes ON messageboard.content = user_likes.user_content AND user_likes.user_id = ? " +
		             "ORDER BY messageboard.date DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int num = resultSet.getInt("num");
				String id = resultSet.getString("user_id");
				String content = resultSet.getString("content");
				String date = resultSet.getString("date");
				int likes = resultSet.getInt("likes");
				boolean isLiked = resultSet.getBoolean("is_liked");
				// 날짜 정보 등을 가져와서 테이블에 추가하는 작업
				Object[] rowData = { num, id, content, date, likes, isLiked };
				tableModel.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 글쓰기
	private void addMessage() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		String id = loginId; // 로그인한 아이디
		String content = contentTextArea.getText();
		int newRowNumber = addMessageToTable(content);
		if (id != null && content != null && !id.isEmpty() && !content.isEmpty()) {
			Object[] rowData = { newRowNumber, id, content, currentDateTime, 0, false };
			tableModel.addRow(rowData);
			try (Connection connection = MySqlConnectionProvider.getConnection()) {
				String sql = "INSERT INTO messageboard (user_id, num, content, date) VALUES ((SELECT id FROM users WHERE id = ?), ?, ?, NOW())";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);
				preparedStatement.setInt(2, newRowNumber);
				preparedStatement.setString(3, content);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private class ToggleButtonEditor extends DefaultCellEditor implements TableCellEditor {
		private JToggleButton button;

		private int row; // 현재 편집 중인 행의 인덱스를 저장하기 위한 변수

		public ToggleButtonEditor() {
			super(new JCheckBox());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row; // 현재 편집 중인 행의 인덱스 저장
			ImageIcon selectedIcon = new ImageIcon("src/image/liked2.png");

			button = new JToggleButton(selectedIcon);

			button.setSelected((Boolean) value);
			button.setFocusPainted(false); // 배경 투명하게 설정
			button.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
			button.setBorderPainted(false); // 테두리 제거
			button.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (button.isSelected()) {
						button.setIcon(selectedIcon);
						String content = (String) tableModel.getValueAt(row, 2);
						saveButton(loginId, content, true); // 버튼 상태 저장
						System.out.println("선택된 " + row);
					} else {
//                      button.setIcon(unselectedIcon);
						String content = (String) tableModel.getValueAt(row, 2);
						saveButton(loginId, content, false); // 버튼 상태 저장
						System.out.println("선택된 " + row);
					}
					fireEditingStopped(); // 셀 편집 종료
				}
			});
			return button;
		}

		// 좋아요 토글버튼 좋아요 횟수
		@Override
		public Object getCellEditorValue() {
			String content = (String) tableModel.getValueAt(row, 2);
			int likes = (int) tableModel.getValueAt(row, 4);
			if (button.isSelected()) {
				tableModel.setValueAt(likes + 1, row, 4); // 좋아요 숫자 증가
				updateLikes(content, likes + 1);
			} else {
				if (likes > 0) {
					tableModel.setValueAt(likes - 1, row, 4);
					updateLikes(content, likes - 1);
				}
			}
			return button.isSelected();
		}
	}

	// 좋아요 숫자 해당 행이 저장
	private void updateLikes(String content, int likes) {
		try (Connection conn = MySqlConnectionProvider.getConnection()) {
			String sql = "UPDATE messageboard SET likes = ? WHERE content = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, likes);
			preparedStatement.setString(2, content);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 좋아요 버튼 누른 아이디 버튼 상태 저장
	private void saveButton(String userId, String content, boolean isLiked) {
		try (Connection conn = MySqlConnectionProvider.getConnection()) {
			if (!checkExists(conn, userId, content)) {
				String insertSql = "INSERT INTO user_likes (user_id, user_content, is_liked) VALUES (?, ?, ?)";
				PreparedStatement insertStatement = conn.prepareStatement(insertSql);
				insertStatement.setString(1, userId);
				insertStatement.setString(2, content);
				insertStatement.setBoolean(3, isLiked);
				insertStatement.executeUpdate();
			}

			String sql = "UPDATE user_likes SET is_liked = ? WHERE user_id = ? AND user_content = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setBoolean(1, isLiked);
			preparedStatement.setString(2, userId);
			preparedStatement.setString(3, content);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 좋아요 누를 아이디 체크
	private boolean checkExists(Connection conn, String userId, String content) throws SQLException {
		String sql = "SELECT COUNT(*) FROM user_likes WHERE user_id = ? AND user_content = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, userId);
		preparedStatement.setString(2, content);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		int count = resultSet.getInt(1);
		return count > 0;
	}

	private class ToggleButtonRenderer extends JToggleButton implements TableCellRenderer {
		private AbstractButton button;

		public ToggleButtonRenderer(ImageIcon selectedIcon) {
			super();
			ImageIcon unselectedIcon = new ImageIcon("src/image/like2.png");
			setIcon(unselectedIcon);
			setSelectedIcon(selectedIcon);

			setOpaque(false); // 배경 투명화
			setBorderPainted(false); // 테두리 제거
			setContentAreaFilled(false); // 내용 영역 채우기 제거
			setFocusPainted(false); // 포커스 표시 제거
			setHorizontalAlignment(SwingConstants.CENTER);
			setBorderPainted(false);

		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setSelected((Boolean) value);
			return this;
		}
	}
}