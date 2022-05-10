<%-- 
    Document   : ListBooks
    Created on : Nov 24, 2021, 9:23:25 PM
    Author     : ThanhNhan
--%>

<%@include file="./include/Header_admin.jsp" %>
<%@page contentType="text/html" errorPage="error.jsp" pageEncoding="UTF-8"%> 

<div class="container-fluid" style="margin-top: 20px"> 
    <table class="table" style="font-size: 18px" cellspacing="3" cellpadding="3">

        <thead>
            <tr>
                <th scope="col">Số thứ tự</th>   
                <th scope="col">Tên thiết bị</th>                                                                
                <th scope="col">Mã thiết bị</th> 
                <th scope="col">Trạng thái</th>  
                <th scope="col">Ngày thanh lý</th>  

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="l" varStatus="loop">            
                <tr id="ds${l.deviceCode}">
                    <td style="text-align: center">${loop.index+1}</td>
                    <td>${l.deviceName}</td>                    
                    <td style="text-align: center">${l.code}</td> 
                    <td style="text-align: center"><i>Đã thanh lý</i></td>
                    <td style="text-align: center"><i>${l.dateDelete}</i></td>
                </tr>
            </c:forEach>               
        </tbody>
    </table>
</div> 
<script>    
        function deleteDevice(id){
        var conf = confirm("Bạn có chắc chắn muốn xóa không?");
        if(conf){
            $.ajax({
                url: '/dangdung.lib/deletedevice',
                type: 'GET',
                data: {
                    id: id,                    
                },
                success: function () {
                    document.getElementById("ds"+id).remove();
                    alert("Xóa thành công!")
                }
            })
        }
    }    
</script>
<%@include file="include/Footer_admin.jsp" %>