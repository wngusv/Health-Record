package _healthcare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import dbutil.MySqlConnectionProvider;
import javafx.scene.paint.Color;

public class MessageBoard extends JFrame {
   private DefaultTableModel tableModel;
   private JTable table;
   private String loginId;

   public MessageBoard(String loginId) {
      this.loginId = loginId;
      setTitle("게시판");
      setSize(947, 400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      System.out.println("로그인한 ID:" + loginId);
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
      table.getColumnModel().getColumn(0).setPreferredWidth(50);
      table.getColumnModel().getColumn(1).setPreferredWidth(100);
      table.getColumnModel().getColumn(2).setPreferredWidth(300);
      table.getColumnModel().getColumn(3).setPreferredWidth(100);
      table.getColumnModel().getColumn(4).setPreferredWidth(100);
      table.getColumnModel().getColumn(5).setPreferredWidth(100);
      ImageIcon unselectedIcon = new ImageIcon("src/image/like2.png"); // 여기인거같아~~~~
      ImageIcon selectedIcon = new ImageIcon("src/image/liked2.png"); // 여기인거같아~~~~
      table.getColumnModel().getColumn(5).setCellRenderer(new ToggleButtonRenderer(selectedIcon));
      table.getColumnModel().getColumn(5).setCellEditor(new ToggleButtonEditor());
   }

   private void addComponents() { // 프레임에 컴포넌트 추가 글쓰기 등록
      JScrollPane scrollPane = new JScrollPane(table);
      getContentPane().add(scrollPane, BorderLayout.CENTER);

      JButton addButton = new JButton("글쓰기");
      addButton.setContentAreaFilled(false);
      addButton.setBorderPainted(false);
      addButton.setFocusPainted(false);

      addButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            addMessage();
         }
      });

      getContentPane().add(addButton, BorderLayout.SOUTH);
   }

   private void loadMessage() { // 데이터베이스에서 게시글과 좋아요 버튼 상태 불러오기
      try (Connection connection = MySqlConnectionProvider.getConnection()) {
         String sql = "SELECT messageboard.*, user_likes.is_liked " +
                    "FROM messageboard " +
                    "LEFT JOIN user_likes " +
                    "ON messageboard.content = user_likes.user_content AND user_likes.user_id = ? ORDER BY messageboard.date DESC";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setString(1, loginId);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            String id = resultSet.getString("user_id");
            String content = resultSet.getString("content");
            String date = resultSet.getString("date");
            int likes = resultSet.getInt("likes");
            boolean isLiked = resultSet.getBoolean("is_liked");
            // 날짜 정보 등을 가져와서 테이블에 추가하는 작업
            Object[] rowData = { tableModel.getRowCount() + 1, id, content, date, likes, isLiked };
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
      String content = JOptionPane.showInputDialog(this, "Enter Content:");
      if (id != null && content != null && !id.isEmpty() && !content.isEmpty()) {
         Object[] rowData = { tableModel.getRowCount() + 1, id, content, currentDateTime, 0, false };
         tableModel.addRow(rowData);
         try (Connection connection = MySqlConnectionProvider.getConnection()) {
            String sql = "INSERT INTO messageboard (user_id, content, date) VALUES ((SELECT id FROM users WHERE id = ?), ?, NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, content);
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
           /*button.setFocusPainted(false); // 배경 투명하게 설정
           button.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
           button.setBorderPainted(false); // 테두리 제거*/
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

   private boolean checkExists(Connection conn, String userId, String content) throws SQLException { //좋아요 누를 아이디 체크
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
      public ToggleButtonRenderer(ImageIcon selectedIcon) {
    	  super();
    	  ImageIcon unselectedIcon = new ImageIcon("src/image/like2.png");
    	  setIcon(unselectedIcon);
    	  setSelectedIcon(selectedIcon);
    	  
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