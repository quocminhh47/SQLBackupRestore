# SQLBackupRestore - 
#In use : Java web - JSP - JDBC
provide backup - restore database solution, this include restore database from unbackup-point
Problem / Requirement:
Thiết kế project cho SystemAdmin để có thể backup / restore 1 cơ sở dữ liệu bất kỳ trên Server về  1 thời điểm chưa sao lưu. 
- Khi chọn phục hồi về 1 thời điểm chưa sao lưu, chương trình tự động lấy bản sao lưu full mới nhất làm mốc + Transactional log để phục hồi
- Cho phép tạo/ drop backup device / delete file log
