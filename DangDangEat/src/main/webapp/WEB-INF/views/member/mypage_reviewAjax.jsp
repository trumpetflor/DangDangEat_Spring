<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.review_content{
	overflow: hidden;  /*박스에서 넘쳐난 텍스트 숨기기*/ 
	text-overflow: ellipsis; /* ... 말줄임 효과*/ 
	
}

.one-review-div{
	height: 200px;
	width: 100%;
	overflow: hidden;  /*박스에서 넘쳐난 텍스트 숨기기*/ 
	text-overflow: ellipsis; /* ... 말줄임 효과*/ 
	 white-space: nowrap;
  
	
}

</style>
<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<script type="text/javascript">

</script>

	<div class="container" id="mypage_reviewAjax">
		<div class="card" id="titleDiv">
			<div class="card-header">
				<strong>내가 쓴 리뷰</strong>
			</div>
		<div id="in-container">
			<div class="card-body card-block" id="reviewWapper">

			
					<div class='card-body card-block'>
					
					<!-- 리뷰가 없을 경우 -->
						<c:if test="${reviewList.size() eq 0}">
							<div class='d-flex py-2  justify-content-center'>
								<div class='align-self-center p-2 mx-4'><img src='${pageContext.request.contextPath}/resources/img/no_review.png' width='100px'></div>
								<div class='align-self-center p-2'> 작성하신 리뷰가 없습니다.</div>
							</div>
						</c:if>
					
					<!-- 리뷰 리스트 -->
					<c:forEach items="${reviewList}" var="review">
						<div  class='d-flex my-1 one-review-div border-bottom' >
							<!-- 상품 이미지 영역 -->
							<div class='p-3' >
								<a href="ProductDetail.pd?pro_code=${review.pro_code}">
									<img width="150" src="<%=request.getScheme()+"://"+request.getServerName() + ":" + request.getServerPort() +"/"+request.getContextPath()%>/resources/upload/${review.pro_real_thumb  }"
														alt="..." onerror="this.src='${pageContext.request.contextPath}/resources/img/sample1_thumb.png';" 
														/>
								</a>
							</div>
							<!-- 리뷰 영역 -->
							<div class='p-3 mr-2 border-start review_content mb-1'>
							<a href="ProductDetail.pd?pro_code=${review.pro_code}">
								<small class="text-secondary"><b>${review.pro_name } | ${review.review_date  }</b></small><br>
								${review.review_content  }
								</a>
							</div>
						</div>
					</c:forEach>
			
					</div> 
			
					
	
	</div>
		</div><!-- div #in-container -->
		
		</div>
	</div>

	
