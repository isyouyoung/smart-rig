
$(document).ready(function () {

    // 재고 조회 버튼
    $("#btnSearchStock").on("click", function () {

        console.log("조회 버튼 클릭");

    });



    // 메인 화면 이동
    $("#btnHome").on("click", function () {

        location.href = "/html/index.html";

    });

});