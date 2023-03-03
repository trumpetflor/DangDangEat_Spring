<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%
request.setCharacterEncoding("UTF-8");
int finalTotal = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DangDangEAT - Cart</title>
<!-- 외부 CSS 가져오기 -->
<!-- <link href="css/bootstrap.css" rel="stylesheet" type="text/css"> -->
<link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" /> 
<style type="text/css">
	@font-face {
	    font-family: 'GmarketSansMedium';
	    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
	  
	body {
	    font-family:"GmarketSansMedium" ;
	}
	
	#total {
		text-align: right;
		margin-bottom: 30px;
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
	
	input, input[type=number] {
	    font-family:"GmarketSansMedium" ;
	}
	
 	input[type=number] { 
 		margin-bottom: 5px;		
 	} 
	
	input[type=text] {
	    font-family:"GmarketSansMedium" ;
	    border: none;
	    text-align: center;
	}
	
	#buttonBox {
		text-align: center;
		vertical-align: center;
	}
	
	#order, #wishlist {
		margin-bottom: 5px;		                       
	}
	
	td {
 		vertical-align: middle; 
	}
	
	#pageList {
		text-align: center;
		margin-bottom: 30px;	
	}
</style>
<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<script type="text/javascript">
$(function() {
	    // 주문
	    $("#order").on("click", function() {
	        $("#result").val();
// 	       alert($("#result").val());
	    });
	    
// 	    $("#amount").on("click", function(){
// 	    	alert($("#amount").val());
// // 	    	console.log()
// 	    })


       
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
		let cartArr = new Array();
		$('input:checkbox[name=inChecked]').each(function(){
			if($(this).is(":checked")==true){
				let index = $(this).val();
				let cart = new Object();
				cart.pro_code = $("#pro_code" + index).val();
				cartArr.push(cart);
			}
		});
		
		$.ajax({
			type: "POST",
			url: "CartDeleteJson",
			contentType: "application/json",
			data: JSON.stringify(cartArr),
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

	// 위시리스트 다중추가 버튼
	$("#checkWishBtn").click(function(){
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
			url: "WishlistInsertJson",
			contentType: "application/json",
			data: JSON.stringify(wishArr),
			success: function(result){
	        	 if(result == "true"){
		             alert("선택한 상품이 위시리스트에 추가되었습니다.");
		             location.reload();
	        	 } else {
	        		 alert("위시리스트 추가에 실패했습니다.");
	        	 }
			},
			fail: function() {
				alert("요청 실패!");
			}
		});
		
	}); //다중 위시 추가
	
}); // jQuery

// 위시리스트 개별 추가
function wishlistInsert(pro_code){
	$.ajax({
		type: "GET",
		url: "WishlistInsert",
		data: {
			pro_code : pro_code
		},
		success: function(result){
        	 if(result == "true"){
	             alert("위시리스트에 상품이 담겼습니다.");
        	 } else if(result == "false") {
	             alert("상품 담기가 실패했습니다.");
        	 } else if(result == "duplication"){
        		 alert("이미 담겨있는 상품입니다.")
        	 }
		}
	});
}

// 개별 삭제
function cartDelete(pro_code){
	$.ajax({
		type: "GET",
		url: "CartDelete",
		data: {
			pro_code : pro_code
		},
		success: function(result){
        	 if(result == "true"){
	             alert("장바구니에서 삭제되었습니다.");
	             location.reload();
        	 } else if(result == "false") {
	             alert("상품 삭제에 실패했습니다.");
        	 }
		}
	});
}

// 수량 수정
function amountModify(pro_code, index){
	var amount = $("#cart_amount"+index).val();
	$.ajax({
		type: "GET",
		url: "CartAmountModify",
		data: {
			pro_code : pro_code,
			cart_amount : amount
		},
		success: function(result){
        	 if(result == "true"){
	             location.reload();
        	 } else if(result == "false") {
	             alert("수량 변경에 실패했습니다.");
        	 }
		}
	});
}

</script>
</head>
<body>
	<!-- Login, Join 링크 표시 영역 -->
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- 장바구니 번호, 사진(대표이미지), 상품명, 사이즈,색상,수량,가격, 삭제 -->
	<div id="id">${sessionScope.sId }님의 장바구니</div>
	<div class="container" style="margin-bottom: 10px;">
	 <table class="table">
		<tr>
		   <th><input type="checkbox" id="AllChecked" name="AllChecked"></th>
		   <th>이미지</th>
		   <th>상품명</th>
		   <th>가격</th>
		   <th style="width: 120px;">수량</th>
		   <th>총금액</th>
		   <th style="width: 50px;">선택</th>
		</tr>
		<c:choose>
			<c:when test="${empty cartList }">
				<tr>
					<td colspan="7">
						<div id="empty">장바구니가 비어있습니다</div>
					</td>
				</tr>
			</c:when>
		<c:otherwise>
			<c:forEach var="cart" items="${cartList }" varStatus="i">
				<input type="hidden" name="cart_code" value=${cart.cart_code }>
				<tr>
					<td>
						<input type="checkbox" value="${i.index }" name="inChecked" id="inChecked">
						<input type="hidden" value="${cart.pro_code }" name="pro_code${i.index }" id="pro_code${i.index }">
					</td>
					<td>
					<div style="text-align: center;">
                      <img class="card-img-top" style="width: 100px; height: 100px;"
		                src="<%=request.getScheme()+"://"+request.getServerName() + ":" + request.getServerPort() +"/"+request.getContextPath()%>/upload/${cart.pro_real_thumb }"
        		        alt="..." onerror="this.src='${pageContext.request.contextPath}/resources/img/sample1_thumb.png';" />
        		        </div>
					</td>
					<td><a href="ProductDetail.pd?pro_code=${cart.pro_code }">${cart.pro_name }</a></td>
					<td>
						<div style="text-align: center;">
						<input type="text" id="price" value="<fmt:formatNumber value="${cart.pro_price }" pattern="###,###,###"/>">
						</div>
<%-- 					<fmt:formatNumber value="${cart.pro_price }" pattern="###,###,###"/> --%>
					</td>
					 <!-- 상품 수량 -->
                     <td class="text-center">
		                <input class="form-control text-center me-3" name="cart_amount" id="cart_amount${i.index }" type="number" value="${cart.cart_amount}" MIN="1" MAX="100" />
		             	<input type="button" value="변경" id="modify" onclick="amountModify(${cart.pro_code}, ${i.index })">
                     </td>
					<td>
						<div style="text-align: center;">
							<input type="text" id="lastPrice" value="<fmt:formatNumber value="${cart.pro_price * cart.cart_amount }" pattern="###,###,###"/>">
						</div>
					</td>
					<td>
						<div id="buttonBox">
						<input type="button" value="위시리스트" id="wishlist" onclick="wishlistInsert(${cart.pro_code})">
						<input type="button" value="주문하기" id="order" onclick="location.href='OrderForm.od?pro_code=${cart.pro_code}&cart_code=${cart.cart_code}'">
						<input type="button" value="삭제" id="delete" onclick="cartDelete(${cart.pro_code})">
						</div>
					</td>
					<c:set var="finalTotal" value="${finalTotal+(cart.pro_price*cart.cart_amount) }" />
				</tr> 
			</c:forEach>
		</c:otherwise>
		</c:choose>
	</table>
	</div>
	<div class="container">
		<input type="button" value="삭제" id="checkDeleteBtn">
		<input type="button" value="위시리스트 추가" id="checkWishBtn">
	</div>
	<div class="container" id="total">
		Total : <fmt:formatNumber value="${finalTotal }" pattern="###,###,###"/> 원
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
            <input type="button" value="이전" onclick="location.href='CartList?pageNum=${pageNum - 1}'">
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
               <a href="CartList?pageNum=${i }">${i }</a>
            </c:otherwise>
         </c:choose>
      </c:forEach>

      <!-- 현재 페이지 번호(pageNum)가 총 페이지 수보다 작을 때만 [다음] 링크 동작 -->
      <c:choose>
         <c:when test="${pageNum < pageInfo.maxPage}">
            <input type="button" value="다음" onclick="location.href='CartList?pageNum=${pageNum + 1}'">
         </c:when>
         <c:otherwise>
            <input type="button" value="다음">
         </c:otherwise>
      </c:choose>
   </section>
</div>
<!-- //////////////////////////// 페이징 처리 ////////////////////////////   -->
	
	
	
<!-- Footer-->
   <footer class="py-5 bg-dark">
      <div class="container">
         <p class="m-0 text-center text-white">Copyright &copy; DangDangEat 2023</p>
      </div>
   </footer>
</body>
</html>