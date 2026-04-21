import mysql.connector

# 连接到 MySQL 数据库
try:
    conn = mysql.connector.connect(
        host="localhost",
        user="root",
        password="123456"
    )
    cursor = conn.cursor()
    
    # 显示所有数据库
    print("Databases:")
    cursor.execute("SHOW DATABASES;")
    for db in cursor.fetchall():
        print(f"- {db[0]}")
    
    # 检查 secondhand_book 数据库是否存在
    cursor.execute("SHOW DATABASES LIKE 'secondhand_book';")
    if cursor.fetchone():
        print("\nsecondhand_book database exists.")
        
        # 使用 secondhand_book 数据库
        cursor.execute("USE secondhand_book;")
        
        # 显示所有表
        print("\nTables in secondhand_book database:")
        cursor.execute("SHOW TABLES;")
        for table in cursor.fetchall():
            print(f"- {table[0]}")
        
        # 检查 books 表是否存在
        cursor.execute("SHOW TABLES LIKE 'books';")
        if cursor.fetchone():
            print("\nbooks table exists.")
            
            # 显示 books 表的结构
            print("\nbooks table structure:")
            cursor.execute("DESCRIBE books;")
            for column in cursor.fetchall():
                print(f"- {column[0]}: {column[1]} {column[2]} {column[3]} {column[4]} {column[5]}")
        else:
            print("\nbooks table does not exist.")
    else:
        print("\nsecondhand_book database does not exist.")
        
    # 关闭连接
    cursor.close()
    conn.close()
    
except Exception as e:
    print(f"Error: {e}")
