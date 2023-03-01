<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DangDangEAT - Wishlist</title>
<!-- 외부 CSS 가져오기 -->
<!-- <link href="css/bootstrap.css" rel="stylesheet" type="text/css"> -->
<link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
<style type="text/css">
@font-face {
	font-family: 'GmarketSansMedium';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

body {
	font-family: "GmarketSansMedium";
}

table {
	margin: auto;
}

#id, #empty {
	text-align: center;
	font-size: 1.5em;
	margin-bottom: 10px;	
}

th {
	text-align: center;
}

input {
	font-family: "GmarketSansMedium";
}

#cart {
	margin-bottom: 5px;		                       
}

#buttonBox {
	text-align: center;
	vertical-align: center;
}

td {
	vertical-align: middle; 
}

input[type=text] {
    font-family:"GmarketSansMedium" ;
    border: none;
    text-align: center;
}

#pageList {
	text-align: center;
	margin-bottom: 30px;	
}
</style>
<script src="resources/js/jquery-3.6.3.js"></script>
<script type="text/javascript">
// 장바구니 개별 담기
function cartInsert(pro_code){
	$.ajax({
		type: "GET",
		url: "CartInsert",
		data: {
			pro_code : pro_code
		},
		success: function(result){
        	 if(result == "true"){
	             alert("장바구니에 상품이 담겼습니다.");
        	 } else if(result == "false") {
	             alert("상품 담기가 실패했습니다.");
        	 } else if(result == "duplication"){
        		 alert("이미 담겨있는 상품입니다.")
        	 }
		}
	});
}

//개별 삭제
function wishlistDelete(pro_code){
	$.ajax({
		type: "GET",
		url: "WishlistDelete",
		data: {
			pro_code : pro_code
		},
		success: function(result){
        	 if(result == "true"){
	             alert("위시리스트에서 삭제되었습니다.");
	             location.reload();
        	 } else if(result == "false") {
	             alert("상품 삭제에 실패했습니다.");
        	 }
		}
	});
}

$(function() {
	//전체선택 버튼 클릭
	$('input:checkbox[name=AllChecked]').on("click",function(){
		if($(this).is(":checked") == true){
			$("input[name=inChecked]").prop("checked",true);
		}else{
			$("input[name=inChecked]").prop("checked",false);
		}
	});	//전체선택버튼
	
	// 다중삭제버튼
	$("#checkDeleteBtn").click(function(){
		let wishArr = new Array();
		$('input:checkbox[name=inChecked]').each(function(){
			if($(this).is(":checked")==true){
				let index = $(this).val();
				let wishlist = new Object();
				wishlist.pro_code = $("#pro_code" + index).val();
				wishArr.push(wishlist);
			}
		});
		
		$.ajax({
			type: "POST",
			url: "WishlistDeleteJson",
			contentType: "application/json",
			data: JSON.stringify(wishArr),
			success: function(result){
	        	 if(result == "true"){
		             alert("선택한 상품이 삭제되었습니다.");
		             location.reload();
	        	 } else if(result == "false") {
		             alert("삭제에 실패했습니다.");
	        	 }
			},
			fail: function() {
				alert("요청 실패!");
			}
		});
		
	}); //다중삭제버튼
	
	
});
</script>
</head>
<body>
	<!-- Login, Join 링크 표시 영역 -->
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- 위시리스트 번호, 사진(대표이미지), 상품명, 사이즈,색상,수량,가격, 삭제 -->
	<div id="id">${sessionScope.sId }님의 위시리스트</div>
	<div class="container">
		<table class="table">
			<tr>
				<th><input type="checkbox" id="AllChecked" name="AllChecked"></th>
				<th>이미지</th>
				<th>상품명</th>
				<th>가격</th>
				<th>선택</th>
			</tr>
			<c:choose>
				<c:when test="${empty wishlist }">
					<tr>
						<td colspan="5">
							<div id="empty">위시리스트가 비어있습니다</div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="wishlist" items="${wishlist }" varStatus="i">
						<tr>
							<td style="text-align: center;">
								<input type="checkbox" value="${i.index }" name="inChecked" id="inChecked">
								<input type="hidden" value="${wishlist.pro_code }" name="pro_code${i.index }" id="pro_code${i.index }">
							</td>
							<td>
							<div style="text-align: center;">
		                      <img class="card-img-top" style="width: 100px; height: 100px;"
				                src="<%=request.getScheme()+"://"+request.getServerName() + ":" + request.getServerPort() +"/"+request.getContextPath()%>/upload/${cart.pro_real_thumb }"
		        		        alt="..." onerror="this.src='${pageContext.request.contextPath}/resources/img/sample1_thumb.png';" />
		        		        </div>
							</td>
							<td><a
								href="ProductDetail.pd?pro_code=${wishlist.pro_code }">${wishlist.pro_name }</a></td>
							<td>
							<div style="text-align: center;">
							<fmt:formatNumber value="${wishlist.pro_price }" pattern="###,###,###" />
							</div>
							</td>
							<td>
								<div id="buttonBox">
								<input type="button" value="장바구니" id="cart" onclick="cartInsert(${wishlist.pro_code})">
								<input type="button" value="삭제" id="delete" onclick="wishlistDelete(${wishlist.pro_code})">
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	<div class="container">
		<input type="button" value="삭제" id="checkDeleteBtn">
		<input type="button" value="장바구니 추가" id="checkCartBtn">
	</div>

<!-- //////////////////////////// 페이징 처리 ////////////////////////////   -->
<div class="float-right">
   <section id="pageList">
      <c:choose>
               <c:when test="${empty param.pageNum }">
                  <c:set var="pageNum" value="1" />
               </c:when>
               <c:otherwise>
                  <c:set var="pageNum" value="${param.pageNum }" />
               </c:otherwise>
            </c:choose>
      <c:choose>
         <c:when test="${pageNum > 1}">
            <input type="button" value="이전" onclick="location.href='WishlistDetail?pageNum=${pageNum - 1}'">
         </c:when>
         <c:otherwise>
            <input type="button" value="이전">
         </c:otherwise>
      </c:choose>
         
      <!-- 페이지 번호 목록은 시작 페이지(startPage)부터 끝 페이지(endPage) 까지 표시 -->
      <c:forEach var="i" begin="${pageInfo.startPage }" end="${pageInfo.endPage }">
         <!-- 단, 현재 페이지 번호는 링크 없이 표시 -->
         <c:choose>
            <c:when test="${pageNum eq i}">
               ${i }
            </c:when>
            <c:otherwise>
               <a href="WishlistDetail?pageNum=${i }">${i }</a>
            </c:otherwise>
         </c:choose>
      </c:forEach>

      <!-- 현재 페이지 번호(pageNum)가 총 페이지 수보다 작을 때만 [다음] 링크 동작 -->
      <c:choose>
         <c:when test="${pageNum < pageInfo.maxPage}">
            <input type="button" value="다음" onclick="location.href='WishlistDetail?pageNum=${pageNum + 1}'">
         </c:when>
         <c:otherwise>
            <input type="button" value="다음">
         </c:otherwise>
      </c:choose>
   </section>
</div>
<!-- //////////////////////////// 페이징 처리 ////////////////////////////   -->
	

<!-- Footer-->
<footer class="py-5 bg-dark margin">
	<div class="container">
		<p class="m-0 text-center text-white">Copyright &copy;
			DangDangEat 2023</p>
	</div>
</footer>
</body>
</html>