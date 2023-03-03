<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>DangDangEAT - Admin Main</title>
<%
// 세션 아이디가 null 이거나 "admin" 이 아닐 경우 "잘못된 접근입니다!" 출력 후 메인페이지로 이동
String sId = (String)session.getAttribute("sId");
// System.out.println(sId);
// 잘못된 접근일 때 바로 main.jsp 로 보내기
if(sId == null || !sId.equals("admin")) {
	%>
	<script>
		alert("잘못된 접근입니다!");
		location.href = "./";
	</script>
	<%
}
%>

<!-- Custom fonts for this template -->
<link href="${pageContext.request.contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

<!-- 폰트 설정 -->
<style>
@font-face {
    font-family: 'GmarketSans';
    font-weight: normal;
    font-style: normal;
    src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.eot');
    src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.eot?#iefix') format('embedded-opentype'),
         url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.woff2') format('woff2'),
         url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.woff') format('woff'),
         url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.ttf') format("truetype");
    font-display: swap;
} 
  @font-face { /*지마켓 산스*/
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}    

body {
    font-family: 'GmarketSansMedium';
/*     vertical-align: middle; */
}

#accordionSidebar{
	font-family: 'GmarketSans';
}
/* ======================================= */
/* @font-face { */
/*     font-family: 'GmarketSans'; */
/*     font-weight: 300; */
/*     font-style: normal; */
/*     src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.eot'); */
/*     src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.eot?#iefix') format('embedded-opentype'), */
/*          url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.woff2') format('woff2'), */
/*          url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.woff') format('woff'), */
/*          url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.ttf') format("truetype"); */
/*     font-display: swap; */
/* }  */

/* body { */
/*     font-family: 'GmarketSans'; */
/* } */

.main-icon {
    padding: 1%;
    height: 40px;
    width: auto;
	margin-right: 3%;
	padding: 5px;
}
</style>

<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<script type="text/javascript">	
	
	function confirmDelete(id) {
		// confirm dialog 사용하여 "XXX 회원 기록을 삭제하시겠습니까?" 확인 요청
		let result = confirm(id + " 회원 기록을 삭제하시겠습니까?");
		
		if(result) {
			location.href = "MemberDelete.ad?id=" + id;
		}
	}


</script>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->

            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="AdminMain.ad">
                <div class="div-top-icon">
                <img class="main-icon" src="${pageContext.request.contextPath}/resources/img/maindog_white.png">
<!--                     <i class="fas fa-laugh-wink"></i> -->
                </div>

                <div class="sidebar-brand-text mx-3">DangDangEat Admin</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->

            <li class="nav-item active">
                <a class="nav-link" href="AdminMain">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>관리자 메인 페이지</span></a>
            </li>


            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                관리 페이지
            </div>


            <!-- Nav Item - Tables -->
            <li class="nav-item active">
                <a class="nav-link" href="AdminProductList">
                    <i class="fas fa-fw fa-calendar"></i>
                    <span>상품 관리</span></a>
            </li>
            <!-- Nav Item - Tables -->
            <li class="nav-item active">
                <a class="nav-link" href="AdminMemberList.ad">
                    <i class="fas fa-fw fa-table"></i>
                    <span>회원 관리</span></a>
            </li>
            <!-- Nav Item - Tables -->
            <li class="nav-item active">
                <a class="nav-link" href="AdminOrderList.ad">
                    <i class="fas fa-fw fa-dollar-sign"></i>
                    <span>주문 관리</span></a>
            </li>
            <!-- Nav Item - Tables -->
            <li class="nav-item active">
                <a class="nav-link" href="AdminCouponList.ad">
                    <i class="fas fa-fw fa-clipboard-list"></i>
                    <span>쿠폰 관리</span></a>
            </li>
            <!-- Nav Item - Tables -->
            <li class="nav-item active">
                <a class="nav-link" href="AdminBoardList.ad">
                    <i class="fas fa-fw fa-comments"></i>
                    <span>게시판 관리</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <form class="form-inline">
                        <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                            <i class="fa fa-bars"></i>
                        </button>
                    </form>

                    <!-- Topbar Search -->
                    <form
                        class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                        <div class="input-group">

<!--                             <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." -->
<!--                                 aria-label="Search" aria-describedby="basic-addon2"> -->
                            <div class="input-group-append">
<!--                                 <button class="btn btn-primary" type="button"> -->
<!--                                     <i class="fas fa-search fa-sm"></i> -->
<!--                                 </button> -->
                            </div>
                        </div>
                    </form>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">

                        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                        <li class="nav-item dropdown no-arrow d-sm-none">
                            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-search fa-fw"></i>
                            </a>
                            <!-- Dropdown - Messages -->
                            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                                aria-labelledby="searchDropdown">
                                <form class="form-inline mr-auto w-100 navbar-search">
                                    <div class="input-group">
                                        <input type="text" class="form-control bg-light border-0 small"
                                            placeholder="Search for..." aria-label="Search"
                                            aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary" type="button">
                                                <i class="fas fa-search fa-sm"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>


                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 font-weight-bold">Admin</span>
                                <img class="img-profile rounded-circle"
                                    src="${pageContext.request.contextPath}/resources/img/dangprofile.png">
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="./">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Member Main
                                </a>
                                <a class="dropdown-item" href="CheckPassForm.me">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Profile
                                </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="MemberLogout.me">
                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Logout
                                </a>
                            </div>
                        </li>

                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->

                     <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">관리자 메인 페이지</h1>
<!--                         <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i -->
<!--                                 class="fas fa-download fa-sm text-white-50"></i> Generate Report</a> -->
                    </div>

                    <!-- Content Row -->
                    <div class="row">

                        <!-- Earnings (Monthly) Card Example -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-primary shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-primary text-uppercase mb-1">
                                                한달 가입자 수</div>
                                            <div class="h5 mb-0 text-gray-800"><fmt:formatNumber value="${joinCount }" pattern="###,###,###"/></div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Earnings (Monthly) Card Example -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-success shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-success text-uppercase mb-1">
                                                한달 매출액</div>
                                            <div class="h5 mb-0 text-gray-800"><fmt:formatNumber value="${salesTotal }" pattern="###,###,###"/></div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Earnings (Monthly) Card Example -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-info shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-info text-uppercase mb-1">한달 주문 수
                                            </div>
                                            <div class="row no-gutters align-items-center">
                                                <div class="col-auto">
                                                    <div class="h5 mb-0 mr-3 text-gray-800">
                                                    <fmt:formatNumber value="${orderCount }" pattern="###,###,###"/></div>
                                                </div>
                                                <div class="col">
                                                    <div class="progress progress-sm mr-2">
                                                        <div class="progress-bar bg-info" role="progressbar"
                                                            style="width: 50%" aria-valuenow="50" aria-valuemin="0"
                                                            aria-valuemax="100"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Pending Requests Card Example -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-warning shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-warning text-uppercase mb-1">
                                                한달 리뷰 수</div>
                                            <div class="h5 mb-0 text-gray-800">
                                            <fmt:formatNumber value="${reviewCount }" pattern="###,###,###"/></div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-comments fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>



<!-- ==================================================================== -->
					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 text-primary">위시리스트 랭킹</h6>

						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>찜한 갯수</th>
											<th>상품 코드</th>
											<th>상품명</th>
											<th>카테고리</th>
											<th>브랜드</th>
											<th>상품 가격</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="wish" items="${wishlist }">
											<tr>
												<td>${wish.wish_count }</td>
												<td class="pro_code">${wish.pro_code }</td>
												<td>${wish.pro_name }</td>
												<td class="form" id="cate_code" name="cate_code">
													<c:if test="${wish.cate_code eq '1'}">사료</c:if>
													<c:if test="${wish.cate_code eq '2'}">간식</c:if>
													<c:if test="${wish.cate_code eq '3'}">파우더 · 토핑</c:if>
													<c:if test="${wish.cate_code eq '4'}">껌 · 츄르</c:if>
													<c:if test="${wish.cate_code eq '5'}">건강보조제</c:if>
												</td>
												<td>${wish.pro_brand }</td>
												<td>${wish.pro_price } 원</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
<!-- ==================================================================== -->


    <!-- Bootstrap core JavaScript-->
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="${pageContext.request.contextPath}/resources/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->

<!--     <script src="vendor/chart.js/Chart.min.js"></script> -->

<!--     Page level custom scripts -->
<!--     <script src="js/demo/chart-area-demo.js"></script> -->
<!--     <script src="js/demo/chart-pie-demo.js"></script> -->

    <script src="${pageContext.request.contextPath}/resources/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="${pageContext.request.contextPath}/resources/js/demo/datatables-demo.js"></script>


</body>

</html>