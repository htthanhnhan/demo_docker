/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import model.dao.BookReceiptDB;
import model.dao.MessageDB;

/**
 *
 * @author ThanhNhan
 */
public class BookReceipt {

    private int bookReceiptID;
    private String userID;
    private String fullName;
    private int typeID;
    private String typeName;
    private String clas;
    private int bookID;
    private String bookName;
    private int bookCode;
    private int statusID;
    private String status;
    private Date borrowDate;
    private Date payDate;
    private String note;

    public BookReceipt() {
    }

    public BookReceipt(int bookReceiptID) {
        this(BookReceiptDB.getBookReceipt(bookReceiptID));
    }

    public BookReceipt(BookReceipt b) {
        this(b.bookReceiptID, b.userID, b.fullName, b.typeID, b.typeName, b.clas, b.bookID, b.bookName, b.bookCode, b.statusID, b.status, b.borrowDate, b.payDate, b.note);
    }

    public BookReceipt(String userID, int bookCode) {
        this.userID = userID;
        this.bookCode = bookCode;
    }

    public BookReceipt(int bookReceiptID, String userID, String fullName, int typeID, String typeName, String clas, int bookID, String bookName, int bookCode, int statusID, String status, Date borrowDate, Date payDate, String note) {
        this.bookReceiptID = bookReceiptID;
        this.userID = userID;
        this.fullName = fullName;
        this.typeID = typeID;
        this.typeName = typeName;
        this.clas = clas;
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookCode = bookCode;
        this.statusID = statusID;
        this.status = status;
        this.borrowDate = borrowDate;
        this.payDate = payDate;
        this.note = note;
    }
    
    public BookReceipt(int bookReceiptID, String userID, String fullName, int typeID, String typeName, String clas, int bookID, String bookName, int bookCode, int statusID, String status, Date borrowDate, Date payDate) {
        this.bookReceiptID = bookReceiptID;
        this.userID = userID;
        this.fullName = fullName;
        this.typeID = typeID;
        this.typeName = typeName;
        this.clas = clas;
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookCode = bookCode;
        this.statusID = statusID;
        this.status = status;
        this.borrowDate = borrowDate;
        this.payDate = payDate;
    }

    public int getBookReceiptID() {
        return bookReceiptID;
    }

    public void setBookReceiptID(int bookReceiptID) {
        this.bookReceiptID = bookReceiptID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookCode() {
        return bookCode;
    }

    public String getCode() {
        Books b = new Books(bookCode);
        return b.getCode();
    }

    public String getCodeSearch() {
        Books b = new Books(bookCode);
        return b.getCodeSearch();
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowDate() {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return f.format(borrowDate);
    }
    
    public Date bDate(){
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getPayDate() {
        if (payDate == null) {
            return "Chưa trả";
        }
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return f.format(payDate);
    }
    
    public Date pDate(){
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "BookReceipt{" + "bookReceiptID=" + bookReceiptID + ", userID=" + userID + ", fullName=" + fullName + ", typeID=" + typeID + ", typeName=" + typeName + ", clas=" + clas + ", bookID=" + bookID + ", bookName=" + bookName + ", bookCode=" + bookCode + ", statusID=" + statusID + ", status=" + status + ", borrowDate=" + borrowDate + ", payDate=" + payDate + '}';
    }

    public int add() {
        return BookReceiptDB.add(this);
    }

    public void back() {
        
        if((new java.util.Date().getTime() - borrowDate.getTime())/24/3600/1000 >=7) {
            BookReceiptDB.overDate(bookReceiptID);
        }
        BookReceiptDB.back(this);
        new Book(bookID).back(bookCode);
        MessageDB.addMessage(new Message(false, this.getUserID(), "<b>Sách bạn mượn:</b> " + this.getBookName() + " <br><b style='color: #1a4423'>ĐÃ TRẢ THÀNH CÔNG!</b><br>Vui lòng xem lại lịch sử mượn trả!"));
    }

    public void borrow(BookOrder b, String time) {
        new Book(bookID).borrow(bookCode);
        MessageDB.addMessage(new Message(false, b.getUserID(), "<b>Sách bạn mượn:</b> " + b.getBookName() + " <br><b style='color: blue'>ĐƯỢC ĐỒNG Ý!</b><br>Vui lòng ghé thư viện vào lúc: " + time + " để nhận sách!"));
        b.delete();
    }

    public static void refuse(BookOrder b, String reason) {
        MessageDB.addMessage(new Message(false, b.getUserID(), "<b>Sách bạn mượn:</b> " + b.getBookName() + " <br><b style='color: red'>BỊ TỪ CHỐI!</b><br><b>Lý do:</b> " + reason));
        b.delete();
    }
    
    public long getOverDate(){
        return (payDate.getTime()-borrowDate.getTime())/24/3600/1000 - 6;
    }
}
