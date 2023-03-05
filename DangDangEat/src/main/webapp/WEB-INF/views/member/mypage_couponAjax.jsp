<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<script type="text/javascript">

//쿠폰검색 버튼 클릭 시 ajax로 쿠폰 검색 
$(document).on("click","#c-search-btn",function(){
	console.log("#c-search-btn 클릭됨");
		if($("#search_coupon_code").val() == ""){
			alert("쿠폰 코드를 입력해주세요 !");
			$("#search_coupon_code").focus();
			return;
		}
	
		$.ajax({
			type: "get",
			url: "SearchCouponCode.ajax",
			dataType: "json",
			data: {"cp_code": $("#search_coupon_code").val()}
			
		}).done(function(result){
// 			alert("result: "+result);
// 			alert("result: "+JSON.stringify(result));
			if(result == false){
				alert("사용가능한 쿠폰이 아닙니다.")
			}else if(result == true ){
				alert("이미 등록된 쿠폰입니다")
			}else{
				
//				alert(JSON.stringify(result));
				$("#couponDiv").empty(); //쿠폰리스트 초기화 후 다시 뿌리기
				
				for(let coupon of result) {
					$("#couponDiv").append(
							"<div  class='border rounded-2 d-flex my-1'>"
							+"<div class='p-5 '>"
								+"<h3>"+coupon.cp_name+" ("+coupon.cp_discount_value+" % 할인)</h3> "
								   +coupon.cp_min_price+ " 원 이상 구매 시 (최대 "+ coupon.cp_max_discount + " 원)<br>"
								  + coupon.target_sd +" - "+coupon.target_ed+" ("+ coupon.cp_period + " 일)"
								+"</div>"
							+"</div>"
					);

				}//end of for
			
			}//end of else
			
		}).fail(function(data){
			alert("요청실패 : " + data);
		});
		
});//$(document)
</script>

	<div class="container" id="mypage_couponAjax">
		<div class="card" id="titleDiv">
			<div class="card-header">
				<strong>내가 가진 쿠폰</strong>
			</div>
		<div id="in-container">
<%-- <%=request.getAttribute("couponList").toString().compareTo("[]") %> --%>
<%-- <%=request.getAttribute("couponList") %> --%>

			<div class="card-body card-block" id="couponWapper">

					<div class='m-3 d-flex justify-content-end'> 
				       <input type='text'  class='col-sm-4 bg-light border border-secondary rounded px-0' id='search_coupon_code' name='search_coupon_code' placeholder=' 쿠폰코드를 입력하세요.'>
				       <input type='button' value='쿠폰 등록' class='mx-2 btn btn-sm  btn-outline-dark rounded' id='c-search-btn'></div>

			

					<div class='card-body card-block' id='couponDiv'>
				<% if (request.getAttribute("couponList").toString().compareTo("[]")  == 0){%>
				
							<div class='d-flex py-2 border border-dark rounded-2'>
								<div class='align-self-center p-2'><img src='${pageContext.request.contextPath}/resources/img/no_coupon.png' width='100px'></div>
								<div class='align-self-center p-2'>사용가능한 쿠폰이 없습니다.</div>
							</div>
					<% }else{%> 
						
						<% JSONArray couponList = (JSONArray)request.getAttribute("couponList");
						for(int i=0; i<couponList.length(); i++){ 
						JSONObject coupon = couponList.getJSONObject(i); 
 	 					//System.out.println("   mypage_couponAjax.jsp  :" + couponList); %>  
					
					<div  class='border rounded-2 d-flex my-1'>
						<div class='p-5 '>
							 <h3><%=coupon.get("cp_name") %> (<%=coupon.get("cp_discount_value") %> % 할인) </h3>  
							  
							    <%=coupon.get("cp_min_price") %> 원 이상 구매 시 (최대 <%=coupon.get("cp_max_discount") %> 원)<br>
							  <%=coupon.get("target_sd") %> - <%=coupon.get("target_ed") %> ( <%=coupon.get("cp_period") %> 일)
						</div>
					</div>
				<% }} %>
					</div> 
			
					
				
	</div>
	</div>
		<div class="card" id="">
			<div class="card-header">
				<strong>쿠폰 사용 내역</strong><small> (*최근 10개까지만 표시됩니다)</small>
			</div>
		<div id="in-container">
			<div class="m-3">
				<table class="table">
					<tr>
						<th>쿠폰명</th>
						<th>사용 유무</th>
						<th>상태</th>
					</tr>
					<c:forEach items="${couponHistory}" var="cp_his">
					<c:choose>
						<c:when test="${cp_his.mc_stat eq 1 }">
							<tr>
						</c:when>
						<c:otherwise>
							<tr style="color: gray;">
						</c:otherwise>
					</c:choose>
					
							<td>${cp_his.cp_name }
								<br><small>${cp_his.target_sd  } - ${cp_his.target_ed  }</small>
							</td>
							<!-- 사용유무 -->
							<c:choose>
								<c:when test="${cp_his.mc_used eq 'N' }">
									<td>미사용</td>
								</c:when>
								<c:otherwise>
									<td>사용완료</td>
								</c:otherwise>
							</c:choose>
							<!-- 상태 -->
							<c:choose>
								<c:when test="${cp_his.mc_stat eq 1 }">
									<td>사용가능</td>
								</c:when>
								<c:otherwise>
									<td>기한만료</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div><!-- div #in-container -->
		
		</div>
	</div>

	
