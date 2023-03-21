<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>DangDangEAT - Review Write</title>
    <meta name="description" content="Ela Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">
    <link rel="shortcut icon" href="https://i.imgur.com/QRAUqs9.png">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/normalize.css@8.0.0/normalize.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lykmapipo/themify-icons@0.1.2/css/themify-icons.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pixeden-stroke-7-icon@1.2.3/pe-icon-7-stroke/dist/pe-icon-7-stroke.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.2.0/css/flag-icon.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/style.css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/lib/chosen/chosen.min.css">

    <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->

<style>
	@font-face {
	    font-family: 'GmarketSansMedium';
	    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
	
	body {
	    font-family:"GmarketSansMedium" ;
	}
		
	#client_table{
		 vertical-align: middle;
	}
	table{
	 text-align: center;
	}
	
	td {
	height: 100px;
	}
		
	.id_pht{
	 width: 80px;
	 transition: 0.5s;
	}
	
	.grandchildren {
		display: none;
		position: absolute;
	}
	
	.children {
		position: relative;
	}
	
	.children:hover .grandchildren {
		display: block;
	}
	
	.col-lg-10{
		margin: auto;
		flex: 100 100;
	}
	
	.click:not(:disabled):not(.disabled) {
    cursor: pointer;
	}
	
</style>


<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<script type="text/javascript">
	
	$(function() {
		
		// submit 막기
// 		$("form").submit(function() {
// 			if(!$isConfirmBn) {
// 				alert("거래처 코드를 확인해주세요.");
// 				return false;
// 			}
// 		});
		
		
		// 파일 입력창 추가
		$("#add").on("click", function(){
			$("#jongmokArea").append(
					'<input type="file" name="jongmok" placeholder="ex) 동물용 사료 및 조제식품 제조업" class="form-control">'
					+'<small class="form-text text-muted text-right click" name="remove">- 삭제</small>');
		});
	
	});

	// 종목 입력창 삭제
	$(document).on("click", "small[name=remove]", function(){
// 		alert("종목 삭제");
		$(this).prev().remove();
		$(this).remove();
	});
	
	function confirmInsert() {
		let result = confirm("리뷰 작성 후 수정/삭제가 불가능합니다. 해당 리뷰를 등록하시겠습니까?");
		if(result) {
			$("form").submit();
		}
	}
	
	function confirmReset() {
		let result = confirm("리뷰 작성을 취소하시겠습니까?");
		if(result) {
			location.reload(); // 이게.. 맞나..
		}
	}
	
// 	function close() {
// 		opener.parent.location='ClientList'; // 부모창 새로고침
// 		opener.parent.location.reload(); // 부모창 새로고침
// 		window.close(); // 자식창 닫기
// 	}
	
</script>

</head>
<body>

        <div class="content">
            <div class="animated fadeIn">

                <div class="row">

                    <div class="col-lg-10">
                        <div class="card">
                            <div class="card-header">
                                <strong>상품평 작성</strong>
                            </div>
                            <div class="card-body card-block">
                                <form action="ReviewWritePro" method="post" class="form-horizontal"  enctype="multipart/form-data">
                                	<input type="hidden" name="pro_code" value="${product.pro_code }"> <!-- 기존 거래처 코드 (UPDATE 구문 시 필요) -->
                                    <div class="row form-group">
                                        <div class="col col-md-3"><label for="pro_name" class=" form-control-label font-weight-bold">상품명</label></div>
                                        <div class="col-12 col-md-9"><span class="form-control-static bg-gradient-light" id="pro_name">${product.pro_name }</span></div>
                                    </div>
                                    <div class="row form-group">
                                        <div class="col col-md-3"><label for="review_subject" class=" form-control-label font-weight-bold">상품평 제목<font style="color: red;">*</font></label></div>
                                        <div class="col-12 col-md-9"><input type="text" id="review_subject" name="review_subject" class="form-control" required="required"><small class="form-text text-muted"></small></div>
                                    </div>
                                    <div class="row form-group">
                                        <div class="col col-md-3"><label for="review_pass" class=" form-control-label font-weight-bold">비밀번호<font style="color: red;">*</font></label></div>
                                        <div class="col col-md-9">
                                          <input type="password" name="review_pass" class="form-control" required="required">
                                          <small class="help-block form-text text-muted"></small>
                                        </div>
                                    </div>
                                    <div class="row form-group">
                                        <div class="col col-md-3"><label for="review_content" class=" form-control-label font-weight-bold">상품평 내용<font style="color: red;">*</font></label></div>
                                        <div class="col-12 col-md-9"><textarea name="review_content" id="review_content" rows="9" class="form-control"></textarea></div>
                                    </div>
                                    <div class="row form-group">
                                        <div class="col col-md-3"><label for="files" class=" form-control-label font-weight-bold">상품 사진</label></div>
                                        <div class="col col-md-9">
                                            <input type="file" name="files" />
                                        </div>
                                    </div>
                            <div class="card-footer">
                                <button type="button" class="btn btn-primary btn-sm" onclick="confirmInsert()">
                                    <i class="fa fa-dot-circle-o"></i> 등록하기
                                </button>
                                <button type="button" class="btn btn-danger btn-sm" onclick="confirmReset()">
                                    <i class="fa fa-ban"></i> Reset
                                </button>
                                <button type="button" class="btn btn-primary btn-sm" onclick="opener.parent.location='ProductDetail.pd?pro_code=${product.pro_code }'; window.close();">
                                    <i class="fa fa-dot-circle-o"></i> 창닫기
                                </button>
                            </div>
                            </form>
                        </div>

           			 </div>
          		  </div>


       		 </div><!-- .animated -->
    	</div><!-- .content -->

    <div class="clearfix"></div>

<!-- Right Panel -->

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/lib/chosen/chosen.jquery.min.js"></script>

<script>
    jQuery(document).ready(function() {
        jQuery(".standardSelect").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!",
            width: "100%"
        });
    });
</script>

</body>
</html>