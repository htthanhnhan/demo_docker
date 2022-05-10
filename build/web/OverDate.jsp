<%-- 
    Document   : ListBooks
    Created on : Nov 24, 2021, 9:23:25 PM
    Author     : ThanhNhan
--%>

<%@include file="./include/Header_admin.jsp" %>
<%@page contentType="text/html" errorPage="error.jsp" pageEncoding="UTF-8"%> 

<div class="container-fluid" style="margin-top: 20px">         
    <table class="table" style="font-size: 18px" cellspacing="3" cellpadding="3">
<!--        <colgroup>            
            <col width=200 span="1">
            <col width=auto span="1">
            <col width=auto span="1">
            <col width="200" span="1">
            <col width="230" span="1">
            <col width="auto" span="1">
            <col width="230" span="1">
            <col width="230" span="1">
            <col width="auto" span="1">
        </colgroup>-->
        <thead>
            <tr>
                <th scope="col">Họ và tên</th>
                <th scope="col">Lớp</th>                
                <th scope="col">Sách mượn</th>
                <th scope="col">Mã sách</th>
                <th scope="col">Ngày mượn</th>
                <th scope="col">Ngày trả</th>
                <th scope="col">Số ngày quá hạn</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="l">
                <tr>
                    <td>${l.fullName}</td>
                    <td style="text-align: center">${l.clas}</td>                    
                    <td>${l.bookName}</td>
                    <td>${l.code}</td>
                    <td>${l.borrowDate}</td>
                    <td id="pay${l.bookReceiptID}">${l.payDate}</td>
                    <td style="text-align: center">${l.overDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div> 

<%@include file="include/Footer_admin.jsp" %>