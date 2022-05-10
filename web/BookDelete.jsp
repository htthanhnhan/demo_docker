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
                    <col width=auto span="1">
                    <col width=300 span="1">
                    <col width="200" span="1">
                    <col width=auto span="1">
                    <col width=auto span="1">
                    <col width=auto span="1">            
                </colgroup>-->
        <thead>
            <tr>
                <th scope="col">STT</th>   
                <th scope="col">Tên sách</th>                                                                
                <th scope="col">Mã sách</th>  
                <th scope="col">Trạng thái</th>  
                <th scope="col">Ngày thanh lý</th> 
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="l" varStatus="loop">            
                <tr id="bs${l.bookCode}">
                    <td style="text-align: center">${loop.index+1}</td>
                    <td>${l.bookName}</td>                    
                    <td style="text-align: center">${l.code}</td>   
                    <td style="text-align: center"><i>Đã thanh lý</i></td>
                    <td style="text-align: center">${l.dateDelete}</td>   
                </tr>
            </c:forEach>               
        </tbody>
    </table>
</div> 
<%@include file="include/Footer_admin.jsp" %>